package Controllers;

import java.util.ArrayList;

import AcademicHistory.Record;
import AcademicHistory.RecordList;
import Database.AcademicHistoryRepository;
import Database.Result;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AcademicHistoryController {

    private static AcademicHistoryController instance;
    private AcademicHistoryRepository repository;

    private ObservableList<Record> records;
    private RecordList recordList;

    private AcademicHistoryController (int studentID) {
        repository = AcademicHistoryRepository.getInstance(studentID);
        records = getRecords();
        recordList = new RecordList(records);
    }

    public static synchronized AcademicHistoryController getInstance (int studentID) {
        if (instance == null) {
            instance = new AcademicHistoryController(studentID);
        }
        return instance;
    }
    
    private ObservableList<Record> getRecords() {
        ObservableList<Record> records = FXCollections.observableArrayList();
        Result academicRecords = repository.getAcademicRecords();
        if (academicRecords.getResultCodes() == 0) {
            ArrayList<Object> studentRecords = academicRecords.getDataset();
            for (Object record : studentRecords) {
                records.add((Record)(record));
            }
        }
        return records;
    }
 
    public RecordList getRecordList() {
        return this.recordList;
    }
}