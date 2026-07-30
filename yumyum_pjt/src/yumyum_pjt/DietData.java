package yumyum_pjt;

import java.util.ArrayList;
import java.util.List;

public class DietData {

    private List<Diet> meals = new ArrayList<>();
    private NutritionGoal nutritionGoals = new NutritionGoal();
    private WeeklyStats weeklyStats = new WeeklyStats();

    public DietData() {
    }

    public List<Diet> getMeals() {
        return meals;
    }

    public void setMeals(List<Diet> meals) {
        this.meals = meals;
    }

    public NutritionGoal getNutritionGoals() {
        return nutritionGoals;
    }

    public void setNutritionGoals(NutritionGoal nutritionGoals) {
        this.nutritionGoals = nutritionGoals;
    }

    public WeeklyStats getWeeklyStats() {
        return weeklyStats;
    }

    public void setWeeklyStats(WeeklyStats weeklyStats) {
        this.weeklyStats = weeklyStats;
    }
}
