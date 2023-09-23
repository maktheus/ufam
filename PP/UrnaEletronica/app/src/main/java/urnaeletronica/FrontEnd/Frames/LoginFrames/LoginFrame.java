package urnaeletronica.FrontEnd.Frames.LoginFrames;

import java.awt.*;
import javax.swing.*;

import urnaeletronica.FrontEnd.Components.ButtonComponent;
import urnaeletronica.FrontEnd.Frames.Frame;
import urnaeletronica.FrontEnd.Frames.MainFrames.MainFrame;
import urnaeletronica.BackEnd.Controllers.LoginController;


public class LoginFrame extends Frame {
    JFrame frame = new JFrame("Login Page");

    public LoginFrame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.getContentPane().setBackground(new java.awt.Color(27, 154, 170));
        frame.setResizable(false);
        frame.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        JLabel loginLabel = new JLabel("Login");
        loginLabel.setFont(new Font("Martin Mono", Font.PLAIN, 30));
        loginLabel.setForeground(Color.WHITE);



        JLabel etituloLabel = new JLabel("Etitulo");
        etituloLabel.setFont(new Font("Martin Mono", Font.PLAIN, 16));
        etituloLabel.setForeground(Color.WHITE);

        JLabel senhaLabel = new JLabel("Senha");
        senhaLabel.setFont(new Font("Martin Mono", Font.PLAIN, 16));
        senhaLabel.setForeground(Color.WHITE);


        JTextField etituloTextField = new JTextField();
        etituloTextField.setPreferredSize(new Dimension(200, 24));
        etituloTextField.setFont(new Font("Martin Mono", Font.PLAIN, 16));
        etituloTextField.setPreferredSize(new Dimension(200, 24));

        JPasswordField senhaTextField = new JPasswordField();
        senhaTextField.setPreferredSize(new Dimension(200, 24));
        senhaTextField.setFont(new Font("Martin Mono", Font.PLAIN, 16));
        


        ButtonComponent loginButton = new ButtonComponent("Login", "primary");
        



        constraints.anchor = GridBagConstraints.WEST;

        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        frame.add(loginLabel, constraints);

        constraints.gridy = 1;
        frame.add(senhaLabel, constraints);

        constraints.gridy = 2;
        frame.add(senhaTextField, constraints);

        constraints.gridy = 3;
        frame.add(loginButton.getButton(), constraints);

        loginButton.getButton().addActionListener(e -> {
            String senha = senhaTextField.getText();
            login(senha);
        });
    }

    private void login(  String senha) {
        try{
            if(LoginController.login(senha)){
                changeFrame(frame, new MainFrame().getFrame());
            }else{
                JOptionPane.showMessageDialog(null, "Senha  incorreto");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao logar");
        }
    
    }

    public void setVisible() {
        frame.setVisible(true);   
    }
}
