package LabbTreInkopsLista;

import java.sql.*;
import java.util.Scanner;

public class LabbTre {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {


        boolean abort = false;


        while(!abort) {
            printMenu();
            int menuChoice = sc.nextInt();
            sc.nextLine();
            switch (menuChoice) {
                case 0 -> {
                    System.out.println("Stänger av..");
                    abort = true;
                }
                case 1 -> showAll();
                case 2 -> addToList();
                case 3 -> deleteProduct();
                case 4 -> updates();
            }
        }
    }

    private static void updates() {
        System.out.println("Skriv in id:t på varan du vill ändra på.");
        int productInput = sc.nextInt();
        System.out.println("Skriv antal du vill ändra till.");
        int productAmountInput = sc.nextInt();
        updateProduct(productInput,productAmountInput);
        sc.nextLine();
    }


    private static void updateProduct(int productId, int productAmountInput) {
        String sql = "UPDATE inkopsLista SET inkopsListaAntal = ?  " + "WHERE inkopsId = ?";
        try{
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(2,productId);
            pstmt.setInt(1,productAmountInput);
            pstmt.executeUpdate();
            System.out.println("Du har lagt till en ny vara");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void showAll() {
        String sql = "SELECT * FROM inkopsLista";

        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                System.out.println(rs.getInt("inkopsId") +  "\t" +
                        rs.getString("inkopsListaVara") + "\t" +
                        rs.getString("inkopsListaAntal"));
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static Connection connect(){

        String url = "jdbc:sqlite:C:\\Users\\vikto\\Documents\\Databaser\\inkopsListaLabben.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    private static void printMenu() {
        System.out.println("Gör ett val:");
        System.out.println("""
                0  - Stäng av
                1  - Visa hela inköpslistan
                2  - Lägga till inköpslistan
                3  - Ta bort från inköpslistan
                4  - Ändra antal i listan
                """); // glöm inte att lägga till listan
    }
    private static void addToList(){
        System.out.println("Skriv vilken vara du vill lägga till");
        String productInput = sc.nextLine();
        System.out.println("Skriv antal av varan");
        int productAmountInput = sc.nextInt();
        insert(productInput,productAmountInput);
        sc.nextLine();

    }

    private static void deleteProduct(){
        System.out.print("Skriv in vilket id:t på varan som ska ta bort: ");
        int inputId = sc.nextInt();
        delete(inputId);
        sc.nextLine();
    }

    private static void delete(int id){
        String sql = "DELETE FROM inkopsLista WHERE inkopsId = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Du har nu tagit bort varan");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    private static void insert(String productInput, int productAmountInput) {
        String sql = "INSERT INTO inkopsLista(inkopsListaVara,inkopsListaAntal) VALUES(?,?)";

        try{
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,productInput);
            pstmt.setInt(2,productAmountInput);
            pstmt.executeUpdate();
            System.out.println("Du har lagt till en ny vara");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
