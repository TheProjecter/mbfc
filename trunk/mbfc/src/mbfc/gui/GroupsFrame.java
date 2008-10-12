package mbfc.gui;

import java.awt.*;
import javax.swing.*;
import mbfc.SharedData;
import mbfc.render.PreviewCanvas;
import mbfc.storage.BitArrayContainer;
import mbfc.storage.CharGroup;
import mbfc.storage.GroupContainer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.RandomAccessFile;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;

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
public class GroupsFrame extends JFrame {

    MainFrame mf;
    GroupContainer groups;
    BitArrayContainer font;
    PreviewCanvas firstPreview;  //  @jve:decl-index=0:visual-constraint="476,179"
    PreviewCanvas middlePreview;
    PreviewCanvas lastPreview;  //  @jve:decl-index=0:visual-constraint="480,131"
    PreviewCanvas sepratePreview;    //BorderLayout xYLayout1 = new BorderLayout();
    JScrollPane scrlNames = new JScrollPane();
    JCheckBox chkAlwaysLast = new JCheckBox();
    JLabel lblSetAll = new JLabel();
    Choice chcAll = new Choice();
    Label lblFirst = new Label();
    Choice chcFirst = new Choice();
    JLabel lblMiddle = new JLabel();  //  @jve:decl-index=0:visual-constraint="494,144"
    JLabel lblLast = new JLabel();
    JLabel lblSeprate = new JLabel();  //  @jve:decl-index=0:visual-constraint="491,214"
    Choice chcMiddle = new Choice();
    Choice chcLast = new Choice();
    Choice chcSeprate = new Choice();
    JScrollPane scrlFirst = new JScrollPane();
    JScrollPane scrlMiddle = new JScrollPane();
    JScrollPane scrlLast = new JScrollPane();
    JScrollPane scrlSeprate = new JScrollPane();
    JButton btnAdd = new JButton();
    JButton btnRemove = new JButton();
    List lstNames = new List();
    JLabel lblName = new JLabel();
    JLabel lblSelectedIs = new JLabel();
    JLabel lblSelected = new JLabel();
    TextField txtName = new TextField();
    JButton btnDone = new JButton();
    private JPanel jContentPane = null;

    public GroupsFrame(MainFrame mf) {        
        this.mf = mf;
        this.setSize(475, 600);
        try {
            jbInit();
            majidInit();
            this.setContentPane(getJContentPane());
        } catch (Exception exception) {
            MyComment mc = new MyComment("error in opening file", "OK", 500);
            exception.printStackTrace();
        }
    }

    private void majidInit() throws Exception {

        font = new BitArrayContainer(SharedData.charFileName, 256);
        for (int i = 2; i < 256; i++) {
            chcAll.add("" + i);
            chcFirst.add("" + i);
            chcMiddle.add("" + i);
            chcLast.add("" + i);
            chcSeprate.add("" + i);
        }

        chcAll.select(0);
        chcFirst.select(0);
        chcMiddle.select(0);
        chcLast.select(0);
        chcSeprate.select(0);

        scrlFirst.setSize(70, 70);
        firstPreview = new PreviewCanvas(font.getCharacter(2));
        middlePreview = new PreviewCanvas(font.getCharacter(2));
        lastPreview = new PreviewCanvas(font.getCharacter(2));
        sepratePreview = new PreviewCanvas(font.getCharacter(2));
        scrlFirst.add(firstPreview);
        firstPreview.setSize(70, 70);
        scrlMiddle.add(middlePreview);
        middlePreview.setSize(70, 70);
        scrlLast.add(lastPreview);
        lastPreview.setSize(70, 70);
        scrlSeprate.add(sepratePreview);
        sepratePreview.setSize(70, 70);
        showAllPreviews(2);

        groups = new GroupContainer();
        SharedData.groupsFile = new RandomAccessFile(SharedData.groupsFileName, "rw");
        if (SharedData.groupsFile.length() != 0) {
            if (!groups.LoadFromFile(SharedData.groupsFileName)) {
                throw new Exception();
            }
        }
        String[] names = groups.getGroups();
        for (int i = 0; i < names.length; i++) {
            lstNames.add(names[i]);
        }
    }

    private void showChar(int number, PreviewCanvas preview) {
        preview.changeArray(font.getCharacter(number));
        preview.repaint();
    }

    private void showAllPreviews(int number) {
        showChar(number, firstPreview);
        showChar(number, middlePreview);
        showChar(number, lastPreview);
        showChar(number, sepratePreview);
    }

