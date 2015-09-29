package fa35.group1.model;

import fa35.group2.model.xml.XmlMarshaller;
import fa35.group2.model.FriendEntity;
import fa35.group2.model.PaymentEntity;
import fa35.group2.model.SkillEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

@FixMethodOrder(value = MethodSorters.JVM)
public class XmlUnMarshallerTest {

    private Map<Integer, PaymentEntity> paymentEntityMap;
    private Map<Integer, SkillEntity> skillEntityMap;
    private Map<Integer, FriendEntity> friendEntityMap;

    private XmlMarshaller xmlMarshaller;

    @Before
    public void setup() {

        this.paymentEntityMap = new TreeMap<Integer, PaymentEntity>();
        this.skillEntityMap = new TreeMap<Integer, SkillEntity>();
        this.friendEntityMap = new TreeMap<Integer, FriendEntity>();

        this.xmlMarshaller = new XmlMarshaller(this.paymentEntityMap, this.skillEntityMap, this.friendEntityMap);
    }

    public void loadFile(String file) throws IOException, SAXException {
        System.out.println("|- load file");
        this.xmlMarshaller.load(new File("testResources/xml/" + file));

        System.out.println("|- unmarshall content");
        this.xmlMarshaller.unmarshall();
    }

    @Test
    public void testLoadEmptyFile() {
        System.out.println("testLoadEmptyFile");
        Exception exception = null;

        try {
            this.loadFile("emptyFile.xml");
        } catch (Exception e) {
            exception = e;
        }

        Assert.assertNotNull(exception);
        exception.printStackTrace();
        Assert.assertTrue(this.paymentEntityMap.isEmpty());
        Assert.assertTrue(this.skillEntityMap.isEmpty());
        Assert.assertTrue(this.friendEntityMap.isEmpty());
    }

    @Test
    public void testLoadNoXmlDeclaration() {
        System.out.println("testLoadNoXmlDeclaration");
        Exception exception = null;

        try {
            this.loadFile("noXmlDeclaration.xml");
        } catch (Exception e) {
            exception = e;
        }

        Assert.assertNull(exception);
        Assert.assertFalse(this.paymentEntityMap.isEmpty());
        Assert.assertFalse(this.skillEntityMap.isEmpty());
        Assert.assertFalse(this.friendEntityMap.isEmpty());

        // check payments
        Assert.assertTrue(this.paymentEntityMap.containsKey(1));

        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setId(1);
        paymentEntity.setName("Payment 1");
        Assert.assertEquals(paymentEntity, this.paymentEntityMap.get(1));

        // check skills
        Assert.assertTrue(this.skillEntityMap.containsKey(1));

        SkillEntity skillEntity = new SkillEntity();
        skillEntity.setId(1);
        skillEntity.setName("Skill 1");
        Assert.assertEquals(skillEntity, this.skillEntityMap.get(1));

        // check friends
        Assert.assertTrue(this.friendEntityMap.containsKey(1));

        FriendEntity friendEntity = new FriendEntity();
        friendEntity.setId(1);
        friendEntity.setName("Friend 1");
        friendEntity.setContact("Contact 1");
        friendEntity.setNote("Note 1");
        friendEntity.getPayments().add(paymentEntity);
        friendEntity.getSkills().add(skillEntity);
        Assert.assertEquals(friendEntity, this.friendEntityMap.get(1));
    }

    @Test
    public void testLoadWrongXmlDeclaration() {
        System.out.println("testLoadWrongXmlDeclaration");
        Exception exception = null;

        try {
            this.loadFile("wrongXmlDeclaration.xml");
        } catch (Exception e) {
            exception = e;
        }

        Assert.assertNull(exception);
        Assert.assertFalse(this.paymentEntityMap.isEmpty());
        Assert.assertFalse(this.skillEntityMap.isEmpty());
        Assert.assertFalse(this.friendEntityMap.isEmpty());

        //TODO
    }

    @Test
    public void testLoadNoTree() {
        System.out.println("testLoadNoTree");
        Exception exception = null;

        try {
            this.loadFile("noTree.xml");
        } catch (Exception e) {
            exception = e;
        }

        Assert.assertNotNull(exception);
        exception.printStackTrace();
        Assert.assertTrue(this.paymentEntityMap.isEmpty());
        Assert.assertTrue(this.skillEntityMap.isEmpty());
        Assert.assertTrue(this.friendEntityMap.isEmpty());
    }

    @Test
    public void testLoadWrongPersistenceName() {
        System.out.println("testLoadWrongPersistenceName");
        Exception exception = null;

        try {
            this.loadFile("wrongPersistenceName.xml");
        } catch (Exception e) {
            exception = e;
        }

        Assert.assertNull(exception);
        Assert.assertTrue(this.paymentEntityMap.isEmpty());
        Assert.assertTrue(this.skillEntityMap.isEmpty());
        Assert.assertTrue(this.friendEntityMap.isEmpty());
    }

    @Test
    public void testLoadFriendsOnly() {
        System.out.println("testLoadFriendsOnly");
        Exception exception = null;

        try {
            this.loadFile("friendsOnly.xml");
        } catch (Exception e) {
            exception = e;
        }

        Assert.assertNull(exception);
        Assert.assertTrue(this.paymentEntityMap.isEmpty());
        Assert.assertTrue(this.skillEntityMap.isEmpty());
        Assert.assertFalse(this.friendEntityMap.isEmpty());

        //TODO
    }

