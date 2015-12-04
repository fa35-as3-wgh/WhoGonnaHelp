package fa35.group2.view.tui;

import fa35.group2.control.ITechnicalSpecification;
import fa35.group2.model.entities.FriendEntity;
import fa35.group2.model.entities.PaymentEntity;
import fa35.group2.model.entities.SkillEntity;
import fa35.group2.view.tui.prints.FindFriendBySkillPrints;
import fa35.group2.view.tui.prints.FriendSubmenuPrints;
import fa35.group2.view.tui.prints.MenuPrints;
import fa35.group2.view.tui.prints.PaymentSubmenuPrints;
import fa35.group2.view.tui.prints.PrintHelper;
import fa35.group2.view.tui.prints.SkillSubmenuPrints;

import java.util.ArrayList;
import java.util.List;

public class Tui
{
    private ITechnicalSpecification technicalSpecification;

    private MenuPrints menuPrints;

    private FindFriendBySkillPrints findFriendBySkillPrints;

    private SkillSubmenuPrints skillSubmenuPrints;

    private FriendSubmenuPrints friendSubmenuPrints;

    private PaymentSubmenuPrints paymentSubmenuPrints;

    private Integer choosedFriendId;

    private PrintHelper printHelper;

    private TuiHelper tuiHelper;

    private MenuSelectionMapperForStateMachine menuMapper;

    public Tui(ITechnicalSpecification technicalSpecification)
    {
        this.technicalSpecification = technicalSpecification;
        this.menuPrints = new MenuPrints();
        this.findFriendBySkillPrints = new FindFriendBySkillPrints();
        this.skillSubmenuPrints = new SkillSubmenuPrints();
        this.friendSubmenuPrints = new FriendSubmenuPrints();
        this.paymentSubmenuPrints = new PaymentSubmenuPrints();
        this.printHelper = new PrintHelper();
        this.tuiHelper = new TuiHelper();
        this.menuMapper = new MenuSelectionMapperForStateMachine(this.tuiHelper);
        StateMachine stateMachine = new StateMachine(this);

        stateMachine.stateMachine();
    }

    public Integer mainMenuSelection()
    {
        this.menuPrints.printMenuStructure();

        return this.menuMapper.verifyMainMenuChoose(this.tuiHelper.readLine());
    }

    public Integer skillMenuSelection()
    {
        this.menuPrints.printSkillMenu();

        return this.menuMapper.verifySkillMenuChoose(this.tuiHelper.readLine());
    }

    public Integer friendMenuSelection()
    {
        this.menuPrints.printFriendMenu();

        return this.menuMapper.verifyFriendMenuChoose(this.tuiHelper.readLine());
    }

    public Integer paymentMenuSelection()
    {
        this.menuPrints.printPaymentMenu();

        return this.menuMapper.verifyPaymentMenuChoose(this.tuiHelper.readLine());
    }

    public Integer findFriendBySkillSelection()
    {
        List<SkillEntity> skillEntities = this.technicalSpecification.getAllSkills();
        this.findFriendBySkillPrints.printFindFriendBySkill(skillEntities);
        Integer id = this.tuiHelper.readLineExpectInteger();

        if (null == id) {
            return 0;
        }

        if (this.isSkillExisting(skillEntities, id)) {
            List<FriendEntity> friendEntities = this.technicalSpecification.getAllFriendsBySkill(id);
            if (friendEntities.isEmpty()) {
                this.printHelper.printNoFriendHasSkill();
                return 5;
            } else {
                System.out.println();
                this.printHelper.printFriendsWithNameAndContactAndNotice(friendEntities);
                return 25;
            }
        }

        return 5;
    }

