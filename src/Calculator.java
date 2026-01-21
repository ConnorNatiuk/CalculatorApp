import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.*;


public class Calculator {
    int boardWidth = 360;
    int boardHeight = 540;

    String[] buttonValues = {
        "AC", "+/-", "%", "÷",
        "7", "8", "9", "×",
        "4", "5", "6", "+",
        "1", "2", "3", "-",
        "0", ".", "√", "="
    };

    String[] rightSymbols = {"÷", "×", "-", "+", "="};
    String[] topSymbols = {"AC", "+/-", "%"};

    Color customLightGreen = new Color(204, 247, 221);
    Color customDarkGreen = new Color(133, 219, 168);
    Color customBlack = new Color(22, 24, 25);
    Color customLightBlue = new Color(227, 244, 251);
    Color customLightYellow = new Color(233, 252, 209);

    JFrame frame = new JFrame("Calculator");
    JLabel displayLabel = new JLabel();
    JPanel displayPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();
    
    String A = "0";
    String B = null;
    String operator = null;

    public Calculator() {
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        displayLabel.setBackground(customBlack);
        displayLabel.setForeground(customLightGreen);
        displayLabel.setFont(new Font("Arial", Font.BOLD, 80));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setText("0");
        displayLabel.setOpaque(true);

        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displayLabel);
        frame.add(displayPanel, BorderLayout.NORTH);

        buttonsPanel.setLayout(new GridLayout(5,4));
        buttonsPanel.setBackground(customBlack);
        frame.add(buttonsPanel);

        for (int i = 0; i < buttonValues.length; i++) {
            JButton button = new JButton();
            String buttonValue = buttonValues[i];
            button.setFont(new Font("Arial", Font.PLAIN, 30));
            button.setText(buttonValue);
            button.setFocusable(false);
            if (Arrays.asList(topSymbols).contains(buttonValue)) {
                button.setBackground(customLightGreen);
            }
            else if (Arrays.asList(rightSymbols).contains(buttonValue)) {
                button.setBackground(customDarkGreen);
            }
            else {
                button.setBackground(customBlack);
                button.setForeground(Color.WHITE);
            }
            buttonsPanel.add(button);

            button.addActionListener(new ActionListener() {
                @SuppressWarnings("StringEquality")
                public void actionPerformed(ActionEvent e) {
                    JButton button = (JButton) e.getSource();
                    String buttonValue = button.getText();
                    if (Arrays.asList(rightSymbols).contains(buttonValue)) {
                        if (buttonValue == "=") {
                            if (A != null) {
                                B = displayLabel.getText();
                                double numA = Double.parseDouble(A);
                                double numB = Double.parseDouble(B);

                                if (operator == "+") {
                                    displayLabel.setText(removeZeroDecimal(numA+numB));
                                }
                                if (operator == "-") {
                                    displayLabel.setText(removeZeroDecimal(numA-numB));
                                }
                                if (operator == "×") {
                                    displayLabel.setText(removeZeroDecimal(numA*numB));
                                }
                                if (operator == "÷") {
                                    displayLabel.setText(removeZeroDecimal(numA/numB));
                                }
                                clearAll();
                            }
                        }
                        else if ("+-×÷".contains(buttonValue)) {
                            if (operator == null) {
                                A = displayLabel.getText();
                                displayLabel.setText("0");
                                B = "0";
                            }
                            operator = buttonValue;
                        }
                    }
                    else if (Arrays.asList(topSymbols).contains(buttonValue)) {
                        if (buttonValue == "AC") {
                            clearAll();
                            displayLabel.setText("0");
                        }
                        else if (buttonValue == "+/-") {
                            double numDisplay = Double.parseDouble(displayLabel.getText());
                            numDisplay *= -1;
                            displayLabel.setText(removeZeroDecimal(numDisplay));
                        }
                        else {
                            double numDisplay = Double.parseDouble(displayLabel.getText());
                            numDisplay /= 100;
                            displayLabel.setText(removeZeroDecimal(numDisplay));
                        }
                    }
                    else {
                        if (buttonValue == ".") {
                            if (!displayLabel.getText().contains(buttonValue)) {
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                            }
                        }
                        else if (buttonValue == "√") {
                            displayLabel.setText(removeZeroDecimal(squareRoot(Double.parseDouble(displayLabel.getText()))));
                        }
                        else if ("0123456789".contains(buttonValue)) {
                            if (displayLabel.getText() == "0") {
                                displayLabel.setText(buttonValue);
                            }
                            else {
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                            }
                        }
                    }
                }
            });
        }
        frame.setVisible(true);
    }
    void clearAll() {
        A = "0";
        B = null;
        operator = null;
    }
    String removeZeroDecimal(double numDisplay) {
        if ((numDisplay % 1) == 0) {
            return Integer.toString((int) numDisplay);
        }
        return Double.toString(numDisplay);
    }
    Double squareRoot(double numDisplay) {
        return (Math.sqrt(numDisplay));
    }
}
