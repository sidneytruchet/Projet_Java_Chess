package Pieces;

public class Rook extends Piece {

    public Rook(boolean IsTeamWhite) {
        super(IsTeamWhite);
    }

    @Override
    public boolean estMouvementValide(int startRow, int startCol, int endRow, int endCol, Piece[][] plateau) {


        if (startRow != endRow && startCol != endCol) {
            return false; // Mouvement en diagonale ou tordu interdit !
        }


        return true;
    }
}