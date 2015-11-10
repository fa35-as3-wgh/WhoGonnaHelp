package fa35.group2.control.technicalspecification2;

import fa35.group2.model.entities.FriendEntity;
import fa35.group2.model.entities.SkillEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TechnicalSpecificationService
{
    EntityComparator comparator;

    public TechnicalSpecificationService()
    {
        this.comparator = new EntityComparator();
    }

    public List sortingByName(List objectEntities)
    {
        if (null == objectEntities) {
            return new ArrayList<>();
        }

        Collections.sort(objectEntities, this.comparator);

        return objectEntities;
    }

    public Map<Integer, String> sortedFriendsWithIdAndName(List friendEntities)
    {
        if (null == friendEntities) {
            return new HashMap<>();
        }

        Collections.sort(friendEntities, this.comparator);

        Map<Integer, String> friendList = new HashMap<>();

        for (FriendEntity friendEntity : (List<FriendEntity>) friendEntities) {
            friendList.put(friendEntity.getId(), friendEntity.getName());
        }

        return friendList;
    }

    public List<FriendEntity> sortedFriendsBySkill(int id, List friendEntities)
    {
        this.sortingByName(friendEntities);

        List<FriendEntity> friendList = new ArrayList<>();

        for (FriendEntity friendEntity : (List<FriendEntity>) friendEntities) {
            for (SkillEntity skillEntity : friendEntity.getSkills()) {
                if (skillEntity.getId() == id) {
                    friendList.add(friendEntity);
                }
            }
        }

        return friendList;
    }

    public FriendEntity friendByName(String name, List<FriendEntity> friendEntities)
    {
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
}
