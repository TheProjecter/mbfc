package mbfc.gui;

import java.awt.*;
import java.util.Vector;
import javax.print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.*;
import java.awt.print.Printable;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import javax.print.attribute.PrintRequestAttributeSet;

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
public class ImageCanvas extends Canvas implements Printable {

    Vector images;

    public ImageCanvas(int width, int height) {
        this.setSize(width, height);
        images = new Vector();
    }

    public void addImage(String filename, int x, int y, int width, int height) {
        ImageData data = new ImageData();
        data.image = Toolkit.getDefaultToolkit().getImage(filename);
        data.image = data.image.getScaledInstance(width, height,
                Image.SCALE_AREA_AVERAGING);
        data.x = x;
        data.y = y;
        images.add(data);
    }

    public void removeImage(int numberOfImageFirstIsOne) {
        images.remove(numberOfImageFirstIsOne - 1);
    }

    public void paintIt(Graphics g) {
        ImageData data;
        if (images.size() > 0) {
            for (int i = 0; i < images.size(); i++) {
                data = (ImageData) images.get(i);
                g.drawImage(data.image, data.x, data.y, this);
            }
        }
    }

    public void paint(Graphics g) {
        paintIt(g);
    }

    public void printAsDoc() {
        DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        PrintRequestAttributeSet aset =
                new HashPrintRequestAttributeSet();
        aset.add(MediaSizeName.ISO_A4);
        aset.add(new Copies(2));
        aset.add(Sides.TWO_SIDED_LONG_EDGE);
        aset.add(Finishings.STAPLE);
        PrintService[] pservices =
                PrintServiceLookup.lookupPrintServices(flavor, aset);
        if (pservices.length > 0) {
            System.out.println("selected printer " + pservices[0].getName());
            DocPrintJob pj = pservices[0].createPrintJob();
            Doc doc = new SimpleDoc(this, flavor, null);
            try {
                pj.print(doc, aset);
            } catch (PrintException ex) {
            }
        }
    }

    public int print(Graphics g, PageFormat pf, int pageIndex) throws
            PrinterException {
        if (pageIndex == 0) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.translate(pf.getImageableX(), pf.getImageableY());
            paintIt(g);
            return Printable.PAGE_EXISTS;
        } else {
            return Printable.NO_SUCH_PAGE;
        }
    }
}

class ImageData {

    public Image image;
    public int x;
    public int y;
}