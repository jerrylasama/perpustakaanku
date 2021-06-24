package view;

import de.vandermeer.asciitable.AsciiTable;
import model.Collection;
import model.Transaction;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class MainActivity {

    public static void main(String[] args) throws SQLException {
        User user = new User();
        String username;
        String password;
        int choice;
        boolean exit = false;

        while (!exit) {
            choice = menu();

            switch (choice) {
                case 1:
                    Scanner scanner = new Scanner(System.in);

                    System.out.print("Username: ");
                    username = scanner.nextLine();
                    System.out.print("Password: ");
                    password = scanner.nextLine();

                    if (user.login(username, password)) {
                        System.out.println("Successfully Logged In!");
                        menu2(user.getId());
                    } else
                        System.out.println("Login Failed! Wrong Password");
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    exit = true;
                    break;
                default:
                    System.out.println("Please select the number of the menu you want to access");
            }
        }
    }

    public static int menu() {
        int choice;
        Scanner scanner = new Scanner(System.in);
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("====\tLibrary in Terminal\t====");
        System.out.println("1. Login\n2. Register\n3. Exit");
        System.out.print("Selection: ");
        choice = scanner.nextInt();
        return choice;
    }

    public static void menu2(int userId) throws SQLException {
        Collection collection = new Collection();
        Transaction transaction = new Transaction();
        User user = new User();
        ResultSet rs;
        AsciiTable at;
        String render;
        int choice;
        int transactionChoice;
        int bookChoice;
        boolean logOut = false;
        Scanner scanner = new Scanner(System.in);

        while (!logOut) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("====\tAdmin Zone\t====");
            System.out.println("1. Borrow a Book\n2. Return a Book");
            System.out.println("3. Borrowing List\n4. Reset Password\n5. Export Transactions");
            System.out.println("6. Log out");
            System.out.print("Pilihan: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    rs = collection.getCollections();
                    at = new AsciiTable();
                    at.addRule();
                    at.addRow("id", "Title", "Current Stock");
                    while (rs.next()) {
                        at.addRule();
                        at.addRow(rs.getInt("id"), rs.getString("book_title"), rs.getInt("book_current"));
                    }
                    at.addRule();
                    render = at.render();
                    System.out.println(render);
                    System.out.print("Choose book id: ");
                    bookChoice = scanner.nextInt();
                    transaction.createBorrowTransaction(userId, bookChoice);
                    System.out.println("Borrowed. Don't forget to return it on time!");
                    break;
                case 2:
                    rs = transaction.getUserTransactions(userId);
                    at = new AsciiTable();
                    at.addRule();
                    at.addRow("id", "Title", "Due");
                    while (rs.next()) {
                        at.addRule();
                        at.addRow(rs.getInt("id"), rs.getString("title"), rs.getTimestamp("due"));
                    }
                    at.addRule();
                    render = at.render();
                    System.out.println(render);
                    System.out.print("Choose id: ");
                    transactionChoice = scanner.nextInt();
                    transaction.returnTransaction(transactionChoice);
                    break;
                case 3:
                    rs = transaction.getUserTransactions(userId);
                    at = new AsciiTable();
                    at.addRule();
                    at.addRow("id", "Title", "Due");
                    while (rs.next()) {
                        at.addRule();
                        at.addRow(rs.getInt("id"), rs.getString("title"), rs.getTimestamp("due"));
                    }
                    at.addRule();
                    render = at.render();
                    System.out.println(render);
                    break;
                case 4:
                    System.out.println("Insert new password: ");
                    scanner.nextLine();
                    String newPassword = scanner.nextLine();
                    user.update(userId, newPassword);
                    System.out.println("Password updated!");
                    break;
                case 5:
                    System.out.println("Insert target location: ");
                    scanner.nextLine();
                    String fileLocation = scanner.nextLine();
                    transaction.exportTransactions(fileLocation);
                    System.out.println("Exported!");
                    break;
                case 6:
                    System.out.println("Exiting...");
                    logOut = true;
                    break;
                default:
                    System.out.println("Please select the number of the menu you want to access");
            }
        }


    }

    public static void register() throws SQLException {
        User user = new User();
        Scanner scanner = new Scanner(System.in);
        String username;
        String password;
        String name;
        String address;

        System.out.println("====\tRegister\t====");
        System.out.print("Username: ");
        username = scanner.nextLine();
        System.out.print("Password: ");
        password = scanner.nextLine();
        System.out.println("Name: ");
        name = scanner.nextLine();
        System.out.println("Address: ");
        address = scanner.nextLine();


        user.register(username, password, name, address);

        System.out.println("Successfully Registered!");
    }


}
