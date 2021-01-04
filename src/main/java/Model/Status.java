package Model;

public enum Status {
    CREATED("CREATED"),
    ENROLLMENT("ENROLLMENT"),
    PROCESSING("PROCESSING"),
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    DELETED("DELETED");

    public final String label;

    Status(String label) {
        this.label = label;
    }
}
