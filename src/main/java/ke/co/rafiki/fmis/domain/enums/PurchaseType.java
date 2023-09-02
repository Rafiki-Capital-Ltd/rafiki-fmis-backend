package ke.co.rafiki.fmis.domain.enums;

public enum PurchaseType {
    CASH("CASH"),
    CREDIT("CREDIT");

    private final String value;

    PurchaseType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
