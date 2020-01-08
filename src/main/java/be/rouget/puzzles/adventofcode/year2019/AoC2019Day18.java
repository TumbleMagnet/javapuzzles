package be.rouget.puzzles.adventofcode.year2019;

import be.rouget.puzzles.adventofcode.year2019.map.Direction;
import be.rouget.puzzles.adventofcode.year2019.map.Position;
import be.rouget.puzzles.adventofcode.year2019.map.VisitedPosition;
import com.google.common.base.Objects;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static be.rouget.puzzles.adventofcode.util.ResourceUtils.readLines;
import static com.google.common.collect.Lists.newArrayList;

public class AoC2019Day18 {

    private static Logger LOG = LogManager.getLogger(AoC2019Day18.class);

    private VaultMap map;

    public static void main(String[] args) {
        AoC2019Day18 aoc = new AoC2019Day18();

        Stopwatch stopWatch1 = Stopwatch.createStarted();
        int resultPart1 = aoc.computeResultPart1(readLines("aoc_2019_day18_input.txt"));
        stopWatch1.stop();
        LOG.info("Result for part 1 is " + resultPart1 + " in " + stopWatch1.elapsed(TimeUnit.MILLISECONDS) + " ms");

        Stopwatch stopWatch2 = Stopwatch.createStarted();
        int resultPart2 = aoc.computeResultPart2(readLines("aoc_2019_day18_input2.txt"));
        stopWatch2.stop();
        LOG.info("Result for part 2 is " + resultPart2 + " in " + stopWatch2.elapsed(TimeUnit.MILLISECONDS) + " ms");
    }

    public int computeResultPart2(List<String> input) {

        map = new VaultMap(input);
        KeyGraph keyGraph = map.buildGraphOfKeys();

        int numberOfRobots = map.getStarts().size();

        LOG.info("Starting search with " + numberOfRobots + " robots ...");
        Set<MultiKeyVertex> visited = Sets.newHashSet();
        Queue<MultiKeyVertexWithDistance> toVisitQueue = new PriorityQueue<>();
        Map<MultiKeyVertex, MultiKeyVertexWithDistance> distances = Maps.newHashMap();

        int currentMaxNumberOfKey = 0;
        long numberOfVisitedNodes = 0;
        long stopLoggingAt = 100;

        // Start vertex is each robot as its start position (lastKey == null)
        MultiKeyVertex start = new MultiKeyVertex(Lists.newArrayList(null, null, null, null), Sets.newHashSet());
        MultiKeyVertexWithDistance startWithDistance = new MultiKeyVertexWithDistance(start, 0);
        toVisitQueue.add(startWithDistance);

        // Do the search
        while (!toVisitQueue.isEmpty()) {

            MultiKeyVertexWithDistance current = toVisitQueue.remove();
            if (LOG.isDebugEnabled() && numberOfVisitedNodes <= stopLoggingAt) {
                LOG.debug("Node " + numberOfVisitedNodes + ": " + current);
            }

            Set<Key> allKeys = current.getVertex().getAllKeys();
            int numberOfKeys = allKeys.size();
            if (numberOfKeys > currentMaxNumberOfKey) {
                LOG.info("Found path up to " + numberOfKeys + " keys, queue size is " + toVisitQueue.size() + ", number of visited nodes: " + numberOfVisitedNodes);
                currentMaxNumberOfKey = numberOfKeys;
            }

            // If current is a path to all keys, end search and return its distance
            if (numberOfKeys == map.getNumberOfKeys()) {
                return current.getDistance();
            }

            // For each robot
            for (int robot = 0; robot < numberOfRobots; robot++) {

                // Compute distances to neighbours and add them to the nodes to visit
                Key lastKey = current.getVertex().getLastKeyForRobot(robot);
                List<ReachableKey> reachableKeys = getReachableKeysByRobot(robot, lastKey, allKeys);

                for (ReachableKey reachableKey : reachableKeys) {

                    Key newKey = reachableKey.getTarget();

                    List<Key> newLastKeys = Lists.newArrayList(current.getVertex().getLastKeys());
                    newLastKeys.set(robot, newKey);

                    Set<Key> newAllKeys = Sets.newHashSet(allKeys);
                    newAllKeys.add(newKey);

                    MultiKeyVertex neighbour = new MultiKeyVertex(newLastKeys, newAllKeys);

                    if (visited.contains(neighbour)) {
                        continue;
                    }

                    if (!distances.containsKey(neighbour)) {
                        // Neighbour is reached for the first time
                        MultiKeyVertexWithDistance neighbourWithDistance = new MultiKeyVertexWithDistance(neighbour, current.getDistance() + reachableKey.getDistance());
                        if (LOG.isDebugEnabled() && numberOfVisitedNodes <= stopLoggingAt) {
                            LOG.debug("  Adding neighbour " + neighbourWithDistance);
                        }
                        distances.put(neighbour, neighbourWithDistance);
                        toVisitQueue.add(neighbourWithDistance);
                    } else {
                        // Neighbour already has a distance, update it only if new distance is smaller
                        MultiKeyVertexWithDistance existingNeighbour = distances.get(neighbour);
                        MultiKeyVertexWithDistance newNeighbour = new MultiKeyVertexWithDistance(neighbour, current.getDistance() + reachableKey.getDistance());
                        if (newNeighbour.getDistance() < existingNeighbour.getDistance()) {

                            if (LOG.isDebugEnabled() && numberOfVisitedNodes <= stopLoggingAt) {
                                LOG.debug("  Updating neighbour " + newNeighbour + " (previous distance was " + existingNeighbour.getDistance() + ")");
                            }

                            // Make sure the ordered queue is updated based on new distance
                            toVisitQueue.remove(existingNeighbour);
                            toVisitQueue.add(newNeighbour);

                            // Make sure best distance is updated
                            distances.put(neighbour, newNeighbour);

                        }
                    }
                }
            }

            visited.add(current.getVertex());
            numberOfVisitedNodes++;
        }

        throw new IllegalStateException("Search completed without finding path with all keys!");
    }

