package org.sheep.gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.sheep.model.db.TamagotchiVariant;
import org.sheep.repository.TamagotchiVariantRepository;
import org.sheep.util.FileUtil;
import org.sheep.util.RoboshiConstant;

import java.io.IOException;
import java.util.List;

@Slf4j
public class DatabaseGateway {
    private static final String TAMAGOTCHI_VARIANTS_FILE_NAME = "tamagotchi_variants.json";

    private DatabaseGateway() {}

    public static void initTamagotchiVariants(TamagotchiVariantRepository tamagotchiRepository, ObjectMapper mapper) {
        log.info("Initializing database");
        try {
            List<TamagotchiVariant> fileVariants = FileUtil.getTamagotchiVariants(TAMAGOTCHI_VARIANTS_FILE_NAME, "tamagotchi/", mapper);
            tamagotchiRepository.saveAll(fileVariants);
            log.info("Saved [{}] Tamagotchi variants to the database", fileVariants.size());
        } catch (
                IOException ex) {
            log.error("Could not read from: {}", TAMAGOTCHI_VARIANTS_FILE_NAME, ex);
        }
    }
}
