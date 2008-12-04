package mbfctest;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

import mbfcme.storage.BitArrayContainer;
import mbfcme.ui.canvas.BitFontCanvas;

import java.io.InputStream;
import mbfcme.ui.canvas.PageCanvas;

public class MBFCTestMIDlet extends MIDlet {

    static MBFCTestMIDlet instance;
    private BitArrayContainer font;
    private PageCanvas canvas;

    public MBFCTestMIDlet() {
        instance = this;
    }

    public void startApp() {
        try {
            majidInit();
        } catch (Exception ex) {
        }
    }

    private void majidInit() throws Exception {
        /*try {
        System.out.println(this.getClass());
        InputStream fontS = Class.forName("mbfctest.MBFCTestMIDlet").getResourceAsStream("majidsans.mbf");
        InputStream textS = Class.forName("mbfctest.MBFCTestMIDlet").getResourceAsStream("mon.mtx");
        if ((fontS == null) || (textS == null)) {
        throw new Exception();
        }
        Form form = new Form("Salam");
        form.append("" + fontS.toString());
        form.append("" + textS.toString() + " " + textS.available());
        
        font = new BitArrayContainer(fontS);
        form.append("First Step");
        
        //System.gc();
        canvas = new BitFontCanvas(textS, font, 2, 5, 0xffffff, 0x000000, 0xff0000,
        BitFontCanvas.justified, 2, 2, 2, 2);
        
        form.append(canvas.toString());
        Display.getDisplay(this).setCurrent(form);
        Display.getDisplay(this).setCurrent(canvas);
        } catch (Exception ex) {
        Form form = new Form("Salam");
        Display.getDisplay(this).setCurrent(form);
        }*/

        try {
            InputStream fontS = Class.forName("mbfctest.MBFCTestMIDlet").getResourceAsStream("majidsans.mbf");
            InputStream textS = Class.forName("mbfctest.MBFCTestMIDlet").getResourceAsStream("lastpage.mbp");
            if ((fontS == null) || (textS == null)) {
                throw new Exception();
            }

            Form form = new Form("Salam");

            font = new BitArrayContainer(fontS);
            form.append("First Step");

            //System.gc();
            canvas = new PageCanvas(textS, font, 10, 10, 0xffffff, 0x000000, 0xff0000,
                    BitFontCanvas.justified);
            Display.getDisplay(this).setCurrent(canvas);
        } catch (Exception ex) {
            Form form = new Form("Salam");
            Display.getDisplay(this).setCurrent(form);
        }
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public static void quitApp() {
        instance.destroyApp(true);
        instance.notifyDestroyed();
        instance = null;
    }
}