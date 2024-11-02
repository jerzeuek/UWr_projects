using System.Collections;

using System.Collections.Generic;
using JetBrains.Annotations;
using Unity.VisualScripting;
using UnityEngine;


public class DragObject : MonoBehaviour
{
    public GameManager gameManager;
    public Rigidbody rb;

    public float selectedX, selectedZ;

    private Vector3 mOffset;

    private float mZCoord;
    bool resetVelocity = false;

    void Start()
    {
        gameManager.GetComponent<GameManager>();
        rb.GetComponent<Rigidbody>();
    }

    void Update()
    {
        if (gameObject == gameManager.selectedBlock)
        {
            if (Input.GetMouseButton(0))
            {
                if (Input.GetKey("z"))
                {
                    gameObject.transform.Rotate(new Vector3(0, -90 * Time.deltaTime, 0));
                }

                if (Input.GetKey("x"))
                {
                    gameObject.transform.Rotate(new Vector3(0, 90 * Time.deltaTime, 0));
                }
            }

            if (Input.GetMouseButton(1))
            {
                gameObject.transform.Translate(Vector3.forward * Time.deltaTime);
            }

            if (!Input.GetMouseButton(0))
            {
                if (resetVelocity == true)
                {
                    rb.constraints = RigidbodyConstraints.None;
                    resetVelocity = false;
                    rb.velocity = new Vector3(0, -2, 0);
                    rb.angularVelocity = Vector3.zero;
                }
            }


        }
    }

    void OnMouseDown()
    {
        resetVelocity = true;
        rb.constraints = RigidbodyConstraints.FreezeRotationZ | RigidbodyConstraints.FreezeRotationX | RigidbodyConstraints.FreezeRotationY;
        mZCoord = Camera.main.WorldToScreenPoint(gameObject.transform.position).z;

        mOffset = gameObject.transform.position - GetMouseAsWorldPoint();

    }

    private Vector3 GetMouseAsWorldPoint()
    {

        Vector3 mousePoint = Input.mousePosition;

        mousePoint.z = mZCoord;

        return Camera.main.ScreenToWorldPoint(mousePoint);

    }


    void OnMouseDrag()

    {
        if (gameObject == gameManager.selectedBlock)
        {
            transform.position = GetMouseAsWorldPoint() + mOffset;
        }
    }

}