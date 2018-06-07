package com.github.master0r0.greywolfbot.API;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public interface IBaseCommand {

    public String getName();

    public boolean execute(MessageReceivedEvent evt, String[] args);

    public String getHelp();

    public boolean guildOnly();

    public float getRequiredPermission();

}