    private void jbInit() throws Exception {
        this.setResizable(false);
        //this.setContentPane(getJContentPane());
        this.setSize(new Dimension(454, 527));
        this.setTitle("Organize Groups");
        lblSetAll.setText("Set all to:");
        lblFirst.setText("First:");
        lblFirst.setFont(new Font("Dialog", Font.BOLD, 12));
        lblMiddle.setText("Middle:");
        lblLast.setText("Last:");
        lblSeprate.setText("Separate:");
        btnAdd.setText("Add/Mod");
        btnAdd.addActionListener(new GroupsFrame_btnAdd_actionAdapter(this));
        btnRemove.setText("Remove");
        btnRemove.addActionListener(new GroupsFrame_btnRemove_actionAdapter(this));
        lblName.setText("Name:");
        lblSelectedIs.setText("Selected Is:");
        lblSelected.setText("0");
        txtName.setText("Alef");
        txtName.setSize(new Dimension(0, 0));
        txtName.addTextListener(new GroupsFrame_txtName_textAdapter(this));
        scrlNames.addMouseListener(new GroupsFrame_scrlNames_mouseAdapter(this));
        chcAll.addItemListener(new GroupsFrame_chcAll_itemAdapter(this));
        chcFirst.addItemListener(new GroupsFrame_chcFirst_itemAdapter(this));
        chcMiddle.addItemListener(new GroupsFrame_chcMiddle_itemAdapter(this));
        chcLast.addItemListener(new GroupsFrame_chcLast_itemAdapter(this));
        chcSeprate.addItemListener(new GroupsFrame_chcSeprate_itemAdapter(this));
        btnDone.setText("Done!");
        btnDone.addActionListener(new GroupsFrame_btnDone_actionAdapter(this));
        lstNames.addMouseListener(new GroupsFrame_lstNames_mouseAdapter(this));
        scrlNames.add(lstNames);
        lstNames.setSize(155, 370);
        chkAlwaysLast.setText("Always is Last Character ");
    }

    public void btnAdd_actionPerformed(ActionEvent e) {
        short first = Short.parseShort(chcFirst.getSelectedItem());
        short middle = Short.parseShort(chcMiddle.getSelectedItem());
        short last = Short.parseShort(chcLast.getSelectedItem());
        short seprate = Short.parseShort(chcSeprate.getSelectedItem());
        boolean isLast = chkAlwaysLast.isSelected();
        if (!groups.modifyOrAddGroup(txtName.getText(), first, middle, last, seprate, isLast)) {
            lstNames.add(txtName.getText());
        }
        lstNames.select(-1);
        /*because of my algorithm in entry canvas, one character can't be used
        in multiple groups.---> see EntryCanvas method searchAndGet
        or getGroup methods in MicroEntryCanvasProperties*/
        for (int i = 0; i < groups.getSize(); i++) {
            CharGroup group = groups.getGroup(i);
            if (group.getName().compareTo(txtName.getText()) != 0) {
                if ((group.getFirst() == first) || (group.getMiddle() == middle) || (group.getLast() == last) || (group.getSeprate() == seprate)) {
                    MyComment mc = new MyComment("Some characters has repeated" + " in 2 or more group, now You" + " can't use Entry-Canvas properly",
                            "OK", 600);
                    mc.show();
                    return;
                }
            }
        }

    }

    public void txtName_textValueChanged(TextEvent e) {
        if (groups.searchForIt(txtName.getText()) != null) {
            btnAdd.setText("Modify");
        } else {
            btnAdd.setText("Add Group");
        }
    }

    public void btnRemove_actionPerformed(ActionEvent e) {
        if (lstNames.getSelectedIndex() != -1) {
            groups.removeGroup(lstNames.getSelectedIndex());
            lstNames.delItem(lstNames.getSelectedIndex());
        }
    }

    public void scrlNames_mouseClicked(MouseEvent e) {
    }

    public void chcAll_itemStateChanged(ItemEvent e) {
        int sel = chcAll.getSelectedIndex() + 2;
        showAllPreviews(sel);
        chcFirst.select(sel - 2);
        chcMiddle.select(sel - 2);
        chcLast.select(sel - 2);
        chcSeprate.select(sel - 2);
    }

    public void chcFirst_itemStateChanged(ItemEvent e) {
        int sel = chcFirst.getSelectedIndex() + 2;
        showChar(sel, firstPreview);
    }

