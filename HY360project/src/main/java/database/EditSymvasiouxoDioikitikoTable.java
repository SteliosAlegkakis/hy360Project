package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class EditSymvasiouxoDioikitikoTable {
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
