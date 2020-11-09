import 'dart:convert';

import 'package:flutter/cupertino.dart';
import 'package:flutter_module/api/Api.dart';
import 'package:flutter_module/api/ApiService.dart';
import 'package:flutter_module/bean/Error.dart';
import 'package:flutter_module/bean/LoginData.dart';
import 'package:flutter_module/config/Config.dart';
import 'package:flutter_module/utils/AppManager.dart';
import 'package:flutter_module/utils/ToastUtil.dart';

class LoginPageModel with ChangeNotifier {
  LoginData data;
  bool loading = false;

  login(String userName, String password, BuildContext context) async {
    loading = true;
    var map = <String, String>{};
    map['username'] = userName;
    map['password'] = password;

    ApiService.getInstance().postData(Api.LOGIN, data: map,
        success: (result) {
          data = LoginData.fromJson(result);
          String userData = json.encode(result);
          saveAccountInfo(userData);
          loading = false;
          Navigator.of(context).pop();
        },
        fail: (ErrorBean error) {
          loading = false;
          ToastUtil.showError(error.errMsg);
        },
        complete: () => notifyListeners());
  }

  void saveAccountInfo(String result) {
    AppManager.prefs.setString(Config.SP_USER_INFO, result);
  }
}
