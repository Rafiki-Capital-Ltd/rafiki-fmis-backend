package ke.co.rafiki.fmis.domain.enums;

public enum AssetStatus {
    FUNCTIONAL("FUNCTIONAL"),
    NON_FUNCTIONAL("NON_FUNCTIONAL");

    private final String value;

    AssetStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
