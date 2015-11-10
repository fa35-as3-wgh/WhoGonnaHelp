package fa35.group2.model.xml;

import fa35.group2.model.*;
import fa35.group2.model.entities.FriendEntity;
import fa35.group2.model.entities.PaymentEntity;
import fa35.group2.model.entities.SkillEntity;
import org.xml.sax.SAXException;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class XmlPersistence implements IPersistence, IResetable {

    private static final String FILE_NAME = "db.xml";
    private static final String FILE_TEST_NAME = "test_db.xml";

    private Map<Integer, PaymentEntity> paymentEntityMap;
    private Map<Integer, SkillEntity> skillEntityMap;
    private Map<Integer, FriendEntity> friendEntityMap;

    private boolean test;

    @Override
    public boolean initializePersistence(boolean test) {

        this.test = test;

        this.paymentEntityMap = new TreeMap<Integer, PaymentEntity>();
        this.skillEntityMap = new TreeMap<Integer, SkillEntity>();
        this.friendEntityMap = new TreeMap<Integer, FriendEntity>();

        File file = new File(test ? FILE_TEST_NAME : FILE_NAME);
        if (file.exists() && file.canRead()) {
            return load(file);
        } else {
            persist();
            return true;
        }
    }

    @Override
    public List<FriendEntity> getAllFriends() {
        return new ArrayList<FriendEntity>(this.friendEntityMap.values());
    }

    @Override
    public FriendEntity getFriend(int id) {
        return this.friendEntityMap.get(id);
    }

    @Override
    public FriendEntity createFriend(FriendEntity friend) {

        int newId = getNextKey(this.friendEntityMap.keySet());

        friend.setId(newId);
        return updateFriend(friend);
    }

    @Override
    public FriendEntity updateFriend(FriendEntity friend) {

        this.friendEntityMap.put(friend.getId(), friend);

        friend.getPayments().forEach(paymentEntity -> {
            if (paymentEntity.getId() == 0) {
                int newId = getNextKey(this.paymentEntityMap.keySet());

                paymentEntity.setId(newId);
                this.paymentEntityMap.put(newId, paymentEntity);
            }
        });

        friend.getSkills().forEach(skillEntity -> {
            if (skillEntity.getId() == 0) {
                int newId = getNextKey(this.skillEntityMap.keySet());

                skillEntity.setId(newId);
                this.skillEntityMap.put(newId, skillEntity);
            }
        });

        persist();

        return friend;
    }

    @Override
    public void removeFriend(FriendEntity friend) {
        this.friendEntityMap.remove(friend.getId());
        persist();
    }

    @Override
    public List<SkillEntity> getAllSkills() {
        return new ArrayList<SkillEntity>(this.skillEntityMap.values());
    }

    @Override
    public SkillEntity createSkill(SkillEntity skillEntity) {
        int newId = getNextKey(this.skillEntityMap.keySet());

        skillEntity.setId(newId);
        this.skillEntityMap.put(newId, skillEntity);
        persist();

        return skillEntity;
    }

    @Override
    public void removeSkill(SkillEntity skillEntity) {
        this.skillEntityMap.remove(skillEntity.getId());

        this.friendEntityMap.forEach((integer, friendEntity) -> {
            friendEntity.getSkills().remove(skillEntity);
        });

        persist();
    }

    @Override
    public List<PaymentEntity> getAllPayments() {
        return new ArrayList<PaymentEntity>(this.paymentEntityMap.values());
    }

    @Override
    public PaymentEntity createPayment(PaymentEntity paymentEntity) {
        int newId = getNextKey(this.paymentEntityMap.keySet());

        paymentEntity.setId(newId);
        this.paymentEntityMap.put(newId, paymentEntity);
        persist();

        return paymentEntity;
    }

    @Override
    public void removePayment(PaymentEntity paymentEntity) {
        this.paymentEntityMap.remove(paymentEntity.getId());

        this.friendEntityMap.forEach((integer, friendEntity) -> {
            friendEntity.getPayments().remove(paymentEntity);
        });

        persist();
    }

    private int getNextKey(Set<Integer> keySet) {
        if (keySet != null) {
            OptionalInt highestKey = keySet.stream().mapToInt(i -> i).max();
            if (highestKey.isPresent()) {
                return highestKey.getAsInt() + 1;
            }
        }
        return 1;
    }

    private boolean load(File file) {
        XmlMarshaller marshaller = new XmlMarshaller(this.paymentEntityMap, this.skillEntityMap, this.friendEntityMap);
        try {
            marshaller.load(file);
            marshaller.unmarshal();
            return true;
        } catch (IOException | SAXException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void persist() {
        XmlMarshaller marshaller = new XmlMarshaller(this.paymentEntityMap, this.skillEntityMap, this.friendEntityMap);
        try {
            marshaller.marshal();
            marshaller.save(new File(test ? FILE_TEST_NAME : FILE_NAME));
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public void reset() {
        this.paymentEntityMap = new TreeMap<Integer, PaymentEntity>();
        this.skillEntityMap = new TreeMap<Integer, SkillEntity>();
        this.friendEntityMap = new TreeMap<Integer, FriendEntity>();

        persist();
    }
}
