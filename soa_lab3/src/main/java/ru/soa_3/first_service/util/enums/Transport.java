package ru.soa_3.first_service.util.enums;

public enum Transport {
    FEW(2),
    NONE(1),
    NORMAL(3),
    ENOUGH(4);

    private final int value;

    Transport(int value) {
        this.value = value;
    }

    public boolean isGreaterThan(Transport other) {
        return this.value >= other.value;
    }
}
