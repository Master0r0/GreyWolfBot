package com.github.master0r0.greywolfbot.Commands;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public abstract class BaseCommand {

    public boolean execute(MessageReceivedEvent evt){
        return false;
    }

    public String getHelp(){
        return "";
    }

    public String getCommandName(){
        return "";
    }

    public int getRequiredPermission(){
        return 100;
    }

}
