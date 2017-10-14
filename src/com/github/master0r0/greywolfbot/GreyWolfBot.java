package com.github.master0r0.greywolfbot;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

import java.util.logging.Logger;

public class GreyWolfBot {

    private static final Logger logger = Logger.getLogger("GreyWolfBot");
    private static IDiscordClient client;

    public static void main(String[] args){
        if(!(args.length >0)){
            StringBuilder failMsg = new StringBuilder().append("\nNo Arguments were provided\n")
                    .append("To start the bot a valid token must be provided\n")
                    .append("Example Command: GreyWolfBot.jar 123456789abcdefghijklmnopqrstuvwxyz");
            logger.severe(failMsg.toString());
        }else{
            client = createClient(args[0],true);
        }
    }

    public GreyWolfBot(){

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
            logger.severe("Connection to Discord could not be made!");
            e.printStackTrace();
            return null;
        }

    }

    public static Logger getLogger(){
        return logger;
    }

}
