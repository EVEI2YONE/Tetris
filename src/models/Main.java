package models;

import controllers.ApplicationController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    private static ApplicationController controller;
    @Override
    public void start(Stage stage) throws Exception {
        stage.setWidth(711);
        stage.setHeight(712);
        stage.setResizable(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/application.fxml"));
        BorderPane application = loader.load();

        controller = loader.getController();
        Group group1 = controller.initBoard();
        Group group2 = controller.initNextPiece();
                       controller.initOtherSections();
        application.getChildren().addAll(group1, group2);
        Scene scene = new Scene(application);
        scene.getStylesheets().add("stylesheets/stylesheet.css");
        stage.setScene(scene);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
        Tetris.running = false;
    }

    public static ApplicationController getController() {
        return controller;
    }
}
