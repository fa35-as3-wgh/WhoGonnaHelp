package fa35.group2.view.gui;

import fa35.group2.control.ITechnicalSpecification;
import fa35.group2.model.entities.FriendEntity;
import fa35.group2.model.entities.PaymentEntity;
import fa35.group2.model.entities.SkillEntity;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ViewController
{
    private ITechnicalSpecification technicalSpecification;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<SkillEntity> skillselect;

    @FXML
    private TextArea notes;

    @FXML
    private ListView<SkillEntity> skills;

    @FXML
    private TextArea address;

    @FXML
    private ListView<FriendEntity> friends;

    @FXML
    private ListView<PaymentEntity> payment;

    @FXML
    void skill_select(ActionEvent event)
    {
        if (!skillselect.getSelectionModel().isEmpty()) {
            List<FriendEntity> friendList = technicalSpecification.getAllFriendsBySkill(skillselect.getValue().getId());
            friends.setItems(FXCollections.observableList(friendList));
        }

    }

    @FXML
    void get_details(MouseEvent event)
    {
        FriendEntity friend = friends.getSelectionModel().getSelectedItems().get(0);
        if (friend != null) {
            address.setText(friend.getContact());
            notes.setText(friend.getNote());
            List<SkillEntity> friendSkills = friend.getSkills();
            skills.setItems(FXCollections.observableList(friendSkills));
            List<PaymentEntity> payments = friend.getPayments();
            payment.setItems(FXCollections.observableList(payments));
        }
    }

    void getMainData()
    {
        if(technicalSpecification != null) {
            List<SkillEntity> skillList = technicalSpecification.getAllSkills();
            skillselect.setItems(FXCollections.observableList(skillList));
        }
    }

    public void setTechnicalSpecification(ITechnicalSpecification technicalSpecification)
    {
        this.technicalSpecification = technicalSpecification;
        getMainData();
    }
}

