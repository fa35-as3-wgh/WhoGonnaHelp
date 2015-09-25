package fa35.group2.control.technicalspecification1;

import fa35.group2.control.ITechnicalSpecification;
import fa35.group2.model.FriendEntity;
import fa35.group2.model.IPersistence;
import fa35.group2.model.PaymentEntity;
import fa35.group2.model.SkillEntity;

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
        List<FriendEntity> friendEntities = persistence.getAllFriends();

        if (null == friendEntities) {
            return new ArrayList<FriendEntity>();
        }

        return friendEntities;
    }

    @Override
    public Map<Integer, String> getAllFriendsIdAndName()
    {
        Map<Integer, String> friendList = new HashMap<>();
        List<FriendEntity> friendEntities = persistence.getAllFriends();

        if (null == friendEntities) {
            return new HashMap<>();
        }

        for (FriendEntity friendEntity : friendEntities) {
            friendList.put(friendEntity.getId(), friendEntity.getName());
        }

        return friendList;
    }

    @Override
    public List<FriendEntity> getAllFriendsBySkill(int skillId)
    {
        List<FriendEntity> friendList = new ArrayList<>();
        List<FriendEntity> friendEntities = persistence.getAllFriends();

        if (null == friendEntities) {
            return new ArrayList<FriendEntity>();
        }

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
        List<FriendEntity> friendEntities = persistence.getAllFriends();

        if (null == friendEntities) {
            return null;
        }

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
        List<SkillEntity> skillEntities = persistence.getAllSkills();

        if (null == skillEntities) {
            return new ArrayList<SkillEntity>();
        }

        return skillEntities;
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
        List<PaymentEntity> paymentEntities = persistence.getAllPayments();

        if (null == paymentEntities) {
            return new ArrayList<PaymentEntity>();
        }

        return paymentEntities;
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
