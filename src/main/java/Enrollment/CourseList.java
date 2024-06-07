package Enrollment;

import java.util.ArrayList;
import java.util.Iterator;

import Observers.MessageObserver;
import Observers.MessageSubject;
import javafx.geometry.Insets;
import javafx.scene.control.Accordion;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class CourseList extends VBox implements MessageSubject {
    
    private CourseSelectionList coursesSelected;
    private ArrayList<MessageObserver> observers;
    private ArrayList<Course> courses;
    private Accordion courseList;
    private HBox header;
    
    public CourseList(ArrayList<Course> courses) {
        this.courses = courses;
        coursesSelected = new CourseSelectionList();
        observers = new ArrayList<MessageObserver>();
        setHeader();
        setAccordion();
        this.getChildren().addAll(header, courseList);
    }

    @Override
    public void registerObserver(MessageObserver observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (Iterator<MessageObserver> iterator = observers.iterator(); iterator.hasNext(); ){
            MessageObserver observer = iterator.next();
            observer.update(message);
        }
    }

    public int getSize() {
        return this.courses.size();
    }

    public CourseSelectionList getCourseSelection() {
        return this.coursesSelected;
    }

    private void setAccordion() {
        courseList = new Accordion();
        for (Course course : courses) {
            TitledPane pane = createTitledPane(course);
            courseList.getPanes().add(pane);
        }
        VBox.setVgrow(courseList, Priority.ALWAYS);
    }

    private void setHeader() {
        header = new HBox();
        header.setSpacing(40);
        header.setPadding(new Insets(10));
        
        Label codeHeader = new Label("CÓDIGO");
        Label courseHeader = new Label("CURSO");
        
        header.getChildren().addAll(codeHeader, courseHeader);

        HBox.setHgrow(codeHeader, Priority.ALWAYS);
        HBox.setHgrow(courseHeader, Priority.ALWAYS);
    }

    private TitledPane createTitledPane(Course course) {
        GridPane gridPane = new GridPane();
        TitledPane titledPane = new TitledPane();
        ArrayList<CheckBox> checkBoxes = new ArrayList<>();
        int rowIndex = 0;
    
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));
    
        ColumnConstraints campus = new ColumnConstraints();
        campus.setPercentWidth(10);
        ColumnConstraints group = new ColumnConstraints();
        group.setPercentWidth(10);
        ColumnConstraints schedule = new ColumnConstraints();
        schedule.setPercentWidth(20);
        ColumnConstraints teacher = new ColumnConstraints();
        teacher.setPercentWidth(30);
        ColumnConstraints capacity = new ColumnConstraints();
        capacity.setPercentWidth(7);
        ColumnConstraints modality = new ColumnConstraints();
        modality.setPercentWidth(18);
        ColumnConstraints select = new ColumnConstraints();
        select.setPercentWidth(5);
    
        gridPane.getColumnConstraints().addAll(campus, group, schedule, teacher, capacity, modality, select);
    
        gridPane.add(new Label("SEDE"), 0, rowIndex);
        gridPane.add(new Label("GRUPO"), 1, rowIndex);
        gridPane.add(new Label("HORARIO"), 2, rowIndex);
        gridPane.add(new Label("PROFESOR(ES)"), 3, rowIndex);
        gridPane.add(new Label("CUPO"), 4, rowIndex);
        gridPane.add(new Label("TIPO"), 5, rowIndex);
        gridPane.add(new Label(" "), 6, rowIndex);
    
        rowIndex++;
    
        for (Group courseGroup : course.getGroups()) {
            gridPane.add(new Label(courseGroup.getCampus()), 0, rowIndex);
            gridPane.add(new Label(String.valueOf(courseGroup.getGroupNumber())), 1, rowIndex);
            gridPane.add(new Label(courseGroup.getSchedule()), 2, rowIndex);
            gridPane.add(new Label(courseGroup.getTeacher()), 3, rowIndex);
            gridPane.add(new Label(String.valueOf(courseGroup.getCapacity())), 4, rowIndex);
            gridPane.add(new Label(courseGroup.getModality()), 5, rowIndex);
    
            CheckBox newCheckbox = new CheckBox();
            newCheckbox.setId(course.getCourseCode() + " " + courseGroup.getGroupNumber());
            if (courseGroup.isSelected()) {
                newCheckbox.setSelected(true);
                coursesSelected.addCourseSelection(new CourseSelection(course.getCourseCode(), courseGroup.getGroupNumber(), courseGroup.getSchedule(), true));
            }
    
            newCheckbox.setOnAction(event -> {
                setCheckBoxAction(course, courseGroup, newCheckbox, checkBoxes);
            });
    
            checkBoxes.add(newCheckbox);
            gridPane.add(newCheckbox, 6, rowIndex);
    
            rowIndex++;
        }
    
        titledPane.setGraphic(createHeaderPane(course));
        titledPane.setContent(gridPane);
        return titledPane;
    }
    
    private void setCheckBoxAction (Course course, Group courseGroup, CheckBox newCheckbox, ArrayList<CheckBox> checkBoxes) {
        CourseSelection selection = new CourseSelection(course.getCourseCode(), courseGroup.getGroupNumber(), courseGroup.getSchedule(), true);
    
        if (!newCheckbox.isSelected()) {
            for (CourseSelection courseSelected : coursesSelected.getSelectedCourses()) {
                if (courseSelected.getCourseID().equals(course.getCourseCode()) && courseSelected.getGroupNumber() == courseGroup.getGroupNumber()){
                    courseSelected.setSelected(false);
                }
            }
        } else {
            boolean valid = true;
            for (CourseSelection courseSelected : coursesSelected.getSelectedCourses()) {
                if (courseSelected.clashesCourse(selection)) {
                    newCheckbox.setSelected(false);
                    notifyObservers("El curso seleccionado choca con " + courseSelected.getCourseID());
                    valid = false;
                    break;
                }
            }
            if (valid) {
                coursesSelected.addCourseSelection(selection);
                // Ensure only one checkbox is selected
                for (CheckBox checkbox : checkBoxes) {
                    if (checkbox != newCheckbox) {
                        checkbox.setSelected(false);
                    }
                }
            }
        }
    }

    private HBox createHeaderPane (Course course) {
        HBox headerPane = new HBox();
        headerPane.setSpacing(30);
        headerPane.setPadding(new Insets(5));

        Label codeLabel = new Label(course.getCourseCode());
        Label nameLabel = new Label(course.getCourse());

        headerPane.getChildren().addAll(codeLabel, nameLabel);

        HBox.setHgrow(codeLabel, Priority.ALWAYS);
        HBox.setHgrow(nameLabel, Priority.ALWAYS);

        return headerPane;
    }
}