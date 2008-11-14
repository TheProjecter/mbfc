package mbfc.gui;

import java.awt.*;
import javax.swing.*;
import mbfc.SharedData;
import mbfc.filing.DataConvertor;
import mbfc.render.TextPreviewCanvas;
import mbfc.storage.BitArrayContainer;
import mbfc.storage.CodeConverter;
import mbfc.storage.GroupContainer;
import mbfc.storage.LinkContainer;
import mbfc.storage.micro.MicroBitArrayContainer;
import java.awt.Font;
import java.io.RandomAccessFile;
import java.awt.event.*;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

// <editor-fold defaultstate="collapsed" desc="mbfc license">
import mbfc.Alignment;
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

public class ConverterFrame extends JFrame {

    GroupContainer groups;
    LinkContainer links;
    BitArrayContainer font;
    BorderLayout xYLayout1 = new BorderLayout();  //  @jve:decl-index=0:
    ScrollPane scrlText = new ScrollPane();
    JButton btnFont = new JButton();
    JButton btnPreview = new JButton();
    JButton btnText = new JButton();
    TextField txtWidth = new TextField();
    TextField txtHeight = new TextField();
    JLabel lblHeight = new JLabel();
    JLabel lblWidth = new JLabel();
    JLabel lblSpaceLen = new JLabel();
    TextField txtSpaceLen = new TextField();
    JLabel lblLinesGap = new JLabel();
    TextField txtLinesGap = new TextField();
    Choice chcAlignment = new Choice();
    Label lblAlignment = new Label();
    JButton btnOpen = new JButton();
    JLabel lblNote = new JLabel();
    ScrollPane scrollPane1 = new ScrollPane();
    JTextArea txaUnused = new JTextArea();
    JLabel lblLeftIntend = new JLabel();
    JLabel lblRightIntend = new JLabel();
    JLabel lblUpIntend = new JLabel();
    JLabel lblDownIntend = new JLabel();
    TextField txtRightIntend = new TextField();
    TextField txtLeftIntend = new TextField();
    TextField txtUpIntend = new TextField();
    TextField txtDownIntend = new TextField();
    JTextArea txaText = new JTextArea();
    private JPanel jContentPane = null;

