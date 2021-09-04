package me.Jonathon594.Mythria.Data.Provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.item.Item;
import org.apache.commons.lang3.text.translate.JavaUnicodeEscaper;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public abstract class WeightProvider implements IDataProvider {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    private final Map<String, Float> data = new TreeMap<>();
    private final DataGenerator gen;
    private final String modid;

    public WeightProvider(DataGenerator gen, String modid) {
        this.gen = gen;
        this.modid = modid;
    }

    @Override
    public void act(DirectoryCache cache) throws IOException {
        addWeights();
        if (!data.isEmpty())
            save(cache, data, this.gen.getOutputFolder().resolve("data/" + modid + "/weight.json"));
    }

    @Override
    public String getName() {
        return "weights";
    }

    public void add(String key, float weight) {
        if (data.put(key, weight) != null)
            throw new IllegalStateException("Duplicate translation key " + key);
    }

    public void add(Item key, float weight) {
        add(key.getRegistryName().toString(), weight);
    }

    private void save(DirectoryCache cache, Object object, Path target) throws IOException {
        String data = GSON.toJson(object);
        data = JavaUnicodeEscaper.outsideOf(0, 0x7f).translate(data); // Escape unicode after the fact so that it's not double escaped by GSON
        String hash = IDataProvider.HASH_FUNCTION.hashUnencodedChars(data).toString();
        if (!Objects.equals(cache.getPreviousHash(target), hash) || !Files.exists(target)) {
            Files.createDirectories(target.getParent());

            try (BufferedWriter bufferedwriter = Files.newBufferedWriter(target)) {
                bufferedwriter.write(data);
            }
        }

        cache.recordHash(target, hash);
    }

    protected abstract void addWeights();
}
