package be.rouget.puzzles.adventofcode.year2020.day21;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AllergenAssessmentTest {

    @Test
    void computeResults() {

        AllergenAssessment allergenAssessment = new AllergenAssessment(List.of(
                "mxmxvkd kfcds sqjhc nhms (contains dairy, fish)",
                "trh fvjkl sbzzf mxmxvkd (contains dairy)",
                "sqjhc fvjkl (contains soy)",
                "sqjhc mxmxvkd sbzzf (contains fish)"
        ));
        assertThat(allergenAssessment.computeResultForPart1()).isEqualTo(5L);
        assertThat(allergenAssessment.computeResultForPart2()).isEqualTo("mxmxvkd,sqjhc,fvjkl");
    }
}