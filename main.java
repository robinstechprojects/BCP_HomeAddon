package bcphome;

import java.util.HashMap;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.blogspot.robinstechprojects.BCP.BCPMain;

public class main extends JavaPlugin implements Listener {
	HashMap<String, savedLocation> homes = new HashMap<String, savedLocation>();

	
	@SuppressWarnings("unchecked")
	public void onEnable() {
		Logger log = getLogger();
		log.info("Starten !");
		try {
			if (getDataFolder().exists()) {
				this.homes = (HashMap<String, savedLocation>) 
						BCPMain.load(getDataFolder() + "/data.bin") ;
			} else {
				getDataFolder().mkdir();
				Bukkit.getLogger().info("Keine Daten Verfügbar ! Wird das Plugin zum ersten mal genutzt ?");
			}
		} catch (Exception e) {
			Bukkit.getLogger().info("Laden der Daten fehlgeschlagen ! :-( ");
		}
	}

	public void onDisable() {
		saveConfig();

		Logger log = getLogger();
		log.info("Stoppen !");
		try {
			if (getDataFolder().exists()) {
				BCPMain.save(this.homes, getDataFolder() + "/data.bin");
			} else {
				getDataFolder().mkdir();
			}
		} catch (Exception e) {
			Bukkit.getLogger().info("Sichern der Daten fehlgeschlagen !");
		}
	}
 public void save(Object obj, String path) throws Exception{ 
	BCPMain.save(obj, path);
 }
	public void load(String path) throws Exception {
BCPMain.load(path);
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if ((cmd.getName().equalsIgnoreCase("home")) && (sender.hasPermission("home.home"))
				&& ((sender instanceof Player))) {
			if (this.homes.containsKey(sender.getName())) {
				Player p = (Player) sender;
				savedLocation l = this.homes.get(p.getName());
				Location lo = l.retrieve();
				if (lo.getWorld() == null) {
					lo.setWorld(p.getWorld());
				}
				p.teleport(lo);
			} else {
				sender.sendMessage("Du hast keinen Home-Punkt gesetzt!");
			}
			return true;
		}
		if ((cmd.getName().equalsIgnoreCase("sethome")) && (sender.hasPermission("home.set"))
				&& ((sender instanceof Player))) {
			Player p = (Player) sender;
			this.homes.put(sender.getName(), new savedLocation(p.getLocation(), p.getWorld().toString()));
			sender.sendMessage("Home-Punkt erfolgreich gesetzt");
			return true;
		}
		if ((cmd.getName().equalsIgnoreCase("delhome")) && (sender.hasPermission("home.del"))
				&& ((sender instanceof Player))) {
			if (this.homes.containsKey(sender.getName())) {
				Player p = (Player) sender;

				this.homes.remove(p.getName());
				sender.sendMessage("Home-Punkt geloescht !.");
			} else {
				sender.sendMessage("Du hast noch keinen HomePunkt gesetzt");
			}
			return true;
		}
		return false;
	}
}