    private List<ReachableKey> getReachableKeys(Key currentKey, Set<Key> allKeys) {
        return currentKey.getReachableKeys().stream()
                .filter(reachableKey -> reachableKey.isReachableWihKeys(allKeys))
                .filter(reachableKey -> !allKeys.contains(reachableKey.getTarget()))
                .collect(Collectors.toList());
    }

/* Version of getReachableKeys() which does not use the pre-computed distances between keys

    private List<ReachableKey> getReachableKeys(Key currentKey, Set<Key> allKeys) {
        Position startPosition = map.getKeyItem(currentKey).getPosition();
        List<Item> allKeyItems = allKeys.stream().map(k -> map.getKeyItem(k)).collect(Collectors.toList());
        Map<Item, Integer> keysFromStart = findAccessibleKeysFromPosition(startPosition, allKeyItems);
        List<ReachableKey> reachableKeys = Lists.newArrayList();
        for (Map.Entry<Item, Integer> keyEntry : keysFromStart.entrySet()) {
            Key key = map.getKeyGraph().getKey(keyEntry.getKey().getId());
            ReachableKey reachableKey = new ReachableKey(key, keyEntry.getValue(), Sets.newHashSet());
            reachableKeys.add(reachableKey);
        }
        return reachableKeys;
    }
*/


    private List<ReachableKey> getReachableKeysByRobot(int robot, Key currentKey, Set<Key> allKeys) {

        if (currentKey == null) {

            // Return keys reachable by robot from start position
            Position startPosition = map.getStarts().get(robot);
            List<Item> allKeyItems = allKeys.stream().map(k -> map.getKeyItem(k)).collect(Collectors.toList());
            Map<Item, Integer> keysFromStart = findAccessibleKeysFromPosition(startPosition, allKeyItems);
            List<ReachableKey> reachableKeys = Lists.newArrayList();
            for (Map.Entry<Item, Integer> keyEntry : keysFromStart.entrySet()) {
                Key key = map.getKeyGraph().getKey(keyEntry.getKey().getId());
                ReachableKey reachableKey = new ReachableKey(key, keyEntry.getValue(), Sets.newHashSet());
                reachableKeys.add(reachableKey);
            }
            return reachableKeys;
        }
        else {
            return getReachableKeys(currentKey, allKeys);
        }
    }


