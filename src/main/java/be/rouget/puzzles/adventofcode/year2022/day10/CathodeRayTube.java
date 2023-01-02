package be.rouget.puzzles.adventofcode.year2022.day10;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import be.rouget.puzzles.adventofcode.util.map.Position;
import be.rouget.puzzles.adventofcode.util.map.RectangleMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


public class CathodeRayTube {

    private static final Logger LOG = LogManager.getLogger(CathodeRayTube.class);
    private final List<Instruction> instructions;

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(CathodeRayTube.class);
        CathodeRayTube aoc = new CathodeRayTube(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public CathodeRayTube(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        instructions = input.stream()
                .map(Instruction::parse)
                .toList();
    }

    public long computeResultForPart1() {
        int sumOfSignalStrengths = 0;
        VideoCpu cpu = new VideoCpu(instructions);
        do {
            int duringCycle =  cpu.getExecutedCycles() + 1;
            LOG.info("During cycle: {} - Register value: {}", duringCycle, cpu.getRegisterX());
            if (duringCycle == 20 || (duringCycle - 20) % 40 == 0) {
                sumOfSignalStrengths = sumOfSignalStrengths + (duringCycle * cpu.getRegisterX());
                LOG.info("**** During cycle {}: sum is now {}", duringCycle, sumOfSignalStrengths);
            }
        }
        while (cpu.executeCycle());
        return sumOfSignalStrengths;
    }
    
    public long computeResultForPart2() {
        RectangleMap<Pixel> screen = new RectangleMap<>(40, 6, Pixel.NONE);
        VideoCpu cpu = new VideoCpu(instructions);
        do {
            int pixelX = cpu.getExecutedCycles() % screen.getWidth();
            int pixelY = cpu.getExecutedCycles() / screen.getWidth();
            Position positionOnScreen = new Position(pixelX, pixelY);
            Pixel pixel = Math.abs(cpu.getRegisterX()-pixelX) <= 1 ? Pixel.LIT : Pixel.DARK;
            screen.setElementAt(positionOnScreen, pixel);
            LOG.info("Cycle: {} - Register value: {} - Position: {} - Pixel: {}", cpu.getExecutedCycles(), cpu.getRegisterX(), positionOnScreen, pixel);
        }
        while (cpu.executeCycle());
        LOG.info("Screen: ----\n{}\n----", screen);
        return -1;
    }
}