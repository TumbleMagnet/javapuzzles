package be.rouget.puzzles.adventofcode.year2015.day12;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Streams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

public class JSAbacusFramework {

    private static final String YEAR = "2015";
    private static final String DAY = "12";

    private static final Logger LOG = LogManager.getLogger(JSAbacusFramework.class);

    private final List<String> input;

    public JSAbacusFramework(List<String> input) {
        this.input = input;
        LOG.info("Input has {} lines...", input.size());
    }

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        JSAbacusFramework aoc = new JSAbacusFramework(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public long computeResultForPart1() {
        JsonNode jsonNode = parseJson(input.get(0));
        SumOfNumericNodes summer = new SumOfNumericNodes();
        walkJsonTree(jsonNode, node -> true, summer);
        return summer.getSum();
    }

    public long computeResultForPart2() {
        JsonNode jsonNode = parseJson(input.get(0));
        SumOfNumericNodes summer = new SumOfNumericNodes();
        walkJsonTree(jsonNode, node -> !isObjectWithValueRed(node), summer);
        return summer.getSum();
    }

    private boolean isObjectWithValueRed(JsonNode node) {
        if (!node.isObject()) {
            return false;
        }

        for (Map.Entry<String, JsonNode> fieldEntry : Lists.newArrayList(node.fields()))
        {
            JsonNode fieldNode = fieldEntry.getValue();
            if (fieldNode.isValueNode() && fieldNode.isTextual() && "red".equals(fieldNode.asText())) {
                return true;
            }
        }
        return false;
    }

    private void walkJsonTree(JsonNode node, Predicate<JsonNode> filter, Consumer<JsonNode> consumer)
    {
        if (!filter.test(node)) {
            return;
        }
        consumer.accept(node);
        if (node.isObject()) {
            Streams.stream(node.fields()).forEach(fieldEntry ->  walkJsonTree(fieldEntry.getValue(), filter, consumer));
        } else if (node.isArray()) {
            Streams.stream(node.elements()).forEach(arrayNode -> walkJsonTree(arrayNode, filter, consumer));
        }
    }

    private JsonNode parseJson(String inputJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readTree(inputJson);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static class SumOfNumericNodes implements Consumer<JsonNode> {

        private long sum = 0;

        public long getSum() {
            return sum;
        }

        @Override
        public void accept(JsonNode jsonNode) {
            if (!jsonNode.isValueNode()) {
                return;
            }

            if (jsonNode.isTextual()) {
                return;
            }
            sum += jsonNode.asLong();
        }
    }
}