    public void chcMiddle_itemStateChanged(ItemEvent e) {
        int sel = chcMiddle.getSelectedIndex() + 2;
        showChar(sel, middlePreview);
    }

    public void chcLast_itemStateChanged(ItemEvent e) {
        int sel = chcLast.getSelectedIndex() + 2;
        showChar(sel, lastPreview);
    }

    public void chcSeprate_itemStateChanged(ItemEvent e) {
        int sel = chcSeprate.getSelectedIndex() + 2;
        showChar(sel, sepratePreview);
    }

    public void lstNames_mouseClicked(MouseEvent e) {
        btnAdd.setText("Modify");
        int i = lstNames.getSelectedIndex();
        CharGroup cg = groups.getGroup(i);
        lblSelected.setText(i + "");
        int first = cg.getFirst();
        if (first < 0) {
            first += 256;
        }
        chcFirst.select(first - 2);
        int middle = cg.getMiddle();
        if (middle < 0) {
            middle += 256;
        }
        chcMiddle.select(middle - 2);
        int last = cg.getLast();
        if (last < 0) {
            last += 256;
        }
        chcLast.select(last - 2);
        int seprate = cg.getSeprate();
        if (seprate < 0) {
            seprate += 256;
        }
        chcSeprate.select(seprate - 2);
        txtName.setText(cg.getName());
        showChar(first, firstPreview);
        showChar(middle, middlePreview);
        showChar(last, lastPreview);
        showChar(seprate, sepratePreview);
        chkAlwaysLast.setSelected(cg.isIsLast());
    }

