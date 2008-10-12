package mbfc.filing;

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
public class DataConvertor {

    public DataConvertor() {
    }

    static public String digitToHex(int i) {
        String temp = "";
        if (i < 0) {
            return null;
        }
        if (i < 10) {
            temp = Integer.toString(i);
        } else {
            switch (i) {
                case 10: {
                    temp = "A";
                    break;
                }
                case 11: {
                    temp = "B";
                    break;
                }
                case 12: {
                    temp = "C";
                    break;
                }
                case 13: {
                    temp = "D";
                    break;
                }
                case 14: {
                    temp = "E";
                    break;
                }
                case 15:
                    temp = "F";
            }
        }
        return temp;
    }

    static public int hexToDigit(String s) {
        int temp = 0;
        switch (s.charAt(0)) {
            case 'A':
                return 10;
            case 'B':
                return 11;
            case 'C':
                return 12;
            case 'D':
                return 13;
            case 'E':
                return 14;
            case 'F':
                return 15;
        }
        try {
            temp = Integer.parseInt(s);
            if (temp > 9) {
                return -1;
            }
        } catch (Exception ex) {
            return -1;
        }
        return temp;
    }

    static public int hexToInt(String s) {
        int temp = 0;
        int factor = 1;
        for (int i = s.length(); i > 0; i--) {
            int t = hexToDigit(s.charAt(i - 1) + "");
            if (t == -1) {
                return -1;
            }
            temp = temp + factor * t;
            factor = factor * 16;
        }
        return temp;
    }

    static public String IntToHex(int i) {
        String s = "";
        while (i != 0) {
            String t = digitToHex(i % 16);
            if (t == null) {
                return null;
            }
            s = t + s;
            i = i / 16;
        }
        return s;
    }

    static public byte[] intToBytes(int in) {
        byte[] temp = new byte[4];
        temp[3] = (byte) (0xff & (in >> 24));
        temp[2] = (byte) (0xff & (in >> 16));
        temp[1] = (byte) (0xff & (in >> 8));
        temp[0] = (byte) (0xff & in);
        return (temp);
    }

    static public byte[] longToBytes(long in) {
        byte[] temp = new byte[8];
        temp[7] = (byte) (0xff & (in >> 56));
        temp[6] = (byte) (0xff & (in >> 48));
        temp[5] = (byte) (0xff & (in >> 40));
        temp[4] = (byte) (0xff & (in >> 32));
        temp[3] = (byte) (0xff & (in >> 24));
        temp[2] = (byte) (0xff & (in >> 16));
        temp[1] = (byte) (0xff & (in >> 8));
        temp[0] = (byte) (0xff & in);
        return (temp);
    }

    static public int bytesToInt(byte[] b) {
        return (((b[3] & 0xff) << 24) | ((b[2] & 0xff) << 16) |
                ((b[1] & 0xff) << 8) | (b[0] & 0xff));
    }

    static public long bytesToLong(byte[] b) {
        return (((b[7] & 0xff) << 56) | ((b[6] & 0xff) << 48) |
                ((b[5] & 0xff) << 40) | ((b[4] & 0xff) << 32) |
                ((b[3] & 0xff) << 24) | ((b[2] & 0xff) << 16) |
                ((b[1] & 0xff) << 8) | (b[0] & 0xff));
    }

    static public byte booleanToByte(boolean b) {
        if (b) {
            return 1;
        }
        return 0;
    }

    static public boolean byteToBoolean(byte b) {
        if (b == 0) {
            return false;
        }
        return true;
    }

    static public char[] bytesToChars(byte[] b) {
        char[] temp = new char[b.length / 2];
        byte[] doubleByte = new byte[2];
        for (int i = 0; i < b.length;) {
            doubleByte[0] = b[i++];
            doubleByte[1] = b[i++];
            temp[i / 2 - 1] = bytesToChar(doubleByte);
        }
        return temp;
    }

    static public byte[] charsToBytes(char[] c) {
        byte[] temp = new byte[c.length * 2];
        for (int i = 0; i < c.length; i++) {
            byte[] t = charToBytes(c[i]);
            temp[2 * i] = t[0];
            temp[2 * i + 1] = t[1];
        }
        return (temp);
    }

    static public String bytesToString(byte[] b) {
        String temp = new String(b);
        return temp;
    }

    static public byte[] shortToBytes(short in) {
        byte[] temp = new byte[2];
        temp[1] = (byte) (0xff & (in >> 8));
        temp[0] = (byte) (0xff & in);
        return (temp);

    }

    static public short bytesToShort(byte[] b) {
        return (short) (((b[1] & 0xff) << 8) | (b[0] & 0xff));
    }

    static public byte[] charToBytes(char ch) {
        return shortToBytes((short) ch);
    }

    static public char bytesToChar(byte[] in) {
        return (char) bytesToShort(in);
    }

    static public String charsToString(char[] in) {
        String s = new String(in);
        return s;
    }

    static public char[] stringToChars(String in) {
        char[] temp = new char[in.length()];
        in.getChars(0, in.length(), temp, 0);
        return (temp);
    }
}