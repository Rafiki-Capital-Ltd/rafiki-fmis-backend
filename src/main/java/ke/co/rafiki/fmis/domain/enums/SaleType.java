package ke.co.rafiki.fmis.domain.enums;

public enum SaleType {
    CASH("CASH"),
    CREDIT("CREDIT");

    private final String value;

    SaleType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
