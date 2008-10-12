package mbfc.storage.micro;

import java.io.*;
import mbfc.storage.EntryCanvasProperties;
import mbfc.storage.GroupContainer;

// <editor-fold defaultstate="collapsed" desc="mbfc license">
/*
 * Created until 26-Nov-2007 at 16:20:01.
 * 
 * Copyright (c) 2007 Majid Asgari Bidhendi / Squirrel Soft�
 *
 * This file is part of Mobile Bit Font Creator.
 *
 * Mobile Bit Font Creator is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * Mobile Bit Font Creator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Mobile Bit Font Creator; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 * Commercial licenses are also available, please
 * refer to the accompanying LICENSE.txt or visit
 * http://www.samancomputers.com for details.
 */
// </editor-fold>
public class MicroEntryCanvasProperties {
    //ten buttons
    //four=first, midlle, last and seprate characters
    //and variable array for sequence
    byte[][][] chars;
    byte[][] isLast;

    public MicroEntryCanvasProperties(String filename) {
        openFromFile(filename);
    }

    public MicroEntryCanvasProperties(EntryCanvasProperties properties, GroupContainer groups) {
        byte b = 0;
        groups.addGroup("space", b, b, b, b, true);
        b = 1;
        groups.addGroup("enter", b, b, b, b, true);
        properties.insertToButton(0, 0, 0, "space");
        properties.insertToButton(0, 0, 1, "enter");

        chars = new byte[10][4][];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 4; j++) {
                chars[i][j] = new byte[properties.getNumberOfGroupsInButton(0, i)];
                for (int k = 0; k < chars[i][j].length; k++) {
                    switch (j) {
                        case 0: {
                            chars[i][j][k] = groups.getGroup(
                                    properties.getSeq(0, i, k)).getFirst();
                            break;
                        }
                        case 1: {
                            chars[i][j][k] = groups.getGroup(
                                    properties.getSeq(0, i, k)).getMiddle();
                            break;
                        }
                        case 2: {
                            chars[i][j][k] = groups.getGroup(
                                    properties.getSeq(0, i, k)).getLast();
                            break;
                        }
                        case 3: {
                            chars[i][j][k] = groups.getGroup(
                                    properties.getSeq(0, i, k)).getSeprate();
                            break;
                        }
                    }
                }
            }
        }
        isLast = new byte[10][];
        for (int i = 0; i < 10; i++) {
            isLast[i] = new byte[properties.getNumberOfGroupsInButton(0, i)];
            for (int j = 0; j < isLast[i].length; j++) {
                if (groups.getGroup(properties.getSeq(0, i, j)).isIsLast()) {
                    isLast[i][j] = (byte) 1;
                } else {
                    isLast[i][j] = (byte) 0;
                }
            }
        }

        groups.removeGroup(groups.getSize() - 1);
        groups.removeGroup(groups.getSize() - 1);
        properties.removeFromButton(0, 0, 0);
        properties.removeFromButton(0, 0, 0);

    }

    public void saveToFile(String filename) {
        FileOutputStream theFile = null;
        try {
            theFile = new FileOutputStream(filename);
            PrintStream outS = new PrintStream(theFile, true);
            byte[] len = new byte[1];
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 4; j++) {
                    len[0] = (byte) (chars[i][j].length);
                    outS.write(len);
                    outS.write(chars[i][j]);
                }
            }
            for (int i = 0; i < 10; i++) {
                len[0] = (byte) (isLast[i].length);
                outS.write(len);
                outS.write(isLast[i]);
            }
        } catch (Exception ex) {
        }
    }

    public void openFromFile(String filename) {
        FileInputStream theFile = null;
        try {
            theFile = new FileInputStream(filename);
            DataInputStream inS = new DataInputStream(theFile);
            chars = new byte[10][4][];
            byte[] len = new byte[1];
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 4; j++) {
                    inS.read(len);
                    chars[i][j] = new byte[len[0]];
                    inS.read(chars[i][j]);
                }
            }
            isLast = new byte[10][];
            for (int i = 0; i < 10; i++) {
                inS.read(len);
                isLast[i] = new byte[len[0]];
                inS.read(isLast[i]);
            }
        } catch (Exception ex) {
        }
    }

    public byte getFirst(int button, int number) {
        return chars[button][0][number];
    }

    public byte getMiddle(int button, int number) {
        return chars[button][1][number];
    }

    public byte getLast(int button, int number) {
        return chars[button][2][number];
    }

    public byte getSeprate(int button, int number) {
        return chars[button][3][number];
    }

    public boolean isLast(int button, int number) {
        if (isLast[button][number] == 1) {
            return true;
        }
        return false;
    }
}