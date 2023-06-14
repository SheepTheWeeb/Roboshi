package org.sheep.model.db;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document("tamagotchi_variant")
public class TamagotchiVariant {
    @Id
    private int id;
    private String type;
    private List<Integer> canEvolveIn;
    private String imageName;
}
