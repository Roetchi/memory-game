import java.util.ArrayList;
import java.util.Collections;

public class GameBoard {
    private Card[][] cards;
    private int rows;
    private int cols;
    private int totalPairs;

    public GameBoard(int rows, int cols) {
        if ((rows * cols) % 2 != 0) {
            throw new IllegalArgumentException("Number of cards must be even");
        }
        this.rows = rows;
        this.cols = cols;
        this.cards = new Card[rows][cols];
        this.totalPairs = (rows * cols) / 2;
        initBoard();
    }

    private void initBoard() {
        ArrayList<Integer> values = new ArrayList<>();
        for (int i = 1; i <= totalPairs; i++) {
            values.add(i);
            values.add(i);
        }
        Collections.shuffle(values);

        int index = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                cards[r][c] = new Card(values.get(index));
                index++;
            }
        }
    }

    public Card getCard(int row, int col) {
        return cards[row][col];
    }

    public int getTotalPairs() {
        return totalPairs;
    }
}
