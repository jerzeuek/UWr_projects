package GameplayAndLogic;

import WindowInterface.GamePanel;
import java.io.File;
import java.util.Random;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Klasa Dice reprezentuje kostkę do gry, rzucamy ją, dostajemy wyrzucony numerek i odpowiadającą mu ikonę.
 * 
 */
public class Dice {

    private static final int DICE_MAX = 6;
    private static final int DICE_MIN = 1;

    private final String dice_1_ImgPath = "Icons" + File.separator +"Dice" + File.separator +"dice_1.png";
    private final String dice_2_ImgPath = "Icons" + File.separator +"Dice" + File.separator +"dice_2.png";
    private final String dice_3_ImgPath = "Icons" + File.separator +"Dice" + File.separator +"dice_3.png";
    private final String dice_4_ImgPath = "Icons" + File.separator +"Dice" + File.separator +"dice_4.png";
    private final String dice_5_ImgPath = "Icons" + File.separator +"Dice" + File.separator +"dice_5.png";
    private final String dice_6_ImgPath = "Icons" + File.separator +"Dice" + File.separator +"dice_6.png";
    private final String diceRoll_GifPath = "Icons" + File.separator +"Dice" + File.separator + "diceRoll.gif";

    private Icon dice_1_Img;
    private Icon dice_2_Img;
    private Icon dice_3_Img;
    private Icon dice_4_Img;
    private Icon dice_5_Img;
    private Icon dice_6_Img;
    private Icon diceRoll_Gif;

    private int diceNum;
    private Random rand;

    /**
     * Konstruktor Dice tworzy seed do losowania i tworzy ikony kostki.
     */
    public Dice() {
        this.rand = new Random();
        this.dice_1_Img = new ImageIcon(dice_1_ImgPath);
        this.dice_2_Img = new ImageIcon(dice_2_ImgPath);
        this.dice_3_Img = new ImageIcon(dice_3_ImgPath);
        this.dice_4_Img = new ImageIcon(dice_4_ImgPath);
        this.dice_5_Img = new ImageIcon(dice_5_ImgPath);
        this.dice_6_Img = new ImageIcon(dice_6_ImgPath);
        this.diceRoll_Gif = new ImageIcon(diceRoll_GifPath);

    }

    /**
     * Generuje losowy numer od 1 do 6 dla przyszłego użycia w metodach getDiceNum() i getDiceIcon()
     * @param gamePanel Używane do pokazywania rezulatu oraz wypisywania go w logach.
     */
    public void diceRoll(GamePanel gamePanel) {
        
        int randNum= rand.nextInt(DICE_MAX) + DICE_MIN;
        
        this.diceNum = randNum;
    }

    /** 
     * @return numer wylosowany w metodzie diceRoll(). 
     */
    public int getDiceNum() {
        return this.diceNum;
    }

    /** 
     * @return ikona przypisana do numeru wylosowanego w metodzie diceRoll().
     */
    public Icon getDiceIcon() {
        
        Icon diceNumIcon= null;
        
        switch (diceNum) {
            case 1:
                diceNumIcon = dice_1_Img;
                break;

            case 2:
                diceNumIcon = dice_2_Img;
                break;

            case 3:
                diceNumIcon = dice_3_Img;
                break;

            case 4:
                diceNumIcon = dice_4_Img;
                break;

            case 5:
                diceNumIcon = dice_5_Img;
                break;

            case 6:
                 diceNumIcon = dice_6_Img;
                break;
        }
        
        return diceNumIcon;

    }
    /** 
     * @return Animacja "kręcącej się" kostki.
     */
    public Icon getGif(){
        return diceRoll_Gif;
    }

}
