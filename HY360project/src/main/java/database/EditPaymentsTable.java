package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class EditPaymentsTable {
    public static void createTable() throws SQLException, ClassNotFoundException {

        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        String query = "CREATE TABLE payment "
                + "(payment_id INTEGER not NULL AUTO_INCREMENT, "
                + "    emp_id INTEGER not null,"
                + "    employee_category varchar(30) not null,"
                + "    amount double not null,"
                + "    date DATE not null,"
                + " PRIMARY KEY ( payment_id))";
        stmt.execute(query);
        stmt.close();
    }
}
