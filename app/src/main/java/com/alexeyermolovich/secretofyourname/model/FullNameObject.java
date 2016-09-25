package com.alexeyermolovich.secretofyourname.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ermolovich on 24.9.16.
 */

public class FullNameObject extends NameObject {

    @SerializedName("available_name")
    private List<String> availableName;
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
    private List<String> compatibilityName;
    @SerializedName("not_compatibility_name")
    private List<String> notCompatibilityName;
    @SerializedName("middle_name_is_combined")
    private List<String> middleNameIsCombined;
    @SerializedName("badges")
    private List<String> badges;
    @SerializedName("colors")
    private List<String> colors;
    @SerializedName("plant")
    private List<String> plant;
    @SerializedName("animals")
    private List<String> animals;
    @SerializedName("mineral")
    private List<String> mineral;
    @SerializedName("planet")
    private List<String> planet;
    @SerializedName("successful_day")
    private List<String> successfulDay;
    @SerializedName("health")
    private String health;
    @SerializedName("character_traits")
    private List<String> characterTraits;
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

    public List<String> getNotCompatibilityName() {
        return notCompatibilityName;
    }

    public List<String> getCompatibilityName() {
        return compatibilityName;
    }

    public List<String> getMiddleNameIsCombined() {
        return middleNameIsCombined;
    }

    public List<String> getBadges() {
        return badges;
    }

    public List<String> getColors() {
        return colors;
    }

    public List<String> getPlant() {
        return plant;
    }

    public List<String> getAnimals() {
        return animals;
    }

    public List<String> getMineral() {
        return mineral;
    }

    public List<String> getPlanet() {
        return planet;
    }

    public List<String> getSuccessfulDay() {
        return successfulDay;
    }

    public String getHealth() {
        return health;
    }

    public List<String> getCharacterTraits() {
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

    public List<String> getAvailableName() {
        return availableName;
    }
}
