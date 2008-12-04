package mbfcme.ui.canvas;

import java.io.InputStream;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import mbfcme.storage.BitArrayContainer;

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
public class PageCanvas extends Canvas {

    public static byte space = 0;
    public static byte enter = 1;
    public static char rightToLeft = 'r';
    public static char leftToRight = 'l';
    public static char centered = 'c';
    public static char justified = 'j';
    byte[] text;
    BitArrayContainer font;
    int gapBetweenLines;
    int color;
    int transparentColor;
    int backgroundColor;
    char alignment;
    int displayWidth;
    int width;
    int leftIntend;
    int upIntend;

    public PageCanvas(InputStream text, BitArrayContainer font,
            int gapBetweenLines, int upIntend,
            int color, int transparentColor,
            int backgroundColor, char alignment) {
        this.font = font;
        this.gapBetweenLines = gapBetweenLines;
        this.color = color;
        this.transparentColor = transparentColor;
        this.backgroundColor = backgroundColor;
        this.alignment = alignment;
        this.width = getWidth();
        try {
            byte[] temp = new byte[4];
            text.read(temp);
            displayWidth = bytesToInt(temp);
            text.read(temp);
            leftIntend = bytesToInt(temp);
            this.text = new byte[text.available()];
            text.read(this.text);
            text.close();
        } catch (Exception exp) {
        }
        setFullScreenMode(true);
    }
    private int startLine = 0;
    private boolean allShown = false;

    public void paint(Graphics g) {

        g.setColor(backgroundColor);
        g.fillRect(0, 0, getWidth(), getHeight());

        if (displayWidth > width) {
            return;
        }
        int y = upIntend;
        int x = leftIntend;
        int lineW = 0;

        int in = startLine;
        int index = 0;
        for (index = startLine; index < text.length; index++) {

            if ((text[index] == enter)) {
                if (index != startLine) {
                    y += gapBetweenLines + font.getHeight();
                    if (y + gapBetweenLines + font.getHeight() >= getHeight()) {
                        break;
                    }
                }
            }

            if ((text[index] == enter) || (index == startLine)) {

                if ((index != startLine)) {
                    //y += gapBetweenLines + font.getHeight();
                    //if (y + gapBetweenLines + font.getHeight() >= getHeight()) {
                    //    break;
                    //}
                    in = index + 1;
                } else {
                    in = startLine;
                }

                if (alignment != leftToRight) {
                    lineW = 0;
                    for (; in < text.length; in++) {
                        if (text[in] == enter) {
                            break;
                        } else if (text[in] == space) {
                            if (in != index) {
                                lineW += text[in + 1];
                            }
                            in++;
                            continue;
                        }
                        lineW += font.getCharacter(text[in]).getColsLength();
                    }
                }

                if (alignment == leftToRight) {
                    x = leftIntend + (width - displayWidth);
                } else if (alignment == rightToLeft) {
                    x = leftIntend + (width - lineW);
                //x = leftIntend + (width - displayWidth) + (displayWidth - lineW);
                } else if (alignment == justified) {
                    x = leftIntend + (width - lineW);
                }
            } 
            
            if(text[index]==enter);
            else if (text[index] == space) {
                if (index == startLine + 1) {
                } else {
                    x += text[++index];
                }
            } else {
                if (font.isTransparent(text[index])) {
                    g.setColor(transparentColor);
                } else {
                    g.setColor(color);
                }
                font.getCharacter(text[index]).paintIt(g, y, x, color);
                x += font.getCharacter(text[index]).getColsLength();
            }
        }

        if (index == text.length) {
            allShown = true;
        } else {
            allShown = false;
        //g.getFont(Font.SIZE_SMALL);
        //g.drawString("\\/", 0, 0, 0);
        }
    }

    private void gotoNextLine() {
        if (allShown) {
            return;
        }
        int i = 0;
        for (i = startLine; i < text.length; i++) {
            if (text[i] == enter) {
                break;
            }
        }
        if (i < text.length) {
            startLine = i + 1;
        }
        repaint();
    }

    private void gotoPreviousLine() {
        if (startLine == 0) {
            return;
        }
        int i = 0;
        for (i = startLine - 2; i >= 0; i--) {
            if (text[i] == enter) {
                break;
            //if(i==0) startLine=0;
            //else 
            }
        }
        startLine = i + 1;
        repaint();
    }

    public void keyPressed(int key) {
        if (key == -2/*DOWN*/) {
            gotoNextLine();
        } else if (key == -1/*UP*/) {
            gotoPreviousLine();
        }
    }

    static public int bytesToInt(byte[] b) {
        return (((b[3] & 0xff) << 24) | ((b[2] & 0xff) << 16) |
                ((b[1] & 0xff) << 8) | (b[0] & 0xff));
    }
}