    public int computeResultPart1(List<String> input) {

        map = new VaultMap(input);
        KeyGraph keyGraph = map.buildGraphOfKeys();

        LOG.info("Starting search...");
        Set<KeyVertex> visited = Sets.newHashSet();
        Queue<KeyVertexWithDistance> toVisitQueue = new PriorityQueue<>();
        Map<KeyVertex, KeyVertexWithDistance> distances = Maps.newHashMap();

        int currentMaxNumberOfKey = 0;
        long numberOfVisitedNodes = 1;
        long stopLoggingAt = 100;

        // Initialize the search with keys reachable from the start position
        Position startPosition = map.getStart();
        Map<Item, Integer> keysFromStart = findAccessibleKeysFromPosition(startPosition, Sets.newHashSet());
        for (Map.Entry<Item, Integer> keyEntry : keysFromStart.entrySet()) {
            Key key = keyGraph.getKey(keyEntry.getKey().getId());
            KeyVertex vertex = new KeyVertex(key, Sets.newHashSet(key));
            KeyVertexWithDistance vertexWithDistance = new KeyVertexWithDistance(vertex, keyEntry.getValue());
            toVisitQueue.add(vertexWithDistance);
            distances.put(vertex, vertexWithDistance);
        }

        // Do the search
        while (!toVisitQueue.isEmpty()) {

            KeyVertexWithDistance current = toVisitQueue.remove();
            if (LOG.isDebugEnabled() && numberOfVisitedNodes <= stopLoggingAt) {
                LOG.debug("Node " + numberOfVisitedNodes + ": " + current);
            }

            Set<Key> allKeys = current.getVertex().getAllKeys();
            int numberOfKeys = allKeys.size();
            if (numberOfKeys > currentMaxNumberOfKey) {
                LOG.info("Found path up to " + numberOfKeys + " keys, queue size is " + toVisitQueue.size() + ", number of visited nodes: " + numberOfVisitedNodes);
                currentMaxNumberOfKey = numberOfKeys;
            }

            // If current is a path to all keys, end search and return its distance
            if (numberOfKeys == map.getNumberOfKeys()) {
                return current.getDistance();
            }

            // Compute distances to neighbours and add them to the nodes to visit
            Key lastKey = current.getVertex().getLastKey();
            List<ReachableKey> reachableKeys = getReachableKeys(lastKey, allKeys);

            for (ReachableKey reachableKey : reachableKeys) {

                Key newKey = reachableKey.getTarget();
                Set<Key> newKeys = Sets.newHashSet(allKeys);
                newKeys.add(newKey);
                KeyVertex neighbour = new KeyVertex(newKey, newKeys);

                if (visited.contains(neighbour)) {
                    continue;
                }

                if (!distances.containsKey(neighbour)) {
                    // Neighbour is reached for the first time
                    KeyVertexWithDistance neighbourWithDistance = new KeyVertexWithDistance(neighbour, current.getDistance() + reachableKey.getDistance());
                    if (LOG.isDebugEnabled() && numberOfVisitedNodes <= stopLoggingAt) {
                        LOG.debug("  Adding neighbour " + neighbourWithDistance);
                    }
                    distances.put(neighbour, neighbourWithDistance);
                    toVisitQueue.add(neighbourWithDistance);
                } else {
                    // Neighbour already has a distance, update it only if new distance is smaller
                    KeyVertexWithDistance existingNeighbour = distances.get(neighbour);
                    KeyVertexWithDistance newNeighbour = new KeyVertexWithDistance(neighbour, current.getDistance() + reachableKey.getDistance());
                    if (newNeighbour.getDistance() < existingNeighbour.getDistance()) {

                        if (LOG.isDebugEnabled() && numberOfVisitedNodes <= stopLoggingAt) {
                            LOG.debug("  Updating neighbour " + newNeighbour + " (previous distance was " + existingNeighbour.getDistance()+ ")");
                        }

                        // Make sure the ordered queue is updated based on new distance
                        toVisitQueue.remove(existingNeighbour);
                        toVisitQueue.add(newNeighbour);

                        // Make sure best distance is updated
                        distances.put(neighbour, newNeighbour);

                    }
                }
            }

            visited.add(current.getVertex());
            numberOfVisitedNodes++;
        }

        throw new IllegalStateException("Search completed without finding path with all keys!");
    }

