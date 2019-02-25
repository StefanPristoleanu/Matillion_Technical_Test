package technical.matillion;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author stefan
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SQLConnectTest {
    
    @Autowired
    private SQLConnect mySQLConnection;
    
    public SQLConnectTest() {
    }
    
    /**
     * Test of connectDatabase method, of class SQLConnect.
     */
    @Test
    public void testConnectDatabase() {
        System.out.println("connectDatabase");
        mySQLConnection.connectDatabase();
        assertTrue(mySQLConnection.getMyConn() != null);
    }

    /**
     * Test of selectQuery method, of class SQLConnect.
     */
    @Test
    public void testSelectQuery() {
        System.out.println("selectQuery");
        mySQLConnection.selectQuery();
        assertTrue(!mySQLConnection.getQueryEmployeeList().isEmpty());
    }
    
}
