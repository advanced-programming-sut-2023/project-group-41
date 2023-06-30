package stronghold.controller;

import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;
import stronghold.model.components.game.Map;
import stronghold.model.components.game.MapCell;
import stronghold.view.sampleView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class NavigatorController {


    public static boolean isAbleToNavigate(int[][] matrix, int startingCoordinateX, int startingCoordinateY,
                                           int endingCoordinateX, int endingCoordinateY) {
        if (matrix[startingCoordinateX][startingCoordinateY] == 0
                || matrix[endingCoordinateX][endingCoordinateY] == 0) {
            return false;
        }

        int nRows = matrix.length;
        int nCols = matrix[0].length;
        boolean[][] visited = new boolean[nRows][nCols];
        visited[startingCoordinateX][startingCoordinateY] = true;
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[] { startingCoordinateX, startingCoordinateY });

        int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int currX = curr[0];
            int currY = curr[1];
            if (currX == endingCoordinateX && currY == endingCoordinateY) {
                return true;
            }
            for (int[] dir : directions) {
                int nextX = currX + dir[0];
                int nextY = currY + dir[1];
                if (nextX >= 0 && nextX < nRows && nextY >= 0 && nextY < nCols &&
                        matrix[nextX][nextY] == 1 && !visited[nextX][nextY]) {
                    visited[nextX][nextY] = true;
                    queue.add(new int[] { nextX, nextY });
                }
            }
        }
        return false;
    }
    public static boolean shortestPathIsLessThanLimit(int[][] matrix, int startingCoordinateX, int startingCoordinateY,
                                                      int endingCoordinateX, int endingCoordinateY, int lengthLimit){

        if (!isAbleToNavigate(matrix, startingCoordinateX, startingCoordinateY, endingCoordinateX, endingCoordinateY)) {
            return false;
        }

        int nRows = matrix.length;
        int nCols = matrix[0].length;
        boolean[][] visited = new boolean[nRows][nCols];
        visited[startingCoordinateX][startingCoordinateY] = true;
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startingCoordinateX, startingCoordinateY, 0});

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int currX = curr[0];
            int currY = curr[1];
            int currDist = curr[2];
            if (currX == endingCoordinateX && currY == endingCoordinateY) {
                return currDist <= lengthLimit;
            }
            for (int[] dir : directions) {
                int nextX = currX + dir[0];
                int nextY = currY + dir[1];
                if (nextX >= 0 && nextX < nRows && nextY >= 0 && nextY < nCols &&
                        matrix[nextX][nextY] == 1 && !visited[nextX][nextY]) {
                    visited[nextX][nextY] = true;
                    queue.add(new int[]{nextX, nextY, currDist + 1});
                }
            }
        }
        return false;
    }
    public static int[][] mapPassable(){
        int[][] mapIsPassable= new int[Map.getInstanceMap().getSize()][Map.getInstanceMap().getSize()];
        for(int i=0;i< Map.getInstanceMap().getSize();i++){
            for (int j = 0; j < Map.getInstanceMap().getSize(); j++) {

                    mapIsPassable[i][j]=1;



            }
        }
        return mapIsPassable;
    }



    public static int[][] findShortestPath(int[][] matrix, int startX, int startY, int endX, int endY, int limit) {

        if (!shortestPathIsLessThanLimit(matrix, startX, startY, endX, endY, limit)) {

            return matrix;

        }

        int rows = matrix.length;

        int cols = matrix[0].length;

        boolean[][] visited = new boolean[rows][cols];

        int[][] distance = new int[rows][cols];

        int[][] path = new int[rows][cols];

        Queue<int[]> queue = new LinkedList<>();



        queue.offer(new int[] { startX, startY });

        visited[startX][startY] = true;

        distance[startX][startY] = 0;



        int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };



        while (!queue.isEmpty()) {

            int[] current = queue.poll();

            int currX = current[0];

            int currY = current[1];

            if (currX == endX && currY == endY) {

                break;

            }

            for (int[] direction : directions) {

                int newX = currX + direction[0];

                int newY = currY + direction[1];

                if (newX >= 0 && newY >= 0 && newX < rows && newY < cols && matrix[newX][newY] == 1

                        && !visited[newX][newY]) {

                    visited[newX][newY] = true;

                    distance[newX][newY] = distance[currX][currY] + 1;

                    path[newX][newY] = currX * cols + currY + 1;

                    queue.offer(new int[] { newX, newY });

                }

            }

        }



        if (distance[endX][endY] == 0) {

            return matrix;

        }



        int[] shortestPath = new int[distance[endX][endY] + 1];

        shortestPath[0] = endX * cols + endY + 1;

        int idx = 1;

        int current = shortestPath[0];

        while (current != startX * cols + startY + 1) {

            int x = (current - 1) / cols;

            int y = (current - 1) % cols;

            shortestPath[idx] = path[x][y];

            current = path[x][y];

            idx++;

        }



        for (int i = shortestPath.length - 1; i >= 1; i--) {

            int x = (shortestPath[i] - 1) / cols;

            int y = (shortestPath[i] - 1) % cols;

            matrix[x][y] = 2;

        }

        matrix[endX][endY] = 2;

        return matrix;

    }
    public static void path(int startX, int startY, int endX, int endY, int[][]path, Node node){
        int k=startX;
        int k2=startY;
        ArrayList<Group> mapCells=new ArrayList<>();
        while(k!=endX||k2!=endY){
            outerloop:
            for(int i=-1;i<=1;i++){
                for(int j=-1;j<=1;j++){
                    if (i == 0 && j == 0) {

                        continue;
                    }
                    if(path[k+i][k2+j]==2){

                        path[k][k2]=-1;
                        k=k+i;
                        k2=k2+j;
                       // node.setLayoutX(node.getLayoutX()+i*20);
                        //node.setLayoutY(node.getLayoutY()+j*20);
                       sampleView.backGround( sampleView.getMapCellNodeHashMap().get(Map.getInstanceMap().getMapCell(k,k2))).setFill(Color.RED);
                        sampleView.backGround( sampleView.getMapCellNodeHashMap().get(Map.getInstanceMap().getMapCell(k,k2))).toFront();
                        sampleView.backGround( sampleView.getMapCellNodeHashMap().get(Map.getInstanceMap().getMapCell(k,k2))).setVisible(true);

                       mapCells.add(sampleView.getMapCellNodeHashMap().get(Map.getInstanceMap().getMapCell(k,k2)));



                        //transition.
                        //sampleView.getMapCellNodeHashMap().get(Map.getInstanceMap().getMapCell(k,k2)).getChildren().clear();
                        System.out.println(k+" "+k2);
                        break outerloop;





                    }


                }

            }





    }


            TranslateTransition transition=new TranslateTransition(Duration.millis(8000));
            transition.setByX(20*(endX-startX));
            transition.setByY(20*(endY-startY));
            transition.setNode(node);
            transition.setOnFinished(actionEvent -> adder(mapCells));

            transition.play();


    }//
    public static void  adder(ArrayList<Group>mapCells){
        ImagePattern grass= null;

            sampleView.getAttackBanner().setFill(Color.BLACK);

        try {
            grass = new ImagePattern(new Image(new FileInputStream("src/main/java/stronghold/database/Image/tiles/grass.jpg")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        for (Group mapCell : mapCells) {
            sampleView.backGround(mapCell).setFill(grass);
        }
    }




}

