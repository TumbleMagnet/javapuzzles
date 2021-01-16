package be.rouget.puzzles.adventofcode.year2020.day21;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FoodTest {

    @Test
    public void fromInput() {
        Food food = Food.fromInput("mxmxvkd kfcds sqjhc nhms (contains dairy, fish)");
        assertThat(food.getIngredients()).containsExactly("mxmxvkd", "kfcds", "sqjhc", "nhms");
        assertThat(food.getAllergens()).containsExactly("dairy", "fish");
    }
}