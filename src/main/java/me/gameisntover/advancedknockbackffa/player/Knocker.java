package me.gameisntover.advancedknockbackffa.player;

import org.bukkit.Location;

import java.util.UUID;

public interface Knocker {
    String getName();

    Location getLocation();

    UUID getUniqueId();


}
