package wordpuzzle.mappers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import wordpuzzle.dbconnect.DatabaseConnect;
import wordpuzzle.models.Users;

public class UsersMapper {

	public Users getUser(String msisdn) throws SQLException {
		
		Users user = null;
		
		DatabaseConnect connect = new DatabaseConnect();

        Connection con = connect.dbConnect();
        String query = "SELECT * FROM Users WHERE msisdn = ?";
        
        PreparedStatement preparedStmt = con.prepareStatement(query);
        preparedStmt.setString(1, msisdn);
        
        ResultSet rs = preparedStmt.executeQuery();
        
        while (rs.next()) {
        		user = new Users();
        		
        		user.setId(rs.getInt("id"));
        		user.setMsisdn(msisdn);
        		user.setRegister(rs.getString("register"));
        		user.setLastSent(rs.getInt("last_sent"));
        		user.setStatus(rs.getString("status"));

        }
        
        con.close();
        
        return user;
	}
	
	public void registerUser(String msisdn) throws SQLException {
		
		DatabaseConnect connect = new DatabaseConnect();
		
		Connection con = connect.dbConnect();
        String query = "INSERT INTO Users (msisdn, register, status, last_sent, wins) VALUES (?, '1', '0', '0', '0')";
        
        PreparedStatement preparedStmt = con.prepareStatement(query);
        preparedStmt.setString(1, msisdn);
        
        System.out.println(preparedStmt);
        preparedStmt.execute();
        con.close();
	}
	
	public void setLastSent(String msisdn, int id) throws SQLException {
		
		DatabaseConnect connect = new DatabaseConnect();
		
		Connection con = connect.dbConnect();
        String query = "UPDATE Users SET last_sent = ? WHERE msisdn = ?";
        
        PreparedStatement preparedStmt = con.prepareStatement(query);
        preparedStmt.setInt(1, id);
        preparedStmt.setString(2, msisdn);
        
        System.out.println(preparedStmt);
        preparedStmt.execute();
        con.close();
	}
	
	public void setStatus(String msisdn, String status) throws SQLException {
		
		DatabaseConnect connect = new DatabaseConnect();
		
		Connection con = connect.dbConnect();
        String query = "UPDATE Users SET status = ? WHERE msisdn = ?";
        
        PreparedStatement preparedStmt = con.prepareStatement(query);
        preparedStmt.setString(1, status);
        preparedStmt.setString(2, msisdn);
        
        System.out.println(preparedStmt);
        preparedStmt.execute();
        con.close();
	}
}
