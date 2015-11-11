package fa35.group2.view.gui;

import fa35.group2.control.ITechnicalSpecification;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController
{
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ViewController viewController;

    @FXML
    private EditController editController;

    @FXML
    private AddDeleteController adddeleteController;

    @FXML
    void load_view()
    {
        viewController.getMainData();
    }

    @FXML
    void load_edit()
    {
        editController.getMainData();
    }

    @FXML
    void load_adddelete()
    {
        adddeleteController.getMainData();
    }

    public void setTechnicalSpecification(ITechnicalSpecification technicalSpecification)
    {
        this.viewController.setTechnicalSpecification(technicalSpecification);
        this.editController.setTechnicalSpecification(technicalSpecification);
        this.adddeleteController.setTechnicalSpecification(technicalSpecification);
    }
}

