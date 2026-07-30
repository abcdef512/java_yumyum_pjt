package yumyum_pjt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Diet implements Serializable, Comparable<Diet> {

	private static final long serialVersionUID = 1L;

	private int id;
	private String date;
	private String type;
	private List<Food> foods = new ArrayList<>();

	public Diet() {
	}

	public Diet(String date, String type, List<Food> foods) {
		this.date = date;
		this.type = type;
		this.foods = foods;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Food> getFoods() {
		return foods;
	}

	public void setFoods(List<Food> foods) {
		this.foods = foods;
	}

	public double getTotalCalories() {
		double total = 0;
		for (Food food : foods) {
			total += food.getCalories();
		}
		return total;
	}

	public double getTotalProtein() {
		double total = 0;
		for (Food food : foods) {
			total += food.getProtein();
		}
		return total;
	}

	public double getTotalCarbs() {
		double total = 0;
		for (Food food : foods) {
			total += food.getCarbs();
		}
		return total;
	}

	public double getTotalFat() {
		double total = 0;
		for (Food food : foods) {
			total += food.getFat();
		}
		return total;
	}

	@Override
	public int compareTo(Diet other) {
		return this.id - other.id;
	}

	@Override
	public String toString() {
		return String.format("Diet [id=%d, date=%s, type=%s, foods=%d개, calories=%d]", id, date, type, foods.size(),
				getTotalCalories());
	}
}
