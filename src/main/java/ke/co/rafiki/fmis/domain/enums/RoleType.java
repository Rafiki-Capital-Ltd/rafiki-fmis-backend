package ke.co.rafiki.fmis.domain.enums;

public enum RoleType {
    FARMER("FARMER"),
    MANAGER("MANAGER"),
    ADMIN("ADMIN");

    private final String value;

    RoleType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
