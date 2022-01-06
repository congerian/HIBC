package ru.arcein.plugins.hibc;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.herocraftonline.heroes.Heroes;
import com.herocraftonline.heroes.characters.Hero;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.plugin.java.JavaPlugin;
import ru.arcein.plugins.hibc.protocol.experience.ExperiencePacketAdapter;

import java.util.HashSet;
import java.util.Set;

public class HIBC extends JavaPlugin {

private FileConfiguration config;

public Set<Player> fightingPlayers = new HashSet<>();

        Heroes heroesPlugin;
        ProtocolManager protocolManager;

public void onLoad() {}

public void onEnable() {

        this.saveDefaultConfig();
        config = this.getConfig();

        getServer().getPluginManager().registerEvents(new HIBCListener(this), this);

        Bukkit.getLogger().info(ChatColor.GREEN + "The plugin has been enabled!");

        Bukkit.getLogger().info(ChatColor.GREEN + "Getting Heroes instance!");

        heroesPlugin = (Heroes) this.getServer().getPluginManager().getPlugin("Heroes");
        protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener(new ExperiencePacketAdapter(this));

}

public void onDisable(){

        }



private class HIBCListener implements Listener {
    HIBC plugin;

    public HIBCListener(HIBC plugin){
        this.plugin = plugin;
    }

    @EventHandler(
            priority = EventPriority.HIGH,
            ignoreCancelled = true
    )
    public void onPlayerSwapHands(PlayerSwapHandItemsEvent event) {

        Player player = event.getPlayer();

        if(fightingPlayers.contains(player)){
            player.sendMessage("Вы вышли из режима использования способностей!");
            fightingPlayers.remove(player);
        }
        else{
            fightingPlayers.add(player);
            player.sendMessage("Вы вошли в режим использования способностей!");
        }
    }


    @EventHandler(
            priority = EventPriority.LOW
    )
    public void onPlayerItemHeldChange(PlayerItemHeldEvent event) {

        Player player = event.getPlayer();

        if(fightingPlayers.contains(player)){
            player.sendMessage("Вы попытались сменить слот " + event.getPreviousSlot()
                    + " на слот " + event.getNewSlot() + " в режиме боя!");
            event.setCancelled(true);
        }

        Hero hero = heroesPlugin.getCharacterManager().getHero(player);


    }



}
}
