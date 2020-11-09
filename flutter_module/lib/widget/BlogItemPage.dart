import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_module/bean/Blog.dart';
import 'package:flutter_module/config/Color.dart';
import 'package:flutter_module/config/String.dart';
import 'package:flutter_module/widget/BlogTitleWidget.dart';
import 'package:flutter_boost/flutter_boost.dart';

class BlogItemPage extends StatefulWidget {
  Blog blog;

  BlogItemPage(this.blog);

  @override
  BlogItemState createState() => BlogItemState();
}

class BlogItemState extends State<BlogItemPage> {
  Blog blog;

  @override
  Widget build(BuildContext context) {
    blog = widget.blog;
    return GestureDetector(
      onTap: () => FlutterBoost.singleton.open(
        'android://detailPage',
        urlParams: <String, dynamic>{'BLOG_KEY': blog.toJson()},
      ),
      child: Card(
        elevation: 1.5,
        /*shape: const RoundedRectangleBorder(
            borderRadius: BorderRadius.all(Radius.circular(14.0))),*/
        margin: EdgeInsets.fromLTRB(0, 5, 0, 5),
        child: Container(
          padding: EdgeInsets.fromLTRB(10, 10, 10, 5),
          child: Column(
            children: <Widget>[
              Row(
                children: <Widget>[
                  Expanded(
                      flex: 1,
                      child: Row(
                        children: <Widget>[
                          Offstage(
                            offstage: blog.type == 0 ?? false,
                            child: Text(
                              DString.top,
                              style: TextStyle(
                                  color: DColor.colorBadge, fontSize: 12),
                            ),
                          ),
                          Container(
                            margin: EdgeInsets.only(left: 5),
                            child: Text(
                                "${blog.author.isNotEmpty ? blog.author : blog.shareUser}",
                                overflow: TextOverflow.ellipsis,
                                maxLines: 1,
                                style: Theme.of(context).textTheme.caption),
                          ),
                        ],
                      )),
                  Align(
                      alignment: Alignment.centerRight,
                      child: Container(
                        margin: EdgeInsets.only(right: 5),
                        child: Text(
                          "${blog.chapterName}/${blog.superChapterName}",
                          style: TextStyle(
                              color: DColor.textColorThird, fontSize: 11.0),
                        ),
                      )),
                ],
              ),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: <Widget>[
                  Expanded(
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: <Widget>[
                        SizedBox(
                          height: 5,
                        ),
                        Container(
                          margin: EdgeInsets.only(left: 5),
                          child: BlogTitleWidget(blog.title),
                        ),
                        SizedBox(
                          height: 2,
                        ),
                        Container(
                            margin: EdgeInsets.only(left: 5),
                            child: Text(
                              blog.desc,
                              style: Theme.of(context).textTheme.caption,
                              overflow: TextOverflow.ellipsis,
                              maxLines: 2,
                            )),
                      ],
                    ),
                  ),
                  SizedBox(
                    width: 5,
                  ),
                ],
              ),
              Row(
                children: <Widget>[
                  Expanded(
                      flex: 1,
                      child: Row(
                        children: <Widget>[
                          Offstage(
                              offstage: !blog.fresh ?? true,
                              child: Text(
                                DString.newBlog,
                                style: TextStyle(
                                    color: DColor.colorBadge, fontSize: 12),
                              )),
                          Container(
                            padding: EdgeInsets.only(left: 5),
                            child: Text(blog.niceDate,
                                textAlign: TextAlign.center,
                                style: Theme.of(context).textTheme.caption),
                          )
                        ],
                      )),
                  Align(
                    alignment: Alignment.centerRight,
                    child: !blog.collect
                        ? IconButton(
                            alignment: Alignment.centerRight,
                            icon: Icon(
                              Icons.star_border,
                            ),
                            //onPressed: () => _collect(uniqueKey),
                          )
                        : IconButton(
                            alignment: Alignment.centerRight,
                            icon: Icon(
                              Icons.star,
                            ),
                            //onPressed: () => _collect(uniqueKey),
                          ),
                  ),
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }

  @override
  bool get wantKeepAlive => true;
}
