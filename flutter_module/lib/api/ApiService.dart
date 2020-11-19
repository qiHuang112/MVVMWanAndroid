import 'dart:convert';
import 'dart:io';

import 'package:cookie_jar/cookie_jar.dart';
import 'package:dio/dio.dart';
import 'package:dio_cookie_manager/dio_cookie_manager.dart';
import 'package:flutter_module/api/Api.dart';
import 'package:flutter_module/bean/Bean.dart';
import 'package:flutter_module/bean/Error.dart';
import 'package:flutter_boost/flutter_boost.dart';
import 'package:path_provider/path_provider.dart';

class ApiService {
  static String _baseUrl = Api.BASE_URL;
  static final ApiService _singleton = ApiService.internal();
  static ApiService get instance => ApiService();

  static Dio _dio;
  BaseOptions options;

  factory ApiService(){
    return _singleton;
  }
  

  ApiService.internal(){
    options = BaseOptions(
      // 访问url
      baseUrl: _baseUrl,
      // 连接超时时间
      connectTimeout: 5000,
      // 响应流收到数据的间隔
      receiveTimeout: 15000,
      // http请求头
      headers: {"version": "1.0.0"},
      // 接收响应数据的格式
      responseType: ResponseType.plain,
    );
    _dio = Dio(options);
    // 在拦截其中加入Cookie管理器
    _addCookie();
  }

  getData(String url,
      {data,
      options,
      cancelToken,
      Function success,
      Function fail,
      Function complete}) async {
    try {
      var response = await _dio.get(url,
          queryParameters: data, options: options, cancelToken: cancelToken);
      if (response.statusCode == 200) {
        var baseResponse = ApiResponse.fromJson(json.decode(response.data));
        if (baseResponse != null) {
          switch (baseResponse.errorCode) {
            case 0:
              success(baseResponse.data);
              break;
            case 1001:
              ErrorBean error = ErrorBean("未登录", 1001);
              fail(error);
              FlutterBoost.singleton.open('login');
              break;
          }
        } else {
          ErrorBean error = ErrorBean("数据解析错误", 101);
          fail(error);
        }
      } else {
        throw Exception('"Request failed with status: ${response.statusCode}"');
      }
    } catch (e) {
      ErrorBean error = ErrorBean(e.toString(), 2000);
      fail(error);
    } finally {
      if (complete != null) {
        complete();
      }
    }
  }

  postData(String url,
      {data,
      options,
      cancelToken,
      Function success,
      Function fail,
      Function complete}) async {
    try {
      var response = await _dio.post(url,
          queryParameters: data, options: options, cancelToken: cancelToken);
      if (response.statusCode == 200) {
        var baseResponse = ApiResponse.fromJson(json.decode(response.data));
        if (baseResponse != null) {
          switch (baseResponse.errorCode) {
            case 0:
              success(baseResponse.data);
              break;
            case -1001:
              ErrorBean error = ErrorBean("未登录", 1001);
              fail(error);
              FlutterBoost.singleton.open('login');
              break;
          }
        } else {
          ErrorBean error = ErrorBean("数据解析错误", 101);
          fail(error);
        }
      } else {
        throw Exception('"Request failed with status: ${response.statusCode}"');
      }
    } catch (e) {
      ErrorBean error = ErrorBean(e.toString(), 2000);
      fail(error);
    } finally {
      if (complete != null) {
        complete();
      }
    }
  }

  static void _addCookie(){
    /*Directory appDocDir = await getApplicationDocumentsDirectory();
    //新建目录
    var dir = Directory("$appDocDir/cookies");
    await dir.create();
    var cookieJar = PersistCookieJar(dir: dir.path);*/
    _dio.interceptors.add(CookieManager(CookieJar()));
  }

}
