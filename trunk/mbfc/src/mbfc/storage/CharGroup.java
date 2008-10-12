package mbfc.storage;

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
public class CharGroup {

    private byte first,  middle,  last,  seprate;
    private boolean isLast;
    private String name;

    public CharGroup(String name, byte first, byte middle, byte last, byte seprate, boolean isLast) {
        this.name = name;
        this.first = first;
        this.middle = middle;
        this.last = last;
        this.seprate = seprate;
        this.isLast = isLast;
    }

    public boolean write(FileWriter fw) {
        try {
            fw.writeByte(first);
            fw.writeByte(middle);
            fw.writeByte(last);
            fw.writeByte(seprate);
            fw.writeBoolean(isLast);
            fw.writeStringAndLen(name);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public boolean read(FileReader fr) {
        try {
            first = fr.readByte();
            middle = fr.readByte();
            last = fr.readByte();
            seprate = fr.readByte();
            isLast = fr.readBoolean();
            name = fr.readStringAndLen();
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public byte getMiddle() {
        return middle;
    }

    public byte getLast() {
        return last;
    }

    public byte getFirst() {
        return first;
    }

    public void setSeprate(byte seprate) {
        this.seprate = seprate;
    }

    public void setMiddle(byte middle) {
        this.middle = middle;
    }

    public void setLast(byte last) {
        this.last = last;
    }

    public void setFirst(byte first) {
        this.first = first;
    }

    public void setIsLast(boolean isLast) {
        this.isLast = isLast;
    }

    public byte getSeprate() {
        return seprate;
    }

    public boolean isIsLast() {
        return isLast;
    }
}