import gdp.stdlib.StdIn; 
import java.util.BitSet;

class Oktadoku {

    public enum Variante {
        normal, 
        withDiagonals
    }
    private Variante v;
    private int[][] board;


    public Oktadoku(Variante var) {
        this.v = var;
        this.board = new int[8][8];
        
    }

    public void read() {
        getInput(board);
    }

    public void write() {
        printBoard(board);
    }

    public boolean check() {
        if (validateInput(board))
            return true; 

        return false;
    }

    public void solve() {
        runTests();
        fill(board);
        if(!checkIfAllFieldsHaveNumbers(board)) {
            System.out.println("not solvable :-(");
            return;
        }
        printBoard(board);
    }

    public void getInput(int[][] board){
        String input = StdIn.readAll();
        changePointsToZeros(board, input);
    }   

    private void printBoard(int[][] board) {
        if (this.v == Variante.withDiagonals)
            System.out.println("Oktadoku with diagonals");
        else System.out.println("Oktadoku");

        System.out.println("+-----+-----+-----+-----+");
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (col % 2 == 0) {
                    System.out.print("| ");   
                } else {
                    System.out.print(" ");
                }

                System.out.print(board[row][col]);

                if (col % 2 != 0) {
                    System.out.print(" ");
                }

                if (col == board[row].length - 1) {
                    System.out.print("|");
                }
            }

            System.out.println();

