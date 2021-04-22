package k07.flashcards2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FlashcardList {
    private List<FlashcardTuple> flashcards;
    private int index = 0;

    public FlashcardList(List<FlashcardTuple> flashcards) {
        this.flashcards = flashcards;
    }

    public void shuffle() {
        Collections.shuffle(flashcards);
        index = 0;
    }

    public FlashcardTuple getCurrent() {
        return flashcards.get(index);
    }

    public int getIndex() {
        return index;
    }

    public int getSize() {
        return flashcards.size();
    }

    public void next() {
        index++;

        if(index >= flashcards.size()) {
            index = 0;
        }
    }

    public void prev() {
        index--;

        if(index < 0) {
            index = flashcards.size() - 1;
        }
    }
}
