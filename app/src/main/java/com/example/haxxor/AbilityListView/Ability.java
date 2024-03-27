package com.example.haxxor.AbilityListView;

public class Ability {
    String Type;
    String Name;
    String Effect;

    public Ability(String type, String name, String effect) {
        Type = type;
        Name = name;
        Effect = effect;
    }

    public String getType() {
        return Type;
    }

    public String getName() {
        return Name;
    }

    public String getEffect() {
        return Effect;
    }
}
