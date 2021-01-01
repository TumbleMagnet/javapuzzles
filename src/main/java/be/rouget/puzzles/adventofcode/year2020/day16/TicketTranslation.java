package be.rouget.puzzles.adventofcode.year2020.day16;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TicketTranslation {

    private static final String YEAR = "2020";
    private static final String DAY = "16";

    private static final Logger LOG = LogManager.getLogger(TicketTranslation.class);

    private final List<String> input;

    private final List<FieldRule> rules;
    private final Ticket myTicket;
    private final List<Ticket> otherTickets;

    public TicketTranslation(List<String> input) {
        this.input = input;
        LOG.info("Input has {} lines...", input.size());

        // Extract Rules
        rules = Lists.newArrayList();
        for (int i = 0; i < 20; i++) {
            rules.add(FieldRule.fromInput(input.get(i)));
        }
        LOG.info("Loaded {} rules...", rules.size());

        // Extract my ticket
        myTicket = Ticket.fromInput(input.get(22));

        // Extract other tickets
        otherTickets = Lists.newArrayList();
        for (int i = 25; i < input.size(); i++) {
            Ticket ticket = Ticket.fromInput(input.get(i));
            otherTickets.add(ticket);
        }
        LOG.info("Loaded {} other tickets...", otherTickets.size());
    }

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        TicketTranslation aoc = new TicketTranslation(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public long computeResultForPart1() {
        return otherTickets.stream()
                .map(Ticket::getValues)
                .flatMap(Collection::stream)
                .filter(value -> !Ticket.matchesAtLeastOneRule(value, rules))
                .mapToInt(Integer::intValue)
                .sum();
    }

    public long computeResultForPart2() {
        List<Ticket> validTickets = otherTickets.stream().filter(t -> t.isValid(rules)).collect(Collectors.toList());

        List<FieldRule> orderedRules = exploreRules(List.of(), rules, validTickets);
        orderedRules.forEach(r-> LOG.info("Rule {}", r));

        // Compute indexes of fields starting with "departure"
        List<Integer> departureIndexes = Lists.newArrayList();
        for (int i = 0; i < rules.size(); i++) {
            if (orderedRules.get(i).getName().startsWith("departure")) {
                departureIndexes.add(i);
            }
        }
        LOG.info("Indexes: {}", departureIndexes);

        // Compute product of values at corresponding indexes in myTicket
        long result = 1L;
        for (Integer index : departureIndexes) {
            result *= myTicket.getValues().get(index);
        }
        return result;
    }

    private List<FieldRule> exploreRules(List<FieldRule> candidates, List<FieldRule> remainingRules, List<Ticket> validTickets) {

        int currentIndex = candidates.size();
        for (FieldRule rule : remainingRules) {
            if (matchesAllTickets(rule, validTickets, currentIndex)) {

                // Current remaining rule is a candidate for next rule

                List<FieldRule> updatedCandidates = Lists.newArrayList(candidates);
                updatedCandidates.add(rule);

                List<FieldRule> updatedRemainingRules = Lists.newArrayList(remainingRules);
                updatedRemainingRules.remove(rule);

                if (updatedRemainingRules.isEmpty()) {
                    // No more remaining rules so we found the solution
                    return updatedCandidates;
                }

                // Explore to see if a solution can be found for remaining rules
                List<FieldRule> solution = exploreRules(updatedCandidates, updatedRemainingRules, validTickets);
                if (solution != null) {
                    // A solution was found return it
                    return solution;
                }
            }
        }
        // No solution was found for this configuration, go back
        return null;
    }

    private boolean matchesAllTickets(FieldRule rule, List<Ticket> validTickets, int fieldIndex) {
        for (Ticket ticket : validTickets) {
            if (!ticket.matchesField(rule, fieldIndex)) {
                return false;
            }
        }
        return true;
    }
}