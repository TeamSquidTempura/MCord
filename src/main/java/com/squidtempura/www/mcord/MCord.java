package com.squidtempura.www.mcord;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Arrays;


public final class MCord extends JavaPlugin {
    private static final Logger log = LoggerFactory.getLogger(MCord.class);
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MCord.class.getName());
    public static FileConfiguration config = null;
    public static TextChannel chat_channel;
    public static TextChannel status_channel;
    public static TextChannel admin_log_channel;
    private static MCord instance;
    public static JDA jda;
    public static Guild guild;
    public static String mode;
    public static String status;
    public static int onlinePlayers = 0;


    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = getConfig();
        instance = this;
        getServer().getPluginManager().registerEvents(new Listener(), this);
        String rpc = config.getString("rpc");
        try {
            if (jda != null) return;

             jda = JDABuilder.createDefault(config.getString("token"))
                     .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.MESSAGE_CONTENT)
                     .addEventListeners(new messageListener())
                     .addEventListeners(new commandListener())
                     .setActivity(Activity.watching(rpc))
                     .build();
            jda.awaitReady();
            long guild_id = config.getLong("guild-id");
            long chat_channel_id = config.getLong("chat-channel");
            long status_channel_id = config.getLong("status-channel");
            long admin_log_channel_id = config.getLong("admin-log-channel");
            guild = jda.getGuildById(guild_id);
            if (guild != null) {
                chat_channel = guild.getTextChannelById(chat_channel_id);
                status_channel = guild.getTextChannelById(status_channel_id);
                admin_log_channel = guild.getTextChannelById(admin_log_channel_id);
            } else {
                getLogger().severe("Guild not found!");
            }
        } catch (Exception e) {
            log.error("エラーが発生しました", e);
        }
        chat("**"+config.getString("server-name")+"が起動しました！ :white_check_mark: **");
        CommandListUpdateAction commands = jda.updateCommands();
        commands.addCommands(
                Commands.slash("command", "サーバーでコマンドを実行")
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR))
                        .setGuildOnly(true)
                        .addOption(OptionType.STRING, "コマンド内容", "ここに入力"),
                Commands.slash("tell", "鯖内のプレイヤーにプライベートメッセージを送信")
                        .addOption(OptionType.STRING, "プレイヤー名", "ここに入力")
                        .addOption(OptionType.STRING, "メッセージ内容", "ここに入力")
                        .setGuildOnly(true),
                Commands.slash("ping", "鯖内のプレイヤーのPINGを確認")
                        .addOption(OptionType.STRING, "プレイヤー名", "ここに入力")
                        .setGuildOnly(true),
                Commands.slash("maxplayer", "最大人数")
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR))
                        .setGuildOnly(true)
                        .addOption(OptionType.INTEGER, "鯖の最大人数を設定", "ここに入力"),
                Commands.slash("player", "プレイヤーの情報を取得")
                        .setGuildOnly(true)
                        .addOption(OptionType.STRING, "プレイヤー名", "ここに入力")
        ).queue();
        int colorCode = 0x00FF00;
        Color color = new Color(colorCode);
        OffsetDateTime timestamp = OffsetDateTime.now();
        onlinePlayers = 0;
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            onlinePlayers++;
        }
        mode = "オンライン :white_check_mark: ";
        status = "オンラインのプレイヤー: "+onlinePlayers+"/"+Bukkit.getMaxPlayers()+"\nTPS: "+ Arrays.toString(Bukkit.getTPS())+"\n BOTのPING: "+jda.getGatewayPing()+"ms";
        status(color, timestamp);
    }
    @Override
    public void onDisable() {
        chat("**"+config.getString("server-name")+"が停止しました！:red_circle: **");

        int colorCode = 0xFF0000;
        Color color = new Color(colorCode);
        OffsetDateTime timestamp = OffsetDateTime.now();
        mode = "オフライン :red_circle:";
        long unixTimestamp = Instant.now().getEpochSecond();
        status = "<t:" + unixTimestamp + ":R>"+"にサーバーが停止";
        status(color, timestamp);
    }
    public static MCord getInstance() {
        return instance;
    }
    public static void chat(String message){
        try {
            if (guild != null) {
                if (chat_channel != null){
                    chat_channel.sendMessage(message).queue();
                } else {
                    logger.severe("Chat channel not found!");
                }
            } else {
                logger.severe("Guild not found!");
            }
        } catch (Exception e){
            log.error("エラーが発生しました", e);
        }
    }
    public static void embed(String title, String description, Color color, String url, OffsetDateTime timestamp){

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle(title);
        embedBuilder.setDescription(description);
        embedBuilder.setColor(color);
        embedBuilder.setThumbnail(url);
        embedBuilder.setTimestamp(timestamp);
        MessageEmbed embed = embedBuilder.build();
        try {
            if (guild != null) {
                if (chat_channel != null){
                    chat_channel.sendMessageEmbeds(embed).queue();
                } else {
                    logger.severe("Chat channel not found!");
                }
            } else {
                logger.severe("Guild not found!");
            }
        } catch (Exception e){
            log.error("エラーが発生しました", e);
        }

    }
    public static void status(Color color,OffsetDateTime timestamp){
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle(mode);
        embedBuilder.setDescription(status);
        embedBuilder.setColor(color);
        embedBuilder.setTimestamp(timestamp);
        MessageEmbed embed = embedBuilder.build();
        try {
            if (guild != null){
                if (status_channel != null){
                    long id = status_channel.getLatestMessageIdLong();
                    if (id == 0){
                        status_channel.sendMessageEmbeds(embed).queue();
                    } else{
                        status_channel.editMessageEmbedsById(id).setEmbeds(embed).queue();
                    }
                } else{
                    logger.severe("Status channel not found!");
                }
            } else{
                logger.severe("Guild not found!");
            }
        }catch (Exception e){
            log.error("エラーが発生しました", e);
        }
    }
    public static void log(String title, String description, Color color, String url, OffsetDateTime timestamp){
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle(title);
        embedBuilder.setDescription(description);
        embedBuilder.setColor(color);
        embedBuilder.setThumbnail(url);
        embedBuilder.setTimestamp(timestamp);
        MessageEmbed embed = embedBuilder.build();
        try {
            if (guild != null) {
                if (admin_log_channel != null){
                    admin_log_channel.sendMessageEmbeds(embed).queue();
                } else {
                    logger.severe("Chat channel not found!");
                }
            } else {
                logger.severe("Guild not found!");
            }
        } catch (Exception e){
            log.error("エラーが発生しました", e);
        }
    }
}
