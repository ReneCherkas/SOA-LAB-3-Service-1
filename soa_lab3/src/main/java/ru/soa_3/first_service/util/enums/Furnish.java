package ru.soa_3.first_service.util.enums;

public enum Furnish {
    DESIGNER(4),
    FINE(3),
    BAD(1),
    LITTLE(2);

    private final int value;

    Furnish(int value) {
        this.value = value;
    }

    public boolean isGreaterThan(Furnish other) {
        return this.value >= other.value;
    }
}