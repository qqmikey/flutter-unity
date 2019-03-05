package com.astera.flutterunity

import android.content.Intent
import android.os.Bundle

import io.flutter.app.FlutterActivity
import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.StringCodec
import io.flutter.plugins.GeneratedPluginRegistrant
import io.flutter.view.FlutterMain

class MainActivity : FlutterActivity() {
    internal var methodChannelResult: MethodChannel.Result? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        FlutterMain.startInitialization(this)
        super.onCreate(savedInstanceState)
        GeneratedPluginRegistrant.registerWith(this)

        val that = this
        MethodChannel(flutterView, CHANNEL).setMethodCallHandler { call, result ->
            when (call.method) {
                "openUnityView" -> {
                    val channel = BasicMessageChannel(flutterView, "test.astera.com/flutter_channel", StringCodec.INSTANCE)

                    channel.send("requestJWT") { reply ->
                        val intent = Intent(that, UnityActivity::class.java)
                        intent.putExtra("jwt", reply)
                        startActivityForResult(intent, 1)
                        methodChannelResult = result
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data == null) {
            return
        }
        if (data.getBooleanExtra("unityResult", false)) {
            val hashmap = HashMap<String, Any>()
            hashmap.put("shouldUpdateProfile", data.getBooleanExtra("shouldUpdateProfile", false))
            methodChannelResult!!.success(hashmap)
        }
    }

    companion object {
        private const val CHANNEL = "flutter_unity.astera.com/flutter_event"
    }
}
