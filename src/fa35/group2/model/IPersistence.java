package fa35.group2.model;

import fa35.group2.model.entities.FriendEntity;
import fa35.group2.model.entities.IEntity;
import fa35.group2.model.entities.PaymentEntity;
import fa35.group2.model.entities.SkillEntity;

import java.util.List;

public interface IPersistence {

    default boolean initializePersistence() {
        return initializePersistence(false);
    }

    boolean initializePersistence(boolean test);

    FriendEntity getFriend(int id);

    FriendEntity updateFriend(FriendEntity friend);

    <T extends IEntity> T createEntity(IEntity entity, Class<T> type);

    void removeEntity(IEntity entity);

    <T extends IEntity> List<T> getAllEntities(Class<T> type);
}
