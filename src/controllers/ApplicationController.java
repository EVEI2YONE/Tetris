package controllers;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import models.NextPieceSection;
import models.Tetris;

public class ApplicationController {

    Tetris tetris = new Tetris();
    NextPieceSection piece = new NextPieceSection();
    @FXML
    Pane game;
    @FXML
    AnchorPane nextPiece;
    @FXML
    Label scoreLabel;
    @FXML
    ListView stats;

    public void initOtherSections(){
        scoreLabel.setAlignment(Pos.CENTER);
        scoreLabel.setText("0");
        //TODO: setup stats section
    }
    public Group initBoard() {
        return tetris.initGame();
    }
    public Group initNextPiece() {
        tetris.setNextPieceSection(piece);
        Group nextSection = piece.initSection();
        piece.generate();
        return nextSection;
    }

    public void onKeyPressed(KeyEvent event) {
        switch(event.getCode()) {
            case A: tetris.shift(-1); break;
            case D: tetris.shift(1); break;
        }
    }

    public void onKeyReleased(KeyEvent event) {
        switch(event.getCode()) {
            case K: tetris.start(); break;
            case R: tetris.stop(); break;
            case W: tetris.rotate(1); break;
//            case A: tetris.shift(-1); break;
            case S: tetris.rotate(-1); break;
//            case D: tetris.shift(1); break;
            case SPACE: tetris.quickMove(); break;
        }
        System.out.println(event.getCode());
        event.consume();
    }
}
