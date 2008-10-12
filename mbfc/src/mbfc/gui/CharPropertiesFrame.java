package mbfc.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

// <editor-fold defaultstate="collapsed" desc="mbfc license">
import mbfc.SharedData;
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
public class CharPropertiesFrame extends JFrame {

    BorderLayout xYLayout1 = new BorderLayout();
    JRadioButton rdiConstantWidth = new JRadioButton();
    ButtonGroup grpWidthType = new ButtonGroup();  //  @jve:decl-index=0:
    JRadioButton rdiVariedWidth = new JRadioButton();
    JLabel lblWidth = new JLabel();
    JLabel lblHeight = new JLabel();
    ButtonGroup grbAlignment = new ButtonGroup();  //  @jve:decl-index=0:
    JRadioButton rdiLtoR = new JRadioButton();
    JRadioButton rdiRtoL = new JRadioButton();
    JLabel lblAlignment = new JLabel();
    JButton btnApply = new JButton();
    JButton btnCancel = new JButton();
    JTextField txtWidth = new JTextField();
    JTextField txtHeight = new JTextField();
    JLabel lblSize = new JLabel();
    private JPanel jContentPane = null;

    public CharPropertiesFrame() {
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setResizable(false);
        this.setContentPane(getJContentPane());
        this.setTitle("New Project - Character Map Properties");
        rdiConstantWidth.setSelected(true);
        rdiConstantWidth.setText(
                "Constant Width of Every Characters - Monospaced");
        rdiVariedWidth.setAction(null);
        rdiVariedWidth.setText("Varied Character Width");
        lblHeight.setText("Height:");
        rdiLtoR.setSelected(true);
        rdiLtoR.setText("Left To Right");
        rdiRtoL.setText("Right to Left");
        lblAlignment.setText("Alignment:");
        btnApply.setText("Apply");
        btnApply.addActionListener(new CharPropertiesFrame_btnApply_actionAdapter(this));
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new CharPropertiesFrame_btnCancel_actionAdapter(this));
        txtWidth.setText("8");
        txtHeight.setText("8");
        lblSize.setText("Character Map  Sizes:");
        lblWidth.setText("(Default) Width:");
        grpWidthType.add(rdiConstantWidth);
        grpWidthType.add(rdiVariedWidth);
        grbAlignment.add(rdiLtoR);
        grbAlignment.add(rdiRtoL);
        this.setSize(405, 331);
    }

    public void btnCancel_actionPerformed(ActionEvent e) {
        this.dispose();
    }

    public void btnApply_actionPerformed(ActionEvent e) {
        if (this.rdiConstantWidth.isSelected()) {
            SharedData.isConstantWidth = true;
        } else {
            SharedData.isConstantWidth = false;
        }
        if (this.rdiLtoR.isSelected()) {
            SharedData.isLtoR = true;
        } else {
            SharedData.isLtoR = false;
        }
        try {
            SharedData.width = Byte.parseByte(txtWidth.getText());
            SharedData.height = Byte.parseByte(txtHeight.getText());
        } catch (Exception ex) {
            MyComment mc = new MyComment("Error in Width or Height Value", "OK", 500);
            mc.show();
            return;
        }
        this.dispose();
    }

    /**
     * This method initializes jContentPane	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
            gridBagConstraints10.gridx = 2;
            gridBagConstraints10.insets = new Insets(20, 0, 0, 0);
            gridBagConstraints10.gridy = 6;
            GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
            gridBagConstraints9.gridx = 1;
            gridBagConstraints9.insets = new Insets(20, 0, 0, 0);
            gridBagConstraints9.fill = GridBagConstraints.NONE;
            gridBagConstraints9.gridy = 6;
            GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
            gridBagConstraints8.gridx = 1;
            gridBagConstraints8.anchor = GridBagConstraints.EAST;
            gridBagConstraints8.insets = new Insets(0, 0, 0, 3);
            gridBagConstraints8.gridy = 5;
            GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
            gridBagConstraints7.gridx = 1;
            gridBagConstraints7.insets = new Insets(20, 0, 0, 0);
            gridBagConstraints7.anchor = GridBagConstraints.EAST;
            gridBagConstraints7.gridy = 4;
            GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
            gridBagConstraints6.gridx = 0;
            gridBagConstraints6.insets = new Insets(20, 0, 0, 0);
            gridBagConstraints6.anchor = GridBagConstraints.EAST;
            gridBagConstraints6.gridy = 4;
            GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
            gridBagConstraints5.fill = GridBagConstraints.BOTH;
            gridBagConstraints5.gridy = 3;
            gridBagConstraints5.weightx = 1.0;
            gridBagConstraints5.anchor = GridBagConstraints.WEST;
            gridBagConstraints5.insets = new Insets(0, 0, 0, 10);
            gridBagConstraints5.gridx = 2;
            GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
            gridBagConstraints4.fill = GridBagConstraints.BOTH;
            gridBagConstraints4.gridy = 2;
            gridBagConstraints4.weightx = 1.0;
            gridBagConstraints4.anchor = GridBagConstraints.WEST;
            gridBagConstraints4.insets = new Insets(20, 0, 0, 10);
            gridBagConstraints4.gridx = 2;
            GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
            gridBagConstraints3.gridx = 1;
            gridBagConstraints3.anchor = GridBagConstraints.EAST;
            gridBagConstraints3.gridy = 3;
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.gridx = 1;
            gridBagConstraints2.anchor = GridBagConstraints.EAST;
            gridBagConstraints2.insets = new Insets(20, 0, 0, 0);
            gridBagConstraints2.gridy = 2;
            GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
            gridBagConstraints11.gridx = 0;
            gridBagConstraints11.insets = new Insets(20, 10, 0, 0);
            gridBagConstraints11.anchor = GridBagConstraints.EAST;
            gridBagConstraints11.gridy = 2;
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.gridx = 1;
            gridBagConstraints1.gridwidth = 1;
            gridBagConstraints1.fill = GridBagConstraints.NONE;
            gridBagConstraints1.gridy = 1;
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridwidth = 3;
            gridBagConstraints.fill = GridBagConstraints.NONE;
            gridBagConstraints.gridy = 0;
            jContentPane = new JPanel();
            jContentPane.setLayout(new GridBagLayout());
            jContentPane.add(rdiConstantWidth, gridBagConstraints);
            jContentPane.add(rdiVariedWidth, gridBagConstraints1);
            jContentPane.add(lblSize, gridBagConstraints11);
            jContentPane.add(lblWidth, gridBagConstraints2);
            jContentPane.add(lblHeight, gridBagConstraints3);
            jContentPane.add(txtWidth, gridBagConstraints4);
            jContentPane.add(txtHeight, gridBagConstraints5);
            jContentPane.add(lblAlignment, gridBagConstraints6);
            jContentPane.add(rdiLtoR, gridBagConstraints7);
            jContentPane.add(rdiRtoL, gridBagConstraints8);
            jContentPane.add(btnApply, gridBagConstraints9);
            jContentPane.add(btnCancel, gridBagConstraints10);
        }
        return jContentPane;
    }
}  //  @jve:decl-index=0:visual-constraint="10,10"

class CharPropertiesFrame_btnApply_actionAdapter implements ActionListener {

    private CharPropertiesFrame adaptee;

    CharPropertiesFrame_btnApply_actionAdapter(CharPropertiesFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnApply_actionPerformed(e);
    }
}

class CharPropertiesFrame_btnCancel_actionAdapter implements ActionListener {

    private CharPropertiesFrame adaptee;

    CharPropertiesFrame_btnCancel_actionAdapter(CharPropertiesFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnCancel_actionPerformed(e);
    }
}