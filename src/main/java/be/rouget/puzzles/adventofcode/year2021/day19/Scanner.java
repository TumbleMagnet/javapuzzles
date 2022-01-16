package be.rouget.puzzles.adventofcode.year2021.day19;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Value
public class Scanner {

    private static final Logger LOG = LogManager.getLogger(Scanner.class);
    public static final int BEACON_MATCH_MIN = 12;
    public static final int DISTANCE_MATCH_MIN = BEACON_MATCH_MIN * (BEACON_MATCH_MIN -1) / 2;

    String name;
    List<Coordinates> beacons;
    List<Integer> distances;

    public Scanner(String name, List<Coordinates> beacons) {
        this.name = name;
        this.beacons = beacons;
        this.distances = Lists.newArrayList();
        for (int i = 0; i < this.beacons.size()-1; i++) {
            for (int j = 1; j < this.beacons.size(); j++) {
                this.distances.add(beacons.get(i).distanceFrom(beacons.get(j)));
            }
        }
    }

    public Optional<LocalizedScanner> checkPairing(Scanner other) {
        LOG.debug("Trying to to pair {} with {}", getName(), other.getName());

        // Optimization: check that enough beacon distances match before actually trying to pair both scanners
        if (!distancesMatch(other)) {
            return Optional.empty();
        }

        // Try actual pairing
        for (Scanner orientedOther : other.listOrientations()) {
            for (Coordinates otherBeacon : orientedOther.getBeacons()) {
                for (Coordinates thisBeacon : this.getBeacons()) {

                    // Translate other to align both beacons and see if any orientation matches enough other beacons
                    Coordinates translation = otherBeacon.minus(thisBeacon);
                    Scanner translatedOrientedOther = orientedOther.translate(translation);
                    if (this.matches(translatedOrientedOther)) {

                        // This orientation of the other scanner matches this scanner
                        return Optional.of(new LocalizedScanner(translation.inverse(), orientedOther));
                    }
                }
            }
        }

        // Scanners cannot be paired
        return Optional.empty();
    }

    private boolean distancesMatch(Scanner other) {
        Set<Integer> intersection = Sets.newHashSet(this.distances);
        intersection.retainAll(other.getDistances());
        return intersection.size() > DISTANCE_MATCH_MIN;
    }

    public boolean matches(Scanner other) {
        // This scanner "matches" this other scanner if at least 12 beacons match
        Set<Coordinates> intersection = Sets.newHashSet(this.getBeacons());
        intersection.retainAll(other.getBeacons());
        return intersection.size() >= BEACON_MATCH_MIN;
    }

    public Scanner changeOrientation(FacingDirection newDirection, Rotation rotation) {
        List<Coordinates> newBeacons = beacons.stream()
                .map(c -> c.changeDirection(newDirection).rotate(rotation))
                .collect(Collectors.toList());
        return new Scanner(name, newBeacons);
    }

    public Scanner translate(Coordinates newReference) {
        List<Coordinates> newBeacons = beacons.stream()
                .map(c -> c.translate(newReference))
                .collect(Collectors.toList());
        return new Scanner(name, newBeacons);
    }

    public List<Scanner> listOrientations() {
        List<Scanner> result = Lists.newArrayList();
        for (FacingDirection direction : FacingDirection.values()) {
            for (Rotation rotation : Rotation.values()) {
                result.add(changeOrientation(direction, rotation));
            }
        }
        return result;
    }

    public static List<Scanner> parseScanners(List<String> input) {
        final List<Scanner> scanners;
        String scannerName = null;
        List<Coordinates> beacons = null;
        scanners = Lists.newArrayList();
        for (String line : input) {
            if (line.startsWith("---")) {
                scannerName = parseScannerName(line);
                beacons = Lists.newArrayList();
            } else if (StringUtils.isBlank(line)) {
                Scanner scanner = new Scanner(scannerName, beacons);
                scanners.add(scanner);
                scannerName = null;
                beacons = null;
            } else {
                beacons.add(Coordinates.parse(line));
            }
        }
        if (scannerName != null) {
            Scanner scanner = new Scanner(scannerName, beacons);
            scanners.add(scanner);
        }
        return scanners;
    }

    private static String parseScannerName(String line) {
        Pattern pattern = Pattern.compile("--- (.+) ---");
        Matcher matcher = pattern.matcher(line);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Cannot parse scanner name: " + line);
        }
        return matcher.group(1);
    }
}
