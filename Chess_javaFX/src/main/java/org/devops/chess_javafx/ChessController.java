package org.devops.chess_javafx;

import Pieces.*;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChessController {
    @FXML
    private GridPane ChessboardGrid;
    private PlayerColor playerColor = PlayerColor.white;
    enum PlayerColor {
        white,
        black
    }
    Piece selectedPiece = null;

    // Création de l'échiquier
    public void initialize() {
        Paint bisque = Paint.valueOf("BISQUE");
        Paint black = Paint.valueOf("SADDLEBROWN");

        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                // Case de l'échiquier
                Rectangle rectangle = new Rectangle(90, 90);
                switch ((i + j) %2) {
                    case (0):
                        rectangle.setFill(bisque);
                        break;
                    case (1):
                        rectangle.setFill(black);
                        break;
                }

                // Cercle pour montrer un déplacement possible
                Circle circle = new Circle(25);
                circle.setFill(Color.GRAY);
                circle.setCursor(Cursor.HAND);
                circle.setVisible(false);
                circle.setOnMouseClicked(this::OnSelectedPiece);

                StackPane caseTemp = new StackPane();
                caseTemp.getChildren().addAll(rectangle, circle);

                ChessboardGrid.add(caseTemp, i, j);
            }
        }
    }

    public void SetStartingPlayer(boolean IsStarting) {
        List<List<Piece>> Pieces = getPieces();

        if (!IsStarting) {
            Pieces = Pieces.reversed();
        }

        ImageView piece = new ImageView(new Image("Black Pieces/spr_bishop_black.png", 50, 50, false, false));
        piece.setSmooth(false);
        piece.setPreserveRatio(true);

        List<Node> NodeTemp = ChessboardGrid.getChildren();

//        for ()
//
//        caseTemp.getChildren().add(piece);
    }

    private List<List<Piece>> getPieces() {
        List<List<Piece>> Pieces = new ArrayList<>();

        Pieces.add(new ArrayList<Piece>(
                Arrays.asList(
                        new Rook(false),
                        new Knight(false),
                        new Bishop(false),
                        new Queen(false),
                        new King(false),
                        new Bishop(false),
                        new Knight(false),
                        new Rook(false)
                )
        ));
        Pieces.add(new ArrayList<Piece>(
                Arrays.asList(
                        new Pawn(false),
                        new Pawn(false),
                        new Pawn(false),
                        new Pawn(false),
                        new Pawn(false),
                        new Pawn(false),
                        new Pawn(false),
                        new Pawn(false)
                )
        ));
        Pieces.add(new ArrayList<Piece>(
                Arrays.asList(
                        new Pawn(true),
                        new Pawn(true),
                        new Pawn(true),
                        new Pawn(true),
                        new Pawn(true),
                        new Pawn(true),
                        new Pawn(true),
                        new Pawn(true)
                )
        ));
        Pieces.add(new ArrayList<Piece>(
                Arrays.asList(
                        new Rook(true),
                        new Knight(true),
                        new Bishop(true),
                        new Queen(true),
                        new King(true),
                        new Bishop(true),
                        new Knight(true),
                        new Rook(true)
                )
        ));
        return Pieces;
    }

    private ImageView getImage(String str) {
        return new ImageView(new Image("Black Pieces/spr_bishop_black.png", 50, 50, false, false));
    }

    public void setStartingPlayer(boolean isUserStarting) {
        if(isUserStarting) {
            playerColor = PlayerColor.white;
        }
        else {
            playerColor = PlayerColor.black;
        }
    }

    private void OnSelectedPiece(javafx.scene.input.MouseEvent event) {
    }

    @FXML
    protected void onAbandonButtonClic(javafx.scene.input.MouseEvent event) {
        ChessboardGrid.getChildren().clear();
    }
}
