package model;

import java.sql.*;
import java.util.Calendar;

public class Transaction {
    private final Connection con = DatabaseConnection.getConnection();

    public void createBorrowTransaction(int userId, int bookId) throws SQLException {
        Collection collection = new Collection();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp.getTime());
        cal.add(Calendar.DAY_OF_MONTH, 7);
        timestamp = new Timestamp(cal.getTime().getTime());

        String query = "INSERT INTO transactions (user_id, collection_id, due_at) VALUES (?,?,?)";
        PreparedStatement pstmt = this.con.prepareStatement(query);
        pstmt.setInt(1, userId);
        pstmt.setInt(2, bookId);
        pstmt.setTimestamp(3, timestamp);
        pstmt.execute();
        pstmt.close();

        collection.borrowBook(bookId);
    }

    public void returnTransaction(int transactionId) throws SQLException {
        Collection collection = new Collection();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        int bookId = 0;
        ResultSet rs;
        String query = "UPDATE transactions SET returned_at = ? WHERE id = ?";
        PreparedStatement pstmt = this.con.prepareStatement(query);
        pstmt.setTimestamp(1, timestamp);
        pstmt.setInt(2, transactionId);
        pstmt.execute();
        pstmt.close();

        String queryBook = "SELECT collection_id FROM transactions WHERE id = " + transactionId;
        Statement stmt = this.con.createStatement();
        rs = stmt.executeQuery(queryBook);
        while (rs.next()) {
            bookId = rs.getInt("collection_id");
        }

        collection.returnBook(bookId);
    }

    public ResultSet getUserTransactions(int userId) throws SQLException {
        String query = "SELECT t.id as id, c.book_title as title, t.due_at as due FROM transactions t INNER JOIN collections c ON c.id = t.collection_id WHERE user_id = " + userId + " AND returned_at IS NULL";
        Statement stmt = this.con.createStatement();
        return stmt.executeQuery(query);
    }



}
