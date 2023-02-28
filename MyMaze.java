// Names: Ahmed Shahkhan, Akshay Peddi
// x500s: shahk005, peddi022

import java.util.Random;

public class MyMaze{
    Cell[][] maze;
    int startRow;
    int endRow;

    public MyMaze(int rows, int cols, int startRow, int endRow) {
        maze = new Cell[rows][cols]; // Makes the array
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                maze[i][j] = new Cell(); // Goes through every cell and instantiates it as a Cell() so that it has all three attributes
            }
        }
        this.startRow = startRow;
        this.endRow = endRow;
    }

    public static int genRandomNum(){ // HELPER FUNCTION: randomly returns a number between 0 - 3
        Random rand = new Random();
        int intRandom = rand.nextInt(4); //4 is upper bound (exclusive)
        return intRandom;
    }

    /* TODO: Create a new maze using the algorithm found in the writeup. */
    public static MyMaze makeMaze(int rows, int cols, int startRow, int endRow) {

        // instantiate the MyMaze:
        MyMaze mazeExample = new MyMaze(rows, cols, startRow, endRow);

        // generate the MyMaze:
        Stack1Gen<int[]> stack = new Stack1Gen<>();
        stack.push(new int[] {startRow, 0});
        mazeExample.maze[startRow][0].setVisited(true);
        int length = mazeExample.maze[0].length;
        mazeExample.maze[endRow][length - 1].setRight(false);
        while (stack.isEmpty() != true) {

            int[] top = stack.top();
            //Randomly gen a 0(U), 1(R), 2(D), 3(L)
            /* 0 = Check UP
               1 = Check RIGHT
               2 = Check DOWN
               3 = CHeck LEFT
             */
            boolean valid = false; // boolean variable is used to check whether we got a random spot that works

            while (valid == false) {
                int num = genRandomNum(); // Helper Function called from Line 22
                int indexRow = top[0]; // row index of current cell
                int indexCol = top[1]; // column index of current cell
                int count = 0; // this is used as a counter to see how many spots are available

                if (indexRow - 1 >= 0 && mazeExample.maze[indexRow - 1][indexCol].getVisited() == false) { // && num == 0 UP
                    count++;
                    if (num == 0) {
                        valid = true;
                        stack.push(new int[] {indexRow - 1, indexCol});
                        mazeExample.maze[indexRow - 1][indexCol].setVisited(true);
                        mazeExample.maze[indexRow - 1][indexCol].setBottom(false);
                    }
                }
                if (indexCol + 1 < cols && mazeExample.maze[indexRow][indexCol + 1].getVisited() == false ) { //&& num == 1 RIGHT
                    count++;
                    if (num == 1) {
                        valid = true;
                        stack.push(new int[] {indexRow, indexCol + 1});
                        mazeExample.maze[indexRow][indexCol + 1].setVisited(true);
                        mazeExample.maze[indexRow][indexCol].setRight(false);
                    }
                }
                if (indexRow + 1 < rows && mazeExample.maze[indexRow + 1][indexCol].getVisited() == false ) { // && num == 2 DOWN
                    count++;
                    if (num == 2) {
                        valid = true;
                        stack.push(new int[] {indexRow + 1, indexCol});
                        mazeExample.maze[indexRow + 1][indexCol].setVisited(true);
                        mazeExample.maze[indexRow][indexCol].setBottom(false);
                    }
                }
                if (indexCol - 1 >= 0 && mazeExample.maze[indexRow][indexCol - 1].getVisited() == false ) { // && num == 3 LEFT
                    count++;
                    if (num == 3) {
                        valid = true;
                        stack.push(new int[] {indexRow, indexCol - 1});
                        mazeExample.maze[indexRow][indexCol - 1].setVisited(true);
                        mazeExample.maze[indexRow][indexCol - 1].setRight(false);
                    }
                }
                if (count == 0) { // if no spots turn out to be available, then current index pair is popped
                    valid = true;
                    stack.pop();
                }
            }
        }
        for (int i = 0; i < rows; i++) { // sets every cell's visited attribute to false
            for (int j = 0; j < cols; j++) {
                mazeExample.maze[i][j].setVisited(false);
            }
        }
        return mazeExample;
    }

    /* TODO: Print a representation of the maze to the terminal */
    public void printMaze() {
        int length = maze[0].length;
        System.out.print("|"); // Top left of maze
        for (int i = 0; i < length; i++) { // Fills top border
            System.out.print("---|");
        }
        System.out.println(""); // New line
        for (int i = 0; i < maze.length; i++) {
            if (i == startRow) { // Creates entrance
                System.out.print(" ");
            }
            else {
                System.out.print("|"); // Left Border
            }

            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j].getVisited() == true) { // * for if Visited
                    System.out.print(" * ");
                } else if (maze[i][j].getVisited() == false){ // space for not Visited
                    System.out.print("   ");
                }
                if (maze[i][j].getRight() == true) { // right wall between cells
                    System.out.print("|");
                }
                else {
                    System.out.print(" "); // No right wall between cells
                }
            }
            System.out.println(""); // New Line
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j].getBottom() == true) {
                    System.out.print("|---"); // bottom wall between cells
                }
                else {
                    System.out.print("|   "); // No bottom wall between cells
                }

            }
            System.out.print("|"); // Right Border
            System.out.println(""); // New Line
        }
    }

    /* TODO: Solve the maze using the algorithm found in the writeup. */
    public void solveMaze() {
        Q2Gen<int[]> queue = new Q2Gen<>();
        queue.add(new int[] {startRow, 0});
        while (queue.length() != 0){
            int[] frontIndex = queue.remove();
            int indexRow = frontIndex[0]; // row index of current cell
            int indexCol = frontIndex[1]; // column index of current cell
            maze[indexRow][indexCol].setVisited(true);
            if (indexRow == endRow && indexCol == maze[0].length - 1){ //breaks out of the loop if maze is solved
                break;
            }

            // Adding all reachable, unvisited neighbors to the queue

            if (indexRow - 1 >= 0 && maze[indexRow - 1][indexCol].getVisited() == false && maze[indexRow - 1][indexCol].getBottom() == false){
                queue.add(new int[] {indexRow - 1, indexCol});
            }
            if (indexCol - 1 >= 0 && maze[indexRow][indexCol - 1].getVisited() == false && maze[indexRow][indexCol - 1].getRight() == false ){
                queue.add(new int[] {indexRow, indexCol - 1});
            }
            if (indexRow + 1 < maze.length && maze[indexRow + 1][indexCol].getVisited() == false  && maze[indexRow][indexCol].getBottom() == false){
                queue.add(new int[] {indexRow + 1, indexCol});
            }
            if (indexCol + 1 < maze[0].length && maze[indexRow][indexCol + 1].getVisited() == false && maze[indexRow][indexCol].getRight() == false){
                queue.add(new int[] {indexRow, indexCol + 1});
            }
        }
        printMaze();
    }

    public static void main(String[] args){
        /* Any testing can be put in this main function */
        MyMaze mazeExample = new MyMaze(10, 10, 0, 4);
        mazeExample = makeMaze(5, 20, 0, 4);
        mazeExample.solveMaze();
    }
}