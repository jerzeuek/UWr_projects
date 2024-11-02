using System.Collections;
using System.Collections.Generic;
using Unity.VisualScripting;
using UnityEditor;
using UnityEngine;
using UnityEngine.SceneManagement;

public class Buttons : MonoBehaviour
{
    public GameManager gameManager;
    void Start()
    {
        gameManager.GetComponent<GameManager>();
    }
    public void EndTurn()
    {
        if (gameManager.selectedY < gameManager.selectedBlock.transform.position.y)
        {
            gameManager.EndTurn();
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
