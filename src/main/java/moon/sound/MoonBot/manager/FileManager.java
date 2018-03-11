package moon.sound.MoonBot.manager;

import moon.sound.MoonBot.MoonBot;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class FileManager {
    private File configsDirectory;
    private File configFile;
    private Map<String, Object> config;

    public FileManager() {
        load();
    }

    public void load() {
        try {
            configsDirectory = new File("configs/");
            configsDirectory.mkdirs();
            this.configFile = new File(getConfigsDirectory(), "config.yml");
            configFile.createNewFile();
            Yaml yaml = new Yaml();
            this.config = (Map<String, Object>) yaml.load(new FileReader(configFile));
            if (this.config == null) this.config = new HashMap<>();
            Map<String, Object> defaults = (Map<String, Object>) yaml.load(this.getClass().getResourceAsStream("/configs/config.yml"));
            for (String key : defaults.keySet()) {
                if (! config.containsKey(key)) config.put(key, defaults.get(key));
            }
            saveConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveConfig() {
        try {
            FileWriter writer = new FileWriter(configFile);
            DumperOptions dumperOptions = new DumperOptions();
            dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            new Yaml(dumperOptions).dump(config, writer);
        } catch (Exception e) {
            MoonBot.severe("Error while saving config: ");
            e.printStackTrace();
        }
    }

    public File getConfigsDirectory() {
        return configsDirectory;
    }

    public File getConfigFile() {
        return configFile;
    }

    public Map getConfig() {
        return config;
    }

}