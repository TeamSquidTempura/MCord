package com.squidtempura.www.mcord;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.awt.*;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Arrays;

public class Listener implements org.bukkit.event.Listener {
    @EventHandler
    public void onPlayerChat(AsyncChatEvent ev){
        Player p = ev.getPlayer();
        Component message = ev.message();
        net.kyori.adventure.text.TextComponent text = (net.kyori.adventure.text.TextComponent) message;
        String content = text.content();
        String name = p.getName();
        String chat = "["+MCord.config.getString("server-name")+"]"+name+":"+content;
        if (content.contains("@everyone") || content.equals("@here")){
            MCord.chat(":fire:"+name+"さんがeveryone又は、hereメンションをしたため、メッセージをキャンセルしました");
            TextComponent component = Component.text("§4"+name+"さんがeveryone又は、hereメンションをしたため、メッセージをキャンセルしました");
            Bukkit.broadcast(component);
            ev.setCancelled(true);
            return;
        }
        MCord.chat(chat);

    }
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
        net.kyori.adventure.text.TextComponent broadcast = Component.text("§c"+message);
        Bukkit.broadcast(broadcast);
    }
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
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent ev){
        Player p = ev.getPlayer();
        String name = p.getName();
        String title = name+"さんが退出しました";
        OffsetDateTime timestamp = OffsetDateTime.now();
        long unixTimestamp = Instant.now().getEpochSecond();
        String description = "<t:" + unixTimestamp + ":R>"+"に退出";
        int colorCode = 0xFF0000;
        Color color =  new Color(colorCode);
        String url = "https://minotar.net/avatar/"+p.getUniqueId();
        MCord.embed(title, description, color, url, timestamp);

        int status_colorCode = 0x00FF00;
        Color status_color = new Color(status_colorCode);
        OffsetDateTime status_timestamp = OffsetDateTime.now();
        MCord.onlinePlayers--;
        MCord.status = "オンラインのプレイヤー: "+MCord.onlinePlayers+"/"+ Bukkit.getMaxPlayers()+"\nTPS: "+ Arrays.toString(Bukkit.getTPS())+"\n BOTのPING: "+MCord.jda.getGatewayPing()+"ms";
        MCord.status(status_color, status_timestamp);

    }
}
