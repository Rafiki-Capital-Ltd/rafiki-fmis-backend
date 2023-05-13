package ke.co.rafiki.fmis.domain.enums;

public enum FarmCropType {
    MAIZE("MAIZE"), BEANS("BEANS"), MANGO("MANGO"),
    ORANGE("ORANGE"), GREEN_GRAMS("GREEN_GRAMS");

    private final String value;

    FarmCropType(String value) { this.value = value; }

    @Override
    public String toString() { return this.value; }
}
