package fa35.group2.view.tui.prints;

import fa35.group2.model.entities.FriendEntity;
import fa35.group2.model.entities.PaymentEntity;
import fa35.group2.model.entities.SkillEntity;

import java.util.List;

public class FriendSubmenuPrints
{
    private PrintHelper printHelper;

    public FriendSubmenuPrints()
    {
        this.printHelper = new PrintHelper();
    }

    public void printFriendSubmenuAdd()
    {
        System.out.println();
        System.out.println("Freund Hinzufügen");
        System.out.println("-------------");
        this.printHelper.printEmptyImputForAbortion();
        this.printHelper.printGiveName();
    }

    public void printFriendSubmenuDelete(List<FriendEntity> friendEntities)
    {
        System.out.println();
        System.out.println("Freund löschen");
        System.out.println("-------------");
        this.printHelper.printFriendsWithIdAndName(friendEntities);
        this.printHelper.printEmptyImputForAbortion();
        this.printHelper.printGiveId();
    }

    public void printFriendSubmenuAll(List<FriendEntity> friendEntities)
    {
        this.printHelper.printFriend();
        this.printHelper.printFriendsWithIdAndName(friendEntities);
    }

    public void printChooseFriendForChange(List<FriendEntity> friendEntities)
    {
        System.out.println("Wähle Freund zum Bearbeiten:");
        this.printHelper.printFriend();
        this.printHelper.printFriendsWithIdAndName(friendEntities);
        this.printHelper.printEmptyImputForAbortion();
        this.printHelper.printGiveId();
    }

    public void printFriendSubmenuChangeAddNotice()
    {
        System.out.println("Freund Bearbeiten: Notiz Hinzufügen");
        System.out.println("-------------");
        this.printHelper.printEmptyImputForAbortion();
        System.out.println("Bitte geben Sie die Notiz ein:> ");
    }

    public void printFriendSubmenuChangeAddSkill(List<SkillEntity> skillEntities)
    {
        System.out.println("Freund Bearbeiten: Fertigkeit Hinzufügen");
        System.out.println("-------------");
        this.printHelper.printSkill();
        this.printHelper.printSkillsWithIdAndName(skillEntities);
        this.printHelper.printEmptyImputForAbortion();
        this.printHelper.printGiveId();
    }

    public void printFriendSubmenuChangeDeleteSkill(List<SkillEntity> skillEntities)
    {
        System.out.println("Freund Bearbeiten: Fertigkeit Löschen");
        System.out.println("-------------");
        this.printHelper.printSkill();
        this.printHelper.printSkillsWithIdAndName(skillEntities);
        this.printHelper.printEmptyImputForAbortion();
        this.printHelper.printGiveId();
    }

    public void printFriendSubmenuChangeAddPayment(List<PaymentEntity> paymentEntities)
    {
        System.out.println("Freund Bearbeiten: Bezahlart Hinzufügen");
        System.out.println("-------------");
        this.printHelper.printPayment();
        this.printHelper.printPaymentsWithIdAndName(paymentEntities);
        this.printHelper.printEmptyImputForAbortion();
        this.printHelper.printGiveId();
    }

    public void printFriendSubmenuChangeDeletePayment(List<PaymentEntity> paymentEntities)
    {
        System.out.println("Freund Bearbeiten: Bezahlart Löschen");
        System.out.println("-------------");
        this.printHelper.printPayment();
        this.printHelper.printPaymentsWithIdAndName(paymentEntities);
        this.printHelper.printEmptyImputForAbortion();
        this.printHelper.printGiveId();
    }

    public void printFriendSubmenuChangeAddcontact()
    {
        System.out.println("Freund Bearbeiten: Kontakt Hinzufügen");
        System.out.println("-------------");
        this.printHelper.printEmptyImputForAbortion();
        System.out.println("Bitte geben Sie die Kontaktdaten ein:> ");
    }

    public void printFriendSubmenuChangeSelectionShowFriend(
        FriendEntity friend, List<SkillEntity> skillEntities, List<PaymentEntity> paymentEntities
    )
    {
        System.out.println();
        System.out.println("------------------------------");
        System.out.println("Name" + "\t:\t" + friend.getName());
        System.out.println("Kontakt" + "\t:\t" + friend.getContact());
        System.out.println("Notiz" + "\t:\t" + friend.getNote());

        this.printHelper.printSkill();
        for (SkillEntity skill : skillEntities) {
            System.out.print(skill.getName() + ", ");
        }
        System.out.println();

        this.printHelper.printPayment();
        for (PaymentEntity payment : paymentEntities) {
            System.out.print(payment.getName() + ", ");
        }
        System.out.println();
    }
}
