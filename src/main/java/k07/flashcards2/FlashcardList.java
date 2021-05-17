package k07.flashcards2;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FlashcardList {
    private List<FlashcardTuple> flashcards;
    private int index = 0;
    private boolean displayingFront = true;

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
        displayingFront = true;

        if(index >= flashcards.size()) {
            index = 0;
        }
    }

    public void prev() {
        index--;
        displayingFront = true;

        if(index < 0) {
            index = flashcards.size() - 1;
        }
    }

    public void add(FlashcardTuple flashcard) {
        flashcards.add(flashcard);
    }

    public void flip() {
        displayingFront = !displayingFront;
    }

    public String getDisplayedText() {
        return displayingFront ? getCurrent().getFront() : getCurrent().getBack();
    }

    //Used for writing to CSV
    public List<Object[]> createRecordArray() {
        List<Object[]> result = new ArrayList<>();

        for(FlashcardTuple flashcard: flashcards) {
            result.add(new Object[] {flashcard.getFront(), flashcard.getBack()});
        }

        return result;
    }
}
