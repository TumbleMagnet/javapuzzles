package be.rouget.puzzles.adventofcode.year2016.day22;

import be.rouget.puzzles.adventofcode.util.map.Position;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NodeTest {

    @Test
    void testParse() {
        assertThat(Node.parse("/dev/grid/node-x0-y0     91T   71T    20T   78%")).isEqualTo(
                new Node("/dev/grid/node-x0-y0", 91, 71, new Position(0, 0))
        );
        assertThat(Node.parse("/dev/grid/node-x5-y21   505T  497T     8T   98%")).isEqualTo(
                new Node("/dev/grid/node-x5-y21", 505, 497, new Position(5, 21))
        );
    }

    @Test
    void testDistance() {
        Node node1 = node(3, 5);
        Node node2 = node(1, 9);
//        assertThat(node1.distanceFrom(node2)).isEqualTo(6);
//        assertThat(node2.distanceFrom(node1)).isEqualTo(6);
//        assertThat(node1.distanceFrom(node1)).isEqualTo(0);
        throw new UnsupportedOperationException();
    }

    @Test
    void testIsConnectedTo() {
        assertThat(node(3, 4).isConnectedTo(node(3, 3))).isTrue();
        assertThat(node(3, 4).isConnectedTo(node(3, 5))).isTrue();
        assertThat(node(3, 4).isConnectedTo(node(2, 4))).isTrue();
        assertThat(node(3, 4).isConnectedTo(node(4, 4))).isTrue();

        assertThat(node(3, 4).isConnectedTo(node(4, 5))).isFalse();
        assertThat(node(3, 4).isConnectedTo(node(2, 5))).isFalse();
        assertThat(node(3, 4).isConnectedTo(node(4, 3))).isFalse();
        assertThat(node(3, 4).isConnectedTo(node(2, 3))).isFalse();
    }

    private Node node(int x, int y) {
        return  new Node("name1", 0, 0, new Position(x, y));
    }
}