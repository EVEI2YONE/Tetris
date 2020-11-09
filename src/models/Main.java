package models;

import controllers.ApplicationController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

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
//        int[][] nums = new int[5][5];
//        populate(nums);
//        printArr(nums);
//        System.out.println("------------------");
//        rotateLeft(nums);
//        printArr(nums);
    }

    public static ApplicationController getController() {
        return controller;
    }

    public static int[][] copyArr(int[][] nums) {
        int[][] copy = new int[nums.length][nums[0].length];
        populate(copy);
        return copy;
    }
    public static void rotateLeft(int[][] nums) {
        int[][] copy = copyArr(nums);
        int c = copy[0].length;
        int r = copy.length-1;
        for(int i = 0; i < c; i++) {
            int[] col = copyCol(i, copy);
            for(int j = 0; j < col.length; j++) {
                nums[r-i][j] = col[j];
            }
        }
    }
    public static void rotateRight(int[][] nums) {
        int[][] copy = copyArr(nums);
        int c = copy[0].length-1;
        for(int i = c; i >= 0; i--) {
            int[] col = copyCol(i, copy);
            for(int j = 0; j < col.length; j++) {
                nums[c-i][j] = col[j];
            }
        }
    }
    public static int[] copyCol(int col, int[][] num) {
        int[] column = new int[num.length];
        for(int i = 0; i < num.length; i++)
            column[i] = num[i][col];
        return column;
    }
    public static int[] copyRow(int row, int[][] num) {
        int[] rows = new int[num[0].length];
        for(int i = 0; i < num[0].length; i++)
            rows[i] = num[row][i];
        return rows;
    }
    public static void populate(int[][] nums) {
        int c = 1;
        for(int i = 0; i < nums.length; i++)
            for(int j = 0; j < nums[0].length; j++)
                nums[i][j] = c++;
    }
    public static void printArr(int[][] nums) {
        for(int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums[0].length; j++)
                System.out.print(nums[i][j] + "\t");
            System.out.println();
        }
    }
}
