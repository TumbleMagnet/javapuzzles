package be.rouget.puzzles.adventofcode.year2016.day17;

import be.rouget.puzzles.adventofcode.util.map.Position;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import jakarta.xml.bind.DatatypeConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class TwoStepsForward {

    private static final Logger LOG = LogManager.getLogger(TwoStepsForward.class);
    @SuppressWarnings("SpellCheckingInspection")
    private static final String INPUT = "vkjiggvb";
    private static final Position TARGET_POSITION = new Position(3, 3);

    private static MessageDigest md;
    private final String input;

    public static void main(String[] args) {
        TwoStepsForward aoc = new TwoStepsForward(INPUT);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public TwoStepsForward(String input) {
        this.input = input;
        LOG.info("Input is: {}", this.input);
    }

    public String computeResultForPart1() {
        Deque<State> states = new ArrayDeque<>();
        states.addLast(new State(new Position(0, 0), List.of()));

        while (!states.isEmpty()) {
            State state = states.removeFirst();
            List<State> newStates = possibleMoves(state);
            for (State newState : newStates) {
                if (newState.getPosition().equals(TARGET_POSITION)) {
                    return printPath(newState.getMoves());
                } else {
                    states.addLast(newState);
                }
            }
        }
        throw new IllegalStateException("Did not find a path to target!");
    }

    public long computeResultForPart2() {
        Deque<State> states = new ArrayDeque<>();
        states.addLast(new State(new Position(0, 0), List.of()));
        int longestPath = 0;

        while (!states.isEmpty()) {
            State state = states.removeFirst();
            List<State> newStates = possibleMoves(state);
            for (State newState : newStates) {
                if (newState.getPosition().equals(TARGET_POSITION)) {
                    longestPath = Math.max(longestPath, newState.getMoves().size());
                } else {
                    states.addLast(newState);
                }
            }
        }
        return longestPath;
    }

    private String printPath(List<Move> moves) {
        return moves.stream()
                .map(Move::getCode)
                .collect(Collectors.joining());
    }

    private List<State> possibleMoves(State state) {
        // Find which directions are open based on the hash
        List<Move> openMoves = openMoves(state.getMoves());

        // Filter moves which are not possible (going out of map)
        return openMoves.stream()
                .map(move -> move(state, move))
                .filter(s -> isInMap(s.getPosition()))
                .collect(Collectors.toList());
    }

    private List<Move> openMoves(List<Move> moves) {

        // Prepare input for hashing
        String hashInput = this.input + printPath(moves);

        // Hashing
        String hashed = md5Hash(hashInput);

        // Convert hash response into moves
        return hashToOpenMoves(hashed);
    }

    @VisibleForTesting
    protected static List<Move> hashToOpenMoves(String hashed) {
        List<Move> openMoves = Lists.newArrayList();
        if (isOpen(hashed.charAt(0))) {
            openMoves.add(Move.UP);
        }
        if (isOpen(hashed.charAt(1))) {
            openMoves.add(Move.DOWN);
        }
        if (isOpen(hashed.charAt(2))) {
            openMoves.add(Move.LEFT);
        }
        if (isOpen(hashed.charAt(3))) {
            openMoves.add(Move.RIGHT);
        }
        return openMoves;
    }

    private static boolean isOpen(char c) {
        return c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f';
    }

    private static boolean isInMap(Position position) {
        return position.getX() >= 0 && position.getX() <= 3
                && position.getY() >= 0 && position.getY() <= 3;
    }

    private Position move(Position current, Move move) {
        switch (move) {
            case UP: return new Position(current.getX(), current.getY()-1);
            case DOWN: return new Position(current.getX(), current.getY()+1);
            case LEFT: return new Position(current.getX()-1, current.getY());
            case RIGHT: return new Position(current.getX()+1, current.getY());
            default:
                throw new IllegalStateException("Unknown move " + move);
        }
    }

    private State move(State state, Move move) {
        Position newPosition = move(state.getPosition(), move);
        List<Move> newMoves = Lists.newArrayList(state.getMoves());
        newMoves.add(move);
        return new State(newPosition, newMoves);
    }

    public static String md5Hash(String input) {
        if (md == null) {
            try {
                md = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
        md.update(input.getBytes());
        return DatatypeConverter.printHexBinary(md.digest()).toLowerCase();
    }
}