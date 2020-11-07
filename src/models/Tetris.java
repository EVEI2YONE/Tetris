package models;

import controllers.ApplicationController;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Tetris {
    private static long seed = 1;
    public final static Random random = new Random(seed);
    NextPieceSection next;
    Rectangle[][] board;

    public static boolean
        running = false;
    private int
        xstart = 10,
        ystart = 10,
        rows = 14,
        cols = 11,
        len = 45;


    static int row = 0;
    static int col = 0;
    public void loadNextPiece() {
        //involve some logic such that when current playable piece is played, to then copy, and update next piece
        //generates next playable piece
        next.generate();
    }
    public void playPiece() {
        //gets copy from the next piece section
        Color[][] piece = next.load();
    }

    public void recolor() {
        if (row >= board.length)
            row = 0;
        if (col >= board[0].length)
            col = 0;
        board[row++][col++].setFill(Piece.getNextColor());
    }
    public void run() {
        try {
            while (running) {
                sleep();
                loadNextPiece();
                playPiece();
                //recolor();
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void sleep() {
        try { Thread.sleep(20); } catch(Exception ex) { }
    }
    public void start() {
        if(running) return;
        running = true;
        Thread thread = new Thread(this::run);
        thread.start();
    }

    public void stop() {
        running = false;
        sleep();
        clearGame();
    }
    public void clearGame() {
        for(Rectangle[] r : board) {
            for(Rectangle rect : r) {
                rect.setFill(null);
            }
        }
        //loadNextPiece
    }

    public void setNextPieceSection(NextPieceSection piece) {
        next = piece;
    }
    public Group initGame() {
        Group boardGroup = new Group();
        board = new Rectangle[rows][cols];
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                int
                    xPos = xstart + j * (len + 1),
                    yPos = ystart + i * (len + 1);
                board[i][j] = new Rectangle(xPos, yPos, len, len);
                board[i][j].setFill(null);
                //TODO: optional to give stroke
                board[i][j].setStroke(Color.BLACK);
                boardGroup.getChildren().add(board[i][j]);
            }
        }
        clearGame();
        return boardGroup;
    }
}