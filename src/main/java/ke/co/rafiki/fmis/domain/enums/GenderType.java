package ke.co.rafiki.fmis.domain.enums;

public enum GenderType {
    MALE("MALE"), FEMALE("FEMALE");

    private final String value;

    GenderType(String value) { this.value = value; }

    @Override
    public String toString() { return this.value; }
}
