package urnaeletronica.BackEnd.Controllers;
import java.sql.*;
import java.util.ArrayList;

import urnaeletronica.BackEnd.Models.Voter;
public class VoterController {
    
    public VoterController() {
     
    }

    public boolean createVoter(Voter voter){
        try {
            String sql = "INSERT INTO Voter (name, cpf, etitulo) VALUES (?, ?, ?)";
            PreparedStatement stmt = DataBaseController.prepareStatement(sql);
            stmt.setString(1, voter.getName());
            stmt.setString(2, voter.getCpf());
            stmt.setString(3, voter.getEtitulo());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean deleteVoter(Connection database, String cpf){
        try {
            String sql = "DELETE FROM Voter WHERE cpf = ?";
            PreparedStatement stmt = database.prepareStatement(sql);
            stmt.setString(1, cpf);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    public Voter getVoter(Connection database, String cpf){
        try {
            String sql = "SELECT * FROM Voter WHERE cpf = ?";
            PreparedStatement stmt = database.prepareStatement(sql);
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();
            Voter voter = new Voter(rs.getString("name"), rs.getString("cpf"), rs.getString("etitulo"));
            return voter;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<Voter> getAllVoters(Connection database){
        try {
            String sql = "SELECT * FROM Voter";
            PreparedStatement stmt = database.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            ArrayList<Voter> voters = new ArrayList<Voter>();
            while(rs.next()){
                Voter voter = new Voter(rs.getString("name"), rs.getString("cpf"), rs.getString("etitulo"));
                voters.add(voter);
            }
            return voters;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
