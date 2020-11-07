package models;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class NextPieceSection {
    Rectangle[][] rectangles;

    private int
        xstart = 580,
        ystart = 19,
        rows = 4,
        cols = 4,
        len = 45/3;

    public Group initSection() {
        Group group = new Group();
        rectangles = new Rectangle[rows][cols];
        for(int i = 0; i < rectangles.length; i++) {
            for(int j = 0; j < rectangles[0].length; j++) {
                int
                    xPos = xstart + j * (len + 1),
                    yPos = ystart + i * (len + 1);
                rectangles[i][j] = new Rectangle(xPos, yPos, len, len);
                rectangles[i][j].setFill(null);
                group.getChildren().add(rectangles[i][j]);
            }
        }
        return group;
    }
    public void generate() {
        int option = Tetris.random.nextInt(5);
        Color[][] piece = Piece.getPiece(option);
        for(int i = 0; i < piece.length; i++)
            for(int j = 0; j < piece[0].length; j++) {
                Color c = piece[i][j];
                rectangles[i][j].setFill(c);
                if(c != null)
                    rectangles[i][j].setStroke(Color.BLACK);
                else
                    rectangles[i][j].setStroke(null);
            }
    }
    public Color[][] load() {
        Color[][] color = new Color[rows][cols];

        return null;
    }
}
