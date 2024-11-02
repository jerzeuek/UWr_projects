using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class GameManager : MonoBehaviour
{
    public bool canSelect = true;
    public int currentPlayer = 0;
    public GameObject selectedBlock;
    public Button turnChange;
    public Block selected;
    public float selectedY;
    public List<Block> newTopObjects = new List<Block>();
    public List<Block> topObjects = new List<Block>();
    public WhichTurn whichTurn;
    public Countdown countdown;

    // Start is called before the first frame update
    void Start()
    {
        countdown.GetComponent<Countdown>();
        whichTurn.GetComponent<WhichTurn>();
        turnChange.interactable = false;
    }

    // Update is called once per frame
    void Update()
    {

    }
    public void GameOver()
    {
        countdown.timerRunning = false;
        turnChange.interactable = false;
        canSelect = false;
        whichTurn.Won(currentPlayer + 1);

    }

    public void EndTurn()
    {
        canSelect = true;
        selectedBlock = null;
        selected.top = true;
        newTopObjects.Add(selected);
        currentPlayer = (currentPlayer + 1) % 2;
        whichTurn.ChangeTurn(currentPlayer + 1);
        if (newTopObjects.Count == 3)
        {
            foreach (Block block in topObjects)
            {
                block.top = false;
            }

            topObjects = new List<Block>(newTopObjects);
            newTopObjects.Clear();
        }
    }
}
