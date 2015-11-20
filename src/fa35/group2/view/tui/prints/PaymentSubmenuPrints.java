package fa35.group2.view.tui.prints;

import fa35.group2.model.entities.PaymentEntity;

import java.util.List;

public class PaymentSubmenuPrints
{
    private PrintHelper printHelper;

    public PaymentSubmenuPrints()
    {
        this.printHelper = new PrintHelper();
    }

    public void printPaymentSubmenuAdd()
    {
        System.out.println();
        System.out.println("Bezahlart Hinzufügen");
        System.out.println("-------------");
        this.printHelper.printEmptyImputForAbortion();
        this.printHelper.printGiveName();
    }

    public void printPaymentSubmenuDelete(List<PaymentEntity> paymentEntities)
    {
        System.out.println();
        System.out.println("Bezahlart löschen");
        this.printHelper.printPaymentsWithIdAndName(paymentEntities);
        this.printHelper.printEmptyImputForAbortion();
        this.printHelper.printGiveId();
    }

    public void printPaymentSubmenuAll(List<PaymentEntity> paymentEntities)
    {
        this.printHelper.printPayment();
        this.printHelper.printPaymentsWithIdAndName(paymentEntities);
    }
}