    @Test
    public void testLoadTreeEmpty() {
        System.out.println("testLoadTreeEmpty");
        Exception exception = null;

        try {
            this.loadFile("treeEmpty.xml");
        } catch (Exception e) {
            exception = e;
        }

        Assert.assertNull(exception);
        Assert.assertTrue(this.paymentEntityMap.isEmpty());
        Assert.assertTrue(this.skillEntityMap.isEmpty());
        Assert.assertTrue(this.friendEntityMap.isEmpty());
    }

    @Test
    public void testLoadWrongCategoryOrder() {
        System.out.println("testLoadWrongCategoryOrder");
        Exception exception = null;

        try {
            this.loadFile("wrongCategoryOrder.xml");
        } catch (Exception e) {
            exception = e;
        }

        Assert.assertNotNull(exception);
        exception.printStackTrace();
        Assert.assertTrue(this.paymentEntityMap.isEmpty());
        Assert.assertTrue(this.skillEntityMap.isEmpty());
        Assert.assertTrue(this.friendEntityMap.isEmpty());
    }

    @Test
    public void testLoadMultipleCategories() {
        System.out.println("testLoadMultipleCategories");
        Exception exception = null;

        try {
            this.loadFile("multipleCategories.xml");
        } catch (Exception e) {
            exception = e;
        }

        Assert.assertNull(exception);
        Assert.assertFalse(this.paymentEntityMap.isEmpty());
        Assert.assertFalse(this.skillEntityMap.isEmpty());
        Assert.assertFalse(this.friendEntityMap.isEmpty());

        //TODO
    }

    @Test
    public void testLoadNoEntityIds() {
        System.out.println("testLoadNoEntityIds");
        Exception exception = null;

        try {
            this.loadFile("noEntityIds.xml");
        } catch (Exception e) {
            exception = e;
        }

        Assert.assertNotNull(exception);
        exception.printStackTrace();
        Assert.assertTrue(this.paymentEntityMap.isEmpty());
        Assert.assertTrue(this.skillEntityMap.isEmpty());
        Assert.assertTrue(this.friendEntityMap.isEmpty());
    }

    @Test
    public void testLoadEntityIdOrder() {
        System.out.println("testLoadEntityIdOrder");
        Exception exception = null;

        try {
            this.loadFile("entityIdOrder.xml");
        } catch (Exception e) {
            exception = e;
        }

        Assert.assertNull(exception);
        Assert.assertFalse(this.paymentEntityMap.isEmpty());
        Assert.assertFalse(this.skillEntityMap.isEmpty());
        Assert.assertFalse(this.friendEntityMap.isEmpty());

        //TODO
    }

    @Test
    public void testLoadBrokenReferences() {
        System.out.println("testLoadBrokenReferences");
        Exception exception = null;

        try {
            this.loadFile("brokenReferences.xml");
        } catch (Exception e) {
            exception = e;
        }

        Assert.assertNull(exception);
        Assert.assertFalse(this.paymentEntityMap.isEmpty());
        Assert.assertFalse(this.skillEntityMap.isEmpty());
        Assert.assertFalse(this.friendEntityMap.isEmpty());

        // check payments
        Assert.assertTrue(this.paymentEntityMap.containsKey(1));

        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setId(1);
        paymentEntity.setName("Payment 1");
        Assert.assertEquals(paymentEntity, this.paymentEntityMap.get(1));

        // check skills
        Assert.assertTrue(this.skillEntityMap.containsKey(1));

        SkillEntity skillEntity = new SkillEntity();
        skillEntity.setId(1);
        skillEntity.setName("Skill 1");
        Assert.assertEquals(skillEntity, this.skillEntityMap.get(1));

        // check friends
        Assert.assertTrue(this.friendEntityMap.containsKey(1));

        FriendEntity friendEntity = new FriendEntity();
        friendEntity.setId(1);
        friendEntity.setName("Friend 1");
        friendEntity.setContact("Contact 1");
        friendEntity.setNote("Note 1");
        Assert.assertTrue(this.friendEntityMap.get(1).getPayments().isEmpty());
        Assert.assertTrue(this.friendEntityMap.get(1).getSkills().isEmpty());
        Assert.assertEquals(friendEntity, this.friendEntityMap.get(1));
    }

    @Test
    public void testLoadCorrectReferences() {
        System.out.println("testLoadCorrectReferences");
        Exception exception = null;

        try {
            this.loadFile("correctReferences.xml");
        } catch (Exception e) {
            exception = e;
        }

        Assert.assertNull(exception);
        Assert.assertFalse(this.paymentEntityMap.isEmpty());
        Assert.assertFalse(this.skillEntityMap.isEmpty());
        Assert.assertFalse(this.friendEntityMap.isEmpty());

        // check payments
        Assert.assertTrue(this.paymentEntityMap.containsKey(1));

        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setId(1);
        paymentEntity.setName("Payment 1");
        Assert.assertEquals(paymentEntity, this.paymentEntityMap.get(1));

        // check skills
        Assert.assertTrue(this.skillEntityMap.containsKey(1));

        SkillEntity skillEntity = new SkillEntity();
        skillEntity.setId(1);
        skillEntity.setName("Skill 1");
        Assert.assertEquals(skillEntity, this.skillEntityMap.get(1));

        // check friends
        Assert.assertTrue(this.friendEntityMap.containsKey(1));

        FriendEntity friendEntity = new FriendEntity();
        friendEntity.setId(1);
        friendEntity.setName("Friend 1");
        friendEntity.setContact("Contact 1");
        friendEntity.setNote("Note 1");
        friendEntity.getPayments().add(paymentEntity);
        friendEntity.getSkills().add(skillEntity);
        Assert.assertEquals(friendEntity, this.friendEntityMap.get(1));
    }
}
