package stronghold.model.components.game;

public class Tool {
    private boolean movable;
    private int engineerNum;
    private int X;
    private int Y;
    private String name;

    public String getName() {
        return name;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public int getEngineerNum() {
        return engineerNum;
    }

    public void setY(int y) {
        Y = y;
    }

    public void setX(int x) {
        X = x;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEngineerNum(int engineerNum) {
        this.engineerNum = engineerNum;
    }

    public void setMovable(boolean movable) {
        this.movable = movable;
    }

    public boolean isMovable() {
        return movable;
    }
    public Tool(int engineerNum,String name,int x,int y,boolean isMovable){
        this.engineerNum=engineerNum;
        this.name=name;
        this.X=x;
        this.Y=y;
        this.movable=isMovable;

    }
}
