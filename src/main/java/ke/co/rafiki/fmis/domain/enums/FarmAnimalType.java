package ke.co.rafiki.fmis.domain.enums;

public enum FarmAnimalType {
    COW("COW"), GOAT("GOAT"), SHEEP("SHEEP"),
    PIG("PIG"), CHICKEN("CHICKEN"), RABBIT("RABBIT");

    private final String value;

    FarmAnimalType(String value) { this.value = value; }

    @Override
    public String toString() { return this.value; }
}
