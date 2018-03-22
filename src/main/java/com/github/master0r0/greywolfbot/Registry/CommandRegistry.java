package com.github.master0r0.greywolfbot.Registry;

import com.github.master0r0.greywolfbot.Commands.GameCommand;
import com.github.master0r0.greywolfbot.Commands.GenPermCommand;
import com.github.master0r0.greywolfbot.Commands.HelpCommand;
import com.github.master0r0.greywolfbot.Commands.LeaveCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandRegistry {

    private Map<String,BaseCommand> commands = new HashMap<>();
    private Map<String,String> alias = new HashMap<>();

    public CommandRegistry(){

    }

    public void registerCommand(BaseCommand command){
        if(!commands.containsKey(command.getName().toLowerCase())){
            commands.put(command.getName().toLowerCase(),command);
        }
    }

    public boolean unregisterCommand(String key){
        if(commands.containsKey(key.toLowerCase())){
            commands.remove(key.toLowerCase());
            if(!commands.containsKey(key.toLowerCase())) {
                for(String nAlias : alias.keySet()){
                    if(alias.get(nAlias)==key.toLowerCase()){
                        unregisterAlias(nAlias);
                    }
                }
                return true;
            }
        }
        return false;
    }

    public void loadDefaults(){
        this.registerCommand(new HelpCommand());
        this.registerCommand(new LeaveCommand());
        this.registerCommand(new GameCommand());
        //this.registerCommand(new GenPermCommand());
    }

    public void registerAlias(BaseCommand command, String alias){
        if(!this.alias.containsKey(alias.toLowerCase())){
            this.alias.put(alias.toLowerCase(),command.getName().toLowerCase());
        }
    }

    public boolean unregisterAlias(String key){
        if(alias.containsKey(key)){
            alias.remove(key);
            if(!alias.containsKey(key))
                return true;
        }
        return false;
    }

    public Map<String, BaseCommand> getCommands() {
        return commands;
    }

    public Map<String, String> getAlias() {
        return alias;
    }
}
