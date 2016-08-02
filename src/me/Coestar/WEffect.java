package me.Coestar;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class WEffect extends JavaPlugin implements Listener{
	public Set<String> Worlds, Effects = Worlds = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
	public void onEnable(){
		getConfig().addDefault("Worlds", Arrays.asList("world"));
		getConfig().addDefault("Effects", Arrays.asList("3, SPEED"));
		getConfig().options().copyDefaults(true);
		saveConfig();
		Worlds.addAll(getConfig().getStringList("Worlds"));
		Effects.addAll(getConfig().getStringList("Effects"));
		getServer().getPluginManager().registerEvents(this, this);
	}
	@EventHandler(priority=EventPriority.MONITOR)
	public void onJoin(PlayerJoinEvent Event){
		Player Player = Event.getPlayer();
		if(Worlds.contains(Player.getWorld().getName())){
			Effects.forEach(
				Effect -> Player.addPotionEffect(
					new PotionEffect(
						PotionEffectType.getByName(Effect.replaceAll("([a-zA-Z_]+)$", "$1")),
						Integer.MAX_VALUE,
						Integer.parseInt(Effect.replaceAll("^([0-9]+)", "$1")),
						true
					)
				)
			);
		}
	}
	@EventHandler(priority=EventPriority.MONITOR)
	public void onQuit(PlayerQuitEvent Event){
		Player Player = Event.getPlayer();
		if(Worlds.contains(Player.getWorld().getName())){
			Effects.forEach(
				Effect -> Player.removePotionEffect(
					PotionEffectType.getByName(Effect.replaceAll("([a-zA-Z_]+)$", "$1"))
				)
			);
		}
	}
	@EventHandler(priority=EventPriority.MONITOR)
	public void onKick(PlayerKickEvent Event){
		Player Player = Event.getPlayer();
		if(Worlds.contains(Player.getWorld().getName())){
			Effects.forEach(
				Effect -> Player.removePotionEffect(
					PotionEffectType.getByName(Effect.replaceAll("([a-zA-Z_]+)$", "$1"))
				)
			);
		}
	}
	@EventHandler(priority=EventPriority.MONITOR)
	public void onJoin(PlayerTeleportEvent Event){
		Player Player = Event.getPlayer();
		if(Worlds.contains(Event.getFrom().getWorld().getName())){
			Effects.forEach(
				Effect -> Player.removePotionEffect(
					PotionEffectType.getByName(Effect.replaceAll("([a-zA-Z_]+)$", "$1"))
				)
			);
		}
		if(Worlds.contains(Event.getTo().getWorld().getName())){
			Effects.forEach(
				Effect -> Player.addPotionEffect(
					new PotionEffect(
						PotionEffectType.getByName(Effect.replaceAll("([a-zA-Z_]+)$", "$1")),
						Integer.MAX_VALUE,
						Integer.parseInt(Effect.replaceAll("^([0-9]+)", "$1")),
						true
					)
				)
			);
		}
	}
}
