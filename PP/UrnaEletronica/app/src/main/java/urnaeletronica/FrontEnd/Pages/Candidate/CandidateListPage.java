package urnaeletronica.FrontEnd.Pages.Candidate;
import  java.awt.*;
import javax.swing.*;


public class CandidateListPage {
        
        private JPanel panel;
        
        public CandidateListPage(JFrame frame){
            panel = new JPanel();
            panel.setBackground(new java.awt.Color(0, 0, 0));

            JLabel label = new JLabel("Digite o Etitulo do candidato");
            label.setForeground(new java.awt.Color(255, 255, 255));
            JTextField textField = new JTextField();
            JButton button = new JButton("Confirmar");

            GridBagConstraints constraints = new GridBagConstraints();
            panel.setLayout(new GridBagLayout());

            constraints.anchor = GridBagConstraints.CENTER;
            constraints.gridx = 1;
            constraints.gridy = 0;

            panel.add(label, constraints);

            constraints.gridx = 1;
            constraints.gridy = 1;
            panel.add(textField, constraints);

            constraints.gridx = 1;
            constraints.gridy = 2;
            panel.add(button, constraints);
        }
    
        public JPanel getPanel(){
            return panel;
        }    
}
