package com.edhub.models;

public enum Especialidad {

    JAVA("Java"),
    JAVASCRIPT("JavaScript"),
    CSHARP("C#"),
    CMASMAS("C++"),
    PYTHON("Python");

    private final String lenguajes;

    Especialidad(String lenguajes) {
        this.lenguajes = lenguajes;
    }

    public String getLenguajes() {
        return lenguajes;
    }

}
