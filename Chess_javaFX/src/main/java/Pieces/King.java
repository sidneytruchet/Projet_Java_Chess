package Pieces;

public class King extends Piece {
    public King(boolean IsTeamWhite) {
        super(IsTeamWhite);
    }

    @Override
    public boolean estMouvementValide(int startRow, int startCol, int endRow, int endCol, Piece[][] plateau) {
        int diffRow = Math.abs(startRow - endRow);
        int diffCol = Math.abs(startCol - endCol);

        // 1 case maximum dans n'importe quelle direction
        return diffRow <= 1 && diffCol <= 1;
    }
}