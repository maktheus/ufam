package urnaeletronica.BackEnd.Controllers;

import java.sql.*;

import urnaeletronica.BackEnd.Models.Candidate;
import java.util.ArrayList;

public class CandidateController {

    // private String name;
    // private String etitulo;
    // private String etitulo;

    DataBaseController database = new DataBaseController();
    public CandidateController() {}

    public static boolean createCandidate( Candidate candidate){
        try {
            String sql = "INSERT INTO Candidate (etitulo, idForCandidate) VALUES (?, ?)";
            PreparedStatement stmt = DataBaseController.prepareStatement(sql);
            stmt.setString(1, candidate.getEtitulo());
            stmt.setString(2, candidate.getIdForCandidate());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {

            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean updateCandidate(Candidate candidate){
        try {
            String sql = "UPDATE Candidate SET etitulo = ?, idForCandidate = ? WHERE etitulo = ?";
            PreparedStatement stmt = DataBaseController.prepareStatement(sql);
            stmt.setString(1, candidate.getEtitulo());
            stmt.setString(2, candidate.getIdForCandidate());
            stmt.setString(3, candidate.getEtitulo());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean deleteCandidate( String etitulo){
        try {
            String sql = "DELETE FROM Candidate WHERE etitulo = ?";
            PreparedStatement stmt = DataBaseController.prepareStatement(sql);
            stmt.setString(1, etitulo);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    public static Candidate getCandidate( String etitulo){
        try {
            String sql = "SELECT * FROM Candidate WHERE etitulo = ?";
            PreparedStatement stmt = DataBaseController.prepareStatement(sql);
            stmt.setString(1, etitulo);
            ResultSet rs = stmt.executeQuery();
            Candidate candidate = new Candidate(rs.getString("etitulo"), rs.getString("idForCandidate"));
            return candidate;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static ArrayList<Candidate> getAllCandidates(){
        try {
            String sql = "SELECT * FROM Candidate";
            PreparedStatement stmt = DataBaseController.prepareStatement(sql);
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
