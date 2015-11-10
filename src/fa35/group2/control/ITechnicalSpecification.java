package fa35.group2.control;

import fa35.group2.model.entities.FriendEntity;
import fa35.group2.model.entities.PaymentEntity;
import fa35.group2.model.entities.SkillEntity;

import java.util.List;
import java.util.Map;

public interface ITechnicalSpecification
{
    List<FriendEntity> getAllFriends();

    Map<Integer, String> getAllFriendsIdAndName();

    List<FriendEntity> getAllFriendsBySkill(int id);

    FriendEntity getFriendById(int id);

    FriendEntity getFriendByName(String name);

    FriendEntity createFriend(String name);

    FriendEntity updateFriend(FriendEntity friend);

    FriendEntity addSkillToFriend(FriendEntity friendEntity, int id);

    FriendEntity removeSkillFromFriend(FriendEntity friendEntity, int id);

    FriendEntity addPaymentToFriend(FriendEntity friendEntity, int id);

    FriendEntity removePaymentFromFriend(FriendEntity friendEntity, int id);

    void removeFriend(int id);

    List<SkillEntity> getAllSkills();

    SkillEntity createSkill(String skill);

    void removeSkills(List<Integer> ids);

    List<PaymentEntity> getAllPayments();

    PaymentEntity createPayment(String payment);

    void removePayments(List<Integer> ids);
}
