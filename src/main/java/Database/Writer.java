package Database;

import java.util.ArrayList;

import Controllers.InclusionController;
import Controllers.MatriculaController;
import Enrollment.Course;
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
        ArrayList<CourseSelection> enrollmentDeselection;
        ArrayList<CourseSelection> inclusionSelection;
    
        enrollmentSelection = matriculaController.getCourseSelection().getSelectedCourses();
        enrollmentDeselection = matriculaController.getCourseDeselection().getSelectedCourses();
    
        for (CourseSelection courseDeselected : enrollmentDeselection) {
            coursesEnrollment.add(new Object[]{courseDeselected.getCourseID(), courseDeselected.getGroupNumber(), false});
        }
    
        for (CourseSelection courseSelected : enrollmentSelection) {
            boolean alreadyDeselected = false;
            for (CourseSelection courseDeselected : enrollmentDeselection) {
                if (courseDeselected.getCourseID().equals(courseSelected.getCourseID()) && 
                    courseDeselected.getGroupNumber() == courseSelected.getGroupNumber()) {
                    alreadyDeselected = true;
                    System.out.println("matches a deselected one");
                    break;
                }
            }
            if (!alreadyDeselected) {
                coursesEnrollment.add(new Object[]{courseSelected.getCourseID(), courseSelected.getGroupNumber(), true});
            }
        }
    
        System.out.println("------------------------------");
        for (Object[] o : coursesEnrollment) {
            System.out.println(o[0] + " " + o[1] + " " + o[2]);
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