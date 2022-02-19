package be.rouget.puzzles.adventofcode.year2021.day23;

import com.google.common.collect.Sets;
import org.junit.jupiter.api.Test;

import static be.rouget.puzzles.adventofcode.year2021.day23.AmphipodType.*;
import static be.rouget.puzzles.adventofcode.year2021.day23.LocationCode.*;
import static org.assertj.core.api.Assertions.assertThat;

class AmphipodMapTest {

    @Test
    void testComputeMovesForPosition() {

        AmphipodMap map = map(
                position(ROOM_A, 0, BRONZE),
                position(ROOM_A, 1, DESERT),
                position(ROOM_B, 0, DESERT),
                position(ROOM_B, 1, COPPER),
                position(ROOM_C, 0, COPPER),
                position(ROOM_C, 1, AMBER),
                position(ROOM_D, 0, DESERT),
                position(ROOM_D, 1, AMBER)
        );

        Position startingPosition = position(ROOM_A, 0, BRONZE);
        assertThat(map.computeMovesForPosition(startingPosition)).containsExactlyInAnyOrder(
                new AmphipodMap.Move(startingPosition, position(HALLWAY, 0, BRONZE), 30),
                new AmphipodMap.Move(startingPosition, position(HALLWAY, 1, BRONZE), 20),
                new AmphipodMap.Move(startingPosition, position(HALLWAY, 3, BRONZE), 20),
                new AmphipodMap.Move(startingPosition, position(HALLWAY, 5, BRONZE), 40),
                new AmphipodMap.Move(startingPosition, position(HALLWAY, 7, BRONZE), 60),
                new AmphipodMap.Move(startingPosition, position(HALLWAY, 9, BRONZE), 80),
                new AmphipodMap.Move(startingPosition, position(HALLWAY, 10, BRONZE), 90)
        );

        startingPosition = position(ROOM_B, 0, DESERT);
        assertThat(map.computeMovesForPosition(startingPosition)).containsExactlyInAnyOrder(
                new AmphipodMap.Move(startingPosition, position(HALLWAY, 0, DESERT), 5000),
                new AmphipodMap.Move(startingPosition, position(HALLWAY, 1, DESERT), 4000),
                new AmphipodMap.Move(startingPosition, position(HALLWAY, 3, DESERT), 2000),
                new AmphipodMap.Move(startingPosition, position(HALLWAY, 5, DESERT), 2000),
                new AmphipodMap.Move(startingPosition, position(HALLWAY, 7, DESERT), 4000),
                new AmphipodMap.Move(startingPosition, position(HALLWAY, 9, DESERT), 6000),
                new AmphipodMap.Move(startingPosition, position(HALLWAY, 10, DESERT), 7000)
        );

        startingPosition = position(ROOM_C, 0, COPPER);
        assertThat(map.computeMovesForPosition(startingPosition)).containsExactlyInAnyOrder(
                new AmphipodMap.Move(startingPosition, position(HALLWAY, 0, COPPER), 700),
                new AmphipodMap.Move(startingPosition, position(HALLWAY, 1, COPPER), 600),
                new AmphipodMap.Move(startingPosition, position(HALLWAY, 3, COPPER), 400),
                new AmphipodMap.Move(startingPosition, position(HALLWAY, 5, COPPER), 200),
                new AmphipodMap.Move(startingPosition, position(HALLWAY, 7, COPPER), 200),
                new AmphipodMap.Move(startingPosition, position(HALLWAY, 9, COPPER), 400),
                new AmphipodMap.Move(startingPosition, position(HALLWAY, 10, COPPER), 500)
        );

        startingPosition = position(ROOM_D, 0, DESERT);
        assertThat(map.computeMovesForPosition(startingPosition)).containsExactlyInAnyOrder(
                new AmphipodMap.Move(startingPosition, position(HALLWAY, 0, DESERT), 9000),
                new AmphipodMap.Move(startingPosition, position(HALLWAY, 1, DESERT), 8000),
                new AmphipodMap.Move(startingPosition, position(HALLWAY, 3, DESERT), 6000),
                new AmphipodMap.Move(startingPosition, position(HALLWAY, 5, DESERT), 4000),
                new AmphipodMap.Move(startingPosition, position(HALLWAY, 7, DESERT), 2000),
                new AmphipodMap.Move(startingPosition, position(HALLWAY, 9, DESERT), 2000),
                new AmphipodMap.Move(startingPosition, position(HALLWAY, 10, DESERT), 3000)
        );
    }

