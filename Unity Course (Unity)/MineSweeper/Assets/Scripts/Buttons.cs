using UnityEngine;
using UnityEngine.SceneManagement;

public class Buttons : MonoBehaviour
{
    public void Reset()
    {
        SceneManager.LoadScene(SceneManager.GetActiveScene().name);
    }

    public void Close()
    {
        Application.Quit();
    }
}
