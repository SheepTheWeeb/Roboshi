package org.sheep.model.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@AllArgsConstructor
@Document("tamagotchi")
public class Tamagotchi {
    @Id
    private String id;
    private int hp;
    private int hunger;
}
