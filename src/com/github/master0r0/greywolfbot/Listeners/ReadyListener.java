package com.github.master0r0.greywolfbot.Listeners;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.IGuild;

public class ReadyListener {

    @EventSubscriber
    public void onReadyEvent(ReadyEvent evt){
        for(IGuild guild : evt.getClient().getGuilds()){

        }
    }

}
