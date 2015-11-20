package fa35.group2.view.tui;

import fa35.group2.view.tui.prints.PrintHelper;
import fa35.group2.view.tui.selectiontypes.IFinalMainMenuSelection;
import fa35.group2.view.tui.selectiontypes.IFinalSubmenuSelectionCharacter;

public class MenuSelectionMapperForStateMachine implements IFinalMainMenuSelection,
    IFinalSubmenuSelectionCharacter
{
    private TuiHelper tuiHelper;

    private PrintHelper printHelper;

    public MenuSelectionMapperForStateMachine(TuiHelper tuiHelper)
    {
        this.tuiHelper = tuiHelper;
        this.printHelper = new PrintHelper();
    }

    public Integer verifyMainMenuChoose(String line)
    {
        String bigCaseLine = line.toUpperCase();
        Integer state = this.standardChoose(bigCaseLine);
        if (null == state) {
            line = this.wrongInput();
            state = this.verifyMainMenuChoose(line);
        }

        return state;
    }

    private Integer standardChoose(String line)
    {
        if (line.isEmpty()) {
            System.out.println("Abbruch");
            return 0;
        }

        Integer state = null;
        switch (line) {
            case MAINMENU:
                state = 0;
                break;
            case EXIT:
                state = 1;
                break;
            case FRIENDMENU:
                state = 2;
                break;
            case SKILLMENU:
                state = 3;
                break;
            case PAYMENTMENU:
                state = 4;
                break;
            case FINDFRIENDBYSKILL:
                state = 5;
                break;
        }

        return state;
    }

    public Integer verifySkillMenuChoose(String line)
    {
        String bigCaseLine = line.toUpperCase();
        Integer state = this.standardChoose(bigCaseLine);

        if (null == state) {
            switch (bigCaseLine) {
                //addskill
                case ONE:
                    state = 6;
                    break;
                //deleteskill
                case TWO:
                    state = 7;
                    break;
                //allskills
                case THREE:
                    state = 8;
                    break;
                default:
                    String newLine = this.wrongInput();
                    state = this.verifySkillMenuChoose(newLine);
                    break;
            }
        }
        return state;
    }

    public Integer verifyPaymentMenuChoose(String line)
    {
        String bigCaseLine = line.toUpperCase();
        Integer state = this.standardChoose(bigCaseLine);

        if (null == state) {
            switch (bigCaseLine) {
                //addpayment
                case TWENTYONE:
                    state = 22;
                    break;
                //deletepayment
                case TWENTYTWO:
                    state = 23;
                    break;
                //allpayments
                case TWENTYTHREE:
                    state = 24;
                    break;
                default:
                    String newLine = this.wrongInput();
                    state = this.verifyPaymentMenuChoose(newLine);
                    break;
            }
        }

        return state;
    }

    public Integer verifyFriendMenuChoose(String line)
    {
        String bigCaseLine = line.toUpperCase();
        Integer state = this.standardChoose(bigCaseLine);

        if (null == state) {
            switch (bigCaseLine) {
                //addfriend
                case FIVE:
                    state = 9;
                    break;
                //deletefriend
                case SIX:
                    state = 10;
                    break;
                //changefriend
                case SEVEN:
                    state = 11;
                    break;
                //allfriends
                case EIGHT:
                    state = 12;
                    break;
                default:
                    String newLine = this.wrongInput();
                    state = this.verifyFriendMenuChoose(newLine);
                    break;
            }
        }

        return state;
    }

    public Integer verifyFriendSubmenuChoose(String line)
    {
        String bigCaseLine = line.toUpperCase();
        Integer state = this.standardChoose(bigCaseLine);

        if (null == state) {
            switch (bigCaseLine) {
                //addnoticefriend
                case ONETEEN:
                    state = 13;
                    break;
                //deletenoticefriend
                case TWOTEEN:
                    state = 14;
                    break;
                //addskillfriend
                case THREETEEN:
                    state = 15;
                    break;
                //deleteskillfriend
                case FOURTEEN:
                    state = 16;
                    break;
                //addpaymentfriend
                case FIVETEEN:
                    state = 17;
                    break;
                //deletepaymentfriend
                case SIXTEEN:
                    state = 18;
                    break;
                //addcontactfriend
                case SEVENTEEN:
                    state = 19;
                    break;
                //deletecontactfriend
                case EIGHTTEEN:
                    state = 20;
                    break;
                //showfriend
                case NINETEEN:
                    state = 21;
                    break;
                default:
                    String newLine = this.wrongInput();
                    state = this.verifyFriendMenuChoose(newLine);
                    break;
            }
        }

        return state;
    }

    private String wrongInput()
    {
        this.printHelper.printWrongInput();

        return this.tuiHelper.readLine();
    }
}
