package gui.board;

import chess.Position;
import chess.board.lowlevel.Board;
import chess.board.features.ChessBoardCoverageAnalyzer;
import chess.board.features.ChessBoardWeakPointsAnalyzer;
import chess.moves.valid.ValidMove;
import chess.pieces.Piece;
import data.annotations.FieldAnnotation;
import data.annotations.GraphicAnnotation;
import data.model.diagrams.Diagram;
import gui.DisplayConfiguration;
import gui.scaling.*;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

import static java.lang.Math.min;

public class BoardPanel extends JPanel {
    private int scale = 60;
    private Diagram diagram;
    private Color colBack = Color.WHITE;
    private Color colWhiteField = Color.WHITE;
    private Color colBlackField = Color.DARK_GRAY;
    private DisplayConfiguration displayConfiguration;

    private boolean doPaintCoverage = false;
    private boolean doPaintLegalMoves = false;
    private boolean doPaintWeakPoints = false;

    public BoardPanel(Diagram diagram) {
        displayConfiguration = new DisplayConfiguration();
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
            if (doPaintCoverage) {
                paintCoverage(g);
            }
            if (doPaintLegalMoves) {
                paintLegalMoves(g);
            }
            if (doPaintWeakPoints) {
                paintWeakPoints(g);
            }
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
        ImageIcon image = displayConfiguration.getImage(piece);
        g.drawImage(image.getImage(),
                (piece.getPosition().getX() - 1) * scale + partOf(0.1, scale),
                (8 - piece.getPosition().getY()) * scale + partOf(0.1, scale),
                partOf(0.8, scale),
                partOf(0.8, scale),
                null
        );
    }

    private void paintAnnotations(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        ((Graphics2D) g).setStroke(new BasicStroke(4));
        diagram.getAnnotations().getArrowAnnotations().stream()
                .map(arrow -> new ScaledArrow(arrow, scale))
                .map(CenteredScaledArrow::new)
                .forEach(arrow -> paintArrow(arrow, g2d));

        diagram.getAnnotations().getFieldAnnotations().forEach(field -> paintField(field, g2d));
    }

    private void paintArrow(CenteredScaledArrow arrow, Graphics g) {
        Vector vector = arrow.toVector().normalize().toVector(partOf(0.2, scale));
        DrawableLine firstLine = new ScaledArrow(arrow.getEnd().toScaledPosition(),
                arrow.getEnd().moveByVector(vector.rotate(150)), arrow.getColor());
        DrawableLine secondLine = new ScaledArrow(arrow.getEnd().toScaledPosition(),
                arrow.getEnd().moveByVector(vector.rotate(210)), arrow.getColor());
        paintLine(arrow, g);
        paintLine(firstLine, g);
        paintLine(secondLine, g);
    }

    private void paintLine(DrawableLine line, Graphics g) {
        g.setColor(convertColor(line.getColor()));
        g.drawLine(line.getStart().getX(), line.getStart().getY(),
                line.getEnd().getX(), line.getEnd().getY());
    }

    private void paintField(FieldAnnotation fieldAnnotation, Graphics g) {
        g.setColor(convertColor(fieldAnnotation.getColor()));
        g.drawOval((fieldAnnotation.getX() - 1) * scale, (8 - fieldAnnotation.getY()) * scale, scale, scale);
    }

    private void paintCoverage(Graphics g) {
        Map<Position, ChessBoardCoverageAnalyzer.Coverage> coverage = new ChessBoardCoverageAnalyzer(diagram.getBoard()).getBoardCoverage(chess.color.Color.white);
        coverage.forEach((position, coverage1) -> paintFieldCoverage(position, coverage1, g));
    }

    private void paintFieldCoverage(Position position, ChessBoardCoverageAnalyzer.Coverage coverage, Graphics g) {
        if (coverage == ChessBoardCoverageAnalyzer.Coverage.STRONG) {
            g.setColor(Color.green);
            g.drawRect((position.getX() - 1) * scale + partOf(0.05, scale),
                    (8 - position.getY()) * scale + partOf(0.05, scale),
                    partOf(0.9, scale),
                    partOf(0.9, scale));
        }
        if (coverage == ChessBoardCoverageAnalyzer.Coverage.WEAK) {
            g.setColor(Color.red);
            g.drawRect((position.getX() - 1) * scale + partOf(0.05, scale),
                    (8 - position.getY()) * scale + partOf(0.05, scale),
                    partOf(0.9, scale),
                    partOf(0.9, scale));
        }
    }

    private void paintLegalMoves(Graphics g) {
        diagram.getBoard().getGenerator().getAllPossibleExecutableMoves().stream()
                .map(ValidMove::getRepresentation)
                .map(rawMove -> new ScaledArrow(
                        new ScaledPosition(rawMove.getStartPosition(), scale),
                        new ScaledPosition(rawMove.getEndPosition(), scale),
                        GraphicAnnotation.DrawColor.GREEN))
                .map(CenteredScaledArrow::new)
                .forEach(scaledArrow -> paintArrow(scaledArrow, g));
    }

    private void paintWeakPoints(Graphics g) {
        new ChessBoardWeakPointsAnalyzer(diagram.getBoard()).getWeakPoints(chess.color.Color.white).forEach(position -> paintWeakPoint(position, g, Color.RED));
        new ChessBoardWeakPointsAnalyzer(diagram.getBoard()).getWeakPoints(chess.color.Color.black).forEach(position -> paintWeakPoint(position, g, Color.GREEN));
    }

    private void paintWeakPoint(Position position, Graphics g, Color color) {
        g.setColor(color);
        g.drawRoundRect((position.getX() - 1) * scale + partOf(0.1, scale),
                (8 - position.getY()) * scale + partOf(0.1, scale),
                partOf(0.8, scale),
                partOf(0.8, scale),
                partOf(0.1, scale),
                partOf(0.1, scale)
        );
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

    public void swapDoPaintCoverage() {
        doPaintCoverage = !doPaintCoverage;
        repaint();
    }

    public void swapDoPaintLegalMoves() {
        doPaintLegalMoves = !doPaintLegalMoves;
        repaint();
    }

    public void swapDoPaintWeakPoints() {
        doPaintWeakPoints = !doPaintWeakPoints;
        repaint();
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
