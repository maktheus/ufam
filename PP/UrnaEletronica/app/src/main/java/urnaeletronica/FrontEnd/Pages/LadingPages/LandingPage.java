package urnaeletronica.FrontEnd.Pages.LadingPages;

import urnaeletronica.BackEnd.Controllers.CandidateController;
import urnaeletronica.BackEnd.Controllers.VoteController;
import urnaeletronica.BackEnd.Controllers.VoterController;
import urnaeletronica.FrontEnd.Components.*;
import urnaeletronica.FrontEnd.Frames.ResultsFrames.ResultsFrame;
import urnaeletronica.FrontEnd.Pages.Eleitor.EleitorPage;
import urnaeletronica.FrontEnd.Pages.Voting.EtituloConfirmVotePage;
import urnaeletronica.FrontEnd.Pages.Page;
import urnaeletronica.FrontEnd.Pages.Candidate.CandidatePage;

//import App.java;

import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class LandingPage extends Page{

    private JPanel panel;
    // private String iconPath =
    // "/home/temp/code/ufam/PP/UrnaEletronica/app/src/main/java/urnaeletronica/FrontEnd/Imgs/Urna.png";
    private ButtonComponent button1 = new ButtonComponent("Eleitores", "secondary");
    private ButtonComponent button2 = new ButtonComponent("Candidatos", "secondary");
    private ButtonComponent button3 = new ButtonComponent("Votar", "secondary");
    private ButtonComponent button4 = new ButtonComponent("Finalizar Votacao", "secondary");

    public LandingPage(JFrame frame) {
        super(frame);
        panel = new JPanel();        

        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        panel.setBackground(new java.awt.Color(255, 255, 255));

        constraints.anchor = GridBagConstraints.CENTER;
        constraints.weightx = 0.8;
        constraints.weighty = 1.0;
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(button1.getButton(), constraints);

        // button on click listener
        setChangePanel(button1.getButton(), panel, new EtituloConfirmVotePage(frame).getPanel());
        constraints.gridy = 1;
        panel.add(button2.getButton(), constraints);

        setChangePanel(button2.getButton(), panel, new CandidatePage(frame).getPanel());
        constraints.gridy = 2;
        panel.add(button3.getButton(), constraints);

        setChangePanel(button3.getButton(), panel, new EleitorPage(frame).getPanel());

        constraints.gridy = 3;
        panel.add(button4.getButton(), constraints);
        
        setChangePanel(button4.getButton(), frame, new ResultsFrame().getFrame());


        verifyIfThereIsAnyEleitor();
        verifyIfThereIsAnyCandidato();
        verifyIfSomeoneAlredyVoted();
    }

    private void verifyIfThereIsAnyEleitor(){
        if (VoterController.getAllVoters().size() == 0 ) {
            button2.getButton().setEnabled(false);
            button2.getButton().setText("Ainda não há Eleitores Cadastrados");
        }
    }

    private void verifyIfThereIsAnyCandidato(){
        if(CandidateController.getAllCandidates().size() == 0){
            button3.getButton().setEnabled(false);
            button3.getButton().setText("Ainda não há Candidatos Cadastrados");
        }
    }

    private void verifyIfSomeoneAlredyVoted() {
        if (VoteController.getAllVotes().size() == 0) {
             button4.getButton().setEnabled(false);
             button4.getButton().setText("Ainda não há Votos Cadastrados");
        }
    }


    public JPanel getPanel() {
        return panel;
    }
}
