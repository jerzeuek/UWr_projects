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

    // Start is called before the first frame update
    void Start()
    {
        whichTurn.GetComponent<WhichTurn>();
        turnChange.interactable = false;
    }

    // Update is called once per frame
    void Update()
    {

    }
    public void GameOver()
    {
        turnChange.interactable = false;
        canSelect = false;
        whichTurn.Won(currentPlayer);

    }

    public void EndTurn()
    {
        turnChange.interactable = false;
        selected.selected = false;
        selected.Deselect();
        canSelect = true;
        selectedBlock = null;
        selected.top = true;
        newTopObjects.Add(selected);
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
