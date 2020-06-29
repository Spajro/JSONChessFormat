package dts;

import java.io.Serializable;

public class Move implements Serializable {
    int ox,oy,nx,ny;
    public Move(){
        ox=0;
        oy=0;
        nx=0;
        ny=0;
    }
    public Move(int aox,int aoy,int anx,int any){
        ox=aox;
        oy=aoy;
        nx=anx;
        ny=any;
    }
    public void Update(int aox,int aoy,int anx,int any){
        ox=aox;
        oy=aoy;
        nx=anx;
        ny=any;
    }
    public void Make_move(Bufor T){
        T.write(T.get(ox,oy),nx,ny);
        T.write(0,ox,oy);
    }
}
