package mbfc.render;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Color;
import java.util.Vector;
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
// </editor-fold>
public class CharacterEditCanvas extends Canvas {

    final static public byte complement = 0;
    final static public byte set = 1;
    final static public byte clear = 2;
    public BitArray array;
    private int arrayWidth,  arrayHeight;
    //private boolean paint=false;
    int hRedLines, vRedLines;
    boolean showRedLines;
    boolean showBlueLines;
    byte penState;
    Vector guidLines = new Vector();

    public CharacterEditCanvas(BitArray anArray, int canvasWidth, int canvasHeight, int hRedLinesStep, int vRedLinesStep, boolean showRedLines,
            boolean showBlueLines, byte penState) {
        array = anArray;
        arrayWidth = array.getColsLength();
        arrayHeight = array.getRowsLength();
        this.vRedLines = vRedLinesStep;
        this.hRedLines = hRedLinesStep;
        this.showRedLines = showRedLines;
        this.showBlueLines = showBlueLines;
        this.setSize(canvasWidth, canvasHeight);
        this.penState = penState;
        refreshTable();
    }

    public void changeArray(BitArray anArray) {
        array = anArray;
        arrayWidth = array.getColsLength();
        arrayHeight = array.getRowsLength();
        refreshTable();
    }

    public void changeVisualProperties(int hRedLinesStep, int vRedLinesStep,
            boolean showRedLines, boolean showBlueLines,
            byte penState) {
        this.vRedLines = vRedLinesStep;
        this.hRedLines = hRedLinesStep;
        this.showRedLines = showRedLines;
        this.showBlueLines = showBlueLines;
        this.penState = penState;
        refreshTable();
    }

    public void changeDimension(int newWidth, int newHeight) {
        this.setSize(newWidth, newHeight);
        refreshTable();
    }

    public void addRemoveGuidLine(Color color, byte lineType, int position) {
        if (lineType == GuideLine.HORIZONTAL) {
            position = (position * arrayHeight - 1) / this.getHeight();
        } else if (lineType == GuideLine.VERTICAL) {
            position = (position * arrayWidth - 1) / this.getWidth();
        }
        GuideLine gl = new GuideLine(color, lineType, (byte) position);
        for (int i = 0; i < guidLines.size(); i++) {
            if ((((GuideLine) (guidLines.get(i))).getLineType() == lineType) &&
                    ((((GuideLine) (guidLines.get(i))).getPosition()) == gl.getPosition())) {
                guidLines.remove(i);
                return;
            }
        }
        guidLines.add(gl);

    }

    public void clickOn(int x, int y) {
        /*int row=(x*arrayHeight-1)/this.getHeight();
        int col=(y*arrayWidth-1)/this.getWidth();*/
        int row = (int) (((x + 0.0) / (this.getHeight() + 0.0)) * arrayHeight);
        int col = (int) (((y + 0.0) / (this.getWidth()) + 0.0) * arrayWidth);
        /*int row=(int)(x*((arrayHeight-0.0)/this.getHeight()));
        int col=(int)(y*((arrayWidth-0.0)/this.getWidth()));*/
        int firstRow = (row / hRedLines) * hRedLines;
        int firstCol = (col / vRedLines) * vRedLines;
        for (int i = 0; i < hRedLines; i++) {
            for (int j = 0; j < vRedLines; j++) {
                int r = firstRow + i;
                int c = firstCol + j;
                if ((r < array.getRowsLength()) && (r < array.getRowsLength())) {
                    switch (penState) {
                        case complement: {
                            array.complementBit(r, c);
                            break;
                        }
                        case set: {
                            array.setBit(r, c);
                            break;
                        }
                        default:
                            array.clearBit(r, c);
                    }
                }
            }
        }
        refreshTable();
    }

    public void refreshTable() {
        //paint=true;
        repaint();
    }

    public void paint(Graphics g) {
        //if(paint){
        int height = this.getHeight();
        int width = this.getWidth();
        int squareHeight = height / arrayHeight;
        int squareWidth = width / arrayWidth;
        /*for(int i=0,a=0;i<height-squareHeight;i+=squareHeight,a++)
        for(int j=0,b=0;j<width-squareWidth+1;j+=squareWidth,b++){
        if (array.getBit(a,b) == 1) g.setColor(Color.black);
        else g.setColor(Color.white);
        //g.setColor(Color.red);
        g.fillRect(j, i, squareWidth, squareHeight);
        }*/
        int x = 0, y = 0;
        for (int i = 0, a = 0; a < array.getRowsLength(); i += squareHeight, a++) {
            for (int j = 0, b = 0; b < array.getColsLength(); j += squareWidth, b++) {
                if (array.getBit(a, b) == 1) {
                    g.setColor(Color.black);
                } else {
                    g.setColor(Color.white);
                //g.setColor(Color.red);
                }
                g.fillRect(j, i, squareWidth, squareHeight);
                x = j + squareWidth + 1;
                y = i + squareHeight + 1;
            }
        //paint=false;
        }
        if (showBlueLines) {
            g.setColor(Color.blue);
            for (int k = 0, c = 0; c < array.getRowsLength(); k += squareHeight, c++) {
                g.drawLine(0, k, width, k);
            }
            for (int l = 0, c = 0; c < array.getColsLength(); l += squareWidth, c++) {
                g.drawLine(l, 0, l, height);
            }
        }
        if (showRedLines) {
            g.setColor(Color.red);
            for (int k = 0, c = 0; c < array.getRowsLength(); k += squareHeight, c++) {
                if (c % hRedLines == 0) {
                    g.drawLine(0, k, width, k);
                }
            }
            for (int k = 0, c = 0; c < array.getColsLength(); k += squareWidth, c++) {
                if (c % vRedLines == 0) {
                    g.drawLine(k, 0, k, height);
                }
            }
        }
        for (int i = 0; i < guidLines.size(); i++) {
            GuideLine gl = (GuideLine) guidLines.get(i);
            g.setColor(gl.getLineColor());
            if (gl.getLineType() == GuideLine.HORIZONTAL) {
                g.fillRect(0, gl.getPosition() * squareHeight, this.getWidth(), 2);
            } else {
                if (gl.getPosition() * squareWidth > this.getWidth() - 2) {
                    continue;
                }
                g.fillRect(gl.getPosition() * squareWidth, 0, 2, this.getHeight());
            }
        }
        this.setSize(x - 1, y - 1);
    /*g.setColor(Color.white);
    g.fillRect(x,0,this.getWidth()-x,this.getHeight());
    g.fillRect(0,y,this.getWidth(),this.getHeight()-y);*/
    //}
    }
}