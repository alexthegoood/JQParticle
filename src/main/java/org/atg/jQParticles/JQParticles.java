package org.atg.jQParticles;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class JQParticles extends JavaPlugin implements Listener {

    private final Logger log = getLogger();
    private final float playerHeight = 1.7671875f;
//    private final float playerHeightGap = 0.2328125f;

    @Override
    public void onEnable() {
        log.info("Plugin is enabled");
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        log.info("Plugin is disabled");
    }

    public void playPlayerSession(Player player, Particle particle, Sound sound){
        if(player.getGameMode() == GameMode.SPECTATOR) return;
//        if() return;

        Location playerLocation = player.getLocation();

        float playerScale = (float) player.getAttribute(Attribute.SCALE).getValue();

        float playerCenterY = playerHeight*playerScale;
        int occupiedBlocksHeightY = (int) Math.floor(playerCenterY);

        Location centerY = playerLocation.clone().add(0.0, playerCenterY/1.85, 0.0);

        for(Player players : Bukkit.getOnlinePlayers()){
            players.spawnParticle(Particle.FLASH, centerY, 1, 0.0, 0.0, 0.0, 0.0);
            players.spawnParticle(particle, centerY, 20*occupiedBlocksHeightY, 0.35*playerScale, 0.45*playerScale, 0.35*playerScale, 0.0);
            players.playSound(centerY, sound, 1.0f, 1.7f);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        playPlayerSession(
                event.getPlayer(),
                Particle.TRIAL_SPAWNER_DETECTION_OMINOUS,
                Sound.ENTITY_ILLUSIONER_PREPARE_MIRROR
        );
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        playPlayerSession(
                event.getPlayer(),
                Particle.TRIAL_SPAWNER_DETECTION,
                Sound.BLOCK_TRIAL_SPAWNER_SPAWN_ITEM_BEGIN
        );
    }
}
