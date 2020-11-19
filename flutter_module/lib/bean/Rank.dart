/// coinCount : 27280
/// level : 273
/// rank : "1"
/// userId : 20382
/// username : "g**eii"

class Rank {
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

  Rank({
    int coinCount,
    int level,
    String rank,
    int userId,
    String username}){
    _coinCount = coinCount;
    _level = level;
    _rank = rank;
    _userId = userId;
    _username = username;
  }

  Rank.fromJson(dynamic json) {
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

