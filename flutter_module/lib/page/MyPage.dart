import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_module/config/Color.dart';
import 'package:flutter_module/config/String.dart';

class MyPage extends StatefulWidget {
  @override
  MyPageState createState() => MyPageState();
}

class MyPageState extends State<MyPage> with AutomaticKeepAliveClientMixin {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: DColor.bgColorSecondary,
      appBar: AppBar(
        title: Text(
          DString.my,
          style: TextStyle(
            color: DColor.textColorPrimary,
            fontSize: 18,
          ),
        ),
        brightness: Brightness.light,
        backgroundColor: Colors.white,
        centerTitle: true,
        elevation: 1.5,
      ),
      body: Column(
        children: [
          SizedBox(
            height: 8,
          ),
          Container(
            color: DColor.colorPrimary,
            padding: EdgeInsets.all(20),
            child: Row(
              children: [
                CircleAvatar(
                  backgroundColor: DColor.colorPrimary,
                  backgroundImage: AssetImage("assets/img/ic_avatar.png"),
                ),
                SizedBox(
                  width: 10,
                ),
                Expanded(
                  flex: 1,
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text('ml091225@126.com'),
                      SizedBox(
                        height: 6,
                      ),
                      Text('ID:22070'),
                    ],
                  ),
                ),
                Align(
                  alignment: Alignment.centerRight,
                  child: Icon(
                    Icons.keyboard_arrow_right,
                    color: Colors.black,
                  ),
                )
              ],
            ),
          ),
          SizedBox(
            height: 8,
          ),
          Container(
            color: DColor.colorPrimary,
            child: ListTile(
              leading: Icon(
                Icons.plus_one,
                color: Colors.black,
              ),
              title: Text(
                DString.coin,
                style: TextStyle(fontSize: 14, color: DColor.textColorPrimary),
              ),
            ),
          ),
          Container(
            color: DColor.colorPrimary,
            child: ListTile(
              leading: Icon(
                Icons.looks_one,
                color: Colors.black,
              ),
              title: Text(
                DString.rank,
                style: TextStyle(fontSize: 14, color: DColor.textColorPrimary),
              ),
            ),
          ),
          SizedBox(
            height: 8,
          ),
          Container(
            color: DColor.colorPrimary,
            child: ListTile(
              leading: Icon(
                Icons.share,
                color: Colors.black,
              ),
              title: Text(
                DString.my_share,
                style: TextStyle(fontSize: 14, color: DColor.textColorPrimary),
              ),
            ),
          ),
          Container(
            color: DColor.colorPrimary,
            child: ListTile(
              leading: Icon(
                Icons.star,
                color: Colors.black,
              ),
              title: Text(
                DString.my_collection,
                style: TextStyle(fontSize: 14, color: DColor.textColorPrimary),
              ),
            ),
          ),
          Container(
            color: DColor.colorPrimary,
            child: ListTile(
              leading: Icon(
                Icons.history,
                color: Colors.black,
              ),
              title: Text(
                DString.my_history,
                style: TextStyle(fontSize: 14, color: DColor.textColorPrimary),
              ),
            ),
          ),
          SizedBox(
            height: 8,
          ),
          Container(
            color: DColor.colorPrimary,
            child: ListTile(
              leading: Icon(
                Icons.info,
                color: Colors.black,
              ),
              title: Text(
                DString.about,
                style: TextStyle(fontSize: 14, color: DColor.textColorPrimary),
              ),
            ),
          ),
          Container(
            color: DColor.colorPrimary,
            child: ListTile(
              leading: Icon(
                Icons.settings,
                color: Colors.black,
              ),
              title: Text(
                DString.setting,
                style: TextStyle(fontSize: 14, color: DColor.textColorPrimary),
              ),
            ),
          )
        ],
      ),
    );
  }

  @override
  bool get wantKeepAlive => true;
}
