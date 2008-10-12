package mbfc.gui;

import java.awt.*;
import java.io.RandomAccessFile;
import javax.swing.*;
import mbfc.SharedData;
import mbfc.storage.BitArrayContainer;
import mbfc.storage.CharGroup;
import mbfc.storage.EntryCanvasProperties;
import mbfc.storage.GroupContainer;
import mbfc.storage.micro.MicroEntryCanvasProperties;
import mbfc.storage.micro.MultiLanguageMECP;
import java.awt.event.*;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
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
public class CanvasFrame extends JFrame {

    GroupContainer groups;
    BitArrayContainer font;
    EntryCanvasProperties properties;
    BorderLayout xYLayout1 = new BorderLayout();
    JLabel lblB1 = new JLabel();
    JLabel lblB0 = new JLabel();
    JLabel lblB2 = new JLabel();
    JLabel lblB3 = new JLabel();
    JLabel lblB4 = new JLabel();
    JLabel lblB5 = new JLabel();
    JLabel lblB6 = new JLabel();
    JLabel lblB7 = new JLabel();
    JLabel lblB8 = new JLabel();
    JLabel lblB9 = new JLabel();
    JTextField txtB0 = new JTextField();
    JTextField txtB1 = new JTextField();
    JTextField txtB2 = new JTextField();
    JTextField txtB3 = new JTextField();
    JTextField txtB4 = new JTextField();
    JTextField txtB5 = new JTextField();
    JTextField txtB6 = new JTextField();
    JTextField txtB7 = new JTextField();
    JTextField txtB8 = new JTextField();
    JTextField txtB9 = new JTextField();
    Choice chcButton = new Choice();
    Choice chcNumber = new Choice();
    Choice chcGroups = new Choice();
    JButton btnAdd = new JButton();
    JButton btnModify = new JButton();
    JButton btnDone = new JButton();
    JButton btnJ2ME = new JButton();
    JButton btnRemove = new JButton();
    Choice chcLayout = new Choice();
    JButton btnRemoveLast = new JButton();
    Choice chcCanvasType = new Choice();
    JButton btnAddLayout = new JButton();
    private JPanel jContentPane = null;

    public CanvasFrame() {
        try {
            this.setSize(420, 500);
            jbInit();
            majidInit();
        } catch (Exception ex) {
            MyComment mc = new MyComment("No Group or Bit-Font is Avaiable", "OK", 500);
            mc.show();
            ex.printStackTrace();
        }
    }

    private void majidInit() throws Exception {
        for (int i = 0; i < 10; i++) {
            chcButton.add("Button " + i);
        }
        chcCanvasType.removeAll();
        chcCanvasType.add("Create Canvas without Multiple Layouts - Only first Layout");
        chcCanvasType.add("Create Canvas with Multiple Layouts - All Layouts");
        chcCanvasType.select(0);
        chcLayout.add("Layout-1");
        if (SharedData.charFile.length() == 0) {
            throw new Exception();
        }
        font = new BitArrayContainer(SharedData.charFileName, 256);
        if (SharedData.groupsFile.length() == 0) {
            throw new Exception();
        }
        groups = new GroupContainer(SharedData.groupsFileName);
        fillGroupsChoice();
        SharedData.canvasFile = new RandomAccessFile(SharedData.canvasFileName, "rw");
        if (SharedData.canvasFile.length() == 0) {
            properties = new EntryCanvasProperties();
        } else {
            properties = new EntryCanvasProperties(SharedData.canvasFileName);
            chcLayout.removeAll();
            for (int i = 1; i <= properties.getNumberOfLayouts(); i++) {
                chcLayout.addItem("Layout-" + i);
            }
            chcLayout.select(0);
            fillAllButtonTexts(0);

        }
    }

    private void refreshMRButtons() {
        if ((chcNumber.getSelectedIndex() >= 0) && (chcButton.getSelectedIndex() >= 0)) {
            if (chcGroups.getSelectedIndex() >= 0) {
                btnModify.setEnabled(true);
            } else {
                btnModify.setEnabled(false);
            }
            btnRemove.setEnabled(true);
        } else {
            btnModify.setEnabled(false);
            btnRemove.setEnabled(false);
        }
    }

    private void fillNumberChoice(int toNum) {
        chcNumber.removeAll();
        for (int i = 1; i <= toNum; i++) {
            chcNumber.add(i + "th Group");
        }
    }

