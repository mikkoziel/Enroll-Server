package Model;

public enum UserType {
    ADMIN("ADMIN"),
    REQUEST("REQUEST"),
    STUDENT("STUDENT"),
    DELETED("DELETED");

    public final String label;

    UserType(String label) {
        this.label = label;
    }
}
