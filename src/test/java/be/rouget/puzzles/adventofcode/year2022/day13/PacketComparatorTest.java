package be.rouget.puzzles.adventofcode.year2022.day13;

import org.junit.jupiter.api.Test;

import java.util.List;

import static be.rouget.puzzles.adventofcode.year2022.day13.PacketComparator.parsePacket;
import static org.assertj.core.api.Assertions.assertThat;

class PacketComparatorTest {

    @Test
    void testParsePacket() {
        assertThat(parsePacket("[]")).isEqualTo(new ListElement(List.of()));
        
        assertThat(parsePacket("[4]")).isEqualTo(new ListElement(List.of(
                new IntegerElement(4)
        )));
        
        assertThat(parsePacket("[4,0,756,8]")).isEqualTo(new ListElement(List.of(
                new IntegerElement(4),
                new IntegerElement(0),
                new IntegerElement(756),
                new IntegerElement(8)
        )));

        assertThat(parsePacket("[[1],[2,3,4]]")).isEqualTo(new ListElement(List.of(
                new ListElement(List.of(new IntegerElement(1))),
                new ListElement(List.of(
                        new IntegerElement(2),
                        new IntegerElement(3),
                        new IntegerElement(4)
                ))
        )));
    }
}