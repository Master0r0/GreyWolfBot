package com.github.master0r0.greywolfbot;

import com.github.master0r0.greycommands.GreyCommands;
import com.github.master0r0.greywolfbot.Commands.GameCommand;
import com.github.master0r0.greywolfbot.Commands.HelpCommand;
import com.github.master0r0.greywolfbot.Commands.LeaveCommand;
import com.github.master0r0.greywolfbot.Listeners.CommandListener;
import com.github.master0r0.greywolfbot.Listeners.ReadyListener;
import com.github.master0r0.greywolfbot.Util.Config;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.modules.IModule;
import sx.blah.discord.util.DiscordException;

import java.io.File;

public class GreyWolfBot {

    private static Logger logger = LoggerFactory.getLogger("GreyWolfBot");
    private static IDiscordClient client;
    private static boolean kill = false;

    public static String botOwner = "";


    private static GreyWolfBot instance;

    public static void main(String[] args){
        new GreyWolfBot();
        boolean greyCommands = false;
        String jarPath = GreyWolfBot.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        jarPath = jarPath.substring(1,jarPath.lastIndexOf("/") );
        File configDir = new File(jarPath+"/config");
        if(!configDir.isDirectory()) {
            if (!configDir.mkdir())
                logger.error("Failed to make config directory");
        }
        JSONObject config = Config.load(configDir.getPath());
        botOwner = config.getString("Bot Owner");
        if (!kill) {
            client = createClient(config.getString("Bot Token"), true);
            EventDispatcher evtDispatcher = client.getDispatcher();
            evtDispatcher.registerListener(new ReadyListener());
            for (IModule module : client.getModuleLoader().getLoadedModules()) {
                if (module.getName() == "GreyCommands")
                    greyCommands = true;
            }
            if (!greyCommands) {
                CommandListener commandListener = new CommandListener(botOwner);
                commandListener.registerCommand(new HelpCommand());
                commandListener.registerCommand(new LeaveCommand());
                commandListener.registerCommand(new GameCommand());
                evtDispatcher.registerListener(commandListener);
            } else {
                GreyCommands.setSuperuser(botOwner);
            }
            // TODO: MAKE PERMISSION MODULE - WITH LOADING FROM FILE AND SUCH
        }
    }

    public GreyWolfBot(){
        instance = this;
    }

    public static GreyWolfBot getInstance() {
        return instance;
    }

    public static IDiscordClient createClient(String token, boolean login){
        // ClientBuilder instance
        ClientBuilder clientBuilder = new ClientBuilder();
        // Create the client with the given bot token
        clientBuilder.withToken(token);
        try{
            if(login)
                return clientBuilder.login(); // Creates the client and logs in for ya
            else
                return clientBuilder.build(); // Creates the client but leaves the call for login to you
        }catch (DiscordException e){
            logger.error("Connection to Discord could not be made!");
            e.printStackTrace();
            return null;
        }

    }

    public static void createWebhooks(IDiscordClient client){
        for(IGuild guild : client.getGuilds()){
            for(IChannel channel : guild.getChannels()){
                channel.createWebhook(client.getApplicationName()+":"+channel.getName(),client.getApplicationIconURL());
            }
        }
    }

    public static void shutdown(){
        if(client!=null)
            client.logout();
        kill = true;
    }

    public static Logger getLogger(){
        return logger;
    }

}
