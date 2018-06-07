package com.github.master0r0.greywolfbot.Registry;

import com.github.master0r0.greywolfbot.API.IBaseCommand;
import com.github.master0r0.greywolfbot.GreyWolfBot;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;

import java.util.HashMap;
import java.util.Map;

public class PermissionsManager {

    public static Map<Float, IRole> permissionMap = new HashMap<>();
    private IDiscordClient client;

    public PermissionsManager(IDiscordClient client){
        this.client = client;
    }

    public static boolean hasPermission(IUser user, IGuild server, IBaseCommand command){
        if(server!=null){
            if(user.getRolesForGuild(server).contains(permissionMap.get(command.getRequiredPermission())))
                return true;
        }else{
            if(user==GreyWolfBot.getSuperUser())
                return true;
        }
        return false;

    }

    public static boolean createPermission(float value, IRole role){
        permissionMap.put(value,role);
        if(permissionMap.get(value)!=null)
            return true;
        return false;
    }


}
