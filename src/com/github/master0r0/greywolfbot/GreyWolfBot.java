package com.github.master0r0.greywolfbot;

import com.github.master0r0.greycommands.GreyCommands;
import com.github.master0r0.greywolfbot.Commands.LeaveCommand;
import com.github.master0r0.greywolfbot.Commands.GameCommand;
import com.github.master0r0.greywolfbot.Commands.HelpCommand;
import com.github.master0r0.greywolfbot.Listeners.CommandListener;
import com.github.master0r0.greywolfbot.Listeners.ReadyListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.modules.IModule;
import sx.blah.discord.util.DiscordException;

public class GreyWolfBot {

    private static Logger logger = LoggerFactory.getLogger("GreyWolfBot");
    private static IDiscordClient client;


    private static GreyWolfBot instance;

    public static void main(String[] args){
        new GreyWolfBot();
        boolean greyCommands = false;
        if(!(args.length >1)){
            StringBuilder failMsg = new StringBuilder().append("\nNo Arguments were provided\n")
                    .append("To start the bot a valid token must be provided\n")
                    .append("Also the username of the Super Admin for the bot should be provided")
                    .append("Example Command: GreyWolfBot.jar 123456789abcdefghijklmnopqrstuvwxyz master0r0");
            logger.error(failMsg.toString());
        }else{
            client = createClient(args[0],true);
            EventDispatcher evtDispatcher = client.getDispatcher();
            evtDispatcher.registerListener(new ReadyListener());
            for(IModule module : client.getModuleLoader().getLoadedModules()){
                if(module.getName()=="GreyCommands")
                    greyCommands = true;
            }
            if(!greyCommands) {
                CommandListener commandListener = new CommandListener(args[1]);
                commandListener.registerCommand(new HelpCommand());
                commandListener.registerCommand(new LeaveCommand());
                commandListener.registerCommand(new GameCommand());
                evtDispatcher.registerListener(commandListener);
            }else{
                GreyCommands.setSuperuser(args[1]);
            }
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

    public static Logger getLogger(){
        return logger;
    }

}
