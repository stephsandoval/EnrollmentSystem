package Screens;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.CallableStatement;
import java.sql.SQLException;
import com.microsoft.sqlserver.jdbc.SQLServerDataTable;

public class ArrayToSqlServer {

    public static void main(String[] args) {
        String connectionUrl = "jdbc:sqlserver://FibyLaptop;"
                        + "database=Enrollment;"
                        + "user=admin-user;"
                        + "password=admin;"
                        + "encrypt=false;"
                        + "trustServerCertificate=true;"
                        + "loginTimeout=30;";
        
        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            // Create a SQLServerDataTable to represent the TVP
            SQLServerDataTable courseArray = new SQLServerDataTable();
            courseArray.addColumnMetadata("CourseID", java.sql.Types.VARCHAR);
            courseArray.addColumnMetadata("GroupNumber", java.sql.Types.INTEGER);

            // Add rows to the SQLServerDataTable
            Object[][] array = {
                {"CS2101", 2}
            };
            for (Object[] row : array) {
                courseArray.addRow(row);
            }

            // Prepare the call to the stored procedure
            String sql = "{call dbo.updateSelectedCourse(?, ?, ?)}";
            try (CallableStatement stmt = connection.prepareCall(sql)) {
                // Set the input parameters
                stmt.setInt(1, 2023395946); // Example StudentID
                stmt.setObject(2, courseArray);// Example Selected value
                stmt.registerOutParameter(3, java.sql.Types.INTEGER);

                // Execute the stored procedure
                stmt.execute();

                // Get the output parameter
                int resultCode = stmt.getInt(3);
                System.out.println("Result Code: " + resultCode);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
