package database;

import com.google.gson.Gson;
import mainClasses.Ypallilos;
import database.DB_Connection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;

public class EditYpallilosTable {

    public static void addYpallilosFromJSON(String json) {
        Ypallilos ypallilos = jsonToYpallilos(json);
        createNewYpallilos(ypallilos);
    }

    public static Ypallilos jsonToYpallilos(String json) {
        Gson gson = new Gson();
        Ypallilos ypallilos = gson.fromJson(json, Ypallilos.class);
        return ypallilos;
    }

    public static Ypallilos ypallilosFromJs(HttpServletRequest request) throws Exception {
        Ypallilos ypallilos = new Ypallilos();
        ypallilos.setName(request.getParameter("name"));
        ypallilos.setAddress(request.getParameter("address"));
        ypallilos.setPhone(request.getParameter("phone"));
        ypallilos.setStartDate(new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("start_date")));
        ypallilos.setIBAN(request.getParameter("IBAN"));
        ypallilos.setBank(request.getParameter("bank"));
        ypallilos.setCategory(request.getParameter("category"));
        ypallilos.setDept(request.getParameter("dept"));
        ypallilos.setMaritalStatus(request.getParameter("marital_status"));
        ypallilos.setChildrenNum(Integer.parseInt(request.getParameter("children_num")));
        ypallilos.setChildrenAges(request.getParameter("children_ages"));
        return ypallilos;
    }

    public static void createNewYpallilos(Ypallilos ypallilos) {
        try {
            Connection con = DB_Connection.getConnection();
            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " ypallilos (name,address,phone,start_date,IBAN,bank,category,dept,marital_status,children_num,children_ages)"
                    + " VALUES ("
                    + "'" + ypallilos.getName() + "',"
                    + "'" + ypallilos.getAddress() + "',"
                    + "'" + ypallilos.getPhone() + "',"
                    + "'" + new java.sql.Date((ypallilos.getStartDate().getTime())) + "',"
                    + "'" + ypallilos.getIBAN() + "',"
                    + "'" + ypallilos.getBank() + "',"
                    + "'" + ypallilos.getCategory() + "',"
                    + "'" + ypallilos.getDept() + "',"
                    + "'" + ypallilos.getMaritalStatus() + "',"
                    + "'" + ypallilos.getChildrenNum() + "',"
                    + "'" + ypallilos.getChildrenAges() + "'"
                    + ")";
            //stmt.execute(table);

            stmt.executeUpdate(insertQuery);
            System.out.println("# The borrowing was successfully added in the database.");

//            /* Get the member id from the database and set it to the member */
//            stmt.close();

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
//        catch (SQLException ex) {System.err.println("SQl exception in createNewYpallilos");}
//        catch (ClassNotFoundException e) {System.err.println(("ClassNotFoundException in createNewYpallilos"));}
//    }

    public static String databaseYpallilosToJSON(String name) {

        ResultSet rs;
        try {
            Connection con = DB_Connection.getConnection();
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM ypallilos WHERE name = '" + name + "'");
            rs.next();
            String json=DB_Connection.getResultsToJSON(rs);
            return json;
        }
        catch (SQLException e) {System.err.println("SQLException in databaseYpallilosToJSON ");}
        catch (ClassNotFoundException e) {System.err.println("ClassNotFoundException in databaseYpallilosToJSON");}
        return null;
    }
}
