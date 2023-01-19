package database;

import com.google.gson.Gson;
import mainClasses.Symvasiouxo_didaktiko;
import mainClasses.Symvasiouxo_dioikitiko;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;

public class EditSymvasiouxoDioikitikoTable {
    public static Symvasiouxo_dioikitiko jsonToObject(String json) {
        Gson gson = new Gson();
        Symvasiouxo_dioikitiko symvasiouxo_dioikitiko = gson.fromJson(json, Symvasiouxo_dioikitiko.class);
        return symvasiouxo_dioikitiko;
    }

    public static void createNewDatabaseEntry(Symvasiouxo_dioikitiko sd) throws SQLException {
        try {
            Connection con = DB_Connection.getConnection();
            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " symvasiouxo_dioikitiko (temp_id,salary,family_allowance,exp_date)"
                    + " VALUES ("
                    + "'" + sd.getTempId() + "',"
                    + "'" + sd.getSalary() + "',"
                    + "'" + sd.getFamilyAllowance() + "',"
                    + "'" + sd.getExpDate() + "'"//the date has to be in yyyy-mm-dd format
                    + ")";

            stmt.executeUpdate(insertQuery);
            System.out.println("#symvasiouxo_dioikitiko was successfully added in the database.");
            stmt.close();

        }
        catch (ClassNotFoundException e) {System.err.println(("ClassNotFoundException in createNewYpallilos"));}
    }
    public static void createTable() throws SQLException, ClassNotFoundException {

        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        String query = "CREATE TABLE symvasiouxo_dioikitiko "
                + "(temp_id INTEGER not NULL , "
                + "    salary double not null,"
                + "    family_allowance double not null,"
                + "    exp_date DATE not null,"
                + " PRIMARY KEY ( temp_id))";
        stmt.execute(query);
        stmt.close();
    }

    public static String databaseToJSON(int temp_id) throws SQLException {

        ResultSet rs;
        try {
            Connection con = DB_Connection.getConnection();
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM symvasiouxo_dioikitiko WHERE temp_id = '" + temp_id + "'");
            rs.next();
            String json=DB_Connection.getResultsToJSON(rs);
            return json;
        }
        catch (ClassNotFoundException e) {System.err.println("ClassNotFoundException in databaseYpallilosToJSON");}
        return null;
    }

    public static void update(Symvasiouxo_dioikitiko symvasiouxo_dioikitiko) throws SQLException {
        Statement stmt = null;
        Connection con = null;
        try {
            con = DB_Connection.getConnection();
            stmt = con.createStatement();
            StringBuilder insQuery = new StringBuilder();

            insQuery.append("UPDATE symvasiouxo_dioikitiko ")
                    .append("SET ")
                    .append("salary = '" + symvasiouxo_dioikitiko.getSalary() + "', " +
                            "family_allowance = '" + symvasiouxo_dioikitiko.getFamilyAllowance() + "', " +
                            "exp_date = '" + symvasiouxo_dioikitiko.getExpDate())
                    .append("' WHERE temp_id = " + symvasiouxo_dioikitiko.getTempId() + ";");

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

            insQuery.append("DELETE FROM symvasiouxo_dioikitiko WHERE temp_id = " + temp_id + ";" );
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

