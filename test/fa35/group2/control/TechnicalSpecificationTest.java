package fa35.group2.control;

import fa35.group2.control.technicalspecification1.TechnicalSpecification1;
import fa35.group2.model.IPersistence;
import fa35.group2.model.entities.FriendEntity;
import fa35.group2.model.entities.PaymentEntity;
import fa35.group2.model.entities.SkillEntity;
import org.easymock.EasyMockSupport;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.easymock.EasyMock.*;

public class TechnicalSpecificationTest extends EasyMockSupport
{
    private TechnicalSpecification1 technicalSpecification1;

    private IPersistence persistenceMock;

    private List<FriendEntity> friends;

    private List<PaymentEntity> payments;

    private List<SkillEntity> skills;

    private List<FriendEntity> emptyFriendList;

    private List<SkillEntity> emptySkillList;

    private List<PaymentEntity> emptyPaymentList;

    private SkillEntity skillEntity;

    private SkillEntity skillEntity2;

    private PaymentEntity paymentEntity;

    private PaymentEntity paymentEntity2;

    private FriendEntity friendEntity;

    private FriendEntity friendEntity2;

    @Before
    public void setUp()
    {
        this.persistenceMock = mock(IPersistence.class);
        this.technicalSpecification1 = new TechnicalSpecification1(persistenceMock);
    }

    public void createPayments()
    {
        this.paymentEntity = new PaymentEntity();
        this.paymentEntity.setName("TestPayment1");
        this.paymentEntity.setId(1);
        this.payments.add(paymentEntity);

        this.paymentEntity2 = new PaymentEntity();
        this.paymentEntity2.setName("TestPayment2");
        this.paymentEntity2.setId(2);
        this.payments.add(paymentEntity2);
    }

    public void createSkills()
    {
        this.skillEntity = new SkillEntity();
        this.skillEntity.setName("TestSkill1");
        this.skillEntity.setId(1);
        this.skills.add(skillEntity);

        this.skillEntity2 = new SkillEntity();
        this.skillEntity2.setName("TestSkill2");
        this.skillEntity2.setId(2);
        this.skills.add(skillEntity2);
    }

    @Before
    public void createFriends()
    {
        friends = new ArrayList<FriendEntity>();
        payments = new ArrayList<PaymentEntity>();
        skills = new ArrayList<SkillEntity>();

        createSkills();
        createPayments();

        this.friendEntity = new FriendEntity();
        this.friendEntity.setName("TestFriend1");
        this.friendEntity.setContact("TestContact1");
        this.friendEntity.setNote("TestNote1");
        this.friendEntity.getPayments().addAll(payments);
        this.friendEntity.getSkills().addAll(skills);
        this.friendEntity.setId(1);
        this.friends.add(friendEntity);

        this.friendEntity2 = new FriendEntity();
        this.friendEntity2.setName("TestFriend2");
        this.friendEntity2.setContact("TestContact2");
        this.friendEntity2.setNote("TestNote2");
        this.friendEntity2.getPayments().addAll(payments);
        this.friendEntity2.getSkills().addAll(skills);
        this.friendEntity2.setId(2);
        this.friends.add(friendEntity2);

        this.emptyPaymentList = new ArrayList<>();
        this.emptySkillList = new ArrayList<>();
        this.emptyFriendList = new ArrayList<>();
    }