    private void fillGroupsChoice() {
        for (int i = 0; i < groups.getSize(); i++) {
            CharGroup group = groups.getGroup(i);
            boolean hasTransparentChar = false;
            if (font.isTransparent(group.getFirst())) {
                hasTransparentChar = true;
            }
            if (font.isTransparent(group.getMiddle())) {
                hasTransparentChar = true;
            }
            if (font.isTransparent(group.getLast())) {
                hasTransparentChar = true;
            }
            if (font.isTransparent(group.getSeprate())) {
                hasTransparentChar = true;
            }
            if (!hasTransparentChar) {
                chcGroups.add(groups.getGroup(i).getName());
            }
        }
    }

    private String getButtonMembersInText(int layout, int button) {
        String temp = "";
        String[] members = properties.getSeq(layout, button);
        if (members.length == 0) {
            return "no groups";
        }
        temp = members[0];
        if (members.length != 1) {
            for (int i = 1; i < members.length; i++) {
                temp = temp + "," + members[i];
            }
        }
        return temp;
    }

    private void fillButtonText(JTextField tf, int layout, int button) {
        tf.setText(getButtonMembersInText(layout, button));
    }

    private void fillAllButtonTexts(int layout) {
        fillButtonText(txtB0, layout, 0);
        fillButtonText(txtB1, layout, 1);
        fillButtonText(txtB2, layout, 2);
        fillButtonText(txtB3, layout, 3);
        fillButtonText(txtB4, layout, 4);
        fillButtonText(txtB5, layout, 5);
        fillButtonText(txtB6, layout, 6);
        fillButtonText(txtB7, layout, 7);
        fillButtonText(txtB8, layout, 8);
        fillButtonText(txtB9, layout, 9);
    }

    private void jbInit() throws Exception {
        this.setResizable(false);
        this.setContentPane(getJContentPane());
        this.setSize(new Dimension(477, 476));
        this.setTitle("Create Canvas");
        lblB1.setText("Button 1:");
        lblB3.setText("Button 3:");
        lblB4.setText("Button 4:");
        lblB5.setText("Button 5:");
        lblB6.setText("Button 6:");
        lblB7.setText("Button 7:");
        lblB8.setText("Button 8:");
        lblB9.setText("Button 9:");
        txtB0.setEditable(false);
        txtB0.setText("no groups");
        txtB1.setEditable(false);
        txtB1.setText("no groups");
        txtB2.setEditable(false);
        txtB2.setText("no groups");
        txtB3.setEditable(false);
        txtB3.setText("no groups");
        txtB4.setEditable(false);
        txtB4.setText("no groups");
        txtB5.setEditable(false);
        txtB5.setText("no groups");
        txtB6.setEditable(false);
        txtB6.setText("no groups");
        txtB7.setEditable(false);
        txtB7.setText("no groups");
        txtB8.setEditable(false);
        txtB8.setText("no groups");
        txtB9.setEditable(false);
        txtB9.setText("no groups");
        btnAdd.setText("Add");
        btnAdd.addActionListener(new CanvasFrame_btnAdd_actionAdapter(this));
        btnModify.setEnabled(false);
        btnModify.setText("Modify");
        btnModify.addActionListener(new CanvasFrame_btnModify_actionAdapter(this));
        btnDone.setText("Done!");
        btnDone.addActionListener(new CanvasFrame_btnDone_actionAdapter(this));
        btnJ2ME.setText("Entry Canvas For J2ME Developers");
        btnJ2ME.addActionListener(new CanvasFrame_btnJ2ME_actionAdapter(this));
        chcGroups.addItemListener(new CanvasFrame_chcGroups_itemAdapter(this));
        btnRemove.setEnabled(false);
        btnRemove.setText("Remove");
        btnRemove.addActionListener(new CanvasFrame_btnRemove_actionAdapter(this));
        chcNumber.addItemListener(new CanvasFrame_chcNumber_itemAdapter(this));
        chcButton.addItemListener(new CanvasFrame_chcButton_itemAdapter(this));
        lblB2.setText("Button 2:");
        lblB0.setText("Button 0: space,enter");


        btnRemoveLast.setText("Remove Last");
        btnRemoveLast.addActionListener(new CanvasFrame_btnRemoveLast_actionAdapter(this));
        btnAddLayout.setText("Add Layout");
        btnAddLayout.addActionListener(new CanvasFrame_btnAddLayout_actionAdapter(this));
        chcLayout.addItemListener(new CanvasFrame_chcLayout_itemAdapter(this));
        jContentPane.add(chcLayout);
    }

