package src.gui;

import src.data.dts.Board;
import src.data.dts.DataModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class App {
    private JFrame frame;
    private BoardPanel boardPanel;
    private OptionPanel optionPanel;
    private Controller controller;
    private JMenuBar menuBar;
    public App(DataModel dataModel) {
        controller=new Controller(dataModel);
        frame=new JFrame();
        menuBar=new JMenuBar();
        createFileMenu(dataModel);
        frame.setJMenuBar(menuBar);
        boardPanel=new BoardPanel(dataModel.getActualBoard());
        optionPanel=new OptionPanel(controller);
        boardPanel.addMouseListener(new BoardMouseListener(boardPanel,controller));
        boardPanel.setSize(480,480);
        //optionPanel.setSize(160,480);
        frame.add(boardPanel,BorderLayout.CENTER);
        frame.add(optionPanel,BorderLayout.EAST);
        frame.setSize(640,480);
        frame.setVisible(true);
    }
    public void setBoard(Board board){
        boardPanel.setBoard(board);
    }

    private void createFileMenu(DataModel dataModel){
        JMenu fileMenu= new JMenu("File");
        JMenuItem saveMenuItem=new JMenuItem("Save");
        saveMenuItem.addActionListener(e -> dataModel.saveDataToFile(getFilename()));
        JMenuItem loadMenuItem=new JMenuItem("Load");
        loadMenuItem.addActionListener(e -> dataModel.loadDataFromFile(getFilename()));
        fileMenu.add(saveMenuItem);
        fileMenu.add(loadMenuItem);
        menuBar.add(fileMenu);
    }


    private String getFilename(){
        return JOptionPane.showInputDialog(frame,"What is name of file?",null);
    }
}
