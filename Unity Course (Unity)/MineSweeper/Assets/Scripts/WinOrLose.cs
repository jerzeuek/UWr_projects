using UnityEngine;
using UnityEngine.UI;

public class WinOrLose : MonoBehaviour
{
    public Text status;
    
    void Start()
    {
        status.GetComponent<Text>();
        status.text = "GRA W TOKU...";
    }

    public void GameWon(){
        status.text = "WYGRAŁEŚ!";
    }

    public void GameLost(){
        status.text = "PRZEGRAŁEŚ...";
    }
}