    public void btnAdd_actionPerformed(ActionEvent e) {
        if ((chcButton.getSelectedIndex() == -1) ||
                (chcGroups.getSelectedIndex() == -1)) {
            return;
        }
        JTextField txt = getSelectedJTextField(chcButton.getSelectedIndex());
        properties.addToButton(chcLayout.getSelectedIndex(), chcButton.getSelectedIndex(),
                chcGroups.getSelectedItem());
        fillButtonText(txt, chcLayout.getSelectedIndex(),
                chcButton.getSelectedIndex());
        fillNumberChoice(
                properties.getNumberOfGroupsInButton(
                chcLayout.getSelectedIndex(), chcButton.getSelectedIndex()));
        refreshMRButtons();
    }

    private JTextField getSelectedJTextField(int number) {
        JTextField txt = txtB0;
        switch (number) {
            case 0: {
                txt = txtB0;
                break;
            }
            case 1: {
                txt = txtB1;
                break;
            }
            case 2: {
                txt = txtB2;
                break;
            }
            case 3: {
                txt = txtB3;
                break;
            }
            case 4: {
                txt = txtB4;
                break;
            }
            case 5: {
                txt = txtB5;
                break;
            }
            case 6: {
                txt = txtB6;
                break;
            }
            case 7: {
                txt = txtB7;
                break;
            }
            case 8: {
                txt = txtB8;
                break;
            }
            case 9: {
                txt = txtB9;
                break;
            }
        }
        return txt;
    }

    public void chcGroups_itemStateChanged(ItemEvent e) {
    }

    public void btnModify_actionPerformed(ActionEvent e) {
        if ((chcButton.getSelectedIndex() == -1) ||
                (chcNumber.getSelectedIndex() == -1) ||
                (chcGroups.getSelectedIndex() == -1)) {
            return;
        }
        JTextField txt = getSelectedJTextField(chcButton.getSelectedIndex());
        properties.setToButton(chcLayout.getSelectedIndex(),
                chcButton.getSelectedIndex(),
                chcNumber.getSelectedIndex(),
                chcGroups.getSelectedItem());
        fillButtonText(txt, chcLayout.getSelectedIndex(),
                chcButton.getSelectedIndex());
    }

    public void chcNumber_itemStateChanged(ItemEvent e) {
        /*if(chcGroups.getSelectedIndex()>=0){
        btnModify.setEnabled(true);
        btnRemove.setEnabled(true);
        }
        else{
        btnModify.setEnabled(false);
        btnRemove.setEnabled(false);
        }*/
        refreshMRButtons();
    }

    public void btnRemove_actionPerformed(ActionEvent e) {
        if ((chcButton.getSelectedIndex() == -1) ||
                (chcNumber.getSelectedIndex() == -1)) {
            return;
        }
        JTextField txt = getSelectedJTextField(chcButton.getSelectedIndex());
        properties.removeFromButton(chcLayout.getSelectedIndex(),
                chcButton.getSelectedIndex(),
                chcNumber.getSelectedIndex());
        fillButtonText(txt, chcLayout.getSelectedIndex(),
                chcButton.getSelectedIndex());
    }

    public void chcButton_itemStateChanged(ItemEvent e) {
        if (chcButton.getSelectedIndex() != -1) {
            fillNumberChoice(properties.getNumberOfGroupsInButton(
                    chcLayout.getSelectedIndex(), chcButton.getSelectedIndex()));
        }
        refreshMRButtons();
    }

    public void btnDone_actionPerformed(ActionEvent e) {
        properties.saveToFile(SharedData.canvasFileName);
        this.dispose();
    }

    public void btnJ2ME_actionPerformed(ActionEvent e) {
        if (chcCanvasType.getSelectedIndex() == 0) {
            MicroEntryCanvasProperties mecp = new MicroEntryCanvasProperties(properties, groups);
            FileDialog dialog = new FileDialog(this, "Chose a name...",
                    FileDialog.SAVE);
            dialog.show();
            if ((dialog.getFile().length() > 0) && (dialog.getFile() != null)) {
                mecp.saveToFile(dialog.getDirectory() + dialog.getFile() + ".mcv");
            }
        } else if (chcCanvasType.getSelectedIndex() == 1) {
            MultiLanguageMECP mecp = new MultiLanguageMECP(properties, groups);
            FileDialog dialog = new FileDialog(this, "Chose a name...",
                    FileDialog.SAVE);
            dialog.show();
            if ((dialog.getFile().length() > 0) && (dialog.getFile() != null)) {
                mecp.saveToFile(dialog.getDirectory() + dialog.getFile() + ".mlc");
            }
        }

    }

