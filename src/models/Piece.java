package models;

import javafx.scene.paint.Color;

import java.util.Random;

public class Piece {
    public enum Tetromino {
        LINE(0), L(1), Z(2), PLUS(3), SQUARE(4);

        int value;
        Tetromino(int i) {
            value = i;
        }
    }

    private Random random = Tetris.random;

    //TODO: Randomly flip piece before returning
    public static Color[][] getPiece(int piece) {
        Tetromino choice = Tetromino.LINE;
        for(Tetromino t : Tetromino.values())
            if(t.value == piece)
                choice = t;
        c = getNextColor();
        switch(choice) {
            case LINE: return Line();
            case L: return L();
            case Z: return Z();
            case PLUS: return Plus();
            case SQUARE: return Square();
        }
        return null;
    }
    public static Color getNextColor() {
        Random rng = Tetris.random;
        int
            offset = 30,
            limit = 255-(offset*2),
            r = rng.nextInt(limit)+offset,
            g = rng.nextInt(limit)+offset,
            b = rng.nextInt(limit)+offset;
        double
            o = (rng.nextDouble()); //0 is transparent
            if(o < .7) o += .29;
            else if(o > .8) o -= .2;
        return Color.rgb(r, g, b, o);
    }
    public void rotatePiece(Color[][] rotate, int dir) {

    }

    static Color c;
    static Color[][] piece;
    private static Color[][] Line() {
        piece = new Color[][]{
            {null, null, null, null, null},
            {null, null,    c, null, null},
            {null, null,    c, null, null},
            {null, null,    c, null, null},
            {null, null,    c, null, null}
        };
        return piece;
    }
    private static Color[][] L() {
        piece = new Color[][]{
            {null, null, null, null, null},
            {null, null,    c, null, null},
            {null, null,    c, null, null},
            {null, null,    c,    c, null},
            {null, null, null, null, null}
        };
        return piece;
    }
    private static Color[][] Z() {
        piece = new Color[][]{
            {null, null, null, null, null},
            {null, null, null, null, null},
            {null,    c,    c, null, null},
            {null, null,    c,    c, null},
            {null, null, null, null, null}
        };
        return piece;
    }
    private static Color[][] Plus() {
        piece = new Color[][]{
            {null, null, null, null, null},
            {null, null,    c, null, null},
            {null, null,    c,    c, null},
            {null, null,    c, null, null},
            {null, null, null, null, null}
        };
        return piece;
    }
    private static Color[][] Square() {
        piece = new Color[][]{
            {null, null, null, null, null},
            {null, null, null, null, null},
            {null, null,    c,    c, null},
            {null, null,    c,    c, null},
            {null, null, null, null, null}
        };
        return piece;
    }
}
