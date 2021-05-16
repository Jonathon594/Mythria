package me.Jonathon594.Mythria.Config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.File;

public class ServerGlobalConfig {
    public static String discordToken = "";

    public static CommentedFileConfig config;

    public static void init() {
        File file = FMLPaths.CONFIGDIR.get().resolve("mythria-server.toml").toFile();
        config = CommentedFileConfig.builder(file).sync().build();
        if (!file.exists()) {
            config.set("discordToken", "");
            config.save();
        }
        config.load();
        discordToken = config.get("discordToken");
    }
}
