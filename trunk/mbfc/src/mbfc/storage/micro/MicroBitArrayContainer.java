package mbfc.storage.micro;

import java.io.*;
import mbfc.filing.DataConvertor;
import mbfc.storage.BitArray;

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
public class MicroBitArrayContainer {

    static final public int oneByteLength = 256;
    static final public int twoByteLength = 65536;//=256*256
    static final public char constantWidth = 'C';
    static final public char varifiedWidth = 'V';
    static final public char rightToLeft = 'R';
    static final public char leftToRight = 'L';
    static final public byte False = 0;
    static final public byte True = 1;
    private BitArray[] array;
    private byte[] transparentArray;
    private char widthType;
    private byte height,  defaultWidth;
    private int len;
    private char alignment;
    private int revAfter = 0;

    public MicroBitArrayContainer(int length, char aWidthType, char anAlignment, byte aHeight, byte aDefaultWidth, int revAfter) {
        alignment = anAlignment;
        len = length;
        defaultWidth = aDefaultWidth;
        array = new BitArray[length];
        widthType = aWidthType;
        height = aHeight;
        this.revAfter = revAfter;
        transparentArray = new byte[len];
        for (int i = 0; i < len; i++) {
            array[i] = new BitArray(height, defaultWidth);
            transparentArray[i] = False;
        }
    }

    public boolean isRev(byte ch) {
        if (revAfter < 2) {
            return false;
        }
        return (((ch > -1) ? ch : ch + 256) >= revAfter);
    }

    public MicroBitArrayContainer(String filename) {
        this.openFromFile(filename);
    }

    public void setTransparents(byte[] array) {
        transparentArray = array;
    }

    public void setChar(BitArray[] array) {
        this.array = array;
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
            FileOutputStream theFile = new FileOutputStream(filename);
            PrintStream outS = new PrintStream(theFile, true);
            byte[] temp = new byte[1];
            //writing font length-number of supported characters
            //temp[0]=(byte)len;
            //outS.write(temp);
            //writing height of font
            temp[0] = height;
            outS.write(temp);
            //writing font type C for Constant width font and V for Other
            temp[0] = 0;
            if (widthType == constantWidth) {
                temp[0] = 1;
            }
            outS.write(temp);
            //writing font alignment
            temp[0] = 0;
            if (alignment == rightToLeft) {
                temp[0] = 1;
            }
            outS.write(temp);
            //save character transparent status
            outS.write(transparentArray);
            //if constant width,here i write width and then BitArrays
            if (widthType == constantWidth) {
                temp[0] = defaultWidth;
                outS.write(temp);
                for (int i = 0; i < len; i++) {
                    outS.write(array[i].getBytes());
                }
            } /*and else for varied Length,first length of any character figure will
            be written on file and then characters' figure will be written and so on.
             */ else {
                for (int i = 0; i < len; i++) {
                    temp[0] = array[i].getColsLength();
                    outS.write(temp);
                    outS.write(array[i].getBytes());
                }
            }
            outS.write(DataConvertor.intToBytes(revAfter));
            outS.close();
        } catch (Exception e) {
            return (false);
        }
        return (true);
    }

    public boolean openFromFile(String filename) {
        try {
            FileInputStream theFile = new FileInputStream(filename);
            DataInputStream inS = new DataInputStream(theFile);
            //reading number of chars in font
            len = 256;
            array = new BitArray[len];
            //readin height of font
            byte[] temp = new byte[1];
            inS.read(temp);
            height = temp[0];
            //reading font type
            inS.read(temp);
            widthType = varifiedWidth;
            if (temp[0] == 1) {
                widthType = constantWidth;
            //reading font alignment
            }
            inS.read(temp);
            alignment = leftToRight;
            if (temp[0] == 1) {
                alignment = rightToLeft;
            //set transparent status
            }
            transparentArray = new byte[len];
            inS.read(transparentArray);
            //if constant width,i read width and then Hole of bitArray
            if (widthType == constantWidth) {
                inS.read(temp);
                byte width = temp[0];
                for (int i = 0; i < len; i++) {
                    int realCol = (width - 1) / 8 + 1;
                    byte[] b = new byte[realCol * height];
                    inS.read(b);
                    this.array[i] = new BitArray(b, height, width);
                }

            } //else for varied length length of any character figure will be readed
            //from file and then characters' figure.
            else {
                for (int i = 0; i < len; i++) {
                    inS.read(temp);
                    byte width = temp[0];
                    int realCol = (width - 1) / 8 + 1;
                    byte[] b = new byte[realCol * height];
                    inS.read(b);
                    this.array[i] = new BitArray(b, height, width);
                }
            }
            temp = new byte[4];
            inS.read(temp);
            revAfter = DataConvertor.bytesToInt(temp);
        } catch (Exception e) {
            System.out.println("Error in BaitArrayCaontainer.OpenFromFile Catched");
            return false;
        }
        return true;
    }
}