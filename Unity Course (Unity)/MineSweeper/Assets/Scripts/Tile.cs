using System.Collections.Generic;
using UnityEngine;

public class Tile : MonoBehaviour
{
    //pola na materiały reprezentujące różne stanów pola
    [Header("Materials")]
    [SerializeField] private Material unclicked;
    [SerializeField] private Material flag;
    [SerializeField] private List<Material> clicked;
    [SerializeField] private Material mine;
    [SerializeField] private Material wrongMine;
    [SerializeField] private Material hitMine;

    public GameManager gameManager;
    private Renderer rend;
    public bool flagged = false;
    public bool clickable = true;
    public bool isMine = false;
    public int mineCount = 0;
    public int numOfClicks = 0;
    
    void Start()
    {
        rend = GetComponent<Renderer>();
    }

    //odbieranie inputów z myszki
    private void OnMouseOver()
    {
        if (clickable)
        {
            if (Input.GetMouseButtonDown(0))
            {
                ClickedTile();
            }

            else if (Input.GetMouseButtonDown(1))
            {
                if (!flagged && gameManager.numFlags>0)
                {
                        flagged = !flagged;
                        gameManager.numFlags--;
                        rend.material = flag;
                }
                else if(flagged)
                {
                    flagged = !flagged;
                    gameManager.numFlags++;
                    rend.material = unclicked;
                }
            }
        }
    }

    //sprawdza czy mina czy puste pole
    //jeśli mina to koniec gry,
    //w.p.p odkrywa i sprawdza czy jest wygrana
    //jeśli puste pole to odkrywa sąsiadów bez min
    public void ClickedTile()
    {
        if (clickable & !flagged)
        {
            clickable = false;
            if (isMine)
            {
                rend.material = hitMine;
                gameManager.GameOver();
            }
            else
            {
                rend.material = clicked[mineCount];
                if (mineCount == 0)
                {
                    gameManager.ClickNeighbours(this);
                }
                gameManager.CheckGameOver();
            }
        }
    }

    //odrywa wszystkie nieodkryte pola, pokazuje pomyłki
    public void ShowGameOverState()
    {
        if (clickable)
        {
            clickable = false;
            if (isMine & !flagged)
            {
                rend.material = mine;
            }
            else if (flagged & !isMine)
            {
                rend.material = wrongMine;
            }
        }
    }

    //ustawia na pole miny flagę przy kończeniu gry
    public void SetFlaggedIfMine()
    {
        if (isMine)
        {
            flagged = true;
            rend.material = flag;
        }
    }
}