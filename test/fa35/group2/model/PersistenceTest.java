package fa35.group2.model;

import fa35.group2.Main;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.List;

@FixMethodOrder(value = MethodSorters.JVM)
public class PersistenceTest {

    private IPersistence persistence;

    private List<FriendEntity> friends;
    private List<PaymentEntity> payments;
    private List<SkillEntity> skills;

    public void createDatabase() {
        System.out.println("|- createDatabase");
        persistence = Main.getPersistence();

        // check if persistence is created successfully
        Assert.assertTrue(persistence.initializePersistence(true));
    }

    public void createPayments() {
        System.out.println("|- createPayments");
        PaymentEntity entity = new PaymentEntity();
        entity.setName("TestPayment1");
        entity = persistence.createPayment(entity);
        payments.add(entity);

        entity = new PaymentEntity();
        entity.setName("TestPayment2");
        entity = persistence.createPayment(entity);
        payments.add(entity);
    }

    public void createSkills() {
        System.out.println("|- createSkills");

        SkillEntity entity = new SkillEntity();
        entity.setName("TestSkill1");
        entity = persistence.createSkill(entity);
        skills.add(entity);

        entity = new SkillEntity();
        entity.setName("TestSkill2");
        entity = persistence.createSkill(entity);
        skills.add(entity);
    }

    @Before
    public void createFriends() {
        createDatabase();

        friends = new ArrayList<FriendEntity>();
        payments = new ArrayList<PaymentEntity>();
        skills = new ArrayList<SkillEntity>();

        createSkills();
        createPayments();

        System.out.println("|- createFriends");

        FriendEntity entity = new FriendEntity();
        entity.setName("TestFriend1");
        entity.setContact("TestContact1");
        entity.setNote("TestNote1");
        entity.getPayments().addAll(payments);
        entity.getSkills().addAll(skills);
        entity = persistence.createFriend(entity);
        friends.add(entity);

        entity = new FriendEntity();
        entity.setName("TestFriend2");
        entity.setContact("TestContact2");
        entity.setNote("TestNote2");
        entity.getPayments().addAll(payments);
        entity.getSkills().addAll(skills);
        entity = persistence.createFriend(entity);
        friends.add(entity);
    }

    @After
    public void reset() {
        if (persistence instanceof IResetable) {

            ((IResetable) persistence).reset();
        }
    }

    @Test
    public void testPaymentsId() {
        System.out.println("-> testPaymentsId");
        // check if entities have id over 0
        payments.forEach(entity -> Assert.assertTrue(entity.getId() > 0));
    }

    @Test
    public void testSkillsId() {
        System.out.println("-> testSkillsId");
        // check if entities have id over 0
        skills.forEach(entity -> Assert.assertTrue(entity.getId() > 0));
    }

    @Test
    public void testFriendsId() {
        System.out.println("-> testFriendsId");
        // check if entities have id over 0
        friends.forEach(entity -> Assert.assertTrue(entity.getId() > 0));
    }

    @Test
    public void testPaymentGetAll() {
        System.out.println("-> testPaymentGetAll");
        // get all payment entities
        List<PaymentEntity> paymentEntities = persistence.getAllPayments();

        // check if query result is not null
        Assert.assertNotNull(paymentEntities);
        // check if query result is not empty
        Assert.assertFalse(paymentEntities.isEmpty());
        // check if query result equals inserted
        Assert.assertEquals(payments, paymentEntities);
    }

    @Test
    public void testSkillGetAll() {
        System.out.println("-> testSkillGetAll");
        // get all skill entities
        List<SkillEntity> skillEntities = persistence.getAllSkills();

        // check if query result is not null
        Assert.assertNotNull(skillEntities);
        // check if query result is not empty
        Assert.assertFalse(skillEntities.isEmpty());
        // check if query result equals inserted
        Assert.assertEquals(skills, skillEntities);
    }

    @Test
    public void testFriendGetAll() {
        System.out.println("-> testFriendGetAll");
        // get all friend entities
        List<FriendEntity> friendEntities = persistence.getAllFriends();

        // check if query result is not null
        Assert.assertNotNull(friendEntities);
        // check if query result is not empty
        Assert.assertFalse(friendEntities.isEmpty());
        // check if query result equals inserted
        Assert.assertEquals(friends, friendEntities);
    }

    @Test
    public void testFriendGet() {
        System.out.println("-> testFriendGet");
        // should be friend with id == 1
        FriendEntity expectedFriend = friends.get(0);

        // get friend with id == 1 from persistence
        FriendEntity friendEntity = persistence.getFriend(1);

        // check if result equals expected
        Assert.assertEquals(expectedFriend, friendEntity);
    }

    @Test
    public void testFriendUpdateFields() {
        System.out.println("-> testFriendUpdateFields");
        // should be friend with id == 1
        FriendEntity expectedFriend = friends.get(0);

        // update fields of friend
        expectedFriend.setName("UpdatedFriend1");
        expectedFriend.setContact("UpdatedContact1");
        expectedFriend.setNote("UpdatedNote1");
        persistence.updateFriend(expectedFriend);

        // get friend with id == 1 from persistence
        FriendEntity friendEntity = persistence.getFriend(1);

        // check if result equals expected
        Assert.assertEquals(expectedFriend, friendEntity);
    }

