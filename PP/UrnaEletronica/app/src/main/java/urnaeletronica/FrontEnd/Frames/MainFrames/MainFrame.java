package urnaeletronica.FrontEnd.Frames.MainFrames;

import javax.swing.*;
import java.awt.*;

import urnaeletronica.FrontEnd.Components.BarrinhasComponent;
import urnaeletronica.FrontEnd.Components.FooterComponent;
import urnaeletronica.FrontEnd.Components.HeaderComponent;
import urnaeletronica.FrontEnd.Pages.LadingPages.LandingPage;

public class MainFrame {
    JFrame frame = new JFrame("Urna Eletronica");

    public MainFrame(){
        // Landing Page screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 946);
        frame.getContentPane().setBackground(new java.awt.Color(248, 255, 229));
        frame.setResizable(false);
        frame.setLayout(new GridBagLayout());

        HeaderComponent header = new HeaderComponent(frame);
        LandingPage body = new LandingPage(frame);
        FooterComponent footer = new FooterComponent();

        GridBagConstraints constraints = new GridBagConstraints();
        

        constraints.weightx = 1.0; // Peso total

        // Header (10%)
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weighty = 0.1; // 10%
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.fill = GridBagConstraints.BOTH;
        frame.add(header.getPanel(), constraints);

        // Landing Page (70%)
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weighty = 0.8; // 70%
        frame.add(body.getPanel(), constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weighty = 0.03; // 20%
        constraints.anchor = GridBagConstraints.SOUTH;
        frame.add(BarrinhasComponent.getPanel(), constraints, 0);
        
        
        // Footer (10%)
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weighty = 0.1; // 20%
        constraints.anchor = GridBagConstraints.SOUTH;
        frame.add(footer.getPanel(), constraints);
    }

    public JFrame getFrame() {
        return this.frame;
    }
}
