package Final.Project.dodo.model.enums;

public enum PaymentType {
    CASH("Наличные"),
    CASHLESS("Безналичные"),

    CARD("Оплата картой курьеру ");

    private final String description;

    PaymentType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
