package com.example.haxxor.AbilityListView;

public class Ability {
    String Type;
    String Name;
    String Effect;
    String Image;

    public Ability(String type, String name, String effect, String image) {
        Type = type;
        Name = name;
        Effect = effect;
        Image = image;

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
    public String getImage() {
        return Image;
    }
}
