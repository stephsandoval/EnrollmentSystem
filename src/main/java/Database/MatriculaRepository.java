package Database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import com.microsoft.sqlserver.jdbc.SQLServerDataTable;

import Enrollment.Course;
import Enrollment.Group;

public class MatriculaRepository extends Repository {
    
    private static MatriculaRepository instance;
    private Result enrollmentCourses;
    private int studentID;
    
    private MatriculaRepository (int studentID) {
        super();
        this.studentID = studentID;
        enrollmentCourses = loadEnrollmentCourses();
    }

    public static synchronized MatriculaRepository getInstance(int studentID) {
        if (instance == null) {
            instance = new MatriculaRepository(studentID);
        }
        return instance;
    }

    public Result getEnrollmentCourses() {
        return this.enrollmentCourses;
    }

    private Result loadEnrollmentCourses() {
        ResultSet resultSet;
        Result result = new Result(); 

        try {
            connection = DriverManager.getConnection(connectionURL);
            String storedProcedureQuery = "{CALL dbo.getEnrollmentCourses(?, ?)}";
            callableStatement = connection.prepareCall(storedProcedureQuery);

            callableStatement.setInt(1, studentID);

            callableStatement.registerOutParameter(2, Types.INTEGER);
            callableStatement.execute();

            resultSet = callableStatement.getResultSet();
            resultSet.next();
            result.addCode(resultSet.getInt(1));

            if (callableStatement.getMoreResults()) {
                resultSet = callableStatement.getResultSet();
                while (resultSet.next()) {
                    String courseID = resultSet.getString(1);
                    String courseName = resultSet.getString(2);
                    ArrayList<Object> groups = getGroupOptions(studentID, courseID).getDataset();
                    result.addDatasetItem(new Course(courseID, courseName, groups));
                }
            }
        } catch (Exception exception) {} finally {
            closeResources();
        }
        return result;
    }

    private Result getGroupOptions (int studentID, String courseID) {
        ResultSet resultSet;
        Result result = new Result(); 

        try {
            connection = DriverManager.getConnection(connectionURL);
            String storedProcedureQuery = "{CALL dbo.getEnrollmentGroupOptions(?, ?, ?)}";
            callableStatement = connection.prepareCall(storedProcedureQuery);

            callableStatement.setInt(1, studentID);
            callableStatement.setString(2, courseID);

            callableStatement.registerOutParameter(3, Types.INTEGER);
            callableStatement.execute();

            resultSet = callableStatement.getResultSet();
            resultSet.next();
            result.addCode(resultSet.getInt(1));

            if (callableStatement.getMoreResults()) {
                resultSet = callableStatement.getResultSet();
                while (resultSet.next()) {
                    String campus = resultSet.getString(2);
                    int groupNumber = resultSet.getInt(3);
                    String schedule = resultSet.getString(4);
                    String teacher = resultSet.getString(5);
                    int capacity = resultSet.getInt(6);
                    String modality = resultSet.getString(7);
                    boolean selected = resultSet.getBoolean(8);
                    result.addDatasetItem(new Group(campus, groupNumber, schedule, teacher, capacity, modality, selected));
                }
            }
        } catch (Exception exception) {} finally {
            closeResources();
        }
        return result;
    }

    public void updateCourseSelection (ArrayList<Object[]> coursesSelected) {

        try (Connection connection = DriverManager.getConnection(connectionURL)) {
            SQLServerDataTable courseArray = new SQLServerDataTable();
            courseArray.addColumnMetadata("CourseID", java.sql.Types.VARCHAR);
            courseArray.addColumnMetadata("GroupNumber", java.sql.Types.INTEGER);
            courseArray.addColumnMetadata("Selected", java.sql.Types.BIT);

            for (Object[] row : coursesSelected) {
                courseArray.addRow(row);
            }

            String sql = "{call dbo.updateSelectedEnrollmentCourse(?, ?, ?)}";
            try (CallableStatement stmt = connection.prepareCall(sql)) {
                stmt.setInt(1, studentID);
                stmt.setObject(2, courseArray);
                stmt.registerOutParameter(3, java.sql.Types.INTEGER);

                stmt.execute();

                int resultCode = stmt.getInt(3);
                System.out.println("Result Code: " + resultCode);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}