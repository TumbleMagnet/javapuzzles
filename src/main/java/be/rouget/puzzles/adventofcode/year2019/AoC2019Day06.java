package be.rouget.puzzles.adventofcode.year2019;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class AoC2019Day06 {

    private static Logger LOG = LogManager.getLogger(AoC2019Day06.class);

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines("aoc_2019_day06_input.txt");

        // Populate map with orbits
        SpaceMap map = new SpaceMap();
        for (String orbit: input) {
            String[] names = orbit.split("\\)");
            map.addOrbit(names[0], names[1]);
        }
        LOG.info("Number of orbits: " + map.computeNumberOfOrbits());
        LOG.info("Number of transfers: " + map.computeNumberOfTransfers("YOU", "SAN"));
    }

    public static class SpaceMap {

        Map<String, SpaceObject> spaceObjects = Maps.newHashMap();

        public void addOrbit(String parent, String child) {
            SpaceObject parentObject = getOrCreateSpaceObject(parent);
            SpaceObject childObject = getOrCreateSpaceObject(child);
            parentObject.addChild(childObject);
            childObject.setParent(parentObject);
        }

        public SpaceObject getSpaceObject(String name) {
            SpaceObject spaceObject = spaceObjects.get(name);
            if (spaceObject == null) {
                throw new IllegalStateException("Unknown object with name " + name);
            }
            return spaceObject;
        }

        private SpaceObject getOrCreateSpaceObject(String name) {
            SpaceObject spaceObject = spaceObjects.get(name);
            if (spaceObject == null) {
                spaceObject = new SpaceObject(name);
                spaceObjects.put(name, spaceObject);
            }
            return spaceObject;
        }

        public int computeNumberOfOrbits() {

            // Traverse the tree and compute depth of each node
            SpaceObject com = getSpaceObject("COM");
            com.setDepthOnObjectAndItsChildren(0);

            return spaceObjects.values().stream().mapToInt(SpaceObject::getDepth).sum();
        }

        public int computeNumberOfTransfers(String fromName, String toName) {
            SpaceObject from = getSpaceObject(fromName);
            SpaceObject to = getSpaceObject(toName);

            SpaceObject[] path1 = from.pathToRoot().toArray(new SpaceObject[0]);
            LOG.info("Path1: " + Arrays.toString(path1));
            SpaceObject[] path2 = to.pathToRoot().toArray(new SpaceObject[0]);
            LOG.info("Path2: " + Arrays.toString(path2));

            int lengthOfCommonPart = 0;
            for (int i=0; i < Math.min(path1.length, path2.length); i++) {
                if (path1[i].getName() == path2[i].getName()) {
                    lengthOfCommonPart++;
                }
                else {
                    break;
                }
            }
            LOG.debug("Length of common part: " + lengthOfCommonPart);

            return (path1.length-1 - lengthOfCommonPart) + (path2.length-1 - lengthOfCommonPart) ;
        }
    }

    private static class SpaceObject {
        private String name;
        private Integer depth = null;
        private SpaceObject parent = null;
        private Set<SpaceObject> children = Sets.newHashSet();

        public SpaceObject(String name) {
            this.name = name;
        }

        public void addChild(SpaceObject child) {
            children.add(child);
        }

        public void setParent(SpaceObject parent) {
            this.parent = parent;
        }

        public String getName() {
            return name;
        }

        public Set<SpaceObject> getChildren() {
            return Collections.unmodifiableSet(children);
        }

        public int getDepth() {
            return depth;
        }

        public void setDepthOnObjectAndItsChildren(int depth) {
            this.depth = depth;
            for (SpaceObject child: children) {
                child.setDepthOnObjectAndItsChildren(depth+1);
            }
        }

        public List<SpaceObject> pathToRoot() {

            List<SpaceObject> path = null;
            if (parent == null) {
                path = Lists.newArrayList();
            }
            else {
                path = parent.pathToRoot();
            }
            path.add(this);
            return path;
        }

        public String toString() {
            return name;
        }
    }
}