package be.rouget.puzzles.adventofcode.year2020.day21;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AllergenAssessment {

    private static final String YEAR = "2020";
    private static final String DAY = "21";

    private static final Logger LOG = LogManager.getLogger(AllergenAssessment.class);

    private final List<Food> foods;

    private Set<Allergen> allergens;

    public AllergenAssessment(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        foods = input.stream().map(Food::fromInput).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        AllergenAssessment aoc = new AllergenAssessment(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public long computeResultForPart1() {

        // Compute which ingredients contain possibly each allergen
        Map<String, Allergen> allergensMap = Maps.newHashMap();
        for (Food food : foods) {
            for (String allergenName : food.getAllergens()) {
                if (!allergensMap.containsKey(allergenName)) {
                    allergensMap.put(allergenName, new Allergen(allergenName));
                }
                Allergen allergen = allergensMap.get(allergenName);
                allergen.addFoodInfo(food);
            }
        }
        allergens = Sets.newHashSet(allergensMap.values());

        // Compute which ingredients cannot possibly contain an allergen
        Set<String> ingredients = foods.stream()
                .flatMap(food -> food.getIngredients().stream())
                .collect(Collectors.toSet());
        List<String> ingredientsWithoutAllergen = ingredients.stream()
                .filter(i -> cannotContainAnAllergen(i, allergens))
                .collect(Collectors.toList());

        // Count how many times ingredients without allergen appear as food ingredient
        long count = 0;
        for (Food food : foods) {
            List<String> foodIngredients = food.getIngredients();
            for (String ingredientWithoutAllergen : ingredientsWithoutAllergen) {
                if (foodIngredients.contains(ingredientWithoutAllergen)) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean cannotContainAnAllergen(String ingredient, Set<Allergen> allergens) {
        for (Allergen allergen : allergens) {
            if (allergen.getPossibleIngredients().contains(ingredient)) {
                return false;
            }
        }
        return true;
    }

    public String computeResultForPart2() {

        allergens.forEach(a -> LOG.info("Allergen {} - ingredients: {}", a.getName(), StringUtils.join(a.getPossibleIngredients(), ",")));

        Set<Allergen> solvedAllergens = Sets.newHashSet();
        while (solvedAllergens.size() != allergens.size()) {
            for (Allergen remainingAllergen : allergens) {
                if (remainingAllergen.isSolved()) {
                    solvedAllergens.add(remainingAllergen);
                }
                else {
                    for (Allergen solvedAllergen : solvedAllergens) {
                        remainingAllergen.removePossibleIngredient(solvedAllergen.getIngredient());
                    }
                    if (remainingAllergen.isSolved()) {
                        solvedAllergens.add(remainingAllergen);
                    }
                }
            }
        }
        return solvedAllergens.stream()
                .sorted(Comparator.comparing(Allergen::getName))
                .map(Allergen::getIngredient)
                .collect(Collectors.joining(","));
    }
}