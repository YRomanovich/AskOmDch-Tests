package org.askOmDch.constants;

public enum Timeouts {
    SHORT(10),
    LONG(50);

    public final int timeout;

    Timeouts(int timeout) {
        this.timeout = timeout;
    }
}
