package be.rouget.puzzles.adventofcode.year2023.day19;

import be.rouget.puzzles.adventofcode.util.AocStringUtils;
import be.rouget.puzzles.adventofcode.util.Range;
import be.rouget.puzzles.adventofcode.util.SolverUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


public class Aplenty {

    private static final Logger LOG = LogManager.getLogger(Aplenty.class);
    private final List<Workflow> workflows;
    private final List<Part> parts;

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(Aplenty.class);
        Aplenty aoc = new Aplenty(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public Aplenty(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        List<List<String>> paragraphs = AocStringUtils.extractParagraphs(input);
        if (paragraphs.size() != 2) {
            throw new IllegalArgumentException("Found unexpected number of paragraphs: " + paragraphs.size());
        }
        workflows = paragraphs.get(0).stream()
                .map(Workflow::parse)
                .toList();
        LOG.info("Parsed {} workflows...", workflows.size());
        parts = paragraphs.get(1).stream()
                .map(Part::parse)
                .toList();
        LOG.info("Parsed {} parts...", parts.size());
    }

    public long computeResultForPart1() {
        return parts.stream()
                .mapToInt(part -> OutcomeType.PART_ACCEPTED.equals(sortPart(part)) ? part.computeRating() : 0)
                .sum();
    }

    private OutcomeType sortPart(Part part) {
        Workflow workflow = getWorkflow("in");
        RuleOutcome outcome = workflow.evaluate(part);
        while (outcome.type() == OutcomeType.MOVE_TO_WORKFLOW) {
            workflow = getWorkflow(outcome.targetName());
            outcome = workflow.evaluate(part);
        }
        return outcome.type();
    }

    private Workflow getWorkflow(String targetName) {
        return workflows.stream()
                .filter(w -> w.name().equals(targetName))
                .findFirst()
                .orElseThrow( () -> new IllegalArgumentException("Could not find workflow with name: " + targetName));
    }


    public long computeResultForPart2() {
        Workflow workflow = getWorkflow("in");
        PartRange range = new PartRange(new Range(0, 4000), new Range(0, 4000), new Range(0, 4000), new Range(0, 4000));
        return computeAcceptedCombinations(workflow, range);
    }

    private long computeAcceptedCombinations(Workflow workflow, PartRange range) {
        LOG.info("=> Workflow {} and range {}...", workflow.name(), range);
        long result = 0L;
        PartRange currentRange = range;
        for (Rule rule : workflow.rules()) {
            if (currentRange != null) {
                if (rule.condition() == null) {
                    // No condition, compute combinations for current range
                    result += computeAcceptedCombinations(rule.outcome(), currentRange);
                } else {
                    // Rule has a condition, compute conditions for range matching the condition
                    PartRange matchingRange = currentRange.applyCondition(rule.condition());
                    result += computeAcceptedCombinations(rule.outcome(), matchingRange);

                    // Continue with other rules and remaining range
                    currentRange = currentRange.applyOppositeCondition(rule.condition());
                }
            }
        }
        LOG.info("<= Workflow {} and range {}: {}", workflow.name(), range, result);
        return result;
    }

    private long computeAcceptedCombinations(RuleOutcome ruleOutcome, PartRange range) {
        if (range == null) {
            return 0;
        }
        if (ruleOutcome.type() == OutcomeType.PART_ACCEPTED) {
            // Returns all combinations for the range
            return range.countCombinations();
        } else if (ruleOutcome.type() == OutcomeType.PART_REJECTED) {
            // The range is rejected
            return 0;
        } else {
            // Move to another workflow with current range
            return computeAcceptedCombinations(getWorkflow(ruleOutcome.targetName()), range);
        }
    }
}