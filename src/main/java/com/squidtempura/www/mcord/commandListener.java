package com.squidtempura.www.mcord;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.time.OffsetDateTime;

public class commandListener extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent ev) {
        switch (ev.getName()) {
            case "command":
                Bukkit.getScheduler().runTask(MCord.getInstance(), () -> {
                    OptionMapping command_String = ev.getOption("コマンド内容");
                    String command_command = "";
                    if (command_String != null){
                        command_command =  command_String.getAsString();
                    }
                    ev.reply("コマンドが実行されました！").setEphemeral(true).queue();
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command_command);

                    int command_colorCode = 0xffa500;
                    Color command_color = new Color(command_colorCode);
                    OffsetDateTime command_timestamp = OffsetDateTime.now();
                    MCord.log(ev.getUser().getName()+"さんがコマンドを実行しました", "内容:"+command_command, command_color, ev.getUser().getAvatarUrl(), command_timestamp);
                });
                break;
            case "tell":
                OptionMapping tell_name = ev.getOption("プレイヤー名");
                OptionMapping tell_message = ev.getOption("メッセージ内容");
                String tell_String_name = "";
                String tell_String_message = "";
                if (tell_name != null){
                    tell_String_name = tell_name.getAsString();
                }
                if (tell_message != null){
                    tell_String_message = tell_message.getAsString();
                }
                String tell_user = ev.getUser().getName();
                Player tell_p = Bukkit.getPlayer(tell_String_name);
                if (tell_p == null){
                    ev.reply(tell_String_name+"さんはオフラインまたは、存在しません").setEphemeral(true).queue();
                    return;
                }
                tell_p.sendMessage("§b"+tell_user+"からのプライベートメッセージ=>§f"+tell_String_message);
                ev.reply(tell_String_name+"さんにプライベートメッセージを送信しました").setEphemeral(true).queue();
                int tell_colorCode = 0xffa500;
                Color tell_color = new Color(tell_colorCode);
                OffsetDateTime tell_timestamp = OffsetDateTime.now();
                MCord.log("tellコマンド", tell_user+"さんが"+tell_String_name+"さんにプライベートメッセージを送信\n内容: "+tell_String_message, tell_color, ev.getUser().getAvatarUrl(), tell_timestamp);
                break;

            case "maxplayer":
                OptionMapping maxplayer_amount = ev.getOption("鯖の最大人数を設定");
                if (maxplayer_amount == null)return;
                int maxplayer_int = maxplayer_amount.getAsInt();
                Bukkit.setMaxPlayers(maxplayer_int);
                ev.reply("サーバーの最大人数を"+maxplayer_int+"人に設定しました").setEphemeral(true).queue();
                int maxplayer_colorCode = 0xffa500;
                String maxplayer_user = ev.getUser().getName();
                Color maxplayer_color = new Color(maxplayer_colorCode);
                OffsetDateTime maxplayer_timestamp = OffsetDateTime.now();
                MCord.log("tellコマンド", maxplayer_user+"さんが鯖の最大人数を"+maxplayer_int+"人に設定しました", maxplayer_color, ev.getUser().getAvatarUrl(), maxplayer_timestamp);
                break;
            case "player":
                OptionMapping player_player = ev.getOption("プレイヤー名");
                String player_String = "";
                if (player_player != null){
                    player_String = player_player.getAsString();
                }
                String player_user = ev.getUser().getName();
                Player player_p = Bukkit.getPlayer(player_user);
                if (player_p == null){
                    ev.reply(player_String+"さんはオフラインまたは、存在しません").setEphemeral(true).queue();
                    return;
                }
                break;
        }
    }
}
