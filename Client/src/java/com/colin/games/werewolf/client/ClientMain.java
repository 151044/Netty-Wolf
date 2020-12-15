/*
 * Netty-Wolf
 * Copyright (C) 2020  Colin Chow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.colin.games.werewolf.client;

import com.colin.games.werewolf.client.audio.Audio;
import com.colin.games.werewolf.client.gui.StartMenu;
import com.colin.games.werewolf.common.Environment;
import com.colin.games.werewolf.common.modding.Mod;
import com.colin.games.werewolf.common.modding.ModLoader;
import com.colin.games.werewolf.common.utils.Config;
import com.colin.games.werewolf.common.utils.ExceptionFrame;
import com.colin.games.werewolf.common.utils.OutputFrame;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.appender.OutputStreamAppender;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.DefaultConfiguration;
import org.apache.logging.log4j.core.layout.PatternLayout;

import javax.swing.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.*;

/**
 * The main class.
 */
public class ClientMain {
    private static OutputFrame log;
    private static Config conf;
    //private static Image icon;
    private ClientMain(){
        throw new AssertionError();
    }
    /**
     * The main method.<br>
     * Currently, only two arguments are accepted.<br>
     * The first argument is one of: debug/info, which sets the level of logging of the application.<br>
     * The second argument is --show-log, which opens a dedicated GUI with the log.<br>
     * Please note that all of the above are optional.
     * @param args Application parameters --- see above
     * @throws IOException When an I/O error occurs
     * @throws ClassNotFoundException Lint
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException, URISyntaxException {
        if(args.length != 0){
            List<String> arg = new ArrayList<>(List.of(args));
            if(arg.contains("debug")){
                arg.removeIf(str -> str.equals("info") || str.equals("warn"));
                Configurator.setRootLevel(Level.DEBUG);
            }else if(arg.contains("info")){
                arg.removeIf(str -> str.equals("debug") || str.equals("warn"));
                Configurator.setRootLevel(Level.INFO);
            }else if(arg.contains("warn")){
                arg.removeIf(str -> str.equals("info") || str.equals("debug"));
                Configurator.setRootLevel(Level.WARN);
            }
            if(arg.stream().anyMatch(str -> str.startsWith("--theme="))){
                Environment.setLookAndFeel(arg.stream().filter(str -> str.startsWith("--theme=")).findFirst().get().replace("--theme=",""));
            }else{
                Environment.setLookAndFeel("nimbus");
            }
            if(arg.contains("--show-log")){
                log = new OutputFrame("Client Log");
                log.setVisible(true);
                log.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        }else{
            Configurator.setRootLevel(Level.WARN);
            Environment.setLookAndFeel("nimbus");
        }
        Logger logger = appendLog(LogManager.getFormatterLogger("Launch"));
        logger.info("Starting!");
        Environment.setSide(Environment.Side.CLIENT);
        logger.info("Launching client!");
        Thread.setDefaultUncaughtExceptionHandler((t,ex) -> {
            ex.printStackTrace();
            if(!(ex instanceof Exception)){
                System.exit(2);
            }
            new ExceptionFrame((Exception) ex,t);
        });
        final URI uri = ClientMain.class.getResource("/resources").toURI();
        Map<String, String> env = new HashMap<>();
        env.put("create", "true");
        FileSystem zip = FileSystems.newFileSystem(uri, env);
        logger.info("Starting audio subsystem...");
        Audio.initSDL();
        Audio.playMusic("./geg.mp3");
        Audio.setVolume(30);
        Path root = Environment.homeDir().resolve(".config/.netty-wolf");
        if(!root.toFile().exists()){
            logger.info("Creating game directory...");
            if(!root.toFile().mkdirs()){
                logger.error("Cannot write to the current directory and no game files detected!\n Exiting...");
                System.exit(1);
            }
            logger.info("Initializing configuration files...");
            initConfig(root);
        }else{
            if(!root.resolve("config.cfg").toFile().exists()){
                logger.warn("Cannot find config file! Creating new...");
                initConfig(root);
            }else{
                logger.info("Loading config file...");
                conf = Config.read(root.resolve("config.cfg"));
                Environment.setModded(conf.fromString("mods",Boolean::parseBoolean,"false"));
                if(conf.fromString("debug",Boolean::parseBoolean,"false")){
                    Configurator.setRootLevel(Level.DEBUG);
                }
            }
        }
        if(Environment.isModded()){
            logger.info("Mod option enabled in settings. Attempting to discover mods.");
            Path modDir = root.resolve("mods");
            if(!modDir.toFile().exists() || !modDir.toFile().isDirectory()){
                if(!modDir.toFile().mkdir()){
                    logger.error("No mod directory and unable to create one! Exiting....");
                    System.exit(1);
                }
            }
            if(Objects.requireNonNull(modDir.toFile().list()).length != 0){
                ModLoader.loadMods(modDir,conf.fromString("throwOnInvalidMod",Boolean::parseBoolean,"false"));
                if(ModLoader.loadedMods() == 0){
                    logger.info("Cannot find any mods. Reverting to non-modded mode!");
                    Environment.setModded(false);
                }else {
                    logger.info("Found " + ModLoader.loadedMods() + " mods!");
                }
            }else{
                logger.info("Cannot find any mods. Reverting to non-modded mode!");
                Environment.setModded(false);
            }
            ModLoader.getLoaded().forEach(Mod::init);
        }
        new StartMenu();
    }
    private static void initConfig(Path root) throws IOException {
        conf = new Config(root.resolve("config.cfg"));
        conf.store("mods","false");
        conf.store("debug","false");
        conf.store("throwOnInvalidMod","false");
        conf.write();
    }

    /**
     * Allows loggers to print to the log provided by the {@code --show-log} option.
     * Please note that this method returns silently when the {@code --show-log} option is not used.
     * @param logging The logger to set as printing to the log
     * @return The logger instance
     */
    public static Logger appendLog(Logger logging){
        if(log == null){
            return logging;
        }
        org.apache.logging.log4j.core.Logger temp = (org.apache.logging.log4j.core.Logger) logging;
        OutputStreamAppender appender = OutputStreamAppender.newBuilder().setLayout(PatternLayout.newBuilder().withPattern(DefaultConfiguration.DEFAULT_PATTERN).build()).setTarget(log.getPrintStream()).setName("Output").build();
        appender.start();
        temp.addAppender(appender);
        return logging;
    }
}
