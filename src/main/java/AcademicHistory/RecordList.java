package AcademicHistory;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class RecordList extends TableView<Record> {

    public RecordList(ObservableList<Record> records) {
        setItems(records);
        initializeColumns();
    }

    @SuppressWarnings("unchecked")
    private void initializeColumns() {
        TableColumn<Record, String> codeColumn = new TableColumn<>("CÓDIGO");
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("courseID"));

        TableColumn<Record, String> courseColumn = new TableColumn<>("CURSO");
        courseColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));

        TableColumn<Record, Integer> groupColumn = new TableColumn<>("GRUPO");
        groupColumn.setCellValueFactory(new PropertyValueFactory<>("groupNumber"));

        TableColumn<Record, Integer> creditsColumn = new TableColumn<>("CRÉDITOS");
        creditsColumn.setCellValueFactory(new PropertyValueFactory<>("credits"));

        TableColumn<Record, String> conditionColumn = new TableColumn<>("ESTADO");
        conditionColumn.setCellValueFactory(new PropertyValueFactory<>("condition"));

        TableColumn<Record, Integer> gradeColumn = new TableColumn<>("NOTA");
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));

        TableColumn<Record, String> periodColumn = new TableColumn<>("PERIODO");
        periodColumn.setCellValueFactory(new PropertyValueFactory<>("period"));

        getColumns().addAll(codeColumn, courseColumn, groupColumn, creditsColumn, conditionColumn, gradeColumn, periodColumn);
    }
}
