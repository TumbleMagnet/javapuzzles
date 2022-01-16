package be.rouget.puzzles.adventofcode.year2021.day19;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Value;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class BeaconMap {

    private static final Logger LOG = LogManager.getLogger(BeaconMap.class);

    private final Set<LocalizedScanner> scanners = Sets.newHashSet();
    private final Set<Coordinates> beacons = Sets.newHashSet();

    private Set<ScannerMatch> failedMatches = Sets.newHashSet();

    public boolean addScanner(Scanner scanner) {

        LOG.debug("Trying to add " + scanner.getName());

        // If first scanner, take it as reference
        if (scanners.isEmpty()) {
            addScanner(new LocalizedScanner(new Coordinates(0, 0, 0), scanner));
            return true;
        }

        // Try to find an existing scanner with 12 matching beacons
        for (LocalizedScanner localizedScanner: scanners) {
            Optional<LocalizedScanner> optionalLocalizedScanner = localizedScanner.getScanner().checkPairing(scanner);
            if (optionalLocalizedScanner.isPresent()) {
                LocalizedScanner newLocalizedScanner = optionalLocalizedScanner.get();
                Coordinates relativeLocation = newLocalizedScanner.getLocation();
                Coordinates scannerLocation = relativeLocation.add(localizedScanner.getLocation());
                addScanner(new LocalizedScanner(scannerLocation, newLocalizedScanner.getScanner()));
                return true;
            }
        }

        // Scanner could not be paired with any of scanners already in map
        return false;
    }

    private void addScanner(LocalizedScanner localizedScanner) {
        LOG.info("Adding {} at location {}", localizedScanner.getScanner().getName(), localizedScanner.getLocation());
        scanners.add(localizedScanner);

        // Compute coordinates of beacons (relative to scanner) so that they are relative to map referential
        List<Coordinates> newBeacons = localizedScanner.getScanner().getBeacons().stream()
                .map(beacon -> beacon.add(localizedScanner.getLocation()))
                .collect(Collectors.toList());
        beacons.addAll(newBeacons);

        LOG.info("Map now contains {} scanners and {} beacons", scanners.size(), beacons.size());
    }

    public int getNumberOfBeacons() {
        return beacons.size();
    }

    public int getMaxDistanceBetweenScanners() {
        int maxDistance = 0;
        List<LocalizedScanner> scannerList = Lists.newArrayList(scanners);
        for (int i = 0; i < scannerList.size() - 1; i++) {
            for (int j = 1; j < scannerList.size(); j++) {
                maxDistance = Math.max(maxDistance, scannerList.get(i).getLocation().distanceFrom(scannerList.get(j).getLocation()));
            }
        }
        return maxDistance;
    }

    @Value
    private static class ScannerMatch {
        Scanner scanner1, scanner2;
    }
}
