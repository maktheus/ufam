package urnaeletronica.FrontEnd.Components;
import javax.swing.*;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;


public class VotingButtonsComponent {
    private JPanel panel;

    // Create number buttons
    ButtonComponent button1 = new ButtonComponent("1", "primary");
    ButtonComponent button2 = new ButtonComponent("2", "primary");
    ButtonComponent button3 = new ButtonComponent("3", "primary");
    ButtonComponent button4 = new ButtonComponent("4", "primary");
    ButtonComponent button5 = new ButtonComponent("5", "primary");
    ButtonComponent button6 = new ButtonComponent("6", "primary");
    ButtonComponent button7 = new ButtonComponent("7", "primary");
    ButtonComponent button8 = new ButtonComponent("8", "primary");
    ButtonComponent button9 = new ButtonComponent("9", "primary");
    ButtonComponent button0 = new ButtonComponent("0", "primary");


    public VotingButtonsComponent() {

        // Create a panel to put the buttons
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        //set transparente background
        panel.setOpaque(false);
        
        constraints.insets = new java.awt.Insets(10, 10, 10, 10);


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
