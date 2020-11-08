package models;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class NextPieceSection {
    Rectangle[][] rectangles;
    public boolean
        generated = false;
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
    public boolean isGenerated() { return generated; }
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
        generated = true;
    }
    public Color[][] load() {
        Color[][] color = new Color[rows][cols];
        for(int i = 0; i < color.length; i++)
            for(int j = 0; j < color[0].length; j++)
                color[i][j] = (Color) rectangles[i][j].getFill();
        return color;
    }
}
