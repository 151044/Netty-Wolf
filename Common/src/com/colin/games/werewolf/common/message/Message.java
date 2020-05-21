package com.colin.games.werewolf.common.message;

/**
 * The class which is transferred over the network.
 * It consists of 2 components: Type and content.
 * The type determines how the message is handled.
 * The content is the actual content of the message.
 */
public class Message {
    private final String type;
    private final String content;
    public Message(String type,String content){
        this.type = type;
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return type + "://:" + content;
    }
}
