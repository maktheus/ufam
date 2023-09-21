package urnaeletronica;

import java.awt.*;
import javax.swing.*;
import urnaeletronica.FrontEnd.Components.*;
import urnaeletronica.FrontEnd.Pages.LadingPages.LandingPage;

import com.formdev.flatlaf.FlatLightLaf;

public class App {

    public static void main(String[] args) {
        FlatLightLaf.setup();

        // Landing Page screen
        JFrame frame = new JFrame("Urna Eletronica");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 946);
        frame.getContentPane().setBackground(new java.awt.Color(248, 255, 229));
        frame.setResizable(false);
        frame.setLayout(new GridBagLayout());
        frame.setVisible(true);

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
        constraints.weighty = 0.7; // 70%
        frame.add(body.getPanel(), constraints);

        // Footer (20%)
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weighty = 0.2; // 20%
        constraints.anchor = GridBagConstraints.SOUTH;
        frame.add(footer.getPanel(), constraints);
    }

  

}
