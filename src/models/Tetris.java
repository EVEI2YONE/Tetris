package models;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Tetris {
    Random random = PieceFactory.rng;
    NextPieceSection next;
    Rectangle[][] board;
    Piece piece, prevPiece;

    public static boolean
        running = false;
    private boolean
        playing = false,
        move = false,
        update = false,
        quickMove = false,
        rotated = false,
        shifted = false;
    private int
        xstart = 10,
        ystart = 10,
        rows = 14,
        cols = 11,
        len = 45,
        regular = 125,
        quick = 5,
        rate = regular;

    public void rotate(int i) {
        rotated = true;
    }
    public void shift(int i) {
        if(!canMoveAcross(i)) return;
        shifted = true;
        piece.x += i;
    }

    public void quickMove() {
        if(quickMove) return;
        rate = quick;
        quickMove = true;
    }

    public int getYOffset(Piece piece) { //height
        Color[][] shape = piece.getShape();
        int len = shape.length;
        for(int i = len-1; i >= 0; i--)
            for(int j = 0; j < shape[0].length; j++)
                if(shape[i][j] != null)
                    return i-(len/2);
        return shape.length-1;
    }
    public boolean inXBounds(int offset) {
        Color[][] shape = piece.getShape();
        int
            x = piece.x-2+offset,
            xLeft = 0, xRight = shape[0].length-1;
        //find xOffset left-side
        boolean flag = false;
        for(int i = 0; i < shape[0].length; i++) {
            if(flag) break;
            for(int j = 0; j < shape.length; j++) {
                if(shape[j][i] != null) {
                    xLeft = i;
                    flag = true;
                    break;
                }
            }
        }
        //find xOffset right-side
        flag = false;
        for(int i = shape[0].length-1; i >= shape.length/2; i--) {
            if(flag) break;
            for(int j = 0; j < shape.length; j++) {
                if(shape[j][i] != null) {
                    xRight = i;
                    flag = true;
                    break;
                }
            }
        }
        //check xOffsets
        if(x+xLeft < 0) return false;
        if(x+xRight >= board[0].length) return false;
        return true;
    }
    public boolean overflow(){
        int yHeight = 0;
        Color[][] shape = piece.getShape();
        boolean flag = false;
        try {
            for (int i = 0; i < shape.length; i++) {
                if (flag) break;
                for (int j = 0; j < shape[0].length; j++) {
                    if (shape[i][j] != null) {
                        yHeight = i;
                        flag = true;
                        break;
                    }
                }
            }
        }catch(Exception ex) { ex.printStackTrace(); }
        int height = piece.y-yHeight;
        if(height >= 0) return false;
        return true;
    }
    public boolean canShift(int dx){
        int
            x = piece.x-2,
            y = piece.y-2;
        Color[][] shape = piece.getShape();
        for(int i = 0; i < shape.length; i++) {
            for(int j = 0; j < shape[0].length; j++) {
                try {
                    int xPos = x + j, yPos = y + i;
                    Color
                            currentColor = shape[i][j],
                            nextColor = (Color) board[yPos][xPos + dx].getFill();
                    //ignore empty spaces
                    if (currentColor == null) continue;
                    //prevent seeing parts of its own piece
                    if (currentColor == nextColor) continue;
                    //checks if next spot is open -> return false on first instance
                    if (nextColor != null)
                        return false;
                }catch(Exception ex) { }
            }
        }
        return true;
    }
    public boolean canMoveAcross(int dir) { //check left and right
        if(!inXBounds(dir)) return false; //check bounds
        if(canShift(dir)) return true;
        return false;
    }
    public boolean canMoveDown() { //check below
        int
            x = piece.x-2,
            y = piece.y-2;
        //check height bounds
        int yOff = getYOffset(piece);
        if((y+2)+yOff >= rows-1) return false;
        //look ahead for all columns -> collision detection
        Color[][]shape = piece.getShape();
        for(int i = 0; i < shape.length; i++) {
            for(int j = 0; j < shape[0].length; j++) {
                try {
                    int xPos = x+j, yPos = y+i;
                    Color currentColor = shape[i][j],
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
        return true;
    }
    /*TODO: FIX PIECE MOVING DOWN
            CHECK BOUNDS OF THE PIECE (X RANGE and Y MAX)
            ROTATION (LEFT AND RIGHT) -> UPDATING ROTATED PIECE
            SHIFT PIECE(LEFT AND RIGHT) -> UPDATING ROATED PIECE

      TODO: IMPLEMENT SCORE SYSTEM -> UPDATE SCORE LABEL
     */

    public void updateChange() {
        if(shifted) {
            prevPiece = piece.copy();
            shifted = false;
        }
        if(rotated) {

            rotated = false;
        }
    }
    public void updatePiece() {
        int
            x = piece.x-2,
            y = piece.y-2;
        Color[][] shape = piece.getShape();
        for(int i = 0; i < shape.length; i++) {
            for(int j = 0; j < shape[0].length; j++) {
                int xPos = x+j, yPos = y+i;
                if(shape[i][j] == null) continue;
                try {
                    board[yPos][xPos].setFill(shape[i][j]);
                }catch(Exception ex) {}
            }
        }
        update = false;
    }
    public void clearPiece() {
        if(prevPiece == null) {
            update = true;
            return;
        }
        int
            x = prevPiece.x-2,
            y = prevPiece.y-2;
        //TODO: FIX UPDATING PIECE'S PREVIOUS POSITION (OVERWRITES FILL)
        Color[][] shape = prevPiece.getShape();
        //clear piece's previous position
        for(int i = 0; i < shape.length; i++) {
            for(int j = 0; j < shape[0].length; j++) {
                int xPos = x+j, yPos = y+i;
                if(shape[i][j] == null) continue;
                //if(board[xPos][yPos].getFill() != piece[i][j]) continue;
                try {
                    board[yPos][xPos].setFill(null);
                }catch(Exception ex) {}
            }
        }
        update = true;
    }
    public void movePiece() {
        update = false;
        sleep();
        Thread clear = new Thread(() -> clearPiece());
        clear.start();
        while(!update && running) { }
        updateChange();
        updatePiece();
    }
    public void loadPieces() {
        piece = next.load();
        next.generate();
        int
            yOff = getYOffset(piece),
            x = random.nextInt(cols-2)+1,//(cols/2),
            y = 0 - yOff -1; //starting point <- -1 is for the initial increment from the clock
        piece.x = x;
        piece.y = y;
        prevPiece = piece.copy();
        prevPiece.y--;
    }
    public void playPiece() {
        if(playing) return;
        playing = true;
        //gets copy from the next piece section
        loadPieces();
        //start from top
        while(canMoveDown() || shifted) {// && inBounds(x, y, piece)) {
            movePiece();
            prevPiece.y += (piece.y - prevPiece.y);
            prevPiece.x += (piece.x - prevPiece.x);
            if(!shifted)
                piece.y++;
        }
        movePiece();
        playing = false;
        if(overflow()) {
            stop();
        }
    }
    public void run() {
        try {
            while (running) {
                quickMove = false;
                rate = regular;
                playPiece();
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