package database;

import com.google.gson.Gson;
import mainClasses.Symvasiouxo_didaktiko;
import mainClasses.Ypallilos;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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
            System.out.println("#Ypallilos was successfully added in the database.");
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
}
