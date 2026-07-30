package yumyum_pjt;

public class DietNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    private int dietId;

    public DietNotFoundException(int dietId) {
        super(dietId + "번 식단을 찾을 수 없습니다.");
        this.dietId = dietId;
    }

    public int getDietId() {
        return dietId;
    }
}
