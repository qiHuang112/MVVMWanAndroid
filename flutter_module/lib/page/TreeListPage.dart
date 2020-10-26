import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_module/bean/Blog.dart';
import 'package:flutter_module/bean/Tree.dart';
import 'package:flutter_module/config/Color.dart';
import 'package:flutter_module/model/ProviderWidget.dart';
import 'package:flutter_module/model/TreeListPageModel.dart';
import 'package:flutter_module/page/BlogItemPage.dart';

class TreeListPage extends StatefulWidget {
  TreeBean _bean;

  TreeListPage(TreeBean bean) {
    this._bean = bean;
  }

  @override
  TreeListState createState() => TreeListState(_bean);
}

class TreeListState extends State<TreeListPage> with TickerProviderStateMixin {
  TreeBean _bean;

  TreeListState(TreeBean bean) {
    this._bean = bean;
  }

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return new ProviderWidget<TreeListPageModel>(
      model: TreeListPageModel(),
      onModelInit: (model) {
        model.loadData(_bean.children[0].id);
      },
      builder: (context, model, child) {
        return Column(
          mainAxisAlignment: MainAxisAlignment.start,
          children: <Widget>[
            new Container(
                height: Theme.of(context).textTheme.headline4.fontSize * 1.1,
                child: ListView.builder(
                  scrollDirection: Axis.horizontal,
                  itemBuilder: (context, index) {
                    return GestureDetector(
                      onTap: ()=> refreshList(model, index),
                      child: new Container(
                          padding: EdgeInsets.all(5),
                          margin: EdgeInsets.all(5),
                          decoration: BoxDecoration(color: Color(0xFFF4F4F4)),
                          child: Center(
                               child:Text(
                                _bean.children[index].name,
                                style: TextStyle(fontSize: 12, color: Colors.grey),
                              )
                          ),
                          ),
                    );
                  },
                  itemCount: _bean.children.length,
                )),
            new Expanded(
              child: ListView.builder(
                scrollDirection: Axis.vertical,
                itemBuilder: (context, index) {
                  return BlogItemPage(model.list[index]);
                },
                itemCount: model.list.length,
              ),
            ),
          ],
        );
      },
    );
  }

  refreshList(TreeListPageModel model, int index) {
    model.loadData(_bean.children[index].id);
  }
}
