package dts;

public class Position {
    //class representing position on board
    int x;
    int y;
    public Position(){
        x=-1;
        y=-1;
    }
    public Position(int nx, int ny){
        x=nx;
        y=ny;
    }
    public Position( Position P){
        x=P.x;
        y=P.y;
    }
    public void Add(Position P){
        x+=P.x;
        y+=P.y;
    }
    public void Add(int nx,int ny){
        x+=nx;
        y+=ny;
    }
    public boolean IsEmpty(){
        return x == -1 && y == -1;
    }
    public boolean IsOnBoard(){
        return !(x < 1 || y < 1 || x > 8 || y > 8);
    }

}
