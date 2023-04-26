package be.rouget.puzzles.adventofcode.year2022.day19;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Blueprint(int index, Quantity oreRobotCost, Quantity clayRobotCost, Quantity obsidianRobotCost, Quantity geodeRobotCost) {

    public Quantity costForRobot(Mineral mineral) {
        return switch (mineral) {
            case ORE -> oreRobotCost;
            case CLAY -> clayRobotCost;
            case OBSIDIAN -> obsidianRobotCost;
            case GEODE -> geodeRobotCost;
        };
    }
    
    public boolean isOneAdditionalRobotUseful(Quantity robots, Mineral mineral) {
        
        // Always useful to build a geode robot
        if (mineral == Mineral.GEODE) {
            return true;
        }
        
        // Other minerals can only be used to build robots and we can only build one robot per turn.
        // It is not useful to build more robots for a mineral than the maximum cost of this robot for any mineral
        List<Quantity> costs = List.of(oreRobotCost, clayRobotCost, obsidianRobotCost, geodeRobotCost);
        int maxCostForMineral = costs.stream()
                .mapToInt(quantity -> Math.abs(quantity.getQuantityForMineral(mineral)))
                .max()
                .orElse(0);
        return robots.getQuantityForMineral(mineral) < maxCostForMineral;
    }

    public static Blueprint parse(String input) {
        // Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 4 ore. Each obsidian robot costs 4 ore and 5 clay. Each geode robot costs 3 ore and 7 obsidian.
        Pattern pattern = Pattern.compile("Blueprint (\\d+): Each ore robot costs (.*). Each clay robot costs (.*). Each obsidian robot costs (.*) and (.*). Each geode robot costs (.*) and (.*).");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Cannot parse input: " + input);
        }
        int index = Integer.parseInt(matcher.group(1));
        Quantity oreRobotQuantity = quantity(matcher.group(2));
        Quantity clayRobotQuantity = quantity(matcher.group(3));
        Quantity obsidianRobotQuantity1 = quantity(matcher.group(4));
        Quantity obsidianRobotQuantity2 = quantity(matcher.group(5));
        Quantity geodeRobotQuantity1 = quantity(matcher.group(6));
        Quantity geodeRobotQuantity2 = quantity(matcher.group(7));
        return new Blueprint(index,
                oreRobotQuantity.inverse(),
                clayRobotQuantity.inverse(),
                obsidianRobotQuantity1.add(obsidianRobotQuantity2).inverse(),
                geodeRobotQuantity1.add(geodeRobotQuantity2).inverse()
        );
    }
    
    private static Quantity quantity(String quantityString) {
        String[] tokens = quantityString.split(" ");
        int quantity = Integer.parseInt(tokens[0]);
        Mineral mineral = Mineral.valueOf(tokens[1].toUpperCase());
        int oreQuantity = 0;
        int clayQuantity = 0;
        int obsidianQuantity = 0;
        int geodeQuantity = 0;
        switch (mineral) {
            case ORE -> oreQuantity += quantity;
            case CLAY -> clayQuantity += quantity;
            case OBSIDIAN -> obsidianQuantity += quantity;
            case GEODE -> geodeQuantity += quantity;
        }
        return new Quantity(oreQuantity, clayQuantity, obsidianQuantity, geodeQuantity);
    }
}
