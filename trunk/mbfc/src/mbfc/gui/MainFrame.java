package mbfc.gui;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import mbfc.SharedData;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FileDialog;
import java.io.*;
import mbfc.filing.*;
import mbfc.filing.FileReader;
import mbfc.filing.FileWriter;
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
public class MainFrame extends JFrame {

    BorderLayout xYLayout1 = new BorderLayout();
    JButton btnNew = new JButton();
    JButton btnOpen = new JButton();
    JButton btnGroups = new JButton();
    JButton btnLinker = new JButton();
    JButton btnChars = new JButton();
    JButton btnEntry = new JButton();
    JButton btnExit = new JButton();
    JButton btnSave = new JButton();
    JButton btnParts = new JButton();
    JLabel lblProjectTitle = new JLabel();
    JLabel lblAuthor = new JLabel();
    JLabel lblVer = new JLabel();
    JButton btnConvertor = new JButton();
    JButton btnAbout = new JButton();
    private JPanel jContentPane = null;
    //TO-DO I can not to save file names in absulate names!!! 
    public MainFrame() {
        try {

            String curDir = new File(new File("t.tmp").getAbsolutePath()).getParentFile().getAbsolutePath();
            System.out.println(curDir);

            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setSize(400, 550);
        // getContentPane().setLayout(xYLayout1);
        this.setResizable(false);
        this.setContentPane(getJContentPane());
        this.setTitle("Mobile Bit Font Creator Package");
        btnNew.setText("New Project");
        btnNew.addActionListener(new MainFrame_btnNew_actionAdapter(this));
        btnOpen.setText("Open Project ...");
        btnOpen.addActionListener(new MainFrame_btnOpen_actionAdapter(this));
        btnLinker.setEnabled(false);
        btnLinker.setText("Link To Unicode...");
        btnLinker.addActionListener(new MainFrame_btnLinker_actionAdapter(this));
        btnGroups.setEnabled(false);
        btnGroups.setText("Char Groups ...");
        btnGroups.addActionListener(new MainFrame_btnGroups_actionAdapter(this));
        btnChars.setEnabled(false);
        btnChars.setToolTipText("");
        btnChars.setText("Bit Map Chars ...");
        btnChars.addActionListener(new MainFrame_btnChars_actionAdapter(this));
        btnExit.setText("Exit");
        btnExit.addActionListener(new MainFrame_btnExit_actionAdapter(this));
        btnSave.setEnabled(false);
        btnSave.setText("Save Project");
        btnSave.addActionListener(new MainFrame_BtnSave_actionAdapter(this));
        btnEntry.setEnabled(false);
        btnEntry.setText("Entry Canvas ...");
        btnEntry.addActionListener(new MainFrame_btnEntry_actionAdapter(this));
        btnParts.setEnabled(false);
        btnParts.setToolTipText("");
        btnParts.setText("Project Files Handler ...");
        btnParts.addActionListener(new MainFrame_btnParts_actionAdapter(this));
        lblProjectTitle.setFont(new java.awt.Font("Dialog", Font.BOLD, 22));
        lblProjectTitle.setForeground(Color.blue);
        lblProjectTitle.setText("Mobile Bit Font Creator Package ");
        lblAuthor.setForeground(Color.red);
        lblAuthor.setText("Majid Asgari\'s");
        lblVer.setText("Version 1.1.3");
        btnConvertor.setEnabled(false);
        btnConvertor.setText("J2ME Converter...");
        btnConvertor.addActionListener(new MainFrame_btnConvertor_actionAdapter(
                this));
        btnAbout.setText("About");
        btnAbout.addActionListener(new MainFrame_btnAbout_actionAdapter(this));

        this.setSize(400, 550);
    }

    public void btnExit_actionPerformed(ActionEvent e) {
        this.dispose();
    }

    public void btnNew_actionPerformed(ActionEvent e) {
        MyComment cf = new MyComment("You can create a folder and save all of" + " your files will be in that folder," + " It's Better!",
                "OK", 500);
        cf.show(true);
        FileDialog d = new FileDialog(this, "Create new Project,chose a name",
                FileDialog.SAVE);
        d.show();
        try {
            if ((d.getFile() != null)) {
                SharedData.projectFileName = d.getDirectory() + d.getFile() + ".prj";
                SharedData.projectFile = new RandomAccessFile(
                        SharedData.projectFileName, "rw");
                CharPropertiesFrame charFrame = new CharPropertiesFrame();
                charFrame.show();
                // changing names and files in shared data pool
                SharedData.charFileName = d.getDirectory() + "characters.mbf";
                SharedData.groupsFileName = d.getDirectory() + "groups.grp";
                SharedData.linkerFileName = d.getDirectory() + "links.lnk";
                SharedData.canvasFileName = d.getDirectory() + "entry-canvas.cnv";
                // changing files
                SharedData.canvasFile = new RandomAccessFile(
                        SharedData.canvasFileName, "rw");
                SharedData.charFile = new RandomAccessFile(
                        SharedData.charFileName, "rw");
                SharedData.groupsFile = new RandomAccessFile(
                        SharedData.groupsFileName, "rw");
                SharedData.linkerFile = new RandomAccessFile(
                        SharedData.linkerFileName, "rw");
                btnParts.setEnabled(true);
                btnChars.setEnabled(true);
                btnSave.setEnabled(true);
                btnGroups.setEnabled(false);
                this.btnLinker.setEnabled(false);
                this.btnConvertor.setEnabled(false);
                this.btnEntry.setEnabled(false);
            }
        } catch (FileNotFoundException ex) {
            cf = new MyComment("Error in Saving file", "OK", 500);
            cf.show(true);
            return;
        }
    }

    public void BtnSave_actionPerformed(ActionEvent e) {
        saveProject();
    }

    public void saveProject() {
        btnSave.setEnabled(false);
        try {
            SharedData.projectFile.setLength(0);
            SharedData.projectFile.seek(0);
            byte[] tempData = new byte[4];
            tempData[0] = SharedData.height;
            tempData[1] = SharedData.width;
            tempData[2] = DataConvertor.booleanToByte(SharedData.isConstantWidth);
            tempData[3] = DataConvertor.booleanToByte(SharedData.isLtoR);
            SharedData.projectFile.write(tempData);

            FileWriter fw = new FileWriter(SharedData.projectFile);
            fw.writeStringAndLen(SharedData.canvasFileName);
            fw.writeStringAndLen(SharedData.charFileName);
            fw.writeStringAndLen(SharedData.groupsFileName);
            fw.writeStringAndLen(SharedData.linkerFileName);
            SharedData.projectFile.setLength(SharedData.projectFile.getFilePointer());
            SharedData.projectFile.close();
            SharedData.projectFile = new RandomAccessFile(
                    SharedData.projectFileName, "rw");
        } catch (Exception ex) {
            MyComment mc = new MyComment("Error in Saving Project", "OK", 500);
            mc.show();
        }
    }

    public void btnParts_actionPerformed(ActionEvent e) {
        ProjectFilesFrame pff = new ProjectFilesFrame();
        pff.show();
    }

    public void btnOpen_actionPerformed(ActionEvent e) {
        FileDialog dialog = new FileDialog(this, "Chose Project File",
                FileDialog.LOAD);
        dialog.show();
        SharedData.projectFileName = dialog.getDirectory() + dialog.getFile();
        try {
            SharedData.projectFile = new RandomAccessFile(
                    SharedData.projectFileName, "rw");
            byte[] tempData = new byte[4];
            SharedData.projectFile.read(tempData);
            SharedData.height = tempData[0];
            SharedData.width = tempData[1];
            SharedData.isConstantWidth = DataConvertor.byteToBoolean(tempData[2]);
            SharedData.isLtoR = DataConvertor.byteToBoolean(tempData[3]);

            FileReader fr = new FileReader(SharedData.projectFile);
            SharedData.canvasFileName = fr.readStringAndLen();
            SharedData.charFileName = fr.readStringAndLen();
            SharedData.groupsFileName = fr.readStringAndLen();
            SharedData.linkerFileName = fr.readStringAndLen();

            // changing files
            try {
                SharedData.canvasFile = new RandomAccessFile(
                        SharedData.canvasFileName, "rw");
                SharedData.charFile = new RandomAccessFile(SharedData.charFileName,
                        "rw");
                SharedData.groupsFile = new RandomAccessFile(
                        SharedData.groupsFileName, "rw");
                SharedData.linkerFile = new RandomAccessFile(
                        SharedData.linkerFileName, "rw");
            } catch (Exception exp) {
                String dir = dialog.getDirectory();

                SharedData.canvasFileName = dir + SharedData.defaultCanvasFileName;
                SharedData.charFileName = dir + SharedData.defaultCharFileName;
                SharedData.groupsFileName = dir + SharedData.defaultGroupsFileName;
                SharedData.linkerFileName = dir + SharedData.defaultLinkerFileName;

                SharedData.canvasFile = new RandomAccessFile(
                        SharedData.canvasFileName, "rw");
                SharedData.charFile = new RandomAccessFile(SharedData.charFileName,
                        "rw");
                SharedData.groupsFile = new RandomAccessFile(
                        SharedData.groupsFileName, "rw");
                SharedData.linkerFile = new RandomAccessFile(
                        SharedData.linkerFileName, "rw");
            }
            btnParts.setEnabled(true);
            btnChars.setEnabled(true);

            if (SharedData.charFile.length() != 0) {
                btnGroups.setEnabled(true);
            }
            if (SharedData.groupsFile.length() != 0) {
                btnLinker.setEnabled(true);
            }
            if (SharedData.linkerFile.length() != 0) {
                btnConvertor.setEnabled(true);
                btnEntry.setEnabled(true);
            }
        } catch (Exception ex) {
            MyComment mc = new MyComment("Error in Opening Project", "OK", 500);
            mc.show();
        }
    }

    public void btnChars_actionPerformed(ActionEvent e) {
        CharsFrame cf = new CharsFrame(this);
        cf.show(true);
    }

    public void btnGroups_actionPerformed(ActionEvent e) {
        GroupsFrame gf = new GroupsFrame(this);
        gf.show();
    }

    public void btnLinker_actionPerformed(ActionEvent e) {
        LinkFrame lf = new LinkFrame(this);
        lf.show();
    }

    public void btnConvertor_actionPerformed(ActionEvent e) {
        ConverterFrame cf = new ConverterFrame();
        cf.show();
    }

    public void btnEntry_actionPerformed(ActionEvent e) {
        CanvasFrame cf = new CanvasFrame();
        cf.show();
    }

    public void btnAbout_actionPerformed(ActionEvent e) {
        AboutFrame af = new AboutFrame();
        af.show();
    }

    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
            gridBagConstraints13.gridx = 0;
            gridBagConstraints13.insets = new Insets(5, 0, 0, 0);
            gridBagConstraints13.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints13.gridy = 13;
            GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
            gridBagConstraints12.gridx = 0;
            gridBagConstraints12.insets = new Insets(5, 0, 0, 0);
            gridBagConstraints12.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints12.gridy = 12;
            GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
            gridBagConstraints11.gridx = 0;
            gridBagConstraints11.insets = new Insets(5, 0, 0, 0);
            gridBagConstraints11.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints11.gridy = 11;
            GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
            gridBagConstraints10.gridx = 0;
            gridBagConstraints10.insets = new Insets(5, 0, 0, 0);
            gridBagConstraints10.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints10.gridy = 10;
            GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
            gridBagConstraints9.gridx = 0;
            gridBagConstraints9.insets = new Insets(5, 0, 0, 0);
            gridBagConstraints9.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints9.gridy = 9;
            GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
            gridBagConstraints8.gridx = 0;
            gridBagConstraints8.insets = new Insets(5, 0, 0, 0);
            gridBagConstraints8.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints8.gridy = 8;
            GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
            gridBagConstraints7.gridx = 0;
            gridBagConstraints7.insets = new Insets(5, 0, 0, 0);
            gridBagConstraints7.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints7.gridy = 7;
            GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
            gridBagConstraints6.gridx = 0;
            gridBagConstraints6.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints6.insets = new Insets(5, 0, 0, 0);
            gridBagConstraints6.gridy = 6;
            GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
            gridBagConstraints5.gridx = 0;
            gridBagConstraints5.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints5.insets = new Insets(5, 0, 0, 0);
            gridBagConstraints5.gridy = 5;
            GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
            gridBagConstraints4.gridx = 0;
            gridBagConstraints4.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints4.insets = new Insets(5, 0, 0, 0);
            gridBagConstraints4.gridy = 4;
            GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
            gridBagConstraints3.gridx = 0;
            gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints3.insets = new Insets(5, 0, 0, 0);
            gridBagConstraints3.gridy = 3;
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.gridx = 0;
            gridBagConstraints2.anchor = GridBagConstraints.EAST;
            gridBagConstraints2.gridy = 2;
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.gridx = 0;
            gridBagConstraints1.anchor = GridBagConstraints.WEST;
            gridBagConstraints1.gridy = 0;
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.insets = new Insets(0, 0, 0, 0);
            gridBagConstraints.gridy = 1;
            gridBagConstraints.ipadx = 0;
            gridBagConstraints.gridx = 0;
            jContentPane = new JPanel();
            jContentPane.setLayout(new GridBagLayout());
            jContentPane.add(lblProjectTitle, gridBagConstraints);
            jContentPane.add(lblAuthor, gridBagConstraints1);
            jContentPane.add(lblVer, gridBagConstraints2);
            jContentPane.add(btnNew, gridBagConstraints3);
            jContentPane.add(btnOpen, gridBagConstraints4);
            jContentPane.add(btnParts, gridBagConstraints5);
            jContentPane.add(btnChars, gridBagConstraints6);
            jContentPane.add(btnGroups, gridBagConstraints7);
            jContentPane.add(btnLinker, gridBagConstraints8);
            jContentPane.add(btnConvertor, gridBagConstraints9);
            jContentPane.add(btnEntry, gridBagConstraints10);
            jContentPane.add(btnSave, gridBagConstraints11);
            jContentPane.add(btnAbout, gridBagConstraints12);
            jContentPane.add(btnExit, gridBagConstraints13);
        }
        return jContentPane;
    }
}

