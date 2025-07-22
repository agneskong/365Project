package PopcornPicks.gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class FilterPage extends JFrame {

    private final JCheckBox actionBox = new JCheckBox("Action");
    private final JCheckBox comedyBox = new JCheckBox("Comedy");
    private final JCheckBox horrorBox = new JCheckBox("Horror");
    private final JCheckBox romanceBox = new JCheckBox("Romance");
    private final JCheckBox dramaBox = new JCheckBox("Drama");
    private final JCheckBox scifiBox = new JCheckBox("Sci-Fi");

    private final JCheckBox fantasyBox = new JCheckBox("Fantasy");

    private final JCheckBox thrillerBox = new JCheckBox("Thriller");

    private final JTextField yearFromField = new JTextField("1990", 4);
    private final JTextField yearToField = new JTextField("2025", 4);

    private final JSlider ratingSlider = new JSlider(1, 5, 3); // default: 3+


    public FilterPage() {
        setTitle("Filters");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        getContentPane().setBackground(new Color(20, 20, 20));

        // title
        JLabel titleLabel = new JLabel("Filters", JLabel.CENTER);
        titleLabel.setFont(new Font("Georgia", Font.BOLD, 36));
        titleLabel.setForeground(new Color(247, 179, 64)); // #F7B340
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(20));
        add(titleLabel);

        // genre filters
        add(Box.createVerticalStrut(20));
        JLabel genreLabel = new JLabel("filters");
        styleLabel(genreLabel);
        add(genreLabel);

        JPanel genrePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        genrePanel.setBackground(new Color(20, 20, 20));
        for (JCheckBox box : new JCheckBox[]{actionBox, comedyBox, dramaBox, fantasyBox, horrorBox, romanceBox,
                scifiBox, thrillerBox}) {
            box.setForeground(Color.WHITE);
            box.setOpaque(false);
            genrePanel.add(makeCheckboxBox(box));
        }
        add(genrePanel);

        // year range
        add(Box.createVerticalStrut(10));
        JLabel yearLabel = new JLabel("year");
        styleLabel(yearLabel);
        add(yearLabel);

        JPanel yearPanel = new JPanel();
        yearPanel.setBackground(new Color(20, 20, 20));
        yearPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        yearPanel.add(yearFromField);
        JLabel to = new JLabel("to");
        to.setForeground(Color.WHITE);
        yearPanel.add(to);
        yearPanel.add(yearToField);
        styleTextField(yearFromField);
        styleTextField(yearToField);
        add(yearPanel);

        // rating slider
        add(Box.createVerticalStrut(10));
        JLabel ratingLabel = new JLabel("rating");
        styleLabel(ratingLabel);
        add(ratingLabel);


        ratingSlider.setLabelTable(makeStarLabels());
        ratingSlider.setPaintLabels(true);
        ratingSlider.setPaintTicks(true);
        ratingSlider.setMajorTickSpacing(1);
        ratingSlider.setForeground(Color.WHITE);
        ratingSlider.setBackground(new Color(20, 20, 20));
        add(ratingSlider);

        // continue button
        JButton continueButton = new JButton("Continue");
        continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        continueButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        continueButton.setBackground(new Color(40, 40, 40));
        continueButton.setForeground(Color.WHITE);
        continueButton.setFocusPainted(false);
        continueButton.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        add(Box.createVerticalStrut(20));
        add(continueButton);

        continueButton.addActionListener(e -> {
            // Example: Print filters or navigate to grid page
            System.out.println("Selected Genres: " + getSelectedGenres());
            System.out.println("Year: " + yearFromField.getText() + " to " + yearToField.getText());
            System.out.println("Min Rating: " + ratingSlider.getValue());

            dispose();
            new MovieGridPage(); // ← Replace with your next page
        });

        setVisible(true);
    }

    private void styleLabel(JLabel label) {
        label.setForeground(Color.LIGHT_GRAY);
        label.setFont(new Font("SansSerif", Font.PLAIN, 14));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private void styleTextField(JTextField field) {
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setBackground(new Color(30, 30, 30));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createLineBorder(Color.WHITE));
    }

    public List<String> getSelectedGenres() {
        List<String> genres = new ArrayList<>();
        if (comedyBox.isSelected()) genres.add("Comedy");
        if (horrorBox.isSelected()) genres.add("Horror");
        if (romanceBox.isSelected()) genres.add("Romance");
        if (actionBox.isSelected()) genres.add("Action");
        if (scifiBox.isSelected()) genres.add("Sci-Fi");
        if (thrillerBox.isSelected()) genres.add("Thriller");
        if (fantasyBox.isSelected()) genres.add("Fantasy");
        if (dramaBox.isSelected()) genres.add("Drama");
        return genres;
    }

    private Hashtable<Integer, JLabel> makeStarLabels() {
        Hashtable<Integer, JLabel> labels = new Hashtable<>();
        for (int i = 1; i <= 10; i++) {
            JLabel star = new JLabel("★");
            star.setForeground(Color.LIGHT_GRAY);
            star.setFont(new Font("SansSerif", Font.BOLD, 18));
            labels.put(i, star);
        }
        return labels;
    }

    private JPanel makeCheckboxBox(JCheckBox box) {
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        wrapper.setBackground(new Color(35, 35, 35));
        wrapper.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true)); // rounded border
        wrapper.setOpaque(true);
        box.setOpaque(false);
        box.setFocusPainted(false);
        box.setForeground(Color.WHITE);
        wrapper.add(box);
        return wrapper;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FilterPage::new);
    }
}
