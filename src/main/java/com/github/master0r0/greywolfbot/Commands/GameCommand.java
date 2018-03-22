package com.github.master0r0.greywolfbot.Commands;

import com.github.master0r0.greywolfbot.Registry.BaseCommand;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class GameCommand extends BaseCommand {

    @Override
    public String getName(){
        return "game";
    }

    @Override
    public boolean execute(MessageReceivedEvent evt, String[] args){
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
    public float getRequiredPermission(){
        return -1f;
    }
}
