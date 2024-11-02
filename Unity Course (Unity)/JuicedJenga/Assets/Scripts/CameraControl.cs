using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CameraControl : MonoBehaviour
{
    public GameObject table;

    public GameObject blocks;

    // Start is called before the first frame update
    void Start()
    {

    }

    void Update()
    {
        float scroll = Input.GetAxis("Mouse ScrollWheel");
        transform.Translate(0, 0, scroll * 10);


        if (Input.GetKey("q"))
        {
            transform.RotateAround(table.transform.position, Vector3.up, 45 * Time.deltaTime);
        }

        if (Input.GetKey("e"))
        {
            transform.RotateAround(table.transform.position, Vector3.down, 45 * Time.deltaTime);
        }

        if (Input.GetKey("w") && !Input.GetKey("left shift") && Camera.main.transform.rotation.x <= 100)
        {
            transform.position += Vector3.up * 30 * Time.deltaTime;
        }

        if (Input.GetKey("s") && !Input.GetKey("left shift") && Camera.main.transform.position.y >= 0)
        {
            transform.position += Vector3.down * 30 * Time.deltaTime;
        }

        if (Input.GetKey("a"))
        {
            transform.Translate(Vector3.left * 20 * Time.deltaTime);
        }

        if (Input.GetKey("d"))
        {
            transform.Translate(Vector3.right * 20 * Time.deltaTime);
        }

        if (Input.GetKey("w") && Input.GetKey("left shift"))
        {
            transform.Rotate(Vector3.left * 20 * Time.deltaTime);
        }

        if (Input.GetKey("s") && Input.GetKey("left shift"))
        {
            transform.Rotate(Vector3.right * 20 * Time.deltaTime);
        }

    }

}
