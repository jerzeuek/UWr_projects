using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class WhichTurn : MonoBehaviour
{
    public Text text;
    // Start is called before the first frame update
    void Start()
    {
        text.GetComponent<Text>();
        text.text = "Tura gracza 1";
    }

    public void ChangeTurn(int player){
        text.text = "Tura gracza " + player;
    }

    public void Won(int player){
        text.text = "Wygrywa gracz " + (player%2 + 1);
    }
}
