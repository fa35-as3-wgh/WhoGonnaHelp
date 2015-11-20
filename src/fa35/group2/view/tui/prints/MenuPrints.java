package fa35.group2.view.tui.prints;

import fa35.group2.view.tui.selectiontypes.IFinalMainMenuSelection;
import fa35.group2.view.tui.selectiontypes.IFinalMainMenuSelectionShort;
import fa35.group2.view.tui.selectiontypes.IFinalSubmenuSelectionCharacter;

public class MenuPrints implements IFinalMainMenuSelection, IFinalSubmenuSelectionCharacter
{
    private PrintHelper printHelper;

    public MenuPrints()
    {
        this.printHelper = new PrintHelper();
    }

    public void printMenuStructure()
    {
        System.out.println();
        System.out.println("------------------------------");
        System.out.println("WHO'S GONNA HELP?!");
        System.out.println("-------------");
        System.out.println(FINDFRIENDBYSKILL + "\t:\tMenü von 'Finde passenden Helfer'");
        System.out.println(FRIENDMENU + "\t:\tMenü von Freund");
        System.out.println(SKILLMENU + "\t:\tMenü von Fertigkeit");
        System.out.println(PAYMENTMENU + "\t:\tMenü von Bezahlart");
        this.printHelper.printStandard();
        System.out.println("-------------");
        this.printHelper.printChooseMenu();
    }

    public void printSkillMenu()
    {
        this.printHelper.printLinedStandard();
        System.out.println("Fertigkeiten Menü");
        System.out.println("-------------");
        System.out.println(ONE + " :\tFertigkeit Hinzufügen");
        System.out.println(TWO + " :\tFertigkeit Löschen");
        System.out.println(THREE + " :\talle Fertigkeiten anzeigen");
        System.out.println("-------------");
        this.printHelper.printChooseMenu();
    }

    public void printFriendMenu()
    {
        this.printHelper.printLinedStandard();
        System.out.println("Freund Menü");
        System.out.println("-------------");
        System.out.println(FIVE + " :\tFreund Hinzufügen: ");
        System.out.println(SIX + " :\tFreund Löschen");
        System.out.println(SEVEN + " :\tFreund bearbeiten");
        System.out.println(EIGHT + " :\talle Freunde anzeigen");
        System.out.println("-------------");
        this.printHelper.printChooseMenu();
    }

    public void printPaymentMenu()
    {
        this.printHelper.printLinedStandard();
        System.out.println("Bezahlart Menü");
        System.out.println("-------------");
        System.out.println(TWENTYONE + " :\tBezahlart Hinzufügen");
        System.out.println(TWENTYTWO + " :\tBezahlart Löschen");
        System.out.println(TWENTYTHREE + " :\talle Bezahlarten anzeigen");
        System.out.println("-------------");
        this.printHelper.printChooseMenu();
    }

    public void printFriendSubmenuChange()
    {
        this.printHelper.printLinedStandard();
        System.out.println("Freund Bearbeiten Menu");
        System.out.println("-------------");
        System.out.println(ONETEEN + " :\tFreund Notiz Hinzufügen");
        System.out.println(TWOTEEN + " :\tFreund Notiz Löschen");
        System.out.println(THREETEEN + " :\tFreund Fertigkeit Hinzufügen");
        System.out.println(FOURTEEN + " :\tFreund Fertigkeit Löschen");
        System.out.println(FIVETEEN + " :\tFreund Bezahlart Hinzufügen");
        System.out.println(SIXTEEN + " :\tFreund Bezahlart Löschen");
        System.out.println(SEVENTEEN + " :\tFreund Kontaktangaben Hinzufügen");
        System.out.println(EIGHTTEEN + " :\tFreund Kontaktangaben Löschen");
        System.out.println(NINETEEN + " :\tFreund anzeigen");
        System.out.println("-------------");
        this.printHelper.printChooseMenu();
    }
}
