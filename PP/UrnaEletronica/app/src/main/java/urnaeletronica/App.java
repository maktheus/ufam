package urnaeletronica;

import java.awt.*;
import javax.swing.*;
import urnaeletronica.FrontEnd.Components.*;
import urnaeletronica.FrontEnd.Pages.LandingPage;
import urnaeletronica.BackEnd.Controllers.DataBaseController;

public class App {

    public static void main(String[] args) {
        // Database
        
        DataBaseController database = new DataBaseController();
        database.connect();

        if(database.connect() == null){
            System.out.println("Erro ao conectar ao banco de dados");
            return ;
        }

        // Landing Page screen
        JFrame frame = new JFrame("Urna Eletronica");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 946);
        frame.getContentPane().setBackground(new java.awt.Color(248, 255, 229));
        frame.setResizable(false);
        frame.setLayout(new GridBagLayout());
        frame.setVisible(true);

        Header header = new Header();
        LandingPage body = new LandingPage();
        Footer footer = new Footer();

        GridBagConstraints constraints = new GridBagConstraints();

        // Defina o peso para alcançar a proporção desejada (10%, 70%, 20%)
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