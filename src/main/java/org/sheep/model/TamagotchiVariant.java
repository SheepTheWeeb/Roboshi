package org.sheep.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TamagotchiVariant {
    private int id;
    private String type;
    private List<Integer> canEvolveIn;
    private String imageName;
}
