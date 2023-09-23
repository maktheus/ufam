package urnaeletronica.FrontEnd.Pages.Results;
import javax.swing.*;

import urnaeletronica.BackEnd.Controllers.VoteController;


public class ResultsPage {
    
    private JPanel panel;
    private String winner = VoteController.getWinner();
    
    
    public ResultsPage(JFrame frame){
        panel = new JPanel();
        panel.setBackground(new java.awt.Color(0, 0, 0));
    }

    public void getResults(){
        String winner = VoteController.getWinner();

    }

    public JPanel getPanel(){
        return panel;
    }

    
}
