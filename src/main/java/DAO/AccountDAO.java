package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;


public class AccountDAO {
    
    /**
     * Creates an account.
     * @param acc the account to be created.
     * @return new account.
     */
    public Account createAccount(Account acc) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, acc.getUsername());
            preparedStatement.setString(2, acc.getPassword());

            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                int id = (int)rs.getLong(1);
                return new Account(id, 
                    acc.getUsername(), 
                    acc.getPassword());
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return null; // Account creation failed
    }

    /**
     * Gets an account by ID.
     * @param id the ID of the account.
     * @return account.
     */
    public Account getAccountById (int id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                Account a = new Account(rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"));
                return a;
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null; // Retrieval failed
    }

    /**
     * Gets an account by username.
     * @param username the username of the account.
     * @return account.
     */
    public Account getAccountByUsername (String username) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Account a = new Account(rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"));
                return a;
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null; // Retrieval failed
    }

    /**
     * Logs into an account if valid credentials are provided.
     * @param acc the account to be logged into.
     * @return account.
     */
    public Account loginAccount (Account acc) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, acc.getUsername());
            preparedStatement.setString(2, acc.getPassword());

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                Account a = new Account(rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"));
                return a;
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null; // login failed
    }

}
