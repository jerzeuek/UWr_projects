using System;
using UnityEngine;
using UnityEngine.UI;

public class Countdown : MonoBehaviour
{
    public GameManager gameManager;
    public Text text;
    public bool timerRunning = false;
    public bool timeEnded = false;
    float seconds = 0;

    void Start()
    {
        gameManager.GetComponent<GameManager>();
        text.GetComponent<Text>();
        text.text = "";
    }

    void Update()
    {
        if (timerRunning)
        {
            timeEnded = true;
            text.text = Math.Ceiling(seconds).ToString();
            if (seconds > 0)
            {
                seconds -= Time.deltaTime;
            }

            else
            {
                if (timeEnded)
                {
                    timerRunning = false;
                    text.text = "";
                    seconds = 0;
                    gameManager.EndTurn();
                    timeEnded = false;
                }
            }
        }
    }
}
