package be.rouget.puzzles.adventofcode.year2019;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import be.rouget.puzzles.adventofcode.year2019.computer.AsciiComputer;
import be.rouget.puzzles.adventofcode.year2019.computer.ComputerState;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Scanner;

public class AoC2019Day25 {

    private static Logger LOG = LogManager.getLogger(AoC2019Day25.class);

    private String input;

    public AoC2019Day25(String input) {
        this.input = input;
    }

    public static void main(String[] args) {

        String input = ResourceUtils.readIntoString("aoc_2019_day25_input.txt");
        AoC2019Day25 aoc = new AoC2019Day25(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public List<String> getDroidScript() {
        return Lists.newArrayList(
                // == Hull Breach ==
                "north",  // == Hallway ==
                "north",  // == Arcade ==
                "north",  // == Crew Quarters ==
                "east",   // == Corridor ==
                "east",   // == Storage ==
                "take loom",
                "west",    // == Corridor ==
                "west",    // == Crew Quarters ==
                "south",   // == Arcade ==
                "west",    // == Observatory ==
                "take mug",
                "east",    // == Arcade ==
                "south",   // == Hallway ==
                "south",   // == Hull Breach ==
                "east",    // == Hot Chocolate Fountain ==
                "take food ration",
                "east",    // == Gift Wrapping Center ==
                "take manifold",
                "east",    // == Navigation ==
                "east",    // == Kitchen ==
                "take jam",
                "west",    // == Navigation ==
                "north",   // == Holodeck ==
                "east",    // == Warp Drive Maintenance ==
                "take spool of cat6",
                "west",    // == Holodeck ==
                "north",   // == Passages ==
                "take fuel cell",
                "south",   // == Holodeck ==
                "south",   // == Navigation ==
                "west",    // == Gift Wrapping Center ==
                "south",   // ==  Sick Bay ==
                "north",   // == Gift Wrapping Center ==
                "west",    // == Hot Chocolate Fountain ==
                "south",   // == Science Lab ==
                "take prime number",
                "north",   // == Hot Chocolate Fountain ==
                "west",    // == Hull Breach ==
                "north",  // == Hallway ==
                "west",   // == Engineering ==
                "north",  // == Stables ==
                "west",   // == Security Checkpoint ==
                "inv"
//                "north",  // == Pressure-Sensitive Floor ==
        );
        // Do not take these object:
        // photons       : It is suddenly completely dark! You are eaten by a Grue!
        // infinite loop : ... never completes! ...
        // molten lava   : The molten lava is way too hot! You melt!
        // escape pod    : You're launched into space! Bye!
        // giant electromagnet : The giant electromagnet is stuck to you.  You can't move!!
    }

    public int computeResultForPart1() {

        // Initialize and start the droid
        AsciiComputer droid = new AsciiComputer(input);
        droid.run(Lists.newArrayList());
        droid.getOutput().forEach(System.out::println);

        // Run the scripted part
        for (String command : getDroidScript()) {
            ComputerState state = runCommand(droid, command);
            if (state == ComputerState.HALTED) {
                return -1;
            }
        }

        // Now try to go trough the "Pressure sensitive floor"
        for (int i = 0; i < 256; i++) {
            adaptInventory(droid, i);
            if (tryPressureSensitiveFloor(droid)) {
                LOG.info("Combination worked: " + i);
                break;
            }
            LOG.info("Combination failed: " + i);
        }
        return -1;

//        while (true) {
//            String command = readCommand();
//            ComputerState state = runCommand(droid, command);
//            if (state == ComputerState.HALTED) {
//                return -1;
//            }
//        }
    }

    private boolean tryPressureSensitiveFloor(AsciiComputer droid) {

        ComputerState state = droid.run(Lists.newArrayList("north"));
        List<String> output = droid.getOutput();
        output.forEach(System.out::println);
        LOG.info("Droid state: " + state);
        boolean backToCheckpoint = output.stream().anyMatch(s -> s.contains("== Security Checkpoint =="));
        return !backToCheckpoint;
    }

    private void adaptInventory(AsciiComputer droid, int combinationIndex) {

        // Drop all items
        for (Item item : Item.values()) {
            runCommand(droid, "drop " + item.getDesc());
        }

        // Pick items to match expected combination
        Item.fromCombinationIndex(combinationIndex).forEach(item -> runCommand(droid, "take " + item.getDesc()));

        // Print expected and actual inventory
        LOG.info("Expecting inventory; " + Item.fromCombinationIndex(combinationIndex));
        runCommand(droid, "inv");
    }

    private ComputerState runCommand(AsciiComputer droid, String command) {
        LOG.info("Command is: ["  + command + "]");
        ComputerState state = droid.run(Lists.newArrayList(command));
        droid.getOutput().forEach(System.out::println);
        LOG.info("Droid state: " + state);
        return state;
    }

    private String readCommand() {
        Scanner in = new Scanner(System.in);
        String command = in.nextLine();
        return command;
    }

    public int computeResultForPart2() {
        return -1;
    }

    public enum Item {
        JAM("jam"),
        LOOM("loom"),
        MUG("mug"),
        SPOOL_OF_CAT6("spool of cat6"),
        PRIME_NUMBER("prime number"),
        FOOD_RATION("food ration"),
        FUEL_CELL("fuel cell"),
        MANIFOLD("manifold");

        private String desc;

        Item(String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }

        public int getBitMask() {
            return 1 << this.ordinal();
        }

        public static List<Item> fromCombinationIndex(int index) {
            List<Item> list = Lists.newArrayList();
            for (Item item : Item.values()) {
                if ((index & item.getBitMask()) > 0) {
                    list.add(item);
                }
            }
            return list;
        }
    }
}

