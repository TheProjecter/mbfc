package mbfc.render;

import java.awt.*;

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
public class GuideLine {

    /**It's a Line Type Value-guide line is Horizontal*/
    public static final byte HORIZONTAL = 0;
    /**It's a Line Type Value-guide line is Vertical*/
    public static final byte VERTICAL = 1;
    private Color lineColor = Color.CYAN;
    private byte lineType = 0;
    /**It's position of line vertically or horizontally*/
    private byte position = 0;

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }

    public void setLineType(byte lineType) {
        this.lineType = lineType;
    }

    public void setPosition(byte position) {
        this.position = position;
    }

    public Color getLineColor() {
        return lineColor;
    }

    public byte getLineType() {
        return lineType;
    }

    public byte getPosition() {
        return position;
    }

    public GuideLine() {
    }

    public GuideLine(Color lineColor) {
        this.lineColor = lineColor;
    }

    public GuideLine(byte lineType) {
        this.lineType = lineType;
    }

    public GuideLine(Color lineColor, byte lineType) {
        this.lineColor = lineColor;
        this.lineType = lineType;
    }

    public GuideLine(Color lineColor, byte lineType, byte position) {
        this.lineColor = lineColor;
        this.lineType = lineType;
        this.position = position;
    }
}