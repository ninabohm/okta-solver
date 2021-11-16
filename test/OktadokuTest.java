import org.junit.Test;
import static org.junit.Assert.*;
public class OktadokuTest {

    @Test
    public void shouldReturnNormalGivenStyleIsNormal() {
        Oktadoku octadoku = new Oktadoku(Oktadoku.Style.withDiagonals);
        assertEquals("withDiagonals", octadoku.style.toString());
    }

    //col
    @Test
    public void shouldReturnTrueGiven3IsInColumn1() {
        Oktadoku octadoku = new Oktadoku(Oktadoku.Style.withDiagonals);
        assertTrue(octadoku.checkIfNumberInColumn(sampleBoard1,3,0));
    }

    @Test
    public void shouldReturnTrueGiven6IsInColumn7() {
        Oktadoku octadoku = new Oktadoku(Oktadoku.Style.withDiagonals);
        assertTrue(octadoku.checkIfNumberInColumn(sampleBoard1,6,7));
    }

    @Test
    public void shouldReturnFalseGiven3IsNotInColumn1() {
        Oktadoku octadoku = new Oktadoku(Oktadoku.Style.withDiagonals);
        assertFalse(octadoku.checkIfNumberInColumn(sampleBoard1,3,1));
    }

    //row
    @Test
    public void shouldReturnTrueGiven7IsInRow1() {
        Oktadoku octadoku = new Oktadoku(Oktadoku.Style.withDiagonals);
        assertTrue(octadoku.checkIfNumberInRow(sampleBoard1,7,1));
    }

    @Test
    public void shouldReturnTrueGiven6IsInRow7() {
        Oktadoku octadoku = new Oktadoku(Oktadoku.Style.withDiagonals);
        assertTrue(octadoku.checkIfNumberInRow(sampleBoard1,6,7));
    }

    @Test
    public void shouldReturnFalseGiven3IsNotInRow1() {
        Oktadoku octadoku = new Oktadoku(Oktadoku.Style.withDiagonals);
        assertFalse(octadoku.checkIfNumberInRow(sampleBoard1,3,1));
    }

    @Test
    public void shouldReturnFalseGiven3IsNotInRow7() {
        Oktadoku octadoku = new Oktadoku(Oktadoku.Style.withDiagonals);
        assertFalse(octadoku.checkIfNumberInRow(sampleBoard1,3,7));
    }

    //diag
    @Test
    public void shouldReturnTrueGiven3IsInRow1AndCol1() {
        Oktadoku octadoku = new Oktadoku(Oktadoku.Style.withDiagonals);
        assertTrue(octadoku.checkIfNumberInDiagonal(sampleBoard1,3,1, 1));
    }

    @Test
    public void shouldReturnFalseGiven3IsNotInRow7AndCol6() {
        Oktadoku octadoku = new Oktadoku(Oktadoku.Style.withDiagonals);
        assertFalse(octadoku.checkIfNumberInDiagonal(sampleBoard1,3,7,6));
    }

    @Test
    public void shouldReturnFalseGiven3IsNotInRow0AndCol7() {
        Oktadoku octadoku = new Oktadoku(Oktadoku.Style.withDiagonals);
        assertFalse(octadoku.checkIfNumberInDiagonal(sampleBoard1,3,0,7));
    }

    @Test
    public void shouldReturnTrueGiven5IsInRow0AndCol7() {
        Oktadoku octadoku = new Oktadoku(Oktadoku.Style.withDiagonals);
        assertTrue(octadoku.checkIfNumberInDiagonal(sampleBoard1,5,0, 7));
    }

    @Test
    public void shouldReturnTrueGiven3IsInRow3AndCol3() {
        Oktadoku octadoku = new Oktadoku(Oktadoku.Style.withDiagonals);
        assertTrue(octadoku.checkIfNumberInDiagonal(sampleBoard1,3,3, 3));
    }

