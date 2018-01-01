package com.github.master0r0.greywolfbot.Util;

import com.github.master0r0.greywolfbot.GreyWolfBot;
import org.json.JSONObject;

import java.io.*;

public class Config {

    public static JSONObject load(String path){
        File cFile = new File(path+"/GreyWolfBot.json");
        JSONObject cObject = new JSONObject();
        if(!cFile.exists()){
            try{
                if(!cFile.createNewFile())
                    GreyWolfBot.getLogger().error("Failed to create config file!");
                else{
                    BufferedWriter writer = new BufferedWriter(new FileWriter(cFile));
                    cObject = writeDefault();
                    writer.write(cObject.toString(4));
                    writer.flush();
                    writer.close();
                    GreyWolfBot.shutdown();
                    GreyWolfBot.getLogger().info("Config file created, please take your time to configure it");
                    return null;
                }
            }catch(IOException e){
                e.printStackTrace();
                return null;
            }
        }else{
            try {
                BufferedReader reader = new BufferedReader(new FileReader(cFile));
                StringBuilder json = new StringBuilder();
                String line = reader.readLine();
                while(line!=null){
                    json.append(line);
                    json.append("\n");
                    line = reader.readLine();
                }
                cObject = new JSONObject(json.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return cObject;
    }

    private static JSONObject writeDefault(){
        JSONObject defaultObj = new JSONObject();
        defaultObj.put("Bot Name","GreyWolfBot");
        defaultObj.put("Bot Token","123456789abcdefghijklmnopqrstuvwxyz");
        defaultObj.put("Bot Owner","Owners Username");
        return defaultObj;
    }

}
