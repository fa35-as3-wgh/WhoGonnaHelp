package fa35.group2.model.sqlite;

import fa35.group2.model.*;
import fa35.group2.model.entities.FriendEntity;
import fa35.group2.model.entities.IEntity;
import fa35.group2.model.entities.PaymentEntity;
import fa35.group2.model.entities.SkillEntity;

import java.util.List;

public class SqlitePersistence implements IPersistence, IResetable {

    private HibernateDao dao;

    @Override
    public boolean initializePersistence(boolean test) {
        try {
            this.dao = HibernateDao.getDao(test);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this.dao != null;
    }

    public List<FriendEntity> getAllFriends() {
        return this.dao.getResults("friend.getAll", FriendEntity.class);
    }

    @Override
    public FriendEntity getFriend(int id) {
        return this.dao.getResult("friend.get", FriendEntity.class, HibernateDao.entry("id", id));
    }

    public FriendEntity updateFriend(FriendEntity friend) {
        return this.dao.update(friend);
    }

    public List<SkillEntity> getAllSkills() {
        return this.dao.getResults("skill.get", SkillEntity.class);
    }

    public List<PaymentEntity> getAllPayments() {
        return this.dao.getResults("payment.get", PaymentEntity.class);
    }

    @Override
    public <T extends IEntity> T createEntity(
        IEntity entity, Class<T> type
    )
    {
        return type.cast(this.dao.insert(entity));
    }

    @Override
    public void removeEntity(IEntity entity) {
        this.dao.remove(entity);
    }

    @Override
    public <T extends IEntity> List<T> getAllEntities(Class<T> type)
    {
        if (type == FriendEntity.class)
        {
            getAllFriends();
        }

        if (type == SkillEntity.class)
        {
            getAllSkills();
        }

        if (type == PaymentEntity.class)
        {
            getAllPayments();
        }

        return null;
    }

    @Override
    public void reset() {
        HibernateDao.reset();
    }
}
