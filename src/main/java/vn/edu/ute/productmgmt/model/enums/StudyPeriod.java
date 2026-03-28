package vn.edu.ute.productmgmt.model.enums;

public enum StudyPeriod {
    P1("Tiết 1", "07:30", "08:20"),
    P2("Tiết 2", "08:30", "09:20"),
    P3("Tiết 3", "09:30", "10:20"),
    P4("Tiết 4", "10:30", "11:20"),
    P5("Tiết 5", "11:30", "12:20"),
    P6("Tiết 6", "13:00", "13:50"),
    P7("Tiết 7", "14:00", "14:50"),
    P8("Tiết 8", "15:00", "15:50"),
    P9("Tiết 9", "16:00", "16:50"),
    P10("Tiết 10", "17:00", "17:50"),
    P11("Tiết 11", "18:00", "18:50"),
    P12("Tiết 12", "19:00", "19:50");

    private final String label;
    private final String startTime;
    private final String endTime;

    StudyPeriod(String label, String startTime, String endTime) {
        this.label = label;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getLabel() { return label; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }

    @Override
    public String toString() {
        return label + " (" + startTime + " - " + endTime + ")";
    }
}