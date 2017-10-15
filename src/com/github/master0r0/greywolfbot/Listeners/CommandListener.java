package com.github.master0r0.greywolfbot.Listeners;

import com.github.master0r0.greywolfbot.Commands.BaseCommand;
import com.github.master0r0.greywolfbot.Commands.HelpCommand;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.Permissions;

import java.util.HashMap;
import java.util.Map;

public class CommandListener {

    private Map<String,BaseCommand> commands = new HashMap<>();
    private String superuser;

    public CommandListener(String superuser){
        this.superuser = superuser;
    }

    public boolean registerCommand(BaseCommand command){
        if(!commands.containsKey(command.getCommandName())) {
            commands.put(command.getCommandName(),command);
        }
        return false;
    }

    @EventSubscriber
    public void onMessageReceived(MessageReceivedEvent evt){
        if(evt.getMessage().getGuild()!=null) {
            boolean done = false;
            if (evt.getMessage().getContent().startsWith("!")) {
                String[] splitString = evt.getMessage().getContent().split(" ");
                String cmd = evt.getMessage().getContent().substring(1, splitString[0].length());
                if (commands.containsKey(cmd)) {
                    BaseCommand command = commands.get(cmd);
                    if (command.getRequiredPermission() != 100) {
                        for (IRole role : evt.getAuthor().getRolesForGuild(evt.getGuild())) {
                            for (Permissions perm : role.getPermissions()) {
                                if (perm.hasPermission(command.getRequiredPermission())) {
                                    if (cmd.equals("help")) {
                                        ((HelpCommand) command).execute(evt, commands, superuser);
                                        done = true;
                                        break;
                                    } else {
                                        command.execute(evt);
                                        done = true;
                                        break;
                                    }
                                }
                            }
                            if (done)
                                break;
                        }
                    } else {
                        if (evt.getAuthor().getName().equals(superuser)) {
                            command.execute(evt);
                        }
                    }
                }
            }
        }
    }

}
