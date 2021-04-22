package k07.flashcards2;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MainForm {
    private JButton flipButton;
    private JButton previousButton;
    private JButton nextButton;
    private JButton addButton;
    private JTextField textField1;
    private JTextField textField2;
    private JPanel mainPanel;
    private JTextPane flashcardPane;
    private JLabel progressLabel;

    private FlashcardList list;

    public MainForm() {
        List<FlashcardTuple> test = new ArrayList<FlashcardTuple>();
        test.add(new FlashcardTuple("a", "1"));
        test.add(new FlashcardTuple("b", "2"));
        list = new FlashcardList(test);

        setupComponents();
        updateCurrentCard();
    }

    private void updateProgressLabel() {
        progressLabel.setText(list.getIndex() + "/" + list.getSize());
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
    }
}
