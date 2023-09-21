package urnaeletronica.BackEnd.Controllers;
import java.sql.*;
import java.util.ArrayList;

public class VoteController {
    public static boolean vote(String eTitulo, String candidateNumber) {
        try {
            String sql = "INSERT INTO vote (etitulo, candidateNumber) VALUES (?, ?)";
            PreparedStatement stmt = DataBaseController.prepareStatement(sql);
            stmt.setString(1, eTitulo);
            stmt.setString(2, candidateNumber);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }   

    public static boolean verifyIfAlredyVoted(String eTitulo) {
        try {
            String sql = "SELECT * FROM vote WHERE etitulo = ?";
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

    public static String getVotes(String candidateNumber) {
        try {
            String sql = "SELECT COUNT(*) FROM vote WHERE candidateNumber = ?";
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
            String sql = "SELECT candidateNumber, COUNT(*) FROM vote GROUP BY candidateNumber ORDER BY COUNT(*) DESC LIMIT 1";
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

    public static ArrayList<String> getAllVotes() {
        try {
            String sql = "SELECT * FROM vote";
            PreparedStatement stmt = DataBaseController.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            ArrayList<String> votes = new ArrayList<String>();
            while (rs.next()) {
                votes.add(rs.getString("etitulo") + " " + rs.getString("candidateNumber"));
            }
            return votes;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
