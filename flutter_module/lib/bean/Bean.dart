import 'dart:core';

import 'package:flutter_module/bean/Rank.dart';

class ApiResponse {
  Object _data;
  int _errorCode;
  String _errorMsg;

  Object get data => _data;

  int get errorCode => _errorCode;

  String get errorMsg => _errorMsg;

  ApiResponse({
    Object data,
    int errorCode,
    String errorMsg}){
    _data = data;
    _errorCode = errorCode;
    _errorMsg = errorMsg;
  }

  ApiResponse.fromJson(Map<String, dynamic> json) {
    _data = json["data"];
    _errorCode = json["errorCode"];
    _errorMsg = json["errorMsg"];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data["data"] = _data;
    data["errorCode"] = _errorCode;
    data["errorMsg"] = _errorMsg;
    return data;
  }
}

class PageData {
  int _curPage;
  Object _datas;
  int _offset;
  bool _over;
  int _pageCount;
  int _size;
  int _total;

  int get curPage => _curPage;

  int get offset => _offset;

  int get pageCount => _pageCount;

  int get size => _size;

  int get total => _total;

  bool get over => _over;

  Object get datas => _datas;

  PageData({
    int curPage,
    Object datas,
    int offset,
    bool over,
    int pageCount,
    int size,
    int total}){
    _curPage = curPage;
    _datas = datas;
    _offset = offset;
    _over = over;
    _pageCount = pageCount;
    _size = size;
    _total = total;
  }

  PageData.fromJson(dynamic json) {
    _curPage = json["curPage"];
    _datas = json["datas"];
    _offset = json["offset"];
    _over = json["over"];
    _pageCount = json["pageCount"];
    _size = json["size"];
    _total = json["total"];
  }

  Map<String, dynamic> toJson() {
    var map = <String, dynamic>{};
    map["curPage"] = _curPage;
    map["datas"] = _datas;
    map["offset"] = _offset;
    map["over"] = _over;
    map["pageCount"] = _pageCount;
    map["size"] = _size;
    map["total"] = _total;
    return map;
  }
}

class ShareData{
  PageData _shareArticles;
  Rank _coinInfo;

  PageData get pageData => _shareArticles;
  Rank get rank => _coinInfo;
  
  ShareData(
  {PageData shareArticles,
    Rank coinInfo}
      ){
    _shareArticles = shareArticles;
    _coinInfo = coinInfo;
  }
  
  ShareData.fromJson(dynamic json){
    _shareArticles = PageData.fromJson(json["shareArticles"]);
    _coinInfo = Rank.fromJson(json["coinInfo"]);
  }

  Map<String,dynamic> toJson(){
    var map = <String,dynamic>{};
    map["shareArticles"] = _shareArticles.toJson();
    map["coinInfo"] = _coinInfo.toJson();
    return map;
  }

}
