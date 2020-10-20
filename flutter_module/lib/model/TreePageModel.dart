import 'package:flutter/cupertino.dart';
import 'package:flutter_module/api/ApiService.dart';
import 'package:flutter_module/bean/Tree.dart';

class TreePageModel with ChangeNotifier{
  List<TreeBean> list = [];
  bool loading = true;
  bool error = false;

  void loadData() async{
    ApiService.getData(ApiService.tree_url,
    success: (result){
      List responseList = result as List;
      List<TreeBean> treeList = responseList
          .map((model) => TreeBean.fromJson(model))
          .toList();
      this.list = treeList;
      loading = false;
      error = false;
    },fail: (){
          loading = false;
          error = true;
        },complete:() => notifyListeners());
  }
  void retry(){
    loading = true;
    notifyListeners();
    //loadData();
  }

}