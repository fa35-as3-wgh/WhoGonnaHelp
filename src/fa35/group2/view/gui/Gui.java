package fa35.group2.view.gui;

import fa35.group2.control.ITechnicalSpecification;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Gui extends Application
{
    public static ITechnicalSpecification technicalSpecification;

    public static void launch(ITechnicalSpecification technicalSpecification)
    {
        Gui.technicalSpecification = technicalSpecification;
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main.fxml"));
        Parent root = loader.load();

        MainController mainController = loader.getController();
        mainController.setTechnicalSpecification(Gui.technicalSpecification);

        primaryStage.setTitle("Who's Gonna Help?");
        primaryStage.setScene(new Scene(root, 640, 480));
        primaryStage.show();
    }

}
