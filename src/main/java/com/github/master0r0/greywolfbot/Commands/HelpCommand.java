package com.github.master0r0.greywolfbot.Commands;

import com.github.master0r0.greywolfbot.GreyWolfBot;
import com.github.master0r0.greywolfbot.Registry.BaseCommand;
import com.github.master0r0.greywolfbot.Registry.PermissionsManager;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IPrivateChannel;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;

public class HelpCommand extends BaseCommand {
    @Override
    public String getName() {
        return "help";
    }

    @Override
    public boolean execute(MessageReceivedEvent evt, String[] args) {
        IPrivateChannel pmChannel = evt.getAuthor().getOrCreatePMChannel();
        EmbedBuilder embed = new EmbedBuilder();
        embed.withAuthorName(evt.getGuild().getName());
        embed.withAuthorIcon(evt.getGuild().getIconURL());
        embed.withTitle("Commands");
        embed.withDesc("");
        for (String cmd : GreyWolfBot.getCommandRegistry().getCommands().keySet()) {
            BaseCommand iCmd = GreyWolfBot.getCommandRegistry().getCommands().get(cmd);
            if (PermissionsManager.hasPermission(evt.getAuthor(), evt.getGuild(), iCmd)) {
                if (!iCmd.getHelp().equals(""))
                    embed.appendField("!" + cmd, GreyWolfBot.getCommandRegistry().getCommands().get(cmd).getHelp(), false);
            } else {
                if (!iCmd.getHelp().equals(""))
                    if (evt.getAuthor().equals(GreyWolfBot.superUser))
                        embed.appendField("!" + cmd, GreyWolfBot.getCommandRegistry().getCommands().get(cmd).getHelp(), false);
            }
        }
        RequestBuffer.request(() -> pmChannel.sendMessage(embed.build()));
        return true;
    }

    @Override
    public String getHelp() {
        return "";
    }

    @Override
    public boolean guildOnly() {
        return true;
    }

    @Override
    public float getRequiredPermission() {
        return 0f;
    }
}
