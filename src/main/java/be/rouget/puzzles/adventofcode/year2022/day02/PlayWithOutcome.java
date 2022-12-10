package be.rouget.puzzles.adventofcode.year2022.day02;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static be.rouget.puzzles.adventofcode.year2022.day02.Outcome.*;
import static be.rouget.puzzles.adventofcode.year2022.day02.Shape.*;

public record PlayWithOutcome(Shape theirPlay, Shape myPlay, Outcome outcome) {

    private static final Pattern ROUND_PATTERN = Pattern.compile("([ABC]) ([XYZ])");

    public long getScore() {
        return myPlay.getScore() + outcome.getScore();
    }

    public static PlayWithOutcome parseForPart1(String input) {
        Matcher matcher = ROUND_PATTERN.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Cannot parse input:" + input);
        }
        Shape play1 = Shape.fromPlay1(matcher.group(1));
        Shape play2 = Shape.fromPlay2(matcher.group(2));
        return outcomes.stream()
                .filter(pwo -> pwo.theirPlay() == play1 && pwo.myPlay() == play2)
                .findFirst().orElseThrow( () -> new IllegalStateException("No match found for round: " + input));
    }

    public static PlayWithOutcome parseForPart2(String input) {
        Matcher matcher = ROUND_PATTERN.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Cannot parse input:" + input);
        }
        Shape play1 = Shape.fromPlay1(matcher.group(1));
        Outcome outcome = Outcome.fromCode(matcher.group(2));
        return outcomes.stream()
                .filter(pwo -> pwo.theirPlay() == play1 && pwo.outcome() == outcome)
                .findFirst().orElseThrow( () -> new IllegalStateException("No match found for round: " + input));

    }

    private static final List<PlayWithOutcome> outcomes = List.of(
            new PlayWithOutcome(ROCK, ROCK, DRAW),
            new PlayWithOutcome(ROCK, PAPER, WIN),
            new PlayWithOutcome(ROCK, SCISSORS, LOSS),
            new PlayWithOutcome(PAPER, ROCK, LOSS),
            new PlayWithOutcome(PAPER, PAPER, DRAW),
            new PlayWithOutcome(PAPER, SCISSORS, WIN),
            new PlayWithOutcome(SCISSORS, ROCK, WIN),
            new PlayWithOutcome(SCISSORS, PAPER, LOSS),
            new PlayWithOutcome(SCISSORS, SCISSORS, DRAW)
    );
}
