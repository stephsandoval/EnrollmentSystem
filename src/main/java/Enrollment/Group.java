package Enrollment;

public class Group {
    
    private String campus, schedule, teacher, modality;
    private int groupNumber, capacity;

    public Group (String campus, int groupNumber, String schedule, String teacher, int capacity, String modality) {
        this.campus = campus;
        this.groupNumber = groupNumber;
        this.schedule = schedule;
        this.teacher = teacher;
        this.capacity = capacity;
        this.modality = modality;
    }

    public void setCampus (String campus) {
        this.campus = campus;
    }

    public void setGroupNumber (int groupNumber) {
        this.groupNumber = groupNumber;
    }

    public void setSchedule (String schedule) {
        this.schedule = schedule;
    }

    public void setTeacher (String teacher) {
        this.teacher = teacher;
    }

    public void setCapacity (int capacity) {
        this.capacity = capacity;
    }

    public void setModality (String modality) {
        this.modality = modality;
    }

    public String getCampus() {
        return this.campus;
    }

    public int getGroupNumber() {
        return this.groupNumber;
    }

    public String getSchedule() {
        return this.schedule;
    }

    public String getTeacher() {
        return this.teacher;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public String getModality() {
        return this.modality;
    }
}