package urnaeletronica.BackEnd.Controllers;
import java.sql.*;


import urnaeletronica.BackEnd.Models.Voter;
import urnaeletronica.BackEnd.Models.Candidate;
import java.util.ArrayList;

public class CandidateController {

    // private String name;
    // private String cpf;
    // private String etitulo;


    public CandidateController() {
    }

    public Candidate createCandidate(Connection database, Voter voter, String idForCandidate){
        try {
            
            String sql = "INSERT INTO candidate (etitulo,idForCandidate) VALUES (?,?)";
            PreparedStatement stmt = database.prepareStatement(sql);
            stmt.setString(3, voter.getEtitulo());
            stmt.setString(4, idForCandidate);

            stmt.executeUpdate();

            Candidate candidate = new Candidate(voter.getEtitulo(), idForCandidate);

            return candidate;

        } catch (SQLException e) {

            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean deleteCandidate(Connection database, String cpf){
        try {
            String sql = "DELETE FROM candidate WHERE cpf = ?";
            PreparedStatement stmt = database.prepareStatement(sql);
            stmt.setString(1, cpf);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    
        
    

    public Candidate getCandidate(Connection database, String cpf){
        try {
            String sql = "SELECT * FROM candidate WHERE cpf = ?";
            PreparedStatement stmt = database.prepareStatement(sql);
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();
            Candidate candidate = new Candidate(rs.getString("etitulo"), rs.getString("idForCandidate"));
            return candidate;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public  ArrayList<Candidate> getAllCandidates(Connection database){
        try {
            String sql = "SELECT * FROM candidate";
            PreparedStatement stmt = database.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            ArrayList<Candidate> candidates = new ArrayList<Candidate>();
            while(rs.next()){
                Candidate candidate = new Candidate(rs.getString("etitulo"), rs.getString("idForCandidate"));
                candidates.add(candidate);
            }
            return candidates;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


}
