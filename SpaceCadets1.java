import java.awt.EventQueue;
import java.io.*;
import java.io.IOException;
import java.net.URL;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 *
 *
 * @author lpp1g14
 *
 * Some code written by shmert on GitHub used under free licence
 * https://gist.github.com/shmert/3859200
 */
public class SpaceCadets1 {


    public static void main(String[] args) throws Exception {

        //Ask for the username
        System.out.println("Enter your username:");

        //Take in the username and set it too EnteredUser
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String EnteredUser;
        EnteredUser = null;

        try {
            EnteredUser = br.readLine();
        } catch (IOException ioe) {
            System.err.println("There was an input error");
        }
        br.close();

        //Add the username to the URL string
        String UnformatedURL = "http://www.ecs.soton.ac.uk/people/" + EnteredUser;

        //Turn the URL string into a URL object
        URL UserURL = new URL(UnformatedURL);

        //Open another Buffered Reader from the URL stream
        BufferedReader in = new BufferedReader(
                new InputStreamReader(UserURL.openStream()));

        //The cssClass is the part of the CSS that is specic to the name
        String cssClass = "uos-sia-title";

        //The imageURLPart is part of the src for the image of the person allowing us to find the image
        String imageURLPart = "http://widgets.ecs.soton.ac.uk/image.php?id";

        //inputLine is the latest line of HTML
        String inputLine;

        //And this is wether the image has been found yet meaning we only load the first image found
        boolean image = false;

        //Now the fun begins
        while ((inputLine = in.readLine()) != null) {
            //We go through every line until the line containing the cssClass is found
            if (inputLine.toLowerCase().contains(cssClass.toLowerCase())) {
                //Then we break the line up into smaller pieces between the > and </ 
                //which luckily happens only once as it is not linked to anything
                String s = inputLine.substring(inputLine.indexOf(">") + 1);
                s = s.substring(0, s.indexOf("</"));

                //If the username doesn't exist the website simply says ECS People in place of the name
                //So then we check for that
                if ("ECS People".equals(s)) {
                    System.out.print("This user does not exist");
                } else {
                    //Having survived all the text the name is finally output
                    System.out.println(s);
                }
            }

            //If the image is found we can then also do some fun stuff
            if (inputLine.toLowerCase().contains(imageURLPart.toLowerCase()) && !image) {
                image = true;

                String src = inputLine.substring(inputLine.indexOf("_") + 1);
                src = src.substring(0, src.indexOf("&"));
                //System.out.println(src);

                String ImageURL = "http://widgets.ecs.soton.ac.uk/image.php?id=person_" + src + "&maxw=250&maxh=300&corners=0&edge=1&checksum=375eaa4f900cffe15474636dd2276f36";


                try {
					//Turn image url string into URL object
                    System.out.println("Get Image from " + ImageURL);
                    URL url = new URL(ImageURL);
					
					//Create a Buffered Image Object
                    final BufferedImage Profile = ImageIO.read(url);
                            
                    //I have used a ASCII Converter created by shmert on github
                    if (Profile == null) {
                        throw new IllegalArgumentException(url + " is not a valid image.");
                    }
                    String ascii = convert(Profile);

                    //And now we return to my own code
                    int character = 0;
					//Cycle through the characters
                    for (int y = 0; y < Profile.getHeight() - 1; y++) {
                        for (int x = 0; x <= Profile.getWidth(); x++) {
                            System.out.print(ascii.charAt(character));
                            character++;
                        }
                    }

                            //This was a boring way of displaying just the image before but now i have ascii
                            /*System.out.println("Load image into frame...");
                             JLabel label = new JLabel(new ImageIcon(image));
                             JFrame f = new JFrame();
                             f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                             f.getContentPane().add(label);
                             f.pack();
                             f.setLocation(200, 200);
                             f.setVisible(true);*/
                } catch (IOException | IllegalArgumentException exp) {
                }
                    
            }
        }
        in.close();
    }

    //The classes below are created by shmert on GitHub as i did not want to create an ascii converter myself
    
    public static String convert(final BufferedImage image) {
        StringBuilder sb = new StringBuilder((image.getWidth() + 1) * image.getHeight());
        for (int y = 0; y < image.getHeight(); y++) {
            if (sb.length() != 0) {
                sb.append("\n");
            }
            for (int x = 0; x < image.getWidth(); x++) {
                Color pixelColor = new Color(image.getRGB(x, y));
                double gValue = (double) pixelColor.getRed() * 0.2989 + (double) pixelColor.getBlue() * 0.5870 + (double) pixelColor.getGreen() * 0.1140;
                final char s = returnStrPos(gValue);
                sb.append(s);
            }
        }
        return sb.toString();
    }

    /**
     * Create a new string and assign to it a string based on the grayscale
     * value. If the grayscale value is very high, the pixel is very bright and
     * assign characters such as . and , that do not appear very dark. If the
     * grayscale value is very lowm the pixel is very dark, assign characters
     * such as # and @ which appear very dark.
     *
     * @param g grayscale
     * @return char
     */
    private static char returnStrPos(double g)//takes the grayscale value as parameter
    {
        final char str;

        if (g >= 230.0) {
            str = ' ';
        } else if (g >= 200.0) {
            str = '.';
        } else if (g >= 180.0) {
            str = '*';
        } else if (g >= 160.0) {
            str = ':';
        } else if (g >= 130.0) {
            str = 'o';
        } else if (g >= 100.0) {
            str = '&';
        } else if (g >= 70.0) {
            str = '8';
        } else if (g >= 50.0) {
            str = '#';
        } else {
            str = '@';
        }
        return str; // return the character

    }

}
