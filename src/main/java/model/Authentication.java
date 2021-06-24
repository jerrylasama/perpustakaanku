package model;

import java.sql.SQLException;

public interface Authentication {
    boolean login(String username, String password);
    void register(String username, String password, String name, String address) throws SQLException;
    void delete(String username) throws SQLException;
    void update(int userId, String newPassword) throws SQLException;
}
