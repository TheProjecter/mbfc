package mbfcme.ui;

import mbfcme.ui.entry.EntryCanvasProperties;

// <editor-fold defaultstate="collapsed" desc="license">
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

public class PackedData {

    public static byte first = EntryCanvasProperties.first;
    public static byte middle = EntryCanvasProperties.middle;
    public static byte last = EntryCanvasProperties.last;
    public static byte seprate = EntryCanvasProperties.seprate;
    public byte data;
    /*
    first bit is a boolean
    next 4 bits is pressed button
    next 2 bits is type
    and last bit is unused
     */

    public PackedData(byte data) {
        this.data = data;
    }

    public PackedData(boolean isLast, int button, byte type) {
        data = (byte) (isLast ? 1 : 0);
        data += (button * 2 + type * 32);
    }

    public PackedData(byte isLast, int button, byte type) {
        data = isLast;
        data += (button * 2 + type * 32);
    }

    public void setIslast(boolean isLast) {
        data = (byte) ((data / 2) * 2);
        data += ((byte) (isLast ? 1 : 0));
    }

    public void setButton(int button) {
        boolean isLast = isLast();
        data = (byte) ((data / 16) * 16 + button * 2);
        data += ((byte) (isLast ? 1 : 0));
    }

    public void setType(byte type) {
        boolean isLast = isLast();
        int button = getButton();
        data = (byte) (isLast ? 1 : 0);
        data += (button * 2 + type * 32);
    }

    public boolean isLast() {
        if (data % 2 == 1) {
            return true;
        }
        return false;
    }

    public int getButton() {
        return (data % 32) / 2;
    }

    public byte getType() {
        return (byte) (data / 32);
    }

    public byte getData() {
        return data;
    }
}