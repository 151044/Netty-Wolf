package com.colin.games.werewolf.client.role;

import com.colin.games.werewolf.client.role.gui.WitchFrame;
import com.colin.games.werewolf.common.message.Message;
import com.colin.games.werewolf.common.roles.Role;
import io.netty.channel.ChannelHandlerContext;

public class Witch implements Role {
    private boolean isHealUsed = false;
    private boolean isKillUsed = false;
    @Override
    public String name() {
        return "Witch";
    }

    @Override
    public void action(ChannelHandlerContext ctx, Message msg) {
        String died = msg.getContent();
        new WitchFrame(died,this);
    }

    @Override
    public boolean isGood() {
        return true;
    }
    public boolean isHealUsed(){
        return isHealUsed;
    }
    public boolean isKillUsed(){
        return isKillUsed;
    }
    public void setKillStatus(boolean toSet){
        isKillUsed = toSet;
    }
    public void setHealStatus(boolean toSet){
        isHealUsed = toSet;
    }
}
