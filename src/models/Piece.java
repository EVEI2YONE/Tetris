package models;

import javafx.scene.paint.Color;

import java.util.Random;

public class Piece {
    private int x, y;

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public Color[][] getShape() {
        return shape;
    }
    public void setShape(Color[][] shape) {
        this.shape = shape;
    }

    private Color[][] shape;
    public Piece(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Piece(Color[][] piece) {
        shape = piece;
    }

    public Piece copy() {
        Piece copy = new Piece(x, y);
        Color[][] color = new Color[shape.length][shape[0].length];
        for(int i = 0; i < color.length; i++)
            for(int j = 0; j < color[0].length; j++)
                color[i][j] = shape[i][j];
        copy.setShape(color);
        return copy;
    }
    public void rotatePiece(Color[][] rotate, int dir) {

    }


}
