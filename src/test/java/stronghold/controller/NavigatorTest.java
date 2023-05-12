package stronghold.controller;

import org.junit.jupiter.api.Test;
import stronghold.model.components.game.Map;

import static org.junit.jupiter.api.Assertions.*;

class NavigatorTest {

    @Test
    void isAbleToNavigate() {
        int[][] ableMatrix = {{0, 1, 0, 0, 1},
                              {0, 1, 1, 1, 0},
                              {0, 1, 0, 1, 1}};
        assertEquals(Navigator.isAbleToNavigate(ableMatrix,
                0, 1,
                2 , 4), true);

        int[][] unableMatrix = {{0, 1, 0, 0, 1},
                {0, 1, 0, 1, 0},
                {0, 1, 0, 1, 1}};
        assertEquals(Navigator.isAbleToNavigate(unableMatrix,
                0, 1,
                2 , 4), false);
    }

    @Test
    void shortestPathIsLessThanLimit() {
        int[][] matrix1 = {{0, 1, 0, 0, 1},
                {0, 1, 1, 1, 0},
                {0, 1, 0, 1, 1}};
        assertEquals(Navigator.shortestPathIsLessThanLimit(matrix1,
                0, 1,
                2 , 4, 5), true);
        assertEquals(Navigator.shortestPathIsLessThanLimit(matrix1,
                0, 1,
                2 , 4, 4), false);
    }

    @Test
    void mapPassable() {
        Map.getInstanceMap().setSize(24);
        int[][] mappedPassables = Navigator.mapPassable();
        for(int[] row: mappedPassables){
            for(int col: row){
                System.out.print(col);
            }
            System.out.println();
            
        }
        
    }

    @Test
    void testAll(){
        mapPassable();
        isAbleToNavigate();
        shortestPathIsLessThanLimit();
    }
}