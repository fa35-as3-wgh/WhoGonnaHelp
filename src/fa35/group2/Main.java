package fa35.group2;

import fa35.group2.control.technicalspecification2.TechnicalSpecification2;
import fa35.group2.model.IPersistence;
import fa35.group2.model.xml.XmlPersistence;
import fa35.group2.view.gui.Gui;

public class Main
{
//        private static final IPersistence persistence = new SqlitePersistence();
    private static final IPersistence persistence = new XmlPersistence();

    public static void main(String[] args)
    {
        persistence.initializePersistence();
        Gui.launch(new TechnicalSpecification2(persistence));

//               new Tui(new TechnicalSpecification1(persistence));
    }

    public static IPersistence getPersistence()
    {
        return persistence;
    }
}

