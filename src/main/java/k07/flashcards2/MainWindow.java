package k07.flashcards2;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MainWindow extends JFrame {
    private JButton flipButton = new JButton("Flip");
    private JButton previousButton = new JButton("Previous");
    private JButton nextButton = new JButton("Next");
    private JButton loadButton = new JButton("Load");
    private JButton shuffleButton = new JButton("Shuffle");
    private JPanel mainPanel = new JPanel();
    private JTextPane flashcardPane = new JTextPane();
    private JLabel progressLabel = new JLabel("/");
    private JMenuBar menuBar = new JMenuBar();

    private JMenuItem addItem = new JMenuItem();
    private JMenuItem saveItem = new JMenuItem();

    private GridBagLayout layout = new GridBagLayout();
    private FlashcardList list;

    public MainWindow() {
        this.setTitle("Flashcards 2");
        this.setLayout(new BorderLayout());
        mainPanel.setLayout(layout);

        setupComponents();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
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
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error loading file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error loading file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void saveToFile() {
        if(list == null) {
            JOptionPane.showMessageDialog(null, "Nothing is currently loaded!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
            chooser.setFileFilter(filter);

            int returnVal = chooser.showOpenDialog(null);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();

                try {
                    FlashcardListSerializer.saveToFile(list, file);
                } catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error loading file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
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

    private void addCard() {
        if(list == null) {
            JOptionPane.showMessageDialog(null, "Nothing is currently loaded!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        FlashcardTuple flashcard = FlashcardAddDialog.getFlashcardFromDialog();

        if (flashcard != null) {
            list.add(flashcard);
            updateProgressLabel();
        }
    }

    private void addComponent(JComponent component, GridBagConstraints gbc, int x, int y) {
        addComponent(component, gbc, x, y, 1, 1);
    }

    private void addComponent(JComponent component, GridBagConstraints gbc, int x, int y, int width, int height) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        mainPanel.add(component, gbc);
    }

    private void setupComponents() {
        GridBagConstraints gbc = new GridBagConstraints();

        flashcardPane.setEditable(false);
        flashcardPane.setEditorKit(new VerticallyCenteredEditorKit());
        flashcardPane.setFont(flashcardPane.getFont().deriveFont(80.0F));
        StyledDocument doc = flashcardPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        this.addComponent(flashcardPane, gbc, 0, 0, 4, 5);

        //Allow the below components to resize horizontally while forcing all the extra vertical space into the flashcard pane
        gbc.weightx = 0.5;
        gbc.weighty = 0;
        gbc.insets = new Insets(3, 3, 3, 3); //external padding
        gbc.fill = GridBagConstraints.HORIZONTAL;

        flipButton.addActionListener(l -> flipCurrentCard());
        previousButton.addActionListener(l -> prevCard());
        nextButton.addActionListener(l -> nextCard());
        loadButton.addActionListener(l -> loadFromFile());
        shuffleButton.addActionListener(l -> shuffle());

        this.addComponent(loadButton, gbc, 0, 5, 2, 1);
        this.addComponent(shuffleButton, gbc, 2, 5, 2, 1);

        this.addComponent(flipButton, gbc, 0, 6);
        this.addComponent(previousButton, gbc, 1, 6);

        progressLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.addComponent(progressLabel, gbc, 2, 6);
        this.addComponent(nextButton, gbc, 3, 6);

        setButtonsEnabled(false);

        //File menu
        JMenuItem loadItem = new JMenuItem("Load");
        loadItem.addActionListener(l -> loadFromFile());
        loadItem.setMnemonic('l');

        saveItem = new JMenuItem("Save");
        saveItem.addActionListener(l -> saveToFile());
        saveItem.setMnemonic('s');
        saveItem.setEnabled(false);

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(l -> exit());
        exitItem.setMnemonic('x');

        JMenu fileMenu = new JMenu("File");
        fileMenu.add(loadItem);
        fileMenu.add(saveItem);
        fileMenu.add(new JSeparator());
        fileMenu.add(exitItem);

        //Edit menu
        addItem = new JMenuItem("Add");
        addItem.addActionListener(l -> addCard());
        addItem.setMnemonic('a');
        addItem.setEnabled(false);
        
        JMenu editMenu = new JMenu("Edit");
        editMenu.add(addItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        this.add(menuBar, BorderLayout.NORTH);
        this.add(mainPanel, BorderLayout.CENTER);
    }

    private void setButtonsEnabled(boolean value) {
        flipButton.setEnabled(value);
        previousButton.setEnabled(value);
        nextButton.setEnabled(value);
        shuffleButton.setEnabled(value);

        addItem.setEnabled(value);
        saveItem.setEnabled(value);
    }

    private void exit() {
        System.exit(0);
    }
}
