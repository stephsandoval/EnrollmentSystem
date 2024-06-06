package Database;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Types;

import AcademicHistory.Record;

public class AcademicHistoryRepository extends Repository {
    
    private static AcademicHistoryRepository instance;
    private Result academicRecords;

    private AcademicHistoryRepository (int studentID) {
        super();
        academicRecords = loadAcademicRecords(studentID);
    }

    public static synchronized AcademicHistoryRepository getInstance(int studentID) {
        if (instance == null) {
            instance = new AcademicHistoryRepository(studentID);
        }
        return instance;
    }

    public Result getAcademicRecords() {
        return this.academicRecords;
    }

    private Result loadAcademicRecords (int studentID) {
        ResultSet resultSet;
        Result result = new Result(); 

        try {
            connection = DriverManager.getConnection(connectionURL);
            String storedProcedureQuery = "{CALL dbo.getAcademicHistory(?, ?)}";
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
                    int groupNumber = resultSet.getInt(3);
                    int credits = resultSet.getInt(4);
                    String condition = resultSet.getString(5);
                    int grade = resultSet.getInt(6);
                    String period = resultSet.getString(7);
                    result.addDatasetItem(new Record(courseID, courseName, groupNumber, credits, condition, grade, period));
                }
            }
        } catch (Exception exception) {} finally {
            closeResources();
        }

        return result;
    }
}