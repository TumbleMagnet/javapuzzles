package be.rouget.puzzles.adventofcode.year2019;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AoC2019Day14 {

    private static Logger LOG = LogManager.getLogger(AoC2019Day14.class);
    private static String FUEL ="FUEL";
    private static String ORE ="ORE";

    private static Map<Chemical, ChemicalQuantity> extraQuantities = Maps.newHashMap();

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines("aoc_2019_day14_input.txt");
        LOG.info("Result is " + computeResult(input));
    }

    public static long computeResult(List<String> input) {

        List<Reaction> reactions = input.stream()
                .map(line -> new Reaction(line))
                .collect(Collectors.toList());
        Map<Chemical, Reaction> reactionsByOutput = reactions.stream()
                .collect(Collectors.toMap(Reaction::getOutputChemical, Function.identity()));

        computeComplexity(Chemicals.fromName("FUEL"), reactionsByOutput);
//        Chemicals.chemicalsByComplexityDesc().stream().forEach(System.out::println);

        long fuelCount = 0;
        long oreCount = 1000000000000L;
        while (true) {
            List<ChemicalQuantity> target = Lists.newArrayList(new ChemicalQuantity(1, Chemicals.fromName(FUEL)));
            long result = decompose(target, reactionsByOutput);
            if (oreCount >= result) {
                fuelCount++;
                oreCount = oreCount - result;
//                System.out.println("Fuel count: " + fuelCount + " - oreCount: " + oreCount);
//                System.out.println("Extra ingredients: " + extraQuantities.values());
                if (fuelCount % 1000L == 0L) {
                    System.out.println("Fuel count: " + fuelCount + " - oreCount: " + oreCount);
                }
            }
            else {
                return fuelCount;
            }
        }
    }

    public static long decompose(List<ChemicalQuantity> quantities, Map<Chemical, Reaction> reactionsByOutput) {

//        LOG.info("Decomposing " + quantities);

        if (quantities.size() ==1) {
            ChemicalQuantity lastQuantity = quantities.get(0);
            if (lastQuantity.getChemical().equals(Chemicals.fromName(ORE))) {
                return lastQuantity.getQuantity();
            }
        }

        // Decompose most complex
        ChemicalQuantity mostComplex = quantities.stream()
                .max(Comparator.comparing(q -> q.getChemical().getComplexity()))
                .orElseThrow(() -> new IllegalStateException("Could not find most complex element"));
//        LOG.info("Most complex is " + mostComplex);

        List<ChemicalQuantity> others = quantities.stream().filter(q -> !q.equals(mostComplex)).collect(Collectors.toList());
        List<ChemicalQuantity> newIngredients = ingredientsFor(mostComplex, reactionsByOutput);

        List<ChemicalQuantity> newQuantities = add(others, newIngredients);

        // Remove extra from previous reactions
        newQuantities = removeExtra(newQuantities);

        return decompose(newQuantities, reactionsByOutput);
    }

    private static List<ChemicalQuantity> removeExtra(List<ChemicalQuantity> newQuantities) {
        List<ChemicalQuantity> remaining = Lists.newArrayList();
        for (ChemicalQuantity quantity: newQuantities) {
            ChemicalQuantity extra = extraQuantities.get(quantity.getChemical());
            if (extra != null) {
                long extraToUse = Math.min(quantity.getQuantity(), extra.getQuantity());
                if (quantity.getQuantity() - extraToUse > 0) {
                    remaining.add(new ChemicalQuantity(quantity.getQuantity() - extraToUse, quantity.getChemical()));
                }
                if (extra.getQuantity() - extraToUse > 0) {
                    extraQuantities.put(quantity.getChemical(), new ChemicalQuantity(extra.getQuantity() - extraToUse, quantity.getChemical()));
                }
                else {
                    extraQuantities.remove(quantity.getChemical());
                }
            }
            else {
                remaining.add(quantity);
            }
        }
        return remaining;
    }

    private static List<ChemicalQuantity> ingredientsFor(ChemicalQuantity quantity, Map<Chemical, Reaction> reactionsByOutput) {

        Reaction reaction = reactionsByOutput.get(quantity.getChemical());
        if (reaction == null) {
            throw new IllegalStateException("No reaction found for producing" + quantity.getChemical());
        }

        long target = quantity.getQuantity();
        long perReaction = reaction.getOutput().getQuantity();
        long numberOfReaction = target / perReaction;
        if (numberOfReaction * perReaction < target) {
            numberOfReaction++;
        }

        long extraQuantity = numberOfReaction * perReaction - target;
        addExtra(extraQuantity, quantity.getChemical());

        List<ChemicalQuantity> ingredients = Lists.newArrayList();
        for (ChemicalQuantity input: reaction.getInput()) {
            ingredients.add(new ChemicalQuantity(numberOfReaction * input.getQuantity(), input.getChemical()));
        }
        return ingredients;
    }

    private static void addExtra(long extraQuantity, Chemical c) {
        if (extraQuantity == 0) {
            return;
        }
        long totalExtra = extraQuantity;
        ChemicalQuantity quantity = extraQuantities.get(c);
        if (quantity != null) {
            totalExtra += quantity.getQuantity();
        }
        extraQuantities.put(c, new ChemicalQuantity(totalExtra, c));
    }

    public static List<ChemicalQuantity> add(List<ChemicalQuantity> quantities1, List<ChemicalQuantity> quantities2) {

        Map<Chemical, ChemicalQuantity> map = quantities1.stream().collect(Collectors.toMap(ChemicalQuantity::getChemical, Function.identity()));
        for (ChemicalQuantity quantity: quantities2) {
            ChemicalQuantity existingQuantity = map.get(quantity.getChemical());
            map.put(quantity.getChemical(),
                    new ChemicalQuantity(
                            quantity.getQuantity() + (existingQuantity != null? existingQuantity.getQuantity() : 0),
                            quantity.getChemical()));
        }

        return map.values().stream().collect(Collectors.toList());
    }

    private static void computeComplexity(Chemical c, Map<Chemical, Reaction> reactionsByOutput) {
        if (c.getComplexity() != null) {
            return;
        }
        if (c.getName().equals(ORE)) {
            c.setComplexity(0);
            return;
        }

        // List chemicals needed to build c
        List<Chemical> chemicals = reactionsByOutput.get(c).getInput().stream().map(ChemicalQuantity::getChemical).collect(Collectors.toList());
        chemicals.stream().forEach(child -> computeComplexity(child, reactionsByOutput));

        // Complexity of element is 1 + max of the children complexity
        c.setComplexity(chemicals.stream().mapToInt(Chemical::getComplexity).max().orElseThrow(() -> new IllegalStateException("Cannot compute max of children")) + 1);
    }

    public static class Reaction {
        private List<ChemicalQuantity> input = new ArrayList<>();
        private ChemicalQuantity output;

        public Reaction(String formula) {
            String[] formulaParts = formula.split(" => ");
            if (formulaParts.length != 2) {
                throw new IllegalStateException("Could not parse formula " + formula);
            }
            input = Arrays.stream(formulaParts[0].split(", ")).map(ChemicalQuantity::new).collect(Collectors.toList());
            output = new ChemicalQuantity(formulaParts[1]);
        }

        public List<ChemicalQuantity> getInput() {
            return Collections.unmodifiableList(input);
        }

        public ChemicalQuantity getOutput() {
            return output;
        }

        @Override
        public String toString() {
            return input + " => " + output;
        }

        public Chemical getOutputChemical() {
            return output.getChemical();
        }
    }

    public static class ChemicalQuantity {
        private long quantity;
        private Chemical chemical;

        public ChemicalQuantity(long quantity, Chemical chemical) {
            this.quantity = quantity;
            this.chemical = chemical;
        }

        public ChemicalQuantity(String formulaQuantity) {
            String[] tokens = formulaQuantity.trim().split(" ");
            this.quantity = Long.valueOf(tokens[0]);
            this.chemical = Chemicals.fromName(tokens[1]);
        }

        public long getQuantity() {
            return quantity;
        }

        public Chemical getChemical() {
            return chemical;
        }

        @Override
        public String toString() {
            return quantity + " " + chemical.getName();
        }
    }

    public static class Chemical {
        private String name;
        private Integer complexity = null;

        public Chemical(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public Integer getComplexity() {
            return complexity;
        }

        public void setComplexity(Integer complexity) {
            this.complexity = complexity;
        }

        @Override
        public String toString() {
            return "Chemical{" +
                    "name='" + name + '\'' +
                    ", complexity=" + complexity +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Chemical chemical = (Chemical) o;
            return Objects.equal(name, chemical.name);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(name);
        }

    }

    public static class Chemicals {
        private static Map<String, Chemical> chemicals = Maps.newHashMap();

        public static Chemical fromName(String name) {
            Chemical chemical = chemicals.get(name);
            if (chemical == null) {
                chemical = new Chemical(name);
                chemicals.put(name, chemical);
            }
            return chemical;
        }

        public static  List<Chemical> chemicalsByComplexityDesc() {
            List<Chemical> list = Lists.newArrayList(chemicals.values());
            Collections.sort(list, new ChemicalsByComplexityDesc());
            return list;
        }
    }

    public static class ChemicalsByComplexityDesc implements Comparator<Chemical> {

        @Override
        public int compare(Chemical o1, Chemical o2) {
            return -o1.getComplexity().compareTo(o2.getComplexity());
        }
    }
}