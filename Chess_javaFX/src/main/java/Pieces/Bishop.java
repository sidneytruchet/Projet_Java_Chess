package Pieces;

public class Bishop extends Piece {
    public Bishop(boolean IsTeamWhite) {
        super(IsTeamWhite);
    }

    @Override
    public boolean estMouvementValide(int startRow, int startCol, int endRow, int endCol, Piece[][] plateau) {

        if (Math.abs(startRow - endRow) != Math.abs(startCol - endCol)) {
            return false;
        }


        return estCheminLibre(startRow, startCol, endRow, endCol, plateau);
    }
}