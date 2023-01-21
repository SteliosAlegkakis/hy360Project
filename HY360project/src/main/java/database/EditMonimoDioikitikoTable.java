package database;

import com.google.gson.Gson;
import mainClasses.Monimo_didaktiko;
import mainClasses.Monimo_dioikitiko;
import database.DB_Connection;

import java.sql.*;
import java.util.ArrayList;

public class EditMonimoDioikitikoTable {
    public static Monimo_dioikitiko jsonToObject(String json) {
        Gson gson = new Gson();
        Monimo_dioikitiko monimo_dioikitiko = gson.fromJson(json, Monimo_dioikitiko.class);
        return monimo_dioikitiko;
    }

    /* The SQLException should be handled by the servlet to inform the front end that something went wrong
     * */
    public static void createNewDatabaseEntry(Monimo_dioikitiko monimo_dioikitiko) throws SQLException {
        try {
            Connection con = DB_Connection.getConnection();
            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " monimo_dioikitiko (perm_id,years,salary,family_allowance)"
                    + " VALUES ("
                    + "'" + monimo_dioikitiko.getPermId() + "',"
                    + "'" + monimo_dioikitiko.getYears() + "',"
                    + "'" + monimo_dioikitiko.getSalary() + "',"
                    + "'" + monimo_dioikitiko.getFamilyAllowance() + "'"
                    + ")";

            stmt.executeUpdate(insertQuery);
            System.out.println("#monimo_dioikitiko was successfully added in the database.");
            stmt.close();

        }
        catch (ClassNotFoundException e) {System.err.println("ClassNotFoundException in createNewMonimoDioikitiko");}
    }

    /* The SQLException should be handled by the servlet to inform the front end that something went wrong
     * */
    public static String databaseToJSON(int perm_id) throws SQLException {

        try {
            ResultSet rs;
            Connection con = DB_Connection.getConnection();
            Statement stmt = con.createStatement();

            rs = stmt.executeQuery("SELECT * FROM monimo_dioikitiko WHERE perm_id = '" + perm_id + "'");
            rs.next();
            String json=DB_Connection.getResultsToJSON(rs);
            return json;
        }
        catch (ClassNotFoundException e) {System.err.println("ClassNotFoundException at databseMonimoDioikitikoToJSON");}

        return null;
    }

    /* The SQLException should be handled by the servlet to inform the front end that something went wrong
     * */
    public static Monimo_dioikitiko monimoDioikitikoFromDatabase(int perm_id) throws SQLException {
        return jsonToObject(databaseToJSON(perm_id));
    }

    public static void update(Monimo_dioikitiko monimo_dioikitiko) throws SQLException {
        Statement stmt = null;
        Connection con = null;
        try {
            con = DB_Connection.getConnection();
            stmt = con.createStatement();
            StringBuilder insQuery = new StringBuilder();

            insQuery.append("UPDATE monimo_dioikitiko ")
                    .append("SET ")
                    .append("years = '" + monimo_dioikitiko.getYears() + "', " +
                            "salary = '" + monimo_dioikitiko.getSalary() + "', " +
                            "family_allowance = '" + monimo_dioikitiko.getFamilyAllowance())
                    .append("' WHERE perm_id = " + monimo_dioikitiko.getPermId() + ";");

            PreparedStatement stmtIns = con.prepareStatement(insQuery.toString());
            stmtIns.executeUpdate();
            System.out.println("#Update executed successfully");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DB_Connection.closeDBConnection(stmt, con);
        }
    }
    public static void delete(int perm_id) throws SQLException {
        Statement stmt = null;
        Connection con = null;
        try {
            con = DB_Connection.getConnection();
            stmt = con.createStatement();
            StringBuilder insQuery = new StringBuilder();

            insQuery.append("DELETE FROM monimo_dioikitiko WHERE perm_id = " + perm_id + ";" );
            PreparedStatement stmtIns = con.prepareStatement(insQuery.toString());
            stmtIns.execute();
            System.out.println("#Delete executed successfully");


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DB_Connection.closeDBConnection(stmt, con);
        }
    }

    public static ArrayList<Monimo_dioikitiko> getAllEntrys(){
        try {
            Connection con = DB_Connection.getConnection();
            Statement stmt = con.createStatement();
            ArrayList<Monimo_dioikitiko> md = new ArrayList<Monimo_dioikitiko>();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT * FROM monimo_dioikitiko");
            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Monimo_dioikitiko monimo_dioikitiko = gson.fromJson(json, Monimo_dioikitiko.class);
                md.add(monimo_dioikitiko);
            }
            return md;

        } catch (Exception e) {
            System.err.println("Exception in getAllEntrys()");
        }
        return null;
    }
}
