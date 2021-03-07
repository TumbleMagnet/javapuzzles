package be.rouget.puzzles.adventofcode.year2015.day15;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CookieRecipe {

    private static final String YEAR = "2015";
    private static final String DAY = "15";
    private static final Logger LOG = LogManager.getLogger(CookieRecipe.class);

    private final static int TOTAL_QUANTITY = 100;
    private final static int NUMBER_OF_CALORIES = 500;
    private final List<Ingredient> ingredients;

    public CookieRecipe(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        ingredients = input.stream()
                .map(Ingredient::fromInput)
                .peek(LOG::info)
                .collect(Collectors.toList());
    }

    public long computeResultForPart1() {
        return enumerateQuantities().stream()
                .mapToLong(this::computeScore)
                .max().orElseThrow();
    }

    public long computeResultForPart2() {
        return enumerateQuantities().stream()
                .filter(r -> computeCalories(r) == NUMBER_OF_CALORIES)
                .mapToLong(this::computeScore)
                .max().orElseThrow();
    }

    private List<List<Integer>> enumerateQuantities() {
        return enumerateQuantities(Lists.newArrayList());
    }

    private List<List<Integer>> enumerateQuantities(List<Integer> partialQuantities) {
        int remainingNumberOfIngredients = ingredients.size() - partialQuantities.size();
        int remainingQuantity = TOTAL_QUANTITY - partialQuantities.stream().mapToInt(Integer::intValue).sum();
        if (remainingNumberOfIngredients == 1) {
            List<Integer> completeRecipe = Lists.newArrayList(partialQuantities);
            completeRecipe.add(remainingQuantity);
            return List.of(completeRecipe);
        } else {
            List<List<Integer>> recipes = Lists.newArrayList();
            for (int quantity = 0; quantity <= remainingQuantity; quantity++) {
                ArrayList<Integer> newQuantities = Lists.newArrayList(partialQuantities);
                newQuantities.add(quantity);
                recipes.addAll(enumerateQuantities(newQuantities));
            }
            return recipes;
        }
    }

    private long computeScore(List<Integer> quantities) {
        if (ingredients.size() != quantities.size()) {
            throw new IllegalArgumentException("Number of quantities " + quantities.size() + " does not match number of ingredients " + ingredients.size());
        }
        long capacity = 0;
        long durability = 0;
        long flavor = 0;
        long texture = 0;
        for (int i = 0; i < ingredients.size(); i++) {
            Ingredient ingredient = ingredients.get(i);
            long quantity = quantities.get(i);
            capacity += quantity * ingredient.getCapacity();
            durability += quantity * ingredient.getDurability();
            flavor += quantity * ingredient.getFlavor();
            texture += quantity * ingredient.getTexture();
        }
        capacity = Math.max(capacity, 0);
        durability = Math.max(durability, 0);
        flavor = Math.max(flavor, 0);
        texture = Math.max(texture, 0);
        return capacity * durability * flavor * texture;
    }

    private long computeCalories(List<Integer> quantities) {
        if (ingredients.size() != quantities.size()) {
            throw new IllegalArgumentException("Number of quantities " + quantities.size() + " does not match number of ingredients " + ingredients.size());
        }
        long calories = 0;
        for (int i = 0; i < ingredients.size(); i++) {
            Ingredient ingredient = ingredients.get(i);
            long quantity = quantities.get(i);
            calories += quantity * ingredient.getCalories();
        }
        return calories;
    }

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        CookieRecipe aoc = new CookieRecipe(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }
}