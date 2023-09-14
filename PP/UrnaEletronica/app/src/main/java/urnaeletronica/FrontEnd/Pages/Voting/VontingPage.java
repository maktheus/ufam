package urnaeletronica.FrontEnd.Pages.Voting;
import urnaeletronica.FrontEnd.Components.*;
import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class VontingPage {
    private JPanel panel;

    public VontingPage() {

        //Create number buttons
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


        //Create a panel to put the buttons
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        panel.setBackground(new java.awt.Color(0, 0, 0));

        //Add the buttons to the panel
        panel.add(button1.getButton(), constraints);
        panel.add(button2.getButton(), constraints);
        panel.add(button3.getButton(), constraints);
        panel.add(button4.getButton(), constraints);
        panel.add(button5.getButton(), constraints);
        panel.add(button6.getButton(), constraints);
        panel.add(button7.getButton(), constraints);
        panel.add(button8.getButton(), constraints);
        panel.add(button9.getButton(), constraints);
        panel.add(button0.getButton(), constraints);
    }

    public JPanel getPanel(){
        return panel;
    }
}
