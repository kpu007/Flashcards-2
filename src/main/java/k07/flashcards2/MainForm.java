package k07.flashcards2;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainForm {
    private JButton flipButton;
    private JButton previousButton;
    private JButton nextButton;
    private JButton loadButton;
    private JPanel mainPanel;
    private JTextPane flashcardPane;
    private JLabel progressLabel;
    private JButton shuffleButton;

    private FlashcardList list;

    public MainForm() {
        List<FlashcardTuple> test = new ArrayList<FlashcardTuple>();
        test.add(new FlashcardTuple("a", "1"));
        test.add(new FlashcardTuple("b", "2"));
        list = new FlashcardList(test);

        setupComponents();
        updateCurrentCard();
    }

    public void setFlashcardList(FlashcardList flashcardList) {
        this.list = flashcardList;
        updateCurrentCard();
    }

    public void loadFromFile() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
        chooser.setFileFilter(filter);

        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();

            try {
                FlashcardList flashcardList = FlashcardListSerializer.createFromFile(file);
                setFlashcardList(flashcardList);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void shuffle() {
        list.shuffle();
        updateCurrentCard();
    }

    private void updateProgressLabel() {
        progressLabel.setText(list.getIndex() + 1 + "/" + list.getSize());
    }

    private void updateCurrentCard() {
        flashcardPane.setText(list.getCurrent().getFront());
        updateProgressLabel();
    }

    private void flipCurrentCard() {
        flashcardPane.setText(list.getCurrent().getBack());
    }

    private void nextCard() {
        list.next();
        updateCurrentCard();
    }

    private void prevCard() {
        list.prev();
        updateCurrentCard();
    }

    public void createMainForm() {
        JFrame frame = new JFrame("MainForm");
        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void setupComponents() {
        flashcardPane.setEditable(false);
        flashcardPane.setEditorKit(new VerticallyCenteredEditorKit());
        flashcardPane.setFont(flashcardPane.getFont().deriveFont(80.0F));
        StyledDocument doc = flashcardPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        updateProgressLabel();

        flipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flipCurrentCard();
            }
        });

        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prevCard();
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextCard();
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadFromFile();
            }
        });

        shuffleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shuffle();
            }
        });
    }
}
