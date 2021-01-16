package be.rouget.puzzles.adventofcode.year2020.day21;


import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Allergen {

    private String name;
    private Set<String> possibleIngredients;

    public Allergen(String name) {
        this.name = name;
        this.possibleIngredients = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public Set<String> getPossibleIngredients() {
        return possibleIngredients;
    }

    public void addFoodInfo(Food food) {
        if (!food.getAllergens().contains(name)) {
            // this food does not mention this allergen so we do not have additional information from it
            return;
        }
        if (possibleIngredients.isEmpty()) {
            // first food mentioning this allergen
            possibleIngredients.addAll(food.getIngredients());
        } else {
            // Possible ingredients are the intersection of current possible ingredients and of food ingredients
            possibleIngredients.retainAll(Sets.newHashSet(food.getIngredients()));
        }
    }

    public void removePossibleIngredient(String ingredient) {
        possibleIngredients.remove(ingredient);
    }

    public boolean isSolved() {
        return possibleIngredients.size() == 1;
    }

    public String getIngredient() {
        if (!isSolved()) {
            throw new IllegalStateException("Allergen " + name + " is not solved!");
        }
        return possibleIngredients.iterator().next();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Allergen allergen = (Allergen) o;
        return name.equals(allergen.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
