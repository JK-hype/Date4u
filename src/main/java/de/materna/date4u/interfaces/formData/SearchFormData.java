package de.materna.date4u.interfaces.formData;

public class SearchFormData {

    private int minAge = 1;
    private int maxAge = 1;
    private int minHornlength = 1;
    private int maxHornlength = 1;

    public SearchFormData() {
    }

    public SearchFormData(int minAge, int maxAge, int minHornlength, int maxHornlength) {
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.minHornlength = minHornlength;
        this.maxHornlength = maxHornlength;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public int getMinHornlength() {
        return minHornlength;
    }

    public void setMinHornlength(int minHornlength) {
        this.minHornlength = minHornlength;
    }

    public int getMaxHornlength() {
        return maxHornlength;
    }

    public void setMaxHornlength(int maxHornlength) {
        this.maxHornlength = maxHornlength;
    }

    @Override
    public String toString() {
        return "SearchFormData{" +
                "fromAge=" + minAge +
                ", toAge=" + maxAge +
                ", fromHornlength=" + minHornlength +
                ", toHornlength=" + maxHornlength +
                '}';
    }
}
