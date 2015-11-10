package fa35.group2.model;

import javax.persistence.*;

@Entity(name = "skill")
@NamedQuery(name = "skill.get", query = "SELECT c FROM skill c")
public class SkillEntity implements IEntity{

    @Id
    @Column(name = "skill_id")
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
        if (obj instanceof SkillEntity) {
            SkillEntity other = (SkillEntity) obj;
            return (this.getId() == other.getId()) &&
                    ((this.getName() == null && other.getName() == null) ||
                            (this.getName() != null && this.getName().equals(other.getName())));
        }

        return super.equals(obj);
    }
}
