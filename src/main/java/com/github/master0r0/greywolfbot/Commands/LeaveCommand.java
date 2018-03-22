package com.github.master0r0.greywolfbot.Commands;

import com.github.master0r0.greywolfbot.Registry.BaseCommand;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class LeaveCommand extends BaseCommand {

    @Override
    public String getName(){
        return "leave";
    }

    @Override
    public boolean execute(MessageReceivedEvent evt, String[] args){
        evt.getAuthor().getOrCreatePMChannel().sendMessage("Leaving Now!");
        evt.getClient().logout();
        return true;
    }

    @Override
    public String getHelp(){
        return "Causes the Bot to shutdown";
    }

    @Override
    public boolean guildOnly(){
        return false;
    }

    @Override
    public float getRequiredPermission(){
        return -1f;
    }

}
