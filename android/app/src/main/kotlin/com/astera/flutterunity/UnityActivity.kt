package com.astera.flutterunity

import android.app.Activity
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.unity3d.player.UnityPlayerActivity

class UnityActivity: UnityPlayerActivity() {

    var instruction: LinearLayout? = null
    var backButton: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val jwt = intent.getStringExtra("jwt")
        this.setContentView(R.layout.activity_unity)
        instruction = findViewById(R.id.instruction)

        val unityContent = findViewById<FrameLayout>(R.id.unity_content)
        val unityView = mUnityPlayer.view

        if (unityContent.indexOfChild(unityView) == -1) {
            unityContent.addView(unityView, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        }

        mUnityPlayer.requestFocus()

        backButton = findViewById<Button>(R.id.back_to_flutter)
        backButton?.let {
            it.setOnClickListener {
                val intent = Intent()
                intent.putExtra("unityResult", true)
                intent.putExtra("shouldUpdateProfile", true)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }

    fun CustomEvent(key: String, value: String) {

        when (key) {
            "unityReady" -> {
                instruction?.let{
                    it.visibility = View.INVISIBLE
                }
                backButton?.let{
                    it.visibility = View.VISIBLE
                }
            }
            else -> {
                print("unhandled unity message ${key} ${value}")
            }
        }
    }
}