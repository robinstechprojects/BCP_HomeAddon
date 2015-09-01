package bcphome;

import java.io.Serializable;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class savedLocation implements Serializable {
	private double x;
	private double y;
	private double z;
	private float pitch;
	private float yaw;
	private String world;

	public savedLocation(Location xa, String w) {
		this.x = xa.getX();
		this.y = xa.getY();
		this.z = xa.getZ();
		this.yaw = xa.getYaw();
		this.pitch = xa.getPitch();
		this.world = w;
	}

	public Location retrieve() {
		Location l = new Location(Bukkit.getWorld(this.world), this.x, this.y, this.z, this.pitch, this.yaw);
		return l;
	}
}
