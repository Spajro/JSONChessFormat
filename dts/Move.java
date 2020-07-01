package dts;

import java.io.Serializable;

public class Move implements Serializable {
    int ox,oy,nx,ny;
    int Roszada; //0 brak 1 bk 2 bd 3 ck 4 cd
    public Move(){
        ox=0;
        oy=0;
        nx=0;
        ny=0;
        Roszada=0;
    }
    public Move(int aox,int aoy,int anx,int any){
        ox=aox;
        oy=aoy;
        nx=anx;
        ny=any;
        Roszada=0;
    }
    public Move(int R){
        Roszada=R;
    }
    public void Update(int aox,int aoy,int anx,int any) {
        ox = aox;
        oy = aoy;
        nx = anx;
        ny = any;
    }
    public void Roszada(int R){
        Roszada=R;
    }
    public void Make_move(Bufor T){
        if(Roszada==0) {
            T.write(T.get(ox, oy), nx, ny);
            T.write(0, ox, oy);
        }
        else{
            //TODO
            //Roszady
        }
    }
}
