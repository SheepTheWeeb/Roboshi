package org.sheep.repository;

import org.sheep.model.db.TamagotchiVariant;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TamagotchiVariantRepository extends MongoRepository<TamagotchiVariant, Integer> {
}
