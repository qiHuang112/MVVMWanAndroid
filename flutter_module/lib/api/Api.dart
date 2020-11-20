class Api{
  static const String BASE_URL = "https://www.wanandroid.com/";

  //登录
  static const String LOGIN = "user/login";

  //退出登录
  static const String LOGIN_OUT_JSON = 'user/logout/json';

  //注册
  static const String REGISTER = "user/register";

  //获取体系数据
  static const String TREE = "tree/json";

  //获取体系详细数据
  static const String TREE_DETAIL = "article/list/";


  //获取用户积分数据
  static const String COIN_INFO = "lg/coin/userinfo/json";

  //获取积分排行榜
  static const String RANK = "coin/rank/";

  //获取用户积分列表
  static const String COIN_INFO_LIST = "lg/coin/list/";

  //获取收藏文章列表
  static const String COLLECT_LIST = "lg/collect/list/";

  //取消收藏
  static const String UN_COLLECT = "lg/uncollect/";

  //取消收藏-文章列表
  static const String UN_COLLECT_ORIGIN_ID = "lg/uncollect_originId/";


  //收藏站内文章
  static const String COLLECT = "lg/collect/";

  //收藏站内文章
  static const String SHARE = "user/lg/private_articles/";
}