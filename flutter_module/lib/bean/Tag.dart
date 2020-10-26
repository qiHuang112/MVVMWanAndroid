class Tag {
  int _id;
  int _articleId;
  String _name;
  String _url;

  Tag(
      int id,
      int articleId,
      String name,
      String url
      ){
    this._id = id;
    this._articleId = articleId;
    this._name = name;
    this._url = url;
  }

  int get id=> _id;
  int get article=> _articleId;
  String get name=> _name;
  String get url => _url;

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
    return map;
  }
}
