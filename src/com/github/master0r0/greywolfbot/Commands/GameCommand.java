package com.github.master0r0.greywolfbot.Commands;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IGuild;

public class GameCommand extends BaseCommand {

    @Override
    public String getCommandName(){
        return "game";
    }

    @Override
    public boolean execute(MessageReceivedEvent evt){
        StringBuilder out = new StringBuilder();
        for(int i = 1;i<evt.getMessage().getContent().split(" ").length;i++){
            if(evt.getMessage().getContent().split(" ")[1].equals("clear")) {
                evt.getClient().changePlayingText("");
                return true;
            }else
                out.append(evt.getMessage().getContent().split(" ")[i]).append(" ");
        }
        evt.getClient().changePlayingText(out.toString());
        return true;
    }

    @Override
    public String getHelp(){
        return "Sets the playing text for the bot";
    }

    @Override
    public int getRequiredPermission(){
        return 100;
    }
}
