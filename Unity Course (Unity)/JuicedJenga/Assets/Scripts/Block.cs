using System.Collections;
using System.Collections.Generic;
using Unity.VisualScripting;
using UnityEngine;

public class Block : MonoBehaviour
{
    int texture;
    [SerializeField] private List<Material> wood;
    [SerializeField] private Material highlightedBlock;
    [SerializeField] private Material selectedBlock;
    public GameManager gameManager;
    public WhichTurn whichTurn;
    private Renderer rend;
    public bool bottom;
    public bool top = false;
    public bool selected = false;

    public GameObject table;

    void OnCollisionStay(Collision collision){
        if(!bottom && !selected && collision.gameObject == table){
            gameManager.GameOver();
        }
    }

     void Start()
    {
        texture = Random.Range(0, 3);
        rend = GetComponent<Renderer>();
        whichTurn.GetComponent<WhichTurn>();
        rend.material = wood[texture];
    }

    void Update(){
    }

    private void OnMouseOver(){
        if(!top && !selected && gameManager.canSelect){
            rend.material = highlightedBlock;
            if(Input.GetMouseButtonDown(0)){
                BlockSelected();
            }
        }
    }

    private void OnMouseExit(){
        if(!selected){
        rend.material = wood[texture];
        }
    }

    private void BlockSelected(){
        GetComponent<AudioSource>().Play();
        gameManager.currentPlayer = (gameManager.currentPlayer% 2) + 1;
        whichTurn.ChangeTurn(gameManager.currentPlayer);
        gameManager.turnChange.interactable = true;
        gameManager.selected = this;
        gameManager.selectedBlock = this.gameObject;
        gameManager.selectedY = gameManager.selectedBlock.transform.position.y + 1.5f;
        gameManager.canSelect = !gameManager.canSelect;
        rend.material = selectedBlock;
        if(bottom){
            bottom = !bottom;
        }
        selected = !selected;
    }

    public void Deselect(){
        rend.material = wood[texture];
    }

}
