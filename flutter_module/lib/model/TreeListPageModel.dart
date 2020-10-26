import 'package:flutter/cupertino.dart';
import 'package:flutter_module/api/ApiService.dart';
import 'package:flutter_module/bean/Bean.dart';
import 'package:flutter_module/bean/Blog.dart';
import 'package:flutter_module/bean/Error.dart';
import 'package:flutter_module/utils/ToastUtil.dart';

class TreeListPageModel with ChangeNotifier{
  List<Blog> list = List();

  void loadData(int id) async{
    String url = ApiService.tree_detail_url +id.toString();
    ApiService.getData(url,success: (result){
      var pageData = PageData.fromJson(result);
      if(pageData!=null){
        List responseList = pageData.datas;
        List<Blog> blogList = responseList
            .map((model) => Blog.fromJson(model))
            .toList();
        list = blogList;
      }else{

      }
    },fail: (ErrorBean error){
      ToastUtil.showError(error.errMsg);
    },complete: ()=> notifyListeners());
  }

}