package k07.flashcards2;

import javax.swing.*;
import java.awt.*;

public class FlashcardAddDialog {

    public static FlashcardTuple getFlashcardFromDialog() {
        JTextField frontField = new JTextField(20);
        JTextField backField = new JTextField(20);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(3, 3, 3, 3);
        panel.add(new JLabel("Front:"), gbc);

        gbc.gridy = 1;
        panel.add(new JLabel("Back:"), gbc);

        gbc.weightx = 1;
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(frontField, gbc);

        gbc.gridy = 1;
        panel.add(backField, gbc);

        int result = JOptionPane.showConfirmDialog(null, panel,"Add Flashcard", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {

            String frontFieldText = frontField.getText();
            String backFieldText = backField.getText();

            if (frontFieldText.length() == 0 || backFieldText.length() == 0) {
                JOptionPane.showMessageDialog(null, "You cannot have a blank field!", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            return new FlashcardTuple(frontFieldText, backFieldText);
        }

        return null;
    }



}
