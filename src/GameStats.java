public class GameStats {
    private int moves;
    private int pairsFound;

    public void incrementMoves() {
        moves++;
    }

    public void incrementPairsFound() {
        pairsFound++;
    }

    public int getMoves() {
        return moves;
    }

    public int getPairsFound() {
        return pairsFound;
    }
}
