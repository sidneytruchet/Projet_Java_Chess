package Pieces;

import javafx.scene.image.ImageView;
import java.io.Serializable;

public abstract class Piece extends ImageView implements Serializable {
    boolean isTeamWhite = true;

    public Piece(boolean IsTeamWhite) {
        this.isTeamWhite = IsTeamWhite;
    }

    public boolean IsPieceWhite() {
        return isTeamWhite;
    }

    // Méthode abstraite que chaque pièce (Pion, Cavalier...) remplira
    public abstract boolean estMouvementValide(int startRow, int startCol, int endRow, int endCol, Piece[][] plateau);


    protected boolean estCheminLibre(int startRow, int startCol, int endRow, int endCol, Piece[][] plateau) {
        int diffRow = Integer.compare(endRow, startRow); // Donne 1, -1 ou 0
        int diffCol = Integer.compare(endCol, startCol); // Donne 1, -1 ou 0

        int currentRow = startRow + diffRow;
        int currentCol = startCol + diffCol;


        while (currentRow != endRow || currentCol != endCol) {
            if (plateau[currentRow][currentCol] != null) {
                return false; // Obstacle trouvé !
            }
            currentRow += diffRow;
            currentCol += diffCol;
        }
        return true;
    }
}