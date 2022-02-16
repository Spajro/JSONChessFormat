package prg;

import dis.BoardDisplayData;
import dts.Diagram;
import dts.Move;

import java.io.*;

public class Database_editor implements Mode{
    Diagram Base;
    String Name;
    boolean AnnotatingOn=false;

    public Database_editor(String N){
        Base= new Diagram();
        Name=N;
    }
    @Override
    public void Make_action(Action_data AD) {
        if(!AnnotatingOn) {
            switch (AD.Get_code()) {
                case "LD" -> {
                    Base = Load((String) AD.Parameter);
                    if (Base == null) System.out.print("Loading failed");
                }
                case "SV" -> Save();
                case "MM" -> Make_move((Move) AD.Parameter);
                case "AN" -> Annotate();
                case "QT" -> Exit();
                case "DL" -> Delete_diagram();
                case "GB" -> Go_back((int) AD.Parameter);
                case "PM" -> PrintMoves();
                case "PH" -> PrintHistory();
                case "JB" -> JumpBack();
                case "JF" -> JumpForward();
                case "HP" -> PrintHelp();
                default -> System.out.print("Unknown code MA");
            }
        }

    }

    @Override
    public BoardDisplayData Display() {
        return new BoardDisplayData(Base);
    }

    @Override
    public void Exit() {
    //TODO
    }


    Diagram Load(String Namefile){
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(Namefile+".bin"))) {
            return (Diagram) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    void Save(){
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(Name+".bin"))) {
            outputStream.writeObject(Base.original());
            System.out.print("Saved sukces");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void Make_move(Move M){
        Base=Base.makeMove(M);
    }
    void Delete_diagram(){
       if(Base.getLastMove() !=null){
           Diagram Temp=Base;
           Base=Base.getLastMove();
           Base.getNextMoves().remove(Temp);
       }
       else {
           System.out.print("Cant delete");
       }

    }
    void Annotate(){
        AnnotatingOn = !AnnotatingOn;

    }
    void Go_back(int pos){
        Base=Base.findMove(pos);
    }
    void PrintMoves(){
        String[] ToPrint=Base.getMoves();
        if(ToPrint!=null){
            for(String S : ToPrint){
                System.out.print(S+" ");
            }

        }
    }
    void PrintHistory(){
        String[] ToPrint=Base.getHistory();
        if(ToPrint!=null){
            for(String S : ToPrint){
                if(S!=null)System.out.print(S+" ");
            }

        }
    }
    void JumpBack(){
        if(Base.getLastMove() !=null){
            Base=Base.getLastMove();
        }
        else{
            System.out.print("Cant jump back");
        }
    }
    void JumpForward(){
        if(!Base.getNextMoves().isEmpty()){
            Base=Base.getNextMoves().peekFirst();
        }
        else{
            System.out.print("Cant jump forward");
        }
    }
    void PrintHelp(){
        System.out.print(
                "            To make action write code , follow it by parameter if neccesary in next line \n"+
                "            case \"SV\" -> Save();\n" +
                "            case \"MM\" -> Makes move from parameter\n" +
                "            case \"Q\" -> Exit();\n" +
                "            case \"DL\" -> Delete_diagram();\n" +
                "            case \"GB\" -> Goes back to position indexed by parameter;\n" +
                "            case \"PM\" -> PrintMoves();\n" +
                "            case \"PH\" -> PrintHistory();\n" +
                "            case \"JB\" -> JumpBack();\n" +
                "            case \"JF\" -> JumpForward();\n" +
                "            case \"HP\" -> PrintHelp();");

    }

    public Diagram GetDiag() {
        return Base;
    }

    public int getColor() {
        return Base.getColor();
    }
}
