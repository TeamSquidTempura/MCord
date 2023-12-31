package com.squidtempura.www.mcord;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;

public class messageListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        long id = MCord.config.getLong("chat-channel");
        String string_id = String.valueOf(id);
        if (event.getChannel().getId().equals(string_id)) {
            String message = event.getMessage().getContentRaw();
            String name = event.getAuthor().getName();
            TextComponent broadcast = Component.text("§7["+"§9ディスコード"+"§f|"+name+"§7]"+" §r"+message);
            Bukkit.broadcast(broadcast);
        }
    }
}
