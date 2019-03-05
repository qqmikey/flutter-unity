import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Unity',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(title: 'Flutter Unity'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  static const platform = const MethodChannel('flutter_unity.astera.com/flutter_event');


  _onOpenUnityPressed() async {
    try {
      final Map result = await platform.invokeMethod('openUnityView');
      print(result);
    } on PlatformException catch (e) {
      print("Failed to get message from unity: '${e.message}'.");
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(
              'Sample flutter-unity-vuforia app',
            ),
            RaisedButton(
              onPressed: _onOpenUnityPressed,
              child: Text('open unity'),
            ),
          ],
        ),
      ),
    );
  }
}
