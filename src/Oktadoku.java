import java.util.BitSet;
import java.util.Scanner;

class Oktadoku {

    public enum Style {
        normal,
        withDiagonals
    }
    public Style style;
    public int[][] board;

    public Oktadoku(Style style) {
        this.style = style;
        this.board = new int[8][8];
    }

    public void getUserInput() {
        System.out.println("User Input: ");
        Scanner scan = new Scanner(System.in);
        for (int row = 0; row <= 7; row +=1) {
            String userInput = scan.next();
            String userInputWithoutDots = userInput.replace(".", "0");
            for (int col = 0; col <= 7; col += 1) {
                board[row][col] = userInputWithoutDots.charAt(col) - 48;
            }
        }
    }

    public void printBoard() {
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

    public void solveOktadoku() {
        checkIfSolvable(board);
        if(!checkIfAllFieldsHaveNumbers(board)) {
            System.out.println("not solvable :-(");
            return;
        }
        System.out.println();
        System.out.println("Solution: ");
        printBoard();
    }

    private boolean checkIfSolvable(int[][] board) {
        int cardinality = calcLowestCardinality(board);

        if (cardinality == 0) {
            return false;
        }

        int[][] copyBoard = new int[8][8];

        for (int x = 0; x < cardinality; x += 1) {
            duplicateBoard(board, copyBoard);
            if (checkIfPosCanBeChosenFromCardinality(copyBoard, x, cardinality)) {
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

    private boolean checkIfPosCanBeChosenFromCardinality(int[][] board, int x, int currCardinality) {
        for (int col = 0; col < board.length; col++) {
            for (int row = 0; row < board.length; row++) {
                if (board[row][col] == 0){
                    BitSet placeable = placeableAtField(board, row, col);

                    if (placeable.cardinality() == currCardinality) {
                        int currNoToBePlaced = placeable.stream().toArray()[x];
                        board[row][col] = currNoToBePlaced;
                        return checkIfSolvable(board);
                    }
                }
            }
        }
        return true;
    }

    private int calcLowestCardinality(int[][] board) {
        int lowestCardinality = 9;

        for (int col = 0; col < board.length; col+=1) {
            for (int row = 0; row < board.length; row+=1) {
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
        if (this.style == Style.withDiagonals) {
            return !checkIfNumberInColumn(board, number, col)
                && !checkIfNumberInRow(board, number, row)
                && !checkIfNumberInBox(board, number, row, col)
                && !checkIfNumberInDiagonal(board, number, row, col);
        }

        return !checkIfNumberInColumn(board, number, col)
            && !checkIfNumberInRow(board, number, row)
            && !checkIfNumberInBox(board, number, row, col);
    }

    public boolean checkIfValidOktadoku() {
        if (this.style == Style.withDiagonals) {
             return checkIfValidRows(board)
                && checkIfValidColumns(board)
                && checkIfInputNotNegative(board)
                && checkIfCorrectNumberRange(board)
                && checkIfValidDiagonals(board);
        }

        return checkIfValidRows(board)
            && checkIfValidColumns(board)
            && checkIfInputNotNegative(board)
            && checkIfCorrectNumberRange(board)
            && checkIfCorrectNumberRange(board);
    }



       
    public boolean checkIfValidRows(int[][] board) {
        int counter = 0;
        for (int number = 1; number <= 8; number += 1) {
            for (int row = 0; row < board.length; row +=1) {
                for (int col = 0; col < board[row].length; col += 1) {
                    if(board[row][col] == number) {
                        counter++;
                    }
                }
                if (counter > 1) {
                    return false;
                }
                counter = 0;
            }
        }
        return true;
    }

    public boolean checkIfValidColumns(int[][] board) {
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
        }
        return true;
    }


    public boolean checkIfValidDiagonals(int[][] board) {
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


    public boolean checkIfInputNotNegative(int[][] board) {
        for (int row = 0; row < board.length; row +=1){
            for (int col = 0; col < board[row].length; col += 1) {
                if (board[row][col] < 0) {
                    return false;
                }
            }
        }
        return true;
    }


    public boolean checkIfCorrectNumberRange(int[][] board) {
        for (int row = 0; row < board.length; row +=1){
            for (int col = 0; col < board[row].length; col += 1) {
                if (board[row][col] > 8) {
                    return false;
                }
            }
        }
        return true;
    }


    public boolean checkIfNumberInColumn(int[][] board, int number, int col) {
        for (int row = 0; row < board.length; row++){
            if (board[row][col] == number)
                return true;
        }
        return false;
    }


    public boolean checkIfNumberInRow(int[][] board, int number, int row){
        for (int col = 0; col < board.length; col++){
            if (board[row][col] == number)
                return true;
        }
        return false;
    }

    private boolean isInFirstDiagonal(int row, int col) {
        return row == col;
    }

    private boolean isInSecondDiagonal(int row, int col) {
        return row + col == 7;
    }

    public boolean checkIfNumberInDiagonal(int[][] board, int number, int row, int col) {
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

    public void duplicateBoard(int[][] board, int[][] duplicateBoard) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                duplicateBoard[row][col] = board[row][col];
            }
        }
    }
}

