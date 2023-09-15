package urnaeletronica.FrontEnd.Pages.Candidate;
import  java.awt.*;
import javax.swing.*;


public class CandidateListPage {
        
        private JPanel panel;
        
        public CandidateListPage(JFrame frame){
            panel = new JPanel();
            panel.setBackground(new java.awt.Color(0, 0, 0));
        }
    
        public JPanel getPanel(){
            return panel;
        }    
}
