package k07.flashcards2;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.io.File;

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
        setupComponents();
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
                setButtonsEnabled(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error loading file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
        list.flip();
        flashcardPane.setText(list.getDisplayedText());
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

        flipButton.addActionListener(l -> flipCurrentCard());
        previousButton.addActionListener(l -> prevCard());
        nextButton.addActionListener(l -> nextCard());
        loadButton.addActionListener(l -> loadFromFile());
        shuffleButton.addActionListener(l -> shuffle());

        setButtonsEnabled(false);
    }

    private void setButtonsEnabled(boolean value) {
        flipButton.setEnabled(value);
        previousButton.setEnabled(value);
        nextButton.setEnabled(value);
        shuffleButton.setEnabled(value);
    }
}
