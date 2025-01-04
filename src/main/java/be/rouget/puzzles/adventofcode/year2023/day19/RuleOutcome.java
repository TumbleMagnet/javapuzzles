package be.rouget.puzzles.adventofcode.year2023.day19;

public record RuleOutcome(OutcomeType type, String targetName) {

    public static RuleOutcome parse(String input) {
        return switch (input) {
            case "A" -> partAccepted();
            case "R" -> partRejected();
            default -> moveToWorflow(input);
        };
    }

    public static RuleOutcome moveToWorflow(String input) {
        return new RuleOutcome(OutcomeType.MOVE_TO_WORKFLOW, input);
    }

    public static RuleOutcome partRejected() {
        return new RuleOutcome(OutcomeType.PART_REJECTED, null);
    }

    public static RuleOutcome partAccepted() {
        return new RuleOutcome(OutcomeType.PART_ACCEPTED, null);
    }
}
