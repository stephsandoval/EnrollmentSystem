package Controllers;

import AcademicHistory.Record;
import AcademicHistory.RecordList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AcademicHistoryController {

    private static AcademicHistoryController instance;
    private ObservableList<Record> records;
    private RecordList recordList;
    private int studentID;

    private AcademicHistoryController (int studentID) {
        this.studentID = studentID;
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
        records.add(new Record("CI0200", "Examen Diagnósitco", 114, 0, "Suficiencia", 100, "1S-2023"));
        records.add(new Record("CI0202", "Inglés Básico", 4, 2, "Cursado", 100, "1S-2023"));
        records.add(new Record("MA0101", "Matemática General", 100, 2, "Reconocimiento", 101, "1S-2023"));
        records.add(new Record("CI1106", "Comunicación Escrita", 100, 2, "Reconocimiento", 101, "1S-2023"));
        records.add(new Record("IC1400", "Fundamentos de Organización de Computadoras", 5, 3, "Cursado", 100, "1S-2023"));
        records.add(new Record("IC1802", "Introducción a la Programación", 5, 3, "Cursado", 95, "1S-2023"));
        records.add(new Record("IC1803", "Taller de Programación", 5, 3, "Cursado", 100, "1S-2023"));
        records.add(new Record("MA1403", "Matemática Discreta", 1, 4, "Cursado", 100, "1S-2023"));
        records.add(new Record("CI1107", "Comunicación Oral", 100, 1, "Reconocimiento", 100, "1S-2023"));
        records.add(new Record("CI1230", "Inglés I", 100, 2, "Reconocimiento", 100, "1S-2023"));
        return records;
    }
 
    public RecordList getRecordList() {
        return this.recordList;
    }
}