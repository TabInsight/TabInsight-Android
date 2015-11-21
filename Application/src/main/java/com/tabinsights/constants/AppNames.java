package com.tabinsights.constants;

/**
 * Created by biphilip on 10/3/15.
 */
public enum AppNames{
    TALLY_TOTS("air.com.spinlight.tallytots"),
    KIDS_ABC_PHONICS("com.intellijoy.android.phonics"),
    ABC_KIDS_HANDWIRTING_HWTP("com.teachersparadise.fingertracing004"),
    FACEBOOK("com.facebook.katana");


    private final String name;

    private AppNames(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}
