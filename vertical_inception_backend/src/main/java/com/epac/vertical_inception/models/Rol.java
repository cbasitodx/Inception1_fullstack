package com.epac.vertical_inception.models;

public enum Rol {
    USUARIO("Usuario"),
    ADMINISTRADOR("Administrador"),
    SUPER_ADMINISTRADOR("Super-Administrador");

    String value;

    Rol(String value) {
        this.value =value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public static Rol fromValue(String value) {
        for (Rol rol : Rol.values()) {
            if (rol.value.equals(value)) {
                return rol;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
