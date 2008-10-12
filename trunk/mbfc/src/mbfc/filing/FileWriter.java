package mbfc.filing;

import java.io.*;

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
public class FileWriter {

    private RandomAccessFile file;

    public FileWriter(RandomAccessFile file) {
        this.file = file;
    }

    public static void writeStringAndLen(RandomAccessFile file, String s) {
        try {
            writeInt(file, s.length());
            file.write(s.getBytes());
        } catch (Exception ex) {
            System.out.println("error in String writing");
        }
    }

    public static void writeLong(RandomAccessFile file, long a) {
        try {
            file.write(DataConvertor.longToBytes(a));
        } catch (IOException ex) {
            System.out.println("error in Long writing");
        }
    }

    public static void writeInt(RandomAccessFile file, int a) {
        try {
            file.write(DataConvertor.intToBytes(a));
        } catch (IOException ex) {
            System.out.println("error in Integer writing");
        }
    }

    static public void writeByte(RandomAccessFile file, byte b) {
        byte[] a = new byte[1];
        a[0] = b;
        try {
            file.write(a);
        } catch (IOException ex) {
            System.out.println("error in Boolean writing");
        }
    }

    static public void writeBoolean(RandomAccessFile file, boolean b) {
        try {
            file.write(DataConvertor.booleanToByte(b));
        } catch (IOException ex) {
            System.out.println("error in Boolean writing");
        }
    }

    static public void writeChars(RandomAccessFile file, char[] c) {
        try {
            file.write(DataConvertor.charsToBytes(c));
        } catch (IOException ex) {
            System.out.println("error in Char Array writing");
        }
    }

    static public void writeChar(RandomAccessFile file, char c) {
        try {
            file.write(DataConvertor.charToBytes(c));
        } catch (IOException ex) {
            System.out.println("error in Char writing");
        }
    }

    static public void writeShort(RandomAccessFile file, short s) {
        try {
            file.write(DataConvertor.shortToBytes(s));
        } catch (IOException ex) {
            System.out.println("error in Short writing");
        }
    }

    static public void writeString(RandomAccessFile file, String s) {
        try {
            file.write(s.getBytes());
        } catch (IOException ex) {
            System.out.println("error in String writing");
        }
    }

    public void writeStringAndLen(String s) {
        try {
            writeInt(s.length());
            file.write(s.getBytes());
        } catch (Exception ex) {
            System.out.println("error in String writing");
        }
    }

    public void writeLong(long a) {
        try {
            file.write(DataConvertor.longToBytes(a));
        } catch (IOException ex) {
            System.out.println("error in Long writing");
        }
    }

    public void writeInt(int a) {
        try {
            file.write(DataConvertor.intToBytes(a));
        } catch (IOException ex) {
            System.out.println("error in Integer writing");
        }
    }

    public void writeByte(byte b) {
        byte[] a = new byte[1];
        a[0] = b;
        try {
            file.write(a);
        } catch (IOException ex) {
            System.out.println("error in Boolean writing");
        }
    }

    public void writeBoolean(boolean b) {
        try {
            file.write(DataConvertor.booleanToByte(b));
        } catch (IOException ex) {
            System.out.println("error in Boolean writing");
        }
    }

    public void writeChars(char[] c) {
        try {
            file.write(DataConvertor.charsToBytes(c));
        } catch (IOException ex) {
            System.out.println("error in Char Array writing");
        }
    }

    public void writeChar(char c) {
        try {
            file.write(DataConvertor.charToBytes(c));
        } catch (IOException ex) {
            System.out.println("error in Char writing");
        }
    }

    public void writeShort(short s) {
        try {
            file.write(DataConvertor.shortToBytes(s));
        } catch (IOException ex) {
            System.out.println("error in Short writing");
        }
    }

    public void writeString(String s) {
        try {
            file.write(s.getBytes());
        } catch (IOException ex) {
            System.out.println("error in String writing");
        }
    }
}