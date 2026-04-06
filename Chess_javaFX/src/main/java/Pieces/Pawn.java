package Pieces;

public class Pawn extends Piece {
    public Pawn(boolean IsTeamWhite) {
        super(IsTeamWhite);
    }

    @Override
    public boolean estMouvementValide(int startRow, int startCol, int endRow, int endCol, Piece[][] plateau) {
        // Le sens dépend de la couleur (Les blancs montent vers 0, les noirs descendent vers 7)
        int direction = IsPieceWhite() ? -1 : 1;
        int ligneDepart = IsPieceWhite() ? 6 : 1;

        // CAS 1 : Avancer d'une case tout droit (la case cible doit être vide)
        if (startCol == endCol && endRow == startRow + direction && plateau[endRow][endCol] == null) {
            return true;
        }

        // CAS 2 : Avancer de deux cases (uniquement si c'est son premier mouvement et chemin vide)
        if (startCol == endCol && startRow == ligneDepart && endRow == startRow + (2 * direction)) {
            if (plateau[startRow + direction][startCol] == null && plateau[endRow][endCol] == null) {
                return true;
            }
        }

        // CAS 3 : Manger en diagonale (il DOIT y avoir une pièce sur la case cible)
        if (Math.abs(startCol - endCol) == 1 && endRow == startRow + direction) {
            if (plateau[endRow][endCol] != null) {
                return true;
            }
        }

        return false;
    }
}