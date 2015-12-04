package fa35.group2.model.entities;

import javax.persistence.*;

@Entity(name = "payment")
@NamedQuery(name = "payment.get", query = "SELECT c FROM payment c")
public class PaymentEntity implements IEntity{
    @Id
    @Column(name = "payment_id")
    @GeneratedValue
    private int id;

    private String name;

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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PaymentEntity) {
            PaymentEntity other = (PaymentEntity) obj;

            return (this.getId() == other.getId()) &&
                    ((this.getName() == null && other.getName() == null) ||
                            (this.getName() != null && this.getName().equals(other.getName())));
        }

        return super.equals(obj);
    }
}