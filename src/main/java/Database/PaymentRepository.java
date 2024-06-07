package Database;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Types;

import Payments.Payment;

public class PaymentRepository extends Repository {
    
    private static PaymentRepository instance;

    private PaymentRepository() {
        super();
    }

    public static synchronized PaymentRepository getInstance() {
        if (instance == null) {
            instance = new PaymentRepository();
        }
        return instance;
    }

    public Result getPendingPayments (int studentID) {
        ResultSet resultSet;
        Result result = new Result(); 

        try {
            connection = DriverManager.getConnection(connectionURL);
            String storedProcedureQuery = "{CALL dbo.getPendingPayments(?, ?)}";
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
                    String convention = resultSet.getString(1);
                    String description = resultSet.getString(2);
                    String period = resultSet.getString(3);
                    float total = resultSet.getFloat(4);
                    result.addDatasetItem(new Payment(convention, description, period, total));
                }
            }
        } catch (Exception exception) {} finally {
            closeResources();
        }
        return result;
    }

    public int updatePayment (int studentID, Payment payment) {
        ResultSet resultSet;
        int resultCode = 0;

        try {
            connection = DriverManager.getConnection(connectionURL);
            String storedProcedureQuery = "{CALL dbo.updatePayment(?, ?, ?, ?, ?)}";
            callableStatement = connection.prepareCall(storedProcedureQuery);

            callableStatement.setInt(1, studentID);
            callableStatement.setString(2, payment.getConvention());
            callableStatement.setString(3, payment.getDescription());
            callableStatement.setString(4, payment.getPeriod());

            callableStatement.registerOutParameter(5, Types.INTEGER);
            callableStatement.execute();

            resultSet = callableStatement.getResultSet();
            resultSet.next();
            resultCode = resultSet.getInt(1);
            System.out.println("Result Code: " + resultCode);
        } catch (Exception exception) {} finally {
            closeResources();
        }
        return resultCode;
    }
}