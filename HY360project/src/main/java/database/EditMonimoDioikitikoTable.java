package database;

import com.google.gson.Gson;
import mainClasses.Monimo_dioikitiko;
import database.DB_Connection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
}
