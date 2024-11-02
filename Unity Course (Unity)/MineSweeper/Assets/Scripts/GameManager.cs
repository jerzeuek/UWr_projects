using System.Collections.Generic;
using System.Linq;
using UnityEngine;
using UnityEngine.UI;

public class GameManager : MonoBehaviour
{
    [SerializeField] private Transform tilePrefab;
    [SerializeField] private Transform gameHolder;
    private List<Tile> tiles = new();
    private int width;
    private int height;
    private int numMines;
    public int numFlags;
    public Timer timer;
    public WinOrLose winOrLose;
    public Text mineText;

    void Start()
    {
        timer.GetComponent<Timer>();
        winOrLose.GetComponent<WinOrLose>();
        mineText.GetComponent<Text>();
        CreateGameBoard(30, 16, 50);
        ResetGameState();
    }

    void Update()
    {
        mineText.text = "Pozostało min: " + numFlags.ToString();
    }

    //tworzenie planszy
    private void CreateGameBoard(int width, int height, int numMines)
    {
        this.width = width;
        this.height = height;
        this.numMines = numMines;
        
        numFlags = numMines;

        for (int row = 0; row < height; row++)
        {
            for (int col = 0; col < width; col++)
            {
                Transform tileTransform = Instantiate(tilePrefab);
                tileTransform.parent = gameHolder;
                float xIndex = col - ((width - 1) / 2.0f);
                float zIndex = row - ((height - 1) / 2.0f);
                tileTransform.localPosition = new Vector3(xIndex, 0, zIndex);
                Tile tile = tileTransform.GetComponent<Tile>();
                tiles.Add(tile);
                tile.gameManager = this;

            }
        }
    }

    //stawianie min w losowych miejscach na planszy
    public void ResetGameState()
    {
        int[] minePositions = Enumerable.Range(0, tiles.Count).OrderBy(x => Random.Range(0.0f, 1.0f)).ToArray();
        for (int i = 0; i < numMines; i++)
        {
            int pos = minePositions[i];
            tiles[pos].isMine = true;
        }

        for (int i = 0; i < tiles.Count; i++)
        {
            tiles[i].mineCount = HowManyMines(i);
        }

    }

    //zwraca ilość bomb wokół pola
    private int HowManyMines(int location)
    {
        int count = 0;
        foreach (int pos in GetNeighbours(location))
        {
            if (tiles[pos].isMine)
            {
                count++;
            }
        }
        return count;
    }

    //zwraca listę sąsiadów pola
    private List<int> GetNeighbours(int pos)
    {
        List<int> neighbours = new();
        int row = pos / width;
        int col = pos % width;

        if (row < (height - 1))
        {
            neighbours.Add(pos + width);
            if (col > 0)
            {
                neighbours.Add(pos + width - 1);
            }
            if (col < (width - 1))
            {
                neighbours.Add(pos + width + 1);
            }
        }

        if (col > 0)
        {
            neighbours.Add(pos - 1);
        }

        if (col < (width - 1))
        {
            neighbours.Add(pos + 1);
        }

        if (row > 0)
        {
            neighbours.Add(pos - width);
            if (col > 0)
            {
                neighbours.Add(pos - width - 1);
            }
            if (col < (width - 1))
            {
                neighbours.Add(pos - width + 1);
            }
        }

        return neighbours;
    }

    //klika sąsiadów bez min pustego pola (bez numerka)
    public void ClickNeighbours(Tile tile)
    {
        int location = tiles.IndexOf(tile);
        foreach (int pos in GetNeighbours(location))
        {
            tiles[pos].ClickedTile();
        }
    }

    //przegrana
    public void GameOver()
    {
        timer.keepUpdating = false;
        winOrLose.GameLost();
        foreach (Tile tile in tiles)
        {
            tile.ShowGameOverState();
        }
    }

    //wygrana
    public void CheckGameOver()
    {
        int count = 0;
        foreach (Tile tile in tiles)
        {
            if (tile.clickable)
            {
                count++;
            }
        }

        if (count == numMines)
        {
            timer.keepUpdating = false;
            winOrLose.GameWon();
            foreach (Tile tile in tiles)
            {
                tile.clickable = false;
                tile.SetFlaggedIfMine();
            }
        }
    }
}
