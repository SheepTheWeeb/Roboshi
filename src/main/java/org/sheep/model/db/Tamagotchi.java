package org.sheep.model.db;

import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Document("tamagotchi")
public class Tamagotchi {
    @Id
    private String id;
    private int hp;
    private int hunger;
}
