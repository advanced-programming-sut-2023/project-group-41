package stronghold.controller;

import stronghold.model.components.game.Map;

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
                if(Map.getInstanceMap().getMapCell(i,j).isPassable()){
                    mapIsPassable[i][j]=1;

                }else{
                    mapIsPassable[i][j]=0;

                }

            }
        }
        return mapIsPassable;
    }

}

