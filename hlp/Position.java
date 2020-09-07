package hlp;

public class Position {
    int x;
    int y;
    Position(){
        x=0;
        y=0;
    }
    Position(int nx, int ny){
        x=nx;
        y=ny;
    }
    Position( Position P){
        x=P.x;
        y=P.y;
    }
    void Add(Position P){
        x+=P.x;
        y+=P.y;
    }
    void Add(int nx,int ny){
        x+=nx;
        y+=ny;
    }
}
