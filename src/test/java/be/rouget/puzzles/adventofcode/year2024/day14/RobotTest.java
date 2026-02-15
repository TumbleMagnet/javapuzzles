package be.rouget.puzzles.adventofcode.year2024.day14;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RobotTest {

    @Test
    void testParse() {
        assertThat(Robot.parse("p=9,5 v=-3,-3")).usingRecursiveComparison()
                .isEqualTo(new Robot(new Coordinates(9,5), new Velocity(-3,-3)));

        assertThat(Robot.parse("p=-10,-15 v=33,66")).usingRecursiveComparison()
                .isEqualTo(new Robot(new Coordinates(-10,-15), new Velocity(33,66)));
    }

    @Test
    void testMove() {
        Robot robot = Robot.parse("p=2,4 v=2,-3");
        Room room = new Room(11, 7);

        robot.move(room);
        assertThat(robot.getCoordinates()).isEqualTo(new Coordinates(4,1));

        robot.move(room);
        assertThat(robot.getCoordinates()).isEqualTo(new Coordinates(6,5));

        robot.move(room);
        assertThat(robot.getCoordinates()).isEqualTo(new Coordinates(8,2));

        robot.move(room);
        assertThat(robot.getCoordinates()).isEqualTo(new Coordinates(10, 6));

        robot.move(room);
        assertThat(robot.getCoordinates()).isEqualTo(new Coordinates(1,3));
    }
}