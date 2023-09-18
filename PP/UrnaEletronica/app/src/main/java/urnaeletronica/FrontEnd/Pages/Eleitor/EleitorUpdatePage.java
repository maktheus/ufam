package urnaeletronica.FrontEnd.Pages.Eleitor;

import java.awt.*;
import javax.swing.*;

import urnaeletronica.FrontEnd.Pages.Page;

public class EleitorUpdatePage extends Page{

    private JPanel panel;

    public EleitorUpdatePage(JFrame jframe, Connection database) {
        super(jframe, database);
        
        panel = new JPanel();
        panel.setBackground(new java.awt.Color(0, 0, 0));
    }

    public JPanel getPanel() {
        return panel;
    }
}
