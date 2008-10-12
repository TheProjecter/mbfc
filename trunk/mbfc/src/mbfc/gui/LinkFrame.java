package mbfc.gui;

import java.awt.*;
import javax.swing.*;
import mbfc.SharedData;
import mbfc.filing.DataConvertor;
import mbfc.render.PreviewCanvas;
import mbfc.storage.BitArrayContainer;
import mbfc.storage.CharGroup;
import mbfc.storage.GroupContainer;
import mbfc.storage.LinkContainer;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.io.RandomAccessFile;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import javax.swing.JPanel;
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
public class LinkFrame extends JFrame {

    MainFrame mf;
    BitArrayContainer font;
    GroupContainer groups;
    LinkContainer links;
    PreviewCanvas first;
    PreviewCanvas middle;
    PreviewCanvas last;
    PreviewCanvas seprate;
    JLabel lblGet = new JLabel();
    Choice chcType = new Choice();
    TextField txtTest = new TextField();
    JLabel lblThatIs = new JLabel();
    JLabel lblChar = new JLabel();
    JLabel lblCodeIs = new JLabel();
    JLabel lblCode = new JLabel();
    ScrollPane scrollPane1 = new ScrollPane();
    ScrollPane scrlGroups = new ScrollPane();
    JScrollPane scrlFirst = new JScrollPane();
    JScrollPane scrlMiddle = new JScrollPane();
    JScrollPane scrlLast = new JScrollPane();
    JScrollPane scrlSeprate = new JScrollPane();
    JButton btnLink = new JButton();
    JButton btnDone = new JButton();
    JTextField txtChar = new JTextField();
    List lstNames = new List();
    private JPanel jContentPane = null;

