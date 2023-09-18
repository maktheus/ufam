package urnaeletronica.FrontEnd.Pages.Candidate;
import java.awt.*;
import javax.swing.*;


public class CandidateUpdatePage {
        
        private JPanel panel;
        
        public CandidateUpdatePage(JFrame frame){
            panel = new JPanel();
            panel.setBackground(new java.awt.Color(0, 0, 0));

            JLabel label = new JLabel("Digite o Etitulo do candidato");
            label.setForeground(new java.awt.Color(255, 255, 255));

            JTextField textField = new JTextField();
            JButton button = new JButton("Confirmar");
            button.setBackground(new java.awt.Color(0, 0, 0));

            GridBagConstraints constraints = new GridBagConstraints();
            panel.setLayout(new GridBagLayout());

            constraints.anchor = GridBagConstraints.CENTER;

            constraints.gridx = 1;
            constraints.gridy = 0;
            panel.add(label, constraints);

            constraints.gridx = 1;
            constraints.gridy = 1;
            panel.add(button, constraints, 0);

            constraints.gridx = 1;
            constraints.gridy = 2;
            panel.add(textField, constraints, 0);
        }
    
        public JPanel getPanel(){
            return panel;
        }
    
}
