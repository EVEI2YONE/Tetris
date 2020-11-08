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

    int count;
    public int getYOffset(Color[][] piece) { //height
        int len = piece.length;
        for(int i = len-1; i >= 0; i--)
            for(int j = 0; j < piece[0].length; j++)
                if(piece[i][j] != null)
                    return i-(len/2);
        return piece.length-1;
    }
    public boolean inBounds(int x, int y, Color[][] piece) {
        int yOff = getYOffset(piece);
        if(y + yOff >= rows)
            return false;
        int
            len = piece[0].length,
            leftBound = 0, rightBound = 0;
        boolean
            flag = false;
        for(int i = 0; i < len/2; i++) {
            for (int j = 0; j < piece.length; j++)
                if(piece[i][j] != null && leftBound != 0) {
                    leftBound = len/2 - i;
                }
        }

        for(int i = len-1; i > len/2; i--) {

        }
        return true;
    }
    public boolean canMoveDown(int x, int y, Color[][] piece) {
        x -= 2;
        y -= 2;
        int yOff = getYOffset(piece);
        //check height bounds
        if((y+2)+yOff >= rows-1) return false;
        //look ahead for all columns -> collision detection
        for(int i = 0; i < piece.length; i++) {
            for(int j = 0; j < piece[0].length; j++) {
                try {
                    int xPos = x+j, yPos = y+i;
                    Color currentColor = piece[i][j],
                        nextColor = (Color) board[yPos+1][xPos].getFill();
                    //ignore empty spaces
                    if(currentColor == null) continue;
                    //prevent seeing parts of its own piece
                    if (currentColor == nextColor) continue;
                    //checks if next spot is open -> return false on first instance
                    if (nextColor != null)
                        return false;
                } catch (Exception ex) { }
            }
        }
        count++;
        return true;
    }
    /*TODO: FIX PIECE MOVING DOWN
            CHECK BOUNDS OF THE PIECE (X RANGE and Y MAX)
            ROTATION (LEFT AND RIGHT) -> UPDATING ROTATED PIECE
            SHIFT PIECE(LEFT AND RIGHT) -> UPDATING ROATED PIECE

      TODO: IMPLEMENT SCORE SYSTEM -> UPDATE SCORE LABEL
     */

    private int
        dx = 0, //-1 <= dx <= 1
        dy = 0; // 0 <= dy <= 1
    public void clearPiece(int x, int y, Color[][] piece) {
        x -= 2;
        y -= 2;
        //TODO: FIX UPDATING PIECE'S PREVIOUS POSITION (OVERWRITES FILL)
        //clear piece's previous position
        for(int i = 0; i < piece.length; i++) {
            for(int j = 0; j < piece[0].length; j++) {
                int xPos = x+j, yPos = y+i;
                if(piece[i][j] == null) continue;
                //if(board[xPos][yPos].getFill() != piece[i][j]) continue;
                try {
                    board[yPos - dy][xPos - dx].setFill(null);
                }catch(Exception ex) {}
            }
        }
    }
    public void updatePiece(int x, int y, Color[][] piece) {
        x -= 2;
        y -= 2;
        for(int i = 0; i < piece.length; i++) {
            for(int j = 0; j < piece[0].length; j++) {
                int xPos = x+j, yPos = y+i;
                if(piece[i][j] == null) continue;
                try {
                    board[yPos][xPos].setFill(piece[i][j]);
                }catch(Exception ex) {}
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
            x = random.nextInt(cols-2)+1,//(cols/2),
            y = 0 - yOff, //starting point
            moves = 0,
            temp = rate;
        rate = 150;//250;
        //start from top
        while(canMoveDown(x, y, piece)) {// && inBounds(x, y, piece)) {
            dy = 1;
            sleep();
            if(dx != 0) //consider user moving piece left/right
                dy = 0;
            clearPiece(x, y, piece);
            updatePiece(x, y, piece);
            y++;
            moves++;
        }
        sleep();
        clearPiece(x, y, piece);
        updatePiece(x, y, piece);

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