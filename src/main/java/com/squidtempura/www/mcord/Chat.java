package com.squidtempura.www.mcord;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;


public class Chat implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncChatEvent ev){
        Player p = ev.getPlayer();
        Component message = ev.message();
        TextComponent text = (TextComponent) message;
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
}
