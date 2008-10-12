package mbfc;

import java.io.RandomAccessFile;

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
public class SharedData {

    final static public String defaultCharFileName = "characters.mbf";
    final static public String defaultGroupsFileName = "groups.grp";
    final static public String defaultLinkerFileName = "links.lnk";
    final static public String defaultCanvasFileName = "entry-canvas.cnv";
    static public boolean isConstantWidth;
    static public byte width;
    static public byte height;
    static public boolean isLtoR;
    static public RandomAccessFile projectFile;
    static public RandomAccessFile charFile;
    static public RandomAccessFile groupsFile;
    static public RandomAccessFile linkerFile;
    static public RandomAccessFile canvasFile;
    static public String projectFileName;
    static public String charFileName;
    static public String groupsFileName;
    static public String linkerFileName;
    static public String canvasFileName;
    static public String fontDirectory;
}