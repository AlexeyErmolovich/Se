package com.alexeyermolovich.secretofyourname.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ermolovich on 24.9.16.
 */

public class FullNameObject extends NameObject {

    @SerializedName("available_name")
    private String availableName;
    @SerializedName("orthodox_birthday")
    private String orthodoxBirthday;
    @SerializedName("catholic_birthday")
    private String catholicBirthday;
    @SerializedName("history")
    private String history;
    @SerializedName("character")
    private String character;
    @SerializedName("sexuality")
    private String sexuality;
    @SerializedName("compatibility_name")
    private String compatibilityName;
    @SerializedName("not_compatibility_name")
    private String notCompatibilityName;
    @SerializedName("middle_name_is_combined")
    private String middleNameIsCombined;
    @SerializedName("badges")
    private String badges;
    @SerializedName("colors")
    private String colors;
    @SerializedName("plant")
    private String plant;
    @SerializedName("animals")
    private String animals;
    @SerializedName("mineral")
    private String mineral;
    @SerializedName("planet")
    private String planet;
    @SerializedName("successful_day")
    private String successfulDay;
    @SerializedName("health")
    private String health;
    @SerializedName("character_traits")
    private String characterTraits;
    @SerializedName("celebrities")
    private List<String> celebrities;
    @SerializedName("professions")
    private String professions;
    @SerializedName("hobbies")
    private String hobbies;

    public FullNameObject() {
        super(NameObject.UNKNOWN, NameObject.UNKNOWN);
    }

    public FullNameObject(String name, String sex) {
        super(name, sex);
    }

    public String getOrthodoxBirthday() {
        return orthodoxBirthday;
    }

    public String getHistory() {
        return history;
    }

    public String getCharacter() {
        return character;
    }

    public String getSexuality() {
        return sexuality;
    }

    public String getNotCompatibilityName() {
        return notCompatibilityName;
    }

    public String getCompatibilityName() {
        return compatibilityName;
    }

    public String getMiddleNameIsCombined() {
        return middleNameIsCombined;
    }

    public String getBadges() {
        return badges;
    }

    public String getColors() {
        return colors;
    }

    public String getPlant() {
        return plant;
    }

    public String getAnimals() {
        return animals;
    }

    public String getMineral() {
        return mineral;
    }

    public String getPlanet() {
        return planet;
    }

    public String getSuccessfulDay() {
        return successfulDay;
    }

    public String getHealth() {
        return health;
    }

    public String getCharacterTraits() {
        return characterTraits;
    }

    public List<String> getCelebrities() {
        return celebrities;
    }

    public String getProfessions() {
        return professions;
    }

    public String getHobbies() {
        return hobbies;
    }

    public String getCatholicBirthday() {
        return catholicBirthday;
    }

    public String getAvailableName() {
        return availableName;
    }
}
