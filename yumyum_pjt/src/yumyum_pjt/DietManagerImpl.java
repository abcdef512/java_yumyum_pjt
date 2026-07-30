package yumyum_pjt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DietManagerImpl implements DietManager {

    private List<Food> foodList = new ArrayList<>();
    private DietData dietData = new DietData();

    private static DietManager manager;

    private final File foodFile = new File("data/foods.json");
    private final File dietFile = new File("data/diet-data.json");

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private DietManagerImpl() {
        loadFoodData();
        loadData();
    }

    public static DietManager getManager() {
        if (manager == null) {
            manager = new DietManagerImpl();
        }
        return manager;
    }

    private void loadFoodData() {
        foodList.clear();

        if (!foodFile.exists()) {
            System.out.println("음식 파일이 없습니다: " + foodFile.getAbsolutePath());
            return;
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(foodFile), StandardCharsets.UTF_8))) {

            JsonArray array = JsonParser.parseReader(br).getAsJsonArray();

            for (JsonElement element : array) {
                JsonObject object = element.getAsJsonObject();

                Food food = new Food(
                        getString(object, "name"),
                        getDouble(object, "calories"),
                        getDouble(object, "protein"),
                        getDouble(object, "carbs"),
                        getDouble(object, "fat"));

                foodList.add(food);
            }

            System.out.println("음식 데이터 " + foodList.size() + "개 불러오기 완료");

        } catch (IOException | RuntimeException e) {
            System.out.println("음식 데이터를 불러오지 못했습니다.");
            e.printStackTrace();
        }
    }

    private String getString(JsonObject object, String key) {
        if (!object.has(key) || object.get(key).isJsonNull()) {
            return "";
        }
        return object.get(key).getAsString();
    }

    private double getDouble(JsonObject object, String key) {
        if (!object.has(key) || object.get(key).isJsonNull()) {
            return 0;
        }

        String value = object.get(key).getAsString().trim();
        if (value.isEmpty()) {
            return 0;
        }

        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @Override
    public boolean add(Diet diet) {
        if (diet == null || diet.getFoods() == null || diet.getFoods().isEmpty()) {
            return false;
        }

        diet.setId(getNextId());
        dietData.getMeals().add(diet);
        saveData();
        return true;
    }

    private int getNextId() {
        int maxId = 0;

        for (Diet diet : dietData.getMeals()) {
            if (diet.getId() > maxId) {
                maxId = diet.getId();
            }
        }

        return maxId + 1;
    }

    @Override
    public List<Diet> getAll() {
        return dietData.getMeals();
    }

    @Override
    public Diet getDiet(int id) throws DietNotFoundException {
        for (Diet diet : dietData.getMeals()) {
            if (diet.getId() == id) {
                return diet;
            }
        }

        throw new DietNotFoundException(id);
    }

    @Override
    public List<Diet> searchDate(String date) {
        List<Diet> result = new ArrayList<>();

        for (Diet diet : dietData.getMeals()) {
            if (diet.getDate().equals(date)) {
                result.add(diet);
            }
        }

        return result;
    }

    @Override
    public Food[] searchFood(String name) {
        List<Food> result = new ArrayList<>();

        if (name == null || name.trim().isEmpty()) {
            return new Food[0];
        }

        for (Food food : foodList) {
            if (food.getName().contains(name.trim())) {
                result.add(food);
            }
        }

        return result.toArray(new Food[result.size()]);
    }

    @Override
    public void update(Diet diet) throws DietNotFoundException {
        for (int i = 0; i < dietData.getMeals().size(); i++) {
            if (dietData.getMeals().get(i).getId() == diet.getId()) {
                dietData.getMeals().set(i, diet);
                saveData();
                return;
            }
        }

        throw new DietNotFoundException(diet.getId());
    }

    @Override
    public boolean delete(int id) {
        for (int i = 0; i < dietData.getMeals().size(); i++) {
            if (dietData.getMeals().get(i).getId() == id) {
                dietData.getMeals().remove(i);
                saveData();
                return true;
            }
        }

        return false;
    }

    @Override
    public double[][] analyzeDate(String date) {
        // 행: 아침, 점심, 저녁, 전체
        // 열: 칼로리, 단백질, 탄수화물, 지방
        double[][] result = new double[4][4];

        for (Diet diet : dietData.getMeals()) {
            if (!diet.getDate().equals(date)) {
                continue;
            }

            int row = getMealRow(diet.getType());

            for (Food food : diet.getFoods()) {
                result[row][0] += food.getCalories();
                result[row][1] += food.getProtein();
                result[row][2] += food.getCarbs();
                result[row][3] += food.getFat();

                result[3][0] += food.getCalories();
                result[3][1] += food.getProtein();
                result[3][2] += food.getCarbs();
                result[3][3] += food.getFat();
            }
        }

        return result;
    }

    private int getMealRow(String type) {
        if ("아침".equals(type)) {
            return 0;
        }
        if ("점심".equals(type)) {
            return 1;
        }
        return 2;
    }

    @Override
    public NutritionGoal getNutritionGoal() {
        return dietData.getNutritionGoals();
    }

    @Override
    public void saveData() {
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(dietFile), StandardCharsets.UTF_8))) {

            gson.toJson(dietData, bw);

        } catch (IOException e) {
            System.out.println("식단 데이터를 저장하지 못했습니다.");
            e.printStackTrace();
        }
    }

    @Override
    public void loadData() {
        if (!dietFile.exists()) {
            dietData = new DietData();
            return;
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(dietFile), StandardCharsets.UTF_8))) {

            DietData loadedData = gson.fromJson(br, DietData.class);

            if (loadedData == null) {
                dietData = new DietData();
            } else {
                dietData = loadedData;
            }

            if (dietData.getMeals() == null) {
                dietData.setMeals(new ArrayList<>());
            }
            if (dietData.getNutritionGoals() == null) {
                dietData.setNutritionGoals(new NutritionGoal());
            }
            if (dietData.getWeeklyStats() == null) {
                dietData.setWeeklyStats(new WeeklyStats());
            }

        } catch (IOException | RuntimeException e) {
            System.out.println("식단 데이터를 불러오지 못했습니다.");
            dietData = new DietData();
            e.printStackTrace();
        }
    }
}
