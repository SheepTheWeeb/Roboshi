package org.sheep.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.sheep.model.TamagotchiVariant;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class FileUtil {
    private FileUtil() {
    }

    public static InputStream getFileStreamFromResources(String name, String path) {
        return FileUtil.class.getClassLoader().getResourceAsStream(path + name);
    }

    public static List<TamagotchiVariant> getTamagotchiVariants(String name, String path, ObjectMapper mapper) throws IOException {
        InputStream is = getFileStreamFromResources(name, path);
        return mapper.readValue(is, new TypeReference<>() {});
    }
}
