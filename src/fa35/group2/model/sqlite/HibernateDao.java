package fa35.group2.model.sqlite;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
import java.io.*;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * based on fireandfuel's work
 * see https://github.com/fireandfuel/Niobe-Legion/blob/master/server/src/niobe/legion/server/Database.java
 */
public class HibernateDao {

    private static final int CONNECTIONS = 1;
    private static final String SQLITE_FILE = "db.sqlite";
    private static final String INIT_SQLITE_PATH = "/fa35/group2/model/sqlite/init.sqlite";
    private static final String PERSISTENCE_NAME = "who_gonna_help";
    private static final String PERSISTENCE_TEST_NAME = "who_gonna_help_test";

    private static HibernateDao dao;

    @PersistenceUnit
    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;
    EntityTransaction entityTransaction;

    public static HibernateDao getDao(boolean test) throws Exception {
        if (dao == null) {
            dao = new HibernateDao(test);
        }
        return dao;
    }

    HibernateDao(boolean test) {
        Map<String, Object> properties = new HashMap<String, Object>();

        properties.put("hibernate.connection.driver_class", "org.sqlite.JDBC");
        properties.put("hibernate.connection.url", "jdbc:sqlite:" + SQLITE_FILE);
        properties.put("hibernate.dialect", "fa35.group2.model.sqlite.SqliteDialect");
        properties.put("hibernate.c3p0.min_size", CONNECTIONS);
        properties.put("hibernate.c3p0.max_size", CONNECTIONS);
        properties.put("hibernate.c3p0.max_statements", "50");

        File dbFile = new File(SQLITE_FILE);
        if (!dbFile.exists()) {
            // copy initial database from jar to file system
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                inputStream = HibernateDao.class.getResourceAsStream(HibernateDao.INIT_SQLITE_PATH);
                outputStream = new FileOutputStream(dbFile);

                int read;
                byte[] buffer = new byte[65535];

                while ((read = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, read);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (outputStream != null) {
                    try {
                        // outputStream.flush();
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        this.entityManagerFactory = Persistence.createEntityManagerFactory(test ? PERSISTENCE_TEST_NAME : PERSISTENCE_NAME, properties);

        this.entityManager = this.entityManagerFactory.createEntityManager();
    }

    private void beginTransaction() {
        if (this.entityManager != null) {
            this.entityTransaction = this.entityManager.getTransaction();
            this.entityTransaction.begin();
        }
    }

    private void commitTransaction() {
        if (this.entityTransaction != null) {
            this.entityTransaction.commit();
            this.entityTransaction = null;
        }
    }

    private void rollbackTransaction() {
        if (this.entityTransaction != null) {
            this.entityTransaction.rollback();
            this.entityTransaction = null;
        }
    }

    public final <T> T getResult(final String queryName, final Class<T> clazz, Map.Entry... parameters) {
        return (T) this.getResult(queryName, clazz, parametersToMap(parameters));
    }

    public final <T> T getResult(final String queryName, final Class<T> clazz, Map<String, Object> parameters) {
        return (T) this.getResult(this.entityManager.createNamedQuery(queryName, clazz), parameters);
    }

    private <T> T getResult(final TypedQuery<T> query, final Map<String, Object> parameters) {
        if (parameters != null) {
            parameters.forEach(query::setParameter);
        }

        T result = null;
        try {
            this.beginTransaction();
            result = query.getSingleResult();
            this.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            this.rollbackTransaction();
        }
        return result;
    }

    public final <T> List<T> getResults(final Class<T> clazz) {
        CriteriaQuery criteriaQuery = this.entityManager.getCriteriaBuilder().createQuery(clazz);
        criteriaQuery.from(clazz);

        return this.getResults(this.entityManager.createQuery(criteriaQuery), null);
    }

    public final <T> List<T> getResults(final Class<T> clazz, final String parameters) {
        Query query = this.entityManager.createNativeQuery(parameters, clazz);

        List<T> results = null;
        try {
            this.beginTransaction();
            results = query.getResultList();
            this.commitTransaction();
        } catch (NoResultException e) {
        } catch (Exception e) {
            e.printStackTrace();
            this.rollbackTransaction();
        }
        return results;
    }

    public final <T> List<T> getResults(final String queryName, final Class<T> clazz, Map.Entry... parameters) {
        return this.getResults(queryName, clazz, parametersToMap(parameters));
    }

    public final <T> List<T> getResults(final String queryName, final Class<T> clazz, Map<String, Object> parameters) {
        return this.getResults(this.entityManager.createNamedQuery(queryName, clazz), parameters);

    }

    private <T> List<T> getResults(final TypedQuery<T> query, final Map<String, Object> parameters) {
        if (parameters != null) {
            parameters.forEach(query::setParameter);
        }

        List<T> results = null;
        try {
            this.beginTransaction();
            results = query.getResultList();
            this.commitTransaction();
        } catch (NoResultException e) {
        } catch (Exception e) {
            e.printStackTrace();
            this.rollbackTransaction();
        }
        return results;
    }

    public final <T> T insert(T object) {
        try {
            this.beginTransaction();
            // object may contain a list or array of objects which are already known to entityManager
            object = this.entityManager.merge(object);
            this.entityManager.persist(object);
            this.commitTransaction();
            return object;
        } catch (Exception e) {
            e.printStackTrace();
            this.rollbackTransaction();
        }
        return null;
    }

    public final <T> T update(T object) {
        T result = object;
        try {
            this.beginTransaction();
            result = this.entityManager.merge(object);
            this.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            this.rollbackTransaction();
        }

        return result;
    }

    public final <T> void remove(T object) {
        try {
            this.beginTransaction();
            this.entityManager.remove(object);
            this.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            this.rollbackTransaction();
        }
    }

    public static Map.Entry<String, Object> entry(String key, Object value) {
        return new AbstractMap.SimpleEntry<String, Object>(key, value);
    }

    private Map<String, Object> parametersToMap(Map.Entry<String, Object>... parameters) {
        return (parameters != null) ? Collections.unmodifiableMap(Stream.of(parameters).collect(entriesToMap())) : null;
    }

    private <K, U> Collector<Map.Entry<K, U>, ?, Map<K, U>> entriesToMap() {
        return Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue);
    }

    // only for testing
    protected static void reset() {
        dao = new HibernateDao(true);
    }
}
