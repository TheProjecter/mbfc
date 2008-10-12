package mbfc.gui;

import java.awt.*;
import javax.swing.*;
import mbfc.SharedData;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.RandomAccessFile;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
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

public class ProjectFilesFrame extends JFrame {
    FileDialog dialog;
    boolean hasChanged=false;

    BorderLayout xYLayout1 = new BorderLayout();  //  @jve:decl-index=0:
    JButton btnApply = new JButton();
    JButton btnCancel = new JButton();
    JLabel lblProject = new JLabel();
    JTextField txtProject = new JTextField();
    JLabel lblChars = new JLabel();
    JLabel lblGroups = new JLabel();
    JLabel lblLinker = new JLabel();
    JLabel lblCanvas = new JLabel();
    JTextField txtChars = new JTextField();
    JTextField txtGroups = new JTextField();
    JTextField txtLinker = new JTextField();
    JTextField txtCanvas = new JTextField();
    JButton btnPrjChange = new JButton();
    JButton btnMbfChange = new JButton();
    JButton btnGrpChange = new JButton();
    JButton btnLnkChange = new JButton();
    JButton btnCnvChange = new JButton();
	private JPanel jContentPane = null;

    public ProjectFilesFrame() {
        this.setSize(475,260);
        dialog=new FileDialog(this,"Chose new File",FileDialog.LOAD);
        try {
            jbInit();            
            //my lines for initialize texts in text fields
            txtProject.setText(SharedData.projectFileName);
            txtChars.setText(SharedData.charFileName);
            txtGroups.setText(SharedData.groupsFileName);
            txtLinker.setText(SharedData.linkerFileName);
            txtCanvas.setText(SharedData.canvasFileName);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setResizable(false);
        this.setSize(new Dimension(511, 334));
        this.setContentPane(getJContentPane());
        this.setTitle("Project Files Handler");
        btnApply.setToolTipText("");
        btnApply.setText("Apply");
        btnApply.addActionListener(new ProjectFilesFrame_btnApply_actionAdapter(this));
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new
                                    ProjectFilesFrame_btnCancel_actionAdapter(this));
        lblProject.setText("Main Project File:");
        txtProject.setEnabled(false);
        txtProject.setText("\\project.prj");
        lblChars.setToolTipText("");
        lblChars.setText("Char Maps File:");
        lblGroups.setText("Char Groups File:");
        lblLinker.setText("Linker (to Unicode) File:");
        lblCanvas.setText("Entry Canvas File:");
        txtChars.setText("\\characters.mbf");
        txtGroups.setText("\\groups.grp");
        txtLinker.setText("\\linkes.lnk");
        txtCanvas.setText("\\entry-canvas.cnv");
        btnPrjChange.setText("Change");
        btnPrjChange.addActionListener(new
                ProjectFilesFrame_btnPrjChange_actionAdapter(this));
        btnMbfChange.setText("Change");
        btnMbfChange.addActionListener(new
                ProjectFilesFrame_btnMbfChange_actionAdapter(this));
        btnGrpChange.setText("Change");
        btnGrpChange.addActionListener(new
                ProjectFilesFrame_btnGrpChange_actionAdapter(this));
        btnLnkChange.setText("Change");
        btnLnkChange.addActionListener(new
                ProjectFilesFrame_btnLnkChange_actionAdapter(this));
        btnCnvChange.setText("Change");
        btnCnvChange.addActionListener(new
                ProjectFilesFrame_btnCnvChange_actionAdapter(this));
    }

    public void btnPrjChange_actionPerformed(ActionEvent e) {
        hasChanged=true;
        dialog.show();
        txtProject.setText(dialog.getDirectory()+dialog.getFile());
    }

    public void btnMbfChange_actionPerformed(ActionEvent e) {
        hasChanged=true;
        dialog.show();
        txtChars.setText(dialog.getDirectory()+dialog.getFile());
    }

    public void btnGrpChange_actionPerformed(ActionEvent e) {
        hasChanged=true;
        dialog.show();
        txtGroups.setText(dialog.getDirectory()+dialog.getFile());
    }

