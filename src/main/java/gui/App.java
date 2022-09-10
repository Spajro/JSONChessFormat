package gui;

import chess.board.Board;
import data.model.DataModel;
import data.model.Diagram;

import javax.swing.*;
import java.awt.*;


public class App {
    private final JFrame frame;
    private final BoardPanel boardPanel;
    private final OptionPanel optionPanel;
    private final Controller controller;
    private final JMenuBar menuBar;
    public App(DataModel dataModel) {
        controller=new Controller(dataModel);
        frame=new JFrame();
        menuBar=new JMenuBar();
        createFileMenu(dataModel);
        frame.setJMenuBar(menuBar);
        boardPanel=new BoardPanel(dataModel.getActualNode());
        optionPanel=new OptionPanel(controller);
        boardPanel.addMouseListener(new BoardMouseListener(boardPanel,controller));
        boardPanel.addKeyListener(new KeyboardListener(controller));
        boardPanel.setSize(480,480);
        //optionPanel.setSize(160,480);
        frame.add(boardPanel,BorderLayout.CENTER);
        frame.add(optionPanel,BorderLayout.EAST);
        frame.setSize(640,480);
        frame.setVisible(true);
    }
    public void setDiagram(Diagram diagram){
        boardPanel.setDiagram(diagram);
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
