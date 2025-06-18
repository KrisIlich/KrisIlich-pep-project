package DAO;

import java.sql.Connection;
import Model.Account;
import Util.ConnectionUtil;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;


public class AccountDAO {
    public Account registerAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ps.executeUpdate();

            ResultSet pkResultSet = ps.getGeneratedKeys();
            if (pkResultSet.next()) {
                int generatedAccountID = (int)pkResultSet.getLong(1);
                return new Account(generatedAccountID, account.getUsername(), account.getPassword());
            }

        } catch(SQLException e) {
            System.out.println(e);
        }

        return null;
    }

    public Account accountLogin(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("account_id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                return new Account(id, username, password);
            } 
            
            return null;
        

        } catch(SQLException e) {
            System.out.println(e);
        }
        return null;
    }

}
