package com.github.master0r0.greywolfbot.Registry;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public abstract class BaseCommand {

    public String getName(){
        return "";
    }

    public boolean execute(MessageReceivedEvent evt, String[] args){
        return false;
    }

    public String getHelp(){
        return "";
    }

    public boolean guildOnly(){
        return true;
    }

    public float getRequiredPermission(){
        return 0;
    }

}
