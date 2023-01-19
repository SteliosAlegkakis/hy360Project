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
                    + " payment (emp_id,employee_category,amount,family_allowance,research_allowance,lib_allowance,date)"
                    + " VALUES ("
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
                    singleinfo = new Payments( rs.getInt("emp_id"), rs.getString("employee_category"), rs.getDouble("amount"), rs.getDouble("family_allowance"), 0, 0, rs.getString("date"));
                    singleinfo.setPayment_id(rs.getInt("payment_id"));
                    info.add(singleinfo);
                }
            }
            else if(type.equals("monimo_didaktiko")) {
                insQuery.append("SELECT payment.payment_id, ypallilos.emp_ID, payment.employee_category, payment.amount, monimo_didaktiko.family_allowance, monimo_didaktiko.research_allowance, payment.date FROM monimo_didaktiko, ypallilos, payment WHERE ypallilos.emp_ID = payment.emp_id AND ypallilos.emp_ID = monimo_didaktiko.perm_id;");
                stmtIns = con.prepareStatement(insQuery.toString());
                stmtIns.executeQuery();
                rs = stmtIns.getResultSet();
                while (rs.next()) {
                    singleinfo = new Payments( rs.getInt("emp_id"), rs.getString("employee_category"), rs.getDouble("amount"), rs.getDouble("family_allowance"), rs.getDouble("research_allowance"), 0, rs.getString("date"));
                    singleinfo.setPayment_id(rs.getInt("payment_id"));
                    info.add(singleinfo);
                }
            }
            else if(type.equals("symvasiouxo_didaktiko")) {
                insQuery.append("SELECT payment.payment_id, ypallilos.emp_ID, payment.employee_category, payment.amount, symvasiouxo_didaktiko.family_allowance, symvasiouxo_didaktiko.lib_allowance, payment.date FROM symvasiouxo_didaktiko, ypallilos, payment WHERE ypallilos.emp_ID = payment.emp_id AND ypallilos.emp_ID = symvasiouxo_didaktiko.temp_id;");
                stmtIns = con.prepareStatement(insQuery.toString());
                stmtIns.executeQuery();
                rs = stmtIns.getResultSet();
                while (rs.next()) {
                    singleinfo = new Payments( rs.getInt("emp_id"), rs.getString("employee_category"), rs.getDouble("amount"), rs.getDouble("family_allowance"), 0, rs.getDouble("lib_allowance"), rs.getString("date"));
                    singleinfo.setPayment_id(rs.getInt("payment_id"));
                    info.add(singleinfo);
                }

            }
            else if(type.equals("symvasiouxo_dioikitiko")) {
                insQuery.append("SELECT payment.payment_id, ypallilos.emp_ID, payment.employee_category, payment.amount, symvasiouxo_dioikitiko.family_allowance, payment.date FROM symvasiouxo_dioikitiko, ypallilos, payment WHERE ypallilos.emp_ID = payment.emp_id AND ypallilos.emp_ID = symvasiouxo_dioikitiko.temp_id;");
                stmtIns = con.prepareStatement(insQuery.toString());
                stmtIns.executeQuery();
                rs = stmtIns.getResultSet();
                while (rs.next()) {
                    singleinfo = new Payments( rs.getInt("emp_id"), rs.getString("employee_category"), rs.getDouble("amount"), rs.getDouble("family_allowance"), 0, 0, rs.getString("date"));
                    singleinfo.setPayment_id(rs.getInt("payment_id"));
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

    public static double MMASalaryForTypeOfEmployee(String type_of_emp, String type_of_sal) throws SQLException {
        Statement stmt = null;
        Connection con = null;
        double ret = 0;
        try {
            con = DB_Connection.getConnection();
            stmt = con.createStatement();
            StringBuilder insQuery = new StringBuilder();
            if(type_of_emp.equals("symvasiouxo_dioikitiko")) {
                if(type_of_sal.equals("max")) {
                    insQuery.append("SELECT MAX(payment.amount) AS max_sal FROM payment INNER JOIN symvasiouxo_dioikitiko" +
                            " ON payment.emp_id = symvasiouxo_dioikitiko.temp_id;");
                    PreparedStatement stmtIns = con.prepareStatement(insQuery.toString());
                    stmtIns.executeQuery();
                    ResultSet rs = stmtIns.getResultSet();
                    if(rs.next()) {
                        ret = rs.getDouble("max_sal");
                        System.out.println("max sal = "+ ret);
                        return ret;
                    }
                }
                else if(type_of_sal.equals("min")) {
                    insQuery.append("SELECT MIN(payment.amount) AS min_sal FROM payment INNER JOIN symvasiouxo_dioikitiko" +
                            " ON payment.emp_id = symvasiouxo_dioikitiko.temp_id;");
                    PreparedStatement stmtIns = con.prepareStatement(insQuery.toString());
                    stmtIns.executeQuery();
                    ResultSet rs = stmtIns.getResultSet();
                    if (rs.next()) {
                        ret = rs.getDouble("min_sal");
                        System.out.println("min sal = " + ret);
                        return ret;
                    }
                }
                else {
                    insQuery.append("SELECT AVG(payment.amount) AS avg_sal FROM payment INNER JOIN symvasiouxo_dioikitiko" +
                            " ON payment.emp_id = symvasiouxo_dioikitiko.temp_id;");
                    PreparedStatement stmtIns = con.prepareStatement(insQuery.toString());
                    stmtIns.executeQuery();
                    ResultSet rs = stmtIns.getResultSet();
                    if(rs.next()) {
                        ret = rs.getDouble("avg_sal");
                        System.out.println("avg sal = "+ ret);
                        return ret;
                    }
                }

            }
            if(type_of_emp.equals("symvasiouxo_didaktiko")) {
                if(type_of_sal.equals("max")) {
                    insQuery.append("SELECT MAX(payment.amount) AS max_sal FROM payment INNER JOIN symvasiouxo_didaktiko" +
                            " ON payment.emp_id = symvasiouxo_didaktiko.temp_id;");
                    PreparedStatement stmtIns = con.prepareStatement(insQuery.toString());
                    stmtIns.executeQuery();
                    ResultSet rs = stmtIns.getResultSet();
                    if(rs.next()) {
                        ret = rs.getDouble("max_sal");
                        System.out.println("max sal = "+ ret);
                        return ret;
                    }
                }
                else if(type_of_sal.equals("min")) {
                    insQuery.append("SELECT MIN(payment.amount) AS min_sal FROM payment INNER JOIN symvasiouxo_didaktiko" +
                            " ON payment.emp_id = symvasiouxo_didaktiko.temp_id;");
                    PreparedStatement stmtIns = con.prepareStatement(insQuery.toString());
                    stmtIns.executeQuery();
                    ResultSet rs = stmtIns.getResultSet();
                    if (rs.next()) {
                        ret = rs.getDouble("min_sal");
                        System.out.println("min sal = " + ret);
                        return ret;
                    }
                }
                else {
                    insQuery.append("SELECT AVG(payment.amount) AS avg_sal FROM payment INNER JOIN symvasiouxo_didaktiko" +
                            " ON payment.emp_id = symvasiouxo_didaktiko.temp_id;");
                    PreparedStatement stmtIns = con.prepareStatement(insQuery.toString());
                    stmtIns.executeQuery();
                    ResultSet rs = stmtIns.getResultSet();
                    if(rs.next()) {
                        ret = rs.getDouble("avg_sal");
                        System.out.println("avg sal = "+ ret);
                        return ret;
                    }
                }

            }
            if(type_of_emp.equals("monimo_dioikitiko")) {
                if(type_of_sal.equals("max")) {
                    insQuery.append("SELECT MAX(payment.amount) AS max_sal FROM payment INNER JOIN monimo_dioikitiko" +
                            " ON payment.emp_id = monimo_dioikitiko.perm_id;");
                    PreparedStatement stmtIns = con.prepareStatement(insQuery.toString());
                    stmtIns.executeQuery();
                    ResultSet rs = stmtIns.getResultSet();
                    if(rs.next()) {
                        ret = rs.getDouble("max_sal");
                        System.out.println("max sal = "+ ret);
                        return ret;
                    }
                }
                else if(type_of_sal.equals("min")) {
                    insQuery.append("SELECT MIN(payment.amount) AS min_sal FROM payment INNER JOIN monimo_dioikitiko" +
                            " ON payment.emp_id = monimo_dioikitiko.perm_id;");
                    PreparedStatement stmtIns = con.prepareStatement(insQuery.toString());
                    stmtIns.executeQuery();
                    ResultSet rs = stmtIns.getResultSet();
                    if (rs.next()) {
                        ret = rs.getDouble("min_sal");
                        System.out.println("min sal = " + ret);
                        return ret;
                    }
                }
                else {
                    insQuery.append("SELECT AVG(payment.amount) AS avg_sal FROM payment INNER JOIN monimo_dioikitiko" +
                            " ON payment.emp_id = monimo_dioikitiko.perm_id;");
                    PreparedStatement stmtIns = con.prepareStatement(insQuery.toString());
                    stmtIns.executeQuery();
                    ResultSet rs = stmtIns.getResultSet();
                    if(rs.next()) {
                        ret = rs.getDouble("avg_sal");
                        System.out.println("avg sal = "+ ret);
                        return ret;
                    }
                }
            }
            if(type_of_emp.equals("monimo_didaktiko")) {
                if (type_of_sal.equals("max")) {
                    insQuery.append("SELECT MAX(payment.amount) AS max_sal FROM payment INNER JOIN monimo_didaktiko" +
                            " ON payment.emp_id = monimo_didaktiko.perm_id;");
                    PreparedStatement stmtIns = con.prepareStatement(insQuery.toString());
                    stmtIns.executeQuery();
                    ResultSet rs = stmtIns.getResultSet();
                    if (rs.next()) {
                        ret = rs.getDouble("max_sal");
                        System.out.println("max sal = " + ret);
                        return ret;
                    }
                } else if (type_of_sal.equals("min")) {
                    insQuery.append("SELECT MIN(payment.amount) AS min_sal FROM payment INNER JOIN monimo_didaktiko" +
                            " ON payment.emp_id = monimo_didaktiko.perm_id;");
                    PreparedStatement stmtIns = con.prepareStatement(insQuery.toString());
                    stmtIns.executeQuery();
                    ResultSet rs = stmtIns.getResultSet();
                    if (rs.next()) {
                        ret = rs.getDouble("min_sal");
                        System.out.println("min sal = " + ret);
                        return ret;
                    }
                } else {
                    insQuery.append("SELECT AVG(payment.amount) AS avg_sal FROM payment INNER JOIN monimo_didaktiko" +
                            " ON payment.emp_id = monimo_didaktiko.perm_id;");
                    PreparedStatement stmtIns = con.prepareStatement(insQuery.toString());
                    stmtIns.executeQuery();
                    ResultSet rs = stmtIns.getResultSet();
                    if (rs.next()) {
                        ret = rs.getDouble("avg_sal");
                        System.out.println("avg sal = " + ret);
                        return ret;
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DB_Connection.closeDBConnection(stmt, con);
        }
        return ret;
    }
    public static double calculateSumOfSal(String type_of_emp) throws SQLException {
        Statement stmt = null;
        Connection con = null;
        try {
            con = DB_Connection.getConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        double arr = 0;
        StringBuilder insQuery = new StringBuilder();
        PreparedStatement stmtIns;
        ResultSet rs;

        try {
            con = DB_Connection.getConnection();
            stmt = con.createStatement();

            if(type_of_emp.equals("monimo_dioikitiko")) {
                insQuery.append("SELECT SUM(amount + payment.family_allowance) AS amount FROM payment JOIN monimo_dioikitiko ON monimo_dioikitiko.perm_id = payment.emp_id;");
            }
            else if(type_of_emp.equals("monimo_didaktiko")) {
                insQuery.append("SELECT SUM(amount + payment.family_allowance + payment.research_allowance) as amount FROM payment JOIN monimo_didaktiko ON payment.emp_id = monimo_didaktiko.perm_id;");
            }
            else if(type_of_emp.equals("symvasiouxo_dioikitiko")) {
                insQuery.append("SELECT SUM(amount + payment.family_allowance) as amount FROM payment JOIN symvasiouxo_dioikitiko ON payment.emp_id = symvasiouxo_dioikitiko.temp_id;");
            }
            else {
                insQuery.append("SELECT SUM(amount + payment.family_allowance + payment.lib_allowance) as amount FROM payment JOIN symvasiouxo_didaktiko ON payment.emp_id = symvasiouxo_didaktiko.temp_id;");
            }
            stmtIns = con.prepareStatement(insQuery.toString());
            stmtIns.executeQuery();
            rs = stmtIns.getResultSet();
            while(rs.next()){
                arr += rs.getDouble("amount");
            }
            System.out.println(arr);
            return arr;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
