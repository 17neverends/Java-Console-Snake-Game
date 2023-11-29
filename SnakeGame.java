import java.util.Random;
import java.util.Scanner;


public class SnakeGame {
    public static final String RESET = "\u001B[0m";

    public static final String RED2 = "\u001B[101m";
    public static final String RED1 = "\u001B[91m";

    public static final String GREEN2 = "\u001B[42m";
    public static final String GREEN1 = "\u001B[32m";


    public static final String WHITE1 = "\u001B[37m";
    public static final String WHITE2 = "\u001B[47m";


    public static final String BLACK1 = "\u001B[95m";
    public static final String BLACK2 = "\u001B[105m";



    public static final String YELLOW1 = "\u001B[92m";
    public static final String YELLOW2 = "\u001B[102m";


    Node head;

    Node tail;
    int rows = 12;
    static int score = 0;
    int columns = 12;
    int[][] matrix = new int[rows][columns];
    String[][] matrix2 = new String[rows][columns];
    String[][] matrix3 = new String[rows][columns];
    Random random = new Random();
    Node apple;

    static class Node {
        int row;
        int col;
        Node next;

        Node(int row, int col) {
            this.row = row;
            this.col = col;
            this.next = null;
        }
    }

    public SnakeGame() {
        int startRow = 6;
        int startlCol = 6;
        this.head = new Node(startRow, startlCol);
        this.tail = head;
        placeFood();
        updateMatrix();
    }

    public void addNode(int row, int col) {
        Node newNode = new Node(row, col);
        newNode.next = head;
        head = newNode;

        if (head.next != null && (row == apple.row && col == apple.col)) {
            tail = newNode;
        }
    }


    public void placeFood() {
        int foodRow, foodCol;

        do {
            foodRow = random.nextInt(rows - 2) + 1;
            foodCol = random.nextInt(columns - 2) + 1;
        } while (matrix[foodRow][foodCol] != 0);

        apple = new Node(foodRow, foodCol);
        matrix[foodRow][foodCol] = 2;
    }


    public void updateMatrix() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (i == 0 || i == rows - 1 || j == 0 || j == columns - 1) {
                    matrix[i][j] = 7;
                    matrix2[i][j] = BLACK1;
                    matrix3[i][j] = BLACK2;
                } else {
                    matrix[i][j] = 0;
                    matrix2[i][j] = WHITE1;
                    matrix3[i][j] = WHITE2;
                }
            }
        }

        Node current = head;
        while (current != null) {
            matrix[current.row][current.col] = 1;
            matrix2[current.row][current.col] = GREEN1;
            matrix3[current.row][current.col] = GREEN2;
            current = current.next;
        }

        assert head != null;
        matrix2[head.row][head.col] = YELLOW1;
        matrix2[apple.row][apple.col] = RED1;
        matrix3[head.row][head.col] = YELLOW2;
        matrix3[apple.row][apple.col] = RED2;
        matrix[apple.row][apple.col] = 2;
    }

    public void move(char direction) {
        int newHeadRow = head.row;
        int newHeadCol = head.col;

        switch (direction) {
            case 'W', 'w','ц','Ц':
                newHeadRow--;
                break;
            case 'S','s','ы','Ы':
                newHeadRow++;
                break;
            case 'A','a','ф','Ф':
                newHeadCol--;
                break;
            case 'D','d','в','В':
                newHeadCol++;
                break;
        }

        if (newHeadRow < 0 || newHeadRow >= rows || newHeadCol < 0 || newHeadCol >= columns
                || matrix[newHeadRow][newHeadCol] == 1 || matrix[newHeadRow][newHeadCol] == 7) {
            System.out.println("Игра окончена! Попробуй еще раз!!! <3");
            System.exit(0);
        }

        addNode(newHeadRow, newHeadCol);

        if (newHeadRow == apple.row && newHeadCol == apple.col) {
            score++;
            placeFood();
        } else {
            Node node = head;
            while (node.next.next != null) {
                node = node.next;
            }
            node.next = null;
        }


        updateMatrix();
        printMatrix();

    }

    public void printMatrix() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print(matrix2[i][j] + matrix3[i][j]+ matrix[i][j]+"  " + RESET);
            }
            System.out.println();
        }
    }


    public static void main(String[] args) {
        SnakeGame snakeGame = new SnakeGame();
        snakeGame.printMatrix();
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\nСчёт:" + score);
                System.out.println("""

                        W - вверх

                        A - влево

                        S - вниз

                        D - вправо

                        Выбери действие (W/A/S/D):\s""");
                char direction = scanner.next().charAt(0);
                snakeGame.move(direction);
            }
        }
    }
}
