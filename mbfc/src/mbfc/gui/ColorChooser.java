package mbfc.gui;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;

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
public class ColorChooser extends JFrame {

    BorderLayout borderLayout = new BorderLayout();
    JColorChooser choser = new JColorChooser();
    Border border1;
    JButton btnOK = new JButton();
    CharsFrame cf;

    public ColorChooser(CharsFrame cf) {
        this.cf = cf;
        try {
            jbInit();
            choser.setColor(Color.cyan);
            this.setSize(500, 500);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void jbInit() throws Exception {
        border1 = BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.white, Color.white, Color.orange, Color.red);
        this.getContentPane().setLayout(borderLayout);
        choser.setBorder(border1);
        choser.setDebugGraphicsOptions(DebugGraphics.NONE_OPTION);
        btnOK.setText("OK");
        btnOK.addActionListener(new ColorChooser_btnOK_actionAdapter(this));
        this.setTitle("Chose Current Color");
        this.getContentPane().add(choser, BorderLayout.CENTER);
        this.getContentPane().add(btnOK, BorderLayout.SOUTH);
    }

    void btnOK_actionPerformed(ActionEvent e) {
        cf.selectedColor = choser.getColor();
        this.dispose();
    }
}

class ColorChooser_btnOK_actionAdapter implements java.awt.event.ActionListener {

    ColorChooser adaptee;

    ColorChooser_btnOK_actionAdapter(ColorChooser adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnOK_actionPerformed(e);
    }
}