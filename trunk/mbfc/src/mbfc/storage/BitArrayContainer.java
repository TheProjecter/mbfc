package mbfc.storage;

import java.io.*;
import mbfc.SharedData;
import mbfc.filing.FileWriter;
import mbfc.filing.FileReader;

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
public class BitArrayContainer {

    static final public int oneByteLength = 256;
    static final public int twoByteLength = 65536;//=256*256
    static final public char constantWidth = 'C';
    static final public char variedWidth = 'V';
    static final public char rightToLeft = 'R';
    static final public char leftToRight = 'L';
    static final public byte False = 0;
    static final public byte True = 1;
    static final public byte space = 0;
    static final public byte enter = 1;
    public BitArray[] array;
    public byte[] transparentArray;
    private char widthType;
    private byte height,  defaultWidth;
    private int len;
    private char alignment;
    private int revAfter;
    private int lastEdited = 2;

    public void setRevAfter(int revAfter) {
        this.revAfter = revAfter;
    }

    public void setLastEdited(int lastEdited) {
        this.lastEdited = lastEdited;
    }

    public boolean isRev(int num) {
        if (revAfter < 2) {
            return false;
        }
        return (num >= revAfter);
    }

    public BitArrayContainer(int length) {
        if (SharedData.isLtoR) {
            alignment = this.leftToRight;
        } else {
            alignment = this.rightToLeft;
        }
        len = length;
        defaultWidth = SharedData.width;
        array = new BitArray[length];
        if (SharedData.isConstantWidth) {
            widthType = this.constantWidth;
        } else {
            widthType = this.variedWidth;
        }
        height = SharedData.height;

        transparentArray = new byte[len];
        for (int i = 0; i < len; i++) {
            array[i] = new BitArray(height, defaultWidth);
            transparentArray[i] = False;
        }
        revAfter = -1;
    }

    public BitArrayContainer(String filename, int length) {

        if (SharedData.isLtoR) {
            alignment = this.leftToRight;
        } else {
            alignment = this.rightToLeft;
        }
        len = length;
        defaultWidth = SharedData.width;
        array = new BitArray[length];
        if (SharedData.isConstantWidth) {
            widthType = this.constantWidth;
        } else {
            widthType = this.variedWidth;
        }
        height = SharedData.height;
        this.openFromFile(filename);
    }

    public char getWidthType() {
        return widthType;
    }

    public byte getHeight() {
        return height;
    }

    public byte getDefaultWidth() {
        return defaultWidth;
    }

    public char getAlignment() {
        return alignment;
    }

    public int getLen() {
        return len;
    }

    public int getRevAfter() {
        return revAfter;
    }

    public int getLastEdited() {
        return lastEdited;
    }

    public boolean addCharacter(int number, BitArray figure) {
        try {
            array[number] = figure;
        } catch (Exception e) {
            return (false);
        }
        return (true);
    }

    public BitArray getCharacter(int number) {
        try {
            if (number < 0) {
                number += 256;
            }
            return (array[number]);
        } catch (Exception e) {
            return (null);
        }
    }

    public void increaseHeight() {
        this.height++;
        for (int i = 0; i < array.length; i++) {
            array[i] = array[i].getACopyWithIncreasedHeight();
        }
    }

    public void setCharacter(int number, BitArray character) {
        array[number] = character;
    }

    public boolean isTransparent(int number) {
        if (number < 0) {
            number += 256;
        }
        return (transparentArray[number] == True);
    }

    public void setTransparent(int number, boolean status) {
        transparentArray[number] = status ? True : False;
    }

    public boolean saveToFile(String filename) {
        try {
            RandomAccessFile file = new RandomAccessFile(filename, "rw");
            FileWriter fw = new FileWriter(file);
            fw.writeInt(len);
            file.write(transparentArray);
            if (widthType == this.constantWidth) {
                fw.writeByte(defaultWidth);
                for (int i = 0; i < len; i++) {
                    file.write(array[i].getBytes());
                }
            } /*and else for varied Length,first length of any character figure will
            be written on file and then characters' figure will be written and so on.
             */ else {
                for (int i = 0; i < len; i++) {
                    fw.writeByte(array[i].getColsLength());
                    file.write(array[i].getBytes());
                }
            }
            fw.writeInt(revAfter);
            fw.writeInt(lastEdited);
            file.close();
        } catch (Exception e) {
            return (false);
        }
        return (true);
    }

    public boolean openFromFile(String filename) {
        try {
            RandomAccessFile file = new RandomAccessFile(filename, "rw");
            FileReader fr = new FileReader(file);
            len = fr.readInt();
            array = new BitArray[len];
            //readin height of font
            transparentArray = new byte[len];
            file.read(transparentArray);
            //if constant width,i read width and then Hole of bitArray
            if (widthType == this.constantWidth) {
                byte width = fr.readByte();
                for (int i = 0; i < len; i++) {
                    int realCol = (width - 1) / 8 + 1;
                    byte[] b = new byte[realCol * height];
                    file.read(b);
                    this.array[i] = new BitArray(b, height, width);
                }
            } //else for varied length length of any character figure will be readed
            //from file and then characters' figure.
            else {
                for (int i = 0; i < len; i++) {
                    byte width = fr.readByte();
                    int realCol = (width - 1) / 8 + 1;
                    byte[] b = new byte[realCol * height];
                    file.read(b);
                    this.array[i] = new BitArray(b, height, width);
                }
            }
            revAfter = fr.readInt();
            lastEdited = fr.readInt();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}