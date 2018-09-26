package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class Main extends Application {

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        primaryStage.toBack();
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.setOpacity(0);

        primaryStage.show();

        Stage mainStage = new Stage();
        mainStage.initOwner(primaryStage);
        mainStage.initStyle(StageStyle.TRANSPARENT);

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mainStage.setX(event.getScreenX() - xOffset);
                mainStage.setY(event.getScreenY() - yOffset);
            }
        });
        Scene scene  = new Scene(root, 200, 200);
        scene.setFill(Color.TRANSPARENT);
        mainStage.setScene(scene);
        mainStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
