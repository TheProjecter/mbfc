package mbfc.gui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;
import mbfc.SharedData;
import mbfc.render.CharacterEditCanvas;
import mbfc.render.GuideLine;
import mbfc.render.PreviewCanvas;
import mbfc.storage.BitArray;
import mbfc.storage.BitArrayContainer;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.ComponentOrientation;

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
public class CharsFrame extends JFrame {

    BitArrayContainer font;
    CharacterEditCanvas canvas;
    PreviewCanvas preview;
    MainFrame mf;
    BitArray copied;
    public Color selectedColor = Color.CYAN;  //  @jve:decl-index=0:

    //BorderLayout xYLayout1 = new BorderLayout();
    JButton btnNext = new JButton();
    JButton btnPrevious = new JButton();
    Choice chcCode = new Choice();
    JLabel lblSpace = new JLabel();
    JLabel lblEnter = new JLabel();
    JLabel lblCodeNavigator = new JLabel();
    JButton btnDone = new JButton();
    JScrollPane scpPreview = new JScrollPane();
    //Panel pnlMain = new Panel();
    //BorderLayout xYLayout2 = new BorderLayout();
    JCheckBox chkTransparent = new JCheckBox();
    TextField txtWidth = new TextField();
    JLabel lblWidth = new JLabel();
    Checkbox chkRedLines = new Checkbox();
    Checkbox chkBlueLines = new Checkbox();
    Choice chcHorizontal = new Choice();
    Choice chcVertical = new Choice();
    JLabel lblThickness = new JLabel();
    JRadioButton rdiComplement = new JRadioButton();
    ButtonGroup grpPen = new ButtonGroup();
    JRadioButton rdiSet = new JRadioButton();
    JRadioButton rdiClear = new JRadioButton();
    JButton btnCopy = new JButton();
    JButton btnPaste = new JButton();
    JButton btnColorChoser = new JButton();
    Choice chcGuide = new Choice();
    JButton btnInc = new JButton();
    JLabel lblQuickGo = new JLabel();
    JTextField txtGo = new JTextField();
    JButton btnGo = new JButton();
    JLabel lblReverse = new JLabel();
    Choice chcRev = new Choice();
    JLabel lblDes = new JLabel();
    JLabel lblDes2 = new JLabel();
    TitledBorder titledBorder1;
    private JPanel jContentPane = null;
    private JPanel pnlNavigation = null;
    private JPanel pnlAppearence = null;
    private JPanel pnlChar = null;
    private JPanel pnlCharPref = null;
    private JPanel pnlDone = null;

