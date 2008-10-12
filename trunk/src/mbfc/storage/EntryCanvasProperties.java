package mbfc.storage;

import java.io.RandomAccessFile;
import java.util.Vector;
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
public class EntryCanvasProperties {

    Vector[][] buttons;
    int numberOfLayouts;

    public EntryCanvasProperties() {
        numberOfLayouts = 1;
        buttons = new Vector[6][10];
        for (int i = 0; i < 10; i++) {
            buttons[0][i] = new Vector();
        }
    }

    public EntryCanvasProperties(String filename) {
        openFromFile(filename);
    }

    public void openFromFile(String filename) {
        try {
            RandomAccessFile file = new RandomAccessFile(filename, "rw");
            FileReader fr = new FileReader(file);
            numberOfLayouts = fr.readByte();
            buttons = new Vector[6][10];
            for (int n = 0; n < numberOfLayouts; n++) {
                for (int i = 0; i < 10; i++) {
                    buttons[n][i] = new Vector();
                }
            }
            for (int n = 0; n < numberOfLayouts; n++) {
                for (int i = 0; i < 10; i++) {
                    int len = fr.readInt();
                    for (int j = 0; j < len; j++) {
                        buttons[n][i].add(fr.readStringAndLen());
                    }
                }
            }
        } catch (Exception ex) {
        }
    }

    public void saveToFile(String filename) {
        try {
            RandomAccessFile file = new RandomAccessFile(filename, "rw");
            file.setLength(0);
            FileWriter fw = new FileWriter(file);
            fw.writeByte((byte) numberOfLayouts);
            for (int n = 0; n < numberOfLayouts; n++) {
                for (int i = 0; i < 10; i++) {
                    fw.writeInt(buttons[n][i].size());
                    for (int j = 0; j < buttons[n][i].size(); j++) {
                        fw.writeStringAndLen((String) (buttons[n][i].get(j)));
                    }
                }
            }
        } catch (Exception ex) {
        }
    }

    public void addToButton(int layout, int button, CharGroup group) {
        buttons[layout][button].add(group.getName());
    }

    public void addToButton(int layout, int button, String group) {
        buttons[layout][button].add(group);
    }

    public void insertToButton(int layout, int button, int number, String name) {
        buttons[layout][button].insertElementAt(name, number);
    }

    public void setToButton(int layout, int button, int number, String name) {
        buttons[layout][button].set(number, name);
    }

    public void removeFromButton(int layout, int button, int number) {
        buttons[layout][button].remove(number);
    }

    public int getNumberOfGroupsInButton(int layout, int button) {
        //button 0 have 2 groups those automatically is added: space and enter
        //if(button==0) return buttons[button].size()-2;
        return buttons[layout][button].size();
    }

    public String getSeq(int layout, int button, int number) {
        return (String) buttons[layout][button].get(number);
    }

    public String[] getSeq(int layout, int button) {
        String[] temp = new String[buttons[layout][button].size()];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = (String) buttons[layout][button].get(i);
        }
        return temp;
    }

    public int getNumberOfLayouts() {
        return numberOfLayouts;
    }

    public void addLayout() {
        buttons[numberOfLayouts] = new Vector[10];
        for (int i = 0; i < 10; i++) {
            buttons[numberOfLayouts][i] = new Vector();
        }
        numberOfLayouts++;
    }

    public void removeLayout() {
        numberOfLayouts--;
    }
}