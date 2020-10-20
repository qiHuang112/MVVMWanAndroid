class Tag {
  int _id;
  int _articleId;
  String _name;
  String _url;

  Tag.fromJson(dynamic json) {
    _id = json["id"];
    _articleId = json["article"];
    _name = json["name"];
    _url = json["url"];
  }

  Map<String, dynamic> toJson() {
    var map = <String, dynamic>{};
    map["id"] = _id;
    map["article"] = _articleId;
    map["name"] = _name;
    map["url"] = _url;
  }
}
