import 'dart:convert';

import 'package:flutter_module/bean/LoginData.dart';
import 'package:flutter_module/config/Config.dart';
import 'package:shared_preferences/shared_preferences.dart';

class AppManager{
  AppManager._();

  static Utf8Decoder utf8decoder = Utf8Decoder();
  static Utf8Encoder utf8encoder = Utf8Encoder();
  static SharedPreferences prefs;

  static init() async{
    prefs = await SharedPreferences.getInstance();
  }


  static LoginData  getLoginData(){
     String data =  prefs.get(Config.SP_USER_INFO);
     if(data ==null || data.isEmpty){
       return null;
     }else{
       Map userMap = json.decode(data);
       LoginData loginData = LoginData.fromJson(userMap);
       return loginData;
     }
  }
}