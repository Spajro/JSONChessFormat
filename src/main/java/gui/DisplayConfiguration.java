package gui;

import chess.pieces.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class DisplayConfiguration {
    private static final String FOLDERPATH = "/pieces/";
    private static final String WPAWNPATH = "white/pawn";
    private static final String WKNIGHTPATH = "white/knight";
    private static final String WBISHOPPATH = "white/bishop";
    private static final String WROOKPATH = "white/rook";
    private static final String WQUEENPATH = "white/queen";
    private static final String WKINGPATH = "white/king";
    private static final String BPAWNPATH = "black/pawn";
    private static final String BKNIGHTPATH = "black/knight";
    private static final String BBISHOPPATH = "black/bishop";
    private static final String BROOKPATH = "black/rook";
    private static final String BQUEENPATH = "black/queen";
    private static final String BKINGPATH = "black/king";
    private static final String FORMAT = ".bmp";
    private final HashMap<Piece.Type, ImageIcon> whiteImageMap;
    private final HashMap<Piece.Type, ImageIcon> blackImageMap;

    public DisplayConfiguration() {
        whiteImageMap = new HashMap<>();
        blackImageMap = new HashMap<>();

        whiteImageMap.put(Piece.Type.PAWN, getImageFromResources(WPAWNPATH));
        whiteImageMap.put(Piece.Type.KNIGHT, getImageFromResources(WKNIGHTPATH));
        whiteImageMap.put(Piece.Type.BISHOP, getImageFromResources(WBISHOPPATH));
        whiteImageMap.put(Piece.Type.ROOK, getImageFromResources(WROOKPATH));
        whiteImageMap.put(Piece.Type.KING, getImageFromResources(WKINGPATH));
        whiteImageMap.put(Piece.Type.QUEEN, getImageFromResources(WQUEENPATH));

        blackImageMap.put(Piece.Type.PAWN, getImageFromResources(BPAWNPATH));
        blackImageMap.put(Piece.Type.KNIGHT, getImageFromResources(BKNIGHTPATH));
        blackImageMap.put(Piece.Type.BISHOP, getImageFromResources(BBISHOPPATH));
        blackImageMap.put(Piece.Type.ROOK, getImageFromResources(BROOKPATH));
        blackImageMap.put(Piece.Type.KING, getImageFromResources(BKINGPATH));
        blackImageMap.put(Piece.Type.QUEEN, getImageFromResources(BQUEENPATH));
    }

    private ImageIcon getImageFromResources(String path) {
        try (InputStream resource = getClass().getResourceAsStream(fullPieceImagePath(path))) {
            Image image = ImageIO.read(resource);
            return new ImageIcon(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String fullPieceImagePath(String piecePath) {
        return FOLDERPATH + piecePath + FORMAT;
    }

    public ImageIcon getImage(Piece piece) {
        if (piece.getColor().isWhite()) {
            return whiteImageMap.get(piece.getType());
        } else {
            return blackImageMap.get(piece.getType());
        }
    }
}
