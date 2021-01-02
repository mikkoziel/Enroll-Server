package Model;

public class FieldOfStudy {
    private int field_id;
    private String name;
    private String short_name;
    private String start_year;

    public FieldOfStudy(int field_id, String name, String short_name, String start_year){
        this.field_id = field_id;
        this.name = name;
        this.short_name = start_year;
        this.start_year = start_year;
    }

    public FieldOfStudy(String name, String short_name, String start_year){
        this.name = name;
        this.short_name = start_year;
        this.start_year = start_year;
    }

    public int getField_id() {
        return field_id;
    }

    public void setField_id(int field_id) {
        this.field_id = field_id;
    }

    public String getName() {
        return name;
    }

    public String getShort_name() {
        return short_name;
    }

    public String getStart_year() {
        return start_year;
    }

    @Override
    public String toString() {
        return "{\"field_id\": " + field_id +
                ", \"name\":\"" + name +
                "\", \"short_name\":\"" + short_name +
                "\", \"start_year\":\"" + start_year +
                "\"}";
    }
}
