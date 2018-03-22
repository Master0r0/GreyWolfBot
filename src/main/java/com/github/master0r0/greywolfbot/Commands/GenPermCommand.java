package com.github.master0r0.greywolfbot.Commands;

import com.github.master0r0.greywolfbot.Registry.BaseCommand;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class GenPermCommand extends BaseCommand {

    @Override
    public String getName(){
        return "genpgroup";
    }

    @Override
    public boolean execute(MessageReceivedEvent evt, String[] args){
//        if(PermissionsManager.createPermission(args[0],Integer.parseInt(args[1]), Long.parseLong(args[2])))
//            return true;
        return false;
    }

    @Override
    public String getHelp(){
        return "Generates a new permission group for commands";
    }

    @Override
    public boolean guildOnly(){
        return true;
    }

    @Override
    public float getRequiredPermission(){
        return -1f;
    }

}
