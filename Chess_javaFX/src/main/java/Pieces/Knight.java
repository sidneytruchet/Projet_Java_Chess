package Pieces;

public class Knight extends Piece {
    public Knight(boolean IsTeamWhite) {
        super(IsTeamWhite);
    }

    @Override
    public boolean estMouvementValide(int startRow, int startCol, int endRow, int endCol, Piece[][] plateau) {
        int diffRow = Math.abs(startRow - endRow);
        int diffCol = Math.abs(startCol - endCol);

        // Il avance de 2 et tourne de 1, OU avance de 1 et tourne de 2
        return (diffRow == 2 && diffCol == 1) || (diffRow == 1 && diffCol == 2);
    }
}