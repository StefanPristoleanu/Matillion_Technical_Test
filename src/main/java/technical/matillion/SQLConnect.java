package technical.matillion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author stefan
 */
@Component
public class SQLConnect {

    private static final Logger logger = Logger.getLogger(SQLConnect.class.getName());

    @Value("${hostname}")
    private String hostname;

    @Value("${username}")
    private String username;

    @Value("${password}")
    private String password;

    @Value("${database}")
    private String database;

    @Value("${pay_type}")
    private String pay_type;

    @Value("${education_level}")
    private String education_level;

    @Value("${department}")
    private int department;

    private Connection myConn;
    private List<String> queryEmployeeList;

    public Connection getMyConn() {
        return myConn;
    }

    public List<String> getQueryEmployeeList() {
        return queryEmployeeList;
    }

    @PostConstruct
    private void init() {
        logger.info("init() of SQLConnect using hostname: " + hostname + " and db: " + database);
        logger.info("query param: pay_type " + pay_type + " education_level: " + education_level + " department " + department);
    }

    public void connectDatabase() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            logger.info("MySQL JDBC Driver Registered!");
        } catch (ClassNotFoundException e) {
            logger.info("Couldn't found JDBC driver. Make sure you have added JDBC Maven Dependency Correctly");
            e.printStackTrace();
            return;
        }
        try {
            logger.info("Starting connection with " + hostname);
            myConn = DriverManager.getConnection("jdbc:mysql://" + hostname + ":3306/" + database,
                    username, password);
            if (myConn != null) {
                logger.info("Connection Successful!");
            } else {
                logger.info("Failed to make connection!");
            }
        } catch (SQLException e) {
            logger.info("MySQL Connection Failed!");
            e.printStackTrace();
            return;
        }
    }

    public void selectQuery() {
        if(myConn == null){
            connectDatabase();
        }
        try {
            String sql = "SELECT employee.employee_id, employee.full_name, employee.position_title, department.department_id, \n"
                    + "department.department_description, `position`.position_id, `position`.management_role\n"
                    + "FROM employee, department, `position` \n"
                    + "WHERE employee.department_id = department.department_id\n"
                    + "AND employee.position_id = `position`.position_id\n"
                    + "AND employee.department_id = ? \n"
                    + "AND `position`.pay_type = ?\n"
                    + "AND employee.education_level = ?;";
            PreparedStatement ps = myConn.prepareStatement(sql);
            ps.setInt(1, department);
            ps.setString(2, pay_type);
            ps.setString(3, education_level);
            ResultSet rs = ps.executeQuery();
            logger.info("Querry results are: ");
            logger.info("|employee_id|full_name    |position_title           |department_id|department_description   |position_id|management_role  |");
            
            queryEmployeeList = new ArrayList<>();
            while (rs.next()) {
                int employee_id = rs.getInt("employee_id");
                String full_name = rs.getString("full_name");
                String position_title = rs.getString("position_title");
                String department_description = rs.getString("department_description");
                int position_id = rs.getInt("position_id");
                String management_role = rs.getString("management_role");         
                queryEmployeeList.add(full_name);
                logger.info(employee_id + " " + full_name + " " + position_title + " " + department + " " + department_description +
                        " " + position_id + " " + management_role);
            }
            logger.info("Total query results: " + getQueryEmployeeList().size());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
