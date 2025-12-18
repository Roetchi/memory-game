public class Card {
    private int value;
    private boolean matched;

    public Card(int value) {
        this.value = value;
        this.matched = false;
    }

    public int getValue() {
        return value;
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }
}
