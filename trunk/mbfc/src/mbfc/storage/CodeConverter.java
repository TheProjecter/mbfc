package mbfc.storage;

import java.io.*;
import javax.swing.JTextArea;

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
public class CodeConverter {

    final static public int space = 0;
    final static public int enter = 1;
    final static public int first = 2;
    final static public int middle = 3;
    final static public int last = 4;
    final static public int seprated = 5;
    private char[] text;
    private GroupContainer groups;
    private LinkContainer linker;
    private BitArrayContainer font;

    public CodeConverter(String text, GroupContainer groups,
            LinkContainer linker, BitArrayContainer font) {
        this.text = text.toCharArray();
        this.groups = groups;
        this.linker = linker;
        this.font = font;
    }

    public byte[] convert(JTextArea txa) {
        txa.setText("");
        // we must remove undefined chars
        for (int i = 0; i < text.length; i++) {
            String name = linker.getGroupName(text[i]);
            if ((name == null) && (text[i] != 32) && (text[i] != '\n') && (text[i] != '\r')) {
                txa.setText(txa.getText() + "\\u" +
                        (int) text[i] + "(" + text[i] + ")");
                text[i] = ' ';
            }
        }

        char[] temp = text;

        /*int numberOfEnters=0;
        for(int i=0;i<text.length;i++)
        if(text[i]=='\n') numberOfEnters++;
        
        boolean lastIsEnter=(text[text.length-1]=='\n');//it's an exception
        int len=text.length-numberOfEnters;
        if(lastIsEnter) len--;
        if((lastIsEnter)&&(len<=1)) return new byte[0];
        
        char [] temp=new char[len];
        for(int i=0,j=0;i<text.length;i++){
        if(text[i]=='\r') continue;
        temp[j]=text[i];
        j++;
        if((lastIsEnter)&&(i==text.length-2)) break;
        }
        text=temp;*/

        //=>temp is text;//we must remove transparent chars temporarely
        int j = 0;// number of transparent chars
        for (int i = 0; i < text.length; i++) {
            String name = linker.getGroupName(text[i]);
            if (name != null) {
                if (font.isTransparent(groups.getGroup(name).getFirst())) {
                    j++;
                }
            }
        }
        char[] withoutTrans = new char[text.length - j];
        j = 0;
        for (int i = 0; i < text.length; i++) {
            String name = linker.getGroupName(text[i]);
            if ((name != null)) {
                if (font.isTransparent(groups.getGroup(name).getFirst())) {
                    continue;
                } else {
                    withoutTrans[j] = text[i];
                    j++;
                }
            } else /*if((text[i]==' ')||(text[i]=='\n'))*/ {
                withoutTrans[j++] = text[i];
            }
        }
        text = withoutTrans;


        byte[] coded = new byte[text.length];

        for (int i = 0; i < coded.length; i++) {
            char currentChar = text[i];
            int type = getType(i, coded);
            if (type == space) {
                coded[i] = BitArrayContainer.space;
            } else if (type == enter) {
                coded[i] = BitArrayContainer.enter;
            } else {
                String name = linker.getGroupName(currentChar);
                //if(name==null)continue;
                CharGroup group = groups.getGroup(name);
                if (type == first) {
                    coded[i] = group.getFirst();
                } else if (type == middle) {
                    coded[i] = group.getMiddle();
                } else if (type == last) {
                    coded[i] = group.getLast();
                } else if (type == seprated) {
                    coded[i] = group.getSeprate();
                }
            }
        }
        if (txa.getText().compareTo("") == 0) {
            txa.setText("All Characters have been used !!");
        }
        text = temp;
        j = 0;
        byte[] codedTemp = new byte[text.length];
        for (int i = 0; i < text.length; i++) {
            String name = linker.getGroupName(text[i]);
            if ((name != null)) {
                if (!font.isTransparent(groups.getGroup(name).getFirst())) {
                    codedTemp[i] = coded[j++];
                } else {
                    codedTemp[i] = groups.getGroup(name).getFirst();
                }
            } else /*if((text[i]==' ')||(text[i]=='\n'))*/ {
                codedTemp[i] = coded[j++];
            }
        }
        return (codedTemp);
    }

    public boolean saveToFile(String filename) {
        try {
            DataOutputStream outS = new DataOutputStream(new FileOutputStream(filename));
            outS.write(text.length);
            JTextArea txaTemp = new JTextArea();
            outS.write(convert(txaTemp));
            outS.close();
        } catch (Exception ex) {
            return (false);
        }
        return true;
    }

    private int getType(int i, byte[] coded) {
        if (text.length == 1) {
            return seprated;
        }
        if ((i == 0) && ((text[1] == 32) || (coded[1] == '\n'))) {
            return seprated;
        }
        if (i == 0) {
            return first;
        }
        if (text[i] == 32/*space*/) {
            return space;
        }
        if ((text[i] == '\n')/*enter key*/) {
            return enter;
        }
        boolean back = (coded[i - 1] == space) || (coded[i - 1] == enter) || (groups.getGroup(linker.getGroupName(text[i - 1])).isIsLast());
        if (i == text.length - 1) {
            if (back) {
                return seprated;
            }
            return last;
        }

        String name = linker.getGroupName(text[i + 1]);
        boolean next = (text[i + 1] == 32) || (text[i + 1] == '\n') /*||(font.isTransparent(groups.getGroup(
                linker.getGroupName(text[i+1])).getFirst()))*/ || (name == null);
        if (back && next) {
            return seprated;
        }
        if (back) {
            return first;
        }
        if (next) {
            return last;
        /*if(font.isTransparent(coded[i-1])){
        if((coded[i-2]==space)||(coded[i-2]==enter)
        ||(groups.getGroup(linker.getGroupName(text[i-2])).isIsLast()))
        return first;
        }*/
        /*if((font.isTransparent(groups.getGroup(
        linker.getGroupName(text[i+1])).getFirst()))){
        if((coded[i+2]==space)||(coded[i+2]==enter)
        ||(groups.getGroup(linker.getGroupName(text[i+2])).isIsLast()))
        return first;
        }*/
        }
        return (middle);
    }
}