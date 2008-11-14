package mbfc.gui;

import java.awt.*;
import javax.swing.*;
import mbfc.render.TextPreviewCanvas;
import mbfc.storage.micro.MicroBitArrayContainer;
import java.awt.event.*;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.RandomAccessFile;
import mbfc.Alignment;

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
public class TextPreviewFrame extends JFrame {

    BorderLayout xYLayout1 = new BorderLayout(); // @jve:decl-index=0:
    JScrollPane scrlCanvas = new JScrollPane();
    TextPreviewCanvas canvas;
    private int width = 0;
    private int height = 0;
    JButton btnNext = new JButton();
    JButton btnGeneratePage = new JButton();
    private JPanel jContentPane = null;

    public TextPreviewFrame(byte[] text, MicroBitArrayContainer font,
            int spaceLengthInPixel, int gapBetweenLines, Color color,
            Color transparentColor, Color backgroundColor, Alignment alignment,
            int lIntend, int rIntend, int uIntend, int dIntend, int height,
            int width) {
        try {
            this.width = width;
            this.height = height;
            canvas = new TextPreviewCanvas(text, font, spaceLengthInPixel,
                    gapBetweenLines, color, transparentColor, backgroundColor,
                    alignment, lIntend, rIntend, uIntend, dIntend, height,
                    width);
            this.setSize(width + 10, height + 35 + 100);
            scrlCanvas.setSize(canvas.getWidth() + 5, canvas.getHeight() + 5);
            scrlCanvas.add(canvas);
            jbInit();
            if (!(canvas.wasLastLine)) {

                btnNext.setEnabled(true);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setResizable(false);
        this.setContentPane(getJContentPane());
        //this.setSize(new Dimension(459, 509));
        this.setTitle("Preview Canvas");
        btnNext.setEnabled(false);
        btnNext.setText(">>");
        btnNext.addActionListener(new TextPreviewFrame_btnNext_actionAdapter(
                this));
        btnGeneratePage.setText("Generate Page");
        btnGeneratePage.addActionListener(new TextPreviewFrame_btnGenerate_actionAdapter(
                this));
    }

    void btnNext_actionPerformed(ActionEvent e) {
        canvas.repaint();
    }

    void btnGenerate_actionPerformed(ActionEvent e) {
        byte[] bytes = canvas.getPageBytes();
        try {
            RandomAccessFile file = new RandomAccessFile("C:\\lastpage.mbp", "rw");
            file.write(bytes);
            file.close();
        } catch (Exception exp) {
        }
    }

    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.gridx = 0;
            gridBagConstraints1.gridwidth = 5;
            gridBagConstraints1.ipadx = width;
            gridBagConstraints1.ipady = height;
            gridBagConstraints1.gridy = 1;

            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridwidth = 2;
            gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints.insets = new Insets(20, 0, 20, 0);
            gridBagConstraints.gridy = 0;

            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.gridx = 3;
            gridBagConstraints2.gridwidth = 2;
            gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints2.insets = new Insets(20, 0, 20, 0);
            gridBagConstraints2.gridy = 0;

            jContentPane = new JPanel();
            jContentPane.setLayout(new GridBagLayout());
            jContentPane.add(btnNext, gridBagConstraints);
            jContentPane.add(btnGeneratePage, gridBagConstraints2);
            jContentPane.add(scrlCanvas, gridBagConstraints1);
        }
        return jContentPane;
    }
} // @jve:decl-index=0:visual-constraint="10,10"

class TextPreviewFrame_btnNext_actionAdapter implements
        java.awt.event.ActionListener {

    TextPreviewFrame adaptee;

    TextPreviewFrame_btnNext_actionAdapter(TextPreviewFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnNext_actionPerformed(e);
    }
}

class TextPreviewFrame_btnGenerate_actionAdapter implements
        java.awt.event.ActionListener {

    TextPreviewFrame adaptee;

    TextPreviewFrame_btnGenerate_actionAdapter(TextPreviewFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnGenerate_actionPerformed(e);
    }
}

/*
سلام
خوبی مردک
فک کردی من کیم؟ معلومه جیگر من مجید عسگریم که دارم اینو تست می کنم.
خوش به حالت آقا مجید
خوش به حال خودت
ابله
*/