    public void btnLnkChange_actionPerformed(ActionEvent e) {
        hasChanged=true;
        dialog.show();
        txtLinker.setText(dialog.getDirectory()+dialog.getFile());
    }

    public void btnCnvChange_actionPerformed(ActionEvent e) {
        hasChanged=true;
        dialog.show();
        txtCanvas.setText(dialog.getDirectory()+dialog.getFile());
    }

    public void btnCancel_actionPerformed(ActionEvent e) {
        this.dispose();
    }

    public void btnApply_actionPerformed(ActionEvent e) {
        if(hasChanged){
            try{
                //saving
                SharedData.canvasFile.close();
                SharedData.charFile.close();
                SharedData.groupsFile.close();
                SharedData.linkerFile.close();
                //changing names and files in shared data pool
                SharedData.canvasFileName=txtCanvas.getText()+".cnv";
                SharedData.charFileName=txtChars.getText()+".mbf";
                SharedData.groupsFileName=txtGroups.getText()+".grp";
                SharedData.linkerFileName=txtLinker.getText()+".lnk";
                //changing files
                SharedData.canvasFile=new RandomAccessFile(SharedData.canvasFileName,"rw");
                SharedData.charFile=new RandomAccessFile(SharedData.charFileName,"rw");
                SharedData.groupsFile=new RandomAccessFile(SharedData.groupsFileName,"rw");
                SharedData.linkerFile=new RandomAccessFile(SharedData.linkerFileName,"rw");
            }catch(Exception ex){
                MyComment c=new MyComment("error in saving or creating files","OK",500);
                c.show();
            }
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
			GridBagConstraints gridBagConstraints36 = new GridBagConstraints();
			gridBagConstraints36.gridx = 2;
			gridBagConstraints36.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints36.insets = new Insets(20, 5, 0, 5);
			gridBagConstraints36.gridy = 6;
			GridBagConstraints gridBagConstraints35 = new GridBagConstraints();
			gridBagConstraints35.gridx = 1;
			gridBagConstraints35.insets = new Insets(20, 10, 0, 10);
			gridBagConstraints35.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints35.gridy = 6;
			GridBagConstraints gridBagConstraints34 = new GridBagConstraints();
			gridBagConstraints34.gridx = 2;
			gridBagConstraints34.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints34.insets = new Insets(0, 10, 0, 10);
			gridBagConstraints34.gridy = 5;
			GridBagConstraints gridBagConstraints33 = new GridBagConstraints();
			gridBagConstraints33.gridx = 2;
			gridBagConstraints33.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints33.insets = new Insets(0, 10, 0, 10);
			gridBagConstraints33.gridy = 3;
			GridBagConstraints gridBagConstraints32 = new GridBagConstraints();
			gridBagConstraints32.gridx = 2;
			gridBagConstraints32.insets = new Insets(0, 10, 0, 10);
			gridBagConstraints32.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints32.gridy = 2;
			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			gridBagConstraints31.gridx = 2;
			gridBagConstraints31.insets = new Insets(0, 10, 0, 10);
			gridBagConstraints31.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints31.gridy = 1;
			GridBagConstraints gridBagConstraints30 = new GridBagConstraints();
			gridBagConstraints30.gridx = 2;
			gridBagConstraints30.insets = new Insets(0, 10, 0, 10);
			gridBagConstraints30.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints30.gridy = 0;
			GridBagConstraints gridBagConstraints29 = new GridBagConstraints();
			gridBagConstraints29.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints29.gridy = 5;
			gridBagConstraints29.weightx = 1.0;
			gridBagConstraints29.insets = new Insets(0, 10, 0, 10);
			gridBagConstraints29.gridx = 1;
			GridBagConstraints gridBagConstraints28 = new GridBagConstraints();
			gridBagConstraints28.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints28.gridy = 3;
			gridBagConstraints28.weightx = 1.0;
			gridBagConstraints28.insets = new Insets(0, 10, 0, 10);
			gridBagConstraints28.gridx = 1;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.anchor = GridBagConstraints.EAST;
			gridBagConstraints3.gridy = 3;
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints7.gridy = 2;
			gridBagConstraints7.weightx = 1.0;
			gridBagConstraints7.insets = new Insets(0, 10, 0, 10);
			gridBagConstraints7.gridx = 1;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints6.gridy = 1;
			gridBagConstraints6.weightx = 1.0;
			gridBagConstraints6.insets = new Insets(0, 10, 0, 10);
			gridBagConstraints6.gridx = 1;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints5.gridy = 0;
			gridBagConstraints5.weightx = 1.0;
			gridBagConstraints5.insets = new Insets(0, 10, 0, 10);
			gridBagConstraints5.gridx = 1;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.anchor = GridBagConstraints.EAST;
			gridBagConstraints4.gridy = 5;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.anchor = GridBagConstraints.EAST;
			gridBagConstraints2.gridy = 2;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.anchor = GridBagConstraints.EAST;
			gridBagConstraints1.gridy = 1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.anchor = GridBagConstraints.EAST;
			gridBagConstraints.gridy = 0;
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());
			jContentPane.add(lblProject, gridBagConstraints);
			jContentPane.add(lblChars, gridBagConstraints1);
			jContentPane.add(lblGroups, gridBagConstraints2);
			jContentPane.add(lblCanvas, gridBagConstraints4);
			jContentPane.add(txtProject, gridBagConstraints5);
			jContentPane.add(txtChars, gridBagConstraints6);
			jContentPane.add(txtGroups, gridBagConstraints7);
			jContentPane.add(lblLinker, gridBagConstraints3);
			jContentPane.add(txtLinker, gridBagConstraints28);
			jContentPane.add(txtCanvas, gridBagConstraints29);
			jContentPane.add(btnPrjChange, gridBagConstraints30);
			jContentPane.add(btnMbfChange, gridBagConstraints31);
			jContentPane.add(btnGrpChange, gridBagConstraints32);
			jContentPane.add(btnLnkChange, gridBagConstraints33);
			jContentPane.add(btnCnvChange, gridBagConstraints34);
			jContentPane.add(btnApply, gridBagConstraints35);
			jContentPane.add(btnCancel, gridBagConstraints36);
		}
		return jContentPane;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"


class ProjectFilesFrame_btnApply_actionAdapter implements ActionListener {
    private ProjectFilesFrame adaptee;
    ProjectFilesFrame_btnApply_actionAdapter(ProjectFilesFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnApply_actionPerformed(e);
    }
}


class ProjectFilesFrame_btnCancel_actionAdapter implements ActionListener {
    private ProjectFilesFrame adaptee;
    ProjectFilesFrame_btnCancel_actionAdapter(ProjectFilesFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnCancel_actionPerformed(e);
    }
}


class ProjectFilesFrame_btnCnvChange_actionAdapter implements ActionListener {
    private ProjectFilesFrame adaptee;
    ProjectFilesFrame_btnCnvChange_actionAdapter(ProjectFilesFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnCnvChange_actionPerformed(e);
    }
}


class ProjectFilesFrame_btnLnkChange_actionAdapter implements ActionListener {
    private ProjectFilesFrame adaptee;
    ProjectFilesFrame_btnLnkChange_actionAdapter(ProjectFilesFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnLnkChange_actionPerformed(e);
    }
}


class ProjectFilesFrame_btnGrpChange_actionAdapter implements ActionListener {
    private ProjectFilesFrame adaptee;
    ProjectFilesFrame_btnGrpChange_actionAdapter(ProjectFilesFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnGrpChange_actionPerformed(e);
    }
}


class ProjectFilesFrame_btnMbfChange_actionAdapter implements ActionListener {
    private ProjectFilesFrame adaptee;
    ProjectFilesFrame_btnMbfChange_actionAdapter(ProjectFilesFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnMbfChange_actionPerformed(e);
    }
}


class ProjectFilesFrame_btnPrjChange_actionAdapter implements ActionListener {
    private ProjectFilesFrame adaptee;
    ProjectFilesFrame_btnPrjChange_actionAdapter(ProjectFilesFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnPrjChange_actionPerformed(e);
    }
}