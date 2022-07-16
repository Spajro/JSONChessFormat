package src.data.dts;

public class Position {
    //class representing position on board
    private int x;
    private int y;
    public Position(){
        x=-1;
        y=-1;
    }
    public Position(int nx, int ny){
        x=nx;
        y=ny;
    }
    public Position(Position P){
        x=P.x;
        y=P.y;
    }
    public Position add(Position P){
        return new Position(x+=P.x,y+=P.y);
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
