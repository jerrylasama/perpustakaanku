package model;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

    public void exportTransactions(String fileLocation) throws SQLException {
        String query = "SELECT t.id, t.user_id, c.book_title, t.borrowed_at, t.due_at, t.returned_at FROM transactions t INNER JOIN collections c ON c.id = t.collection_id";
        String outputLocation = fileLocation + "/transactions.xlsx";

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("transactions");
        Row rowhead = sheet.createRow((short) 0);
        rowhead.createCell((short) 0).setCellValue("id");
        rowhead.createCell((short) 1).setCellValue("user_id");
        rowhead.createCell((short) 2).setCellValue("Book Name");
        rowhead.createCell((short) 3).setCellValue("Borrowed");
        rowhead.createCell((short) 4).setCellValue("Due");
        rowhead.createCell((short) 5).setCellValue("Returned");

        Statement stmt = this.con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        int i = 1;
        while (rs.next()) {
            Row row = sheet.createRow((short) i);
            row.createCell((short) 0).setCellValue(Integer.toString(rs.getInt("id")));
            row.createCell((short) 1).setCellValue(Integer.toString((rs.getInt("user_id"))));
            row.createCell((short) 2).setCellValue(rs.getString("book_title"));
            row.createCell((short) 3).setCellValue(rs.getTimestamp("borrowed_at").toString());
            row.createCell((short) 4).setCellValue(rs.getTimestamp("due_at").toString());
            row.createCell((short) 5).setCellValue(rs.getTimestamp("returned_at").toString());
            i++;
        }

        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(outputLocation);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
