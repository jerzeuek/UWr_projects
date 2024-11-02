using System.Collections;
using System.Collections.Generic;
using Unity.VisualScripting;
using UnityEngine;

public class Block : MonoBehaviour
{
    [SerializeField] private Material wood;
    [SerializeField] private Material highlightedBlock;
    [SerializeField] private Material selectedBlock;
    public GameManager gameManager;
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
        rend = GetComponent<Renderer>();
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
        rend.material = wood;
        }
    }

    private void BlockSelected(){
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
        rend.material = wood;
    }

}
