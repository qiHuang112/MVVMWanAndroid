import 'package:flutter/material.dart';
import 'package:flutter_boost/flutter_boost.dart';
import 'package:flutter_module/page/CollectionPage.dart';
import 'package:flutter_module/page/LoginPage.dart';
import 'package:flutter_module/page/MyCoinPage.dart';
import 'package:flutter_module/page/MyPage.dart';
import 'package:flutter_module/page/RankPage.dart';
import 'package:flutter_module/page/TreePage.dart';
import 'package:flutter_module/utils/AppManager.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  // This widget is the root of your application.
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {

  // This widget is the home page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  //

  @override
  void initState() {
    super.initState();
    AppManager.init();
    FlutterBoost.singleton.registerPageBuilders(<String, PageBuilder>{
      'tree':(String pageName, Map<String, dynamic> params, String _) =>
          TreeWidget(),
      'my':(String pageName, Map<String, dynamic> params, String _) =>
          MyPage(),
      'login':(String pageName, Map<String, dynamic> params, String _) =>
          LoginPage(),
      'collection':(String pageName, Map<String, dynamic> params, String _) =>
          CollectionPage(),
      'mycoin':(String pageName, Map<String, dynamic> params, String _) =>
          MyCoinPage(),
      'rank':(String pageName, Map<String, dynamic> params, String _) =>
          RankPage(),
    });

    FlutterBoost.singleton.addBoostNavigatorObserver(TestBoostNavigatorObserver());

  }


  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        title: 'Flutter Boost example',
        builder: FlutterBoost.init(postPush: _onRoutePushed),
        home: Container(color: Colors.white));
  }

  void _onRoutePushed(
      String pageName,
      String uniqueId,
      Map<String, dynamic> params,
      Route<dynamic> route,
      Future<dynamic> _,
      ) {}
}



class TestBoostNavigatorObserver extends NavigatorObserver {
  @override
  void didPush(Route<dynamic> route, Route<dynamic> previousRoute) {
    print('flutterboost#didPush');
  }

  @override
  void didPop(Route<dynamic> route, Route<dynamic> previousRoute) {
    print('flutterboost#didPop');
  }

  @override
  void didRemove(Route<dynamic> route, Route<dynamic> previousRoute) {
    print('flutterboost#didRemove');
  }

  @override
  void didReplace({Route<dynamic> newRoute, Route<dynamic> oldRoute}) {
    print('flutterboost#didReplace');
  }
}

