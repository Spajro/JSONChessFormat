package gui;

import chess.board.Board;
import chess.board.BoardWrapper;
import chess.hlp.Translator;
import chess.pieces.Piece;
import data.model.Diagram;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

import static java.lang.Math.min;

public class BoardPanel extends JPanel {
    private int scale = 60;
    private Diagram diagram;
    private Color colBack = Color.WHITE;
    private Color colWhiteField = Color.WHITE;
    private Color colBlackField = Color.BLACK;
    private HashMap<Integer, ImageIcon> imageMap;

    public BoardPanel(Diagram diagram) {
        imageMap=DisplayConfiguration.setImageMap();
        this.diagram = diagram;
    }

    @Override
    public void paint(Graphics g) {
        scale = getScale();
        g.setFont(new Font("TimesRoman", Font.PLAIN, partOf(0.25, scale)));
        g.setColor(colBack);
        g.fillRect(0, 0, getWidth(), getHeight());
        if (diagram != null) {
            paintBoard(g);
            paintPieces(g);
        }
    }

    private void paintBoard(Graphics g) {
        boolean nextColor = true;
        for (int y = 0; y < Board.SIZE; y++) {
            for (int x = 0; x < Board.SIZE; x++) {
                if (nextColor) {
                    g.setColor(colWhiteField);
                } else {
                    g.setColor(colBlackField);
                }
                g.fillRect(x * scale, y * scale, scale, scale);
                nextColor = !nextColor;
            }
            nextColor = !nextColor;
        }
    }

    private void paintPieces(Graphics g) {
        diagram.getBoard().getPiecesOfColor(chess.color.Color.white).forEach(piece -> paintPiece(piece, g));
        diagram.getBoard().getPiecesOfColor(chess.color.Color.black).forEach(piece -> paintPiece(piece, g));
    }

    private void paintPiece(Piece piece, Graphics g) {
        String s = Translator.numberToFigure(BoardWrapper.getBoardIdFromPiece(piece));
        assert s != null;
        g.setColor(Color.green);
        g.drawString(s, (piece.getPosition().getX()-1) * scale + partOf(0.5, scale), (piece.getPosition().getY()-1) * scale + partOf(0.5, scale));
    }

    public void setDiagram(Diagram diagram) {
        this.diagram = diagram;
        this.repaint();
    }

    public int getScale() {
        int px = getWidth() / Board.SIZE;
        int py = getHeight() / Board.SIZE;
        return min(px, py);
    }

    public int partOf(double a, int b) {
        return (int) (a * b);
    }
}
