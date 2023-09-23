package urnaeletronica.BackEnd.Controllers;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import urnaeletronica.BackEnd.Models.Voto;

public class VoteController {
    public static boolean vote(Voto vote) {
        try {
            String sql = "INSERT INTO VotoCandidato (etitulo, candidateNumber) VALUES (?, ?)";
            PreparedStatement stmt = DataBaseController.prepareStatement(sql);
            stmt.setString(1, vote.getEtitulo());
            stmt.setString(2, vote.getCandidate());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }   

    public static boolean verifyIfAlredyVoted(String eTitulo) {
        try {
            String sql = "SELECT * FROM VotoCandidato WHERE etitulo = ?";
            PreparedStatement stmt = DataBaseController.prepareStatement(sql);
            stmt.setString(1, eTitulo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // getTotalVotes
    public static   String getTotalVotes() {
        try {
            String sql = "SELECT COUNT(*) FROM VotoCandidato";
            PreparedStatement stmt = DataBaseController.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("COUNT(*)");
            } else {
                return "0";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return "0";
        }
    }

    public static String getNullVotes() {
        try {
            String sql = "SELECT COUNT(*) FROM VotoCandidato WHERE candidateNumber = 0";
            PreparedStatement stmt = DataBaseController.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("COUNT(*)");
            } else {
                return "0";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return "0";
        }
    }

    public static String getWhiteVotes() {
        try {
            String sql = "SELECT COUNT(*) FROM VotoCandidato WHERE candidateNumber = -1";
            PreparedStatement stmt = DataBaseController.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("COUNT(*)");
            } else {
                return "0";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return "0";
        }
    }

    public static String getValidVotes() {
        try {
            String sql = "SELECT COUNT(*) FROM VotoCandidato WHERE candidateNumber != 0 AND candidateNumber != -1";
            PreparedStatement stmt = DataBaseController.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("COUNT(*)");
            } else {
                return "0";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return "0";
        }
    }

    public static String getVotes(String candidateNumber) {
        try {
            String sql = "SELECT COUNT(*) FROM VotoCandidato WHERE candidateNumber = ?";
            PreparedStatement stmt = DataBaseController.prepareStatement(sql);
            stmt.setString(1, candidateNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("COUNT(*)");
            } else {
                return "0";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return "0";
        }
    }

    public static String getWinner() {
        try {
            String sql = "SELECT candidateNumber, COUNT(*) FROM VotoCandidato GROUP BY candidateNumber ORDER BY COUNT(*) DESC LIMIT 1";
            PreparedStatement stmt = DataBaseController.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("candidateNumber");
            } else {
                return "0";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return "0";
        }
    }

        public static Map<String, Integer> getAllVotes() {
        try {
            String sql = "SELECT candidateNumber, COUNT(*) as voteCount FROM VotoCandidato GROUP BY candidateNumber";
            PreparedStatement stmt = DataBaseController.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            Map<String, Integer> votesMap = new HashMap<>();
            
            while (rs.next()) {
                String candidateNumber = rs.getString("candidateNumber");
                int voteCount = rs.getInt("voteCount");
                votesMap.put(candidateNumber, voteCount);
            }
            
            return votesMap;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
