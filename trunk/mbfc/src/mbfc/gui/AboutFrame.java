package mbfc.gui;

import javax.swing.*;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Cursor;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.Insets;

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
public class AboutFrame extends JFrame {

    private JPanel pane = null;
    private JLabel lblAuthor = null;
    private JLabel lblName = null;
    private JLabel lblVersion = null;
    private JLabel lblSquirrelSoft = null;
    private JLabel lblOwned = null;
    private JLabel lblEmail = null;
    private JLabel lblDesc = null;
    private JLabel lblDesc2 = null;
    private JPanel pnlTitle = null;
    private JPanel pnlImage = null;

    /**
     * This method initializes 
     * 
     */
    public AboutFrame() {
        super();
        initialize();
    }

    /**
     * This method initializes this
     * 
     */
    private void initialize() {
        this.setSize(new Dimension(406, 430));
        this.setForeground(Color.white);
        this.setContentPane(getPane());
        this.setLocale(new Locale("en", "", ""));
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        this.setName("AboutFrame");
        this.setTitle("Majid Asgari's Mobile Bit Font Creator");

    }

    /**
     * This method initializes pane	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getPane() {
        if (pane == null) {
            GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
            gridBagConstraints9.gridx = 0;
            gridBagConstraints9.gridy = 0;
            GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
            gridBagConstraints7.gridx = 0;
            gridBagConstraints7.gridwidth = 1;
            gridBagConstraints7.gridheight = 4;
            gridBagConstraints7.gridy = 1;
            GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
            gridBagConstraints6.gridx = 0;
            gridBagConstraints6.gridy = 12;
            lblDesc2 = new JLabel();
            lblDesc2.setText("and  continued again in November 2007 in Java & Mobile Community");
            lblDesc2.setFont(new Font("Dialog", Font.PLAIN, 10));
            lblDesc2.setForeground(Color.red);
            GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
            gridBagConstraints5.gridx = 0;
            gridBagConstraints5.gridy = 11;
            lblDesc = new JLabel();
            lblDesc.setText("This project started by Majid Asgari in July to Auguest 2006 ");
            lblDesc.setFont(new Font("Dialog", Font.PLAIN, 10));
            lblDesc.setForeground(Color.red);
            GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
            gridBagConstraints4.gridx = 0;
            gridBagConstraints4.ipady = 5;
            gridBagConstraints4.gridy = 9;
            lblEmail = new JLabel();
            lblEmail.setText("majid.asgari@gmail.com");
            GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
            gridBagConstraints3.gridx = 0;
            gridBagConstraints3.gridheight = 2;
            gridBagConstraints3.ipady = 5;
            gridBagConstraints3.gridy = 7;
            lblOwned = new JLabel();
            lblOwned.setText("Owned By: Majid Asgari Bidhendi");
            lblOwned.setForeground(new Color(255, 0, 208));
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.gridx = 0;
            gridBagConstraints2.gridheight = 2;
            gridBagConstraints2.fill = GridBagConstraints.NONE;
            gridBagConstraints2.weighty = 0.0;
            gridBagConstraints2.ipady = 8;
            gridBagConstraints2.gridy = 5;
            lblSquirrelSoft = new JLabel();
            lblSquirrelSoft.setText("Parseh Soft� - Copyright 2007 ");
            lblSquirrelSoft.setForeground(new Color(255, 105, 0));
            lblVersion = new JLabel();
            lblVersion.setText("One-Byte version - 1.1.3");
            lblVersion.setForeground(new Color(0, 146, 0));
            lblName = new JLabel();
            lblName.setText("Mobile Bit Font Creator");
            lblName.setForeground(Color.red);
            lblName.setFont(new Font("Dialog", Font.BOLD, 24));
            lblAuthor = new JLabel();
            lblAuthor.setText("Majid Asgari's");
            lblAuthor.setFont(new Font("Dialog", Font.BOLD, 18));
            lblAuthor.setForeground(Color.blue);
            pane = new JPanel();
            pane.setLayout(new GridBagLayout());
            pane.setName("");
            pane.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            pane.setForeground(Color.white);
            pane.add(lblSquirrelSoft, gridBagConstraints2);
            pane.add(lblOwned, gridBagConstraints3);
            pane.add(lblEmail, gridBagConstraints4);
            pane.add(lblDesc, gridBagConstraints5);
            pane.add(lblDesc2, gridBagConstraints6);
            pane.add(getPnlTitle(), gridBagConstraints7);
            pane.add(getPnlImage(), gridBagConstraints9);
        }
        return pane;
    }

    /**
     * This method initializes pnlTitle	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getPnlTitle() {
        if (pnlTitle == null) {
            GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
            gridBagConstraints8.anchor = GridBagConstraints.EAST;
            gridBagConstraints8.gridy = 2;
            gridBagConstraints8.insets = new Insets(0, 0, 0, 2);
            gridBagConstraints8.gridx = 0;
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.gridx = 0;
            gridBagConstraints1.gridy = 1;
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.anchor = GridBagConstraints.WEST;
            gridBagConstraints.gridy = -1;
            gridBagConstraints.insets = new Insets(0, 2, 0, 0);
            gridBagConstraints.gridx = -1;
            pnlTitle = new JPanel();
            pnlTitle.setLayout(new GridBagLayout());
            pnlTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            pnlTitle.setForeground(Color.white);
            pnlTitle.add(lblAuthor, gridBagConstraints);
            pnlTitle.add(lblName, gridBagConstraints1);
            pnlTitle.add(lblVersion, gridBagConstraints8);
        }
        return pnlTitle;
    }

    /**
     * This method initializes pnlImage	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getPnlImage() {
        if (pnlImage == null) {
            pnlImage = new JPanel();
            pnlImage.setSize(400, 220);
            ImageCanvas image = new ImageCanvas(387, 207);
            image.addImage("about.jpg", 4, 5, image.getWidth(), image.getHeight());
            pnlImage.add(image);
            pnlImage.setLayout(new GridBagLayout());
        }
        return pnlImage;
    }
}  //  @jve:decl-index=0:visual-constraint="21,17"