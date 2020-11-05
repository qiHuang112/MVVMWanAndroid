import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_module/api/ApiService.dart';
import 'package:flutter_module/bean/Bean.dart';
import 'package:flutter_module/bean/Blog.dart';
import 'package:flutter_module/bean/Error.dart';
import 'package:flutter_module/utils/ToastUtil.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';

class TreeListPageModel with ChangeNotifier{
  List<Blog> list = List();
  bool loading = true;
  bool error = false;
  int page = 0;
  int _id = 0;
  bool isEnd = false;

  RefreshController refreshController = new RefreshController(initialRefresh: false);

  void loadData(int id) async{
    this._id = id;
    page = 0;
    isEnd = false;
    list.clear();
    String url = "${ApiService.tree_detail_url}$page/json?cid=$_id";
    ApiService.getData(url,success: (result){
      var pageData = PageData.fromJson(result);
      if(pageData!=null){
        List responseList = pageData.datas;
        page = pageData.curPage+1;
        setIsEnd(pageData);
        List<Blog> blogList = responseList
            .map((model) => Blog.fromJson(model))
            .toList();
        loading = false;
        error = false;
        list = blogList;
        refreshController.refreshCompleted();
        refreshController.footerMode.value= LoadStatus.canLoading;
      }else{
        error = true;
        loading = false;
      }
    },fail: (ErrorBean error){
      ToastUtil.showError(error.errMsg);
      this.error = true;
      loading = false;
      refreshController.refreshFailed();
    },complete: ()=> notifyListeners());
  }

  retry(int id){
    loading = true;
    notifyListeners();
    loadData(id);
  }

  refresh(){
    loadData(_id);
  }

  loadMore() async{
    if(isEnd){
      refreshController.loadNoData();
      return;
    }
    String url = "${ApiService.tree_detail_url}$page/json?cid=$_id";
    ApiService.getData(url,success: (result){
      var pageData = PageData.fromJson(result);
      if(pageData!=null){
        List responseList = pageData.datas;
        page = pageData.curPage+1;
        setIsEnd(pageData);
        List<Blog> blogList = responseList
            .map((model) => Blog.fromJson(model))
            .toList();
        list.addAll(blogList);
        refreshController.loadComplete();
      }else{
        refreshController.loadFailed();
      }
    },fail: (ErrorBean error){
      refreshController.loadFailed();
      ToastUtil.showError(error.errMsg);

    },complete: ()=> notifyListeners());


  }

  void setIsEnd(PageData pageData) {
    isEnd = pageData.offset>=pageData.total;
  }

}