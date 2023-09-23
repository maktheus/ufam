package urnaeletronica.FrontEnd.Pages.Eleitor;

import urnaeletronica.BackEnd.Controllers.VoterController;
import urnaeletronica.BackEnd.Models.Voter;
import urnaeletronica.FrontEnd.Frames.EleitorShowFrames.EleitorShowFrame;
import urnaeletronica.FrontEnd.Pages.Page;

import java.awt.*;
import javax.swing.*;


public class EleitorListPage extends Page{
        
        private JPanel panel;
        
        public EleitorListPage(JFrame frame){
            super(frame);
            panel = new JPanel();
            panel.setBackground(new java.awt.Color(0, 0, 0));

            JLabel label = new JLabel("Digite o número do eleitor");
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

            button.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    getEleitor(textField.getText());
                }
            });
            
        }
        
        private void getEleitor(String etitulo){
            Voter eleitor = VoterController.getVoter(etitulo);
            if(eleitor == null){
                JOptionPane.showMessageDialog(null, "Eleitor não encontrado");
                setChangePanel(panel, new EleitorPage(frame).getPanel());
            }else{
                JOptionPane.showMessageDialog(null, "Eleitor encontrado");
                EleitorShowFrame eleitorShowComponent = new EleitorShowFrame(eleitor);
                //return to EleitorPage
                setChangePanel(panel, new EleitorPage(frame).getPanel());
            }
        }



        public JPanel getPanel(){
            return panel;
        }
        
        
}
