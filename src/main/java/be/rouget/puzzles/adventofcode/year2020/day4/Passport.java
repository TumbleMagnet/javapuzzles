package be.rouget.puzzles.adventofcode.year2020.day4;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

public class Passport {

    Map<PassportProperty, String> values = Maps.newHashMap();

    public Passport(List<String> lines) {
        for (String line : lines) {
            String[] tokens = line.split(" ");
            for (String token : tokens) {
                String[] parts = token.split(":");
                if (parts.length != 2) {
                    throw new IllegalArgumentException("Invalid token [" + token + "] in line [" + line + "]");
                }
                PassportProperty property = PassportProperty.fromCode(parts[0]);
                String value = parts[1];
                addProperty(property, value);
            }
        }
    }

    public boolean hasMandatoryProperties() {
        // Passport is valid if it contains all mandatory properties
        for (PassportProperty property : PassportProperty.values()) {
            if (property.isMandatory() && !values.containsKey(property) ) {
                return false;
            }
        }
        return true;
    }

    public boolean isValid() {
        // Passport is valid if it contains all mandatory properties
        for (PassportProperty property : PassportProperty.values()) {
            if (!isPropertyValid(property)) {
                return false;
            }
        }
        return true;
    }

    private boolean isPropertyValid(PassportProperty property) {
        return property.isValueValid(values.get(property));
    }


    private void addProperty(PassportProperty property, String value) {
        values.put(property, value);
    }
}