    public ConverterFrame() {
        try {
            jbInit();
            this.setSize(460, 580);
            majidInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void majidInit() throws Exception {
        font = new BitArrayContainer(SharedData.charFileName, 256);
        groups = new GroupContainer(SharedData.groupsFileName);
        links = new LinkContainer(SharedData.linkerFileName);
        chcAlignment.add("Left To Right");
        chcAlignment.add("Right To Left");
        chcAlignment.add("Centered");
        chcAlignment.add("Justified");
        chcAlignment.select(0);
    }

    private void jbInit() throws Exception {
        this.setResizable(false);
        this.setContentPane(getJContentPane());
        this.setSize(new Dimension(522, 552));
        this.setTitle("Text Converter Assistant");
        txaText.setBackground(Color.white);
        txaText.setFont(new java.awt.Font("Arial Unicode MS", Font.PLAIN, 18));
        txaText.setText("Type Your Text Here!");
        btnFont.setFont(new java.awt.Font("Dialog", 1, 10));
        btnFont.setText("Create Bit Font for J2ME and Java");
        btnFont.addActionListener(new ConverterFrame_btnFont_actionAdapter(this));
        btnPreview.setToolTipText("");
        btnPreview.setText("Preview");
        btnPreview.addActionListener(new ConverterFrame_btnPreview_actionAdapter(this));
        btnText.setFont(new java.awt.Font("Dialog", 1, 10));
        btnText.setText("Create Approciate Text For Java 2");
        btnText.addActionListener(new ConverterFrame_btnText_actionAdapter(this));
        txtWidth.setText("400");
        txtHeight.setText("300");
        lblHeight.setText("Preview Height:");
        lblWidth.setText("Preview Width:");
        lblSpaceLen.setText("Space Length In Pixels:");
        txtSpaceLen.setText("8");
        lblLinesGap.setText("Lines Distance In Pixels:");
        txtLinesGap.setText("8");
        lblAlignment.setText("Alignment:");
        lblAlignment.setFont(new Font("Dialog", Font.BOLD, 12));
        btnOpen.setText("Open From File");
        btnOpen.addActionListener(new ConverterFrame_btnOpen_actionAdapter(this));
        lblNote.setText("Unused Unicode Chars:");
        txaUnused.setText("You Haven\'t any Convert !!!");
        txaUnused.setLineWrap(true);
        txaUnused.setWrapStyleWord(true);
        lblLeftIntend.setText("Left Intend:");
        lblRightIntend.setText("Right Intend:");
        lblUpIntend.setText("Up Intend:");
        lblDownIntend.setText("Down Intend:");
        txtRightIntend.setText("0");
        txtLeftIntend.setText("0");
        txtUpIntend.setText("0");
        txtDownIntend.setText("0");
        txaText.setText("Type Your Text Here!");
        txaText.setLineWrap(true);
        scrollPane1.setLocale(java.util.Locale.getDefault());
        scrlText.setLocale(new java.util.Locale("ar", "", ""));
        scrlText.add(txaText, null);
        scrollPane1.add(txaUnused, null);

    }

    public void btnText_actionPerformed(ActionEvent e) {
        FileDialog dialog = new FileDialog(this, "Choose a file name", FileDialog.SAVE);
        dialog.show();
        if ((dialog.getFile() != null)) {
            CodeConverter convertor = new CodeConverter(txaText.getText(),
                    groups, links, font);
            convertor.saveToFile(dialog.getDirectory() + dialog.getFile());
        }
    }

    public void btnFont_actionPerformed(ActionEvent e) {
        MicroBitArrayContainer mfont = new MicroBitArrayContainer(256,
                font.getWidthType(), font.getAlignment(), font.getHeight(),
                font.getDefaultWidth(), font.getRevAfter());
        mfont.setTransparents(font.transparentArray);
        mfont.setChar(font.array);
        FileDialog dialog = new FileDialog(this, "Choose a file name", FileDialog.SAVE);
        dialog.show();
        if ((dialog.getFile() != null)) {
            mfont.saveToFile(dialog.getDirectory() + dialog.getFile());
        }
    }

    public void btnPreview_actionPerformed(ActionEvent e) {
        CodeConverter convertor = new CodeConverter(txaText.getText(), groups, links, font);
        MicroBitArrayContainer mfont = new MicroBitArrayContainer(256,
                font.getWidthType(), font.getAlignment(), font.getHeight(),
                font.getDefaultWidth(), font.getRevAfter());
        mfont.setTransparents(font.transparentArray);
        mfont.setChar(font.array);
        try {
            int spaceLen = Integer.parseInt(txtSpaceLen.getText());
            int linesGap = Integer.parseInt(txtLinesGap.getText());
            int width = Integer.parseInt(txtWidth.getText());
            int height = Integer.parseInt(txtHeight.getText());
            int rIntend = Integer.parseInt(txtRightIntend.getText());
            int lIntend = Integer.parseInt(txtLeftIntend.getText());
            int uIntend = Integer.parseInt(txtUpIntend.getText());
            int dIntend = Integer.parseInt(txtDownIntend.getText());
            Alignment alignment = Alignment.LEFT_TO_RIGHT;
            switch (chcAlignment.getSelectedIndex()) {
                case 0: {
                    alignment = Alignment.LEFT_TO_RIGHT;
                    break;
                }
                case 1: {
                    alignment = Alignment.RIGHT_TO_LEFT;
                    break;
                }
                case 2: {
                    alignment = Alignment.CENTERED;
                    break;
                }
                case 3: {
                    alignment = Alignment.JUSTIFIED;
                    break;
                }
            }
            TextPreviewFrame tpf = new TextPreviewFrame(convertor.convert(txaUnused),
                    mfont, spaceLen, linesGap, Color.blue, Color.red, Color.white,
                    alignment, lIntend, rIntend, uIntend, dIntend, height, width);
            tpf.show();
        } catch (Exception ex) {
            MyComment mc = new MyComment("Invalid Integer Or Extera Return Keys in the end of Text", "OK", 500);
            mc.show();
        }

    }

    public void btnOpen_actionPerformed(ActionEvent e) {
        FileDialog dialog = new FileDialog(this, "Choose a file name", FileDialog.LOAD);
        dialog.show();
        if ((dialog.getFile() != null)) {
            try {
                RandomAccessFile file = new RandomAccessFile(dialog.getDirectory() + dialog.getFile(), "r");
                byte[] textBytes = new byte[(int) file.length()];
                file.read(textBytes);
                txaText.setText(new String(DataConvertor.bytesToChars(textBytes)));
            } catch (Exception exp) {
            }
        }
    }

    void btnReversed_actionPerformed(ActionEvent e) {
        String s = txaUnused.getText();
        String r = "";
        for (int i = 0; i < s.length(); i++) {
            r = r + s.charAt(s.length() - i - 1);
        }
        txaText.setText(txaText.getText() + r);
    }

    /**
     * This method initializes jContentPane	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            GridBagConstraints gridBagConstraints39 = new GridBagConstraints();
            gridBagConstraints39.gridx = 0;
            gridBagConstraints39.fill = GridBagConstraints.BOTH;
            gridBagConstraints39.gridwidth = 4;
            gridBagConstraints39.gridy = 1;
            GridBagConstraints gridBagConstraints37 = new GridBagConstraints();
            gridBagConstraints37.gridx = 0;
            gridBagConstraints37.gridwidth = 4;
            gridBagConstraints37.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints37.gridy = 11;
            GridBagConstraints gridBagConstraints36 = new GridBagConstraints();
            gridBagConstraints36.gridx = 0;
            gridBagConstraints36.gridy = 10;
            GridBagConstraints gridBagConstraints35 = new GridBagConstraints();
            gridBagConstraints35.gridx = 0;
            gridBagConstraints35.gridwidth = 2;
            gridBagConstraints35.gridheight = 3;
            gridBagConstraints35.fill = GridBagConstraints.BOTH;
            gridBagConstraints35.insets = new Insets(10, 10, 10, 10);
            gridBagConstraints35.gridy = 7;
            GridBagConstraints gridBagConstraints34 = new GridBagConstraints();
            gridBagConstraints34.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints34.gridy = 9;
            gridBagConstraints34.weightx = 1.0;
            gridBagConstraints34.insets = new Insets(5, 5, 5, 5);
            gridBagConstraints34.gridx = 3;
            GridBagConstraints gridBagConstraints33 = new GridBagConstraints();
            gridBagConstraints33.gridx = 2;
            gridBagConstraints33.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints33.gridy = 9;
            GridBagConstraints gridBagConstraints32 = new GridBagConstraints();
            gridBagConstraints32.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints32.gridy = 8;
            gridBagConstraints32.weightx = 1.0;
            gridBagConstraints32.insets = new Insets(5, 5, 5, 5);
            gridBagConstraints32.gridx = 3;
            GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
            gridBagConstraints31.gridx = 2;
            gridBagConstraints31.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints31.gridy = 8;
            GridBagConstraints gridBagConstraints30 = new GridBagConstraints();
            gridBagConstraints30.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints30.gridy = 7;
            gridBagConstraints30.weightx = 1.0;
            gridBagConstraints30.insets = new Insets(5, 5, 5, 5);
            gridBagConstraints30.gridx = 3;
            GridBagConstraints gridBagConstraints29 = new GridBagConstraints();
            gridBagConstraints29.gridx = 2;
            gridBagConstraints29.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints29.gridy = 7;
            GridBagConstraints gridBagConstraints28 = new GridBagConstraints();
            gridBagConstraints28.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints28.gridy = 6;
            gridBagConstraints28.weightx = 1.0;
            gridBagConstraints28.insets = new Insets(5, 5, 5, 5);
            gridBagConstraints28.gridx = 3;
            GridBagConstraints gridBagConstraints27 = new GridBagConstraints();
            gridBagConstraints27.gridx = 2;
            gridBagConstraints27.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints27.gridy = 6;
            GridBagConstraints gridBagConstraints26 = new GridBagConstraints();
            gridBagConstraints26.gridx = 1;
            gridBagConstraints26.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints26.insets = new Insets(5, 5, 5, 0);
            gridBagConstraints26.gridy = 6;
            GridBagConstraints gridBagConstraints25 = new GridBagConstraints();
            gridBagConstraints25.gridx = 0;
            gridBagConstraints25.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints25.gridy = 6;
            GridBagConstraints gridBagConstraints24 = new GridBagConstraints();
            gridBagConstraints24.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints24.gridy = 5;
            gridBagConstraints24.weightx = 1.0;
            gridBagConstraints24.insets = new Insets(5, 5, 5, 5);
            gridBagConstraints24.gridx = 3;
            GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
            gridBagConstraints23.gridx = 2;
            gridBagConstraints23.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints23.gridy = 5;
            GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
            gridBagConstraints22.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints22.gridy = 5;
            gridBagConstraints22.weightx = 1.0;
            gridBagConstraints22.insets = new Insets(5, 5, 5, 5);
            gridBagConstraints22.gridx = 1;
            GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
            gridBagConstraints21.gridx = 0;
            gridBagConstraints21.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints21.gridy = 5;
            GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
            gridBagConstraints20.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints20.gridy = 4;
            gridBagConstraints20.weightx = 1.0;
            gridBagConstraints20.insets = new Insets(5, 5, 5, 5);
            gridBagConstraints20.gridx = 3;
            GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
            gridBagConstraints18.gridx = 2;
            gridBagConstraints18.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints18.gridy = 4;
            GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
            gridBagConstraints17.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints17.gridy = 4;
            gridBagConstraints17.weightx = 1.0;
            gridBagConstraints17.insets = new Insets(5, 5, 5, 5);
            gridBagConstraints17.gridx = 1;
            GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
            gridBagConstraints16.gridx = 0;
            gridBagConstraints16.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints16.gridy = 4;
            GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
            gridBagConstraints15.gridx = 2;
            gridBagConstraints15.gridwidth = 2;
            gridBagConstraints15.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints15.insets = new Insets(5, 5, 5, 5);
            gridBagConstraints15.gridy = 3;
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.gridx = 0;
            gridBagConstraints2.gridwidth = 2;
            gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints2.insets = new Insets(5, 5, 5, 5);
            gridBagConstraints2.gridy = 3;
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.fill = GridBagConstraints.BOTH;
            gridBagConstraints1.gridy = 1;
            gridBagConstraints1.weightx = 1.0;
            gridBagConstraints1.weighty = 1.0;
            gridBagConstraints1.gridwidth = 4;
            gridBagConstraints1.gridx = 0;
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridwidth = 4;
            gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints.ipadx = 0;
            gridBagConstraints.insets = new Insets(5, 5, 5, 5);
            gridBagConstraints.gridy = 0;
            jContentPane = new JPanel();
            jContentPane.setLayout(new GridBagLayout());
            jContentPane.add(btnOpen, gridBagConstraints);
            jContentPane.add(txaText, gridBagConstraints1);
            jContentPane.add(btnFont, gridBagConstraints2);
            jContentPane.add(btnText, gridBagConstraints15);
            jContentPane.add(lblSpaceLen, gridBagConstraints16);
            jContentPane.add(txtSpaceLen, gridBagConstraints17);
            jContentPane.add(lblWidth, gridBagConstraints18);
            jContentPane.add(txtWidth, gridBagConstraints20);
            jContentPane.add(lblLinesGap, gridBagConstraints21);
            jContentPane.add(txtLinesGap, gridBagConstraints22);
            jContentPane.add(lblHeight, gridBagConstraints23);
            jContentPane.add(txtHeight, gridBagConstraints24);
            jContentPane.add(lblAlignment, gridBagConstraints25);
            jContentPane.add(chcAlignment, gridBagConstraints26);
            jContentPane.add(lblRightIntend, gridBagConstraints27);
            jContentPane.add(txtRightIntend, gridBagConstraints28);
            jContentPane.add(lblLeftIntend, gridBagConstraints29);
            jContentPane.add(txtLeftIntend, gridBagConstraints30);
            jContentPane.add(lblUpIntend, gridBagConstraints31);
            jContentPane.add(txtUpIntend, gridBagConstraints32);
            jContentPane.add(lblDownIntend, gridBagConstraints33);
            jContentPane.add(txtDownIntend, gridBagConstraints34);
            jContentPane.add(btnPreview, gridBagConstraints35);
            jContentPane.add(lblNote, gridBagConstraints36);
            jContentPane.add(scrollPane1, gridBagConstraints37);
            jContentPane.add(scrlText, gridBagConstraints39);
        }
        return jContentPane;
    }
}  //  @jve:decl-index=0:visual-constraint="8,7"

class ConverterFrame_btnOpen_actionAdapter implements ActionListener {

    private ConverterFrame adaptee;

    ConverterFrame_btnOpen_actionAdapter(ConverterFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnOpen_actionPerformed(e);
    }
}

class ConverterFrame_btnPreview_actionAdapter implements ActionListener {

    private ConverterFrame adaptee;

    ConverterFrame_btnPreview_actionAdapter(ConverterFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.btnPreview_actionPerformed(actionEvent);
    }
}

class ConverterFrame_btnFont_actionAdapter implements ActionListener {

    private ConverterFrame adaptee;

    ConverterFrame_btnFont_actionAdapter(ConverterFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.btnFont_actionPerformed(actionEvent);
    }
}

class ConverterFrame_btnText_actionAdapter implements ActionListener {

    private ConverterFrame adaptee;

    ConverterFrame_btnText_actionAdapter(ConverterFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.btnText_actionPerformed(actionEvent);
    }
}