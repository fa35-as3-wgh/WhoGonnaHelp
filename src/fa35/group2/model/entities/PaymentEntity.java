package fa35.group2.model.entities;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import javax.persistence.*;

@Entity(name = "payment")
@NamedQuery(name = "payment.get", query = "SELECT c FROM payment c")
public class PaymentEntity implements IEntity{
    @Id
    @Column(name = "payment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Transient
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

    @Override
    public String toString() {
        return name;
    }

    public final BooleanProperty onProperty() {
        return this.on;
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
