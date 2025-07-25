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
    private final JSlider ratingSlider = new JSlider(1, 10, 5);

    public FilterPage() {
        setTitle("Filters");
        setSize(800, 1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(20, 20, 20));
        setLayout(new BorderLayout());

        Color darkBG = new Color(20, 20, 20);
        Color gold = new Color(247, 179, 64);
        Color panelBG = new Color(28, 28, 28);
        Color light = Color.decode("#FEE6B6");

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(darkBG);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Filters");
        titleLabel.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 64));
        titleLabel.setForeground(gold);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/images/popcorn.png"));
        Image logoImg = logoIcon.getImage().getScaledInstance(60, 70, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(logoImg));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(Box.createVerticalStrut(20));
        headerPanel.add(logoLabel);
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(10));
        add(headerPanel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(darkBG);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        RoundedPanel genrePanel = new RoundedPanel(30, panelBG);
        genrePanel.setLayout(new BoxLayout(genrePanel, BoxLayout.Y_AXIS));
        genrePanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel genreLabel = new JLabel("Genre");
        styleSectionLabel(genreLabel, light);
        genrePanel.add(genreLabel);
        genrePanel.add(Box.createVerticalStrut(10));

        JPanel genreBoxes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        genreBoxes.setBackground(panelBG);
        for (JCheckBox box : new JCheckBox[]{actionBox, comedyBox, dramaBox, fantasyBox, horrorBox, romanceBox, scifiBox, thrillerBox}) {
            box.setForeground(Color.WHITE);
            box.setOpaque(false);
            box.setFont(new Font("SansSerif", Font.PLAIN, 24));
            genreBoxes.add(makeCheckboxBox(box));
        }
        genrePanel.add(genreBoxes);
        mainPanel.add(genrePanel);
        mainPanel.add(Box.createVerticalStrut(20));

        RoundedPanel yearPanel = new RoundedPanel(30, panelBG);
        yearPanel.setLayout(new BoxLayout(yearPanel, BoxLayout.Y_AXIS));
        yearPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));

        JLabel yearLabel = new JLabel("Year");
        styleSectionLabel(yearLabel, light);
        yearPanel.add(yearLabel);
        yearPanel.add(Box.createVerticalStrut(7));

        JPanel yearFields = new JPanel(new FlowLayout(FlowLayout.CENTER));
        yearFields.setBackground(panelBG);
        styleTextField(yearFromField);
        styleTextField(yearToField);
        yearFields.add(yearFromField);
        JLabel to = new JLabel("to");
        to.setForeground(Color.WHITE);
        yearFields.add(to);
        yearFields.add(yearToField);
        yearPanel.add(yearFields);
        mainPanel.add(yearPanel);
        mainPanel.add(Box.createVerticalStrut(20));

        RoundedPanel ratingPanel = new RoundedPanel(30, panelBG);
        ratingPanel.setLayout(new BoxLayout(ratingPanel, BoxLayout.Y_AXIS));
        ratingPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel ratingLabel = new JLabel("Rating");
        styleSectionLabel(ratingLabel, light);
        ratingPanel.add(ratingLabel);
        ratingPanel.add(Box.createVerticalStrut(10));

        ratingSlider.setLabelTable(makeStarLabels());
        ratingSlider.setPaintLabels(true);
        ratingSlider.setPaintTicks(true);
        ratingSlider.setMajorTickSpacing(1);
        ratingSlider.setForeground(gold);
        ratingSlider.setBackground(panelBG);
        ratingPanel.add(ratingSlider);
        mainPanel.add(ratingPanel);
        mainPanel.add(Box.createVerticalStrut(30));

        JButton continueButton = new JButton("Continue");
        continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        continueButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        continueButton.setBackground(gold);
        continueButton.setForeground(Color.white);
        continueButton.setFocusPainted(false);
        continueButton.setBorder(BorderFactory.createEmptyBorder(12, 40, 12, 40));
        continueButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        mainPanel.add(continueButton);
        mainPanel.add(Box.createVerticalStrut(20));

        continueButton.addActionListener(e -> {
            List<String> genres = getSelectedGenres();
            int yearFrom = Integer.parseInt(yearFromField.getText());
            int yearTo = Integer.parseInt(yearToField.getText());
            int minRating = ratingSlider.getValue();
            dispose();
            new MovieGridPage(genres, yearFrom, yearTo, minRating);
        });

        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void styleTextField(JTextField field) {
        field.setFont(new Font("SansSerif", Font.PLAIN, 24));
        field.setBackground(new Color(30, 30, 30));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
    }

    private void styleSectionLabel(JLabel label, Color color) {
        label.setForeground(color);
        label.setFont(new Font("SansSerif", Font.BOLD, 26));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
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
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        wrapper.setBackground(new Color(35, 35, 35));
        wrapper.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true));
        wrapper.setOpaque(true);
        box.setOpaque(false);
        box.setFocusPainted(false);
        box.setForeground(Color.WHITE);
        wrapper.add(box);
        return wrapper;
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FilterPage::new);
    }
}
