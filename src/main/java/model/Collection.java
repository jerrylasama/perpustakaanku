package model;

import java.sql.*;
import java.util.Scanner;

public class Collection {
    private final Connection con = DatabaseConnection.getConnection();

    public void addCollection() throws SQLException {
        int bookAmount;
        String bookTitle;

        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukan judul buku: ");
        bookTitle = scanner.nextLine();
        System.out.print("Masukan jumlah buku: ");
        bookAmount = scanner.nextInt();

        String query = "INSERT INTO collections (book_title, book_total, book_current VALUES (?,?,?)";
        PreparedStatement pstmt = this.con.prepareStatement(query);
        pstmt.setString(1, bookTitle);
        pstmt.setInt(2, bookAmount);
        pstmt.setInt(3, bookAmount);
        pstmt.execute();
    }

    public void returnBook(int bookId) throws SQLException {
        String query = "UPDATE collections SET book_current = book_current + 1 WHERE id = ?";
        PreparedStatement pstmt = this.con.prepareStatement(query);
        pstmt.setInt(1, bookId);
        pstmt.execute();
    }

    public void borrowBook(int bookId) throws SQLException {
        String query = "UPDATE collections SET book_current = book_current - 1 WHERE id = ?";
        PreparedStatement pstmt = this.con.prepareStatement(query);
        pstmt.setInt(1, bookId);
        pstmt.execute();
    }

    public ResultSet getCollections() throws SQLException
    {
        String query = "SELECT * FROM collections";
        Statement stmt = con.createStatement();
        return stmt.executeQuery(query);
    }
}
