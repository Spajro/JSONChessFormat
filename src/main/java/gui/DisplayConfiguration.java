package gui;

import chess.pieces.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class DisplayConfiguration {
    public static final String FOLDERPATH = "/pieces/";
    public static final String WPAWNPATH = "white/pawn";
    public static final String WKNIGHTPATH = "white/knight";
    public static final String WBISHOPPATH = "white/bishop";
    public static final String WROOKPATH = "white/rook";
    public static final String WQUEENPATH = "white/queen";
    public static final String WKINGPATH = "white/king";
    public static final String BPAWNPATH = "black/pawn";
    public static final String BKNIGHTPATH = "black/knight";
    public static final String BBISHOPPATH = "black/bishop";
    public static final String BROOKPATH = "black/rook";
    public static final String BQUEENPATH = "black/queen";
    public static final String BKINGPATH = "/black/king";
    public static final String FORMAT = ".bmp";
    HashMap<Piece.Type, ImageIcon> whiteImageMap = new HashMap<>();
    HashMap<Piece.Type, ImageIcon> blackImageMap = new HashMap<>();

    public DisplayConfiguration() {
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
        URL url = DisplayConfiguration.class.getResource(fullPieceImagePath(path));
        try {
            if (url == null) {
                throw new RuntimeException("Cant read piece from resources");
            }
            Image image = ImageIO.read(url);
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
