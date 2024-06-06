package Database;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;

import Enrollment.Course;
import Enrollment.Group;

public class MatriculaRepository extends Repository {
    
    private static MatriculaRepository instance;
    private Result enrollmentCourses;
    
    private MatriculaRepository (int studentID) {
        super();
        enrollmentCourses = loadEnrollmentCourses(studentID);
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

    private Result loadEnrollmentCourses (int studentID) {
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
                    result.addDatasetItem(new Group(campus, groupNumber, schedule, teacher, capacity, modality));
                }
            }
        } catch (Exception exception) {} finally {
            closeResources();
        }
        return result;
    }
}