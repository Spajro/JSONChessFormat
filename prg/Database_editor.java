package prg;

import dis.BoardDisplayData;
import dts.Diagram;
import dts.Move;
import dts.Start_pose;

import java.io.*;

public class Database_editor implements Mode{
    Diagram Base;
    String Name;
    boolean AnnotatingOn=false;

    public Database_editor(String N){
        Base= new Start_pose();
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
        else{
            if(AD.Get_code()=="AN"){
                Annotate();
            }
            else Base.Info.Make_action(AD);
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
            outputStream.writeObject(Base.Original());
            System.out.print("Saved sukces");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void Make_move(Move M){
        Base=Base.Make_move(M);
    }
    void Delete_diagram(){
       if(Base.LastMove!=null){
           Diagram Temp=Base;
           Base=Base.LastMove;
           Base.Next_moves.remove(Temp);
       }
       else {
           System.out.print("Cant delete");
       }

    }
    void Annotate(){
        AnnotatingOn = !AnnotatingOn;

    }
    void Go_back(int pos){
        Base=Base.FindMove(pos);
    }
    void PrintMoves(){
        String[] ToPrint=Base.GetMoves();
        if(ToPrint!=null){
            for(String S : ToPrint){
                System.out.print(S+" ");
            }

        }
    }
    void PrintHistory(){
        String[] ToPrint=Base.GetHistory();
        if(ToPrint!=null){
            for(String S : ToPrint){
                if(S!=null)System.out.print(S+" ");
            }

        }
    }
    void JumpBack(){
        if(Base.LastMove!=null){
            Base=Base.LastMove;
        }
        else{
            System.out.print("Cant jump back");
        }
    }
    void JumpForward(){
        if(!Base.Next_moves.isEmpty()){
            Base=Base.Next_moves.peekFirst();
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

    public boolean GetColor() {
        return Base.Color;
    }
}
