package yumyum_pjt;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DietManagerTest {

    private static final Scanner sc = new Scanner(System.in);
    private static final DietManager manager = DietManagerImpl.getManager();

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            printMenu();
            int menu = readInt("메뉴 선택: ");

            try {
                switch (menu) {
                case 1:
                    searchFood();
                    break;
                case 2:
                    addDiet();
                    break;
                case 3:
                    printAll();
                    break;
                case 4:
                    printDetail();
                    break;
                case 5:
                    updateDiet();
                    break;
                case 6:
                    deleteDiet();
                    break;
                case 7:
                    analyzeDate();
                    break;
                case 0:
                    manager.saveData();
                    running = false;
                    System.out.println("프로그램을 종료합니다.");
                    break;
                default:
                    System.out.println("올바른 메뉴를 선택해주세요.");
                }
            } catch (DietNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("처리 중 오류가 발생했습니다: " + e.getMessage());
            }
        }

        sc.close();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("========== 식단 관리 ==========");
        System.out.println("1. 음식 검색");
        System.out.println("2. 식단 작성");
        System.out.println("3. 식단 전체 조회");
        System.out.println("4. 식단 상세 조회");
        System.out.println("5. 식단 수정");
        System.out.println("6. 식단 삭제");
        System.out.println("7. 날짜별 영양 분석");
        System.out.println("0. 종료");
        System.out.println("===============================");
    }

    private static void searchFood() {
        System.out.print("검색할 음식명: ");
        String keyword = sc.nextLine();

        Food[] result = manager.searchFood(keyword);
        printFoodResults(result);
    }

    private static void addDiet() {
        System.out.print("날짜(yyyy-MM-dd): ");
        String date = sc.nextLine();
        String type = selectMealType();
        List<Food> foods = selectFoods();

        if (manager.add(new Diet(date, type, foods))) {
            System.out.println("식단이 등록되었습니다.");
        } else {
            System.out.println("식단 등록에 실패했습니다.");
        }
    }

    private static void printAll() {
        List<Diet> diets = manager.getAll();

        if (diets.isEmpty()) {
            System.out.println("등록된 식단이 없습니다.");
            return;
        }

        for (Diet diet : diets) {
            System.out.println(diet);
        }
    }

    private static void printDetail() throws DietNotFoundException {
        int id = readInt("식단 ID: ");
        Diet diet = manager.getDiet(id);
        printDiet(diet);
    }

    private static void updateDiet() throws DietNotFoundException {
        int id = readInt("수정할 식단 ID: ");
        manager.getDiet(id);

        System.out.print("새 날짜(yyyy-MM-dd): ");
        String date = sc.nextLine();
        String type = selectMealType();
        List<Food> foods = selectFoods();

        Diet updated = new Diet(date, type, foods);
        updated.setId(id);
        manager.update(updated);

        System.out.println("식단이 수정되었습니다.");
    }

    private static void deleteDiet() {
        int id = readInt("삭제할 식단 ID: ");

        if (manager.delete(id)) {
            System.out.println("식단이 삭제되었습니다.");
        } else {
            System.out.println("해당 식단을 찾을 수 없습니다.");
        }
    }

    private static void analyzeDate() {
        System.out.print("분석 날짜(yyyy-MM-dd): ");
        String date = sc.nextLine();

        double[][] result = manager.analyzeDate(date);
        String[] rows = { "아침", "점심", "저녁", "전체" };

        System.out.println("\n[" + date + " 영양 분석]");
        System.out.println("구분\t칼로리\t단백질\t탄수화물\t지방");

        for (int i = 0; i < result.length; i++) {
            System.out.printf("%s\t%.1f\t%.1f\t%.1f\t%.1f%n",
                    rows[i], result[i][0], result[i][1], result[i][2], result[i][3]);
        }

        NutritionGoal goal = manager.getNutritionGoal();
        System.out.printf("목표 칼로리: %.1f / 섭취 칼로리: %.1f%n",
                goal.getDailyCalories(), result[3][0]);
    }

    private static String selectMealType() {
        String[] types = { "아침", "점심", "저녁" };

        while (true) {
            System.out.println("1. 아침  2. 점심  3. 저녁");
            int choice = readInt("식사 종류: ");

            if (choice >= 1 && choice <= 3) {
                return types[choice - 1];
            }

            System.out.println("1~3 중에서 선택해주세요.");
        }
    }

    private static List<Food> selectFoods() {
        List<Food> selectedFoods = new ArrayList<>();

        while (true) {
            System.out.print("음식 검색어: ");
            String keyword = sc.nextLine();
            Food[] result = manager.searchFood(keyword);

            if (result.length == 0) {
                System.out.println("검색 결과가 없습니다.");
            } else {
                int displayCount = Math.min(result.length, 30);

                for (int i = 0; i < displayCount; i++) {
                    System.out.println((i + 1) + ". " + result[i]);
                }

                if (result.length > displayCount) {
                    System.out.println("검색 결과가 많아 앞의 " + displayCount + "개만 표시합니다.");
                }

                int choice = readInt("추가할 음식 번호(0: 취소): ");

                if (choice >= 1 && choice <= displayCount) {
                    selectedFoods.add(result[choice - 1]);
                    System.out.println(result[choice - 1].getName() + " 추가 완료");
                }
            }

            System.out.print("음식을 더 추가하시겠습니까?(y/n): ");
            String answer = sc.nextLine();

            if (!answer.equalsIgnoreCase("y")) {
                break;
            }
        }

        return selectedFoods;
    }

    private static void printFoodResults(Food[] foods) {
        if (foods.length == 0) {
            System.out.println("검색 결과가 없습니다.");
            return;
        }

        int displayCount = Math.min(foods.length, 30);
        for (int i = 0; i < displayCount; i++) {
            System.out.println((i + 1) + ". " + foods[i]);
        }

        System.out.println("총 " + foods.length + "개 검색됨");
    }

    private static void printDiet(Diet diet) {
        System.out.println("식단 ID: " + diet.getId());
        System.out.println("날짜: " + diet.getDate());
        System.out.println("종류: " + diet.getType());
        System.out.println("[음식 목록]");

        for (Food food : diet.getFoods()) {
            System.out.println("- " + food);
        }

        System.out.printf("합계: %.1fkcal / 단백질 %.1fg / 탄수화물 %.1fg / 지방 %.1fg%n",
                diet.getTotalCalories(), diet.getTotalProtein(), diet.getTotalCarbs(), diet.getTotalFat());
    }

    private static int readInt(String message) {
        while (true) {
            System.out.print(message);
            String input = sc.nextLine();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
            }
        }
    }
}
