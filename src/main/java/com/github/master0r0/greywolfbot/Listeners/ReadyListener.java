package com.github.master0r0.greywolfbot.Listeners;

import com.github.master0r0.greywolfbot.Registry.PermissionsManager;
import com.github.master0r0.greywolfbot.GreyWolfBot;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.GuildCreateEvent;

public class ReadyListener {

    private boolean firstTime = true;

    @EventSubscriber
    public void onGuildJoin(GuildCreateEvent evt){
        if(firstTime) {
//            GreyWolfBot.getLogger().info("Creating Default Permissions");
//            if(PermissionsManager.createPermission("Minimal", 0, evt.getGuild().getEveryoneRole()))
//                GreyWolfBot.getLogger().info("Successfully created Minimal Permission");
//            if(PermissionsManager.createPermission("Admin", 1, evt.getGuild().getRoleByID(GreyWolfBot.adminRoleID)))
//                GreyWolfBot.getLogger().info("Successfully created Admin Permission");
//            PermissionsManager.loadRoles(evt.getGuild());
            firstTime = false;
        }
    }

}
