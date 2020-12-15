package Model;

public enum Status {
    CREATED("CREATED"),
    ENROLLMENT("ENROLLMENT"),
    PROCESSING("PROCESSING"),
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    public final String label;

    private Status(String label) {
        this.label = label;
    }
}
