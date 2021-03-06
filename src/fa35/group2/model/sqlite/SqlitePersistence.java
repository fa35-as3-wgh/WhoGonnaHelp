package fa35.group2.model.sqlite;

import fa35.group2.model.*;
import fa35.group2.model.entities.FriendEntity;
import fa35.group2.model.entities.PaymentEntity;
import fa35.group2.model.entities.SkillEntity;

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
    public FriendEntity createFriend(FriendEntity friend) {
        return this.dao.insert(friend);
    }

    @Override
    public FriendEntity updateFriend(FriendEntity friend) {
        return this.dao.update(friend);
    }

    @Override
    public void removeFriend(FriendEntity friend) {
        this.dao.remove(friend);
    }

    @Override
    public List<SkillEntity> getAllSkills() {
        return this.dao.getResults("skill.get", SkillEntity.class);
    }

    @Override
    public SkillEntity createSkill(SkillEntity skillEntity) {
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
    public PaymentEntity createPayment(PaymentEntity paymentEntity) {
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
