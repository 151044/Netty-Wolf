/*
 *     Netty-Wolf
 *     Copyright (C) 2020  Colin Chow
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.colin.utils;

import javax.swing.*;
import java.io.File;
/**
 * A file selector interface form users.
 * @author Colin
 */
public class FileSelector extends JFrame{
    private File selected;

    /**
     * Constructs a new FileSelector with the specified name shown as the title.
     * @param name The name shown
     */
    public FileSelector(String name){
        super(name);
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int returnValue = chooser.showOpenDialog(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if(returnValue == JFileChooser.APPROVE_OPTION){
            selected = chooser.getSelectedFile();
        }else{
            System.exit(0);
        }
    }

    /**
     * Constructs a FileSelector.
     */
    public FileSelector(){
        this("Choose a file");
    }

    /**
     * Retrieves the selected file.
     * @return The selected file
     */
    public File retrieveSelected(){
        return selected;
    }
}
