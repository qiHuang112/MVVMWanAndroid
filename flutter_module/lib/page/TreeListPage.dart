import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_module/bean/Tree.dart';
import 'package:flutter_module/config/string.dart';

class TreeListPage extends StatefulWidget{
  TreeBean _bean;

  TreeListPage(TreeBean bean){
    this._bean = bean;
  }

  @override
  TreeListState createState() => TreeListState();
}

class TreeListState extends State<TreeListPage> with TickerProviderStateMixin{


  @override
  Widget build(BuildContext context) {
    return Column(
      children: <Widget>[
        Text(
          DString.tree,
          style: TextStyle(
            fontSize: 18,
            color: Colors.black,
          ),
        )
      ],
    );
  }


}