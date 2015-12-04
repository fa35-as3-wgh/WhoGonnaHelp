package fa35.group2.view.tui;

public class StateMachine {
    private Tui tui;

    private int state = 0;

    public StateMachine(Tui tui) {
        this.tui = tui;
    }

    public void stateMachine() {
        boolean running = true;

        while (running) {

            switch (state) {
                //mainMenu
                case 0:
                    state = this.tui.mainMenuSelection();
                    break;
                //exit
                case 1:
                    System.out.println("Exit!");
                    running = false;
                    break;
                //friendmenu
                case 2:
                    state = this.tui.friendMenuSelection();
                    break;
                //skillmenu
                case 3:
                    state = this.tui.skillMenuSelection();
                    break;
                //paymentmenu
                case 4:
                    state = this.tui.paymentMenuSelection();
                    break;
                //find
                case 5:
                    state = this.tui.findFriendBySkillSelection();
                    break;
                //addskill
                case 6:
                    state = this.tui.skillSubmenuSelectionAdd();
                    break;
                //deleteskill
                case 7:
                    state = this.tui.skillSubmenuSelectionDelete();
                    break;
                //allskills
                case 8:
                    state = this.tui.skillSubmenuSelectionAll();
                    break;
                //addfriend
                case 9:
                    state = this.tui.friendSubmenuSelectionAdd();
                    break;
                //deletefriend
                case 10:
                    state = this.tui.friendSubmenuSelectionDelete();
                    break;
                //changefriend
                case 11:
                    state = this.tui.friendSubmenuChangeSelection();
                    break;
                //allfriends
                case 12:
                    state = this.tui.friendSubmenuSelectionAll();
                    break;
                //addnoticefriend
                case 13:
                    state = this.tui.friendSubmenuChangeSelectionAddNotice();
                    break;
                //deletenoticefriend
                case 14:
                    state = this.tui.friendSubmenuChangeSelectionDeleteNotice();
                    break;
                //addskillfriend
                case 15:
                    state = this.tui.friendSubmenuChangeSelectionAddSkill();
                    break;
                //deleteskillfriend
                case 16:
                    state = this.tui.friendSubmenuChangeSelectionDeleteSkill();
                    break;
                //addpaymentfriend
                case 17:
                    state = this.tui.friendSubmenuChangeSelectionAddPayment();
                    break;
                //deletepaymentfriend
                case 18:
                    state = this.tui.friendSubmenuChangeSelectionDeletePayment();
                    break;
                //addcontactfriend
                case 19:
                    state = this.tui.friendSubmenuChangeSelectionAddContact();
                    break;
                //deletecontactfriend
                case 20:
                    state = this.tui.friendSubmenuChangeSelectionDeleteContact();
                    break;
                //showfriend
                case 21:
                    state = this.tui.friendSubmenuChangeSelectionShowFriend();
                    break;
                //addpayment
                case 22:
                    state = this.tui.paymentSubmenuSelectionAdd();
                    break;
                //deletepayment
                case 23:
                    state = this.tui.paymentSubmenuSelectionDelete();
                    break;
                //allpayments
                case 24:
                    state = this.tui.paymentSubmenuSelectionAll();
                    break;
                case 25:
                    state = this.tui.waitingForInput();
                    break;
            }
        }

        // force exit of program!
        System.exit(0);
    }
}
