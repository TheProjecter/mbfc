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
public class MultiLanguageMECP {
    //ten buttons
    //four=first, midlle, last and seprate characters
    //and variable array for sequence
    byte[][][][] chars;
    byte[][][] isLast;

    public MultiLanguageMECP(String filename) {
        openFromFile(filename);
    }

    public MultiLanguageMECP(EntryCanvasProperties properties, GroupContainer groups) {
        isLast = new byte[properties.getNumberOfLayouts()][10][];
        chars = new byte[properties.getNumberOfLayouts()][10][4][];
        byte b = 0;
        groups.addGroup("space", b, b, b, b, true);
        b = 1;
        groups.addGroup("enter", b, b, b, b, true);

        for (int n = 0; n < properties.getNumberOfLayouts(); n++) {
            properties.insertToButton(n, 0, 0, "space");
            properties.insertToButton(n, 0, 1, "enter");
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 4; j++) {
                    chars[n][i][j] = new byte[properties.getNumberOfGroupsInButton(n, i)];
                    for (int k = 0; k < chars[n][i][j].length; k++) {
                        switch (j) {
                            case 0: {
                                chars[n][i][j][k] = groups.getGroup(
                                        properties.getSeq(n, i, k)).getFirst();
                                break;
                            }
                            case 1: {
                                chars[n][i][j][k] = groups.getGroup(
                                        properties.getSeq(n, i, k)).getMiddle();
                                break;
                            }
                            case 2: {
                                chars[n][i][j][k] = groups.getGroup(
                                        properties.getSeq(n, i, k)).getLast();
                                break;
                            }
                            case 3: {
                                chars[n][i][j][k] = groups.getGroup(
                                        properties.getSeq(n, i, k)).getSeprate();
                                break;
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < 10; i++) {
                isLast[n][i] = new byte[properties.getNumberOfGroupsInButton(n, i)];
                for (int j = 0; j < isLast[n][i].length; j++) {
                    if (groups.getGroup(properties.getSeq(n, i, j)).isIsLast()) {
                        isLast[n][i][j] = (byte) 1;
                    } else {
                        isLast[n][i][j] = (byte) 0;
                    }
                }
            }

            properties.removeFromButton(n, 0, 0);
            properties.removeFromButton(n, 0, 0);
        }
        groups.removeGroup(groups.getSize() - 1);
        groups.removeGroup(groups.getSize() - 1);
    }

    public void saveToFile(String filename) {
        FileOutputStream theFile = null;
        try {
            theFile = new FileOutputStream(filename);
            PrintStream outS = new PrintStream(theFile, true);
            byte[] len = new byte[1];
            len[0] = (byte) chars.length;
            outS.write(len);
            for (int n = 0; n < chars.length; n++) {
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 4; j++) {
                        len[0] = (byte) (chars[n][i][j].length);
                        outS.write(len);
                        outS.write(chars[n][i][j]);
                    }
                }
            }
            for (int n = 0; n < isLast.length; n++) {
                for (int i = 0; i < 10; i++) {
                    len[0] = (byte) (isLast[n][i].length);
                    outS.write(len);
                    outS.write(isLast[n][i]);
                }
            }
        } catch (Exception ex) {
        }
    }

    public void openFromFile(String filename) {
        FileInputStream theFile = null;
        try {
            theFile = new FileInputStream(filename);
            DataInputStream inS = new DataInputStream(theFile);
            byte[] len = new byte[1];
            inS.read(len);
            int lay = len[0];
            chars = new byte[lay][10][4][];

            for (int n = 0; n < lay; n++) {
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 4; j++) {
                        inS.read(len);
                        chars[n][i][j] = new byte[len[0]];
                        inS.read(chars[n][i][j]);
                    }
                }
            }
            isLast = new byte[lay][10][];
            for (int n = 0; n < lay; n++) {
                for (int i = 0; i < 10; i++) {
                    inS.read(len);
                    isLast[n][i] = new byte[len[0]];
                    inS.read(isLast[n][i]);
                }
            }
        } catch (Exception ex) {
        }
    }

    public byte getFirst(int layout, int button, int number) {
        return chars[layout][button][0][number];
    }

    public byte getMiddle(int layout, int button, int number) {
        return chars[layout][button][1][number];
    }

    public byte getLast(int layout, int button, int number) {
        return chars[layout][button][2][number];
    }

    public byte getSeprate(int layout, int button, int number) {
        return chars[layout][button][3][number];
    }

    public boolean isLast(int layout, int button, int number) {
        if (isLast[layout][button][number] == 1) {
            return true;
        }
        return false;
    }
}