    public LinkFrame(MainFrame mf) {        
        this.mf = mf;
        this.setSize(540, 380);
        try {
            jbInit();
            majidInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void majidInit() throws Exception {
        fillChoice(chcType, txtTest.getText());
        SharedData.charFile = new RandomAccessFile(SharedData.charFileName, "rw");
        if (SharedData.charFile.length() == 0) {
            MyComment mc = new MyComment("No Character Map is available!", "OK", 500);
            mc.show();
            mf.btnGroups.setEnabled(false);
            mf.btnLinker.setEnabled(false);
            this.dispose();
            return;
        }
        font = new BitArrayContainer(SharedData.charFileName, 256);
        SharedData.groupsFile = new RandomAccessFile(SharedData.groupsFileName, "rw");
        if (SharedData.groupsFile.length() == 0) {
            MyComment mc = new MyComment("No Group is available!", "OK", 500);
            mc.show();
            mf.btnLinker.setEnabled(false);
            this.dispose();
            return;
        }

        groups = new GroupContainer(SharedData.groupsFileName);
        fillList(lstNames, groups);
        lstNames.select(0);
        first = new PreviewCanvas(font.getCharacter(groups.getGroup(0).getFirst()));
        middle = new PreviewCanvas(font.getCharacter(groups.getGroup(0).getMiddle()));
        last = new PreviewCanvas(font.getCharacter(groups.getGroup(0).getLast()));
        seprate = new PreviewCanvas(font.getCharacter(groups.getGroup(0).getSeprate()));
        scrlFirst.add(first);
        first.setSize(70, 70);
        scrlMiddle.add(middle);
        middle.setSize(70, 70);
        scrlLast.add(last);
        last.setSize(70, 70);
        scrlSeprate.add(seprate);
        seprate.setSize(70, 70);

        SharedData.linkerFile = new RandomAccessFile(SharedData.linkerFileName, "rw");
        if (SharedData.linkerFile.length() != 0) {
            links = new LinkContainer(SharedData.linkerFileName);
        } else {
            links = new LinkContainer();
        }
        if (links.getNumberOfLinks() > 0) {
            int ch = links.getUnicode(groups.getGroup(0).getName());
            if (ch != -1) {
                txtChar.setText((char) ch + "");
            } else {
                txtChar.setText("No Link is Available for this group");
            }
        }
    }

    private void fillChoice(Choice c, String s) {
        c.removeAll();
        c.add("Show Character with this Code:");
        for (int i = 0; i < txtTest.getText().length(); i++) {
            c.add("Show " + (i + 1) + "th Character of");
        }
        c.select(1);
    }

    private void fillList(List list, GroupContainer groups) {
        list.removeAll();
        String[] s = groups.getGroups();
        for (int i = 0; i < s.length; i++) {
            list.add(s[i]);
        }
    }

    private void showChar(PreviewCanvas p, byte number) {
        p.changeArray(font.getCharacter(number));
        p.repaint();
    }

    private void showGroups(String name) {
        CharGroup group = groups.getGroup(name);
        showChar(first, group.getFirst());
        showChar(middle, group.getMiddle());
        showChar(last, group.getLast());
        showChar(seprate, group.getSeprate());
    }

    private void showGroups(int number) {
        CharGroup group = groups.getGroup(number);
        showChar(first, group.getFirst());
        showChar(middle, group.getMiddle());
        showChar(last, group.getLast());
        showChar(seprate, group.getSeprate());
    }

    private void jbInit() throws Exception {
        this.setResizable(false);
        this.setContentPane(getJContentPane());
        this.setSize(new Dimension(621, 423));
        this.setTitle("Unicode Linker");
        lblGet.setText("Unicode Preview: Get Character Number");
        txtTest.setFont(new java.awt.Font("Arial Unicode MS", Font.PLAIN, 12));
        txtTest.setText("����");
        txtTest.addTextListener(new LinkFrame_txtTest_textAdapter(this));
        lblThatIs.setText("That Character Is");
        txtChar.setText("No Link is Available for this group");
        lblCodeIs.setText("Code Is:");
        lblCode.setText("FEB1");
        btnLink.setText("Link Group to Unicode");
        btnLink.addActionListener(new LinkFrame_btnLink_actionAdapter(this));
        btnDone.setText("Done!");
        btnDone.addActionListener(new LinkFrame_btnDone_actionAdapter(this));
        txtChar.setFont(new java.awt.Font("Arial Unicode MS", Font.PLAIN, 11));
        txtChar.setColumns(1);
        chcType.addItemListener(new LinkFrame_chcType_itemAdapter(this));
        lstNames.addMouseListener(new LinkFrame_lstNames_mouseAdapter(this));
        //this.getContentPane().add(lblGet);
        scrlGroups.add(lstNames);
    }

    public void chcType_itemStateChanged(ItemEvent e) {
        switch (chcType.getSelectedIndex()) {
            case 0: {
                try {
                    int i = DataConvertor.hexToInt(txtTest.getText().toUpperCase());
                    if (i == -1) {
                        throw new Exception();
                    }
                    char ch = (char) i;
                    lblChar.setText(ch + "");
                    lblCode.setText(txtTest.getText());
                    txtChar.setText(ch + "");
                } catch (Exception ex) {
                    MyComment mc = new MyComment(
                            "Error in Type Casting, It wasn't A Hexadecimal Unicode Number",
                            "OK", 500);
                    mc.show();
                    return;
                }
            }
            default: {
                char ch = txtTest.getText().charAt(chcType.getSelectedIndex() - 1);
                lblChar.setText(ch + "");
                int i = (int) ch;
                lblCode.setText(DataConvertor.IntToHex(i));
                txtChar.setText(ch + "");
            }
        }
    }

    public void txtTest_textValueChanged(TextEvent e) {
        fillChoice(chcType, txtTest.getText());
    }

    public void lstNames_mouseClicked(MouseEvent e) {
        showGroups(lstNames.getSelectedIndex());
        int ch = links.getUnicode(groups.getGroup(lstNames.getSelectedIndex()).getName());
        if (ch != -1) {
            txtChar.setText((char) ch + "");
        } else {
            txtChar.setText("No Link is Available for this group");
        }
    }

    public void btnLink_actionPerformed(ActionEvent e) {
        links.addOrChangeLink(lstNames.getSelectedItem(),
                txtChar.getText().charAt(0));
    }

    public void btnDone_actionPerformed(ActionEvent e) {
        links.saveLinker(SharedData.linkerFileName);
        mf.btnConvertor.setEnabled(true);
        mf.btnEntry.setEnabled(true);
        this.dispose();
    }

    /**
     * This method initializes jContentPane	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            GridBagConstraints gridBagConstraints27 = new GridBagConstraints();
            gridBagConstraints27.gridx = 1;
            gridBagConstraints27.gridy = 3;
            GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
            gridBagConstraints7.gridx = 2;
            gridBagConstraints7.ipadx = 70;
            gridBagConstraints7.ipady = 70;
            gridBagConstraints7.insets = new Insets(10, 20, 10, 20);
            gridBagConstraints7.gridy = 4;
            GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
            gridBagConstraints6.gridx = 1;
            gridBagConstraints6.ipadx = 70;
            gridBagConstraints6.ipady = 70;
            gridBagConstraints6.insets = new Insets(10, 20, 10, 20);
            gridBagConstraints6.gridy = 4;
            GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
            gridBagConstraints5.gridx = 2;
            gridBagConstraints5.ipadx = 70;
            gridBagConstraints5.ipady = 70;
            gridBagConstraints5.insets = new Insets(10, 20, 10, 20);
            gridBagConstraints5.gridy = 3;
            GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
            gridBagConstraints4.gridx = 1;
            gridBagConstraints4.ipadx = 70;
            gridBagConstraints4.ipady = 70;
            gridBagConstraints4.insets = new Insets(10, 20, 10, 20);
            gridBagConstraints4.gridy = 3;
            GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
            gridBagConstraints3.gridx = 0;
            gridBagConstraints3.gridy = 2;
            gridBagConstraints3.fill = GridBagConstraints.BOTH;
            gridBagConstraints3.insets = new Insets(0, 10, 0, 0);
            gridBagConstraints3.gridheight = 4;
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.gridx = 2;
            gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints2.insets = new Insets(0, 10, 0, 10);
            gridBagConstraints2.ipadx = 70;
            gridBagConstraints2.gridy = 0;
            GridBagConstraints gridBagConstraints26 = new GridBagConstraints();
            gridBagConstraints26.gridx = 0;
            gridBagConstraints26.gridwidth = 3;
            gridBagConstraints26.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints26.insets = new Insets(20, 10, 0, 10);
            gridBagConstraints26.gridy = 6;
            GridBagConstraints gridBagConstraints25 = new GridBagConstraints();
            gridBagConstraints25.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints25.gridy = 5;
            gridBagConstraints25.weightx = 1.0;
            gridBagConstraints25.gridwidth = 2;
            gridBagConstraints25.insets = new Insets(0, 10, 0, 1);
            gridBagConstraints25.gridx = 1;
            GridBagConstraints gridBagConstraints24 = new GridBagConstraints();
            gridBagConstraints24.gridx = 1;
            gridBagConstraints24.gridwidth = 2;
            gridBagConstraints24.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints24.insets = new Insets(0, 10, 0, 10);
            gridBagConstraints24.gridy = 2;
            GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
            gridBagConstraints23.gridx = 2;
            gridBagConstraints23.insets = new Insets(0, 10, 20, 10);
            gridBagConstraints23.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints23.gridy = 1;
            GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
            gridBagConstraints22.gridx = 1;
            gridBagConstraints22.insets = new Insets(0, 10, 20, 10);
            gridBagConstraints22.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints22.gridy = 1;
            GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
            gridBagConstraints21.gridx = 0;
            gridBagConstraints21.insets = new Insets(0, 0, 20, 0);
            gridBagConstraints21.anchor = GridBagConstraints.EAST;
            gridBagConstraints21.gridy = 1;
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.gridy = 0;
            gridBagConstraints1.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints1.insets = new Insets(0, 10, 0, 10);
            gridBagConstraints1.gridx = 1;
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridy = 0;
            gridBagConstraints.anchor = GridBagConstraints.EAST;
            gridBagConstraints.gridx = 0;
            jContentPane = new JPanel();
            jContentPane.setLayout(new GridBagLayout());
            jContentPane.add(lblGet, gridBagConstraints);
            jContentPane.add(chcType, gridBagConstraints1);
            jContentPane.add(lblThatIs, gridBagConstraints21);
            jContentPane.add(lblCodeIs, gridBagConstraints22);
            jContentPane.add(lblCode, gridBagConstraints23);
            jContentPane.add(btnLink, gridBagConstraints24);
            jContentPane.add(txtChar, gridBagConstraints25);
            jContentPane.add(btnDone, gridBagConstraints26);
            jContentPane.add(txtTest, gridBagConstraints2);
            jContentPane.add(scrlGroups, gridBagConstraints3);
            jContentPane.add(scrlFirst, gridBagConstraints4);
            jContentPane.add(scrlMiddle, gridBagConstraints5);
            jContentPane.add(scrlLast, gridBagConstraints6);
            jContentPane.add(scrlSeprate, gridBagConstraints7);
        }
        return jContentPane;
    }
}  //  @jve:decl-index=0:visual-constraint="10,10"

class LinkFrame_lstNames_mouseAdapter extends MouseAdapter {

    private LinkFrame adaptee;

    LinkFrame_lstNames_mouseAdapter(LinkFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void mouseClicked(MouseEvent e) {
        adaptee.lstNames_mouseClicked(e);
    }
}

class LinkFrame_btnLink_actionAdapter implements ActionListener {

    private LinkFrame adaptee;

    LinkFrame_btnLink_actionAdapter(LinkFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnLink_actionPerformed(e);
    }
}

class LinkFrame_btnDone_actionAdapter implements ActionListener {

    private LinkFrame adaptee;

    LinkFrame_btnDone_actionAdapter(LinkFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnDone_actionPerformed(e);
    }
}

class LinkFrame_chcType_itemAdapter implements ItemListener {

    private LinkFrame adaptee;

    LinkFrame_chcType_itemAdapter(LinkFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void itemStateChanged(ItemEvent e) {
        adaptee.chcType_itemStateChanged(e);
    }
}

class LinkFrame_txtTest_textAdapter implements TextListener {

    private LinkFrame adaptee;

    LinkFrame_txtTest_textAdapter(LinkFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void textValueChanged(TextEvent e) {
        adaptee.txtTest_textValueChanged(e);
    }
}