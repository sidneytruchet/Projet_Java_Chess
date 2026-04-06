package Pieces;

public class Queen extends Piece {
    public Queen(boolean IsTeamWhite) {
        super(IsTeamWhite);
    }

    @Override
    public boolean estMouvementValide(int startRow, int startCol, int endRow, int endCol, Piece[][] plateau) {
        boolean ligneDroite = (startRow == endRow || startCol == endCol);
        boolean diagonale = Math.abs(startRow - endRow) == Math.abs(startCol - endCol);


        if (!ligneDroite && !diagonale) {
            return false;
        }


        return estCheminLibre(startRow, startCol, endRow, endCol, plateau);
    }
}