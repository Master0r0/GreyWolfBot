package com.github.master0r0.greywolfbot.Registry;

import com.github.master0r0.greywolfbot.GreyWolfBot;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class CommandExecutor {

    public static boolean execute(MessageReceivedEvent evt, String cmd, String[] args) {
        if (GreyWolfBot.getCommandRegistry().getCommands().containsKey(cmd.toLowerCase())) {
            BaseCommand command = GreyWolfBot.getCommandRegistry().getCommands().get(cmd.toLowerCase());
            if (command.guildOnly()) {
                if (evt.getGuild() != null)
                    runGuildOnly(evt, command, args);
                else
                    evt.getAuthor().getOrCreatePMChannel().sendMessage("You can only use this command from a server");
            } else {
                runGlobal(evt, command, args);
            }
        } else if (GreyWolfBot.getCommandRegistry().getAlias().containsKey(cmd.toLowerCase())) {
            String alias = GreyWolfBot.getCommandRegistry().getAlias().get(cmd.toLowerCase());
            if (GreyWolfBot.getCommandRegistry().getCommands().containsKey(alias)) {
                BaseCommand command = GreyWolfBot.getCommandRegistry().getCommands().get(alias);
                if (command.guildOnly()) {
                    if (evt.getGuild() != null)
                        runGuildOnly(evt, command, args);
                    else
                        evt.getAuthor().getOrCreatePMChannel().sendMessage("You can only use this command from a server");
                } else {
                    runGlobal(evt, command, args);
                }
            }
        }
        return false;
    }

    private static boolean runGuildOnly(MessageReceivedEvent evt, BaseCommand command, String[] args) {
        if (PermissionsManager.hasPermission(evt.getAuthor(), evt.getGuild(), command)) {
            if (command.execute(evt, args))
                return true;
        } else if (evt.getAuthor().equals(GreyWolfBot.getSuperUser())) {
            if (command.execute(evt, args))
                return true;
        }
        return false;
    }

    private static boolean runGlobal(MessageReceivedEvent evt, BaseCommand command, String[] args) {
        if (PermissionsManager.hasPermission(evt.getAuthor(), evt.getGuild(), command)) {
            if (command.execute(evt, args))
                return true;
        } else if (evt.getAuthor().equals(GreyWolfBot.getSuperUser())) {
            if (command.execute(evt, args))
                return true;
        }
        return false;
    }

}
