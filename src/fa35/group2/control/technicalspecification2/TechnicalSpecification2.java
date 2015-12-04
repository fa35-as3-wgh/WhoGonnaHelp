package fa35.group2.control.technicalspecification2;

import fa35.group2.control.ITechnicalSpecification;
import fa35.group2.model.entities.FriendEntity;
import fa35.group2.model.IPersistence;
import fa35.group2.model.entities.PaymentEntity;
import fa35.group2.model.entities.SkillEntity;

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
        List friendEntities = persistence.getAllEntities(FriendEntity.class);

        return technicalSpecificationService.sortingByName(friendEntities);
    }

    @Override
    public Map<Integer, String> getAllFriendsIdAndName()
    {
        List friendEntities = persistence.getAllEntities(FriendEntity.class);

        return technicalSpecificationService.sortedFriendsWithIdAndName(friendEntities);
    }

    @Override
    public List<FriendEntity> getAllFriendsBySkill(int id)
    {
        List friendEntities = persistence.getAllEntities(FriendEntity.class);

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
        List<FriendEntity> friendEntities = persistence.getAllEntities(FriendEntity.class);

        return technicalSpecificationService.friendByName(name, friendEntities);
    }

    @Override
    public FriendEntity createFriend(String name)
    {
        FriendEntity friendEntity = new FriendEntity();
        friendEntity.setName(name);
        persistence.createEntity(friendEntity, FriendEntity.class);

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
        persistence.removeEntity(friendEntity);
    }

    @Override
    public FriendEntity addSkillToFriend(FriendEntity friendEntity, int skillId)
    {
        for (SkillEntity skillEntity : persistence.getAllEntities(SkillEntity.class)) {
            if (skillEntity.getId() == skillId) {
                if (!friendEntity.getSkills().contains(skillEntity))
                {
                    friendEntity.getSkills().add(skillEntity);
                }
            }
        }

        return persistence.updateFriend(friendEntity);
    }

    @Override
    public FriendEntity removeSkillFromFriend(FriendEntity friendEntity, int skillId)
    {
        for (SkillEntity skillEntity : friendEntity.getSkills()) {
            if (skillEntity.getId() == skillId) {
                friendEntity.getSkills().remove(skillEntity);
            }
        }

        return persistence.updateFriend(friendEntity);
    }

    @Override
    public FriendEntity addPaymentToFriend(FriendEntity friendEntity, int paymentId)
    {
        for (PaymentEntity paymentEntity : persistence.getAllEntities(PaymentEntity.class)) {
            if (paymentEntity.getId() == paymentId) {
                if (!friendEntity.getPayments().contains(paymentEntity)) {
                    friendEntity.getPayments().add(paymentEntity);
                }
            }
        }

        return persistence.updateFriend(friendEntity);
    }

    @Override
    public FriendEntity removePaymentFromFriend(FriendEntity friendEntity, int paymentId)
    {
        for (PaymentEntity paymentEntity : friendEntity.getPayments()) {
            if (paymentEntity.getId() == paymentId) {
                friendEntity.getPayments().remove(paymentEntity);
            }
        }

        return persistence.updateFriend(friendEntity);
    }

    @Override
    public List<SkillEntity> getAllSkills()
    {
        List skillEntities = persistence.getAllEntities(SkillEntity.class);

        return technicalSpecificationService.sortingByName(skillEntities);
    }

    @Override
    public SkillEntity createSkill(String skill)
    {
        SkillEntity skillEntity = new SkillEntity();
        skillEntity.setName(skill);
        persistence.createEntity(skillEntity, SkillEntity.class);

        return skillEntity;
    }

    @Override
    public void removeSkills(List<Integer> ids)
    {
        List<SkillEntity> skillEntities = persistence.getAllEntities(SkillEntity.class);

        if (null != skillEntities) {
            for (SkillEntity skillEntity : skillEntities) {
                for (int id : ids) {
                    if (id == skillEntity.getId()) {
                        persistence.removeEntity(skillEntity);
                    }
                }
            }
        }
    }

    @Override
    public List<PaymentEntity> getAllPayments()
    {
        List paymentEntities = persistence.getAllEntities(PaymentEntity.class);

        return technicalSpecificationService.sortingByName(paymentEntities);
    }

    @Override
    public PaymentEntity createPayment(String payment)
    {
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setName(payment);
        persistence.createEntity(paymentEntity, PaymentEntity.class);

        return paymentEntity;
    }

    @Override
    public void removePayments(List<Integer> ids)
    {
        List<PaymentEntity> paymentEntities = persistence.getAllEntities(PaymentEntity.class);

        if (null != paymentEntities) {
            for (PaymentEntity paymentEntity : paymentEntities) {
                for (int id : ids) {
                    if (id == paymentEntity.getId()) {
                        persistence.removeEntity(paymentEntity);
                    }
                }
            }
        }
    }
}
