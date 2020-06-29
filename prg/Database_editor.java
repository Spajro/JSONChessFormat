package prg;

import dts.Bufor;
import dts.Diagram;
import dts.Move;
import dts.Start_pose;

import java.io.*;

public class Database_editor implements Mode{
    Diagram Base;
    String Name;

    Database_editor(String N){
        Base= new Start_pose();
        Name=N;
    }
    @Override
    public void Make_action(Action_data AD) {
        switch (AD.Get_code()){
            case "LD":
                Base=Load((String)AD.Parameter);
                if(Base==null)System.out.print("Loading failed");
                break;
            case "SV":
                Save();
                break;
            case "MM":
                Make_move((Move) AD.Parameter);
                break;
            case "AN":
                Annotate();
                break;
            case "QT":
                Exit();
                break;
            case "DL":
                Delete_diagram((Diagram) AD.Parameter);
                break;
            case "GB":
                Go_back((int) AD.Parameter);
                break;
            default:
                System.out.print("Unknown code MA");
        }
    }

    @Override
    public Display_data Display() {
        Display_data D=new Display_data(Base);
        return D;
    }

    @Override
    public void Exit() {
    //TODO
    }

    @Override
    public Diagram getDiag() {
    return  Base;
    }

    @Override
    public boolean GetColor() {
        return Base.Color;
    }

    Diagram Load(String Namefile){
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(Namefile+".bin"))) {
            Diagram neu = (Diagram) inputStream.readObject();
            return neu;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    void Save(){
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(Name+".bin"))) {
            outputStream.writeObject(Base.Original());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void Make_move(Move M){
        Base=Base.Make_move(M);
    }
    void Delete_diagram(Diagram D){
        Diagram temp = Base.Story.getLast();
        if(Base.equals(D)){
            temp.Next_moves.remove(Base);
            Base=temp;
        }
        else{
            if(Base.T.equals(new Start_pose().T)){
                System.out.print("Diagram not found");
            }
            else{
                Base=temp;
                Delete_diagram(D);
            }
        }
    }
    void Annotate(){
        //TODO
    }
    void Go_back(int pos){
        Base=Base.Story.get(pos);
    }
}
