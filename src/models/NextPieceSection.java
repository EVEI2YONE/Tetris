package models;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class NextPieceSection {
    Rectangle[][] rectangles;
    Piece nextPiece;
    private int
        xstart = 570,
        ystart = 11,
        rows = 5,
        cols = 5,
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
        nextPiece = PieceFactory.getPiece();
        Color[][] shape = nextPiece.getShape();
        for(int i = 0; i < shape.length; i++)
            for(int j = 0; j < shape[0].length; j++) {
                Color c = shape[i][j];
                rectangles[i][j].setFill(c);
                if(c != null)
                    rectangles[i][j].setStroke(Color.BLACK);
                else
                    rectangles[i][j].setStroke(null);
            }
    }
    public Piece load() {
        return nextPiece;
    }
}
