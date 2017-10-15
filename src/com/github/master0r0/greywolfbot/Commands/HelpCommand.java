package com.github.master0r0.greywolfbot.Commands;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IPrivateChannel;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.Permissions;

import java.util.Map;

public class HelpCommand {

    public String getCommandName(){
        return "help";
    }

    public boolean execute(MessageReceivedEvent evt, Map<String,BaseCommand> commands){
        IPrivateChannel pmChannel = evt.getAuthor().getOrCreatePMChannel();
        StringBuilder help = new StringBuilder();
        help.append("Commands you can use on server "+evt.getGuild().getName()+" are:\n")
            .append("-------------------------------------\n");
        for(String cmd : commands.keySet()){
            boolean found = false;
            for(IRole role : evt.getAuthor().getRolesForGuild(evt.getGuild())){
                if(!found) {
                    for (Permissions perm : role.getPermissions()) {
                        if (commands.get(cmd).getRequiredPermission() == perm.ordinal()) {
                            found = true;
                            help.append(String.format("!%s: %s\n", cmd, commands.get(cmd).getHelp()));
                        }
                        if(found)
                            break;
                    }
                }
            }
        }
        pmChannel.sendMessage(help.toString());
        return true;
    }

    public int getRequiredPermission(){
        return Permissions.SEND_MESSAGES.ordinal();
    }

}