    @Test
    public void testFriendUpdateInsertedRelations() {
        System.out.println("-> testFriendUpdateInsertedRelations");
        // should be friend with id == 1
        FriendEntity expectedFriend = friends.get(0);

        // new payment
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setName("InsertedSkillWithFriend1");

        // new skill
        SkillEntity skillEntity = new SkillEntity();
        skillEntity.setName("InsertedSkillWithFriend1");

        // update relational fields of friend
        expectedFriend.getPayments().add(paymentEntity);
        expectedFriend.getSkills().add(skillEntity);
        expectedFriend = persistence.updateFriend(expectedFriend);

        // insert payment and skill to internal
        payments.add(expectedFriend.getPayments().get(2));
        skills.add(expectedFriend.getSkills().get(2));

        // get friend with id == 1 from persistence
        FriendEntity friendEntity = persistence.getFriend(1);

        // check if result equals expected
        Assert.assertEquals(expectedFriend, friendEntity);

        // check if payment added
        List<PaymentEntity> paymentEntities = persistence.getAllPayments();
        Assert.assertEquals(payments, paymentEntities);

        // check if skill added
        List<SkillEntity> skillEntities = persistence.getAllSkills();
        Assert.assertEquals(skills, skillEntities);
    }

    @Test
    public void testFriendUpdateRemovedRelations() {
        System.out.println("-> testFriendUpdateRemovedRelations");
        // should be friend with id == 1
        FriendEntity expectedFriend = friends.get(0);

        PaymentEntity paymentEntity = expectedFriend.getPayments().get(1);
        SkillEntity skillEntity = expectedFriend.getSkills().get(1);

        // update relational fields of friend
        expectedFriend.getPayments().remove(paymentEntity);
        expectedFriend.getSkills().remove(skillEntity);
        payments.remove(paymentEntity);
        skills.remove(skillEntity);
        expectedFriend = persistence.updateFriend(expectedFriend);

        // get friend with id == 1 from persistence
        FriendEntity friendEntity = persistence.getFriend(1);

        // check if result equals expected
        Assert.assertEquals(expectedFriend, friendEntity);
    }

    @Test
    public void testPaymentRemove() {
        System.out.println("-> testPaymentRemove");
        // get first payment
        PaymentEntity paymentEntity = payments.get(0);

        // remove payment from internal payments and friend's payments
        payments.remove(paymentEntity);
        friends.forEach(friendEntity -> friendEntity.getPayments().remove(paymentEntity));

        // check if internal payments not contains removed payment
        Assert.assertFalse(payments.contains(paymentEntity));

        // check if internal friend's payments not contains removed payment
        friends.forEach(friendEntity -> Assert.assertFalse(friendEntity.getPayments().contains(paymentEntity)));

        // remove payment
        persistence.removePayment(paymentEntity);

        // check if payments not contains removed payment
        List<PaymentEntity> paymentEntities = persistence.getAllPayments();
        Assert.assertNotNull(paymentEntities);
        Assert.assertFalse(paymentEntities.contains(paymentEntity));

        // check if payments equals expected
        Assert.assertEquals(payments, paymentEntities);

        // check if friend's payments not contains removed payment
        List<FriendEntity> friendEntities = persistence.getAllFriends();
        Assert.assertNotNull(friendEntities);
        friendEntities.forEach(friendEntity -> Assert.assertFalse(friendEntity.getPayments().contains(paymentEntity)));

        // check if friends equals expected
        Assert.assertEquals(friends, friendEntities);
    }

    @Test
    public void testSkillRemove() {
        System.out.println("-> testSkillRemove");
        // get first skill
        SkillEntity skillEntity = skills.get(0);

        // remove skill from internal skills and friend's skills
        skills.remove(skillEntity);
        friends.forEach(friendEntity -> friendEntity.getSkills().remove(skillEntity));

        // check if internal skills not contains removed skill
        Assert.assertFalse(skills.contains(skillEntity));

        // check if internal friend's skills not contains removed skills
        friends.forEach(friendEntity -> Assert.assertFalse(friendEntity.getSkills().contains(skillEntity)));

        // remove skill
        persistence.removeSkill(skillEntity);

        // check if skills not contains removed skill
        List<SkillEntity> skillEntities = persistence.getAllSkills();
        Assert.assertNotNull(skillEntities);
        Assert.assertFalse(skillEntities.contains(skillEntity));

        // check if skills equals expected
        Assert.assertEquals(skills, skillEntities);

        // check if friend's skills not contains removed skill
        List<FriendEntity> friendEntities = persistence.getAllFriends();
        Assert.assertNotNull(friendEntities);
        friendEntities.forEach(friendEntity -> Assert.assertFalse(friendEntity.getSkills().contains(skillEntity)));

        // check if friends equals expected
        Assert.assertEquals(friends, friendEntities);
    }

    @Test
    public void testFriendRemove() {
        System.out.println("-> testFriendRemove");
        // get first friend
        FriendEntity friendEntity = friends.get(0);

        // remove friend from internal friends
        friends.remove(friendEntity);

        // check if friend is removed from internal friends
        Assert.assertFalse(friends.contains(friendEntity));

        // remove friend
        persistence.removeFriend(friendEntity);

        // check if friends is removed from friends
        List<FriendEntity> friendEntities = persistence.getAllFriends();
        Assert.assertFalse(friendEntities.contains(friendEntity));

        // check if friends equals expected
        Assert.assertEquals(friends, friendEntities);

        // check if friend is removed (by id)
        System.out.println("WARNING: This may print an exception!");
        FriendEntity actualFriend = persistence.getFriend(friendEntity.getId());
        Assert.assertNull(actualFriend);
    }
}
