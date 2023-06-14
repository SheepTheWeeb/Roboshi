package org.sheep.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Happiness {
    DEPRESSED(0),
    GLOOMY(1),
    DEADPAN(2),
    SATISFIED(3),
    HAPPY(4),
    THRILLED(5),
    OVERJOYED(6);

    private final int id;

    public static Happiness getHappiness(int id) {
        return Arrays.stream(Happiness.values())
                .filter(item -> item.getId() == id)
                .findFirst()
                .orElse(DEPRESSED);
    }
}
