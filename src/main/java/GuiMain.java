import data.dts.DataModel;
import gui.App;

public class GuiMain {
    public static void main(String[] args){
        DataModel dataModel=new DataModel();
        App app=new App(dataModel);
    }
}
