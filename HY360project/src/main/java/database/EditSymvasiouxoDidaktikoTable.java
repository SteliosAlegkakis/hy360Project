package database;

import com.google.gson.Gson;
import mainClasses.Monimo_didaktiko;
import mainClasses.Symvasiouxo_didaktiko;
import mainClasses.Ypallilos;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.ArrayList;

public class EditSymvasiouxoDidaktikoTable {

    public static Symvasiouxo_didaktiko jsonToObject(String json) {
        Gson gson = new Gson();
        Symvasiouxo_didaktiko symvasiouxo_didaktiko = gson.fromJson(json, Symvasiouxo_didaktiko.class);
        return symvasiouxo_didaktiko;
    }

    public static void createNewDatabaseEntry(Symvasiouxo_didaktiko sd) throws SQLException {
        try {
            Connection con = DB_Connection.getConnection();
            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " symvasiouxo_didaktiko (temp_id,salary,family_allowance,exp_date,lib_allowance)"
                    + " VALUES ("
                    + "'" + sd.getTempId() + "',"
                    + "'" + sd.getSalary() + "',"
                    + "'" + sd.getFamilyAllowance() + "',"
                    + "'" + sd.getExpDate() + "',"//the date has to be in yyyy-mm-dd format
                    + "'" + sd.getLibAllowance() + "'"
                    + ")";

            stmt.executeUpdate(insertQuery);
            System.out.println("#symvasiouxo_didaktiko was successfully added in the database.");
            stmt.close();

        }
        catch (ClassNotFoundException e) {System.err.println(("ClassNotFoundException in createNewYpallilos"));}
    }

    public static void createTable() throws SQLException, ClassNotFoundException {

        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        String query = "CREATE TABLE symvasiouxo_didaktiko "
                + "(temp_id INTEGER not NULL , "
                + "    salary double not null,"
                + "    family_allowance double not null,"
                + "    exp_date DATE not null,"
                + "    lib_allowance DOUBLE not null,"
                + " PRIMARY KEY ( temp_id))";
        stmt.execute(query);
        stmt.close();
    }

    public static String databaseToJSON(int temp_id) throws SQLException {

        ResultSet rs;
        try {
            Connection con = DB_Connection.getConnection();
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM symvasiouxo_didaktiko WHERE temp_id = '" + temp_id + "'");
            rs.next();
            String json=DB_Connection.getResultsToJSON(rs);
            return json;
        }
        catch (ClassNotFoundException e) {System.err.println("ClassNotFoundException in databaseSymvasiouxoDidaktikoToJSON");}
        return null;
    }

    public static void update(Symvasiouxo_didaktiko symvasiouxo_didaktiko) throws SQLException {
        Statement stmt = null;
        Connection con = null;
        try {
            con = DB_Connection.getConnection();
            stmt = con.createStatement();
            StringBuilder insQuery = new StringBuilder();

            insQuery.append("UPDATE symvasiouxo_didaktiko ")
                    .append("SET ")
                    .append("salary = '" + symvasiouxo_didaktiko.getSalary() + "', " +
                            "family_allowance = '" + symvasiouxo_didaktiko.getFamilyAllowance() + "', " +
                            "exp_date = '" + symvasiouxo_didaktiko.getExpDate() + "', " +
                            "lib_allowance = '" + symvasiouxo_didaktiko.getLibAllowance())
                    .append("' WHERE temp_id = " + symvasiouxo_didaktiko.getTempId() + ";");

            PreparedStatement stmtIns = con.prepareStatement(insQuery.toString());
            stmtIns.executeUpdate();
            System.out.println("#Update executed successfully");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DB_Connection.closeDBConnection(stmt, con);
        }
    }
    public static void delete(int temp_id) throws SQLException {
        Statement stmt = null;
        Connection con = null;
        try {
            con = DB_Connection.getConnection();
            stmt = con.createStatement();
            StringBuilder insQuery = new StringBuilder();

            insQuery.append("DELETE FROM symvasiouxo_didaktiko WHERE temp_id = " + temp_id + ";" );
            PreparedStatement stmtIns = con.prepareStatement(insQuery.toString());
            stmtIns.execute();
            System.out.println("#Delete executed successfully");


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DB_Connection.closeDBConnection(stmt, con);
        }
    }

    public static ArrayList<Symvasiouxo_didaktiko> getAllEntrys(){
        try {
            Connection con = DB_Connection.getConnection();
            Statement stmt = con.createStatement();
            ArrayList<Symvasiouxo_didaktiko> sd = new ArrayList<Symvasiouxo_didaktiko>();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT * FROM symvasiouxo_didaktiko");
            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Symvasiouxo_didaktiko symvasiouxo_didaktiko = gson.fromJson(json, Symvasiouxo_didaktiko.class);
                sd.add(symvasiouxo_didaktiko);
            }
            return sd;

        } catch (Exception e) {
            System.err.println("Exception in getAllEntrys()");
        }
        return null;
    }
}