    public void btnDone_actionPerformed(ActionEvent e) {
        mf.btnLinker.setEnabled(true);
        groups.saveToFile(SharedData.groupsFileName);
        try {
            SharedData.groupsFile.close();
        } catch (Exception ex) {
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
            GridBagConstraints gridBagConstraints201 = new GridBagConstraints();
            gridBagConstraints201.gridx = 0;
            gridBagConstraints201.insets = new Insets(10, 0, 0, 0);
            gridBagConstraints201.gridwidth = 3;
            gridBagConstraints201.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints201.gridy = 12;
            GridBagConstraints gridBagConstraints191 = new GridBagConstraints();
            gridBagConstraints191.gridx = 2;
            gridBagConstraints191.insets = new Insets(20, 0, 0, 0);
            gridBagConstraints191.gridy = 11;
            GridBagConstraints gridBagConstraints181 = new GridBagConstraints();
            gridBagConstraints181.gridx = 1;
            gridBagConstraints181.fill = GridBagConstraints.NONE;
            gridBagConstraints181.insets = new Insets(20, 0, 0, 0);
            gridBagConstraints181.gridy = 11;
            GridBagConstraints gridBagConstraints171 = new GridBagConstraints();
            gridBagConstraints171.fill = GridBagConstraints.BOTH;
            gridBagConstraints171.gridy = 8;
            gridBagConstraints171.weightx = 1.0;
            gridBagConstraints171.weighty = 1.0;
            gridBagConstraints171.gridx = 3;
            GridBagConstraints gridBagConstraints161 = new GridBagConstraints();
            gridBagConstraints161.fill = GridBagConstraints.BOTH;
            gridBagConstraints161.gridy = 9;
            gridBagConstraints161.weightx = 1.0;
            gridBagConstraints161.weighty = 1.0;
            gridBagConstraints161.gridx = 2;
            GridBagConstraints gridBagConstraints29 = new GridBagConstraints();
            gridBagConstraints29.gridx = 2;
            gridBagConstraints29.fill = GridBagConstraints.NONE;
            gridBagConstraints29.ipadx = 70;
            gridBagConstraints29.ipady = 70;
            gridBagConstraints29.gridy = 10;
            GridBagConstraints gridBagConstraints28 = new GridBagConstraints();
            gridBagConstraints28.gridx = 1;
            gridBagConstraints28.ipadx = 70;
            gridBagConstraints28.ipady = 70;
            gridBagConstraints28.gridy = 10;
            GridBagConstraints gridBagConstraints27 = new GridBagConstraints();
            gridBagConstraints27.gridx = 2;
            gridBagConstraints27.ipadx = 70;
            gridBagConstraints27.ipady = 70;
            gridBagConstraints27.gridy = 9;
            GridBagConstraints gridBagConstraints26 = new GridBagConstraints();
            gridBagConstraints26.gridx = 1;
            gridBagConstraints26.ipadx = 70;
            gridBagConstraints26.ipady = 70;
            gridBagConstraints26.gridy = 9;
            GridBagConstraints gridBagConstraints25 = new GridBagConstraints();
            gridBagConstraints25.gridx = 2;
            gridBagConstraints25.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints25.insets = new Insets(5, 0, 5, 0);
            gridBagConstraints25.gridy = 1;
            GridBagConstraints gridBagConstraints24 = new GridBagConstraints();
            gridBagConstraints24.gridx = 2;
            gridBagConstraints24.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints24.insets = new Insets(5, 0, 5, 0);
            gridBagConstraints24.gridy = 7;
            GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
            gridBagConstraints23.gridx = 1;
            gridBagConstraints23.anchor = GridBagConstraints.EAST;
            gridBagConstraints23.insets = new Insets(5, 0, 5, 0);
            gridBagConstraints23.gridy = 7;
            GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
            gridBagConstraints22.gridx = 2;
            gridBagConstraints22.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints22.insets = new Insets(5, 0, 0, 0);
            gridBagConstraints22.gridy = 6;
            GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
            gridBagConstraints21.gridx = 1;
            gridBagConstraints21.anchor = GridBagConstraints.EAST;
            gridBagConstraints21.insets = new Insets(5, 0, 0, 0);
            gridBagConstraints21.gridy = 6;
            GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
            gridBagConstraints20.gridx = 2;
            gridBagConstraints20.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints20.insets = new Insets(5, 0, 0, 0);
            gridBagConstraints20.gridy = 5;
            GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
            gridBagConstraints19.gridx = 1;
            gridBagConstraints19.anchor = GridBagConstraints.EAST;
            gridBagConstraints19.insets = new Insets(5, 0, 0, 0);
            gridBagConstraints19.gridy = 5;
            GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
            gridBagConstraints18.gridx = 2;
            gridBagConstraints18.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints18.insets = new Insets(5, 0, 0, 0);
            gridBagConstraints18.gridy = 4;
            GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
            gridBagConstraints17.gridx = 1;
            gridBagConstraints17.anchor = GridBagConstraints.EAST;
            gridBagConstraints17.insets = new Insets(5, 0, 0, 0);
            gridBagConstraints17.gridy = 4;
            GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
            gridBagConstraints16.gridx = 2;
            gridBagConstraints16.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints16.insets = new Insets(5, 0, 0, 0);
            gridBagConstraints16.gridy = 3;
            GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
            gridBagConstraints15.gridx = 1;
            gridBagConstraints15.anchor = GridBagConstraints.EAST;
            gridBagConstraints15.insets = new Insets(5, 0, 0, 0);
            gridBagConstraints15.gridy = 3;
            GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
            gridBagConstraints14.gridx = 1;
            gridBagConstraints14.gridy = 2;
            GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
            gridBagConstraints13.gridx = 1;
            gridBagConstraints13.anchor = GridBagConstraints.EAST;
            gridBagConstraints13.insets = new Insets(5, 0, 5, 5);
            gridBagConstraints13.gridy = 1;
            GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
            gridBagConstraints12.gridx = 2;
            gridBagConstraints12.fill = GridBagConstraints.NONE;
            gridBagConstraints12.gridy = 0;
            GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
            gridBagConstraints11.gridx = 1;
            gridBagConstraints11.anchor = GridBagConstraints.EAST;
            gridBagConstraints11.gridy = 0;
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.insets = new Insets(0, 0, 0, 0);
            gridBagConstraints.gridy = 0;
            gridBagConstraints.fill = GridBagConstraints.BOTH;
            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridheight = 12;
            gridBagConstraints.ipadx = 150;
            gridBagConstraints.gridx = 0;
            jContentPane = new JPanel();
            jContentPane.setLayout(new GridBagLayout());
            jContentPane.add(scrlNames, gridBagConstraints);
            jContentPane.add(lblSelectedIs, gridBagConstraints11);
            jContentPane.add(lblSelected, gridBagConstraints12);
            jContentPane.add(lblName, gridBagConstraints13);
            jContentPane.add(chkAlwaysLast, gridBagConstraints14);
            jContentPane.add(lblSetAll, gridBagConstraints15);
            jContentPane.add(chcAll, gridBagConstraints16);
            jContentPane.add(lblFirst, gridBagConstraints17);
            jContentPane.add(chcFirst, gridBagConstraints18);
            jContentPane.add(lblMiddle, gridBagConstraints19);
            jContentPane.add(chcMiddle, gridBagConstraints20);
            jContentPane.add(lblLast, gridBagConstraints21);
            jContentPane.add(chcLast, gridBagConstraints22);
            jContentPane.add(lblSeprate, gridBagConstraints23);
            jContentPane.add(chcSeprate, gridBagConstraints24);
            jContentPane.add(txtName, gridBagConstraints25);
            jContentPane.add(scrlFirst, gridBagConstraints26);
            jContentPane.add(scrlMiddle, gridBagConstraints27);
            jContentPane.add(scrlLast, gridBagConstraints28);
            jContentPane.add(scrlSeprate, gridBagConstraints29);
            scrlFirst.add(firstPreview);
            scrlMiddle.add(middlePreview);
            scrlLast.add(lastPreview);
            scrlSeprate.add(sepratePreview);
            scrlNames.add(lstNames);
            jContentPane.add(btnAdd, gridBagConstraints181);
            jContentPane.add(btnRemove, gridBagConstraints191);
            jContentPane.add(btnDone, gridBagConstraints201);
        }
        return jContentPane;
    }
}  //  @jve:decl-index=0:visual-constraint="10,10"

