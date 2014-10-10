/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spacecadets2;

import java.applet.*;
import java.awt.*;
import static java.lang.Thread.sleep;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lpp1g14
 */
public class SpaceCadets2 extends Applet {

    private static final int FRAME_PERIOD = 1000;
    private Timer timer;
    String characters = "0123456789";
    int appletHeight;
    int appletWidth;
    int[][] storageArray;
    int doThisPass = 0;
    int cleanThisPass = 10;

    @Override
    public void paint(Graphics g) {
        int lastPassNo = doThisPass;
        if (doThisPass > 10){
            doThisPass = 0;
        }
        cleanThisPass = doThisPass + 1;
        Dimension appletSize = this.getSize();
        appletSize = this.getSize();
        if (appletHeight != appletSize.height || appletWidth != appletSize.width) {
            appletHeight = appletSize.height;
            appletWidth = appletSize.width;
            storageArray = new int[appletWidth][appletHeight];
        }
        g.setColor(Color.green);

        Random randomGenerator = new Random();

        int speed;
        do {
            speed = randomGenerator.nextInt(100);
        } while (speed < 20);
        int x;
        do {
            x = randomGenerator.nextInt(appletWidth);
        } while (x % 10 != 0);
        int randCharacter = randomGenerator.nextInt(54);
        String selectedChar = characters.substring(randCharacter, randCharacter + 1);
        g.drawString(selectedChar, x, 10);
        storageArray[x][10] = doThisPass;

        for (int y = 10; y < appletHeight - 10; y = y + 10) {
            for (int xScroll = 0; xScroll < appletWidth; xScroll = xScroll + 10) {
                if (storageArray[xScroll][y] == lastPassNo) {
                    randCharacter = randomGenerator.nextInt(54);
                    selectedChar = characters.substring(randCharacter, randCharacter + 1);
                    g.drawString(selectedChar, xScroll, y + 10);
                    storageArray[xScroll][y + 10] = doThisPass;
                }
                if (storageArray[xScroll][y] == cleanThisPass) {
                        storageArray[xScroll][y] = 100;
                        g.clearRect(xScroll, y, xScroll + 10, y + 10);
                }

            }
            try {
                sleep(speed);
            } catch (InterruptedException ex) {
                Logger.getLogger(SpaceCadets2.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        g.clearRect(0, 0, appletWidth, 10);
    }

    public void init() {
        //
        // Here we change the default gray color background of an 
        // applet to black background.
        //
        setBackground(Color.black);
        char selectedCharacter = (char) 920;
        characters = characters + String.valueOf(selectedCharacter);
        selectedCharacter = (char) 926;
        characters = characters + String.valueOf(selectedCharacter);

        for (int i = 0; i <= 45; i++) {
            selectedCharacter = (char) (i + 931);
            characters = characters + String.valueOf(selectedCharacter);
            //System.out.println(characters);
        }
    }

    public void start() {
        final Graphics graphics = getGraphics();

        timer = new Timer(true);
        timer.scheduleAtFixedRate(
                new TimerTask() {
                    public void run() {
                        paint(graphics);
                    }
                },
                0, FRAME_PERIOD
        );
    }

    public void stop() {
        timer.cancel();
    }

}
