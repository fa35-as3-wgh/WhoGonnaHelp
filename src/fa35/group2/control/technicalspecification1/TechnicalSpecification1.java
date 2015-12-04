package fa35.group2.control.technicalspecification1;

import fa35.group2.control.ITechnicalSpecification;
import fa35.group2.model.entities.FriendEntity;
import fa35.group2.model.IPersistence;
import fa35.group2.model.entities.PaymentEntity;
import fa35.group2.model.entities.SkillEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TechnicalSpecification1 implements ITechnicalSpecification
{
    private IPersistence persistence;

    public TechnicalSpecification1(IPersistence persistence)
    {
        this.persistence = persistence;
    }

    @Override
    public List<FriendEntity> getAllFriends()
    {
        List<FriendEntity> friendEntities = persistence.getAllEntities(FriendEntity.class);

        return friendEntities;
    }

    @Override
    public Map<Integer, String> getAllFriendsIdAndName()
    {
        Map<Integer, String> friendList = new HashMap<>();
        List<FriendEntity> friendEntities = persistence.getAllEntities(FriendEntity.class);

        for (FriendEntity friendEntity : friendEntities) {
            friendList.put(friendEntity.getId(), friendEntity.getName());
        }

        return friendList;
    }

    @Override
    public List<FriendEntity> getAllFriendsBySkill(int skillId)
    {
        List<FriendEntity> friendList = new ArrayList<>();
        List<FriendEntity> friendEntities = persistence.getAllEntities(FriendEntity.class);

        for (FriendEntity friendEntity : friendEntities) {
            for (SkillEntity skillEntity : friendEntity.getSkills()) {
                if (skillEntity.getId() == skillId) {
                    friendList.add(friendEntity);
                }
            }
        }

        return friendList;
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

        for (FriendEntity friendEntity : friendEntities) {
            if (friendEntity.getName().equals(name)) {
                return friendEntity;
            }
        }

        return null;
    }

    @Override
    public FriendEntity createFriend(String name)
    {
        FriendEntity friendEntity = new FriendEntity();
        friendEntity.setName(name);
        friendEntity = persistence.createEntity(friendEntity, FriendEntity.class);

        return friendEntity;
    }

    @Override
    public FriendEntity updateFriend(FriendEntity friend)
    {
        return persistence.updateFriend(friend);
    }

    @Override
    public FriendEntity addSkillToFriend(FriendEntity friendEntity, int skillId)
    {
        List<SkillEntity> skillEntities = persistence.getAllEntities(SkillEntity.class);

        for (SkillEntity skillEntity : skillEntities) {
            if (skillEntity.getId() == skillId) {
                if (!friendEntity.getSkills().contains(skillEntity)) {
                    friendEntity.getSkills().add(skillEntity);
                }
            }
        }

        return persistence.updateFriend(friendEntity);
    }

    @Override
    public FriendEntity removeSkillFromFriend(FriendEntity friendEntity, int skillId)
    {
        List<SkillEntity> skillEntities = persistence.getAllEntities(SkillEntity.class);

        for (SkillEntity skillEntity : skillEntities) {
            if (skillEntity.getId() == skillId) {
                friendEntity.getSkills().remove(skillEntity);
            }
        }

        return persistence.updateFriend(friendEntity);
    }

    @Override
    public FriendEntity addPaymentToFriend(FriendEntity friendEntity, int paymentId)
    {
        List<PaymentEntity> paymentEntities = persistence.getAllEntities(PaymentEntity.class);

        for (PaymentEntity paymentEntity : paymentEntities) {
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
        List<PaymentEntity> paymentEntities = persistence.getAllEntities(PaymentEntity.class);

        for (PaymentEntity paymentEntity : paymentEntities) {
            if (paymentEntity.getId() == paymentId) {
                friendEntity.getPayments().remove(paymentEntity);
            }
        }

        return persistence.updateFriend(friendEntity);
    }

    @Override
    public void removeFriend(int id)
    {
        FriendEntity friendEntity = persistence.getFriend(id);
        persistence.removeEntity(friendEntity);
    }

    @Override
    public List<SkillEntity> getAllSkills()
    {
        List<SkillEntity> skillEntities = persistence.getAllEntities(SkillEntity.class);

        return skillEntities;
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
        List<PaymentEntity> paymentEntities = persistence.getAllEntities(PaymentEntity.class);

        return paymentEntities;
    }

    @Override
    public PaymentEntity createPayment(String payment)
    {
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setName(payment);
        persistence.createEntity(paymentEntity, SkillEntity.class);

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