    @Test
    public void test_of_method_getAllFriends()
    {
        List<FriendEntity> expected = new ArrayList<>();
        expected.add(this.friendEntity);
        expected.add(this.friendEntity2);

        expect(persistenceMock.getAllEntities(FriendEntity.class)).andReturn(this.friends);
        replay(persistenceMock);

        List<FriendEntity> actual = technicalSpecification1.getAllFriends();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test_of_method_getAllFriends_with_empty_friendlist()
    {
        List<FriendEntity> expected = new ArrayList<>();
        expect(persistenceMock.getAllEntities(FriendEntity.class)).andReturn(this.emptyFriendList);
        replay(persistenceMock);

        List<FriendEntity> actual = technicalSpecification1.getAllFriends();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test_of_method_getAllFriendsIdAndName()
    {
        Map<Integer, String> expected = new HashMap<>();
        expected.put(1, "TestFriend1");
        expected.put(2, "TestFriend2");

        expect(persistenceMock.getAllEntities(FriendEntity.class)).andReturn(this.friends);
        replay(persistenceMock);

        Map<Integer, String> actual = technicalSpecification1.getAllFriendsIdAndName();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test_of_method_getAllFriendsIdAndName_with_empty_friendlist()
    {
        Map<Integer, String> expected = new HashMap<>();

        expect(persistenceMock.getAllEntities(FriendEntity.class)).andReturn(this.emptyFriendList);
        replay(persistenceMock);

        Map<Integer, String> actual = technicalSpecification1.getAllFriendsIdAndName();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test_of_method_getAllFriendsBySkill_with_existing_skilId()
    {
        List<FriendEntity> expected = new ArrayList<>();
        expected.add(this.friendEntity);
        expected.add(this.friendEntity2);

        expect(persistenceMock.getAllEntities(FriendEntity.class)).andReturn(this.friends);
        replay(persistenceMock);

        List<FriendEntity> actual = technicalSpecification1.getAllFriendsBySkill(1);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test_of_method_getAllFriendsBySkill_with_non_existing_skillId()
    {
        List<FriendEntity> expected = new ArrayList<>();

        expect(persistenceMock.getAllEntities(FriendEntity.class)).andReturn(this.friends);
        replay(persistenceMock);

        List<FriendEntity> actual = technicalSpecification1.getAllFriendsBySkill(3);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test_of_method_getAllFriendsBySkill_with_empty_friendlist()
    {
        List<FriendEntity> expected = new ArrayList<>();

        expect(persistenceMock.getAllEntities(FriendEntity.class)).andReturn(this.emptyFriendList);
        replay(persistenceMock);

        List<FriendEntity> actual = technicalSpecification1.getAllFriendsBySkill(2);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test_of_method_getFriendById_with_existing_id()
    {
        FriendEntity expected = this.friendEntity;
        expect(persistenceMock.getFriend(1)).andReturn(this.friendEntity);
        replay(persistenceMock);

        FriendEntity actual = technicalSpecification1.getFriendById(1);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test_of_method_getFriendById_with_non_existing_id()
    {
        FriendEntity expected = null;
        expect(persistenceMock.getFriend(3)).andReturn(null);
        replay(persistenceMock);

        FriendEntity actual = technicalSpecification1.getFriendById(3);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test_of_method_getFriendByName_with_existing_name()
    {
        FriendEntity expected = this.friendEntity;
        expect(persistenceMock.getAllEntities(FriendEntity.class)).andReturn(this.friends);
        replay(persistenceMock);

        FriendEntity actual = technicalSpecification1.getFriendByName("TestFriend1");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test_of_method_getFriendByName_with_non_existing_name()
    {
        FriendEntity expected = null;
        expect(persistenceMock.getAllEntities(FriendEntity.class)).andReturn(this.friends);
        replay(persistenceMock);

        FriendEntity actual = technicalSpecification1.getFriendByName("TestFriend3");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test_of_method_getFriendByName_with_empty_friendlist()
    {
        FriendEntity expected = null;

        expect(persistenceMock.getAllEntities(FriendEntity.class)).andReturn(this.emptyFriendList);
        replay(persistenceMock);

        FriendEntity actual = technicalSpecification1.getFriendByName("TestFriend1");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test_of_method_addSkillToFriend_with_existing_skillId()
    {
        List<SkillEntity> expected = new ArrayList<>();
        expected.add(skillEntity);
        expected.add(skillEntity2);

        expect(persistenceMock.getAllEntities(SkillEntity.class)).andReturn(this.skills);
        expect(persistenceMock.updateFriend(this.friendEntity)).andReturn(this.friendEntity);
        replay(persistenceMock);

        FriendEntity actual = technicalSpecification1.addSkillToFriend(this.friendEntity, 1);
        Assert.assertEquals(expected, actual.getSkills());
    }

    @Test
    public void test_of_method_addSkillToFriend_with_non_existing_skillId()
    {
        List<SkillEntity> expected = new ArrayList<>();
        expected.add(skillEntity);
        expected.add(skillEntity2);

        expect(persistenceMock.getAllEntities(SkillEntity.class)).andReturn(this.skills);
        expect(persistenceMock.updateFriend(this.friendEntity)).andReturn(this.friendEntity);
        replay(persistenceMock);

        FriendEntity actual = technicalSpecification1.addSkillToFriend(this.friendEntity, 3);
        Assert.assertEquals(expected, actual.getSkills());
    }

    @Test
    public void test_of_method_addSkillToFriend_with_empty_skilllist()
    {
        List<SkillEntity> expected = new ArrayList<>();
        expected.add(skillEntity);
        expected.add(skillEntity2);

        expect(persistenceMock.getAllEntities(SkillEntity.class)).andReturn(this.emptySkillList);
        expect(persistenceMock.updateFriend(this.friendEntity)).andReturn(this.friendEntity);
        replay(persistenceMock);

        FriendEntity actual = technicalSpecification1.addSkillToFriend(this.friendEntity, 1);
        Assert.assertEquals(expected, actual.getSkills());
    }

    @Test
    public void test_of_method_removeSkillFromFriend_with_existing_skillId()
    {
        List<SkillEntity> expected = new ArrayList<>();
        expected.add(skillEntity2);

        expect(persistenceMock.updateFriend(this.friendEntity)).andReturn(this.friendEntity);
        replay(persistenceMock);

        FriendEntity actual = technicalSpecification1.removeSkillFromFriend(this.friendEntity, 1);
        Assert.assertEquals(expected, actual.getSkills());
    }

    @Test
    public void test_of_method_removeSkillFromFriend_with_non_existing_skillId()
    {
        List<SkillEntity> expected = new ArrayList<>();
        expected.add(skillEntity);
        expected.add(skillEntity2);

        expect(persistenceMock.updateFriend(this.friendEntity)).andReturn(this.friendEntity);
        replay(persistenceMock);

        FriendEntity actual = technicalSpecification1.removeSkillFromFriend(this.friendEntity, 3);
        Assert.assertEquals(expected, actual.getSkills());
    }

    @Test
    public void test_of_method_removeSkillFromFriend_with_empty_skilllist()
    {
        List<SkillEntity> expected = new ArrayList<>();
        this.friendEntity.getSkills().removeAll(this.skills);
        this.friendEntity.getSkills().addAll(new ArrayList<>());

        expect(persistenceMock.updateFriend(this.friendEntity)).andReturn(this.friendEntity);
        replay(persistenceMock);

        FriendEntity actual = technicalSpecification1.removeSkillFromFriend(this.friendEntity, 1);
        Assert.assertEquals(expected, actual.getSkills());
    }

    @Test
    public void test_of_method_addPaymentToFriend_with_existing_paymentId()
    {
        List<PaymentEntity> expected = new ArrayList<>();
        expected.add(paymentEntity);
        expected.add(paymentEntity2);

        expect(persistenceMock.getAllEntities(PaymentEntity.class)).andReturn(this.payments);
        expect(persistenceMock.updateFriend(this.friendEntity)).andReturn(this.friendEntity);
        replay(persistenceMock);

        FriendEntity actual = technicalSpecification1.addPaymentToFriend(this.friendEntity, 1);
        Assert.assertEquals(expected, actual.getPayments());
    }

    @Test
    public void test_of_method_addPaymentToFriend_with_non_existing_paymentId()
    {
        List<PaymentEntity> expected = new ArrayList<>();
        expected.add(paymentEntity);
        expected.add(paymentEntity2);

        expect(persistenceMock.getAllEntities(PaymentEntity.class)).andReturn(this.payments);
        expect(persistenceMock.updateFriend(this.friendEntity)).andReturn(this.friendEntity);
        replay(persistenceMock);

        FriendEntity actual = technicalSpecification1.addPaymentToFriend(this.friendEntity, 3);
        Assert.assertEquals(expected, actual.getPayments());
    }

    @Test
    public void test_of_method_addPaymentToFriend_with_empty_paymentlist()
    {
        List<PaymentEntity> expected = new ArrayList<>();
        expected.add(paymentEntity);
        expected.add(paymentEntity2);

        expect(persistenceMock.getAllEntities(PaymentEntity.class)).andReturn(this.emptyPaymentList);
        expect(persistenceMock.updateFriend(this.friendEntity)).andReturn(this.friendEntity);
        replay(persistenceMock);

        FriendEntity actual = technicalSpecification1.addPaymentToFriend(this.friendEntity, 1);
        Assert.assertEquals(expected, actual.getPayments());
    }

    @Test
    public void test_of_method_removePaymentFromFriend_with_existing_paymentId()
    {
        List<PaymentEntity> expected = new ArrayList<>();
        expected.add(paymentEntity2);

        expect(persistenceMock.updateFriend(this.friendEntity)).andReturn(this.friendEntity);
        replay(persistenceMock);

        FriendEntity actual = technicalSpecification1.removePaymentFromFriend(this.friendEntity, 1);
        Assert.assertEquals(expected, actual.getPayments());
    }

    @Test
    public void test_of_method_removePaymentFromFriend_with_non_existing_paymentId()
    {
        List<PaymentEntity> expected = new ArrayList<>();
        expected.add(paymentEntity);
        expected.add(paymentEntity2);

        expect(persistenceMock.updateFriend(this.friendEntity)).andReturn(this.friendEntity);
        replay(persistenceMock);

        FriendEntity actual = technicalSpecification1.removePaymentFromFriend(this.friendEntity, 3);
        Assert.assertEquals(expected, actual.getPayments());
    }

    @Test
    public void test_of_method_removePaymentFromFriend_with_empty_paymentlist()
    {
        List<PaymentEntity> expected = new ArrayList<>();
        this.friendEntity.getPayments().removeAll(this.payments);
        this.friendEntity.getPayments().addAll(new ArrayList<>());

        expect(persistenceMock.updateFriend(this.friendEntity)).andReturn(this.friendEntity);
        replay(persistenceMock);

        FriendEntity actual = technicalSpecification1.removePaymentFromFriend(this.friendEntity, 1);
        Assert.assertEquals(expected, actual.getPayments());
    }

    @Test
    public void test_of_method_getAllSkills()
    {
        List<SkillEntity> expected = new ArrayList<>();
        expected.add(this.skillEntity);
        expected.add(this.skillEntity2);

        expect(persistenceMock.getAllEntities(SkillEntity.class)).andReturn(this.skills);
        replay(persistenceMock);

        List<SkillEntity> actual = technicalSpecification1.getAllSkills();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test_of_method_getAllSkills_with_empty_skilllist()
    {
        List<SkillEntity> expected = new ArrayList<>();

        expect(persistenceMock.getAllEntities(SkillEntity.class)).andReturn(this.emptySkillList);
        replay(persistenceMock);

        List<SkillEntity> actual = technicalSpecification1.getAllSkills();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test_of_method_getAllPayments()
    {
        List<PaymentEntity> expected = new ArrayList<>();
        expected.add(this.paymentEntity);
        expected.add(this.paymentEntity2);

        expect(persistenceMock.getAllEntities(PaymentEntity.class)).andReturn(this.payments);
        replay(persistenceMock);

        List<PaymentEntity> actual = technicalSpecification1.getAllPayments();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test_of_method_getAllPayments_with_empty_paymentlist()
    {
        List<PaymentEntity> expected = new ArrayList<>();

        expect(persistenceMock.getAllEntities(PaymentEntity.class)).andReturn(this.emptyPaymentList);
        replay(persistenceMock);

        List<PaymentEntity> actual = technicalSpecification1.getAllPayments();
        Assert.assertEquals(expected, actual);
    }
}
