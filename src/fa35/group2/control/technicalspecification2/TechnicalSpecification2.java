package fa35.group2.control.technicalspecification2;

import fa35.group2.control.ITechnicalSpecification;
import fa35.group2.model.FriendEntity;
import fa35.group2.model.IPersistence;
import fa35.group2.model.PaymentEntity;
import fa35.group2.model.SkillEntity;

import java.util.List;
import java.util.Map;

public class TechnicalSpecification2 implements ITechnicalSpecification
{
    private IPersistence persistence;

    private TechnicalSpecificationService technicalSpecificationService;

    public TechnicalSpecification2(IPersistence persistence)
    {
        this.persistence = persistence;
        this.technicalSpecificationService = new TechnicalSpecificationService();
    }

    @Override
    public List<FriendEntity> getAllFriends()
    {
        List friendEntities = persistence.getAllFriends();

        return technicalSpecificationService.sortingByName(friendEntities);
    }

    @Override
    public Map<Integer, String> getAllFriendsIdAndName()
    {
        List friendEntities = persistence.getAllFriends();

        return technicalSpecificationService.sortedFriendsWithIdAndName(friendEntities);
    }

    @Override
    public List<FriendEntity> getAllFriendsBySkill(int id)
    {
        List friendEntities = persistence.getAllFriends();

        return technicalSpecificationService.sortedFriendsBySkill(id, friendEntities);
    }

    @Override
    public FriendEntity getFriendById(int id)
    {
        return persistence.getFriend(id);
    }

    @Override
    public FriendEntity getFriendByName(String name)
    {
        List<FriendEntity> friendEntities = persistence.getAllFriends();

        return technicalSpecificationService.friendByName(name, friendEntities);
    }

    @Override
    public FriendEntity createFriend(String name)
    {
        FriendEntity friendEntity = new FriendEntity();
        friendEntity.setName(name);
        persistence.createFriend(friendEntity);

        return friendEntity;
    }

    @Override
    public FriendEntity updateFriend(FriendEntity friend)
    {
        return persistence.updateFriend(friend);
    }

    @Override
    public void removeFriend(int id)
    {
        FriendEntity friendEntity = persistence.getFriend(id);
        persistence.removeFriend(friendEntity);
    }

    @Override
    public List<SkillEntity> getAllSkills()
    {
        List skillEntities = persistence.getAllSkills();

        return technicalSpecificationService.sortingByName(skillEntities);
    }

    @Override
    public SkillEntity createSkill(String skill)
    {
        SkillEntity skillEntity = new SkillEntity();
        skillEntity.setName(skill);
        persistence.createSkill(skillEntity);

        return skillEntity;
    }

    @Override
    public void removeSkills(List<Integer> ids)
    {
        List<SkillEntity> skillEntities = persistence.getAllSkills();

        if (null != skillEntities) {
            for (SkillEntity skillEntity : skillEntities) {
                for (int id : ids) {
                    if (id == skillEntity.getId()) {
                        persistence.removeSkill(skillEntity);
                    }
                }
            }
        }
    }

    @Override
    public List<PaymentEntity> getAllPayments()
    {
        List paymentEntities = persistence.getAllPayments();

        return technicalSpecificationService.sortingByName(paymentEntities);
    }

    @Override
    public PaymentEntity createPayment(String payment)
    {
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setName(payment);
        persistence.createPayment(paymentEntity);

        return paymentEntity;
    }

    @Override
    public void removePayments(List<Integer> ids)
    {
        List<PaymentEntity> paymentEntities = persistence.getAllPayments();

        if (null != paymentEntities) {
            for (PaymentEntity paymentEntity : paymentEntities) {
                for (int id : ids) {
                    if (id == paymentEntity.getId()) {
                        persistence.removePayment(paymentEntity);
                    }
                }
            }
        }
    }
}
