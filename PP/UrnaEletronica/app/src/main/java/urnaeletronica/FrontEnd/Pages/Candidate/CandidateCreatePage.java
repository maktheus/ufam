package urnaeletronica.FrontEnd.Pages.Candidate;
import urnaeletronica.FrontEnd.Components.*;
import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;



public class CandidateCreatePage {
    public JPanel panel;
    public CandidateCreatePage(){
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        panel.setPreferredSize(new java.awt.Dimension(0, 400));
        panel.setBackground(new java.awt.Color(0, 0, 0));

        Button button1 = new Button("Cadastrar", "primary");
        Button button2 = new Button("Home", "primary");

        panel.add(button1.getButton(), constraints);
        panel.add(button2.getButton(), constraints);


        //create a form to Candidate
        JLabel label1 = new JLabel("Nome");
        JLabel label2 = new JLabel("CPF");
        JLabel label3 = new JLabel("Titulo de Eleitor");
        JLabel label4 = new JLabel("Numero do Candidato");


        JTextField textField1 = new JTextField(20);
        JTextField textField2 = new JTextField(20);
        JTextField textField3 = new JTextField(20);
        JTextField textField4 = new JTextField(20);
        

        panel.add(label1, constraints);
        panel.add(textField1, constraints);
        panel.add(label2, constraints);
        panel.add(textField2, constraints);
        panel.add(label3, constraints);
        panel.add(textField3, constraints);
        panel.add(label4, constraints);
        panel.add(textField4, constraints);
        
    }

}
