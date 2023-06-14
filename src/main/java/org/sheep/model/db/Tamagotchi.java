package org.sheep.model.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Getter
@AllArgsConstructor
@Document("tamagotchi")
public class Tamagotchi {
    @Id
    private String id;
    private int variantId;
    private String name;
    private String imageName;
    private int hp;
    private int hunger;
    private int happiness;
    private boolean needsToilet;
    private boolean isSick;
    private int age;
    private int created;
}
