package fa35.group1.model;


import fa35.group1.model.xml.XmlMarshaller;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.xml.transform.TransformerException;
import java.util.Map;
import java.util.TreeMap;

@FixMethodOrder(value = MethodSorters.JVM)
public class XmlMarshallerTest {

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

    public String toXmlString() throws TransformerException {
        System.out.println("|- marshal content");
        this.xmlMarshaller.marshal();
        System.out.println("|- write to xml string");
        return this.xmlMarshaller.toXmlString();
    }

    @Test
    public void testEmpty() throws TransformerException {
        System.out.println("testEmpty");
        String result = this.toXmlString().replaceAll(">\\s*<", "><").trim();

        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><persistence name=\"who_gonna_help\">" +
                "<payments/>" +
                "<skills/>" +
                "<friends/>" +
                "</persistence>";
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testPayment() throws TransformerException {
        System.out.println("testPayment");
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setId(1);
        paymentEntity.setName("Payment 1");

        this.paymentEntityMap.put(1, paymentEntity);

        String result = this.toXmlString().replaceAll(">\\s*<", "><").trim();

        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<persistence name=\"who_gonna_help\">" +
                "<payments>" +
                "<payment name=\"Payment 1\" payment_id=\"1\"/>" +
                "</payments>" +
                "<skills/>" +
                "<friends/>" +
                "</persistence>";
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testSkill() throws TransformerException {
        System.out.println("testSkill");
        SkillEntity skillEntity = new SkillEntity();
        skillEntity.setId(1);
        skillEntity.setName("Skill 1");

        this.skillEntityMap.put(1, skillEntity);

        String result = this.toXmlString().replaceAll(">\\s*<", "><").trim();

        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<persistence name=\"who_gonna_help\">" +
                "<payments/>" +
                "<skills>" +
                "<skill name=\"Skill 1\" skill_id=\"1\"/>" +
                "</skills>" +
                "<friends/>" +
                "</persistence>";
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testFriend() throws TransformerException {
        System.out.println("testFriend");
        FriendEntity friendEntity = new FriendEntity();
        friendEntity.setId(1);
        friendEntity.setName("Friend 1");
        friendEntity.setContact("Contact 1");
        friendEntity.setNote("Note 1");

        this.friendEntityMap.put(1, friendEntity);

        String result = this.toXmlString().replaceAll(">\\s*<", "><").trim();

        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<persistence name=\"who_gonna_help\">" +
                "<payments/>" +
                "<skills/>" +
                "<friends>" +
                "<friend contact=\"Contact 1\" friend_id=\"1\" name=\"Friend 1\" note=\"Note 1\">" +
                "<payments/>" +
                "<skills/>" +
                "</friend>" +
                "</friends>" +
                "</persistence>";
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testFriendPaymentSkill() throws TransformerException {
        System.out.println("testFriendPaymentSkill");
        FriendEntity friendEntity = new FriendEntity();
        friendEntity.setId(1);
        friendEntity.setName("Friend 1");
        friendEntity.setContact("Contact 1");
        friendEntity.setNote("Note 1");

        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setId(1);
        paymentEntity.setName("Payment 1");
        this.paymentEntityMap.put(1, paymentEntity);
        friendEntity.getPayments().add(paymentEntity);

        SkillEntity skillEntity = new SkillEntity();
        skillEntity.setId(1);
        skillEntity.setName("Skill 1");
        this.skillEntityMap.put(1, skillEntity);
        friendEntity.getSkills().add(skillEntity);

        this.friendEntityMap.put(1, friendEntity);

        String result = this.toXmlString().replaceAll(">\\s*<", "><").trim();

        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<persistence name=\"who_gonna_help\">" +
                "<payments>" +
                "<payment name=\"Payment 1\" payment_id=\"1\"/>" +
                "</payments>" +
                "<skills>" +
                "<skill name=\"Skill 1\" skill_id=\"1\"/>" +
                "</skills>" +
                "<friends>" +
                "<friend contact=\"Contact 1\" friend_id=\"1\" name=\"Friend 1\" note=\"Note 1\">" +
                "<payments>" +
                "<payment payment_id=\"1\"/>" +
                "</payments>" +
                "<skills>" +
                "<skill skill_id=\"1\"/>" +
                "</skills>" +
                "</friend>" +
                "</friends>" +
                "</persistence>";
        Assert.assertEquals(expected, result);
    }
}
