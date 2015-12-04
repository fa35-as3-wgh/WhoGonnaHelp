package fa35.group2;

import fa35.group2.control.technicalspecification1.TechnicalSpecification1;
import fa35.group2.model.entities.FriendEntity;
import fa35.group2.model.entities.IEntity;
import fa35.group2.model.xml.XmlPersistence;

import java.util.ArrayList;
import java.util.List;

public class TestingGenerics
{

    public void wakabe()
    {
        List myIntList = new ArrayList();
        myIntList.add(0);
        Integer x = (Integer) myIntList.iterator().next();

        List<Integer> myGenericIntList = new ArrayList<>();
        myGenericIntList.add(0);
        Integer y = myGenericIntList.iterator().next();
    }

    public static void main(String[] args)
    {
        List<String> listeMitGenerics = new ArrayList<>();
        listeMitGenerics.add("Hallo1");

        //geht nicht
        //   listeMitGenerics.addAll(new Object());

        methodeOhneGenerics(listeMitGenerics);

        //raw type
        List listeOhneGenerics = new ArrayList();

        listeOhneGenerics.add("Hallo2");
        listeOhneGenerics.add(new Object());

        methodeMitGenerics(listeOhneGenerics);

        methodeOhneGenericsTwo(listeOhneGenerics);

        //laufzeitfehler
        //       methodeMitGenericsTwo(listeOhneGenerics);

        List<String> listeMitGenerics2 = listeOhneGenerics;
        List listeOhneGenerics2 = listeMitGenerics;

        FriendEntity friend = new FriendEntity();
        friend.setName("bello");


        getFriend("bello", FriendEntity.class);
    }

    static <T extends IEntity> T getFriend(String name, Class<T> type)
    {
        TechnicalSpecification1 technicalSpecification1 = new TechnicalSpecification1(new XmlPersistence());
        return type.cast(technicalSpecification1.getFriendByName(name));
    }

    static void methodeOhneGenerics(List lst)
    {
        System.out.println(lst.get(0));
    }

    static void methodeMitGenerics(List<String> lst)
    {
        System.out.println(lst.get(0));
    }

    static void methodeOhneGenericsTwo(List lst)
    {
        System.out.println(lst.get(1));
    }

    static void methodeMitGenericsTwo(List<String> lst)
    {
        System.out.println(lst.get(1));
    }
}
