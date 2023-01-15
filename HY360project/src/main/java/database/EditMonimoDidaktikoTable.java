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

    public static void createNewMonimoDidaktiko(Monimo_didaktiko monimo_didaktiko) {
        try {
            Connection con = DB_Connection.getConnection();
            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " monimo_didaktiko (perm_id,years,salary,family_allowance,research_allowance)"
                    + " VALUES ("
                    + "'" + monimo_didaktiko.getPermId() + "',"
                    + "'" + monimo_didaktiko.getYears() + "',"
                    + "'" + monimo_didaktiko.getSalary() + "',"
                    + "'" + monimo_didaktiko.getFamily_allowance() + "',"
                    + "'" + monimo_didaktiko.getResearch_allowance() + "'"
                    + ")";

            stmt.executeUpdate(insertQuery);
            System.out.println("#monimo_didaktiko was successfully added in the database.");
            stmt.close();
        }
        catch (SQLException e) {System.err.println("SQl exception in createNewMonimoDidaktiko");}
        catch (ClassNotFoundException e) {System.err.println("ClassNotFoundException in createNewMonimoDidaktiko");}
    }

    public static String databaseMonimoDidaktikoToJSON(int perm_id) {

        try {
            ResultSet rs;
            Connection con = DB_Connection.getConnection();
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM monimo_didaktiko WHERE perm_id = '" + perm_id + "'");
            rs.next();
            String json=DB_Connection.getResultsToJSON(rs);
            return json;
        }
        catch (SQLException e) {System.err.println("SQLException at databaseMonimoDidaktikoToJSON");}
        catch (ClassNotFoundException e) {System.err.println("ClassNotFoundException at databseMonimoDidaktikoToJSON");}

        return null;
    }

    public static Monimo_didaktiko monimoDidaktikoFromDatabase(int perm_id){
        return jsonToMonimoDidaktiko(databaseMonimoDidaktikoToJSON(perm_id));
    }
}