    public CharsFrame(MainFrame mf) {
        try {
            this.mf = mf;
            //this.setSize(704, 670);
            jbInit();
            //this.setContentPane(getJContentPane());
            majidInit();
            this.setContentPane(getJContentPane());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void majidInit() throws Exception {
        if (!SharedData.isConstantWidth) {
            txtWidth.setEnabled(true);
            lblWidth.setEnabled(true);
        } else {
            txtWidth.setEnabled(false);
            lblWidth.setEnabled(false);
        }
        chcRev.add("Undefined");
        for (int i = 2; i < 256; i++) {
            chcCode.add("" + i);
            chcRev.add("" + i);
        }
        if (SharedData.charFile.length() != 0) {
            font = new BitArrayContainer(SharedData.charFileName, 256);
        } else {
            font = new BitArrayContainer(256);
        }
        canvas = new CharacterEditCanvas(font.getCharacter(2), 500, 500, 2, 1,
                true, true, CharacterEditCanvas.complement);
        preview = new PreviewCanvas(font.getCharacter(2));
        showChar(font.getLastEdited());
        canvas.addMouseListener(new CanvasListener(canvas, preview, chcGuide,
                this));
        //pnlMain.add(canvas);
        scpPreview.setSize(200, 200);
        scpPreview.add(preview);
        preview.setSize(70, 70);
        chcVertical.removeAll();
        chcHorizontal.removeAll();
        for (int i = 1; i <= font.getHeight(); i++) {
            chcHorizontal.add(i + "x");
        }
        for (int i = 1; i <= font.getDefaultWidth(); i++) {
            chcVertical.add(i + "x");
        }
        chcHorizontal.select(1);
        chcVertical.select(0);
        txtWidth.setText(font.getDefaultWidth() + "");
        if (font.getRevAfter() < 2) {
            chcRev.select(0);
        } else {
            chcRev.select(font.getRevAfter() - 1);
        }
    }

    private void showChar(int number) {
        canvas.changeArray(font.getCharacter(number));
        preview.changeArray(font.getCharacter(number));
        chcCode.select(number - 2);
        chkTransparent.setSelected(font.isTransparent(number));
        if (font.getWidthType() == font.variedWidth) {
            chcVertical.removeAll();
            for (int i = 1; i <= font.getCharacter(number).getColsLength(); i++) {
                chcVertical.add(i + "x");
            // chcVertical.select(0);
            }
        }
        canvas.setSize(500, 500);
        font.setLastEdited(number);
    }

    private void jbInit() throws Exception {
        chcGuide.setSize(new Dimension(8, 8));
        titledBorder1 = new TitledBorder("");
        this.setResizable(true);
        this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        this.setName("CharsFrame");
        this.setBounds(new Rectangle(0, 0, 800, 800));
        this.setTitle("Defining Bit Map Chars...");
        this.setVisible(true);
        this.addWindowListener(new CharsFrame_this_windowAdapter(this));
        btnNext.setText(">");
        btnNext.addActionListener(new CharsFrame_btnNext_actionAdapter(this));
        lblEnter.setForeground(new Color(0, 0, 132));
        lblEnter.setText("1 is Enter");
        lblSpace.setForeground(new Color(0, 132, 0));
        lblSpace.setText("0 is Space");

        btnPrevious.setText("<");
        btnPrevious.addActionListener(new CharsFrame_btnPrevious_actionAdapter(
                this));
        btnDone.setForeground(new Color(255, 116, 0));
        btnDone.setText("Done!");
        btnDone.addActionListener(new CharsFrame_btnDone_actionAdapter(this));
        //pnlMain.setLayout(xYLayout2);
        chkTransparent.setForeground(Color.magenta);
        chkTransparent.setBorder(null);
        chkTransparent.setDebugGraphicsOptions(0);
        chkTransparent.setText("Is a Transparent Character");
        chkTransparent.addActionListener(new CharsFrame_chkTransparent_actionAdapter(
                this));
        chkTransparent.addMouseListener(new CharsFrame_chkTransparent_mouseAdapter(
                this));
        lblCodeNavigator.setForeground(Color.blue);
        lblCodeNavigator.setText("Code Navigator");
        chcCode.addMouseListener(new CharsFrame_chcCode_mouseAdapter(this));
        chcCode.addItemListener(new CharsFrame_chcCode_itemAdapter(this));
        txtWidth.setEnabled(false);
        txtWidth.setText("8");
        txtWidth.addTextListener(new CharsFrame_txtWidth_textAdapter(this));
        lblWidth.setEnabled(false);
        lblWidth.setToolTipText("");
        lblWidth.setText("Width:");
        chkRedLines.setForeground(Color.red);
        chkRedLines.setLabel("Red Lines");
        chkRedLines.setState(true);
        chkRedLines.addItemListener(new CharsFrame_chkRedLines_itemAdapter(this));
        chkBlueLines.setForeground(Color.blue);
        chkBlueLines.setLabel("Blue Lines");
        chkBlueLines.setState(true);
        chkBlueLines.addItemListener(new CharsFrame_chkBlueLines_itemAdapter(
                this));
        lblThickness.setForeground(new Color(255, 140, 0));
        lblThickness.setText("Pen Thickness:");
        chcVertical.addItemListener(new CharsFrame_chcVertical_itemAdapter(this));
        chcHorizontal.addItemListener(new CharsFrame_chcHorizontal_itemAdapter(
                this));
        rdiComplement.setSelected(true);
        rdiComplement.setText("Complement");
        rdiComplement.addItemListener(new CharsFrame_rdiComplement_itemAdapter(
                this));
        rdiSet.setText("Set");
        rdiSet.addItemListener(new CharsFrame_rdiSet_itemAdapter(this));
        rdiClear.setText("Clear");
        rdiClear.addItemListener(new CharsFrame_rdiClear_itemAdapter(this));
        btnCopy.setText("Copy Character Map");
        btnCopy.addActionListener(new CharsFrame_btnCopy_actionAdapter(this));
        btnPaste.setEnabled(false);
        btnPaste.setText("Paste");
        btnPaste.addActionListener(new CharsFrame_btnPaste_actionAdapter(this));
        btnColorChoser.setForeground(Color.red);
        btnColorChoser.setText("Guide Line Color");
        btnColorChoser.addActionListener(new CharsFrame_btnColorChoser_actionAdapter(
                this));
        chcGuide.addItemListener(new CharsFrame_chcGuide_itemAdapter(this));
        btnInc.setText("Increase Height by 1 (And Save Project)");
        btnInc.addActionListener(new CharsFrame_btnInc_actionAdapter(this));
        lblQuickGo.setForeground(new Color(255, 83, 91));
        lblQuickGo.setText("Go to Char #");
        txtGo.setText("2");
        btnGo.setForeground(new Color(0, 132, 255));
        btnGo.setText("Go");
        btnGo.addActionListener(new CharsFrame_btnGo_actionAdapter(this));
        lblReverse.setForeground(new Color(0, 149, 157));
        lblReverse.setText("All Characters have Reverse Direction AFTER char#");
        lblDes.setFont(new java.awt.Font("Dialog", 1, 12));
        lblDes.setForeground(Color.blue);
        lblDes.setText("# Squirrel Soft � #");
        lblDes2.setFont(new java.awt.Font("Dialog", 1, 10));
        lblDes2.setForeground(Color.red);
        lblDes2.setText("Character Map Tool");
        chcRev.addItemListener(new CharsFrame_chcRev_itemAdapter(this));
        grpPen.add(rdiComplement);
        grpPen.add(rdiClear);
        grpPen.add(rdiSet);

        chcGuide.add("None");
        chcGuide.add("Horizontal");
        chcGuide.add("Vertical");
        chcGuide.select(0);

    }

    public void btnNext_actionPerformed(ActionEvent e) {
        int number = Integer.parseInt(chcCode.getSelectedItem());
        if (number < 255) {
            showChar(number + 1);
        }
    }

    public void btnPrevious_actionPerformed(ActionEvent e) {
        int number = Integer.parseInt(chcCode.getSelectedItem());
        if (number > 2) {
            showChar(number - 1);
        }
    }

    public void chkTransparent_mouseClicked(MouseEvent e) {
        int selected = Integer.parseInt(chcCode.getSelectedItem());
        if (chkTransparent.isSelected()) {
            font.setTransparent(selected, true);
        } else {
            font.setTransparent(selected, false);
        }
    }

    public void btnDone_actionPerformed(ActionEvent e) {
        font.saveToFile(SharedData.charFileName);
        this.dispose();
        mf.btnGroups.setEnabled(true);
    }

    public void chcCode_mouseClicked(MouseEvent e) {
        // ezafie
    }

    public void chcCode_itemStateChanged(ItemEvent e) {
        int number = Integer.parseInt(chcCode.getSelectedItem());
        showChar(number);
    }

    public void txtWidth_textValueChanged(TextEvent e) {
        try {
            int selected = Integer.parseInt(chcCode.getSelectedItem());
            byte newWidth = (byte) Integer.parseInt(txtWidth.getText());
            font.setCharacter(selected, new BitArray(SharedData.height,
                    newWidth));
            showChar(selected);
        } catch (Exception ex) {
            MyComment mc = new MyComment("You must enter an integer", "OK", 500);
            mc.show();
        }
    }

    public void chkRedLines_itemStateChanged(ItemEvent e) {
        changeZoomTools();
    }

    private void changeZoomTools() {
        boolean showRL = chkRedLines.getState();
        boolean showBL = chkBlueLines.getState();
        int hor = chcHorizontal.getSelectedIndex() + 1;
        int ver = chcVertical.getSelectedIndex() + 1;
        byte pen = CharacterEditCanvas.clear;
        if (rdiComplement.isSelected()) {
            pen = CharacterEditCanvas.complement;
        } else if (rdiSet.isSelected()) {
            pen = CharacterEditCanvas.set;
        }
        canvas.changeVisualProperties(hor, ver, showRL, showBL, pen);
    }

    public void chcVertical_itemStateChanged(ItemEvent e) {
        changeZoomTools();
    }

    public void chcHorizontal_itemStateChanged(ItemEvent e) {
        changeZoomTools();
    }

    public void chkBlueLines_itemStateChanged(ItemEvent e) {
        changeZoomTools();
    }

    public void rdiComplement_itemStateChanged(ItemEvent e) {
        changeZoomTools();
    }

    public void rdiSet_itemStateChanged(ItemEvent e) {
        changeZoomTools();
    }

    public void rdiClear_itemStateChanged(ItemEvent e) {
        changeZoomTools();
    }

    void btnCopy_actionPerformed(ActionEvent e) {
        copied = new BitArray(canvas.array.getRowsLength(), canvas.array.getColsLength());
        copied.setBytes(canvas.array.getBytes());
        btnPaste.setEnabled(true);
    }

    void btnPaste_actionPerformed(ActionEvent e) {
        int selected = Integer.parseInt(chcCode.getSelectedItem());
        BitArray temp = new BitArray(copied.getRowsLength(), copied.getColsLength());
        temp.setBytes(copied.getBytes());
        font.setCharacter(selected, temp);
        showChar(selected);
    }

    void btnColorChoser_actionPerformed(ActionEvent e) {
        ColorChooser cc = new ColorChooser(this);
        cc.show();
    }

    void chcGuide_itemStateChanged(ItemEvent e) {
        canvas.refreshTable();
    }

    void this_windowActivated(WindowEvent e) {
        canvas.refreshTable();
        preview.repaint();
    }

    void btnInc_actionPerformed(ActionEvent e) {
        font.increaseHeight();
        SharedData.height++;
        showChar(Integer.parseInt(chcCode.getSelectedItem()));
        mf.saveProject();
    }

    void chkTransparent_actionPerformed(ActionEvent e) {
    }

    void chcRev_itemStateChanged(ItemEvent e) {
        if (chcRev.getSelectedIndex() == 0) {
            font.setRevAfter(0);
        } else {
            font.setRevAfter(chcRev.getSelectedIndex() + 1);
        }
    }

    void btnGo_actionPerformed(ActionEvent e) {
        try {
            int selectedChar = Integer.parseInt(txtGo.getText());
            if ((selectedChar < 2) || (selectedChar > 255)) {
                throw new Exception("Out of Bundries integer");
            }
            showChar(selectedChar);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            GridBagConstraints gridBagConstraints38 = new GridBagConstraints();
            gridBagConstraints38.gridx = 1;
            gridBagConstraints38.gridwidth = 2;
            gridBagConstraints38.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints38.gridy = 4;
            GridBagConstraints gridBagConstraints37 = new GridBagConstraints();
            gridBagConstraints37.gridx = 1;
            gridBagConstraints37.gridwidth = 2;
            gridBagConstraints37.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints37.gridy = 3;
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.gridheight = 1;
            gridBagConstraints2.gridy = 2;
            gridBagConstraints2.gridwidth = 1;
            gridBagConstraints2.fill = GridBagConstraints.BOTH;
            //gridBagConstraints2.ipadx = 500;
            //gridBagConstraints2.ipady = 500;
            gridBagConstraints2.gridx = 2;
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.gridheight =
                    gridBagConstraints1.gridwidth = 1;
            gridBagConstraints1.gridy = 2;
            gridBagConstraints1.fill = GridBagConstraints.VERTICAL;
            gridBagConstraints1.gridx = 1;
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridheight =
                    gridBagConstraints.gridwidth = 1;
            gridBagConstraints.gridy = 1;
            gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints.gridwidth = 2;
            gridBagConstraints.gridx = 1;
            jContentPane = new JPanel();
            jContentPane.setLayout(new GridBagLayout());
            jContentPane.add(getPnlNavigation(), gridBagConstraints);
            jContentPane.add(getPnlAppearence(), gridBagConstraints1);
            jContentPane.add(getPnlChar(), gridBagConstraints2);
            jContentPane.add(getPnlCharPref(), gridBagConstraints37);
            jContentPane.add(getPnlDone(), gridBagConstraints38);
        }
        return jContentPane;
    }

    /**
     * This method initializes pnlNavigation	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getPnlNavigation() {
        if (pnlNavigation == null) {
            GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
            gridBagConstraints12.insets = new Insets(5, 5, 0, 5);
            GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
            gridBagConstraints11.gridx = 4;
            gridBagConstraints11.gridwidth = 3;
            gridBagConstraints11.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints11.insets = new Insets(5, 5, 5, 5);
            gridBagConstraints11.gridy = 1;
            GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
            gridBagConstraints10.gridx = 0;
            gridBagConstraints10.gridwidth = 3;
            gridBagConstraints10.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints10.insets = new Insets(5, 5, 5, 5);
            gridBagConstraints10.gridy = 1;
            GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
            gridBagConstraints9.gridx = 6;
            gridBagConstraints9.insets = new Insets(5, 5, 0, 5);
            gridBagConstraints9.gridy = 0;
            GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
            gridBagConstraints8.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints8.gridy = 0;
            gridBagConstraints8.weightx = 1.0;
            gridBagConstraints8.ipadx = 50;
            gridBagConstraints8.insets = new Insets(5, 0, 0, 0);
            gridBagConstraints8.gridx = 5;
            GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
            gridBagConstraints7.gridx = 4;
            gridBagConstraints7.insets = new Insets(5, 5, 0, 5);
            gridBagConstraints7.gridy = 0;
            GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
            gridBagConstraints6.gridx = 3;
            gridBagConstraints6.insets = new Insets(5, 0, 0, 5);
            gridBagConstraints6.gridy = 0;
            GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
            gridBagConstraints5.gridx = 1;
            gridBagConstraints5.insets = new Insets(5, 5, 0, 0);
            gridBagConstraints5.gridy = 0;
            GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
            gridBagConstraints4.gridx = 2;
            gridBagConstraints4.ipadx = 50;
            gridBagConstraints4.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints4.insets = new Insets(5, 5, 0, 5);
            gridBagConstraints4.gridy = 0;
            pnlNavigation = new JPanel();
            pnlNavigation.setLayout(new GridBagLayout());
            pnlNavigation.setBorder(BorderFactory.createLineBorder(new Color(0, 123, 0), 5));
            pnlNavigation.add(lblCodeNavigator, gridBagConstraints12);
            pnlNavigation.add(btnNext, gridBagConstraints6);
            pnlNavigation.add(btnPrevious, gridBagConstraints5);
            pnlNavigation.add(chcCode, gridBagConstraints4);
            pnlNavigation.add(lblQuickGo, gridBagConstraints7);
            pnlNavigation.add(txtGo, gridBagConstraints8);
            pnlNavigation.add(btnGo, gridBagConstraints9);
            pnlNavigation.add(btnCopy, gridBagConstraints10);
            pnlNavigation.add(btnPaste, gridBagConstraints11);
        }
        return pnlNavigation;
    }

    /**
     * This method initializes pnlAppearence	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getPnlAppearence() {
        if (pnlAppearence == null) {
            GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
            gridBagConstraints18.gridx = 0;
            gridBagConstraints18.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints18.insets = new Insets(0, 5, 0, 5);
            gridBagConstraints18.gridy = 9;
            GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
            gridBagConstraints17.gridx = 0;
            gridBagConstraints17.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints17.insets = new Insets(0, 5, 0, 5);
            gridBagConstraints17.gridy = 8;
            GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
            gridBagConstraints16.gridx = 0;
            gridBagConstraints16.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints16.insets = new Insets(5, 5, 0, 5);
            gridBagConstraints16.gridy = 7;
            GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
            gridBagConstraints5.gridx = 0;
            gridBagConstraints5.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints5.insets = new Insets(2, 5, 0, 5);
            gridBagConstraints5.gridy = 6;
            GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
            gridBagConstraints4.gridx = 0;
            gridBagConstraints4.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints4.insets = new Insets(0, 5, 0, 5);
            gridBagConstraints4.gridy = 5;
            GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
            gridBagConstraints15.gridx = 0;
            gridBagConstraints15.insets = new Insets(5, 0, 0, 0);
            gridBagConstraints15.gridy = 4;
            GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
            gridBagConstraints14.gridx = 0;
            gridBagConstraints14.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints14.insets = new Insets(0, 5, 0, 5);
            gridBagConstraints14.gridy = 3;
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.gridx = 0;
            gridBagConstraints1.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints1.gridy = 3;
            GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
            gridBagConstraints13.gridx = 0;
            gridBagConstraints13.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints13.insets = new Insets(0, 5, 0, 5);
            gridBagConstraints13.gridy = 1;
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.gridx = 0;
            gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints2.insets = new Insets(0, 5, 0, 5);
            gridBagConstraints2.gridy = 0;
            GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
            gridBagConstraints3.gridx = 0;
            gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints3.insets = new Insets(5, 5, 0, 5);
            gridBagConstraints3.gridy = 2;
            pnlAppearence = new JPanel();
            pnlAppearence.setLayout(new GridBagLayout());
            pnlAppearence.setBorder(BorderFactory.createLineBorder(new Color(152, 0, 174), 5));
            pnlAppearence.add(chkRedLines, gridBagConstraints3);
            pnlAppearence.add(chcGuide, gridBagConstraints2);
            pnlAppearence.add(btnColorChoser, gridBagConstraints13);
            pnlAppearence.add(chkBlueLines, gridBagConstraints14);
            pnlAppearence.add(lblThickness, gridBagConstraints15);
            pnlAppearence.add(chcHorizontal, gridBagConstraints4);
            pnlAppearence.add(chcVertical, gridBagConstraints5);
            pnlAppearence.add(rdiComplement, gridBagConstraints16);
            pnlAppearence.add(rdiSet, gridBagConstraints17);
            pnlAppearence.add(rdiClear, gridBagConstraints18);
        }
        return pnlAppearence;
    }

    /**
     * This method initializes pnlChar	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getPnlChar() {
        if (pnlChar == null) {
            pnlChar = new JPanel();
            pnlChar.setLayout(new GridBagLayout());
            pnlChar.setBorder(BorderFactory.createLineBorder(SystemColor.controlDkShadow, 5));
            pnlChar.add(canvas);
        }
        return pnlChar;
    }

    /**
     * This method initializes pnlCharPref	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getPnlCharPref() {
        if (pnlCharPref == null) {
            GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
            gridBagConstraints3.gridx = 3;
            gridBagConstraints3.ipadx = 30;
            gridBagConstraints3.insets = new Insets(0, 5, 0, 0);
            gridBagConstraints3.gridy = 1;
            GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
            gridBagConstraints21.gridx = 1;
            gridBagConstraints21.gridy = 1;
            GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
            gridBagConstraints20.gridx = 2;
            gridBagConstraints20.anchor = GridBagConstraints.EAST;
            gridBagConstraints20.insets = new Insets(0, 10, 0, 0);
            gridBagConstraints20.gridy = 1;
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.gridx = 3;
            gridBagConstraints2.ipadx = 30;
            gridBagConstraints2.insets = new Insets(10, 5, 0, 0);
            gridBagConstraints2.gridy = 0;
            GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
            gridBagConstraints19.gridx = 1;
            gridBagConstraints19.gridwidth = 2;
            gridBagConstraints19.insets = new Insets(10, 0, 0, 0);
            gridBagConstraints19.gridy = 0;
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.gridx = 0;
            gridBagConstraints1.gridy = 0;
            gridBagConstraints1.anchor = GridBagConstraints.CENTER;
            gridBagConstraints1.ipadx = 70;
            gridBagConstraints1.ipady = 70;
            //gridBagConstraints1.insets = new Insets(0, 0, 0, 10);
            gridBagConstraints1.gridheight = 2;
            pnlCharPref = new JPanel();
            pnlCharPref.setLayout(new GridBagLayout());
            pnlCharPref.setBorder(BorderFactory.createLineBorder(Color.blue, 5));
            pnlCharPref.add(scpPreview, gridBagConstraints1);
            pnlCharPref.add(lblReverse, gridBagConstraints19);
            pnlCharPref.add(chcRev, gridBagConstraints2);
            //scpPreview.setSize(25,25);
            pnlCharPref.add(lblWidth, gridBagConstraints20);
            pnlCharPref.add(chkTransparent, gridBagConstraints21);
            pnlCharPref.add(txtWidth, gridBagConstraints3);
        }
        return pnlCharPref;
    }

    /**
     * This method initializes pnlDone	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getPnlDone() {
        if (pnlDone == null) {
            GridBagConstraints gridBagConstraints27 = new GridBagConstraints();
            gridBagConstraints27.gridx = 3;
            gridBagConstraints27.insets = new Insets(0, 5, 0, 5);
            gridBagConstraints27.gridy = 1;
            GridBagConstraints gridBagConstraints26 = new GridBagConstraints();
            gridBagConstraints26.gridx = 2;
            gridBagConstraints26.insets = new Insets(0, 5, 0, 5);
            gridBagConstraints26.gridy = 1;
            GridBagConstraints gridBagConstraints25 = new GridBagConstraints();
            gridBagConstraints25.gridx = 1;
            gridBagConstraints25.insets = new Insets(0, 5, 0, 0);
            gridBagConstraints25.gridy = 2;
            GridBagConstraints gridBagConstraints24 = new GridBagConstraints();
            gridBagConstraints24.gridx = 0;
            gridBagConstraints24.insets = new Insets(0, 0, 0, 5);
            gridBagConstraints24.gridy = 2;
            GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
            gridBagConstraints23.gridx = 0;
            gridBagConstraints23.gridwidth = 2;
            gridBagConstraints23.gridy = 1;
            GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
            gridBagConstraints22.gridx = 0;
            gridBagConstraints22.gridwidth = 2;
            gridBagConstraints22.gridy = 0;
            pnlDone = new JPanel();
            pnlDone.setLayout(new GridBagLayout());
            pnlDone.setBorder(BorderFactory.createLineBorder(Color.red, 5));
            pnlDone.add(lblDes2, gridBagConstraints22);
            pnlDone.add(lblDes, gridBagConstraints23);
            pnlDone.add(lblSpace, gridBagConstraints24);
            pnlDone.add(lblEnter, gridBagConstraints25);
            pnlDone.add(btnInc, gridBagConstraints26);
            pnlDone.add(btnDone, gridBagConstraints27);
        }
        return pnlDone;
    }
} // @jve:decl-index=0:visual-constraint="10,10"

class CharsFrame_rdiClear_itemAdapter implements ItemListener {

    private CharsFrame adaptee;

    CharsFrame_rdiClear_itemAdapter(CharsFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void itemStateChanged(ItemEvent e) {
        adaptee.rdiClear_itemStateChanged(e);
    }
}

class CharsFrame_rdiSet_itemAdapter implements ItemListener {

    private CharsFrame adaptee;

    CharsFrame_rdiSet_itemAdapter(CharsFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void itemStateChanged(ItemEvent e) {
        adaptee.rdiSet_itemStateChanged(e);
    }
}

class CharsFrame_rdiComplement_itemAdapter implements ItemListener {

    private CharsFrame adaptee;

    CharsFrame_rdiComplement_itemAdapter(CharsFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void itemStateChanged(ItemEvent e) {
        adaptee.rdiComplement_itemStateChanged(e);
    }
}

class CharsFrame_chcHorizontal_itemAdapter implements ItemListener {

    private CharsFrame adaptee;

    CharsFrame_chcHorizontal_itemAdapter(CharsFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void itemStateChanged(ItemEvent e) {
        adaptee.chcHorizontal_itemStateChanged(e);
    }
}

class CharsFrame_chcVertical_itemAdapter implements ItemListener {

    private CharsFrame adaptee;

    CharsFrame_chcVertical_itemAdapter(CharsFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void itemStateChanged(ItemEvent e) {
        adaptee.chcVertical_itemStateChanged(e);
    }
}

class CharsFrame_chkRedLines_itemAdapter implements ItemListener {

    private CharsFrame adaptee;

    CharsFrame_chkRedLines_itemAdapter(CharsFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void itemStateChanged(ItemEvent e) {
        adaptee.chkRedLines_itemStateChanged(e);
    }
}

class CharsFrame_chkBlueLines_itemAdapter implements ItemListener {

    private CharsFrame adaptee;

    CharsFrame_chkBlueLines_itemAdapter(CharsFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void itemStateChanged(ItemEvent e) {
        adaptee.chkBlueLines_itemStateChanged(e);
    }
}

class CharsFrame_txtWidth_textAdapter implements TextListener {

    private CharsFrame adaptee;

    CharsFrame_txtWidth_textAdapter(CharsFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void textValueChanged(TextEvent e) {
        adaptee.txtWidth_textValueChanged(e);
    }
}

class CharsFrame_chcCode_mouseAdapter extends MouseAdapter {

    private CharsFrame adaptee;

    CharsFrame_chcCode_mouseAdapter(CharsFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void mouseClicked(MouseEvent e) {
        adaptee.chcCode_mouseClicked(e);
    }
}

class CharsFrame_chcCode_itemAdapter implements ItemListener {

    private CharsFrame adaptee;

    CharsFrame_chcCode_itemAdapter(CharsFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void itemStateChanged(ItemEvent e) {
        adaptee.chcCode_itemStateChanged(e);
    }
}

class CharsFrame_btnDone_actionAdapter implements ActionListener {

    private CharsFrame adaptee;

    CharsFrame_btnDone_actionAdapter(CharsFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnDone_actionPerformed(e);
    }
}

class CharsFrame_chkTransparent_mouseAdapter extends MouseAdapter {

    private CharsFrame adaptee;

    CharsFrame_chkTransparent_mouseAdapter(CharsFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void mouseClicked(MouseEvent e) {
        adaptee.chkTransparent_mouseClicked(e);
    }
}

class CharsFrame_btnPrevious_actionAdapter implements ActionListener {

    private CharsFrame adaptee;

    CharsFrame_btnPrevious_actionAdapter(CharsFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnPrevious_actionPerformed(e);
    }
}

class CharsFrame_btnNext_actionAdapter implements ActionListener {

    private CharsFrame adaptee;

    CharsFrame_btnNext_actionAdapter(CharsFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnNext_actionPerformed(e);
    }
}

class CanvasListener extends MouseAdapter {

    CharacterEditCanvas canvas;
    PreviewCanvas preview;
    Choice choice;
    CharsFrame cf;

    public CanvasListener(CharacterEditCanvas c, PreviewCanvas preview,
            Choice chcGuide, CharsFrame cf) {
        canvas = c;
        this.preview = preview;
        choice = chcGuide;
        this.cf = cf;
    }

    public void mouseClicked(MouseEvent e) {
        if (choice.getSelectedIndex() == 1) {
            canvas.addRemoveGuidLine(cf.selectedColor, GuideLine.HORIZONTAL, e.getY());
            canvas.refreshTable();
        } else if (choice.getSelectedIndex() == 2) {
            canvas.addRemoveGuidLine(cf.selectedColor, GuideLine.VERTICAL, e.getX());
            canvas.refreshTable();
        } else {
            canvas.clickOn(e.getY(), e.getX());
            canvas.refreshTable();
            preview.repaint();
        }
    }
}

class CharsFrame_btnCopy_actionAdapter implements java.awt.event.ActionListener {

    CharsFrame adaptee;

    CharsFrame_btnCopy_actionAdapter(CharsFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnCopy_actionPerformed(e);
    }
}

class CharsFrame_btnPaste_actionAdapter implements
        java.awt.event.ActionListener {

    CharsFrame adaptee;

    CharsFrame_btnPaste_actionAdapter(CharsFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnPaste_actionPerformed(e);
    }
}

class CharsFrame_btnColorChoser_actionAdapter implements
        java.awt.event.ActionListener {

    CharsFrame adaptee;

    CharsFrame_btnColorChoser_actionAdapter(CharsFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnColorChoser_actionPerformed(e);
    }
}

class CharsFrame_chcGuide_itemAdapter implements java.awt.event.ItemListener {

    CharsFrame adaptee;

    CharsFrame_chcGuide_itemAdapter(CharsFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void itemStateChanged(ItemEvent e) {
        adaptee.chcGuide_itemStateChanged(e);
    }
}

class CharsFrame_this_windowAdapter extends java.awt.event.WindowAdapter {

    CharsFrame adaptee;

    CharsFrame_this_windowAdapter(CharsFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void windowActivated(WindowEvent e) {
        adaptee.this_windowActivated(e);
    }
}

class CharsFrame_btnInc_actionAdapter implements java.awt.event.ActionListener {

    CharsFrame adaptee;

    CharsFrame_btnInc_actionAdapter(CharsFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnInc_actionPerformed(e);
    }
}

class CharsFrame_chkTransparent_actionAdapter implements
        java.awt.event.ActionListener {

    CharsFrame adaptee;

    CharsFrame_chkTransparent_actionAdapter(CharsFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.chkTransparent_actionPerformed(e);
    }
}

class CharsFrame_chcRev_itemAdapter implements java.awt.event.ItemListener {

    CharsFrame adaptee;

    CharsFrame_chcRev_itemAdapter(CharsFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void itemStateChanged(ItemEvent e) {
        adaptee.chcRev_itemStateChanged(e);
    }
}

class CharsFrame_btnGo_actionAdapter implements java.awt.event.ActionListener {

    CharsFrame adaptee;

    CharsFrame_btnGo_actionAdapter(CharsFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnGo_actionPerformed(e);
    }
}