            if (row == 3) {
                System.out.println("+-----+-----+-----+-----+");
            }
        }
        
        System.out.println("+-----+-----+-----+-----+");
    }

    private void runTests(){
        testCheckIfNumberInColumn();
        testCheckIfNumberInRow();
        testCheckIfNumberInDiagonal();
        testCheckIfValidRows();
        testCheckIfValidColumns();
        testCheckIfValidDiagonals();
        testCheckIfInputNotNegative();
        testCheckCorrectNumberRange();
    }


     

    private void changePointsToZeros(int[][] board, String input){
        String inputWithZeros = "";
        inputWithZeros = input.replace(".", "0");
        divideInputToSubstrings(board, inputWithZeros);
    }
    
    private void divideInputToSubstrings(int[][] board, String input){
        String rowContent = "";
        for (int row = 0; row <= 7; row++) {
            rowContent = input.substring(0,8);
            writeSubstringToBoard(board, rowContent, row);
            input = input.substring(9, input.length());
        }
    }
   
    private void writeSubstringToBoard(int[][] board, String rowContent, int row){
        for (int col = 0; col < rowContent.length(); col++){
            board[row][col] = rowContent.charAt(col) - 48;
            }
    }

    private void duplicateBoard(int[][] board, int[][] duplicateBoard) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                duplicateBoard[row][col] = board[row][col];
            }
        }
    }

    

    private boolean fill(int[][] board) {
        int cardinality = fillSimple(board);

        if (cardinality == 0) {
            return false;
        }

        int[][] copyBoard = new int[8][8];

        for (int x = 0; x < cardinality; x += 1) {
            duplicateBoard(board, copyBoard);
            if (chooseXFromY(copyBoard, x, cardinality)) {
                duplicateBoard(copyBoard, board);
                return true;
            }
        }

        return false;
    }

    private BitSet placeableAtField(int[][] board, int row, int col) {
        BitSet placeable = new BitSet();

        for (int workNumber = 1; workNumber <= 8; workNumber++) {
            if (canNumberBePlacedHere(board, workNumber, row, col)) {
                placeable.set(workNumber);
            }
        }

        return placeable;
    }

    private boolean chooseXFromY(int[][] board, int x, int y){
        for (int col = 0; col < board.length; col++){
            for (int row = 0; row < board.length; row++){
                if (board[row][col] == 0){
                    BitSet placeable = placeableAtField(board, row, col);

                    if (placeable.cardinality() == y) {
                        int current = placeable.stream().toArray()[x];

                        board[row][col] = current;                        

                        return fill(board);
                    }
                }
            }
        }

        return true;
    }

    private int fillSimple(int[][] board){
        int lowestCardinality = 9;

        for (int col = 0; col < board.length; col++){
            for (int row = 0; row < board.length; row++){
                if (board[row][col] == 0){
                    BitSet placeable = placeableAtField(board, row, col);
                    int cardinality = placeable.cardinality();

                    if (cardinality == 0) {
                        return 0;
                    }

                    if (cardinality == 1) {
                        int number = placeable.nextSetBit(0);

                        board[row][col] = number;

                        row = 0;
                        col = 0;
                        lowestCardinality = 9;

                    } else {
                        lowestCardinality = Math.min(cardinality, lowestCardinality);
                    }
                }
            }
        }

        return lowestCardinality;
    }
    
    private boolean canNumberBePlacedHere(int[][] board, int number, int row, int col) {
        if (this.v == Variante.withDiagonals) {
            return !checkIfNumberInColumn(board, number, col)
                && !checkIfNumberInRow(board, number, row)
                && !checkIfNumberInBox(board, number, row, col)
                && !checkIfNumberInDiagonal(board, number, row, col);
        }

        return !checkIfNumberInColumn(board, number, col)
            && !checkIfNumberInRow(board, number, row)
            && !checkIfNumberInBox(board, number, row, col);
    }

    private int[][] sampleBoard1 = {
        {3,0,0,0,0,0,0,5},
        {0,0,7,6,0,0,0,0},
        {0,2,0,0,0,7,0,0},
        {0,5,0,4,0,0,6,1}, 
        {6,1,0,0,8,0,2,0},
        {0,0,4,0,0,0,8,0},
        {0,0,0,0,5,3,0,0},
        {2,0,0,0,0,0,0,6}
    };

    private int[][] sampleBoard2 = {
        {0,6,0,1,0,0,0,0},
        {0,4,0,0,8,6,0,2},
        {4,0,0,3,5,8,0,0},
        {0,0,0,0,0,0,0,0}, 
        {0,0,0,4,0,0,0,0},
        {0,0,0,8,0,0,0,6},
        {7,0,1,0,0,0,2,8},
        {0,0,0,0,7,0,4,0}
    };

    private int[][] sampleBoard3 = {
        {2,6,-8,1,0,9,0,0},
        {3,3,8,0,8,6,0,2},
        {4,2,8,3,5,8,0,0},
        {0,0,0,2,4,0,0,0}, 
        {0,10,0,4,-1,0,0,0},
        {0,7,2,8,0,3,0,6},
        {7,2,1,0,0,0,2,8},
        {0,0,0,0,7,0,4,1000}
    };
    
   
    private boolean validateInput(int[][] board) {
        if (this.v == Variante.withDiagonals) {
             return checkIfValidRows(board)
                && checkIfValidColums(board)
                && checkIfInputNotNegative(board)
                && checkCorrectNumberRange(board)
                && checkIfValidDiagonals(board);
        }

        return checkIfValidRows(board)
            && checkIfValidColums(board)
            && checkIfInputNotNegative(board)
            && checkCorrectNumberRange(board)
            && checkCorrectNumberRange(board);
    }


    private void testCheckIfValidRows() {
        test1checkIfValidRows();
        test2checkIfValidRows();
    }


    private void test1checkIfValidRows() {
        boolean expected = true;
        boolean result = checkIfValidRows(sampleBoard2);

        if (result != expected) {
            throw new RuntimeException("Test failed with result " + result + " and expected " + expected);
        }
    }

    private void test2checkIfValidRows() {
        boolean expected = false;
        boolean result = checkIfValidRows(sampleBoard3);

        if (result != expected) {
            throw new RuntimeException("Test failed with result " + result + " and expected " + expected);
        }
    }

       
    private boolean checkIfValidRows(int[][] board) {
        int counter = 0; 
        
        for (int number = 1; number <= 8; number += 1) {
            for (int row = 0; row < board.length; row +=1){
                for (int col = 0; col < board[row].length; col += 1){
                    if(board[row][col] == number) {
                        counter++;
                    }
                }
                if (counter > 1) {
                    return false;
                }

                counter = 0;
            }
            counter = 0;
        }
        return true;
    }

    private void testCheckIfValidColumns() {
        test1checkIfValidColumns();
        test2checkIfValidColumns();
    }


    private void test1checkIfValidColumns() {
        boolean expected = true;
        boolean result = checkIfValidColums(sampleBoard2);

        if (result != expected) {
            throw new RuntimeException("Test failed with result " + result + " and expected " + expected);
        }
    }

    private void test2checkIfValidColumns() {
        boolean expected = false;
        boolean result = checkIfValidColums(sampleBoard3);

        if (result != expected) {
            throw new RuntimeException("Test failed with result " + result + " and expected " + expected);
        }
    }

    private boolean checkIfValidColums(int[][] board) {
        int counter = 0; 
        
        for (int number = 1; number <= 8; number += 1) {
            for (int col = 0; col < board.length; col += 1){
                for (int row = 0; row < board[col].length; row +=1){
                    if(board[row][col] == number) {
                        counter++;
                    }
                }
                if (counter > 1) {
                    return false;
                }

                counter = 0;
            }
            counter = 0;
        }
        return true;
    }

    private void testCheckIfValidDiagonals() {
        test1checkIfValidDiagonals();
        test2checkIfValidDiagonals();
    }


    private void test1checkIfValidDiagonals() {
        boolean expected = true;
        boolean result = checkIfValidDiagonals(sampleBoard2);

        if (result != expected) {
            throw new RuntimeException("Test failed with result " + result + " and expected " + expected);
        }
    }

    private void test2checkIfValidDiagonals() {
        boolean expected = false;
        boolean result = checkIfValidDiagonals(sampleBoard3);

        if (result != expected) {
            throw new RuntimeException("Test failed with result " + result + " and expected " + expected);
        }
    }

    private boolean checkIfValidDiagonals(int[][] board) {
        int counter = 0;
        
        for (int number = 1; number <= 8; number += 1) {
            for (int row = 0; row < board.length; row++) {
                if (board[row][row] == number) 
                    counter++;
            }

            if (counter >= 2) {
                return false;
            }
            counter = 0;

            for (int diag = 0; diag < board.length; diag++) {
                    if (board[diag][7 - diag] == number)
                        counter++;
                }
            
            if (counter >= 2) {
                return false;
            }
            counter = 0;

        }
        return true;
    }

    private void testCheckIfInputNotNegative() {
        test1checkIfInputNotNegative1();
        test2checkIfInputNotNegative2();
    }


    private void test2checkIfInputNotNegative2() {
        boolean expected = true;
        boolean result = checkIfInputNotNegative(sampleBoard2);

        if (result != expected) {
            throw new RuntimeException("Test failed with result " + result + " and expected " + expected);
        }
    }

    private void test1checkIfInputNotNegative1() {
        boolean expected = false;
        boolean result = checkIfInputNotNegative(sampleBoard3);

        if (result != expected) {
            throw new RuntimeException("Test failed with result " + result + " and expected " + expected);
        }
    }

    private boolean checkIfInputNotNegative(int[][] board) {
        for (int row = 0; row < board.length; row +=1){
            for (int col = 0; col < board[row].length; col += 1) {
                if (board[row][col] < 0) {
                    return false;
                }
            }
        }

        return true;
    }

    private void testCheckCorrectNumberRange() {
        test1CorrectNumberRange();
        test2CorrectNumberRange();
    }


    private void test1CorrectNumberRange() {
        boolean expected = true;
        boolean result = checkCorrectNumberRange(sampleBoard2);

        if (result != expected) {
            throw new RuntimeException("Test failed with result " + result + " and expected " + expected);
        }
    }

    private void test2CorrectNumberRange() {
        boolean expected = false;
        boolean result = checkCorrectNumberRange(sampleBoard3);

        if (result != expected) {
            throw new RuntimeException("Test failed with result " + result + " and expected " + expected);
        }
    }

    private boolean checkCorrectNumberRange(int[][] board) {
        for (int row = 0; row < board.length; row +=1){
            for (int col = 0; col < board[row].length; col += 1) {
                if (board[row][col] > 8) {
                    return false;
                }
            }
        }

        return true;
    }

    private void testCheckIfNumberInColumn() {
        test1Column();
        test2Column();
        test3Column();
        test4Column();
    }

    private void test1Column() {
        int number = 3;
        int col = 1;

        boolean expected = false;
        boolean result = checkIfNumberInColumn(sampleBoard1, number, col);

        if (result != expected) {
            throw new RuntimeException("Test failed with result " + result + " and expected " + expected);
        }
    }

    private void test2Column() {
        int number = 5;
        int col = 1;

        boolean expected = true;
        boolean result = checkIfNumberInColumn(sampleBoard1, number, col);

        if (result != expected) {
            throw new RuntimeException("Test failed with result " + result + " and expected " + expected);
        }
    }

    private void test3Column() {
        int number = 6;
        int col = 7;

        boolean expected = true;
        boolean result = checkIfNumberInColumn(sampleBoard1, number, col);

        if (result != expected) {
            throw new RuntimeException("Test failed with result " + result + " and expected " + expected);
        }
    }

    private void test4Column() {
        int number = 3;
        int col = 7;

        boolean expected = false;
        boolean result = checkIfNumberInColumn(sampleBoard1, number, col);

        if (result != expected) {
            throw new RuntimeException("Test failed with result " + result + " and expected " + expected);
        }
    }

    private boolean checkIfNumberInColumn(int[][] board, int number, int col) {
        for (int row = 0; row < board.length; row++){
            if (board[row][col] == number)
                return true;
        }
        return false;
    }

    private void testCheckIfNumberInRow() {
        test1Row();
        test2Row();
        test3Row();
        test4Row();
    }

    private void test1Row() {
        int number = 3;
        int row = 1;

        boolean expected = false;
        boolean result = checkIfNumberInRow(sampleBoard1, number, row);

        if (result != expected) {
            throw new RuntimeException("Test failed with result " + result + " and expected " + expected);
        }
    }
    private void test2Row() {
        int number = 7;
        int row = 1;

        boolean expected = true;
        boolean result = checkIfNumberInRow(sampleBoard1, number, row);

        if (result != expected) {
            throw new RuntimeException("Test failed with result " + result + " and expected " + expected);
        }
    }
    private void test3Row() {
        int number = 6;
        int row = 7;

        boolean expected = true;
        boolean result = checkIfNumberInRow(sampleBoard1, number, row);

        if (result != expected) {
            throw new RuntimeException("Test failed with result " + result + " and expected " + expected);
        }
    }
    private void test4Row() {
        int number = 3;
        int row = 7;

        boolean expected = false;
        boolean result = checkIfNumberInRow(sampleBoard1, number, row);

        if (result != expected) {
            throw new RuntimeException("Test failed with result " + result + " and expected " + expected);
        }
    }

    private boolean checkIfNumberInRow(int[][] board, int number, int row){
        for (int col = 0; col < board.length; col++){
            if (board[row][col] == number)
                return true;
        }
        return false;
    }

    private void testCheckIfNumberInDiagonal() {
        test1Diagonal();
        test2Diagonal();
        test3Diagonal();
        test4Diagonal();
        test5Diagonal();
    }

    private void test1Diagonal() {
        int number = 3;
        int row = 1;
        int col = 1;

        boolean expected = true;
        boolean result = checkIfNumberInDiagonal(sampleBoard1, number, row, col);

        if (result != expected) {
            throw new RuntimeException("Test failed with result " + result + " and expected " + expected);
        }
    }

    private void test2Diagonal() {
        int number = 3;
        int row = 7;
        int col = 6;

        boolean expected = false;
        boolean result = checkIfNumberInDiagonal(sampleBoard1, number, row, col);

        if (result != expected) {
            throw new RuntimeException("Test failed with result " + result + " and expected " + expected);
        }
    }

    private void test3Diagonal() {
        int number = 3;
        int row = 0;
        int col = 7;

        boolean expected = false;
        boolean result = checkIfNumberInDiagonal(sampleBoard1, number, row, col);

        if (result != expected) {
            throw new RuntimeException("Test failed with result " + result + " and expected " + expected);
        }
    }

    private void test4Diagonal() {
        int number = 5;
        int row = 0;
        int col = 7;

        boolean expected = true;
        boolean result = checkIfNumberInDiagonal(sampleBoard1, number, row, col);

        if (result != expected) {
            throw new RuntimeException("Test failed with result " + result + " and expected " + expected);
        }
    }

    private void test5Diagonal() {
        int number = 3;
        int row = 3;
        int col = 3;

        boolean expected = true;
        boolean result = checkIfNumberInDiagonal(sampleBoard1, number, row, col);

        if (result != expected) {
            throw new RuntimeException("Test failed with result " + result + " and expected " + expected);
        }
    }

    private boolean isInFirstDiagonal(int row, int col) {
        if (row == col) {
            return true;
        }

        return false;
    }

    private boolean isInSecondDiagonal(int row, int col) {
        if (row + col == 7) {
            return true;
        }

        return false;
    }

    private boolean checkIfNumberInDiagonal(int[][] board, int number, int row, int col) {
        if (isInFirstDiagonal(row, col)) {
            for (int diag = 0; diag < board.length; diag++){
                if (board[diag][diag] == number)
                    return true;
            }
        }

        if (isInSecondDiagonal(row, col)) {
            for (int diag = 0; diag < board.length; diag++){
                if (board[diag][7 - diag] == number)
                    return true;
            }
        }
        
        return false;
    }


    private int rowBoxStart(int row) {
        //top1-4
        if (row <= 3) {
            return 0;
        }

        //bottom1-4
        return 4;
    }

    private int columnBoxStart(int col) {
        //bottom1
        //top1
        if (col <= 1) {
            return 0;
        }
        //bottom2
        //top2
        if (col <= 3) {
            return 2;
        }
        //bottom3
        //top3
        if (col <= 5) {
            return 4;
        }

        //bottom4
        //top4
        return 6;
    }

    private boolean checkIfNumberInBox(int[][] board, int number, int row, int col){
        int rowStart = rowBoxStart(row);
        int columnStart = columnBoxStart(col);

        for (int boxRow = rowStart; boxRow <= rowStart + 3; boxRow++) {
            for (int boxCol = columnStart; boxCol <= columnStart + 1; boxCol++) {
                if (board[boxRow][boxCol] == number) {
                    return true;
                }
            }
        }

        return false;
    }


    private boolean checkIfAllFieldsHaveNumbers(int[][] board){
        int counter = 0;
        for (int row = 0; row < board.length; row++){
            for (int col = 0; col < board.length; col++){
                if (board[row][col] == 0)
                    counter++;
            }
        }
        if (counter > 0) {
            return false;
        }

        return true;
    }
}

