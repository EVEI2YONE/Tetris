package models;

import javafx.scene.paint.Color;

import java.util.Random;

public class Piece {
    public int x, y;
    private Color[][] shape;

    public Piece(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Piece(Color[][] piece) {
        shape = piece;
    }

    public Color[][] getShape() {
        return shape;
    }
    public void setShape(Color[][] shape) {
        this.shape = shape;
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
    public Color[][] shapeCopy() {
        Color[][] copy = new Color[shape.length][shape[0].length];
        for(int i = 0; i < shape.length; i++)
            for(int j = 0; j < shape[0].length; j++)
                copy[i][j] = shape[i][j];
        return copy;
    }
    public void rotatePiece(int dir) {
        if(dir < 0) rotateLeft();
        else rotateRight();
    }

    public void flip() {
        Color[][] flip = shapeCopy();
        for(int i = 0; i < flip.length; i++)
            for(int j = 0; j < flip[0].length; j++)
                shape[i][flip[0].length-1-j] = flip[i][j];
    }
    public String toString() {
        String str = "";
        for(Color[] row : shape) {
            for(Color c : row) {
                if(c == null)
                    str += " ";
                else
                    str += "X";
            }
            str += "\n";
        }
        return str;
    }


    public void rotateLeft() {
        Color[][] copy = shapeCopy();
        int c = copy[0].length;
        int r = copy.length-1;
        for(int i = 0; i < c; i++) {
            Color[] col = copyCol(i, copy);
            for(int j = 0; j < col.length; j++) {
                shape[r-i][j] = col[j];
            }
        }
    }
    public void rotateRight() {
        Color[][] copy = shapeCopy();
        int c = copy[0].length-1;
        for(int i = c; i >= 0; i--) {
            Color[] col = copyCol(i, copy);
            for(int j = 0; j < col.length; j++) {
                shape[c-i][j] = col[j];
            }
        }
    }

    private Color[] copyCol(int col, Color[][] shape) {
        Color[] column = new Color[shape.length];
        for(int i = 0; i < shape.length; i++)
            column[i] = shape[i][col];
        return column;
    }
    private Color[] copyRow(int row, Color[][] shape) {
        Color[] rows = new Color[shape[0].length];
        for(int i = 0; i < shape[0].length; i++)
            rows[i] = shape[row][i];
        return rows;
    }
}
