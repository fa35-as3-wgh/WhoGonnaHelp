package fa35.group2.model;

import fa35.group2.model.entities.FriendEntity;
import fa35.group2.model.entities.PaymentEntity;
import fa35.group2.model.entities.SkillEntity;

import java.util.List;

public interface IPersistence {

    default boolean initializePersistence() {
        return initializePersistence(false);
    }

    boolean initializePersistence(boolean test);

    // Freunde
    List<FriendEntity> getAllFriends();

    FriendEntity getFriend(int id);

    FriendEntity createFriend(FriendEntity friend);

    FriendEntity updateFriend(FriendEntity friend);

    void removeFriend(FriendEntity friend);

    // Fertigkeiten
    List<SkillEntity> getAllSkills();

    SkillEntity createSkill(SkillEntity skillEntity);

    void removeSkill(SkillEntity skillEntity);

    // Bezahlungen
    List<PaymentEntity> getAllPayments();

    PaymentEntity createPayment(PaymentEntity paymentEntity);

    void removePayment(PaymentEntity paymentEntity);
}