    private Map<Item, Integer> findAccessibleKeysFromPosition(Position startPosition, Collection<Item> currentKeys) {

        Map<Item, Integer> reachableKeys = Maps.newHashMap();

        // Do a BFS search from the start position
        Queue<VisitedPosition> queue = new LinkedList<>();
        Set<VisitedPosition> visitedNodes = Sets.newHashSet();

        VisitedPosition start = new VisitedPosition(startPosition, 0);
        queue.add(start);
        visitedNodes.add(start);

        while (!queue.isEmpty()) {
            VisitedPosition v = queue.remove();
            List<Position> neighbours = accessibleNeighboursAccordingToMap(v.getPosition(), currentKeys);
            for (Position neighbour : neighbours) {
                VisitedPosition newVisitedPosition = new VisitedPosition(neighbour, v.getDepth() + 1);
                if (!visitedNodes.contains(newVisitedPosition)) {
                    visitedNodes.add(newVisitedPosition);
                    Item item = map.get(neighbour);
                    if (item.getType() == ItemType.KEY) {

                        // On new key stop searching this path
                        if (!currentKeys.contains(item)) {
                            reachableKeys.put(item, newVisitedPosition.getDepth());
                        }
                        // On key already collected, continue searching
                        else {
                            queue.add(newVisitedPosition);
                        }

                    }
                    else {
                        queue.add(newVisitedPosition);
                    }
                }
            }
        }
        return reachableKeys;
    }

    private List<Position> accessibleNeighboursAccordingToMap(Position current, Collection<Item> currentKeys) {
        return Arrays.stream(Direction.values())
                .map(d -> current.move(d))
                .filter(p -> isReachable(p, currentKeys))
                .collect(Collectors.toList());
    }

    private boolean isReachable(Position p, Collection<Item> currentKeys) {
        Item item = map.get(p);
        if (item.getType() == ItemType.WALL) {
            return false;
        }
        if (item.getType() == ItemType.DOOR) {
            // Can go through door only if we have matching key
            return currentKeys.stream().anyMatch(k -> k.getId().equals(item.getId()));
        }
        return true;
    }

    public static class VaultMap {

        private Item[][] map;
        private int width;
        private int height;
        private List<Position> starts = newArrayList();
        private List<Item> doors = newArrayList();
        private List<Item> keys = newArrayList();
        private KeyGraph keyGraph = null;

        public VaultMap(List<String> input) {

            // Initialize dimensions
            width = input.get(0).length();
            height = input.size();
            LOG.info("Map size is " + height + "*" + width);
            map = new Item[height][width];

            // Fill map
            int x = 0;
            int y = 0;
            for (String line : input) {
                for (char c : line.toCharArray()) {
                    Item item = toItem(c, x, y);
                    if (item.getType() == ItemType.START) {
                        starts.add(new Position(x, y));
                    }
                    else if (item.getType() == ItemType.DOOR) {
                        doors.add(item);
                    } else if (item.getType() == ItemType.KEY) {
                        keys.add(item);
                    }
                    map[x][y] = item;
                    x++;
                }
                y++;
                x = 0;
            }
            LOG.info("Starts found at " + starts);
            LOG.info("Found " + doors.size() + " doors: " + doors);
            LOG.info("Found " + keys.size() + " keys: " + keys);
        }

