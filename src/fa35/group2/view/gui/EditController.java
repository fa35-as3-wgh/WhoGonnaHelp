package fa35.group2.view.gui;

import fa35.group2.control.ITechnicalSpecification;
import fa35.group2.model.entities.FriendEntity;
import fa35.group2.model.entities.PaymentEntity;
import fa35.group2.model.entities.SkillEntity;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class EditController
{
    public ITechnicalSpecification technicalSpecification;

    @FXML
    private Button add_btn;

    @FXML
    private Button save_btn;

    @FXML
    private Button delete_btn;

    @FXML
    private TextField multiinsert;

    @FXML
    private TextArea friend_address;

    @FXML
    private TextArea friend_notes;

    @FXML
    private ListView<SkillEntity> friend_skills;

    @FXML
    private ListView<PaymentEntity> friend_payment;

    @FXML
    private ListView<FriendEntity> friends;

    private List<FriendEntity> friendList;

    @FXML
    void get_details(MouseEvent event)
    {
        if (friends.getSelectionModel().getSelectedItems().get(0) != null) {
            FriendEntity friend = friends.getSelectionModel().getSelectedItems().get(0);
            friend_address.setText(friend.getContact());
            friend_notes.setText(friend.getNote());

            friend_skills.setItems(FXCollections.observableList(createSkillSelection(friend)));
            friend_skills.setCellFactory(CheckBoxListCell.forListView(SkillEntity::onProperty));

            friend_payment.setItems(FXCollections.observableList(createPaymentSelection(friend)));
            friend_payment.setCellFactory(CheckBoxListCell.forListView(PaymentEntity::onProperty));
        }
    }

    @FXML
    void del_selection(ActionEvent event)
    {
        getMainData();
        for (int i = 0; i < friendList.size(); i++) {
            FriendEntity friend = friendList.get(i);
            if (friend.onProperty().getValue()) {
                technicalSpecification.removeFriend(friend.getId());
            }
        }
        getMainData();
    }

    @FXML
    void add_friend(ActionEvent event)
    {
        if (!multiinsert.getText().isEmpty()) {
            technicalSpecification.createFriend(multiinsert.getText());
            getMainData();
        }
    }

    @FXML
    void save_friend(ActionEvent event)
    {
        FriendEntity friend = friends.getSelectionModel().getSelectedItems().get(0);
        friend.setNote(friend_notes.getText());
        friend.setContact(friend_address.getText());
        technicalSpecification.updateFriend(friend);
        technicalSpecification.updateFriend(friend);
        addSkill(friend);
        addPayment(friend);
    }

    void getMainData()
    {
        if (technicalSpecification != null) {
            friendList = technicalSpecification.getAllFriends();
            friends.setItems(FXCollections.observableList(friendList));
            friends.setCellFactory(CheckBoxListCell.forListView(FriendEntity::onProperty));
            clearSelection();
        }
    }

    private List<SkillEntity> createSkillSelection(FriendEntity friend)
    {
        List<SkillEntity> allSkills = technicalSpecification.getAllSkills();
        List<SkillEntity> friendSkills = friend.getSkills();
        //reset
        for (int i = 0; i < allSkills.size(); i++) {
            allSkills.get(i).onProperty().setValue(false);
        }
        //set
        for (int i = 0; i < friendSkills.size(); i++) {
            friendSkills.get(i).onProperty().setValue(true);
        }

        return allSkills;
    }

    private List<PaymentEntity> createPaymentSelection(FriendEntity friend)
    {
        List<PaymentEntity> allPayments = technicalSpecification.getAllPayments();
        List<PaymentEntity> friendPayments = friend.getPayments();
        //reset
        for (int i = 0; i < allPayments.size(); i++) {
            allPayments.get(i).onProperty().setValue(false);
        }
        //set
        for (int i = 0; i < friendPayments.size(); i++) {
            friendPayments.get(i).onProperty().setValue(true);
        }

        return allPayments;
    }

    void addSkill(FriendEntity friend)
    {
        List<SkillEntity> allSkills = technicalSpecification.getAllSkills();
        for (int i = 0; i < allSkills.size(); i++) {
            if (allSkills.get(i).onProperty().getValue()) {
                technicalSpecification.addSkillToFriend(friend, allSkills.get(i).getId());
            } else {
                technicalSpecification.removeSkillFromFriend(friend, allSkills.get(i).getId());
            }
        }
    }

    void addPayment(FriendEntity friend)
    {
        List<PaymentEntity> allPayments = technicalSpecification.getAllPayments();
        for (int i = 0; i < allPayments.size(); i++) {
            if (allPayments.get(i).onProperty().getValue()) {
                technicalSpecification.addPaymentToFriend(friend, allPayments.get(i).getId());
            } else {
                technicalSpecification.removePaymentFromFriend(friend, allPayments.get(i).getId());
            }
        }
    }

    void clearSelection()
    {
        friends.getSelectionModel().clearSelection();
        friend_notes.clear();
        friend_skills.getItems().clear();
        friend_address.clear();
        friend_payment.getItems().clear();
    }

    public void setTechnicalSpecification(ITechnicalSpecification technicalSpecification)
    {
        this.technicalSpecification = technicalSpecification;
        getMainData();
    }
}