    public Integer friendSubmenuChangeSelection()
    {
        List<FriendEntity> friendEntities = this.technicalSpecification.getAllFriends();
        this.friendSubmenuPrints.printChooseFriendForChange(friendEntities);
        boolean hasFriendChoosed = this.chooseFriendForChange();
        if (!hasFriendChoosed) {
            return 2;
        }
        this.menuPrints.printFriendSubmenuChange();

        return this.menuMapper.verifyFriendSubmenuChoose(this.tuiHelper.readLine());
    }

    public Integer skillSubmenuSelectionAdd()
    {
        this.skillSubmenuPrints.printSkillSubmenuAdd();
        String line = this.tuiHelper.readLine();

        if (null == line) {
            return 0;
        }

        SkillEntity skill = technicalSpecification.createSkill(line);
        this.printHelper.printIdAndName(skill);

        return 25;
    }

    public Integer skillSubmenuSelectionDelete()
    {
        List<SkillEntity> skillEntities = this.technicalSpecification.getAllSkills();
        this.skillSubmenuPrints.printSkillSubmenuDelete(skillEntities);

        try {
            Integer id = this.tuiHelper.readLineExpectInteger();
            if (null == id) {
                return 0;
            }
            this.removeSkill(skillEntities, id);
        } catch (Exception e) {
            this.printHelper.printNotAsigned();
        }

        return 3;
    }

    public Integer skillSubmenuSelectionAll()
    {
        this.skillSubmenuPrints.printSkillSubmenuAll(this.technicalSpecification.getAllSkills());

        return 25;
    }

    private void removeSkill(List<SkillEntity> skillEntities, Integer id) throws Exception
    {
        Boolean notFound = true;
        for (SkillEntity skill : skillEntities) {
            if (skill.getId() == id) {
                List<Integer> ids = new ArrayList<>();
                ids.add(id);
                technicalSpecification.removeSkills(ids);
                this.printHelper.printHasBeenDeleted();
                notFound = false;
            }
        }

        if (notFound) {
            throw new Exception();
        }
    }

    public Integer friendSubmenuSelectionAdd()
    {
        this.friendSubmenuPrints.printFriendSubmenuAdd();
        String line = this.tuiHelper.readLine();

        if (null == line) {
            return 0;
        }

        FriendEntity friend = technicalSpecification.createFriend(line);
        this.printHelper.printIdAndName(friend);

        return 25;
    }

    public Integer friendSubmenuSelectionDelete()
    {
        List<FriendEntity> friendEntities = this.technicalSpecification.getAllFriends();
        this.friendSubmenuPrints.printFriendSubmenuDelete(friendEntities);

        try {
            Integer id = this.tuiHelper.readLineExpectInteger();
            if (null == id) {
                return 0;
            }
            this.removeFriend(id);
        } catch (Exception e) {
            this.printHelper.printNotAsigned();
        }

        return 2;
    }

    public Integer friendSubmenuSelectionAll()
    {
        List<FriendEntity> friendEntities = this.technicalSpecification.getAllFriends();
        this.friendSubmenuPrints.printFriendSubmenuAll(friendEntities);

        return 25;
    }

    private boolean chooseFriendForChange()
    {
        Integer id = this.tuiHelper.readLineExpectInteger();
        if (null == id) {
            return false;
        }

        if (null != technicalSpecification.getFriendById(id)) {
            this.choosedFriendId = id;
        } else {
            this.printHelper.printWrongInput();
            this.chooseFriendForChange();
        }

        return true;
    }

    private void removeFriend(Integer id) throws Exception
    {
        Boolean notFound = true;
        if (null != technicalSpecification.getFriendById(id)) {
            technicalSpecification.removeFriend(id);
            this.printHelper.printHasBeenDeleted();
            notFound = false;
        }

        if (notFound) {
            throw new Exception();
        }
    }

    public Integer paymentSubmenuSelectionAdd()
    {
        this.paymentSubmenuPrints.printPaymentSubmenuAdd();
        String line = this.tuiHelper.readLine();

        if (null == line) {
            return 0;
        }

        PaymentEntity payment = technicalSpecification.createPayment(line);
        this.printHelper.printIdAndName(payment);

        return 25;
    }

