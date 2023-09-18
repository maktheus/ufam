package urnaeletronica.FrontEnd.Pages.LadingPages;

import urnaeletronica.FrontEnd.Components.*;
import urnaeletronica.FrontEnd.Pages.Eleitor.EleitorPage;
import urnaeletronica.FrontEnd.Pages.Voting.VontingPage;
import urnaeletronica.FrontEnd.Pages.Page;
import urnaeletronica.FrontEnd.Pages.Candidate.CandidatePage;
import urnaeletronica.FrontEnd.Pages.Results.ResultsPage;

//import App.java;

import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class LandingPage extends Page{

    private JPanel panel;
    // private String iconPath =
    // "/home/temp/code/ufam/PP/UrnaEletronica/app/src/main/java/urnaeletronica/FrontEnd/Imgs/Urna.png";
    private Button button1 = new Button("Votar", "primary");
    private Button button2 = new Button("Candidatos", "primary");
    private Button button3 = new Button("Eleitores", "primary");
    private Button button4 = new Button("Resultados", "primary");

    public LandingPage(JFrame frame) {
        super(frame);
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        panel.setBackground(new java.awt.Color(0, 0, 0));

        // ImageIcon UrnaIcon = new ImageIcon(iconPath);
        // icon = new JLabel(UrnaIcon);
        // constraints.gridx = 0;
        // constraints.gridy = 0;
        // constraints.gridwidth = 1;
        // constraints.gridheight = 4;
        // panel.add(icon, constraints);
        // constraints.gridheight = 1; // Reset grid height for buttons

        // constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.8;
        constraints.weighty = 1.0;
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(button1.getButton(), constraints);

        // button on click listener
        button1.getButton().addActionListener(e -> {
            frame.getContentPane().remove(panel);
            constraints.gridx = 0;
            constraints.gridy = 1;
            constraints.gridwidth = 1;
            constraints.gridheight = 1;
            constraints.weighty = 0.7; // 70%
            constraints.fill = GridBagConstraints.BOTH;

            frame.getContentPane().add(new VontingPage(frame).getPanel(), constraints);
            frame.revalidate();
            frame.repaint();

        });

        constraints.gridy = 1;
        panel.add(button2.getButton(), constraints);

        button2.getButton().addActionListener(e -> {
            frame.getContentPane().remove(panel);
            constraints.gridx = 0;
            constraints.gridy = 1;
            constraints.gridwidth = 1;
            constraints.gridheight = 1;
            constraints.weighty = 0.7; // 70%
            constraints.fill = GridBagConstraints.BOTH;

            frame.getContentPane().add(new CandidatePage(frame).getPanel(), constraints);
            frame.revalidate();
            frame.repaint();

        });

        constraints.gridy = 2;
        panel.add(button3.getButton(), constraints);

        button3.getButton().addActionListener(e -> {
            frame.getContentPane().remove(panel);
            constraints.gridx = 0;
            constraints.gridy = 1;
            constraints.gridwidth = 1;
            constraints.gridheight = 1;
            constraints.weighty = 0.7; // 70%
            constraints.fill = GridBagConstraints.BOTH;
            frame.getContentPane().add(new EleitorPage(frame).getPanel(), constraints);
            frame.revalidate();
            frame.repaint();

        });


        constraints.gridy = 3;
        panel.add(button4.getButton(), constraints);
        button4.getButton().addActionListener(e -> {
            frame.getContentPane().remove(panel);
            constraints.gridx = 0;
            constraints.gridy = 1;
            constraints.gridwidth = 1;
            constraints.gridheight = 1;
            constraints.weighty = 0.7; // 70%
            constraints.fill = GridBagConstraints.BOTH;

            frame.getContentPane().add(new ResultsPage(frame).getPanel(), constraints);
            frame.revalidate();
            frame.repaint();

        });
    }

    public JPanel getPanel() {
        return panel;
    }
}
