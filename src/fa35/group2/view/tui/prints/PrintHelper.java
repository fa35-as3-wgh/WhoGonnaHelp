package fa35.group2.view.tui.prints;

import fa35.group2.model.entities.FriendEntity;
import fa35.group2.model.entities.IEntity;
import fa35.group2.model.entities.PaymentEntity;
import fa35.group2.model.entities.SkillEntity;
import fa35.group2.view.tui.selectiontypes.IFinalMainMenuSelection;

import java.util.List;

public class PrintHelper implements IFinalMainMenuSelection
{
    public void printStandard()
    {
        System.out.println(MAINMENU + "\t:\tdas Hauptmenü");
        System.out.println(EXIT + "\t:\tbeendet das Programm");
    }

    public void printLinedStandard()
    {
        System.out.println();
        System.out.println("------------------------------");
        this.printStandard();
        System.out.println("-------------");
    }

    public void printSkillsWithIdAndName(List<SkillEntity> skillEntities)
    {
        printLinedIsAndName();
        for (SkillEntity skill : skillEntities) {
            System.out.println(skill.getId() + "\t:\t" + skill.getName());
        }
        System.out.println("-------------");
    }

    public void printIdAndName(IEntity entity)
    {
        System.out.println("Nr." + "\t:\t" + "Name");
        System.out.println(entity.getId() + "\t:\t" + entity.getName() + "\t wurde angelegt.");
    }

    private void printLinedIsAndName()
    {
        System.out.println("-------------");
        System.out.println("Nr." + "\t:\t" + "Name");
    }

    public void printFriendsWithIdAndName(List<FriendEntity> friendEntities)
    {
        printLinedIsAndName();
        for (FriendEntity friend : friendEntities) {
            System.out.println(friend.getId() + "\t:\t" + friend.getName());
        }
        System.out.println("-------------");
    }

    public void printPaymentsWithIdAndName(List<PaymentEntity> paymentEntities)
    {
        printLinedIsAndName();
        for (PaymentEntity payment : paymentEntities) {
            System.out.println(payment.getId() + "\t:\t" + payment.getName());
        }
        System.out.println("-------------");
    }

    public void printFriendsWithNameAndContactAndNotice(List<FriendEntity> friendEntities)
    {
        System.out.println("-------------");
        System.out.println("Name" + "\t:\t" + "Kontakt" + "\t:\t" + "Notiz");
        for (FriendEntity friend : friendEntities) {
            System.out.println(friend.getName() + ", " + friend.getContact() + ", " + friend.getNote());
        }
        System.out.println("-------------");
    }

    public void printGiveName()
    {
        System.out.println();
        System.out.println("Bitte geben Sie den Namen ein:> ");
    }

    public void printChooseMenu()
    {
        System.out.println();
        System.out.print("Wählen Sie einen Menüpunkt:> ");
    }

    public void printGiveId()
    {
        System.out.println();
        System.out.print("Bitte geben Sie die Nummer ein:> ");
    }

    public void printSkill()
    {
        System.out.println("Fertigkeiten: ");
    }

    public void printFriend()
    {
        System.out.println("Freunde: ");
    }

    public void printPayment()
    {
        System.out.println("Bezahlarten: ");
    }

    public void printWrongInput()
    {
        System.out.println();
        System.out.println("Eingabe ungültig, bitte erneut eingeben:> ");
    }

    public void printEmptyImputForAbortion()
    {
        System.out.print("(leere Eingabe : Abbruch)");
        System.out.println();
    }

    public void printNotAsigned()
    {
        System.out.println("Diese Nummer ist nicht zugeordnet.");
    }

    public void printNoFriendHasSkill()
    {
        System.out.println("Kein Freund besitzt diese Fähigkeit.");
    }

    public void printHasBeenDeleted()
    {
        System.out.println("Es wurde gelöscht.");
    }

    public void printHasBeenSaved()
    {
        System.out.println("Es wurde hinzugefügt.");
    }
}