        private Item toItem(char c, int x, int y) {
            Position p = new Position(x, y);
            switch (c) {
                case '#':
                    return Item.wall(p);
                case '.':
                    return Item.empty(p);
                case '@':
                    return Item.start(p);
                default:
                    if (Character.isUpperCase(c)) {
                        return Item.door(p, String.valueOf(c));
                    } else {
                        return Item.key(p, String.valueOf(c));
                    }
            }
        }

        public Position getStart() {
            return starts.get(0);
        }

        public List<Position> getStarts() {
            return starts;
        }

        public Item get(Position position) {
            return map[position.getX()][position.getY()];
        }

        public int getNumberOfKeys() {
            return keys.size();
        }

        public KeyGraph buildGraphOfKeys() {

            KeyGraph graph = new KeyGraph();

            // Compute shortest path between keys
            for (Item key: keys) {
                LOG.debug("Computing paths from " + key);
                for (Path path: computePathsToOtherKeys(key)) {
                    LOG.debug("Path: " + path);
                    graph.addPath(key.getId(), path.getTarget().getId(), path.getDoorNames(), path.getDistance());
                }
            }

            this.keyGraph = graph;

            return graph;
        }

        public KeyGraph getKeyGraph() {
            return keyGraph;
        }

        private List<Path> computePathsToOtherKeys(Item from) {

            Map<Item, Path> pathMap = Maps.newHashMap();

            // Do a BFS search from the start position
            Queue<VisitedPosition> toDo = new LinkedList<>();
            Set<Position> done = Sets.newHashSet();

            VisitedPosition start = new VisitedPosition(from.getPosition(), 0, Sets.newHashSet());
            toDo.add(start);

            while (!toDo.isEmpty()) {

                VisitedPosition current = toDo.remove();
                done.add(current.getPosition());

                Item item = get(current.getPosition());
                Set<String> newDoors = Sets.newHashSet(current.getDoorNames());
                if (item.getType() == ItemType.KEY
                        && !item.equals(from)
                        && !pathMap.containsKey(item)) {
                    pathMap.put(item, new Path(item, current.getDistance(), current.getDoorNames()));

                    // Continue exploring past this key but add a corresponding virtual door
                    // to make sure that paths to further keys "stop" to pick up this key
                    // (otherwise the search algorithm ends up exploring non-optimal paths going to keys further away
                    // without realizing they picked up this key on the way).
                    newDoors.add(item.getId());
                }
                if (item.getType() == ItemType.DOOR) {
                    newDoors.add(item.getId());
                }

                List<Position> neighbours = accessibleNeighboursAccordingToMap(current.getPosition());
                for (Position neighbour : neighbours) {

                    if (!done.contains(neighbour)) {
                        VisitedPosition newVisitedPosition = new VisitedPosition(neighbour, current.getDistance() + 1, newDoors);
                        toDo.add(newVisitedPosition);
                    }
                }
            }

            return pathMap.values().stream().sorted(Comparator.comparing(Path::getDistance)).collect(Collectors.toList());
        }

        private List<Position> accessibleNeighboursAccordingToMap(Position current) {
            return Arrays.stream(Direction.values())
                    .map(d -> current.move(d))
                    .filter(p -> isReachable(p))
                    .collect(Collectors.toList());
        }

        private boolean isReachable(Position p) {
            Item item = get(p);
            if (item.getType() == ItemType.WALL) {
                return false;
            }
            return true;
        }

        private Item getKeyItem(Key key) {
            for (Item item : keys) {
                if (item.getId().equals(key.getName())) {
                    return item;
                }
            }
            throw new IllegalArgumentException("No key item found for " + key);
        }

        private static class Path {
            private Item target;
            private int distance;
            private Set<String> doorNames;

