package prg;

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
        //TODO
        //dokonczyc switcha
        switch (AD.Get_code()){
            case "LD":
                Base=Load((String)AD.Parameters[0]);
                if(Base==null)System.out.print("Loading failed");
                break;
            case "SV":
                Save();
                break;
            case "MM":
                Make_move((Move) AD.Parameters[0]);
                break;
            case "AN":
                Annotate();
                break;
            case "QT":
                Exit();
                break;
            case "DL":
                Delete_move((Move) AD.Parameters[0]);
                break;
        }
    }

    @Override
    public Display_data Display() {
        //TODO
        return null;
    }

    @Override
    public void Exit() {
    //TODO
    }
    Diagram Load(String Namefile){
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(Namefile+".bin"))) {
            Diagram neu = (Diagram) inputStream.readObject();
            return neu;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    void Save(){
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(Name+".bin"))) {
            outputStream.writeObject(Base.Original());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void Make_move(Move M){
        Base=Base.Make_move(M);
    }
    void Delete_move(Move M){
        //TODO
    }
    void Annotate(){
        //TODO
    }
}
