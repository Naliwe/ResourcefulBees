package com.teamresourceful.resourcefulbees.registry;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import com.teamresourceful.resourcefulbees.config.Config;
import com.teamresourceful.resourcefulbees.lib.BiomeType;
import com.teamresourceful.resourcefulbees.lib.ModConstants;
import com.teamresourceful.resourcefulbees.utils.FileUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.biome.Biome;

import java.io.Reader;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

import static com.teamresourceful.resourcefulbees.ResourcefulBees.LOGGER;
import static net.minecraftforge.common.BiomeDictionary.Type;
import static net.minecraftforge.common.BiomeDictionary.getBiomes;

public class BiomeDictionary extends HashMap<String, BiomeType> {

    protected static final BiomeDictionary INSTANCE = new BiomeDictionary();
    private static Path dictionaryPath;

    public static BiomeDictionary get() {
        return INSTANCE;
    }

    public static void build() {
        LOGGER.info("Building Biome Dictionary...");
        if (Config.GENERATE_BIOME_DICTIONARIES.get()) {
            FileUtils.setupDefaultFiles("/data/resourcefulbees/biome_dictionary", dictionaryPath);
        }
        FileUtils.streamFilesAndParse(dictionaryPath, BiomeDictionary::parseType, "Could not stream biome dictionary!!");
    }

    private static void parseType(Reader reader, String name) {
        JsonObject jsonObject = GsonHelper.fromJson(ModConstants.GSON, reader, JsonObject.class);
        BiomeType biomeType = BiomeType.CODEC.parse(JsonOps.INSTANCE, jsonObject).getOrThrow(false, s -> LOGGER.warn("Could not parse biome type {}", name));
        get().put(name, biomeType);
    }

    public static void setPath(Path dictionaryPath) {
        BiomeDictionary.dictionaryPath = dictionaryPath;
    }

    public static Collection<? extends ResourceLocation> getForgeBiomeLocations(Type type) {
        return getBiomes(type).stream().map(ResourceKey::location).collect(Collectors.toList());
    }

    public static Type getForgeType(ResourceLocation resourceLocation) {
        Collection<Type> forgeDict = Type.getAll();
        for (Type type : forgeDict) {
            if (type.getName().equalsIgnoreCase(resourceLocation.getPath())) {
                return type;
            }
        }
        return null;
    }
}
