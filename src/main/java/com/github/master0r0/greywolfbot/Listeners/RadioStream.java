package com.github.master0r0.greywolfbot.Listeners;

import com.github.master0r0.greywolfbot.GreyWolfBot;
import com.github.master0r0.greywolfbot.Util.IcyStreamMeta;
import org.json.JSONObject;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IEmbed;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;

import java.io.IOException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class RadioStream {

    private IcyStreamMeta streamMeta = null;
    private Timer timer;
    private String data = "";
    private JSONObject radioConfig;


    public RadioStream(IDiscordClient client) {
        radioConfig = GreyWolfBot.getConfig().getJSONObject("RadioStream");
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    streamMeta = new IcyStreamMeta(new URL(radioConfig.getString("IP") + ":" + radioConfig.getString("Port")));
                    String artist = streamMeta.getArtist().isEmpty() ? "Doesn't Exist" : streamMeta.getArtist();
                    String title = streamMeta.getTitle().isEmpty() ? "" : streamMeta.getTitle();
                    emit(client,title,artist);
                } catch (IOException e) {
                    GreyWolfBot.getLogger().warn("Unable to Connect to Radio Stream. Reason: " + e.getMessage());
                    timer.cancel();
                    //e.printStackTrace();
                }
            }
        },0,90000);
    }

    private void emit(IDiscordClient client, String title, String artist){
        for(IGuild guild : client.getGuilds()){
            if(guild.getDefaultChannel().getFullMessageHistory().getLatestMessage().getEmbeds().size()>0) {
                IEmbed lastEmbed = guild.getDefaultChannel().getFullMessageHistory().getLatestMessage().getEmbeds().get(0);
                if (lastEmbed.getAuthor().getName().equals(radioConfig.getString("Name")+" Radio")) {
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.withAuthorName(radioConfig.getString("Name")+" Radio");
                    embed.withTitle("Currently Playing:");
                    embed.withDesc("");
                    embed.appendField(title, artist, false);
                    embed.withColor(0, 255, 0);
                    embed.withAuthorIcon(radioConfig.getString("Icon"));
                    guild.getDefaultChannel().getFullMessageHistory().getLatestMessage().edit(embed.build());
                } else {
                    RequestBuffer.request(() -> guild.getDefaultChannel().sendMessage(generateEmbed(title,artist).build()));
                }
            }else
                RequestBuffer.request(() -> guild.getDefaultChannel().sendMessage(generateEmbed(title,artist).build()));
        }
    }

    private EmbedBuilder generateEmbed(String title, String artist){
        EmbedBuilder embed = new EmbedBuilder();
        embed.withAuthorName(radioConfig.getString("Name")+" Radio");
        embed.withTitle("Currently Playing:");
        embed.withDesc("");
        embed.appendField(title, artist, false);
        embed.withColor(0, 255, 0);
        embed.withAuthorIcon(radioConfig.getString("Icon"));
        return embed;
    }

    public void shutdown(){
        timer.cancel();
    }

    public String getData() {
        return data;
    }
}