            public Path(Item target, int distance, Set<String> doorNames) {
                this.target = target;
                this.distance = distance;
                this.doorNames = doorNames;
            }

            public Item getTarget() {
                return target;
            }

            public int getDistance() {
                return distance;
            }

            public Set<String> getDoorNames() {
                return doorNames;
            }

            @Override
            public String toString() {
                return "Path{" +
                        "target=" + target +
                        ", distance=" + distance +
                        ", doorNames=" + doorNames +
                        '}';
            }
        }

        private static class VisitedPosition {
            private Position position;
            private int distance;
            private Set<String> doorNames;

            public VisitedPosition(Position position, int distance, Set<String> doorNames) {
                this.position = position;
                this.distance = distance;
                this.doorNames = doorNames;
            }

            public Position getPosition() {
                return position;
            }

            public int getDistance() {
                return distance;
            }

            public Set<String> getDoorNames() {
                return doorNames;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                VisitedPosition that = (VisitedPosition) o;
                return Objects.equal(position, that.position);
            }

            @Override
            public int hashCode() {
                return Objects.hashCode(position);
            }
        }
    }

    public enum ItemType {
        WALL,
        EMPTY,
        START,
        DOOR,
        KEY;
    }

    public static class Item {
        private Position position;
        private ItemType type;
        private String id;

        private Item(Position p, ItemType type, String id) {
            this.position = p;
            this.type = type;
            this.id = id;
        }

        public Position getPosition() {
            return position;
        }

        public ItemType getType() {
            return type;
        }

        public String getId() {
            return id;
        }

        public static Item wall(Position p) {
            return new Item(p, ItemType.WALL, null);
        }
        public static Item empty(Position p) {
            return new Item(p, ItemType.EMPTY, null);
        }
        public static Item start(Position p) {
            return new Item(p, ItemType.START, null);
        }
        public static Item door(Position p, String id) {
            return new Item(p, ItemType.DOOR, id.toUpperCase());
        }
        public static Item key(Position p, String id) {
            return new Item(p, ItemType.KEY, id.toUpperCase());
        }

        public String print() {
            switch (type) {
                case WALL:  return "#";
                case EMPTY: return ".";
                case START: return "@";
                case DOOR : return id.toUpperCase();
                case KEY: return id.toLowerCase();
                default:
                    throw new IllegalStateException("Unknown type " + type);
            }
        }

        @Override
        public String toString() {
            if (type == ItemType.KEY) {
                return "Key " + id;
            }
            return "Item{" +
                    "p=" + position +
                    ", type=" + type +
                    ", id='" + id + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Item item = (Item) o;
            return Objects.equal(position, item.position) &&
                    type == item.type &&
                    Objects.equal(id, item.id);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(position, type, id);
        }
    }

    public static class KeyGraph {
        private Map<String, Key> keysByName = Maps.newHashMap();

        public Key getKey(String name) {
            Key key = keysByName.get(name);
            if (key == null) {
                throw new IllegalArgumentException("No key found with name " + name);
            }
            return key;
        }

        public void addPath(String fromName, String toName, Set<String> requiredKeys, int distance) {
            Key from = getOrCreate(fromName);
            Key to = getOrCreate(toName);
            Set<Key> required = requiredKeys.stream().map(n -> getOrCreate(n)).collect(Collectors.toSet());

            from.addReachableKey(new ReachableKey(to, distance, required));
            to.addReachableKey(new ReachableKey(from, distance, required));
        }

        private Key getOrCreate(String fromName) {
            Key key = keysByName.get(fromName);
            if (key == null) {
                key = new Key(fromName);
                keysByName.put(fromName, key);
            }
            return key;
        }

        public List<ReachableKey> findReachableKeys(Key current, Set<Key> currentKeys) {
            return current.getReachableKeys().stream().filter(r -> r.isReachableWihKeys(currentKeys)).collect(Collectors.toList());
        }
    }

