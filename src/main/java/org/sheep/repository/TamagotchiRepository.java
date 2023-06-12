package org.sheep.repository;

import org.sheep.model.db.Tamagotchi;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TamagotchiRepository extends MongoRepository<Tamagotchi, String> {

    Tamagotchi findFirstByOrderByCreatedDesc();
}
