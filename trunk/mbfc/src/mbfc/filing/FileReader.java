package mbfc.filing;

import java.io.RandomAccessFile;
import java.io.IOException;

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
public class FileReader {

    private RandomAccessFile file;

    public FileReader(RandomAccessFile file) {
        this.file = file;
    }

    static public boolean readBoolean(RandomAccessFile file) {
        boolean b = false;
        try {
            b = DataConvertor.byteToBoolean(file.readByte());
        } catch (IOException ex) {
            System.out.println("error in Boolean reading");
        }
        return (b);
    }

    public static byte readByte(RandomAccessFile file) {
        byte[] b = new byte[1];
        try {
            file.read(b);
        } catch (IOException ex) {
            System.out.println("error in Byte Array reading");
        }
        return b[0];
    }

    public static byte[] readBytes(RandomAccessFile file, int len) {
        byte[] b = new byte[len];
        try {
            file.read(b);
        } catch (IOException ex) {
            System.out.println("error in Byte Array reading");
        }
        return b;
    }

    public static String readStringAndLen(RandomAccessFile file) {
        String s = null;
        try {
            int len = readInt(file);
            byte[] b = new byte[len];
            file.read(b);
            s = new String(b);
        } catch (Exception ex) {
            System.out.println("error in String reading");
        }
        return s;
    }

    public static long readLong(RandomAccessFile file) {
        long a = 0;
        byte[] b = new byte[8];
        try {
            file.read(b);
            a = DataConvertor.bytesToLong(b);
        } catch (IOException ex) {
            System.out.println("error in Long reading");
        }
        return a;
    }

    public static int readInt(RandomAccessFile file) {
        int a = 0;
        byte[] b = new byte[4];
        try {
            file.read(b);
            a = DataConvertor.bytesToInt(b);
        } catch (IOException ex) {
            System.out.println("error in Integer reading");
        }
        return a;
    }

    static public char[] readChars(RandomAccessFile file, int len) {
        char[] c = new char[len];
        byte[] b = new byte[2 * len];
        try {
            file.read(b);
            c = DataConvertor.bytesToChars(b);
        } catch (IOException ex) {
            System.out.println("error in Char Array reading");
        }
        return c;
    }

    static public char readChar(RandomAccessFile file) {
        char c = 0;
        byte[] b = new byte[2];
        try {
            file.read(b);
            c = DataConvertor.bytesToChar(b);
        } catch (IOException ex) {
            System.out.println("error in Char reading");
        }
        return c;
    }

    static public short readShort(RandomAccessFile file) {
        short s = 0;
        byte[] b = new byte[2];
        try {
            file.read(b);
            s = DataConvertor.bytesToShort(b);
        } catch (IOException ex) {
            System.out.println("error in Short reading");
        }
        return s;
    }

    static public String readString(RandomAccessFile file, int len) {
        String s = "";
        byte[] b = new byte[len];
        try {
            file.read(b);
            s = new String(b);
        } catch (IOException ex) {
            System.out.println("error in String reading");
        }
        return s;
    }

    public byte readByte() {
        byte[] b = new byte[1];
        try {
            file.read(b);
        } catch (IOException ex) {
            System.out.println("error in Byte Array reading");
        }
        return b[0];
    }

    public byte[] readBytes(int len) {
        byte[] b = new byte[len];
        try {
            file.read(b);
        } catch (IOException ex) {
            System.out.println("error in Byte Array reading");
        }
        return b;
    }

    public String readStringAndLen() {
        String s = null;
        try {
            int len = readInt();
            byte[] b = new byte[len];
            file.read(b);
            s = new String(b);
        } catch (Exception ex) {
            System.out.println("error in String reading");
        }
        return s;
    }

    public long readLong() {
        long a = 0;
        byte[] b = new byte[8];
        try {
            file.read(b);
            a = DataConvertor.bytesToLong(b);
        } catch (IOException ex) {
            System.out.println("error in Long reading");
        }
        return a;
    }

    public int readInt() {
        int a = 0;
        byte[] b = new byte[4];
        try {
            file.read(b);
            a = DataConvertor.bytesToInt(b);
        } catch (IOException ex) {
            System.out.println("error in Integer reading");
        }
        return a;
    }

    public boolean readBoolean() {
        boolean b = false;
        try {
            b = DataConvertor.byteToBoolean(file.readByte());
        } catch (IOException ex) {
            System.out.println("error in Boolean reading");
        }
        return (b);
    }

    public char[] readChars(int len) {
        char[] c = new char[len];
        byte[] b = new byte[2 * len];
        try {
            file.read(b);
            c = DataConvertor.bytesToChars(b);
        } catch (IOException ex) {
            System.out.println("error in Char Array reading");
        }
        return c;
    }

    public char readChar() {
        char c = 0;
        byte[] b = new byte[2];
        try {
            file.read(b);
            c = DataConvertor.bytesToChar(b);
        } catch (IOException ex) {
            System.out.println("error in Char reading");
        }
        return c;
    }

    public short readShort() {
        short s = 0;
        byte[] b = new byte[2];
        try {
            file.read(b);
            s = DataConvertor.bytesToShort(b);
        } catch (IOException ex) {
            System.out.println("error in Short reading");
        }
        return s;
    }

    public String readString(int len) {
        String s = "";
        byte[] b = new byte[len];
        try {
            file.read(b);
            s = new String(b);
        } catch (IOException ex) {
            System.out.println("error in String reading");
        }
        return s;
    }
}