class GroupsFrame_btnDone_actionAdapter implements ActionListener {

    private GroupsFrame adaptee;

    GroupsFrame_btnDone_actionAdapter(GroupsFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnDone_actionPerformed(e);
    }
}

class GroupsFrame_lstNames_mouseAdapter extends MouseAdapter {

    private GroupsFrame adaptee;

    GroupsFrame_lstNames_mouseAdapter(GroupsFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void mouseClicked(MouseEvent e) {
        adaptee.lstNames_mouseClicked(e);
    }
}

class GroupsFrame_chcSeprate_itemAdapter implements ItemListener {

    private GroupsFrame adaptee;

    GroupsFrame_chcSeprate_itemAdapter(GroupsFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void itemStateChanged(ItemEvent e) {
        adaptee.chcSeprate_itemStateChanged(e);
    }
}

class GroupsFrame_chcLast_itemAdapter implements ItemListener {

    private GroupsFrame adaptee;

    GroupsFrame_chcLast_itemAdapter(GroupsFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void itemStateChanged(ItemEvent e) {
        adaptee.chcLast_itemStateChanged(e);
    }
}

class GroupsFrame_chcAll_itemAdapter implements ItemListener {

    private GroupsFrame adaptee;

    GroupsFrame_chcAll_itemAdapter(GroupsFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void itemStateChanged(ItemEvent e) {
        adaptee.chcAll_itemStateChanged(e);
    }
}

class GroupsFrame_chcMiddle_itemAdapter implements ItemListener {

    private GroupsFrame adaptee;

    GroupsFrame_chcMiddle_itemAdapter(GroupsFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void itemStateChanged(ItemEvent e) {
        adaptee.chcMiddle_itemStateChanged(e);
    }
}

class GroupsFrame_scrlNames_mouseAdapter extends MouseAdapter {

    private GroupsFrame adaptee;

    GroupsFrame_scrlNames_mouseAdapter(GroupsFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void mouseClicked(MouseEvent e) {
        adaptee.scrlNames_mouseClicked(e);
    }
}

class GroupsFrame_btnRemove_actionAdapter implements ActionListener {

    private GroupsFrame adaptee;

    GroupsFrame_btnRemove_actionAdapter(GroupsFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnRemove_actionPerformed(e);
    }
}

class GroupsFrame_txtName_textAdapter implements TextListener {

    private GroupsFrame adaptee;

    GroupsFrame_txtName_textAdapter(GroupsFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void textValueChanged(TextEvent e) {
        adaptee.txtName_textValueChanged(e);
    }
}

class GroupsFrame_btnAdd_actionAdapter implements ActionListener {

    private GroupsFrame adaptee;

    GroupsFrame_btnAdd_actionAdapter(GroupsFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnAdd_actionPerformed(e);
    }
}

class GroupsFrame_chcFirst_itemAdapter implements ItemListener {

    private GroupsFrame adaptee;

    GroupsFrame_chcFirst_itemAdapter(GroupsFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void itemStateChanged(ItemEvent e) {
        adaptee.chcFirst_itemStateChanged(e);
    }
}