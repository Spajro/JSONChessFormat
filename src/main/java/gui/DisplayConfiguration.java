package gui;

import chess.board.Board;

import javax.swing.*;
import java.util.HashMap;

public class DisplayConfiguration {
    public static final String FOLDERPATH = "";
    public static final String WPAWNPATH = "";
    public static final String WKNIGHTPATH = "";
    public static final String WBISHOPPATH = "";
    public static final String WROOKPATH = "";
    public static final String WQUEENPATH = "";
    public static final String WKINGPATH = "";
    public static final String BPAWNPATH = "";
    public static final String BKNIGHTPATH = "";
    public static final String BBISHOPPATH = "";
    public static final String BROOKPATH = "";
    public static final String BQUEENPATH = "";
    public static final String BKINGPATH = "";

    public static HashMap<Integer,ImageIcon> setImageMap() {
        HashMap<Integer,ImageIcon> imageMap = new HashMap<>();
        imageMap.put(Board.WPAWN, new ImageIcon(FOLDERPATH + WPAWNPATH));
        imageMap.put(Board.WKNIGHT, new ImageIcon(FOLDERPATH + WKNIGHTPATH));
        imageMap.put(Board.WBISHOP, new ImageIcon(FOLDERPATH + WBISHOPPATH));
        imageMap.put(Board.WROOK, new ImageIcon(FOLDERPATH + WROOKPATH));
        imageMap.put(Board.WKING, new ImageIcon(FOLDERPATH + WKINGPATH));
        imageMap.put(Board.WQUEEN, new ImageIcon(FOLDERPATH + WQUEENPATH));
        imageMap.put(Board.BPAWN, new ImageIcon(FOLDERPATH + BPAWNPATH));
        imageMap.put(Board.BKNIGHT, new ImageIcon(FOLDERPATH + BKNIGHTPATH));
        imageMap.put(Board.BBISHOP, new ImageIcon(FOLDERPATH + BBISHOPPATH));
        imageMap.put(Board.BROOK, new ImageIcon(FOLDERPATH + BROOKPATH));
        imageMap.put(Board.BKING, new ImageIcon(FOLDERPATH + BKINGPATH));
        imageMap.put(Board.BQUEEN, new ImageIcon(FOLDERPATH + BQUEENPATH));
        return imageMap;
    }
}
