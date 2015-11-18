package fa35.group2.model.sqlite;

import fa35.group2.model.*;

import java.util.List;

public class SqlitePersistence implements IPersistence, IResetable {

    private HibernateDao dao;

    @Override
    public boolean initializePersistence(boolean test) {
        try {
            this.dao = HibernateDao.getDao(test);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this.dao != null;
    }

    @Override
    public List<FriendEntity> getAllFriends() {
        return this.dao.getResults("friend.getAll", FriendEntity.class);
    }

    @Override
    public FriendEntity getFriend(int id) {
        return this.dao.getResult("friend.get", FriendEntity.class, HibernateDao.entry("id", id));
    }

    @Override
    public FriendEntity createFriend(String name) {
        FriendEntity friendEntity = new FriendEntity();
        friendEntity.setName(name);
        return this.dao.insert(friendEntity);
    }

    @Override
    public FriendEntity updateFriend(FriendEntity friendEntity) {
        return this.dao.update(friendEntity);
    }

    @Override
    public void removeFriend(FriendEntity friendEntity) {
        this.dao.remove(friendEntity);
    }

    @Override
    public List<SkillEntity> getAllSkills() {
        return this.dao.getResults("skill.get", SkillEntity.class);
    }

    @Override
    public SkillEntity createSkill(String name) {
        SkillEntity skillEntity = new SkillEntity();
        skillEntity.setName(name);
        return this.dao.insert(skillEntity);
    }

    @Override
    public void removeSkill(SkillEntity skillEntity) {
        this.dao.remove(skillEntity);
    }

    @Override
    public List<PaymentEntity> getAllPayments() {
        return this.dao.getResults("payment.get", PaymentEntity.class);
    }

    @Override
    public PaymentEntity createPayment(String name) {
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setName(name);
        return this.dao.insert(paymentEntity);
    }

    @Override
    public void removePayment(PaymentEntity paymentEntity) {
        this.dao.remove(paymentEntity);
    }

    @Override
    public void reset() {
        HibernateDao.reset();
    }
}
