package k07.flashcards2;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
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

    public static void saveToFile(FlashcardList list, File outputFile) throws IOException {
        CSVFormat format = CSVFormat.DEFAULT;
        FileWriter writer = new FileWriter(outputFile);
        CSVPrinter printer = new CSVPrinter(writer, format);

        printer.printRecords(list.createRecordArray());
        printer.flush();
        writer.close();

    }
}
