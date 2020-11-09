import 'package:flutter/cupertino.dart';
import 'package:flutter_module/api/Api.dart';
import 'package:flutter_module/api/ApiService.dart';
import 'package:flutter_module/bean/Error.dart';
import 'package:flutter_module/bean/Tree.dart';
import 'package:flutter_module/utils/ToastUtil.dart';

class TreePageModel with ChangeNotifier{
  List<TreeBean> list = [];
  bool loading = true;
  bool error = false;

  void loadData() async{
    ApiService.getInstance().getData(Api.TREE,
    success: (result){
      List responseList = result as List;
      List<TreeBean> treeList = responseList
          .map((model) => TreeBean.fromJson(model))
          .toList();
      this.list = treeList;
      loading = false;
      error = false;
    },fail: (err){
          showError(err);
          loading = false;
          error = true;
        },complete:() => notifyListeners());
  }


  retry(){
    loading = true;
    notifyListeners();
    loadData();
  }

  void showError(ErrorBean err) {
    ToastUtil.showError(err.errMsg);
  }

}