class MainFrame_btnAbout_actionAdapter implements ActionListener {

    private MainFrame adaptee;

    MainFrame_btnAbout_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnAbout_actionPerformed(e);
    }
}

class MainFrame_btnEntry_actionAdapter implements ActionListener {

    private MainFrame adaptee;

    MainFrame_btnEntry_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnEntry_actionPerformed(e);
    }
}

class MainFrame_btnConvertor_actionAdapter implements ActionListener {

    private MainFrame adaptee;

    MainFrame_btnConvertor_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnConvertor_actionPerformed(e);
    }
}

class MainFrame_btnLinker_actionAdapter implements ActionListener {

    private MainFrame adaptee;

    MainFrame_btnLinker_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnLinker_actionPerformed(e);
    }
}

class MainFrame_btnGroups_actionAdapter implements ActionListener {

    private MainFrame adaptee;

    MainFrame_btnGroups_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnGroups_actionPerformed(e);
    }
}

class MainFrame_btnChars_actionAdapter implements ActionListener {

    private MainFrame adaptee;

    MainFrame_btnChars_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnChars_actionPerformed(e);
    }
}

class MainFrame_btnOpen_actionAdapter implements ActionListener {

    private MainFrame adaptee;

    MainFrame_btnOpen_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnOpen_actionPerformed(e);
    }
}

class MainFrame_btnParts_actionAdapter implements ActionListener {

    private MainFrame adaptee;

    MainFrame_btnParts_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnParts_actionPerformed(e);
    }
}

class MainFrame_BtnSave_actionAdapter implements ActionListener {

    private MainFrame adaptee;

    MainFrame_BtnSave_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.BtnSave_actionPerformed(e);
    }
}

class MainFrame_btnNew_actionAdapter implements ActionListener {

    private MainFrame adaptee;

    MainFrame_btnNew_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnNew_actionPerformed(e);
    }
}

class MainFrame_btnExit_actionAdapter implements ActionListener {

    private MainFrame adaptee;

    MainFrame_btnExit_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnExit_actionPerformed(e);
    }
}