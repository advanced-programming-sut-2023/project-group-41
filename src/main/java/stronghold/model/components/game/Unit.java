package stronghold.model.components.game;

import stronghold.model.components.game.enums.State;

import java.util.ArrayList;

public class Unit {

    ////Integers
   private int X;
    private int Y;
    private int patrolX1;
    private Tool tool;
    private int patrolY1;
    private int patrolX2;
    private int patrolY2;

    private int count;
    ////Strings
    private People people;

    ////Arraylists

    private State state;

    public Unit(int x,int y,People type,int count){
        this.people=type;
        this.X=x;
        this.Y=y;
        this.count=count;
        this.patrolX1=0;
        this.patrolY1=0;
        this.patrolX2=0;
        this.patrolY2=0;
        this.tool=null;


    }

    public Tool getTool() {
        return tool;
    }

    public int getPatrolX2() {
        return patrolX2;
    }

    public int getPatrolY2() {
        return patrolY2;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public int getPatrolX1() {
        return patrolX1;
    }

    public int getPatrolY1() {
        return patrolY1;
    }

    public void setPatrolX1(int patrolX1) {
        this.patrolX1 = patrolX1;
    }

    public void setPatrolX2(int patrolX2) {
        this.patrolX2 = patrolX2;
    }

    public void setPatrolY1(int patrolY1) {
        this.patrolY1 = patrolY1;
    }

    public void setPatrolY2(int patrolY2) {
        this.patrolY2 = patrolY2;
    }

    public void setX(int x) {
        X = x;
    }

    public void setY(int y) {
        Y = y;
    }

    public int getCount() {
        return count;
    }

    public People getPeople() {
        return people;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getY() {
        return Y;
    }

    public int getX() {
        return X;
    }

    public boolean checkBondsForDisband(int X1,int Y1){
        if((X1>X+(people.getSpeed()*40))||(X1<X-(people.getSpeed()*40))||(Y1>Y+(people.getSpeed()*40))||(Y1>Y-(people.getSpeed()*40))){
            return false;
        }
        return true;

    }
    public boolean isInRange(int X2,int Y2){
        if(Math.abs(X2-X)<people.getSpeed()*5||Math.abs(Y2-Y)<people.getSpeed()*5){
            return  true;

        }
        return  false;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
