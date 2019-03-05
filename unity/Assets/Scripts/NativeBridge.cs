using System.Runtime.InteropServices;
using UnityEngine;
using UnityEngine.UI;
using System.Collections.Generic;

public class NativeBridge : MonoBehaviour
{

	public static NativeBridge instance;

	#if UNITY_IOS && !UNITY_EDITOR
	[DllImport("__Internal")]
	private extern static void CustomEvent(string key, string value);
	#else

	private void CustomEvent(string key, string value)
	{
		print("custom event to ios");
	}

	#endif

	private void HideControls(string command)
	{
		Canvas canvas = GameObject.Find("Canvas").GetComponent<Canvas>();
		switch (command)
		{
		case "true":
			canvas.enabled = false;
			break;
		case "false":
			canvas.enabled = true;
			break;
		}
	}

	public void OnMenuClick()
	{
		CustomEvent("menu", "toggle");
	}

	public void CallNative(string key, string value)
    {
        // Debug.Log("CallNative: " + funcName + "(" + mTrackableBehaviour.TrackableName + ")");
#if UNITY_ANDROID && !UNITY_EDITOR
        AndroidJavaClass jc = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
        AndroidJavaObject jo = jc.GetStatic<AndroidJavaObject>("currentActivity");
        jo.Call("runOnUiThread", new AndroidJavaRunnable(() =>
        {
            jo.Call("CustomEvent", key, value);
        }));
#endif
#if UNITY_IOS && !UNITY_EDITOR
        public void OnTargetFound(string target)
	{
		CustomEvent("target", target);
	}
        NativeBridge nb = GameObject.Find("NATIVE_BRIDGE").GetComponent<NativeBridge>();
        nb.OnTargetFound(mTrackableBehaviour.TrackableName);
#endif
    }
}
