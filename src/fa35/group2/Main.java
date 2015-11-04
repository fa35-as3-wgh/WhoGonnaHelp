package fa35.group2;

import fa35.group2.model.IPersistence;
import fa35.group2.model.xml.XmlPersistence;

public class Main {

    //    private static final IPersistence persistence = new SqlitePersistence();
    private static final IPersistence persistence = new XmlPersistence();

    public static void main(String[] args) {

    }

    public static IPersistence getPersistence() {
        return persistence;
    }
}
