using System.Collections;
using System.Collections.Generic;
using Unity.VisualScripting;
using UnityEditor;
using UnityEngine;
using UnityEngine.SceneManagement;

public class Buttons : MonoBehaviour
{
    public GameManager gameManager;
    public Countdown countdown;

    void Start()
    {
        countdown.GetComponent<Countdown>();
        gameManager.GetComponent<GameManager>();
    }
    public void EndTurn()
    {
        if (gameManager.selectedY < gameManager.selectedBlock.transform.position.y)
        {
            gameManager.turnChange.interactable = false;
            gameManager.selected.selected = false;
            gameManager.selected.Deselect();
            gameManager.canSelect = false;
            countdown.timerRunning = true;
        }
    }

    public void NewGame()
    {
        SceneManager.LoadScene(SceneManager.GetActiveScene().name);
    }

    public void QuitGame()
    {
        Application.Quit();
    }
}
