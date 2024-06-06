package Database;

import java.sql.CallableStatement;
import java.sql.Connection;

public class Repository {
    
    protected Connection connection;
    protected String connectionURL;
    protected CallableStatement callableStatement;
        
    /* ------------------------------------------------------------ */

    protected Repository() {
        connectionURL = "jdbc:sqlserver://FibyLaptop;"
                        + "database=Enrollment;"
                        + "user=admin-user;"
                        + "password=admin;"
                        + "encrypt=false;"
                        + "trustServerCertificate=true;"
                        + "loginTimeout=30;";
    }
        
    /* ------------------------------------------------------------ */
        
    protected void closeResources() {
        try {
            if (callableStatement != null) {
                callableStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {}
    }
}