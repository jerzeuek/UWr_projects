using UnityEngine;
using UnityEngine.UI;

public class Timer : MonoBehaviour
{
    public Text counter;
    public bool keepUpdating = true;
    public int minutes, seconds;
    void Start()
    {
        counter = GetComponent<Text>();
    }

    void Update()
    {
        if (keepUpdating)
        {
            minutes = (int)(Time.timeSinceLevelLoad / 60f);
            seconds = (int)(Time.timeSinceLevelLoad % 60f);
            counter.text = "Czas: " + minutes.ToString("00") + ":" + seconds.ToString("00");
        }
    }
}
