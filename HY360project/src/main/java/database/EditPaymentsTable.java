package database;

import javafx.animation.PauseTransition;
import mainClasses.Payments;
import mainClasses.Symvasiouxo_didaktiko;
import mainClasses.Ypallilos;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

public class EditPaymentsTable {
    public static void createTable() throws SQLException, ClassNotFoundException {

        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        String query = "CREATE TABLE payment "
                + "(payment_id INTEGER not NULL AUTO_INCREMENT, "
                + "    emp_id INTEGER not null,"
                + "    employee_category varchar(30) not null,"
                + "    amount double not null,"
                + "    family_allowance double not null,"
                + "    research_allowance double not null,"
                + "    lib_allowance double not null,"
                + "    date DATE not null,"
                + " PRIMARY KEY ( payment_id))";
        stmt.execute(query);
        stmt.close();
    }

    public static void createNewDatabaseEntry(Payments p) throws SQLException {
        try {
            Connection con = DB_Connection.getConnection();
            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " payment (payment_id,emp_id,employee_category,amount,family_allowance,research_allowance,lib_allowance,date)"
                    + " VALUES ("
                    + "'" + p.getBillId() + "',"
                    + "'" + p.getEmpId() + "',"
                    + "'" + p.getEmployeeCategory() + "',"
                    + "'" + p.getAmount() + "',"
                    + "'" + p.getFamily_allowance() + "',"
                    + "'" + p.getResearch_allowance() + "',"
                    + "'" + p.getLib_allowance() + "',"
                    + "'" + p.getDate() + "'"
                    + ")";

            stmt.executeUpdate(insertQuery);
            System.out.println("#Payment was successfully added in the database.");
            stmt.close();

        }
        catch (ClassNotFoundException e) {System.err.println(("ClassNotFoundException in createNewPayment"));}
    }

    public static ArrayList<Payments> getPaymentInfo(String type) throws SQLException {
        ArrayList<Payments> info = new ArrayList<>();
        Payments singleinfo;
        ArrayList<Ypallilos> employees = EditYpallilosTable.getEmployees();
        Ypallilos emp;
        String date_paid = null;

        Statement stmt = null;
        PreparedStatement stmtIns;
        ResultSet rs;
        Connection con = null;
        try {
            con = DB_Connection.getConnection();
            stmt = con.createStatement();
            StringBuilder insQuery = new StringBuilder();

            if(type.equals("monimo_dioikitiko")) {
                insQuery.append("SELECT payment.payment_id, ypallilos.emp_ID, payment.employee_category, payment.amount, monimo_dioikitiko.family_allowance, payment.date FROM monimo_dioikitiko, ypallilos, payment WHERE ypallilos.emp_ID = payment.emp_id AND ypallilos.emp_ID = monimo_dioikitiko.perm_id;");
                stmtIns = con.prepareStatement(insQuery.toString());
                stmtIns.executeQuery();
                rs = stmtIns.getResultSet();
                while (rs.next()) {
                    singleinfo = new Payments(rs.getInt("payment_id"), rs.getInt("emp_id"), rs.getString("employee_category"), rs.getDouble("amount"), rs.getDouble("family_allowance"), 0, 0, rs.getString("date"));
                    info.add(singleinfo);
                }
            }
            else if(type.equals("monimo_didaktiko")) {
                insQuery.append("SELECT payment.payment_id, ypallilos.emp_ID, payment.employee_category, payment.amount, monimo_didaktiko.family_allowance, monimo_didaktiko.research_allowance, payment.date FROM monimo_didaktiko, ypallilos, payment WHERE ypallilos.emp_ID = payment.emp_id AND ypallilos.emp_ID = monimo_didaktiko.perm_id;");
                stmtIns = con.prepareStatement(insQuery.toString());
                stmtIns.executeQuery();
                rs = stmtIns.getResultSet();
                while (rs.next()) {
                    singleinfo = new Payments(rs.getInt("payment_id"), rs.getInt("emp_id"), rs.getString("employee_category"), rs.getDouble("amount"), rs.getDouble("family_allowance"), rs.getDouble("research_allowance"), 0, rs.getString("date"));
                    info.add(singleinfo);
                }
            }
            else if(type.equals("symvasiouxo_didaktiko")) {
                insQuery.append("SELECT payment.payment_id, ypallilos.emp_ID, payment.employee_category, payment.amount, symvasiouxo_didaktiko.family_allowance, symvasiouxo_didaktiko.lib_allowance, payment.date FROM symvasiouxo_didaktiko, ypallilos, payment WHERE ypallilos.emp_ID = payment.emp_id AND ypallilos.emp_ID = symvasiouxo_didaktiko.temp_id;");
                stmtIns = con.prepareStatement(insQuery.toString());
                stmtIns.executeQuery();
                rs = stmtIns.getResultSet();
                while (rs.next()) {
                    singleinfo = new Payments(rs.getInt("payment_id"), rs.getInt("emp_id"), rs.getString("employee_category"), rs.getDouble("amount"), rs.getDouble("family_allowance"), 0, rs.getDouble("lib_allowance"), rs.getString("date"));
                    info.add(singleinfo);
                }
            }
            else if(type.equals("symvasiouxo_dioikitiko")) {
                insQuery.append("SELECT payment.payment_id, ypallilos.emp_ID, payment.employee_category, payment.amount, symvasiouxo_dioikitiko.family_allowance, payment.date FROM symvasiouxo_dioikitiko, ypallilos, payment WHERE ypallilos.emp_ID = payment.emp_id AND ypallilos.emp_ID = symvasiouxo_dioikitiko.temp_id;");
                stmtIns = con.prepareStatement(insQuery.toString());
                stmtIns.executeQuery();
                rs = stmtIns.getResultSet();
                while (rs.next()) {
                    singleinfo = new Payments(rs.getInt("payment_id"), rs.getInt("emp_id"), rs.getString("employee_category"), rs.getDouble("amount"), rs.getDouble("family_allowance"), 0, 0, rs.getString("date"));
                    info.add(singleinfo);
                }
            }

            for(int i = 0;i<info.size();i++){
                System.out.println(info.get(i));
            }
            return info;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return info;
    }
}
