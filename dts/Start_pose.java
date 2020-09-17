package dts;

import ant.Annotation;
import ant.Annotation_Editor;

import java.util.LinkedList;

public class Start_pose extends Diagram {
    public Start_pose(){

        T=new Bufor(8);
        for(int i=0;i<8;i++){
            T.write(11,i,1);
            T.write(21,i,6);

        }
        for(int i=0;i<5;i++){
            T.write(12+i,i,0);
            T.write(22+i,i,7);

        }
        int j=0;
        for(int i=5;i<8;i++){
            T.write(14-j,i,0);
            T.write(24-j,i,7);
            j++;
        }
        MoveId=0;
        LastMove=null;
        Next_moves = new LinkedList<>();
        Info = new Annotation_Editor();
    }
}
