package com.github.master0r0.greywolfbot.Commands;

import com.github.master0r0.greywolfbot.API.IBaseCommand;
import org.apache.commons.lang3.ArrayUtils;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class StoreMsgCommand implements IBaseCommand {

    @Override
    public String getName() {
        return "SM";
    }

    @Override
    public boolean execute(MessageReceivedEvent evt, String[] args) {
        int msgAmount = Integer.parseInt(args[0])>25 ? 26 : Integer.parseInt(args[0])+1;
        IMessage[] messages = evt.getChannel().getFullMessageHistory().asArray();
        List<IMessage> storedMessages = new ArrayList<>();
        messages = ArrayUtils.subarray(messages,1,msgAmount);
        ArrayUtils.reverse(messages);
        for(int i=0;i<messages.length;i++){
            if(!messages[i].getContent().isEmpty())
                storedMessages.add(messages[i]);
        }
        EmbedBuilder embedBuilder = generateEmbed(args[1],evt.getAuthor(),storedMessages);
        RequestBuffer.request(() -> evt.getChannel().sendMessage(embedBuilder.build()));
        return true;
    }

    @Override
    public String getHelp() {
        return "Stores the last n messages and their recipient in the servers main chat";
    }

    @Override
    public boolean guildOnly() {
        return true;
    }

    @Override
    public float getRequiredPermission() {
        return 0f;
    }

    private EmbedBuilder generateEmbed(String title, IUser author, List<IMessage> msg){
        EmbedBuilder builder = new EmbedBuilder();
        builder.withAuthorName(author.getName());
        builder.withAuthorIcon(author.getAvatarURL());
        builder.withTitle("Recorded at: "+LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        for(IMessage nMsg : msg){
            builder.appendField(nMsg.getAuthor().getName(),nMsg.getContent(),false);
        }
        return builder;
    }

}
