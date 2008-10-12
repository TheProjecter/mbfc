package mbfc.storage;

import java.io.RandomAccessFile;
import java.util.*;
import mbfc.filing.*;

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
public class GroupContainer {

    LinkedList list;

    public GroupContainer() {
        list = new LinkedList();
    }

    public GroupContainer(String filename) {
        LoadFromFile(filename);
    }

    public int getSize() {
        return list.size();
    }

    public String[] getGroups() {
        String[] s = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            s[i] = ((CharGroup) (list.get(i))).getName();
        }
        return s;
    }

    public CharGroup searchForIt(String name) {
        for (int i = 0; i < list.size(); i++) {
            if (((CharGroup) (list.get(i))).getName().compareTo(name) == 0) {
                return (CharGroup) (list.get(i));
            }
        }
        return null;
    }

    public void addGroup(String name, byte first, byte second, byte third, byte seprate, boolean isLast) {
        CharGroup g = new CharGroup(name, first, second, third, seprate, isLast);
        list.add(g);
    }

    public boolean setGroup(String name, byte first, byte middle, byte last, byte seprate, boolean isLast) {
        int i;
        for (i = 0; i < list.size(); i++) {
            if (((CharGroup) list.get(i)).getName().compareTo(name) == 0) {
                break;
            }
        }
        if (i == list.size()) {
            return false;
        }
        CharGroup cg = (CharGroup) list.get(i);
        cg.setFirst(first);
        cg.setMiddle(middle);
        cg.setLast(last);
        cg.setSeprate(seprate);
        cg.setIsLast(isLast);
        return true;
    }

    public CharGroup getGroup(String name) {
        int i;
        for (i = 0; i < list.size(); i++) {
            CharGroup cg = (CharGroup) list.get(i);
            if (cg.getName().compareTo(name) == 0) {
                return (cg);
            }
        }
        return null;
    }

    public boolean modifyOrAddGroup(String name, short first, short second,
            short last, short seprate, boolean isLast) {
        //return false it wasn't in groups and true for modify excisted group
        if (first > 127) {
            first -= 256;
        }
        if (second > 127) {
            second -= 256;
        }
        if (last > 127) {
            last -= 256;
        }
        if (seprate > 127) {
            seprate -= 256;
        }
        if (!setGroup(name, (byte) first, (byte) second, (byte) last, (byte) seprate, isLast)) {
            addGroup(name, (byte) first, (byte) second, (byte) last, (byte) seprate, isLast);
            return false;
        }
        return true;
    }

    public CharGroup getGroup(int number) {
        if (number >= list.size()) {
            return null;
        }
        return ((CharGroup) list.get(number));
    }

    public void removeGroup(int number) {
        list.remove(number);
    }

    public boolean saveToFile(String filename) {
        try {
            RandomAccessFile file = new RandomAccessFile(filename, "rw");
            FileWriter fw = new FileWriter(file);
            fw.writeInt(list.size());
            for (int i = 0; i < list.size(); i++) {
                if (!(((CharGroup) (list.get(i))).write(fw))) {
                    return false;
                //CharGroup cg=(CharGroup)list.get(i);
                //if(!cg.write(outS)) return(false);
                }
            }
            file.close();
        } catch (Exception ex) {
            return (false);
        }
        return true;
    }

    public boolean LoadFromFile(String filename) {
        try {
            RandomAccessFile file = new RandomAccessFile(filename, "r");
            FileReader fr = new FileReader(file);
            int size = fr.readInt();
            list = new LinkedList();
            for (int i = 0; i < size; i++) {
                CharGroup cg = new CharGroup("name", (byte) 0, (byte) 0, (byte) 0, (byte) 0, false);
                if (!(cg.read(fr))) {
                    return false;
                }
                list.add(cg);
            }
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
}