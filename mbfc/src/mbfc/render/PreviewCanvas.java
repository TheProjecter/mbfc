package mbfc.render;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Color;
import mbfc.storage.BitArray;

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
// </editor-fold>s
public class PreviewCanvas extends Canvas {

    BitArray array;

    public PreviewCanvas(BitArray anArray) {

        array = anArray;
    }

    public void changeArray(BitArray anArray) {
        array = anArray;
        repaint();
    }

    public void paint(Graphics g) {
        this.setSize(array.getColsLength(), array.getRowsLength());
        for (int i = 0; i < array.getRowsLength(); i++) {
            for (int j = 0; j < array.getColsLength(); j++) {
                if (array.getBit(i, j) == 1) {
                    g.setColor(Color.black);
                } else {
                    g.setColor(Color.white);
                }
                g.fillRect(j, i, 1, 1);
            }
        }
    }
}