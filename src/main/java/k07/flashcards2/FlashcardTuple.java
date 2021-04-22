package k07.flashcards2;

public class FlashcardTuple {
    private String front;
    private String back;

    public FlashcardTuple(String front, String back) {
        this.front = front;
        this.back = back;
    }

    public String getFront() {
        return this.front;
    }

    public String getBack() {
        return this.back;
    }

}
