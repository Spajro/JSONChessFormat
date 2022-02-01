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
    public void add(Position P){
        x+=P.x;
        y+=P.y;
    }
    public void add(int nx, int ny){
        x+=nx;
        y+=ny;
    }
    public boolean isEmpty(){
        return x == -1 && y == -1;
    }
    public boolean isOnBoard(){
        return !(x < 1 || y < 1 || x > 8 || y > 8);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
