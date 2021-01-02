package Model;

public enum UserType {
    ADMIN("ADMIN"),
    REQUEST("REQUEST"),
    STUDENT("STUDENT");

    public final String label;

    private UserType(String label) {
        this.label = label;
    }
}
