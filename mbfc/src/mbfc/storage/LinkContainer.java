package mbfc.storage;

import java.io.*;
import java.util.Vector;
import mbfc.filing.FileReader;
import mbfc.filing.FileWriter;

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
public class LinkContainer {

    private int length;
    private Vector unicodeArray;
    private Vector groups;

    public int getLength() {
        return length;
    }

    public LinkContainer() {
        length = 0;
        unicodeArray = new Vector();
        groups = new Vector();
    }

    public int getNumberOfLinks() {
        return length;
    }

    public LinkContainer(String filename) {
        openLinker(filename);
    }

    public boolean saveLinker(String filename) {
        try {
            RandomAccessFile file = new RandomAccessFile(filename, "rw");
            FileWriter fw = new FileWriter(file);
            file.setLength(0);
            fw.writeInt(length);
            for (int i = 0; i < length; i++) {
                fw.writeChar(((Character) unicodeArray.get(i)).charValue());
                fw.writeStringAndLen((String) (groups.get(i)));
            }
            file.close();
        } catch (Exception e) {
            return (false);
        }
        return (true);
    }

    public boolean openLinker(String filename) {
        try {
            RandomAccessFile file = new RandomAccessFile(filename, "rw");
            FileReader fr = new FileReader(file);
            length = fr.readInt();
            unicodeArray = new Vector();
            groups = new Vector();
            for (int i = 0; i < length; i++) {
                unicodeArray.add(new Character(fr.readChar()));
                groups.add(fr.readStringAndLen());
            }
        } catch (Exception e) {
            return (false);
        }
        return (true);
    }

    public void setLink(int number, String name, char unicode) {
        if (number < length);
        unicodeArray.set(number, new Character(unicode));
        groups.set(number, name);
    }

    public boolean addOrChangeLink(String name, char unicode) {
        int i;
        for (i = 0; i < length; i++) {
            if (((String) (groups.get(i))).compareTo(name) == 0) {
                break;
            }
        }
        if (i == length) {
            unicodeArray.add(new Character(unicode));
            groups.add(name);
            length++;
            return false;
        }
        unicodeArray.set(i, new Character(unicode));
        groups.set(i, name);
        return true;
    }

    public char getUnicode(int number) {
        return (((Character) (unicodeArray.get(number))).charValue());
    }

    public int getUnicode(String name) {
        for (int i = 0; i < length; i++) {
            if (((String) groups.get(i)).compareTo(name) == 0) {
                return ((Character) unicodeArray.get(i)).charValue();
            }
        }
        return -1;
    }

    public String getGroupName(char unicode) {
        int i;
        for (i = 0; i < length; i++) {
            if (((Character) (unicodeArray.get(i))).charValue() == unicode) {
                return ((String) (groups.get(i)));
            }
        }
        return null;
    }
}