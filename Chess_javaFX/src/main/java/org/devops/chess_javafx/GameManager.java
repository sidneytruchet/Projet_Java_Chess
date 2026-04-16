package org.devops.chess_javafx;

import Pieces.King;
import Pieces.Piece;

public class GameManager {
    public Piece[][] plateauLogique;
    private boolean isWhiteTurn = true;

    public GameManager() {
        plateauLogique = new Piece[8][8];
    }

    public boolean isWhiteTurn() {
        return isWhiteTurn;
    }

    public void enregistrerPiece(int row, int col, Piece piece) {
        plateauLogique[row][col] = piece;
    }

    public boolean tenterUnCoup(int startR, int startC, int endR, int endC) {
        Piece piece = plateauLogique[startR][startC];

        if (piece == null || piece.IsPieceWhite() != isWhiteTurn) return false;


        Piece pieceCible = plateauLogique[endR][endC];
        if (pieceCible != null && pieceCible.IsPieceWhite() == isWhiteTurn) return false;

        if (!piece.estMouvementValide(startR, startC, endR, endC, plateauLogique)) return false;

        // SIMULATION
        Piece pieceMangee = plateauLogique[endR][endC];
        plateauLogique[endR][endC] = piece;
        plateauLogique[startR][startC] = null;

        if (estEnEchec(isWhiteTurn)) {
            plateauLogique[startR][startC] = piece;
            plateauLogique[endR][endC] = pieceMangee;
            return false;
        }

        isWhiteTurn = !isWhiteTurn;
        return true;
    }

    public void forcerCoupReseau(int startR, int startC, int endR, int endC) {
        Piece piece = plateauLogique[startR][startC];
        plateauLogique[endR][endC] = piece;
        plateauLogique[startR][startC] = null;
        isWhiteTurn = !isWhiteTurn;
    }

    public boolean estEnEchec(boolean verifierBlanc) {
        int roiR = -1, roiC = -1;

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = plateauLogique[r][c];
                if (p instanceof King && p.IsPieceWhite() == verifierBlanc) {
                    roiR = r; roiC = c;
                }
            }
        }
        if (roiR == -1) return false;

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece ennemi = plateauLogique[r][c];
                if (ennemi != null && ennemi.IsPieceWhite() != verifierBlanc) {
                    if (ennemi.estMouvementValide(r, c, roiR, roiC, plateauLogique)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean estEchecEtMat(boolean verifierBlanc) {
        if (!estEnEchec(verifierBlanc)) return false;

        for (int startR = 0; startR < 8; startR++) {
            for (int startC = 0; startC < 8; startC++) {
                Piece allie = plateauLogique[startR][startC];

                if (allie != null && allie.IsPieceWhite() == verifierBlanc) {
                    for (int endR = 0; endR < 8; endR++) {
                        for (int endC = 0; endC < 8; endC++) {


                            Piece caseCible = plateauLogique[endR][endC];
                            if (caseCible != null && caseCible.IsPieceWhite() == verifierBlanc) continue;

                            if (allie.estMouvementValide(startR, startC, endR, endC, plateauLogique)) {
                                Piece pieceMangee = plateauLogique[endR][endC];
                                plateauLogique[endR][endC] = allie;
                                plateauLogique[startR][startC] = null;

                                boolean toujoursEnEchec = estEnEchec(verifierBlanc);

                                plateauLogique[startR][startC] = allie;
                                plateauLogique[endR][endC] = pieceMangee;

                                if (!toujoursEnEchec) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}