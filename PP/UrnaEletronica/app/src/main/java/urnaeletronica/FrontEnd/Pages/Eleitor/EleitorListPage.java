package urnaeletronica.FrontEnd.Pages.Eleitor;
import java.sql.Connection;
import urnaeletronica.FrontEnd.Pages.Page;

import java.awt.*;
import javax.swing.*;


public class EleitorListPage extends Page{
        
        private JPanel panel;
        
        public EleitorListPage(JFrame frame, Connection database){
            super(frame, database);
            panel = new JPanel();
            panel.setBackground(new java.awt.Color(0, 0, 0));
        }
    
        public JPanel getPanel(){
            return panel;
        }
        
        
}
