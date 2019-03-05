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

    companion object {
        private const val CHANNEL = "flutter_unity.astera.com/flutter_event"
    }
}