    //validRows
    @Test
    public void shouldReturnTrueGivenRowsAreValid() {
        Oktadoku octadoku = new Oktadoku(Oktadoku.Style.withDiagonals);
        assertTrue(octadoku.checkIfValidRows(sampleBoard2));
    }

    @Test
    public void shouldReturnFalseGivenRowsAreNotValid() {
        Oktadoku octadoku = new Oktadoku(Oktadoku.Style.withDiagonals);
        assertFalse(octadoku.checkIfValidRows(sampleBoard3));
    }

    //validCols
    @Test
    public void shouldReturnTrueGivenColsAreValid() {
        Oktadoku octadoku = new Oktadoku(Oktadoku.Style.withDiagonals);
        assertTrue(octadoku.checkIfValidColumns(sampleBoard2));
    }

    @Test
    public void shouldReturnFalseGivenColsAreNotValid() {
        Oktadoku octadoku = new Oktadoku(Oktadoku.Style.withDiagonals);
        assertFalse(octadoku.checkIfValidColumns(sampleBoard3));
    }

    //validDiagonlas
    @Test
    public void shouldReturnTrueGivenDiagonalsAreValid() {
        Oktadoku octadoku = new Oktadoku(Oktadoku.Style.withDiagonals);
        assertTrue(octadoku.checkIfValidDiagonals(sampleBoard2));
    }

    @Test
    public void shouldReturnFalseGivenDiagonalsAreNotValid() {
        Oktadoku octadoku = new Oktadoku(Oktadoku.Style.withDiagonals);
        assertFalse(octadoku.checkIfValidDiagonals(sampleBoard3));
    }

    //inputNotNegative
    @Test
    public void shouldReturnTrueGivenInputIsNotNegative() {
        Oktadoku octadoku = new Oktadoku(Oktadoku.Style.withDiagonals);
        assertTrue(octadoku.checkIfInputNotNegative(sampleBoard2));
    }

    @Test
    public void shouldReturnFalseGivenInputIsNegative() {
        Oktadoku octadoku = new Oktadoku(Oktadoku.Style.withDiagonals);
        assertFalse(octadoku.checkIfInputNotNegative(sampleBoard3));
    }

    //numberRange
    @Test
    public void shouldReturnTrueGivenCorrectNumberRange() {
        Oktadoku octadoku = new Oktadoku(Oktadoku.Style.withDiagonals);
        assertTrue(octadoku.checkIfCorrectNumberRange(sampleBoard2));
    }

    @Test
    public void shouldReturnFalseGivenIncorrectNumberRange() {
        Oktadoku octadoku = new Oktadoku(Oktadoku.Style.withDiagonals);
        assertFalse(octadoku.checkIfCorrectNumberRange(sampleBoard3));
    }


    private final int[][] sampleBoard1 = {
            {3,0,0,0,0,0,0,5},
            {0,0,7,6,0,0,0,0},
            {0,2,0,0,0,7,0,0},
            {0,5,0,4,0,0,6,1},
            {6,1,0,0,8,0,2,0},
            {0,0,4,0,0,0,8,0},
            {0,0,0,0,5,3,0,0},
            {2,0,0,0,0,0,0,6}
    };

    private final int[][] sampleBoard2 = {
            {0,6,0,1,0,0,0,0},
            {0,4,0,0,8,6,0,2},
            {4,0,0,3,5,8,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,4,0,0,0,0},
            {0,0,0,8,0,0,0,6},
            {7,0,1,0,0,0,2,8},
            {0,0,0,0,7,0,4,0}
    };

    private final int[][] sampleBoard3 = {
            {2,6,-8,1,0,9,0,0},
            {3,3,8,0,8,6,0,2},
            {4,2,8,3,5,8,0,0},
            {0,0,0,2,4,0,0,0},
            {0,10,0,4,-1,0,0,0},
            {0,7,2,8,0,3,0,6},
            {7,2,1,0,0,0,2,8},
            {0,0,0,0,7,0,4,1000}
    };
}