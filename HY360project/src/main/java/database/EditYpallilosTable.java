package database;

import com.google.gson.Gson;
import mainClasses.Ypallilos;
import database.DB_Connection;
import java.sql.*;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

public class EditYpallilosTable {

    public static void addYpallilosFromJSON(String json) throws SQLException {
        Ypallilos ypallilos = jsonToObject(json);
        createNewDatabaseEntry(ypallilos);
    }

    public static Ypallilos jsonToObject(String json) {
        Gson gson = new Gson();
        Ypallilos ypallilos = gson.fromJson(json, Ypallilos.class);
        return ypallilos;
    }

    public static Ypallilos objectFromJs(HttpServletRequest request) {
        Ypallilos ypallilos = new Ypallilos();
        ypallilos.setName(request.getParameter("name"));
        ypallilos.setAddress(request.getParameter("address"));
        ypallilos.setPhone(request.getParameter("phone"));
        ypallilos.setStartDate(request.getParameter("start_date"));//format -> yyyy-mm-dd
        ypallilos.setIBAN(request.getParameter("IBAN"));
        ypallilos.setBank(request.getParameter("bank"));
        ypallilos.setCategory(request.getParameter("category"));
        ypallilos.setDept(request.getParameter("dept"));
        ypallilos.setMaritalStatus(request.getParameter("marital_status"));
        ypallilos.setChildrenNum(Integer.parseInt(request.getParameter("children_num")));
        ypallilos.setChildrenAges(request.getParameter("children_ages"));
        return ypallilos;
    }

    /* The SQLException should be handled by the servlet to inform the front end that something went wrong
     * */
    public static void createNewDatabaseEntry(Ypallilos ypallilos) throws SQLException {
        try {
            Connection con = DB_Connection.getConnection();
            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " ypallilos (name,address,phone,start_date,IBAN,bank,category,dept,marital_status,children_num,children_ages)"
                    + " VALUES ("
                    + "'" + ypallilos.getName() + "',"
                    + "'" + ypallilos.getAddress() + "',"
                    + "'" + ypallilos.getPhone() + "',"
                    + "'" + ypallilos.getStartDate() + "',"//the date has to be in yyyy-mm-dd format
                    + "'" + ypallilos.getIBAN() + "',"
                    + "'" + ypallilos.getBank() + "',"
                    + "'" + ypallilos.getCategory() + "',"
                    + "'" + ypallilos.getDept() + "',"
                    + "'" + ypallilos.getMaritalStatus() + "',"
                    + "'" + ypallilos.getChildrenNum() + "',"
                    + "'" + ypallilos.getChildrenAges() + "'"
                    + ")";

            stmt.executeUpdate(insertQuery);
            System.out.println("#Ypallilos was successfully added in the database.");
            stmt.close();

        }
        catch (ClassNotFoundException e) {System.err.println(("ClassNotFoundException in createNewYpallilos"));}
    }

    /* The SQLException should be handled by the servlet to inform the front end that something went wrong
     * */
    public static void update( Ypallilos ypallilos) throws SQLException {
        Statement stmt = null;
        Connection con = null;
        try {
            con = DB_Connection.getConnection();
            stmt = con.createStatement();
            StringBuilder insQuery = new StringBuilder();

            insQuery.append("UPDATE ypallilos ")
                    .append("SET ")
                    .append("name = '" + ypallilos.getName() + "', " +
                            "address = '" + ypallilos.getAddress() + "', " +
                            "phone = '" + ypallilos.getPhone() + "', " +
                            "start_date = '" + ypallilos.getStartDate() + "', " +//the date has to be in yyyy-mm-dd format
                            "IBAN = '" + ypallilos.getIBAN() + "', " +
                            "bank = '" + ypallilos.getBank() + "', " +
                            "dept = '" + ypallilos.getDept() + "', " +
                            "marital_status = '" + ypallilos.getMaritalStatus() + "', " +
                            "children_num = '" + ypallilos.getChildrenNum() + "', " +
                            "children_ages = '" + ypallilos.getChildrenAges())
                    .append("' WHERE emp_id = " + ypallilos.getEmpID() + ";");

            PreparedStatement stmtIns = con.prepareStatement(insQuery.toString());
            stmtIns.executeUpdate();
            System.out.println("#Update executed successfully");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DB_Connection.closeDBConnection(stmt, con);
        }
    }

    public static void delete(int emp_id) throws SQLException {
        Statement stmt = null;
        Connection con = null;
        try {
            con = DB_Connection.getConnection();
            stmt = con.createStatement();
            StringBuilder insQuery = new StringBuilder();

            insQuery.append("DELETE FROM ypallilos WHERE emp_id = " + emp_id + ";" );
            PreparedStatement stmtIns = con.prepareStatement(insQuery.toString());
            stmtIns.execute();
            System.out.println("#Delete executed successfully");


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DB_Connection.closeDBConnection(stmt, con);
        }
    }

    public static ArrayList<Ypallilos> getEmployees() throws SQLException {
        Statement stmt = null;
        Connection con = null;
        ArrayList<Ypallilos> emps = new ArrayList<>();
        try {
            con = DB_Connection.getConnection();
            stmt = con.createStatement();
            StringBuilder insQuery = new StringBuilder();

            insQuery.append("SELECT * from ypallilos;");
            stmt.executeQuery(insQuery.toString());

            ResultSet res = stmt.getResultSet();
            while (res.next()) {
                Ypallilos emp = new Ypallilos();
                emp.setName(res.getString("name"));
                emp.setAddress(res.getString("address"));
                emp.setPhone(res.getString("phone"));
                emp.setStartDate(res.getString("start_date"));
                emp.setIBAN(res.getString("IBAN"));
                emp.setBank(res.getString("bank"));
                emp.setCategory(res.getString("category"));
                emp.setDept(res.getString("dept"));
                emp.setMaritalStatus(res.getString("marital_status"));
                emp.setChildrenNum(res.getInt("children_num"));
                emp.setChildrenAges(res.getString("children_ages"));
                emps.add(emp);
            }
            return emps;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DB_Connection.closeDBConnection(stmt,con);
        }
        return null;
    }

    /* The SQLException should be handled by the servlet to inform the front end that something went wrong
     * */
    public static String databaseToJSON(String name) throws SQLException {

        ResultSet rs;
        try {
            Connection con = DB_Connection.getConnection();
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM ypallilos WHERE name = '" + name + "'");
            rs.next();
            String json=DB_Connection.getResultsToJSON(rs);
            return json;
        }
        catch (ClassNotFoundException e) {System.err.println("ClassNotFoundException in databaseYpallilosToJSON");}
        return null;
    }
}
