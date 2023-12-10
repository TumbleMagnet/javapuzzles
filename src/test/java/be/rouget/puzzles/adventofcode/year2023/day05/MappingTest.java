package be.rouget.puzzles.adventofcode.year2023.day05;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MappingTest {

    @Test
    void testParse() {
        Mapping actual = Mapping.parse(List.of("seed-to-soil map:", "50 98 2", "52 50 48"));
        assertThat(actual).isEqualTo(new Mapping("seed", "soil", List.of(
                new MappingLine(50L, 98L, 2L),
                new MappingLine(52L, 50L, 48L)
        )));
    }

    @Test
    void testMapSourceToDestination() {
        Mapping mapping = Mapping.parse(List.of("seed-to-soil map:", "50 98 2", "52 50 48"));
        assertThat(mapping.mapValue( 0L)).isEqualTo( 0L);
        assertThat(mapping.mapValue( 1L)).isEqualTo( 1L);
        assertThat(mapping.mapValue(48L)).isEqualTo(48L);
        assertThat(mapping.mapValue(49L)).isEqualTo(49L);
        assertThat(mapping.mapValue(50L)).isEqualTo(52L);
        assertThat(mapping.mapValue(51L)).isEqualTo(53L);
        assertThat(mapping.mapValue(96L)).isEqualTo(98L);
        assertThat(mapping.mapValue(97L)).isEqualTo(99L);
        assertThat(mapping.mapValue(98L)).isEqualTo(50L);
        assertThat(mapping.mapValue(99L)).isEqualTo(51L);
        assertThat(mapping.mapValue(100L)).isEqualTo(100L);
        
        assertThat(mapping.mapValue(79L)).isEqualTo(81L);
        assertThat(mapping.mapValue(14L)).isEqualTo(14L);
        assertThat(mapping.mapValue(55L)).isEqualTo(57L);
        assertThat(mapping.mapValue(13L)).isEqualTo(13L);
    }
}