    void btnRemoveLast_actionPerformed(ActionEvent e) {
        if (chcLayout.countItems() > 2) {
            chcLayout.remove(chcLayout.countItems() - 1);
            properties.removeLayout();
            chcLayout.select(chcLayout.countItems() - 1);
            fillAllButtonTexts(chcLayout.getSelectedIndex());

        }
    }

    void btnAddLayout_actionPerformed(ActionEvent e) {
        if (chcLayout.countItems() < 6) {
            chcLayout.add("Layout-" + (chcLayout.countItems() + 1));
            properties.addLayout();
            chcLayout.select(chcLayout.countItems() - 1);
            fillAllButtonTexts(chcLayout.getSelectedIndex());
        }
    }

    void chcLayout_itemStateChanged(ItemEvent e) {
        fillAllButtonTexts(chcLayout.getSelectedIndex());
    }

    /**
     * This method initializes jContentPane	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            GridBagConstraints gridBagConstraints141 = new GridBagConstraints();
            gridBagConstraints141.gridx = 0;
            gridBagConstraints141.gridwidth = 3;
            gridBagConstraints141.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints141.insets = new Insets(5, 0, 5, 0);
            gridBagConstraints141.gridy = 14;
            GridBagConstraints gridBagConstraints131 = new GridBagConstraints();
            gridBagConstraints131.gridx = 0;
            gridBagConstraints131.gridwidth = 3;
            gridBagConstraints131.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints131.gridy = 15;
            GridBagConstraints gridBagConstraints121 = new GridBagConstraints();
            gridBagConstraints121.gridx = 2;
            gridBagConstraints121.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints121.insets = new Insets(10, 0, 0, 0);
            gridBagConstraints121.gridy = 12;
            GridBagConstraints gridBagConstraints111 = new GridBagConstraints();
            gridBagConstraints111.gridx = 0;
            gridBagConstraints111.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints111.gridwidth = 3;
            gridBagConstraints111.ipady = 0;
            gridBagConstraints111.insets = new Insets(20, 0, 0, 0);
            gridBagConstraints111.gridy = 13;
            GridBagConstraints gridBagConstraints101 = new GridBagConstraints();
            gridBagConstraints101.gridx = 1;
            gridBagConstraints101.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints101.insets = new Insets(10, 0, 0, 0);
            gridBagConstraints101.gridy = 12;
            GridBagConstraints gridBagConstraints91 = new GridBagConstraints();
            gridBagConstraints91.gridx = 0;
            gridBagConstraints91.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints91.insets = new Insets(10, 0, 0, 0);
            gridBagConstraints91.gridy = 12;
            GridBagConstraints gridBagConstraints81 = new GridBagConstraints();
            gridBagConstraints81.gridx = 2;
            gridBagConstraints81.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints81.insets = new Insets(10, 5, 0, 5);
            gridBagConstraints81.gridy = 11;
            GridBagConstraints gridBagConstraints71 = new GridBagConstraints();
            gridBagConstraints71.gridx = 1;
            gridBagConstraints71.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints71.insets = new Insets(10, 5, 0, 5);
            gridBagConstraints71.gridy = 11;
            GridBagConstraints gridBagConstraints61 = new GridBagConstraints();
            gridBagConstraints61.gridx = 0;
            gridBagConstraints61.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints61.insets = new Insets(10, 5, 0, 5);
            gridBagConstraints61.gridy = 11;
            GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
            gridBagConstraints22.anchor = GridBagConstraints.WEST;
            gridBagConstraints22.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints22.gridx = 0;
            gridBagConstraints22.gridy = 10;
            GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
            gridBagConstraints21.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints21.weightx = 0.0;
            gridBagConstraints21.gridwidth = 2;
            gridBagConstraints21.gridx = 1;
            gridBagConstraints21.gridy = 10;
            GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
            gridBagConstraints20.anchor = GridBagConstraints.WEST;
            gridBagConstraints20.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints20.gridx = 0;
            gridBagConstraints20.gridy = 9;
            GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
            gridBagConstraints19.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints19.weightx = 0.0;
            gridBagConstraints19.gridwidth = 2;
            gridBagConstraints19.gridx = 1;
            gridBagConstraints19.gridy = 9;
            GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
            gridBagConstraints18.anchor = GridBagConstraints.WEST;
            gridBagConstraints18.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints18.gridx = 0;
            gridBagConstraints18.gridy = 8;
            GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
            gridBagConstraints17.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints17.weightx = 0.0;
            gridBagConstraints17.gridwidth = 2;
            gridBagConstraints17.gridx = 1;
            gridBagConstraints17.gridy = 8;
            GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
            gridBagConstraints16.anchor = GridBagConstraints.WEST;
            gridBagConstraints16.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints16.gridx = 0;
            gridBagConstraints16.gridy = 7;
            GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
            gridBagConstraints15.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints15.weightx = 0.0;
            gridBagConstraints15.gridwidth = 2;
            gridBagConstraints15.gridx = 1;
            gridBagConstraints15.gridy = 7;
            GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
            gridBagConstraints14.anchor = GridBagConstraints.WEST;
            gridBagConstraints14.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints14.gridx = 0;
            gridBagConstraints14.gridy = 6;
            GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
            gridBagConstraints13.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints13.weightx = 0.0;
            gridBagConstraints13.gridwidth = 2;
            gridBagConstraints13.gridx = 1;
            gridBagConstraints13.gridy = 6;
            GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
            gridBagConstraints12.anchor = GridBagConstraints.WEST;
            gridBagConstraints12.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints12.gridx = 0;
            gridBagConstraints12.gridy = 5;
            GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
            gridBagConstraints10.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints10.weightx = 0.0;
            gridBagConstraints10.gridwidth = 2;
            gridBagConstraints10.gridx = 1;
            gridBagConstraints10.gridy = 5;
            GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
            gridBagConstraints9.anchor = GridBagConstraints.WEST;
            gridBagConstraints9.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints9.gridx = 0;
            gridBagConstraints9.gridy = 4;
            GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
            gridBagConstraints8.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints8.weightx = 0.0;
            gridBagConstraints8.gridwidth = 2;
            gridBagConstraints8.gridx = 1;
            gridBagConstraints8.gridy = 4;
            GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
            gridBagConstraints7.gridx = 0;
            gridBagConstraints7.anchor = GridBagConstraints.WEST;
            gridBagConstraints7.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints7.gridy = 3;
            GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
            gridBagConstraints6.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints6.gridy = 3;
            gridBagConstraints6.weightx = 0.0;
            gridBagConstraints6.gridwidth = 2;
            gridBagConstraints6.gridx = 1;
            GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
            gridBagConstraints5.gridx = 0;
            gridBagConstraints5.anchor = GridBagConstraints.WEST;
            gridBagConstraints5.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints5.gridy = 2;
            GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
            gridBagConstraints4.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints4.gridy = 2;
            gridBagConstraints4.weightx = 0.0;
            gridBagConstraints4.gridwidth = 2;
            gridBagConstraints4.gridx = 1;
            GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
            gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints3.gridy = 1;
            gridBagConstraints3.weightx = 0.0;
            gridBagConstraints3.gridwidth = 2;
            gridBagConstraints3.gridx = 1;
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.gridx = 0;
            gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints2.gridy = 1;
            GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
            gridBagConstraints11.gridx = 2;
            gridBagConstraints11.anchor = GridBagConstraints.WEST;
            gridBagConstraints11.ipadx = 50;
            gridBagConstraints11.gridy = 0;
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.gridx = 1;
            gridBagConstraints1.ipadx = 50;
            gridBagConstraints1.gridy = 0;
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.insets = new Insets(0, 0, 0, 0);
            gridBagConstraints.gridy = 0;
            gridBagConstraints.ipadx = 0;
            gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints.gridwidth = 1;
            gridBagConstraints.gridx = 0;
            jContentPane = new JPanel();
            jContentPane.setLayout(new GridBagLayout());
            jContentPane.add(chcLayout, gridBagConstraints);
            jContentPane.add(btnRemoveLast, gridBagConstraints1);
            jContentPane.add(btnAddLayout, gridBagConstraints11);
            jContentPane.add(lblB0, gridBagConstraints2);
            jContentPane.add(txtB0, gridBagConstraints3);
            jContentPane.add(txtB1, gridBagConstraints4);
            jContentPane.add(lblB1, gridBagConstraints5);
            jContentPane.add(txtB2, gridBagConstraints6);
            jContentPane.add(lblB2, gridBagConstraints7);
            jContentPane.add(txtB3, gridBagConstraints8);
            jContentPane.add(lblB3, gridBagConstraints9);
            jContentPane.add(txtB4, gridBagConstraints10);
            jContentPane.add(lblB4, gridBagConstraints12);
            jContentPane.add(txtB5, gridBagConstraints13);
            jContentPane.add(lblB5, gridBagConstraints14);
            jContentPane.add(txtB6, gridBagConstraints15);
            jContentPane.add(lblB6, gridBagConstraints16);
            jContentPane.add(txtB7, gridBagConstraints17);
            jContentPane.add(lblB7, gridBagConstraints18);
            jContentPane.add(txtB8, gridBagConstraints19);
            jContentPane.add(lblB8, gridBagConstraints20);
            jContentPane.add(txtB9, gridBagConstraints21);
            jContentPane.add(lblB9, gridBagConstraints22);
            jContentPane.add(chcButton, gridBagConstraints61);
            jContentPane.add(chcNumber, gridBagConstraints71);
            jContentPane.add(chcGroups, gridBagConstraints81);
            jContentPane.add(btnAdd, gridBagConstraints91);
            jContentPane.add(btnModify, gridBagConstraints101);
            jContentPane.add(btnDone, gridBagConstraints111);
            jContentPane.add(btnRemove, gridBagConstraints121);
            jContentPane.add(btnJ2ME, gridBagConstraints131);
            jContentPane.add(chcCanvasType, gridBagConstraints141);
        }
        return jContentPane;
    }
}  //  @jve:decl-index=0:visual-constraint="10,10"

class CanvasFrame_btnJ2ME_actionAdapter implements ActionListener {

    private CanvasFrame adaptee;

    CanvasFrame_btnJ2ME_actionAdapter(CanvasFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnJ2ME_actionPerformed(e);
    }
}

class CanvasFrame_btnDone_actionAdapter implements ActionListener {

    private CanvasFrame adaptee;

    CanvasFrame_btnDone_actionAdapter(CanvasFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnDone_actionPerformed(e);
    }
}

class CanvasFrame_btnRemove_actionAdapter implements ActionListener {

    private CanvasFrame adaptee;

    CanvasFrame_btnRemove_actionAdapter(CanvasFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnRemove_actionPerformed(e);
    }
}

class CanvasFrame_chcButton_itemAdapter implements ItemListener {

    private CanvasFrame adaptee;

    CanvasFrame_chcButton_itemAdapter(CanvasFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void itemStateChanged(ItemEvent e) {
        adaptee.chcButton_itemStateChanged(e);
    }
}

class CanvasFrame_chcGroups_itemAdapter implements ItemListener {

    private CanvasFrame adaptee;

    CanvasFrame_chcGroups_itemAdapter(CanvasFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void itemStateChanged(ItemEvent e) {
        adaptee.chcGroups_itemStateChanged(e);
    }
}

class CanvasFrame_chcNumber_itemAdapter implements ItemListener {

    private CanvasFrame adaptee;

    CanvasFrame_chcNumber_itemAdapter(CanvasFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void itemStateChanged(ItemEvent e) {
        adaptee.chcNumber_itemStateChanged(e);
    }
}

class CanvasFrame_btnAdd_actionAdapter implements ActionListener {

    private CanvasFrame adaptee;

    CanvasFrame_btnAdd_actionAdapter(CanvasFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnAdd_actionPerformed(e);
    }
}

class CanvasFrame_btnModify_actionAdapter implements ActionListener {

    private CanvasFrame adaptee;

    CanvasFrame_btnModify_actionAdapter(CanvasFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnModify_actionPerformed(e);
    }
}

class CanvasFrame_btnRemoveLast_actionAdapter implements java.awt.event.ActionListener {

    CanvasFrame adaptee;

    CanvasFrame_btnRemoveLast_actionAdapter(CanvasFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnRemoveLast_actionPerformed(e);
    }
}

class CanvasFrame_btnAddLayout_actionAdapter implements java.awt.event.ActionListener {

    CanvasFrame adaptee;

    CanvasFrame_btnAddLayout_actionAdapter(CanvasFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnAddLayout_actionPerformed(e);
    }
}

class CanvasFrame_chcLayout_itemAdapter implements java.awt.event.ItemListener {

    CanvasFrame adaptee;

    CanvasFrame_chcLayout_itemAdapter(CanvasFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void itemStateChanged(ItemEvent e) {
        adaptee.chcLayout_itemStateChanged(e);
    }
}