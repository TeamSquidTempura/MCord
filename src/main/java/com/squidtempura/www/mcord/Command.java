package com.squidtempura.www.mcord;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.awt.*;
import java.time.OffsetDateTime;

public class Command implements Listener {
    @EventHandler
    public void onPlayerCommandEvent(PlayerCommandPreprocessEvent ev){
        Player p = ev.getPlayer();
        String name = p.getName();
        String command = ev.getMessage();
        int colorCode = 0x00008B;
        Color color = new Color(colorCode);
        String url = "https://minotar.net/avatar/"+p.getUniqueId();
        OffsetDateTime timestamp = OffsetDateTime.now();
        MCord.log("コマンド使用", name+"さんがコマンドを使用しました。\n内容: "+command, color, url, timestamp);
    }
}
