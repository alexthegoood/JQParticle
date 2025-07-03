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

    @Override
    public void onEnable() {
        log.info("Plugin is enabled");
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        log.info("Plugin is disabled");
    }

    private void playPlayerSession(Player player, Particle particle, Sound sound, float pitch){

        if(player.getGameMode() == GameMode.SPECTATOR) return;
//        if() return; <- mb the logic of vanish plugins

        Location playerLocation = player.getLocation();

        float playerScale = (float) player.getAttribute(Attribute.SCALE).getValue();
        float playerHeight = 1.7671875f;
//        float playerHeightGap = 0.2328125f; <- I wanted to use it, but forgot why (lol)

        float playerCenterY = playerHeight*playerScale;
        int occupiedBlocksHeightY = (int) Math.ceil(playerCenterY);

        Location centerY = playerLocation.clone().add(0.0, playerCenterY/1.85, 0.0);
        World world = player.getWorld();

        world.spawnParticle(Particle.FLASH, centerY, 1, 0.0, 0.0, 0.0, 0.0);
        world.spawnParticle(particle, centerY, 20*occupiedBlocksHeightY, 0.35*playerScale, 0.45*playerScale, 0.35*playerScale, 0.0);
        world.playSound(centerY, sound, playerScale/2, pitch);

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        playPlayerSession(
                event.getPlayer(),
                Particle.TRIAL_SPAWNER_DETECTION_OMINOUS,
                Sound.ENTITY_ILLUSIONER_PREPARE_MIRROR,
                1.7f
        );
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        playPlayerSession(
                event.getPlayer(),
                Particle.TRIAL_SPAWNER_DETECTION,
                Sound.BLOCK_TRIAL_SPAWNER_SPAWN_ITEM,
                0.0f
        );
    }
}
