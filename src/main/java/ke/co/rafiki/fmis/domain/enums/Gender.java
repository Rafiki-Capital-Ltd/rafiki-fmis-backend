package ke.co.rafiki.fmis.domain.enums;

public enum Gender {
    MALE("MALE"), FEMALE("FEMALE");

    private final String value;

    Gender(String value) { this.value = value; }

    @Override
    public String toString() { return this.value; }
}
