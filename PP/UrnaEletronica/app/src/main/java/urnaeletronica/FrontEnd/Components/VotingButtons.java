package urnaeletronica.FrontEnd.Components;
import javax.swing.*;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;


public class VotingButtons {
    private JPanel panel;

    // Create number buttons
    Button button1 = new Button("1", "primary");
    Button button2 = new Button("2", "primary");
    Button button3 = new Button("3", "primary");
    Button button4 = new Button("4", "primary");
    Button button5 = new Button("5", "primary");
    Button button6 = new Button("6", "primary");
    Button button7 = new Button("7", "primary");
    Button button8 = new Button("8", "primary");
    Button button9 = new Button("9", "primary");
    Button button0 = new Button("0", "primary");


    public VotingButtons() {

        // Create a panel to put the buttons
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        //set transparente background
        panel.setOpaque(false);



         // Primeira linha
         constraints.gridx = 0;
         constraints.gridy = 0;
         panel.add(button1.getButton(), constraints);
 
         constraints.gridx = 1;
         panel.add(button2.getButton(), constraints);
 
         constraints.gridx = 2;
         panel.add(button3.getButton(), constraints);
 
         // Segunda linha
         constraints.gridx = 0;
         constraints.gridy = 1;
         panel.add(button4.getButton(), constraints);
 
         constraints.gridx = 1;
         panel.add(button5.getButton(), constraints);
 
         constraints.gridx = 2;
         panel.add(button6.getButton(), constraints);
 
         // Terceira linha
         constraints.gridx = 0;
         constraints.gridy = 2;
         panel.add(button7.getButton(), constraints);
 
         constraints.gridx = 1;
         panel.add(button8.getButton(), constraints);
 
         constraints.gridx = 2;
         panel.add(button9.getButton(), constraints);
 
         // Quarta linha
         constraints.gridx = 1;
         constraints.gridy = 3;
         panel.add(button0.getButton(), constraints);
    }

    public void setButton1ActionListener(ActionListener actionListener) {
        button1.getButton().addActionListener(actionListener);
    }

    public void setButton2ActionListener(ActionListener actionListener) {
        button2.getButton().addActionListener(actionListener);
    }


    public void setButton3ActionListener(ActionListener actionListener) {
        button3.getButton().addActionListener(actionListener);
    }

    public void setButton4ActionListener(ActionListener actionListener) {
        button4.getButton().addActionListener(actionListener);
    }

    public void setButton5ActionListener(ActionListener actionListener) {
        button5.getButton().addActionListener(actionListener);
    }

    public void setButton6ActionListener(ActionListener actionListener) {
        button6.getButton().addActionListener(actionListener);
    }

    public void setButton7ActionListener(ActionListener actionListener) {
        button7.getButton().addActionListener(actionListener);
    }

    public void setButton8ActionListener(ActionListener actionListener) {
        button8.getButton().addActionListener(actionListener);
    }

    public void setButton9ActionListener(ActionListener actionListener) {
        button9.getButton().addActionListener(actionListener);
    }

    public void setButton0ActionListener(ActionListener actionListener) {
        button0.getButton().addActionListener(actionListener);
    }




    public JPanel getPanel() {
        return panel;
    }

}
