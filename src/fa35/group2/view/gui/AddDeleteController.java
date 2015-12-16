package fa35.group2.view.gui;

import fa35.group2.control.ITechnicalSpecification;
import fa35.group2.model.entities.PaymentEntity;
import fa35.group2.model.entities.SkillEntity;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;

import java.util.ArrayList;
import java.util.List;

public class AddDeleteController
{
	public ITechnicalSpecification technicalSpecification;

	@FXML
	private ListView<SkillEntity> skills;

	@FXML
	private Button add_payment_btn;

	@FXML
	private Button add_skill_btn;

	@FXML
	private TextField add_skill;

	@FXML
	private TextField add_payment;

	@FXML
	private ListView<PaymentEntity> payment;

	@FXML
	private Button payment_delete_btn;

	@FXML
	private Button skill_delete_btn;

	private List<SkillEntity> skillList;

	private List<PaymentEntity> paymentList;

	@FXML
	void del_paymentSelection(ActionEvent event)
	{
		getMainData();
		List<Integer> removedPayments = new ArrayList<>();
		for (int i = 0; i < paymentList.size(); i++)
		{
			PaymentEntity payment = paymentList.get(i);
			if (payment.onProperty().getValue())
			{
				removedPayments.add(payment.getId());
			}
			technicalSpecification.removePayments(removedPayments);
		}
		getMainData();
	}

	@FXML
	void del_skillSelection(ActionEvent event)
	{
		getMainData();
		List<Integer> removedSkills = new ArrayList<>();
		for (int i = 0; i < skillList.size(); i++)
		{
			SkillEntity skill = skillList.get(i);
			if (skill.onProperty().getValue())
			{
				removedSkills.add(skill.getId());
			}
			technicalSpecification.removeSkills(removedSkills);
		}
		getMainData();
	}

	@FXML
	void create_skill(ActionEvent event)
	{
		if (!add_skill.getText().isEmpty())
		{
			technicalSpecification.createSkill(add_skill.getText());
			add_skill.clear();
		}
		getMainData();

	}

	@FXML
	void create_payment(ActionEvent event)
	{
		if (!add_payment.getText().isEmpty())
		{
			technicalSpecification.createPayment(add_payment.getText());
			add_payment.clear();
		}
		getMainData();

	}

	void getMainData()
	{
		if (technicalSpecification != null)
		{
			paymentList = technicalSpecification.getAllPayments();

			payment.setItems(FXCollections.observableList(paymentList));
			payment.setCellFactory(CheckBoxListCell.forListView(PaymentEntity::onProperty));

			skillList = technicalSpecification.getAllSkills();

			skills.setItems(FXCollections.observableList(skillList));
			skills.setCellFactory(CheckBoxListCell.forListView(SkillEntity::onProperty));
		}
	}

	public void setTechnicalSpecification(ITechnicalSpecification technicalSpecification)
	{
		this.technicalSpecification = technicalSpecification;
		getMainData();
	}

}

