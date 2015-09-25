package fa35.group2.model.xml;

import fa35.group2.model.FriendEntity;
import fa35.group2.model.IPersistence;
import fa35.group2.model.PaymentEntity;
import fa35.group2.model.SkillEntity;

import java.util.List;

public class XmlPersistence implements IPersistence {

    @Override
    public boolean initializePersistence() {
        return false;
    }

    @Override
    public List<FriendEntity> getAllFriends() {
        return null;
    }

    @Override
    public FriendEntity getFriend(int id) {
        return null;
    }

    @Override
    public FriendEntity createFriend(FriendEntity friend) {
        return null;
    }

    @Override
    public FriendEntity updateFriend(FriendEntity friend) {
        return null;
    }

    @Override
    public void removeFriend(FriendEntity friend) {

    }

    @Override
    public List<SkillEntity> getAllSkills() {
        return null;
    }

    @Override
    public SkillEntity createSkill(SkillEntity skillEntity) {
        return null;
    }

    @Override
    public void removeSkill(SkillEntity skillEntity) {

    }

    @Override
    public List<PaymentEntity> getAllPayments() {
        return null;
    }

    @Override
    public PaymentEntity createPayment(PaymentEntity paymentEntity) {
        return null;
    }

    @Override
    public void removePayment(PaymentEntity paymentEntity) {

    }
}
