package database;

import com.google.gson.Gson;
import mainClasses.Monimo_didaktiko;
import database.DB_Connection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EditMonimoDidaktikoTable {

    public static Monimo_didaktiko jsonToMonimoDidaktiko(String json) {
        Gson gson = new Gson();
        Monimo_didaktiko monimo_didaktiko = gson.fromJson(json, Monimo_didaktiko.class);
        return monimo_didaktiko;
    }

    /* The SQLException should be handled by the servlet to inform the front end that something went wrong
     * */
    public static void createNewMonimoDidaktiko(Monimo_didaktiko monimo_didaktiko) throws SQLException {
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
    public static String databaseMonimoDidaktikoToJSON(int perm_id) throws SQLException {

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
    public static Monimo_didaktiko monimoDidaktikoFromDatabase(int perm_id) throws SQLException {
        return jsonToMonimoDidaktiko(databaseMonimoDidaktikoToJSON(perm_id));
    }
}
