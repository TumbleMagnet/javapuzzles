package be.rouget.puzzles.adventofcode.year2021.day19;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static be.rouget.puzzles.adventofcode.year2021.day19.Coordinates.parse;
import static org.assertj.core.api.Assertions.assertThat;

class ScannerTest {

    private static final Logger LOG = LogManager.getLogger(ScannerTest.class);

    @Test
    void testChangingOrientation() {
        Scanner scanner = new Scanner("scanner 0", List.of(
                parse("-1,-1,1"),
                parse("-2,-2,2"),
                parse("-3,-3,3"),
                parse("-2,-3,1"),
                parse("5,6,-4"),
                parse("8,0,7")
        ));

        List<Scanner> allOrientations = scanner.listOrientations();
        assertThat(allOrientations).contains(
                new Scanner("scanner 0", List.of(
                        parse("-1,-1,1"),
                        parse("-2,-2,2"),
                        parse("-3,-3,3"),
                        parse("-2,-3,1"),
                        parse("5,6,-4"),
                        parse("8,0,7")))
        );
        assertThat(allOrientations).contains(
                new Scanner("scanner 0", List.of(
                        parse("1,-1,1"),
                        parse("2,-2,2"),
                        parse("3,-3,3"),
                        parse("2,-1,3"),
                        parse("-5,4,-6"),
                        parse("-8,-7,0")))
        );
        assertThat(allOrientations).contains(
                new Scanner("scanner 0", List.of(
                        parse("-1,-1,-1"),
                        parse("-2,-2,-2"),
                        parse("-3,-3,-3"),
                        parse("-1,-3,-2"),
                        parse("4,6,5"),
                        parse("-7,0,8")))
        );
        assertThat(allOrientations).contains(
                new Scanner("scanner 0", List.of(
                        parse("1,1,-1"),
                        parse("2,2,-2"),
                        parse("3,3,-3"),
                        parse("1,3,-2"),
                        parse("-4,-6,5"),
                        parse("7,0,8")))
        );
        assertThat(allOrientations).contains(
                new Scanner("scanner 0", List.of(
                        parse("1,1,1"),
                        parse("2,2,2"),
                        parse("3,3,3"),
                        parse("3,1,2"),
                        parse("-6,-4,-5"),
                        parse("0,7,-8")))
        );
    }

    @Test
    void testPairing() {
        List<Scanner> scanners = Scanner.parseScanners(SolverUtils.readTest(BeaconScanner.class));

        assertThat(scanners.get(0).checkPairing(scanners.get(1))).isPresent();
        assertThat(scanners.get(1).checkPairing(scanners.get(4))).isPresent();
    }

    @Test
    void testFindOrientation() {

        Coordinates beacon0 = new Coordinates(-618, -824, -621);
        Coordinates beacon1 = new Coordinates(686,422,578);
        Coordinates translation = new Coordinates(68, -1246, -43).inverse();

        for (FacingDirection direction : FacingDirection.values()) {
            for (Rotation rotation : Rotation.values()) {
                Coordinates orientedBeacon1 = beacon1.changeDirection(direction).rotate(rotation);
                LOG.info("Oriented beacon1: {}", orientedBeacon1);
                Coordinates transformedBeacon1 = orientedBeacon1.translate(translation);
                LOG.info("Translated and oriented beacon1: {}", transformedBeacon1);
                if (transformedBeacon1.equals(beacon0)) {
                    LOG.info("Match found! Direction: {} and rotation {}", direction, rotation);
                    return;
                }
            }
        }
    }

    @Test
    void testMatchingBeacons() {
        // Oriented beacon1: Coordinates(x=-686, y=422, z=-578)
        // Translated and oriented beacon1: Coordinates(x=-618, y=-824, z=-621)
        // Match found! Direction: X_NEGATIVE and rotation R_180

        Coordinates translation = new Coordinates(68, -1246, -43).inverse();
        FacingDirection direction = FacingDirection.X_NEGATIVE;
        Rotation rotation = Rotation.R_180;

        assertThat(transform(new Coordinates(686,422,578), direction, rotation, translation)).isEqualTo(new Coordinates(-618,-824,-621));
        assertThat(transform(new Coordinates(605,423,415), direction, rotation, translation)).isEqualTo(new Coordinates(-537,-823,-458));
        assertThat(transform(new Coordinates(515,917,-361), direction, rotation, translation)).isEqualTo(new Coordinates(-447,-329,318));
        assertThat(transform(new Coordinates(-336,658,858), direction, rotation, translation)).isEqualTo(new Coordinates(404,-588,-901));
        assertThat(transform(new Coordinates(-476,619,847), direction, rotation, translation)).isEqualTo(new Coordinates(544,-627,-890));
        assertThat(transform(new Coordinates(-460,603,-452), direction, rotation, translation)).isEqualTo(new Coordinates(528,-643,409));
        assertThat(transform(new Coordinates(729,430,532), direction, rotation, translation)).isEqualTo(new Coordinates(-661,-816,-575));
        assertThat(transform(new Coordinates(-322,571,750), direction, rotation, translation)).isEqualTo(new Coordinates(390,-675,-793));
        assertThat(transform(new Coordinates(-355,545,-477), direction, rotation, translation)).isEqualTo(new Coordinates(423,-701,434));
        assertThat(transform(new Coordinates(413,935,-424), direction, rotation, translation)).isEqualTo(new Coordinates(-345,-311,381));
        assertThat(transform(new Coordinates(-391,539,-444), direction, rotation, translation)).isEqualTo(new Coordinates(459,-707,401));
        assertThat(transform(new Coordinates(553,889,-390), direction, rotation, translation)).isEqualTo(new Coordinates(-485,-357,347));
    }

    private Coordinates transform(Coordinates coordinates, FacingDirection direction, Rotation rotation, Coordinates translation) {
        return coordinates.changeDirection(direction).rotate(rotation).translate(translation);
    }
}