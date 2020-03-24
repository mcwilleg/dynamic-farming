package com.neo.dynfarming.util.config;

import com.google.common.io.Files;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public abstract class ConfigAccessor {
	// the associated JavaPlugin object
	protected final JavaPlugin plugin;
	
	// the Configuration object to be accessed and modified
	protected FileConfiguration config;
	
	/*
	In short, a "dynamic" ConfigAccessor subclass is meant for reading and writing, while a non-dynamic (or static)
	ConfigAccessor is only meant for reading.  Dynamic ConfigAccessors are more likely to be used.
	 */
	private final boolean dynamic;
	
	// fileName and ancestry help the class find the file it's supposed to be accessing
	private final String fileName, ancestry;
	
	// the physical File object being written to
	private File configFile;
	
	public ConfigAccessor(JavaPlugin plugin, boolean dynamic, String fileName, String... ancestry) {
		this.plugin = plugin;
		this.dynamic = dynamic;
		this.fileName = fileName.endsWith(".yml") ? fileName : (fileName + ".yml");
		this.ancestry = (ancestry == null || ancestry.length == 0) ? "" : String.join(File.separator, ancestry);
		
		// automatically load existing configuration or create config file when creating new object
		reloadConfig();
	}
	
	/**
	 * If necessary, first initializes the configuration and associated file, then loads the configuration.
	 */
	public final void reloadConfig() {
		// create plugin data folder and subfolders if they don't exist
		if(!plugin.getDataFolder().exists()) {
			if(!plugin.getDataFolder().mkdir())
				throw new IllegalStateException("plugin data folder could not be created");
		}
		File subdirs = new File(plugin.getDataFolder(), ancestry);
		if(!subdirs.exists()) {
			if(!subdirs.mkdirs())
				throw new IllegalStateException("plugin subfolders could not be created");
		}
		
		if(ancestry.isEmpty())
			configFile = new File(plugin.getDataFolder(), fileName);
		else
			configFile = new File(plugin.getDataFolder() + File.separator + ancestry, fileName);
		
		try {
			if (configFile.exists()) {
				// if config file already exists, just load configuration
				if (!dynamic)
					copyInputStreamToFile(this.getResource(), configFile);
				config = YamlConfiguration.loadConfiguration(configFile);
				
				if(dynamic && this.getResource() != null) {
					// repair config in case parts were removed
					Map<String, Object> previous = new HashMap<>();
					for (String path : config.getKeys(true)) {
						if (!config.isConfigurationSection(path))
							previous.put(path, config.get(path));
					}
					copyInputStreamToFile(this.getResource(), configFile);
					config = YamlConfiguration.loadConfiguration(configFile);
					for(Map.Entry<String, Object> entry : previous.entrySet()) {
						if(config.contains(entry.getKey()))
							config.set(entry.getKey(), entry.getValue());
					}
					saveConfig();
				}
			} else {
				// if config file does not exist, create it appropriately
				if (this.getResource() != null) {
					// if config file has a default in the JAR
					copyInputStreamToFile(this.getResource(), configFile);
					config = YamlConfiguration.loadConfiguration(configFile);
					config.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(this.getResource())));
				} else {
					// if config file does not have a default in the JAR
					try {
						if (!configFile.createNewFile())
							plugin.getLogger().log(Level.SEVERE, "Could not create " + configFile.getName());
					} catch (IOException ex) {
						ex.printStackTrace();
					}
					config = YamlConfiguration.loadConfiguration(configFile);
				}
			}
		} catch(IOException ex) {
			plugin.getLogger().log(Level.SEVERE, "Error getting input stream for file: \"" + fileName + "\"");
			ex.printStackTrace();
		}
	}
	
	/**
	 * Deletes the file associated with this configuration.
	 */
	protected void deleteConfigFile() {
		// ignore if config file has not yet been initialized
		// * this should never happen
		if(configFile != null && !configFile.delete())
			plugin.getLogger().log(Level.SEVERE, "Could not delete " + configFile.getName());
	}
	
	/**
	 * Saves the current configuration to the specified file.
	 */
	protected void saveConfig() {
		// ignore if config file or configuration have not yet been initialized
		// * this should never happen
		if(configFile == null || config == null)
			return;
		try {
			// save configuration to file
			config.save(configFile);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	Copies the contents of the specified InputStream to the specified File, assuming file is not null and not a
	directory.  If the File does not exist, it this method will attempt to create it.
	 */
	private void copyInputStreamToFile(InputStream inputStream, File file) throws IOException {
		if(inputStream == null || file == null)
			throw new IOException("input stream and file cannot be null");
		if(file.isDirectory())
			throw new IOException("file " + file.getName() + " cannot be a directory");
		if(!file.exists()) {
			if(!file.createNewFile())
				throw new IOException("file " + file.getName() + " could not be created");
		}
		byte[] buffer = new byte[inputStream.available()];
		if(inputStream.read(buffer) > 0)
			Files.write(buffer, file);
	}
	
	// helper method for readability
	private InputStream getResource() {
		return plugin.getResource(fileName);
	}
}
