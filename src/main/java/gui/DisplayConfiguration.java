package gui;

import chess.board.lowlevel.Board;

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

    public static HashMap<Byte, ImageIcon> setImageMap() {
        HashMap<Byte, ImageIcon> imageMap = new HashMap<>();
        imageMap.put(Board.WPAWN, getImageFromResources(WPAWNPATH));
        imageMap.put(Board.WKNIGHT, getImageFromResources(WKNIGHTPATH));
        imageMap.put(Board.WBISHOP, getImageFromResources(WBISHOPPATH));
        imageMap.put(Board.WROOK, getImageFromResources(WROOKPATH));
        imageMap.put(Board.WKING, getImageFromResources(WKINGPATH));
        imageMap.put(Board.WQUEEN, getImageFromResources(WQUEENPATH));
        imageMap.put(Board.BPAWN, getImageFromResources(BPAWNPATH));
        imageMap.put(Board.BKNIGHT, getImageFromResources(BKNIGHTPATH));
        imageMap.put(Board.BBISHOP, getImageFromResources(BBISHOPPATH));
        imageMap.put(Board.BROOK, getImageFromResources(BROOKPATH));
        imageMap.put(Board.BKING, getImageFromResources(BKINGPATH));
        imageMap.put(Board.BQUEEN, getImageFromResources(BQUEENPATH));
        return imageMap;
    }

    private static ImageIcon getImageFromResources(String path) {
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

    private static String fullPieceImagePath(String piecePath) {
        return FOLDERPATH + piecePath + FORMAT;
    }
}