    @Test
    void testPossibleMoves() {
        AmphipodMap map = map(
                position(ROOM_B, 0, DESERT),
                position(ROOM_B, 1, COPPER),
                position(ROOM_C, 0, COPPER),
                position(ROOM_D, 0, DESERT),
                position(ROOM_D, 1, AMBER),
                position(HALLWAY, 10, AMBER),
                position(HALLWAY, 0, BRONZE),
                position(HALLWAY, 1, DESERT)
        );

        assertThat(map.possibleMoves()).containsExactly(
                new AmphipodMapWithCost(
                        map(
                                position(ROOM_A, 1, AMBER),
                                position(ROOM_B, 0, DESERT),
                                position(ROOM_B, 1, COPPER),
                                position(ROOM_C, 0, COPPER),
                                position(ROOM_D, 0, DESERT),
                                position(ROOM_D, 1, AMBER),
                                position(HALLWAY, 0, BRONZE),
                                position(HALLWAY, 1, DESERT)
                        ),
                        10
                )
        );
    }

    @Test
    void testDoMove() {

        AmphipodMap map = map(
                position(ROOM_A, 0, AMBER),
                position(ROOM_A, 1, AMBER),
                position(ROOM_B, 0, BRONZE),
                position(ROOM_B, 1, BRONZE),
                position(HALLWAY, 10, COPPER),
                position(ROOM_C, 1, COPPER),
                position(ROOM_D, 0, DESERT),
                position(ROOM_D, 1, DESERT)
        );

        int energyCost = 19;

        AmphipodMapWithCost actual = map.doMove(new AmphipodMap.Move(
                position(HALLWAY, 10, COPPER),
                position(ROOM_C, 0, COPPER),
                energyCost));

        assertThat(actual.getCost()).isEqualTo(energyCost);
        assertThat(actual.getMap()).isEqualTo(
                map(
                        position(ROOM_A, 0, AMBER),
                        position(ROOM_A, 1, AMBER),
                        position(ROOM_B, 0, BRONZE),
                        position(ROOM_B, 1, BRONZE),
                        position(ROOM_C, 0, COPPER),
                        position(ROOM_C, 1, COPPER),
                        position(ROOM_D, 0, DESERT),
                        position(ROOM_D, 1, DESERT)
                )
        );
    }

    @Test
    void testIsTarget() {
        assertThat(map(
                position(ROOM_A, 0, AMBER),
                position(ROOM_A, 1, AMBER),
                position(ROOM_B, 0, BRONZE),
                position(ROOM_B, 1, BRONZE),
                position(ROOM_C, 0, COPPER),
                position(ROOM_C, 1, COPPER),
                position(ROOM_D, 0, DESERT),
                position(ROOM_D, 1, DESERT)
        ).isTarget()).isTrue();

        assertThat(map(
                position(ROOM_A, 0, AMBER),
                position(ROOM_A, 1, AMBER),
                position(ROOM_B, 0, BRONZE),
                position(ROOM_B, 1, BRONZE),
                position(HALLWAY, 10, COPPER),
                position(ROOM_C, 1, COPPER),
                position(ROOM_D, 0, DESERT),
                position(ROOM_D, 1, DESERT)
        ).isTarget()).isFalse();

        assertThat(map(
                position(ROOM_A, 0, AMBER),
                position(ROOM_A, 1, AMBER),
                position(ROOM_B, 0, BRONZE),
                position(ROOM_B, 1, DESERT),
                position(ROOM_C, 0, COPPER),
                position(ROOM_C, 1, COPPER),
                position(ROOM_D, 0, DESERT),
                position(ROOM_D, 1, BRONZE)
        ).isTarget()).isFalse();
    }

    private AmphipodMap map(Position... positions) {
        return new AmphipodMap(Sets.newHashSet(positions));
    }

    private Position position(LocationCode locationCode, int index, AmphipodType amphipodType) {
        return new Position(new Location(locationCode, index), amphipodType);
    }
}