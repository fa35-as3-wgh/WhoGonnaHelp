package fa35.group2.control.technicalspecification2;

import fa35.group2.model.IEntity;

import java.util.Comparator;

public class EntityComparator implements Comparator<IEntity>
{
    @Override
    public int compare(IEntity o1, IEntity o2)
    {
        return o1.getName().compareTo(o2.getName());
    }
}
