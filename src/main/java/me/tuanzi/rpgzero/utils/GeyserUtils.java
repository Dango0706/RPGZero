package me.tuanzi.rpgzero.utils;

import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.Form;
import org.geysermc.cumulus.form.util.FormBuilder;

import static me.tuanzi.rpgzero.RPGZero.*;

public class GeyserUtils {


    public static void sendForm(Player player, Form form) {
        if (hasGeyser) {
            if (hasFloodgate) {
                floodgateApi.sendForm(player.getUniqueId(), form);
            } else {
                geyserApi.sendForm(player.getUniqueId(), form);
            }
        }
    }

    public static void sendForm(Player player, FormBuilder<?, ?, ?> formBuilder) {
        if (hasGeyser) {
            if (hasFloodgate) {
                floodgateApi.sendForm(player.getUniqueId(), formBuilder);
            } else {
                geyserApi.sendForm(player.getUniqueId(), formBuilder);
            }
        }
    }


    public static boolean isBedrockPlayer(Player player) {
        if (hasGeyser) {
            return geyserApi.isBedrockPlayer(player.getUniqueId());
        } else {
            return false;
        }
    }

}