    public Integer paymentSubmenuSelectionDelete()
    {
        List<PaymentEntity> paymentEntities = this.technicalSpecification.getAllPayments();
        this.paymentSubmenuPrints.printPaymentSubmenuDelete(paymentEntities);

        try {
            Integer id = this.tuiHelper.readLineExpectInteger();
            if (null == id) {
                return 0;
            }
            this.removePayment(paymentEntities, id);
        } catch (Exception e) {
            this.printHelper.printNotAsigned();
        }

        return 4;
    }

    public Integer paymentSubmenuSelectionAll()
    {
        this.paymentSubmenuPrints.printPaymentSubmenuAll(this.technicalSpecification.getAllPayments());

        return 25;
    }

    private void removePayment(List<PaymentEntity> paymentEntities, Integer id) throws Exception
    {
        Boolean notFound = true;
        for (PaymentEntity payment : paymentEntities) {
            if (payment.getId() == id) {
                List<Integer> ids = new ArrayList<>();
                ids.add(id);
                technicalSpecification.removePayments(ids);
                this.printHelper.printHasBeenDeleted();
                notFound = false;
            }
        }

        if (notFound) {
            throw new Exception();
        }
    }

    public Integer friendSubmenuChangeSelectionAddNotice()
    {
        this.friendSubmenuPrints.printFriendSubmenuChangeAddNotice();
        FriendEntity choosedFriendEntiy = this.technicalSpecification.getFriendById(choosedFriendId);
        String line = this.tuiHelper.readLine();
        if (!line.isEmpty()) {
            choosedFriendEntiy.setNote(line);
            technicalSpecification.updateFriend(choosedFriendEntiy);
            this.printHelper.printHasBeenSaved();
        }

        return 11;
    }

    public Integer friendSubmenuChangeSelectionDeleteNotice()
    {
        FriendEntity choosedFriendEntiy = this.technicalSpecification.getFriendById(choosedFriendId);
        choosedFriendEntiy.setNote(null);
        technicalSpecification.updateFriend(choosedFriendEntiy);
        this.printHelper.printHasBeenDeleted();

        return 11;
    }

    public Integer friendSubmenuChangeSelectionAddSkill()
    {
        Integer state;
        List<SkillEntity> skillEntities = this.technicalSpecification.getAllSkills();
        this.friendSubmenuPrints.printFriendSubmenuChangeAddSkill(skillEntities);
        Integer id = this.tuiHelper.readLineExpectInteger();

        if (null == id) {
            this.choosedFriendId = null;
            return 2;
        }

        FriendEntity choosedFriendEntiy = this.technicalSpecification.getFriendById(choosedFriendId);
        if (this.isSkillExisting(skillEntities, id)) {
            technicalSpecification.addSkillToFriend(choosedFriendEntiy, id);
            this.printHelper.printHasBeenSaved();
            state = 11;
        } else {
            this.printHelper.printNotAsigned();
            state = this.friendSubmenuChangeSelectionAddSkill();
        }

        return state;
    }

    private boolean isSkillExisting(List<SkillEntity> skillEntities, Integer id)
    {
        for (SkillEntity skill : skillEntities) {
            if (id == skill.getId()) {
                return true;
            }
        }

        return false;
    }

    public Integer friendSubmenuChangeSelectionDeleteSkill()
    {
        Integer state;
        FriendEntity choosedFriendEntiy = this.technicalSpecification.getFriendById(choosedFriendId);
        List<SkillEntity> skillEntities = choosedFriendEntiy.getSkills();
        this.friendSubmenuPrints.printFriendSubmenuChangeDeleteSkill(skillEntities);
        Integer id = this.tuiHelper.readLineExpectInteger();

        if (null == id) {
            this.choosedFriendId = null;
            return 2;
        }

        if (this.isSkillExisting(skillEntities, id)) {
            technicalSpecification.removeSkillFromFriend(choosedFriendEntiy, id);
            this.printHelper.printHasBeenDeleted();
            state = 11;
        } else {
            this.printHelper.printNotAsigned();
            state = this.friendSubmenuChangeSelectionDeleteSkill();
        }

        return state;
    }

