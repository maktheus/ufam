package urnaeletronica.FrontEnd.Components;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class ButtonComponent {
    private JButton button;

    public JButton getButton() {
        return this.button;
    }

    public ButtonComponent(String text, int x, int y) {
        this.button = new JButton();
        this.button.setText(text);
        PrimaryButton(x, y);
    }

    public ButtonComponent(String text, String type) {
        this.button = new JButton();
        this.button.setText(text);
        checkType(type);
    }

    public ButtonComponent(String text, String type, int x, int y) {
        this.button = new JButton();
        this.button.setText(text);
        checkType(type, x, y);
    }

    private void checkType(String type) {
        // switch case type
        switch (type) {
            case "primary":
                PrimaryButton();
                break;
            case "secondary":
                SecondaryButton();
                break;
            case "tertiary":
                TertiaryButton();
                break;
            default:
                PrimaryButton();
                break;
        }
    }

    private void PrimaryButton() {
        this.button.setBackground(new java.awt.Color(248, 255, 229)); // Cor de fundo #F8FFE5
        this.button.setBorderPainted(false);
        this.button.setFocusPainted(false);
        this.button.setFont(new java.awt.Font("Martian Mono", java.awt.Font.PLAIN, 16)); // Fonte "Martian Mono"
        this.button.setForeground(new java.awt.Color(0, 0, 0)); // Cor do texto
        // Borda com raio de 8
        this.button.setBorder(BorderFactory.createMatteBorder(8, 8, 8, 8, new java.awt.Color(0, 0, 0)));
    }

    private void SecondaryButton() {
        this.button.setBackground(new java.awt.Color(200, 200, 200)); // Cor de fundo diferente
        this.button.setBorderPainted(false);
        this.button.setFocusPainted(false);
        this.button.setFont(new java.awt.Font("Martian Mono", 0, 16));
        this.button.setForeground(new java.awt.Color(0, 0, 0));
    }

    private void TertiaryButton() {
        this.button.setBackground(new java.awt.Color(150, 150, 150)); // Cor de fundo diferente
        this.button.setBorderPainted(true); // Borda pintada
        this.button.setFocusPainted(false);
        this.button.setFont(new java.awt.Font("Martian Mono", 0, 16));
        this.button.setForeground(new java.awt.Color(0, 0, 0));
    }

    private void checkType(String type, int x, int y) {
        // switch case type
        switch (type) {
            case "primary":
                PrimaryButton(x, y);
                break;
            case "secondary":
                SecondaryButton(x, y);
                break;
            case "tertiary":
                TertiaryButton(x, y);
                break;
            default:
                PrimaryButton(x, y);
                break;
        }
    }

    private void PrimaryButton(int x, int y) {
        this.button.setBounds(x, y, 100, 100);
        this.button.setBackground(new java.awt.Color(255, 255, 255));
        this.button.setBorderPainted(false);
        this.button.setFocusPainted(false);
        this.button.setFont(new java.awt.Font("Martian Mono", 0, 20));
        this.button.setForeground(new java.awt.Color(0, 0, 0));
    }

    private void SecondaryButton(int x, int y) {
        this.button.setBounds(x, y, 100, 100);
        this.button.setBackground(new java.awt.Color(200, 200, 200)); // Cor de fundo diferente
        this.button.setBorderPainted(false);
        this.button.setFocusPainted(false);
        this.button.setFont(new java.awt.Font("Martian Mono", 0, 20));
        this.button.setForeground(new java.awt.Color(0, 0, 0));
    }

    private void TertiaryButton(int x, int y) {
        this.button.setBounds(x, y, 100, 100);
        this.button.setBackground(new java.awt.Color(150, 150, 150)); // Cor de fundo diferente
        this.button.setBorderPainted(true); // Borda pintada
        this.button.setFocusPainted(false);
        this.button.setFont(new java.awt.Font("Martian Mono", 0, 20));
        this.button.setForeground(new java.awt.Color(0, 0, 0));
    }
}
