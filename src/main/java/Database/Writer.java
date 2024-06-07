package Database;

import java.util.ArrayList;

import Controllers.MatriculaController;
import Enrollment.CourseSelection;

public class Writer {
    
    private static Writer instance;

    private MatriculaRepository matriculaRepository;
    private MatriculaController matriculaController;
    private ArrayList<Object[]> coursesSelected;

    private Writer(int studentID) {
        coursesSelected = new ArrayList<>();
        matriculaRepository = MatriculaRepository.getInstance(studentID);
        matriculaController = MatriculaController.getInstance(studentID);
    }

    public static synchronized Writer getInstance (int studentID) {
        if (instance == null) {
            instance = new Writer(studentID);
        }
        return instance;
    }

    private void getCoursesSelected() {
        ArrayList<CourseSelection> selection;
        selection = matriculaController.getCourseSelection().getSelectedCourses();
        for (CourseSelection courseSelection : selection) {
            coursesSelected.add(new Object[]{courseSelection.getCourseID(), courseSelection.getGroupNumber()});
        }
    }

    public void writeData() {
        getCoursesSelected();
        matriculaRepository.updateCourseSelection(coursesSelected);
    }
}