    public Integer friendSubmenuChangeSelectionAddPayment()
    {
        Integer state;
        FriendEntity choosedFriendEntiy = this.technicalSpecification.getFriendById(choosedFriendId);
        List<PaymentEntity> paymentEntities = this.technicalSpecification.getAllPayments();
        this.friendSubmenuPrints.printFriendSubmenuChangeAddPayment(paymentEntities);

        Integer id = this.tuiHelper.readLineExpectInteger();
        if (null == id) {
            this.choosedFriendId = null;
            return 2;
        }

        if (this.isPaymentExisting(paymentEntities, id)) {
            technicalSpecification.addPaymentToFriend(choosedFriendEntiy, id);
            this.printHelper.printHasBeenSaved();
            state = 11;
        } else {
            this.printHelper.printNotAsigned();
            state = this.friendSubmenuChangeSelectionAddPayment();
        }

        return state;
    }

    private boolean isPaymentExisting(List<PaymentEntity> paymentEntities, Integer id)
    {
        for (PaymentEntity payment : paymentEntities) {
            if (id == payment.getId()) {
                return true;
            }
        }

        return false;
    }

    public Integer friendSubmenuChangeSelectionDeletePayment()
    {
        Integer state;
        FriendEntity choosedFriendEntiy = this.technicalSpecification.getFriendById(choosedFriendId);
        List<PaymentEntity> paymentEntities = choosedFriendEntiy.getPayments();
        this.friendSubmenuPrints.printFriendSubmenuChangeDeletePayment(paymentEntities);
        Integer id = this.tuiHelper.readLineExpectInteger();

        if (null == id) {
            this.choosedFriendId = null;
            return 2;
        }

        if (this.isPaymentExisting(paymentEntities, id)) {
            technicalSpecification.removePaymentFromFriend(choosedFriendEntiy, id);
            this.printHelper.printHasBeenDeleted();
            state = 11;
        } else {
            this.printHelper.printNotAsigned();
            state = this.friendSubmenuChangeSelectionAddPayment();
        }

        return state;
    }

    public Integer friendSubmenuChangeSelectionAddContact()
    {
        FriendEntity choosedFriendEntiy = this.technicalSpecification.getFriendById(choosedFriendId);
        this.friendSubmenuPrints.printFriendSubmenuChangeAddcontact();
        String line = this.tuiHelper.readLine();

        if (!line.isEmpty()) {
            choosedFriendEntiy.setContact(line);
            technicalSpecification.updateFriend(choosedFriendEntiy);
            this.printHelper.printHasBeenSaved();
        }

        return 11;
    }

    public Integer friendSubmenuChangeSelectionDeleteContact()
    {
        FriendEntity choosedFriendEntiy = this.technicalSpecification.getFriendById(choosedFriendId);
        choosedFriendEntiy.setContact(null);
        technicalSpecification.updateFriend(choosedFriendEntiy);
        this.printHelper.printHasBeenDeleted();

        return 11;
    }

    public Integer friendSubmenuChangeSelectionShowFriend()
    {
        FriendEntity choosedFriendEntiy = this.technicalSpecification.getFriendById(choosedFriendId);
        this.friendSubmenuPrints
            .printFriendSubmenuChangeSelectionShowFriend(
                choosedFriendEntiy, choosedFriendEntiy.getSkills(), choosedFriendEntiy.getPayments()
            );

        return 25;
    }

    public Integer waitingForInput()
    {
        this.printHelper.printEmptyImputForAbortion();

        return this.menuMapper.verifyMainMenuChoose(this.tuiHelper.readLineWaiting());
    }
}
