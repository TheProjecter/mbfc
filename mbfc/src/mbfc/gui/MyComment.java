package mbfc.gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class MyComment extends JFrame {

    BorderLayout layout = new BorderLayout();  //  @jve:decl-index=0:
    JLabel lblComment = new JLabel();
    JButton btnOK = new JButton();
    private JPanel jContentPane = null;

    public MyComment(String message, String buttonLabel, int width) {
        try {
            jbInit();
            lblComment.setText(message);
            btnOK.setText(buttonLabel);
            btnOK.setLocation((width - btnOK.getWidth()) / 2, 15);
            this.setSize(width, 100);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setSize(500, 175);
        //this.setContentPane(lblComment);
        this.setTitle("Comment");
        this.setContentPane(getJContentPane());
        lblComment.setText("It\'s a Comment");
        btnOK.setText("OK");
        btnOK.addActionListener(new MyComment_btnOK_actionAdapter(this));
    }

    public void btnOK_actionPerformed(ActionEvent e) {
        this.dispose();
    }

    /**
     * This method initializes jContentPane	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.insets = new Insets(0, 0, 0, 0);
            gridBagConstraints1.gridy = 0;
            gridBagConstraints1.ipadx = 0;
            gridBagConstraints1.fill = GridBagConstraints.NONE;
            gridBagConstraints1.gridheight = 2;
            gridBagConstraints1.weightx = 0.0;
            gridBagConstraints1.gridwidth = 3;
            gridBagConstraints1.anchor = GridBagConstraints.CENTER;
            gridBagConstraints1.gridx = 0;
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.insets = new Insets(20, 0, 0, 0);
            gridBagConstraints.gridy = 2;
            gridBagConstraints.ipadx = 0;
            gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints.gridwidth = 3;
            gridBagConstraints.gridx = 0;
            jContentPane = new JPanel();
            jContentPane.setLayout(new GridBagLayout());
            jContentPane.add(btnOK, gridBagConstraints);
            jContentPane.add(lblComment, gridBagConstraints1);
        }
        return jContentPane;
    }
}  //  @jve:decl-index=0:visual-constraint="10,10"

class MyComment_btnOK_actionAdapter implements ActionListener {

    private MyComment adaptee;

    MyComment_btnOK_actionAdapter(MyComment adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnOK_actionPerformed(e);
    }
}