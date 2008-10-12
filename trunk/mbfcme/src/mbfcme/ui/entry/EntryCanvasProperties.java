package mbfcme.ui.entry;
import java.io.*;
/*
 * Created until 26-Nov-2007 at 16:20:01.
 * 
 * Copyright (c) 2007 Majid Asgari Bidhendi / Squirrel Soft®
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
public class EntryCanvasProperties {
    //ten buttons
    //four=first, midlle, last and seprate characters
    //and variable array for sequence
    byte[][][] chars;
    byte[][] isLast;

    public static byte first=0;
    public static byte middle=1;
    public static byte last=2;
    public static byte seprate=3;

    public EntryCanvasProperties(InputStream s) {
        openFromFile(s);
    }

    public void openFromFile(InputStream s){
        try {
            DataInputStream inS=new DataInputStream(s);
            chars=new byte[10][4][];
            byte[]len=new byte[1];
            for(int i=0;i<10;i++)
                for(int j=0;j<4;j++){
                    inS.read(len);
                    chars[i][j]=new byte[len[0]];
                    inS.read(chars[i][j]);
                }
            isLast=new byte[10][];
            for(int i=0;i<10;i++){
                inS.read(len);
                isLast[i]=new byte[len[0]];
                inS.read(isLast[i]);
            }

        } catch (Exception ex) {
        }
    }

    public byte getFirst(int button,int number){
        return chars[button][0][number];
    }
    public byte getMiddle(int button,int number){
        return chars[button][1][number];
    }
    public byte getLast(int button,int number){
        return chars[button][2][number];
    }
    public byte getSeprate(int button,int number){
        return chars[button][3][number];
    }
    public boolean isLast(int button,int number){
        if(isLast[button][number]==1) return true;
        return false;
    }
    public boolean isLast(byte ch){
        for(int i=0;i<10;i++)
            for(int j=0;j<4;j++)
                for(int k=0;k<chars[i][j].length;k++)
                    if(chars[i][j][k]==ch){
                        if(isLast[i][k]==1) return true;
                        return false;
                    }
        return false;

    }


    public byte[] getGroup(int button,int number){
        byte[] temp=new byte[4];
        temp[0]=chars[button][0][number];
        temp[1]=chars[button][1][number];
        temp[2]=chars[button][2][number];
        temp[3]=chars[button][3][number];
        return temp;
    }

    public byte[] getGroup(int ch,int button,byte type){
            int len=chars[button][type].length;
            for(int j=0;j<len;j++){
                if(chars[button][type][j]==ch)
                    return getGroup(button,j);
            }
        return null;
    }

    public byte[] getGroup(byte ch,byte type){
        for(int i=0;i<10;i++){
            int len=chars[i][type].length;
            for(int j=0;j<len;j++){
                if(chars[i][type][j]==ch)
                    return getGroup(i,j);
            }
        }
        return null;
    }

    public byte[] getGroup(byte ch){
        for(int i=0;i<4;i++){
            byte[] temp=getGroup(ch,i);
            if(temp!=null) return temp;
        }
        return null;
    }

}
