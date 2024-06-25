// mojInterface.aidl
package eti.kuchta.lab4;

// Declare any non-default types here with import statements

interface mojInterface {
    int getPid();
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
                double aDouble, String aString);
}