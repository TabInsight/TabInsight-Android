package com.tabinsights.rest;

/**
 * Created by biphilip on 10/3/15.
 */
public enum EndPoints {
    PUBLISH_DEVICE_LOGS("/publish/log");

    private final String name;

    private EndPoints(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}
