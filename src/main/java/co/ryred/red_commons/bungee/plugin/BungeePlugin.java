package co.ryred.red_commons.bungee.plugin;

import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;

/**
 * Created by Cory Redmond on 02/01/2016.
 *
 * @author Cory Redmond <ace@ac3-servers.eu>
 */
public class BungeePlugin extends Plugin {

    private Configuration config;

    public void saveDefaultConfig() {
        saveDefaultConfig(false);
    }

    public void saveDefaultConfig( boolean force ) {
        if (!getConfigFile().getParentFile().exists()) {
            getConfigFile().getParentFile().mkdirs();
        }
        if (force || !getConfigFile().exists()) {
            try {
                getConfigFile().createNewFile();
                try (InputStream is = getResourceAsStream("config.yml"); OutputStream os = new FileOutputStream(getConfigFile())) {
                    ByteStreams.copy(is, os);
                }
            } catch (IOException e) {
                throw new RuntimeException("Unable to create config file", e);
            }
        }
    }

    public void reloadConfig() {
        config = null;
    }

    public Configuration getConfig()
    {
		saveDefaultConfig(false);
        try {
            return this.config == null ? ( this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load( getConfigFile() ) ) : config;
        } catch ( IOException e ) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveConfig() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(this.config, getConfigFile());
        } catch (IOException ex) {
            ex.printStackTrace();
            getLogger().severe("Couldn\'t save config file!");
        }
    }

    public File getConfigFile() {
        return new File( getDataFolder(), "config.yml" );
    }

}
