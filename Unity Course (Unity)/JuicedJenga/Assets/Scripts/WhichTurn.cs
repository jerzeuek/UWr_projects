using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using TMPro;

public class WhichTurn : MonoBehaviour
{
    public TMP_Text text;
    // Start is called before the first frame update
    void Start()
    {
        text.text = "";
    }

    public void ChangeTurn(int player){
        text.text = "Tura gracza " + player;
    }

    public void Won(int player){
        text.text = "Wygrywa gracz " + (player%2 + 1);
    }
}
