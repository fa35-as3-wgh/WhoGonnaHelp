package fa35.group2.model.entities;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "friend")
@NamedQueries({
        @NamedQuery(name = "friend.getAll", query = "SELECT c FROM friend c"),
        @NamedQuery(name = "friend.get", query = "SELECT c FROM friend c WHERE c.id = :id")
})
public class FriendEntity implements IEntity{
    @Id
    @Column(name = "friend_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String contact;
    private String note;

    @ManyToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JoinTable(name = "friend_skills",
            joinColumns = {@JoinColumn(name = "friend_id")},
            inverseJoinColumns = {@JoinColumn(name = "skill_id")})
    private List<SkillEntity> skills = new ArrayList<SkillEntity>();

    @ManyToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JoinTable(name = "friend_payments",
            joinColumns = {@JoinColumn(name = "friend_id")},
            inverseJoinColumns = {@JoinColumn(name = "payment_id")})
    private List<PaymentEntity> payments = new ArrayList<PaymentEntity>();

    private final BooleanProperty on = new SimpleBooleanProperty();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<SkillEntity> getSkills() {
        return skills;
    }

    public List<PaymentEntity> getPayments() {
        return payments;
    }

    public final BooleanProperty onProperty() {
        return this.on;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FriendEntity) {
            FriendEntity other = (FriendEntity) obj;

            return (this.getId() == other.getId()) &&
                    ((this.getName() == null && other.getName() == null) ||
                            (this.getName() != null && this.getName().equals(other.getName()))) &&
                    ((this.getContact() == null && other.getContact() == null) ||
                            (this.getContact() != null && this.getContact().equals(other.getContact()))) &&
                    ((this.getNote() == null && other.getNote() == null) ||
                            (this.getNote() != null && this.getNote().equals(other.getNote()))) &&
                    ((this.getSkills() == null && other.getSkills() == null) ||
                            (this.getSkills() != null && this.getSkills().equals(other.getSkills()))) &&
                    ((this.getPayments() == null && other.getPayments() == null) ||
                            (this.getPayments() != null && this.getPayments().equals(other.getPayments())));
        }

        return super.equals(obj);
    }
}
