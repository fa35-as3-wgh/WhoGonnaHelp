package fa35.group2.model.xml;

import fa35.group2.model.IPersistence;
import fa35.group2.model.IResetable;
import fa35.group2.model.entities.FriendEntity;
import fa35.group2.model.entities.IEntity;
import fa35.group2.model.entities.PaymentEntity;
import fa35.group2.model.entities.SkillEntity;
import org.xml.sax.SAXException;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Set;
import java.util.TreeMap;

public class XmlPersistence implements IPersistence, IResetable
{
    private static final String FILE_NAME = "db.xml";

    private static final String FILE_TEST_NAME = "test_db.xml";

    private Map<Integer, PaymentEntity> paymentEntityMap;

    private Map<Integer, SkillEntity> skillEntityMap;

    private Map<Integer, FriendEntity> friendEntityMap;

    private boolean test;

    @Override
    public boolean initializePersistence(boolean test)
    {
        this.test = test;

        this.paymentEntityMap = new TreeMap<Integer, PaymentEntity>();
        this.skillEntityMap = new TreeMap<Integer, SkillEntity>();
        this.friendEntityMap = new TreeMap<Integer, FriendEntity>();

        File file = new File(test ? FILE_TEST_NAME : FILE_NAME);
        if (file.exists() && file.canRead()) {
            return load(file);
        } else {
            persist();
            return true;
        }
    }

    @Override
    public <T extends IEntity> List<T> getAllEntities(Class<T> type)
    {
        List myList;

        if (type == FriendEntity.class) {
            myList = new ArrayList<FriendEntity>(this.friendEntityMap.values());
            return myList;
        }

        if (type == SkillEntity.class) {
            myList = new ArrayList<SkillEntity>(this.skillEntityMap.values());
            return myList;
        }

        if (type == PaymentEntity.class) {
            myList = new ArrayList<PaymentEntity>(this.paymentEntityMap.values());
            return myList;
        }

        return new ArrayList<>();
    }

    @Override
    public FriendEntity getFriend(int id)
    {
        return this.friendEntityMap.get(id);
    }

    @Override
    public <T extends IEntity> T createEntity(IEntity entity, Class<T> type)
    {
        int newId = -1;

        if (entity instanceof FriendEntity) {
            newId = getNextKey(this.friendEntityMap.keySet());
            this.friendEntityMap.put(newId, (FriendEntity) entity);
        }

        if (entity instanceof SkillEntity) {
            newId = getNextKey(this.skillEntityMap.keySet());
            this.skillEntityMap.put(newId, (SkillEntity) entity);
        }

        if (entity instanceof PaymentEntity) {
            newId = getNextKey(this.paymentEntityMap.keySet());
            this.paymentEntityMap.put(newId, (PaymentEntity) entity);
        }

        entity.setId(newId);
        persist();

        return type.cast(entity);
    }

    @Override
    public FriendEntity updateFriend(FriendEntity friend)
    {

        this.friendEntityMap.put(friend.getId(), friend);

        friend.getPayments().forEach(
            paymentEntity -> {
                if (paymentEntity.getId() == 0) {
                    int newId = getNextKey(this.paymentEntityMap.keySet());

                    paymentEntity.setId(newId);
                    this.paymentEntityMap.put(newId, paymentEntity);
                }
            }
        );

        friend.getSkills().forEach(
            skillEntity -> {
                if (skillEntity.getId() == 0) {
                    int newId = getNextKey(this.skillEntityMap.keySet());

                    skillEntity.setId(newId);
                    this.skillEntityMap.put(newId, skillEntity);
                }
            }
        );

        persist();

        return friend;
    }

    @Override
    public void removeEntity(IEntity entity)
    {
        if (entity instanceof FriendEntity) {
            this.friendEntityMap.remove(entity.getId());
        }

        if (entity instanceof SkillEntity) {
            this.skillEntityMap.remove(entity.getId());

            this.friendEntityMap.forEach(
                (integer, friendEntity) -> {
                    friendEntity.getSkills().remove(entity);
                }
            );
        }

        if (entity instanceof PaymentEntity) {
            this.paymentEntityMap.remove(entity.getId());

            this.friendEntityMap.forEach(
                (integer, friendEntity) -> {
                    friendEntity.getPayments().remove(entity);
                }
            );
        }

        persist();
    }

    private int getNextKey(Set<Integer> keySet)
    {
        if (keySet != null) {
            OptionalInt highestKey = keySet.stream().mapToInt(i -> i).max();
            if (highestKey.isPresent()) {
                return highestKey.getAsInt() + 1;
            }
        }
        return 1;
    }

    private boolean load(File file)
    {
        XmlMarshaller marshaller = new XmlMarshaller(this.paymentEntityMap, this.skillEntityMap, this.friendEntityMap);
        try {
            marshaller.load(file);
            marshaller.unmarshal();
            return true;
        } catch (IOException | SAXException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void persist()
    {
        XmlMarshaller marshaller = new XmlMarshaller(this.paymentEntityMap, this.skillEntityMap, this.friendEntityMap);
        try {
            marshaller.marshal();
            marshaller.save(new File(test ? FILE_TEST_NAME : FILE_NAME));
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public void reset()
    {
        this.paymentEntityMap = new TreeMap<Integer, PaymentEntity>();
        this.skillEntityMap = new TreeMap<Integer, SkillEntity>();
        this.friendEntityMap = new TreeMap<Integer, FriendEntity>();

        persist();
    }
}
