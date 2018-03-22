package com.github.master0r0.greywolfbot;

import com.github.master0r0.greywolfbot.Listeners.CommandListener;
import com.github.master0r0.greywolfbot.Listeners.RadioStream;
import com.github.master0r0.greywolfbot.Listeners.ReadyListener;
import com.github.master0r0.greywolfbot.Registry.CommandRegistry;
import com.github.master0r0.greywolfbot.Registry.PermissionsManager;
import com.github.master0r0.greywolfbot.Util.Config;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.modules.IModule;
import sx.blah.discord.util.DiscordException;

import java.io.File;

public class GreyWolfBot {

    private static Logger logger = LoggerFactory.getLogger("GreyWolfBot");
    private static IDiscordClient client;
    private static boolean kill = false;
    private static PermissionsManager permissionsManager;
    private static CommandRegistry commandRegistry = null;
    private static JSONObject config;

    public static IUser superUser = null;
    public static int adminRoleID = 0;


    private static GreyWolfBot instance;

    public static void main(String[] args){
        new GreyWolfBot();
        if (!kill) {
            start();
            new RadioStream(client);
            // TODO: MAKE PERMISSION MODULE - WITH LOADING FROM FILE AND SUCH
        }
    }

    public GreyWolfBot(){
        instance = this;
    }

    public static GreyWolfBot getInstance() {
        return instance;
    }

    public static void start(){
        String jarPath = GreyWolfBot.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        jarPath = jarPath.substring(1,jarPath.lastIndexOf("/") );
        File configDir = new File(jarPath+"/config");
        if(!configDir.isDirectory()) {
            if (!configDir.mkdir())
                logger.error("Failed to make config directory");
        }
        config = Config.load(configDir.getPath());
        adminRoleID = config.getInt("Admin Role ID");
        client = createClient(config.getString("Bot Token"), true);
        superUser = client.getApplicationOwner();
        EventDispatcher evtDispatcher = client.getDispatcher();
        evtDispatcher.registerListener(new ReadyListener());
        for (IModule module : client.getModuleLoader().getLoadedModules()) {

        }
            commandRegistry = new CommandRegistry();
            CommandListener commandListener = new CommandListener();
            permissionsManager = new PermissionsManager(client);
            commandRegistry.loadDefaults();
            evtDispatcher.registerListener(commandListener);
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
    public static void shutdown(){
        if(client!=null)
            client.logout();
        kill = true;
    }

    public static Logger getLogger(){
        return logger;
    }

    public static PermissionsManager getPermissionsManager() {
        return permissionsManager;
    }

    public static IUser getSuperUser() {
        return superUser;
    }

    public static IDiscordClient getClient(){
        return client;
    }

    public static CommandRegistry getCommandRegistry() {
        return commandRegistry;
    }

    public static JSONObject getConfig() {
        return config;
    }
}
