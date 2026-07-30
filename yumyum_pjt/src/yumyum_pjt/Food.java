package yumyum_pjt;

import java.io.Serializable;

public class Food implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private double calories;
    private double protein;
    private double carbs;
    private double fat;

    public Food() {
    }

    public Food(String name, double calories, double protein, double carbs, double fat) {
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getCarbs() {
        return carbs;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    @Override
    public String toString() {
        return String.format("%s | %.1fkcal | 단백질 %.1fg | 탄수화물 %.1fg | 지방 %.1fg",
                name, calories, protein, carbs, fat);
    }
}
