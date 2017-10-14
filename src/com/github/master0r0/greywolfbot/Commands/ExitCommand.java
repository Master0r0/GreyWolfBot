package com.github.master0r0.greywolfbot.Commands;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IGuild;

public class ExitCommand extends BaseCommand {

    @Override
    public String getCommandName(){
        return "exit";
    }

    @Override
    public boolean execute(MessageReceivedEvent evt){
        for(IGuild guild : evt.getClient().getGuilds()){
            guild.getDefaultChannel().sendMessage("Leaving Now!");
            evt.getClient().logout();
        }
        return true;
    }

    @Override
    public String getHelp(){
        return "";
    }

    @Override
    public int getRequiredPermission(){
        return 100;
    }

}