    public static class Key {
        private String name;
        private List<ReachableKey> reachableKeys = Lists.newArrayList();

        public Key(String name) {
            this.name = name;
        }

        public void addReachableKey(ReachableKey reachableKey) {
            if (!reachableKeys.contains(reachableKey)) {
                reachableKeys.add(reachableKey);
            }
        }

        public String getName() {
            return name;
        }

        public List<ReachableKey> getReachableKeys() {
            return reachableKeys;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Key key = (Key) o;
            return Objects.equal(name, key.name);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(name);
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static class ReachableKey {
        private Key target;
        private int distance;
        private Set<Key> keyRequired = Sets.newHashSet();

        public ReachableKey(Key target, int distance, Set<Key> keyRequired) {
            this.target = target;
            this.distance = distance;
            this.keyRequired = keyRequired;
        }

        public Key getTarget() {
            return target;
        }

        public int getDistance() {
            return distance;
        }

        public boolean isReachableWihKeys(Set<Key> currentKeys) {
            return currentKeys.containsAll(keyRequired);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ReachableKey that = (ReachableKey) o;
            return Objects.equal(target, that.target);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(target);
        }
    }

    public static class KeyVertex {
        private Key lastKey;
        private Set<Key> allKeys;

        public KeyVertex(Key lastKey, Set<Key> allKeys) {
            this.lastKey = lastKey;
            this.allKeys = allKeys;
        }

        public Key getLastKey() {
            return lastKey;
        }

        public Set<Key> getAllKeys() {
            return allKeys;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            KeyVertex keyVertex = (KeyVertex) o;
            return Objects.equal(lastKey, keyVertex.lastKey) &&
                    Objects.equal(allKeys, keyVertex.allKeys);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(lastKey, allKeys);
        }

        @Override
        public String toString() {
            return "{" +
                    "lastKey=" + lastKey +
                    ", all=" + allKeys +
                    '}';
        }
    }

    public static class KeyVertexWithDistance implements Comparable<KeyVertexWithDistance> {

        private KeyVertex vertex;
        private int distance;

        public KeyVertexWithDistance(KeyVertex vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        public KeyVertex getVertex() {
            return vertex;
        }

        public int getDistance() {
            return distance;
        }

        @Override
        public int compareTo(KeyVertexWithDistance other) {
            return this.distance - other.distance;
        }

        @Override
        public String toString() {
            return "{" + vertex + ", distance=" + distance + '}';
        }
    }

    public static class MultiKeyVertex {
        private List<Key> lastKeys;
        private Set<Key> allKeys;

        public MultiKeyVertex(List<Key> lastKeys, Set<Key> allKeys) {
            this.lastKeys = lastKeys;
            this.allKeys = allKeys;
        }

        public Key getLastKeyForRobot(int robot) {
            return lastKeys.get(robot);
        }

        public List<Key> getLastKeys() {
            return lastKeys;
        }

        public Set<Key> getAllKeys() {
            return allKeys;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MultiKeyVertex keyVertex = (MultiKeyVertex) o;
            return Objects.equal(lastKeys, keyVertex.lastKeys) &&
                    Objects.equal(allKeys, keyVertex.allKeys);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(lastKeys, allKeys);
        }

        @Override
        public String toString() {
            return "{" +
                    "lastKeys=" + lastKeys +
                    ", all=" + allKeys +
                    '}';
        }
    }

    public static class MultiKeyVertexWithDistance implements Comparable<MultiKeyVertexWithDistance> {

        private MultiKeyVertex vertex;
        private int distance;

        public MultiKeyVertexWithDistance(MultiKeyVertex vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        public MultiKeyVertex getVertex() {
            return vertex;
        }

        public int getDistance() {
            return distance;
        }

        @Override
        public int compareTo(MultiKeyVertexWithDistance other) {
            return this.distance - other.distance;
        }

        @Override
        public String toString() {
            return "{" + vertex + ", distance=" + distance + '}';
        }
    }

}