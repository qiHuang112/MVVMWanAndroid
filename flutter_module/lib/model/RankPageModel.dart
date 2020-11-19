import 'package:flutter/cupertino.dart';
import 'package:flutter_module/api/Api.dart';
import 'package:flutter_module/api/ApiService.dart';
import 'package:flutter_module/bean/Bean.dart';
import 'package:flutter_module/bean/Error.dart';
import 'package:flutter_module/bean/Rank.dart';
import 'package:flutter_module/utils/ToastUtil.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';

class RankPageModel with ChangeNotifier{

  List<Rank> list = [];
  bool loading = true;
  bool error = false;
  int page = 1;
  bool isEnd = false;
  RefreshController refreshController =
  new RefreshController(initialRefresh: false);

  loadData() async {
    page = 1;
    isEnd = false;
    list.clear();
    String url = "${Api.RANK}$page/json";

    ApiService.instance.getData(url,
        success: (result) {
          var pageData = PageData.fromJson(result);
          if (pageData != null) {
            List responseList = pageData.datas;
            page = pageData.curPage + 1;
            setIsEnd(pageData);
            List<Rank> coinList =
            responseList.map((model) => Rank.fromJson(model)).toList();
            list = coinList;
            loading = false;
            error = false;
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
    String url = "${Api.RANK}$page/json";
    ApiService.instance.getData(url,
        success: (result) {
          var pageData = PageData.fromJson(result);
          if (pageData != null) {
            List responseList = pageData.datas;
            page = pageData.curPage + 1;
            setIsEnd(pageData);
            List<Rank> coinList =
            responseList.map((model) => Rank.fromJson(model)).toList();
            list.addAll(coinList);
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