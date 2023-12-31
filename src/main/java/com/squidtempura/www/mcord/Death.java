package com.squidtempura.www.mcord;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.awt.*;
import java.time.OffsetDateTime;

public class Death implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent ev){
        if (!MCord.config.getBoolean("death-location"))return;
        Player p =ev.getPlayer();
        String name = p.getName();
        Location loc = p.getLastDeathLocation();
        int x = (int) loc.getX();
        int y = (int) loc.getY();
        int z = (int) loc.getZ();
        String world = loc.getWorld().getName();
        String message = name+"さんが"+world+"の"+"\nx:"+x+" y:"+y+" z:"+z+"\nで死亡しました";
        int colorCode = 0x5A5A5A;
        Color color =  new Color(colorCode);
        String url = "https://minotar.net/avatar/"+p.getUniqueId();
        OffsetDateTime timestamp = OffsetDateTime.now();
        MCord.embed("死亡通知", message, color, url, timestamp);
        TextComponent broadcast = Component.text("§c"+message);
        Bukkit.broadcast(broadcast);
    }
}
