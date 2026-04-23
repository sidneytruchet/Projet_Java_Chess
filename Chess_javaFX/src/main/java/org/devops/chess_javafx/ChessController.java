package org.devops.chess_javafx;

import Communication.ChessClient;
import Pieces.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class ChessController {
    @FXML
    public TextField chatInput;
    @FXML
    private TextArea chatDisplay;

    @FXML
    private GridPane ChessboardGrid;
    private StackPane[][] ChessboardRef = new StackPane[8][8];
    private ChessClient client;

    private PlayerColor playerColor = PlayerColor.white;
    enum PlayerColor { white, black }

    private Piece selectedPiece = null;
    private int selectedRow = -1;
    private int selectedCol = -1;
    private boolean partieTerminee = false;

    private GameManager gameManager;

    public void initialize() {
        gameManager = new GameManager();
        chatDisplay.setScrollTop(Double.MAX_VALUE);
    }

    public void assignerClient(ChessClient client) {
        this.client = client;
    }

    public void setStartingPlayer(boolean isWhite) {
        playerColor = isWhite ? PlayerColor.white : PlayerColor.black;
        construirePlateauVisuel();
    }

    private void construirePlateauVisuel() {
        ChessboardGrid.getChildren().clear();
        Paint lightColor = Paint.valueOf("#ebecd0");
        Paint darkColor = Paint.valueOf("#739552");

        for (int logR = 0; logR < 8; logR++) {
            for (int logC = 0; logC < 8; logC++) {

                int visR = (playerColor == PlayerColor.white) ? logR : 7 - logR;
                int visC = (playerColor == PlayerColor.white) ? logC : 7 - logC;

                Rectangle rectangle = new Rectangle(75, 75);
                switch ((visR + visC) % 2) {
                    case 0: rectangle.setFill(lightColor); break;
                    case 1: rectangle.setFill(darkColor); break;
                }

                Circle circle = new Circle(20);
                circle.setFill(Color.rgb(0, 0, 0, 0.3));
                circle.setVisible(false);

                StackPane caseTemp = new StackPane();
                caseTemp.getChildren().addAll(rectangle, circle);

                Color textColor = ((visR + visC) % 2 == 0) ? Color.valueOf("#739552") : Color.valueOf("#ebecd0");


                if (visC == 0) {
                    Text rankText = new Text(String.valueOf(8 - logR));
                    rankText.setFont(Font.font("System", FontWeight.BOLD, 12));
                    rankText.setFill(textColor);
                    StackPane.setAlignment(rankText, Pos.TOP_LEFT);
                    StackPane.setMargin(rankText, new Insets(3, 0, 0, 3)); // Petite marge pour aérer
                    caseTemp.getChildren().add(rankText);
                }


                if (visR == 7) {
                    Text fileText = new Text(String.valueOf((char)('a' + logC)));
                    fileText.setFont(Font.font("System", FontWeight.BOLD, 12));
                    fileText.setFill(textColor);
                    StackPane.setAlignment(fileText, Pos.BOTTOM_RIGHT);
                    StackPane.setMargin(fileText, new Insets(0, 3, 3, 0));
                    caseTemp.getChildren().add(fileText);
                }


                configurerInteractions(caseTemp, logR, logC);

                ChessboardRef[logR][logC] = caseTemp;
                ChessboardGrid.add(caseTemp, visC, visR);
            }
        }
    }

    public void SetPieces(boolean IsStarting) {
        Piece[][] piecesLogiques = genererPiecesLogiques();
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece piece = piecesLogiques[r][c];
                if (piece != null) {
                    gameManager.enregistrerPiece(r, c, piece);
                    ImageView pieceImage = getImage(piece);
                    if (pieceImage != null) {
                        pieceImage.setCursor(Cursor.OPEN_HAND);
                        ChessboardRef[r][c].getChildren().add(pieceImage);
                    }
                }
            }
        }
    }

    private Piece[][] genererPiecesLogiques() {
        Piece[][] p = new Piece[8][8];
        p[0] = new Piece[]{new Rook(false), new Knight(false), new Bishop(false), new Queen(false), new King(false), new Bishop(false), new Knight(false), new Rook(false)};
        for(int i=0; i<8; i++) p[1][i] = new Pawn(false);
        for(int i=0; i<8; i++) p[6][i] = new Pawn(true);
        p[7] = new Piece[]{new Rook(true), new Knight(true), new Bishop(true), new Queen(true), new King(true), new Bishop(true), new Knight(true), new Rook(true)};
        return p;
    }

    private void configurerInteractions(StackPane caseTemp, int logR, int logC) {

        caseTemp.setOnMouseClicked(event -> gererClicCase(logR, logC));

        caseTemp.setOnDragDetected(event -> {
            if (partieTerminee) return;
            Piece piece = obtenirPieceVisuelle(logR, logC);

            if (piece == null || piece.IsPieceWhite() != gameManager.isWhiteTurn()) return;

            if ((playerColor == PlayerColor.white && !gameManager.isWhiteTurn()) ||
                    (playerColor == PlayerColor.black && gameManager.isWhiteTurn())) return;

            Dragboard db = caseTemp.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(logR + "," + logC);
            db.setContent(content);

            db.setDragView(piece.getImage(), 30, 30);
            piece.setVisible(false);

            selectedPiece = piece;
            selectedRow = logR;
            selectedCol = logC;

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    afficherCercleSelection(i, j, false);
                    if (selectedPiece.estMouvementValide(logR, logC, i, j, gameManager.plateauLogique)) {
                        // Savoir si pièce de la même équipe sans tester sur valeur nulle (erreur)
                        if (gameManager.plateauLogique[i][j] != null) {
                            if (selectedPiece.IsPieceWhite() == gameManager.plateauLogique[i][j].IsPieceWhite()) {
                                continue;
                            }
                        }
                        afficherCercleSelection(i, j, true);
                    }
                }
            }
            event.consume();
        });

        caseTemp.setOnDragOver(event -> {
            if (event.getGestureSource() != caseTemp && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        caseTemp.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;

            if (db.hasString()) {
                String[] coords = db.getString().split(",");
                int startR = Integer.parseInt(coords[0]);
                int startC = Integer.parseInt(coords[1]);

                if (gameManager.tenterUnCoup(startR, startC, logR, logC)) {
                    deplacerPieceVisuellement(startR, startC, logR, logC);

                    String commandeCoup = "MOVE:" + startR + "," + startC + ":" + logR + "," + logC;

                    if (client != null) {
                        client.envoyerCoup(commandeCoup);
                    }


                    if (gameManager.estEchecEtMat(gameManager.isWhiteTurn())) {
                        String gagnant = !gameManager.isWhiteTurn() ? "Blancs" : "Noirs";
                        declencherVictoire(gagnant);
                    }
                    success = true;
                }
                afficherCercleSelection(startR, startC, false);
            }

            selectedPiece = null;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    afficherCercleSelection(i, j, false);
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });

        caseTemp.setOnDragDone(event -> {
            Piece piece = obtenirPieceVisuelle(logR, logC);
            if (piece != null) piece.setVisible(true);
            event.consume();
        });
    }

    private void gererClicCase(int logR, int logC) {
        if (partieTerminee) return;

        boolean cEstMonTour = (playerColor == PlayerColor.white && gameManager.isWhiteTurn()) ||
                (playerColor == PlayerColor.black && !gameManager.isWhiteTurn());

        if (!cEstMonTour) return;

        Piece pieceCliquee = obtenirPieceVisuelle(logR, logC);

        if (selectedPiece == null) {
            if (pieceCliquee == null || pieceCliquee.IsPieceWhite() != gameManager.isWhiteTurn()) return;

            selectedPiece = pieceCliquee;
            selectedRow = logR;
            selectedCol = logC;

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    afficherCercleSelection(i, j, false);
                    if (selectedPiece.estMouvementValide(logR, logC, i, j, gameManager.plateauLogique)) {
                        // Savoir si pièce de la même équipe sans tester sur valeur nulle (erreur)
                        if (gameManager.plateauLogique[i][j] != null) {
                            if (selectedPiece.IsPieceWhite() == gameManager.plateauLogique[i][j].IsPieceWhite()) {
                                continue;
                            }
                        }
                        afficherCercleSelection(i, j, true);
                    }
                }
            }
        } else {
            if (pieceCliquee != null && pieceCliquee.IsPieceWhite() == selectedPiece.IsPieceWhite()) {
                selectedPiece = pieceCliquee;
                selectedRow = logR;
                selectedCol = logC;

                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        afficherCercleSelection(i, j, false);
                        if (selectedPiece.estMouvementValide(logR, logC, i, j, gameManager.plateauLogique)) {
                            // Savoir si pièce de la même équipe sans tester sur valeur nulle (erreur)
                            if (gameManager.plateauLogique[i][j] != null) {
                                if (selectedPiece.IsPieceWhite() == gameManager.plateauLogique[i][j].IsPieceWhite()) {
                                    continue;
                                }
                            }
                            afficherCercleSelection(i, j, true);
                        }
                    }
                }
            } else {
                if (gameManager.tenterUnCoup(selectedRow, selectedCol, logR, logC)) {
                    deplacerPieceVisuellement(selectedRow, selectedCol, logR, logC);

                    String commandeCoup = "MOVE:" + selectedRow + "," + selectedCol + ":" + logR + "," + logC;

                    if (client != null) {
                        client.envoyerCoup(commandeCoup);
                    }


                    if (gameManager.estEchecEtMat(gameManager.isWhiteTurn())) {
                        String gagnant = !gameManager.isWhiteTurn() ? "Blancs" : "Noirs";
                        declencherVictoire(gagnant);
                    }
                }
                selectedPiece = null;
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        afficherCercleSelection(i, j, false);
                    }
                }
            }
        }
    }

    public void recevoirCoupAdverse(int startRow, int startCol, int endRow, int endCol) {
        gameManager.forcerCoupReseau(startRow, startCol, endRow, endCol);
        deplacerPieceVisuellement(startRow, startCol, endRow, endCol);

        if (gameManager.estEchecEtMat(gameManager.isWhiteTurn())) {
            String gagnant = !gameManager.isWhiteTurn() ? "Blancs" : "Noirs";
            declencherVictoire(gagnant);
        }


    }

    private ImageView getImage(Piece piece) {
        if (piece == null) return null;
        String dossierCouleur = piece.IsPieceWhite() ? "White Pieces" : "Black Pieces";
        String suffixe = piece.IsPieceWhite() ? "_white.png" : "_black.png";
        String nomPiece = piece.getClass().getSimpleName().toLowerCase();
        if (nomPiece.equals("rook")) nomPiece = "rook";

        String cheminImage = "/Assets/" + dossierCouleur + "/spr_" + nomPiece + suffixe;
        try {
            Image image = new Image(getClass().getResourceAsStream(cheminImage));
            piece.setImage(image);
            piece.setFitWidth(60);
            piece.setFitHeight(60);
            return piece;
        } catch (Exception e) {
            return null;
        }
    }

    private Piece obtenirPieceVisuelle(int row, int col) {
        StackPane caseCible = ChessboardRef[row][col];
        for (Node node : caseCible.getChildren()) {
            if (node instanceof Piece) return (Piece) node;
        }
        return null;
    }

    private void afficherCercleSelection(int row, int col, boolean visible) {
        StackPane caseCible = ChessboardRef[row][col];
        for (Node node : caseCible.getChildren()) {
            if (node instanceof Circle) node.setVisible(visible);
        }
    }

    private void deplacerPieceVisuellement(int startRow, int startCol, int endRow, int endCol) {
        StackPane caseDepart = ChessboardRef[startRow][startCol];
        StackPane caseArrivee = ChessboardRef[endRow][endCol];
        Piece pieceABouger = obtenirPieceVisuelle(startRow, startCol);

        if (pieceABouger != null) {
            pieceABouger.setVisible(true);
            Piece pieceAdverse = obtenirPieceVisuelle(endRow, endCol);
            if (pieceAdverse != null) caseArrivee.getChildren().remove(pieceAdverse);

            caseDepart.getChildren().remove(pieceABouger);
            caseArrivee.getChildren().add(pieceABouger);
        }
    }

    public void recevoirAbandon() {
        partieTerminee = true;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Victoire !");
        alert.setHeaderText("L'adversaire a abandonné.");
        alert.setContentText("Vous remportez la partie par forfait !");
        alert.show();
    }

    private void declencherVictoire(String gagnant) {
        partieTerminee = true;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fin de partie");
        alert.setHeaderText("Échec et Mat !");
        alert.setContentText("Les " + gagnant + " ont gagné la partie !");
        alert.show();
    }

    @FXML private void envoyerMessage() {
        String msg = chatInput.getText();
        if (msg.isEmpty()) return;
        recevoirMessage("Moi : " + msg);
        if (client != null) client.envoyerCoup("CHAT:" + msg);
        chatInput.clear();
    }
    public void recevoirMessage(String msg) {
        if (msg.startsWith("CHAT:")) {

            chatDisplay.appendText("Adversaire : " + msg.substring(5) + "\n");
        } else {

            chatDisplay.appendText(msg + "\n");
        }
        chatDisplay.setScrollTop(Double.MAX_VALUE);
    }

    @FXML
    protected void onAbandonButtonClic(javafx.scene.input.MouseEvent event) {
        if (partieTerminee) return;

        if (client != null) client.envoyerCoup("SURRENDER");

        partieTerminee = true;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Abandon");
        alert.setHeaderText("Vous avez abandonné !");
        alert.setContentText("L'adversaire remporte la partie.");
        alert.show();
    }
}