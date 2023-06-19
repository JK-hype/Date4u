package de.materna.date4u.core.filter;

import java.time.LocalDate;
import java.util.Objects;

public class SearchFilter {
    private LocalDate minDate;
    private LocalDate maxDate;
    private int minHornlength;
    private int maxHornlength;

    public SearchFilter() {
    }

    public SearchFilter(int minAge, int maxAge, int minHornlength, int maxHornlength) {
        setMinDate(minAge);
        setMaxDate(maxAge);
        this.minHornlength = minHornlength;
        this.maxHornlength = maxHornlength;
    }

    public LocalDate getMinDate() {
        return minDate;
    }

    public void setMinDate(int maxAge) {
        if (maxAge > 0) {
            this.minDate = LocalDate.now().minusYears(maxAge);
        }
    }

    public LocalDate getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(int minAge) {
        if (minAge > 0 ) {
            this.maxDate = LocalDate.now().minusYears(minAge);
        }
    }

    public int getMinHornlength() {
        return minHornlength;
    }

    public void setMinHornlength(int minHornlength) {
        if (minHornlength > 0) {
            this.minHornlength = minHornlength;
        }
    }

    public int getMaxHornlength() {
        return maxHornlength;
    }

    public void setMaxHornlength(int maxHornlength) {
        if (maxHornlength > 0) {
            this.maxHornlength = maxHornlength;
        }
    }

    public boolean isValidHornlength() {
        return maxHornlength > minHornlength;
    }

    public boolean isValidDate() {
     return Objects.nonNull(maxDate) && Objects.nonNull(minDate) && minDate.isBefore(maxDate);
    }

    @Override
    public String toString() {
        return "SearchFilter{" +
                "minDate=" + minDate +
                ", maxDate=" + maxDate +
                ", minHornlength=" + minHornlength +
                ", maxHornlength=" + maxHornlength +
                '}';
    }
}
