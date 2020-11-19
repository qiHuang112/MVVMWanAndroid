import 'package:flutter/cupertino.dart';
import 'package:flutter_module/api/Api.dart';
import 'package:flutter_module/api/ApiService.dart';
import 'package:flutter_module/bean/Bean.dart';
import 'package:flutter_module/bean/Blog.dart';
import 'package:flutter_module/bean/Error.dart';
import 'package:flutter_module/utils/ToastUtil.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';

class CollectionPageModel with ChangeNotifier {
  List<Blog> list = List();
  bool loading = true;
  bool error = false;
  int page = 0;
  bool isEnd = false;
  RefreshController refreshController =
      new RefreshController(initialRefresh: false);

  loadData() async {
    page = 0;
    isEnd = false;
    list.clear();
    String url = "${Api.COLLECT_LIST}$page/json";
    ApiService.instance.getData(url,
        success: (result) {
          var pageData = PageData.fromJson(result);
          if (pageData != null) {
            List responseList = pageData.datas;
            page = pageData.curPage + 1;
            setIsEnd(pageData);
            List<Blog> blogList = responseList.map((model) {
              Blog blog = Blog.fromJson(model);
              blog.setCollect(true);
              return blog;
            }).toList();
            loading = false;
            error = false;
            list = blogList;
            refreshController.refreshCompleted();
            refreshController.footerMode.value = LoadStatus.canLoading;
          } else {
            error = true;
            loading = false;
          }
        },
        fail: (ErrorBean error) {
          ToastUtil.showError(error.errMsg);
          this.error = true;
          loading = false;
          refreshController.refreshFailed();
        },
        complete: () => notifyListeners());
  }

  retry() {
    loading = true;
    notifyListeners();
    loadData();
  }

  loadMore() async {
    if (isEnd) {
      refreshController.loadNoData();
      return;
    }
    String url = "${Api.COLLECT_LIST}$page/json";
    ApiService.instance.getData(url,
        success: (result) {
          var pageData = PageData.fromJson(result);
          if (pageData != null) {
            List responseList = pageData.datas;
            page = pageData.curPage + 1;
            setIsEnd(pageData);
            List<Blog> blogList = responseList.map((model) {
              Blog blog = Blog.fromJson(model);
              blog.setCollect(true);
              return blog;
            }).toList();
            list.addAll(blogList);
            refreshController.loadComplete();
          } else {
            refreshController.loadFailed();
          }
        },
        fail: (ErrorBean error) {
          refreshController.loadFailed();
          ToastUtil.showError(error.errMsg);
        },
        complete: () => notifyListeners());
  }

  void setIsEnd(PageData pageData) {
    isEnd = pageData.offset >= pageData.total;
  }
}
