package fa35.group2.control;

import fa35.group2.control.technicalspecification1.TechnicalSpecification1;
import fa35.group2.model.entities.FriendEntity;
import fa35.group2.model.IPersistence;
import org.easymock.EasyMockSupport;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.easymock.EasyMock.*;

public class TechnicalSpecificationTest extends EasyMockSupport
{
    private TechnicalSpecification1 technicalSpecification1;

    private IPersistence persistenceMock;

    @Before
    public void setUp()
    {
        this.persistenceMock = mock(IPersistence.class);
        this.technicalSpecification1 = new TechnicalSpecification1(persistenceMock);
    }

    @Test
    public void test_of_method_getAllFriendsIdAndName_returns_correct_list()
    {
        List<FriendEntity> friendEntities = new ArrayList<>();
        FriendEntity friend1 = new FriendEntity();
        friend1.setId(1);
        friend1.setName("Berta");
        FriendEntity friend2 = new FriendEntity();
        friend2.setId(2);
        friend2.setName("Alfred");
        friendEntities.add(friend1);
        friendEntities.add(friend2);

        Map<Integer, String> expectedList = new HashMap<>();
        expectedList.put(2, "Alfred");
        expectedList.put(1, "Berta");

        expect(persistenceMock.getAllFriends()).andReturn(friendEntities);
        replay(persistenceMock);

        Map<Integer, String> friendList = technicalSpecification1.getAllFriendsIdAndName();
        Assert.assertEquals(expectedList, friendList);
    }
}
