package model;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.jetbrains.annotations.NotNull;

import java.sql.*;

public class User implements Authentication {
    private Connection con;
    private int id;

    @Override
    public boolean login(String username, String password) {
        String hashedPassword = "";

        try {
            this.con = DatabaseConnection.getConnection();
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }

        String queryUsername = "SELECT * FROM users WHERE username = ?";

        try {
            PreparedStatement pstmt = con.prepareStatement(queryUsername);
            pstmt.setString(1, username);
            pstmt.execute();
            ResultSet rs = pstmt.getResultSet();

            if (!rs.next()) {
                System.out.println("Login Failed! Wrong username");
                return false;
            } else {
                do {
                    hashedPassword = rs.getString("password");
                    this.id = rs.getInt("id");
                } while (rs.next());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword);
        return result.verified;
    }

    @Override
    public void register(String username, @NotNull String password, String name, String address) throws SQLException {
        String hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());

        try {
            this.con = DatabaseConnection.getConnection();
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }

        String queryUser = "INSERT INTO users (username, password, name, address) VALUES(?,?,?,?)";

        PreparedStatement pstmt = con.prepareStatement(queryUser);
        pstmt.setString(1, username);
        pstmt.setString(2, hashedPassword);
        pstmt.setString(3, name);
        pstmt.setString(4, address);
        pstmt.execute();
        pstmt.close();
    }

    @Override
    public void delete(String username) throws SQLException {
        try {
            this.con = DatabaseConnection.getConnection();
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }

        String query = "DELETE FROM users WHERE username = ?";

        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, username);
        pstmt.execute();

    }

    @Override
    public void update(int userId, String newPassword) throws SQLException {
        this.con = DatabaseConnection.getConnection();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String hashedPassword = BCrypt.withDefaults().hashToString(12, newPassword.toCharArray());

        String query = "UPDATE users SET password = ?, updated_at = ? WHERE id = ?";
        PreparedStatement pstmt = this.con.prepareStatement(query);
        pstmt.setString(1, hashedPassword);
        pstmt.setTimestamp(2, timestamp);
        pstmt.setInt(3, userId);
        pstmt.execute();
    }

    public int getId() {
        return this.id;
    }

    public int getIdByUsername(String username) throws SQLException {
        this.con = DatabaseConnection.getConnection();
        String query = "SELECT * FROM users WHERE username = '" + username + "'";
        Statement stmt = this.con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        this.id = rs.getInt("id");
        return this.id;
    }


}
