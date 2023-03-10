package gui.board;

import chess.board.Board;
import chess.board.BoardWrapper;
import chess.pieces.Piece;
import data.annotations.FieldAnnotation;
import data.annotations.GraphicAnnotation;
import data.model.Diagram;
import gui.DisplayConfiguration;
import gui.scaling.CenteredScaledArrow;
import gui.scaling.ScaledArrow;
import log.Log;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

import static java.lang.Math.min;

public class BoardPanel extends JPanel {
    private int scale = 60;
    private Diagram diagram;
    private Color colBack = Color.WHITE;
    private Color colWhiteField = Color.WHITE;
    private Color colBlackField = Color.DARK_GRAY;
    private HashMap<Byte, ImageIcon> imageMap;

    public BoardPanel(Diagram diagram) {
        imageMap = DisplayConfiguration.setImageMap();
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
            paintAnnotations(g);
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
        diagram.getBoard().getUtility().getPiecesOfColor(chess.color.Color.white).forEach(piece -> paintPiece(piece, g));
        diagram.getBoard().getUtility().getPiecesOfColor(chess.color.Color.black).forEach(piece -> paintPiece(piece, g));
    }

    private void paintPiece(Piece piece, Graphics g) {
        ImageIcon image = imageMap.get(BoardWrapper.getBoardIdFromPiece(piece));
        g.drawImage(image.getImage(),
                (piece.getPosition().getX() - 1) * scale + partOf(0.1, scale),
                (8 - piece.getPosition().getY()) * scale + partOf(0.1, scale),
                partOf(0.8, scale),
                partOf(0.8, scale),
                null
        );
    }

    private void paintAnnotations(Graphics g) {
        diagram.getAnnotations().getArrowAnnotations().stream()
                .map(arrow -> new ScaledArrow(arrow, scale))
                .map(CenteredScaledArrow::new)
                .forEach(arrow -> paintArrow(arrow, g));

        diagram.getAnnotations().getFieldAnnotations().forEach(field -> paintField(field, g));
    }

    private void paintArrow(CenteredScaledArrow arrow, Graphics g) {
        Log.debug(arrow.toString());
        g.setColor(convertColor(arrow.getColor()));
        g.drawLine(arrow.getStart().getX(), arrow.getStart().getY(),
                arrow.getEnd().getX(), arrow.getEnd().getY());
        drawArrowHead(arrow, g);
    }

    private void drawArrowHead(CenteredScaledArrow line, Graphics g) {
        //TODO
    }

    private void paintField(FieldAnnotation fieldAnnotation, Graphics g) {
        g.setColor(convertColor(fieldAnnotation.getColor()));
        g.drawOval((fieldAnnotation.getX() - 1) * scale, (8 - fieldAnnotation.getY()) * scale, scale, scale);
    }

    private Color convertColor(GraphicAnnotation.DrawColor color) {
        return switch (color) {
            case BLUE -> Color.blue;
            case RED -> Color.red;
            case GREEN -> Color.green;
            case YELLOW -> Color.yellow;
        };
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
