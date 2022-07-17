package src.gui;

import src.data.dts.Board;
import src.data.dts.Position;
import src.data.hlp.Translator;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

import static java.lang.Math.min;

public class BoardPanel extends JPanel {
    private int scale=60;
    private Board board;
    private Color colBack=Color.WHITE;
    private Color colWhiteField=Color.WHITE;
    private Color colBlackField=Color.BLACK;
    private HashMap<Integer,ImageIcon> imageMap;
    public static final String FOLDERPATH="";
    public static final String WPAWNPATH="";
    public static final String WKNIGHTPATH="";
    public static final String WBISHOPPATH="";
    public static final String WROOKPATH="";
    public static final String WQUEENPATH="";
    public static final String WKINGPATH="";
    public static final String BPAWNPATH="";
    public static final String BKNIGHTPATH="";
    public static final String BBISHOPPATH="";
    public static final String BROOKPATH="";
    public static final String BQUEENPATH="";
    public static final String BKINGPATH="";


    public BoardPanel() {
        setImageMap();
    }

    public BoardPanel(Board board) {
        setImageMap();
        this.board = board;
    }

    @Override
    public void paint(Graphics g) {
        scale=getScale();
        g.setFont(new Font("TimesRoman", Font.PLAIN, partOf(0.25,scale)));
        g.setColor(colBack);
        g.fillRect(0, 0, getWidth(), getHeight());
        if (board != null) {
            boolean temp=true;
            for (int y = 0; y < Board.SIZE; y++) {
                for (int x = 0; x < Board.SIZE; x++) {
                    if (temp) {
                        g.setColor(colWhiteField);
                    }
                    else{
                        g.setColor(colBlackField);
                    }
                    g.fillRect(x * scale, y * scale, scale, scale);
                    temp=!temp;
                    int fig=board.read(new Position(x+1,Board.SIZE-y));
                    if(fig!=Board.EMPTY){
                         //g.drawImage(imageMap.get(fig).getImage(),x * scale, y * scale, scale, scale,g.getColor(),null);
                        String s=Translator.numberToFigure(fig);
                        assert s != null;
                        g.setColor(Color.green);
                        g.drawString(s,x*scale+partOf(0.5,scale),y*scale+partOf(0.5,scale));
                    }
                }
                temp=!temp;
            }
        }
    }

    private void setImageMap(){
        // przypisanie obrazka do inta z Board
        imageMap=new HashMap<>();
        imageMap.put(Board.WPAWN,new ImageIcon(FOLDERPATH+WPAWNPATH));
        imageMap.put(Board.WKNIGHT,new ImageIcon(FOLDERPATH+WKNIGHTPATH));
        imageMap.put(Board.WBISHOP,new ImageIcon(FOLDERPATH+WBISHOPPATH));
        imageMap.put(Board.WROOK,new ImageIcon(FOLDERPATH+WROOKPATH));
        imageMap.put(Board.WKING,new ImageIcon(FOLDERPATH+WKINGPATH));
        imageMap.put(Board.WQUEEN,new ImageIcon(FOLDERPATH+WQUEENPATH));
        imageMap.put(Board.BPAWN,new ImageIcon(FOLDERPATH+BPAWNPATH));
        imageMap.put(Board.BKNIGHT,new ImageIcon(FOLDERPATH+BKNIGHTPATH));
        imageMap.put(Board.BBISHOP,new ImageIcon(FOLDERPATH+BBISHOPPATH));
        imageMap.put(Board.BROOK,new ImageIcon(FOLDERPATH+BROOKPATH));
        imageMap.put(Board.BKING,new ImageIcon(FOLDERPATH+BKINGPATH));
        imageMap.put(Board.BQUEEN,new ImageIcon(FOLDERPATH+BQUEENPATH));
    }

    public void setBoard(Board board) {
        this.board = board;
        this.repaint();
    }

    public int getScale(){
        int px=getWidth()/Board.SIZE;
        int py=getHeight()/Board.SIZE;
        System.out.print("PX: "+px+"PY: "+py+"\n");
        return min(px,py);
    }

    public int partOf(double a,int b){
        return (int) (a*b);
    }
}
