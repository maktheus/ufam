package urnaeletronica.FrontEnd.Pages;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import urnaeletronica.BackEnd.Models.Voter;

import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;

public abstract class Page {
    public JFrame frame;

    public Page(JFrame frame) {
        this.frame = frame;
    }

    public void setChangePanel(JButton button, JPanel panel, JPanel newPanel) {
        button.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GridBagConstraints constraints = new GridBagConstraints();

                frame.getContentPane().remove(panel);
                constraints.gridx = 0;
                constraints.gridy = 1;
                constraints.gridwidth = 1;
                constraints.gridheight = 1;
                constraints.weighty = 0.7; // 70%
                constraints.fill = GridBagConstraints.BOTH;
                frame.getContentPane().add(newPanel, constraints);
                frame.revalidate();
                frame.repaint();
            }
        });
    }

    public void setChangePanel(JPanel panel, JPanel newPanel) {

        GridBagConstraints constraints = new GridBagConstraints();

        frame.getContentPane().remove(panel);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weighty = 0.7; // 70%
        constraints.fill = GridBagConstraints.BOTH;
        frame.getContentPane().add(newPanel, constraints);
        frame.revalidate();
        frame.repaint();
    }

    public void FormInputComponent(JTextField textField, JLabel label, String text, int x, int y, int width,
            int height) {
        textField.setBounds(x, y, width, height);
        textField.setFont(new java.awt.Font("Martian Mono", 0, 16));
        textField.setForeground(new java.awt.Color(0, 0, 0));
        textField.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label.setBounds(x, y, width, height);
        label.setFont(new java.awt.Font("Martian Mono", 0, 16));
        label.setForeground(new java.awt.Color(255, 255, 255));
        label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label.setText(text);
    }

    public boolean verifyForm(Voter voter) {
        if (voter == null) {
            return false;
        }
        if (voter.getName() == null || voter.getCpf() == null || voter.getEtitulo() == null) {
            return false;
        }

        if (voter.getName().equals("") || voter.getCpf().equals("") || voter.getEtitulo().equals("")) {
            return false;
        }

        if (voter.getName().length() > 100 || voter.getCpf().length() > 11 || voter.getEtitulo().length() > 12) {
            return false;
        }

        if (voter.getName().length() < 3 || voter.getCpf().length() < 11 || voter.getEtitulo().length() < 12) {
            return false;
        }

        if (!voter.getCpf().matches("[0-9]+")) {
            return false;
        }

        if (!voter.getEtitulo().matches("[0-9]+")) {
            return false;
        }

        if (!voter.getName().matches("[a-zA-Z]+")) {
            return false;
        }
        return true;
    }

}
