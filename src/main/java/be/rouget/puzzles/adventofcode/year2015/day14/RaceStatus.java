package be.rouget.puzzles.adventofcode.year2015.day14;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RaceStatus {
    private Reindeer reindeer;
    private int distance;
    private int score;

    public void updateDistance(int raceTime) {
        this.distance = reindeer.computeDistance(raceTime);
    }

    public void addOnePoint() {
        this.score += 1;
    }
}
