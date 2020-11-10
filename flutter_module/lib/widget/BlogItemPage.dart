import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_module/api/Api.dart';
import 'package:flutter_module/api/ApiService.dart';
import 'package:flutter_module/bean/Blog.dart';
import 'package:flutter_module/bean/Error.dart';
import 'package:flutter_module/config/Color.dart';
import 'package:flutter_module/config/String.dart';
import 'package:flutter_module/utils/ToastUtil.dart';
import 'package:flutter_module/widget/BlogTitleWidget.dart';
import 'package:flutter_boost/flutter_boost.dart';

class BlogItemPage extends StatefulWidget {
  Blog blog;
  bool isCollection;

  BlogItemPage(this.blog,this.isCollection);

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
        margin: EdgeInsets.only(bottom: 10),
        child: Container(
          padding: EdgeInsets.fromLTRB(10, 10, 10, 5),
          child: Column(
            children: <Widget>[
              Row(
                children: <Widget>[
                  Expanded(
                      flex: 1,
                      child: Container(
                        margin: EdgeInsets.only(left: 5),
                        child: Text(
                            "${blog.shareUser !=null ? blog.author.isNotEmpty? blog.author : blog.shareUser: DString.anonymous}",
                            overflow: TextOverflow.ellipsis,
                            maxLines: 1,
                            style: Theme.of(context).textTheme.caption),
                      )
                  ),
                  Align(
                      alignment: Alignment.centerRight,
                      child: Container(
                        margin: EdgeInsets.only(right: 5),
                        child: Text(
                          "${blog.superChapterName !=null? blog.chapterName+'/'+blog.superChapterName :blog.chapterName}",
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
                      child: Container(
                        margin: EdgeInsets.only(left: 5),
                        child: Text(blog.niceDate,
                            style: Theme.of(context).textTheme.caption),
                      )),
                  Align(
                    alignment: Alignment.centerRight,
                    child: !blog.collect
                        ? IconButton(
                            alignment: Alignment.centerRight,
                            icon: Icon(
                              Icons.star_border,
                            ),
                            onPressed: () => _collect(),
                          )
                        : IconButton(
                            alignment: Alignment.centerRight,
                            icon: Icon(
                              Icons.star,
                            ),
                            onPressed: () => _collect(),
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

  _collect() {
    String url;
    if(blog.collect){
      if(widget.isCollection){
        url = "${Api.UN_COLLECT_ORIGIN_ID}${blog.originId}/json";
      }else{
        url = "${Api.UN_COLLECT_ORIGIN_ID}${blog.id}/json";
      }
    }else{
      url = "${Api.COLLECT}${blog.id}/json";
    }
    ApiService.getInstance().postData(url,
      success: (result){
        setState(() {
          blog.setCollect(!blog.collect);
          blog.collect == true?ToastUtil.showTip(DString.collect_success)
          :ToastUtil.showTip(DString.unCollect_success);
        });
      },
      fail: (ErrorBean errorBean){
        blog.collect == true?ToastUtil.showTip(DString.unCollect_failed)
            :ToastUtil.showTip(DString.collect_failed);
      }
    );
  }
}
