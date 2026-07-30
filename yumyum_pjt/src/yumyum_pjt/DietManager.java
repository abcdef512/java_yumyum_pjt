package yumyum_pjt;

import java.util.List;

public interface DietManager {

    boolean add(Diet diet);

    List<Diet> getAll();

    Diet getDiet(int id) throws DietNotFoundException;

    List<Diet> searchDate(String date);

    Food[] searchFood(String name);

    void update(Diet diet) throws DietNotFoundException;

    boolean delete(int id);

    double[][] analyzeDate(String date);

    NutritionGoal getNutritionGoal();

    void saveData();

    void loadData();
}
