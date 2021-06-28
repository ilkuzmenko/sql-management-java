import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.ResultSet;


public class PostgreSQL {

	// make connection with db
    public static Connection connect() {

        Connection c = null;

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:PORT/NAME_DB",
                    "NAME_DB",
                    "PASSWORD"
            );
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");
        return c;
    }

	// insert function to db
    public static void insert(String sql) {

        Connection c;
        Statement stmt;

        try {
            c = connect();
            c.setAutoCommit(false);
            stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation insert successfully");
    }

	// update function to db
    public static void update(String sql){

        Connection c;
        Statement stmt;

        try {
            c = connect();
            c.setAutoCommit(false);
            stmt = c.createStatement();
            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation update successfully");
    }

	// select list function to db
	// return list with selected data
    public static List<String> select(String table, String columnLabel) {

        ArrayList<String> selectedData = new ArrayList<>();
        Connection c;
        Statement stmt;

        try {
            c = connect();
            c.setAutoCommit(false);
            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery( "SELECT * FROM " + table + ";" );
            while ( rs.next() ) {
                String link = rs.getString(columnLabel);
                selectedData.add(link);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation select successfully");
        return selectedData;
    }

    //create table in db 
    public static void create(String sql) {
        Connection c;
        Statement stmt;

        try {
            c = connect();

            stmt = c.createStatement();
            String sql = "CREATE TABLE NAME " +
                    "(ID INT PRIMARY KEY     NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " AGE            INT     NOT NULL, " +
                    " ADDRESS        CHAR(50))";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }

	// delete sql function
    public static void delete(String sql) {   // "DELETE from COMPANY where ID = 2;";

        Connection c;
        Statement stmt;

        try {
            c = connect();
            c.setAutoCommit(false);

            stmt = c.createStatement();
            stmt.executeUpdate(sql);
            c.commit();

            ResultSet rs = stmt.executeQuery( "SELECT * FROM NAME;" );
            while ( rs.next() ) {
                int id = rs.getInt("id");
                String  name = rs.getString("name");
                int age  = rs.getInt("age");
                String  address = rs.getString("address");
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation delete successfully");
    }


	// testing database connection
    public static void testing(){

        System.out.println("-------- PostgreSQL JDBC Connection Testing ------------");

        try {

            Class.forName("org.postgresql.Driver");

        } catch (ClassNotFoundException e) {

            System.out.println("Where is your PostgreSQL JDBC Driver? "
                    + "Include in your library path!");
            e.printStackTrace();
            return;

        }
        System.out.println("PostgreSQL JDBC Driver Registered!");
        Connection connection;

        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:PORT/NAME_DB",
                    "NAME_DB",
                    "PASSWORD");
        } catch (SQLException e) {

            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }
        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }
    }

}
