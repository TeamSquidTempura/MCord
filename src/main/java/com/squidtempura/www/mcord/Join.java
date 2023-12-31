package com.squidtempura.www.mcord;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.awt.*;
import java.time.OffsetDateTime;
import java.util.Arrays;


public class Join implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent ev){
        Player p = ev.getPlayer();
        String name = p.getName();
        String title = name+"さんが参加しました";
        String description = "クライアント:"+p.getClientBrandName()+"\nUUID:"+p.getUniqueId();
        int colorCode = 0x00FF00;
        Color color =  new Color(colorCode);
        String url = "https://minotar.net/avatar/"+p.getUniqueId();
        OffsetDateTime timestamp = OffsetDateTime.now();
        MCord.embed(title, description, color, url, timestamp);


        int status_colorCode = 0x00FF00;
        Color status_color = new Color(status_colorCode);
        OffsetDateTime status_timestamp = OffsetDateTime.now();
        MCord.onlinePlayers++;
        MCord.status = "オンラインのプレイヤー: "+MCord.onlinePlayers+"/"+ Bukkit.getMaxPlayers()+"\nTPS: "+ Arrays.toString(Bukkit.getTPS())+"\n BOTのPING: "+MCord.jda.getGatewayPing()+"ms";
        MCord.status(status_color, status_timestamp);

    }
}
