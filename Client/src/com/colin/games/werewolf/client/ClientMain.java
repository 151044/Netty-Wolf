package com.colin.games.werewolf.client;

import com.colin.games.werewolf.client.gui.ConnectFrame;
import com.colin.games.werewolf.client.gui.ExceptionFrame;
import com.colin.games.werewolf.common.Environment;
import com.colin.games.werewolf.common.message.Message;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class ClientMain {
    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new NimbusLookAndFeel());
        Environment.setSide(Environment.Side.CLIENT);
        Thread.setDefaultUncaughtExceptionHandler((t,ex) -> {
            ex.printStackTrace();
            if(!(ex instanceof Exception)){
                System.exit(2);
            }
            new ExceptionFrame((Exception) ex,t);
        });
        ConnectFrame cf = new ConnectFrame();
        SwingUtilities.invokeLater(cf::init);
        Runtime.getRuntime().addShutdownHook(new Thread(()-> {
            Client.getCurrent().getChannel().write(new Message("disconnect",Client.getCurrent().getName()));
            Client.getCurrent().getChannel().flush();
        }
        ));
    }
}
