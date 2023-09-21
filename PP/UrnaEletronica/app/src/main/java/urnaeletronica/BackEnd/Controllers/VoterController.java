package urnaeletronica.BackEnd.Controllers;
import java.sql.*;
import java.util.ArrayList;

import urnaeletronica.BackEnd.Models.Voter;
public class VoterController {
    
    public VoterController() {
     
    }

    public static boolean createVoter(Voter voter){
        String sql = "INSERT INTO Voter (name, cpf, etitulo) VALUES (?, ?, ?)";
        
        try (PreparedStatement stmt = DataBaseController.prepareStatement(sql)) {
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
    

    public static boolean deleteVoter(String cpf) {
        String sql = "DELETE FROM Voter WHERE cpf = ?";
        
        try (PreparedStatement stmt = DataBaseController.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    

    public static boolean updateVoter(Voter voter){
        String sql = "UPDATE Voter SET name = ?, cpf = ?, etitulo = ? WHERE cpf = ?";
        
        try (PreparedStatement stmt = DataBaseController.prepareStatement(sql)) {
            stmt.setString(1, voter.getName());
            stmt.setString(2, voter.getCpf());
            stmt.setString(3, voter.getEtitulo());
            stmt.setString(4, voter.getCpf());
            
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    

    public static Voter getVoter( String etitulo){
        try {
            System.out.println(etitulo);
            String sql = "SELECT * FROM Voter WHERE etitulo = ?";
            PreparedStatement stmt = DataBaseController.prepareStatement(sql);
            stmt.setString(1, etitulo);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) { // Mova o cursor para a primeira linha antes de acessar os resultados
                Voter voter = new Voter(rs.getString("name"), rs.getString("cpf"), rs.getString("etitulo"));
                System.out.println("Eleitor: "+voter);
                return voter;
            } else {
                System.out.println("Nenhum eleitor encontrado");
                return null;
            }
    
        } catch (SQLException e) {
            System.out.println("Erro");
            System.out.println(e.getMessage());
            return null;
        }
    }
    

    public static ArrayList<Voter> getAllVoters(){
        ArrayList<Voter> voters = new ArrayList<>();
        String sql = "SELECT * FROM Voter";
        
        try (
            PreparedStatement stmt = DataBaseController.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
        ) {
            while (rs.next()) {
                Voter voter = new Voter(rs.getString("name"), rs.getString("cpf"), rs.getString("etitulo"));
                voters.add(voter);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        
        return voters;
    }
    

}
