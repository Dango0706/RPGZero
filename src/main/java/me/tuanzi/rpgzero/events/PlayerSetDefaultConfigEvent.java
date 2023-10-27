package me.tuanzi.rpgzero.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.geysermc.geyser.api.GeyserApi;

import static me.tuanzi.rpgzero.RPGZero.geyserApi;
import static me.tuanzi.rpgzero.RPGZero.hasGeyser;
import static me.tuanzi.rpgzero.utils.Config.*;

public class PlayerSetDefaultConfigEvent implements Listener {
    @EventHandler
    public void aVoid(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String name = player.getDisplayName().toLowerCase();
        if (playerConfig.get(name) == null) {
            setPlayerConfig(name, "isNewDamageCalculate", true);
            setPlayerConfig(name, "isDrawCountToast", true);
        }
        //是否是手机版的玩家?
        if (hasGeyser) {
            if (geyserApi.isBedrockPlayer(player.getUniqueId()))
                setPlayerConfig(name, "isBedrockPlayer", true);
            else
                setPlayerConfig(name, "isBedrockPlayer", false);
        }
        savePlayerConfig();

        System.out.println("是否是基岩版玩家:" + GeyserApi.api().isBedrockPlayer(player.getUniqueId()));
    }
}
