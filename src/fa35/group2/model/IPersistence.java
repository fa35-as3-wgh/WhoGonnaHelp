package fa35.group2.model;

import java.util.List;

public interface IPersistence {

    boolean initializePersistence();

    // Freunde
    List<FriendEntity> getAllFriends(); // nur id und name

    FriendEntity getFriend(int id); // alle Daten

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
