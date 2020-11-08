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
    private boolean
        playing = false;
    private int
        xstart = 10,
        ystart = 10,
        rows = 14,
        cols = 11,
        len = 45,
        rate = 50;

    static int row = 0;
    static int col = 0;

    public int getYOffset(Color[][] piece) { //height
        int len = piece.length;
        for(int i = len-1; i >= len/2; i--)
            for(int j = 0; j < piece[0].length; j++)
                if(piece[i][j] != null)
                    return i-(len/2);
        return piece.length-1;
    }
    public boolean canMoveDown(int x, int y, Color[][] piece) {
        int yOff = getYOffset(piece);
        //look ahead for all columns
        for(int i = 0; i < piece[0].length; i++) {
            try {
            if (y + yOff >= cols) //check height
                return false;
            else if (board[i][y+1].getFill() != null) //check colors
                    return false;
            } catch(Exception ex) { }
        }
        return true;
    }

    private int
        dx = 0, //-1 <= dx <= 1
        dy = 0; // 0 <= dy <= 1
    public void updatePiece(int x, int y, Color[][] piece) {
        dy = 1;
        x -= 2;
        y -= 2;
        //clear piece's previous position
        for(int i = 0; i < piece[0].length; i++) {
            for (int j = 0; j < piece.length; j++) {
                try {
                    board[i+y-dy][j+x-dx].setFill(null);
                }catch(Exception ex) { }
            }
        }
        //update piece
        for(int i = 0; i < piece[0].length; i++) {
            for(int j = 0; j < piece.length; j++) {
                try {//attempt to paint current position
                    if(piece[i][j] != null)
                        board[i+y][j+x].setFill(piece[i][j]);
                }catch(Exception ex) { }
            }
        }
    }
    public void playPiece() {
        if(playing) return;
        playing = true;
        //gets copy from the next piece section
        Color[][] piece = next.load();
        next.generated = false;
        loadNextPiece();
        int
            yOff = getYOffset(piece),
            x = (cols/2),
            y = 0 - yOff, //starting point
            limit = cols,
            moves = 0,
            temp = rate;
        rate = 250;
        //start from top
        while(moves<rows) { //canMoveDown(x, y, piece)) {
            sleep();
            updatePiece(x, y, piece);
            y++;
            moves++;
        }
        rate = temp;
        playing = false;
        if(moves == 0) {
            //gameOver();
            stop();
        }
    }
    public void loadNextPiece() {
        //involve some logic such that when current playable piece is played, to then copy, and update next piece
        if(next.generated) return;
        //generates next playable piece
        next.generate();
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
                playPiece();
                //loadNextPiece();
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void sleep() {
        try { Thread.sleep(rate); } catch(Exception ex) { }
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