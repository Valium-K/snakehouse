package dev.valium.snakehouse.module.game.score.processor;


import dev.valium.snakehouse.module.game.Title;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Title2String {
    public static List<String> getAPISpecificationStrings() {
        Title[] values = Title.values();
        ArrayList<String> strings = new ArrayList<>();

        for(Title title : values) {
            strings.add(title.toString()
                    .replace('_', '-')
                    .toLowerCase(Locale.ROOT));
        }

        return strings;
    }

    public static String getForSwaggerAllowableValues() {
        return String.join(", ", getAPISpecificationStrings());
    }
}
