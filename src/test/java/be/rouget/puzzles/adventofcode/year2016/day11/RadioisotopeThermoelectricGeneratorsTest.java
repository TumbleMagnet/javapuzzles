package be.rouget.puzzles.adventofcode.year2016.day11;

import com.google.common.collect.Sets;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static be.rouget.puzzles.adventofcode.year2016.day11.EquipmentType.GENERATOR;
import static be.rouget.puzzles.adventofcode.year2016.day11.EquipmentType.MICROCHIP;
import static be.rouget.puzzles.adventofcode.year2016.day11.Isotope.HYDROGEN;
import static be.rouget.puzzles.adventofcode.year2016.day11.Isotope.LITHIUM;
import static org.assertj.core.api.Assertions.assertThat;

class RadioisotopeThermoelectricGeneratorsTest {

    @Test
    void numberOfMovesToFinalState() {
        Set<Equipment>[] floors = new Set[] {
                Sets.newHashSet(new Equipment(MICROCHIP, HYDROGEN), new Equipment(MICROCHIP, LITHIUM)),
                Sets.newHashSet(new Equipment(GENERATOR, HYDROGEN)),
                Sets.newHashSet(new Equipment(GENERATOR, LITHIUM)),
                Sets.newHashSet()
        };
        State startState = State.createState(0, floors);
        int count = new RadioisotopeThermoelectricGenerators().numberOfMovesToFinalState(startState);
        assertThat(count).isEqualTo(11);
    }
}