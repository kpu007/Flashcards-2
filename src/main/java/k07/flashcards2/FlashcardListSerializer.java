package k07.flashcards2;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FlashcardListSerializer {

    public static FlashcardList createFromFile(File input) throws IOException {
        FileReader reader = new FileReader(input);

        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
        List<FlashcardTuple> list = new ArrayList<FlashcardTuple>();
        for(CSVRecord record: records) {
            String front = record.get(0);
            String back = record.get(1);

            FlashcardTuple flashcard = new FlashcardTuple(front, back);
            list.add(flashcard);
        }

        return new FlashcardList(list);
    }
}
