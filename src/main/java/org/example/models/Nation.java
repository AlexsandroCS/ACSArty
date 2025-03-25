package org.example.models;

public enum Nation {
    ALIADO_EIXO("Aliado | Eixo", 1),
    SOVIETICO("Soviético", 2),
    BRITANICO("Britânico",3);

    private final String name;
    private final int value;

    Nation(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName(){
        return name;
    }

    public int getValue(){
        return value;
    }
}
