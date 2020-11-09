package models;

import javafx.scene.paint.Color;

import java.util.Random;

public class PieceFactory {
    //TODO: Randomly flip piece before returning?
    public enum Tetromino {
        LINE(0), L(1), Z(2), PLUS(3), SQUARE(4);

        int value;
        Tetromino(int i) {
            value = i;
        }
    }

    private int choice;
    private static long seed = 1;
    public static Random rng = new Random(seed);

    public static Piece getPiece() {
        int piece = rng.nextInt(Tetromino.values().length);
        Tetromino choice = Tetromino.LINE;
        for(Tetromino t : Tetromino.values())
            if(t.value == piece)
                choice = t;
        c = getNextColor();
        switch(choice) {
            case LINE: Line(); break;
            case L: L(); break;
            case Z: Z(); break;
            case PLUS: Plus(); break;
            case SQUARE: Square(); break;
        }
        return new Piece(shape);
    }
    public static Color getNextColor() {
        int
            offset = 195,
            limit = 255,
            r = limit - rng.nextInt(offset),
            g = limit - rng.nextInt(offset),
            b = limit - rng.nextInt(offset);
        double
            alpha = .8,
            o = alpha + (1-alpha) * (rng.nextDouble()); //0 is transparent
        return Color.rgb(r, g, b, o);
    }
    static Color c;
    static Color[][] shape;
    private static Color[][] Line() {
        shape = new Color[][]{
                {null, null, null, null, null},
                {null, null,    c, null, null},
                {null, null,    c, null, null},
                {null, null,    c, null, null},
                {null, null,    c, null, null}
        };
        return shape;
    }
    private static Color[][] L() {
        shape = new Color[][]{
                {null, null, null, null, null},
                {null, null,    c, null, null},
                {null, null,    c, null, null},
                {null, null,    c,    c, null},
                {null, null, null, null, null}
        };
        return shape;
    }
    private static Color[][] Z() {
        shape = new Color[][]{
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null,    c,    c, null, null},
                {null, null,    c,    c, null},
                {null, null, null, null, null}
        };
        return shape;
    }
    private static Color[][] Plus() {
        shape = new Color[][]{
                {null, null, null, null, null},
                {null, null,    c, null, null},
                {null, null,    c,    c, null},
                {null, null,    c, null, null},
                {null, null, null, null, null}
        };
        return shape;
    }
    private static Color[][] Square() {
        shape = new Color[][]{
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null,    c,    c, null},
                {null, null,    c,    c, null},
                {null, null, null, null, null}
        };
        return shape;
    }
}
