package database;

import com.google.gson.Gson;
import mainClasses.Monimo_didaktiko;
import database.DB_Connection;
import mainClasses.Symvasiouxo_didaktiko;

import java.sql.*;

public class EditMonimoDidaktikoTable {

    public static Monimo_didaktiko jsonToObject(String json) {
        Gson gson = new Gson();
        Monimo_didaktiko monimo_didaktiko = gson.fromJson(json, Monimo_didaktiko.class);
        return monimo_didaktiko;
    }

    /* The SQLException should be handled by the servlet to inform the front end that something went wrong
     * */
    public static void createNewDatabaseEntry(Monimo_didaktiko monimo_didaktiko) throws SQLException {
        try {
            Connection con = DB_Connection.getConnection();
            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " monimo_didaktiko (perm_id,years,salary,family_allowance,research_allowance)"
                    + " VALUES ("
                    + "'" + monimo_didaktiko.getPermId() + "',"
                    + "'" + monimo_didaktiko.getYears() + "',"
                    + "'" + monimo_didaktiko.getSalary() + "',"
                    + "'" + monimo_didaktiko.getFamilyAllowance() + "',"
                    + "'" + monimo_didaktiko.getResearchAllowance() + "'"
                    + ")";

            stmt.executeUpdate(insertQuery);
            System.out.println("#monimo_didaktiko was successfully added in the database.");
            stmt.close();
        }
        catch (ClassNotFoundException e) {System.err.println("ClassNotFoundException in createNewMonimoDidaktiko");}
    }

    /* The SQLException should be handled by the servlet to inform the front end that something went wrong
     * */
    public static String databaseToJSON(int perm_id) throws SQLException {

        try {
            ResultSet rs;
            Connection con = DB_Connection.getConnection();
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM monimo_didaktiko WHERE perm_id = '" + perm_id + "'");
            rs.next();
            String json=DB_Connection.getResultsToJSON(rs);
            return json;
        }
        catch (ClassNotFoundException e) {System.err.println("ClassNotFoundException at databseMonimoDidaktikoToJSON");}

        return null;
    }

    /* The SQLException should be handled by the servlet to inform the front end that something went wrong
     * */
    public static Monimo_didaktiko ObjectFromDatabase(int perm_id) throws SQLException {
        return jsonToObject(databaseToJSON(perm_id));
    }

    public static void update(Monimo_didaktiko monimo_didaktiko) throws SQLException {
        Statement stmt = null;
        Connection con = null;
        try {
            con = DB_Connection.getConnection();
            stmt = con.createStatement();
            StringBuilder insQuery = new StringBuilder();

            insQuery.append("UPDATE monimo_didaktiko ")
                    .append("SET ")
                    .append("years = '" + monimo_didaktiko.getYears() + "', " +
                            "salary = '" + monimo_didaktiko.getSalary() + "', " +
                            "family_allowance = '" + monimo_didaktiko.getFamilyAllowance() + "', " +
                            "research_allowance = '" + monimo_didaktiko.getResearchAllowance())
                    .append("' WHERE perm_id = " + monimo_didaktiko.getPermId() + ";");

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

            insQuery.append("DELETE FROM monimo_didaktiko WHERE perm_id = " + perm_id + ";" );
            PreparedStatement stmtIns = con.prepareStatement(insQuery.toString());
            stmtIns.execute();
            System.out.println("#Delete executed successfully");


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DB_Connection.closeDBConnection(stmt, con);
        }
    }
}
