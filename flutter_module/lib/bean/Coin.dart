/// coinCount : 30
/// date : 1605592747000
/// desc : "2020-11-17 13:59:07 签到 , 积分：10 + 20"
/// id : 331021
/// reason : "签到"
/// type : 1
/// userId : 22070
/// userName : "ml091225@126.com"

class Coin {
  int _coinCount;
  int _date;
  String _desc;
  int _id;
  String _reason;
  int _type;
  int _userId;
  String _userName;

  int get coinCount => _coinCount;

  int get date => _date;

  String get desc => _desc;

  int get id => _id;

  String get reason => _reason;

  int get type => _type;

  int get userId => _userId;

  String get userName => _userName;

  Coin(
      {int coinCount,
      int date,
      String desc,
      int id,
      String reason,
      int type,
      int userId,
      String userName}) {
    _coinCount = coinCount;
    _date = date;
    _desc = desc;
    _id = id;
    _reason = reason;
    _type = type;
    _userId = userId;
    _userName = userName;
  }

  Coin.fromJson(dynamic json) {
    _coinCount = json["coinCount"];
    _date = json["date"];
    _desc = json["desc"];
    _id = json["id"];
    _reason = json["reason"];
    _type = json["type"];
    _userId = json["userId"];
    _userName = json["userName"];
  }

  Map<String, dynamic> toJson() {
    var map = <String, dynamic>{};
    map["coinCount"] = _coinCount;
    map["date"] = _date;
    map["desc"] = _desc;
    map["id"] = _id;
    map["reason"] = _reason;
    map["type"] = _type;
    map["userId"] = _userId;
    map["userName"] = _userName;
    return map;
  }
}

class MyCoin {
  int _coinCount;
  int _level;
  String _rank;
  int _userId;
  String _username;

  int get coinCount => _coinCount;

  int get level => _level;

  String get rank => _rank;

  int get userId => _userId;

  String get username => _username;

  MyCoin({int coinCount, int level, String rank, int userId, String username}) {
    _coinCount = coinCount;
    _level = level;
    _rank = rank;
    _userId = userId;
    _username = username;
  }

  MyCoin.fromJson(dynamic json) {
    _coinCount = json["coinCount"];
    _level = json["level"];
    _rank = json["rank"];
    _userId = json["userId"];
    _username = json["username"];
  }

  Map<String, dynamic> toJson() {
    var map = <String, dynamic>{};
    map["coinCount"] = _coinCount;
    map["level"] = _level;
    map["rank"] = _rank;
    map["userId"] = _userId;
    map["username"] = _username;
    return map;
  }
}
