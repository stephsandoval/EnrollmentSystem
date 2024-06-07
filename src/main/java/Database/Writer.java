package Database;

import java.util.ArrayList;

import Controllers.InclusionController;
import Controllers.MatriculaController;
import Enrollment.CourseSelection;

public class Writer {
    
    private static Writer instance;

    private MatriculaRepository matriculaRepository;
    private MatriculaController matriculaController;
    private ArrayList<Object[]> coursesEnrollment;

    private InclusionRepository inclusionRepository;
    private InclusionController inclusionController;
    private ArrayList<Object[]> coursesSelectedInclusion;

    private Writer(int studentID) {
        coursesEnrollment = new ArrayList<>();
        coursesSelectedInclusion = new ArrayList<>();
        matriculaRepository = MatriculaRepository.getInstance(studentID);
        matriculaController = MatriculaController.getInstance(studentID);
        inclusionRepository = InclusionRepository.getInstance(studentID);
        inclusionController = InclusionController.getInstance(studentID);
    }

    public static synchronized Writer getInstance (int studentID) {
        if (instance == null) {
            instance = new Writer(studentID);
        }
        return instance;
    }

    private void getCoursesSelected() {
        ArrayList<CourseSelection> enrollmentSelection;
        ArrayList<CourseSelection> inclusionSelection;
    
        enrollmentSelection = matriculaController.getCourseSelection().getSelectedCourses();
    
        for (CourseSelection courseSelected : enrollmentSelection) {
            coursesEnrollment.add(new Object[]{courseSelected.getCourseID(), courseSelected.getGroupNumber(), courseSelected.isSelected()});
        }
    
        inclusionSelection = inclusionController.getCourseSelection().getSelectedCourses();
        for (CourseSelection courseSelection : inclusionSelection) {
            coursesSelectedInclusion.add(new Object[]{courseSelection.getCourseID(), courseSelection.getGroupNumber()});
        }
    }
    
    public void writeData() {
        getCoursesSelected();
        matriculaRepository.updateCourseSelection(coursesEnrollment);
        inclusionRepository.updateCourseSelection(coursesSelectedInclusion);
    }
}