package database;

import com.google.gson.Gson;
import mainClasses.Symvasiouxo_didaktiko;
import mainClasses.Symvasiouxo_dioikitiko;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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
}
