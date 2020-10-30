import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_module/bean/Tree.dart';
import 'package:flutter_module/config/Color.dart';
import 'package:flutter_module/model/ProviderWidget.dart';
import 'package:flutter_module/model/TreeListPageModel.dart';
import 'package:flutter_module/page/BlogItemPage.dart';
import 'package:flutter_module/page/LoadingContainer.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';

class TreeListPage extends StatefulWidget {
  TreeBean _bean;

  TreeListPage(TreeBean bean) {
    this._bean = bean;
  }

  @override
  TreeListState createState() => TreeListState(_bean);
}

class TreeListState extends State<TreeListPage> with TickerProviderStateMixin ,AutomaticKeepAliveClientMixin{
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
        refreshList(model, 0,false);
      },
      builder: (context, model, child) {
        return new LoadingContainer(
            loading: model.loading,
            error: model.error,
            retry:()=> refreshList(model, 0,true),
            child: new Column(
              mainAxisAlignment: MainAxisAlignment.start,
              children: <Widget>[
                new Container(
                    height:
                        Theme.of(context).textTheme.headline4.fontSize * 1.4,
                    child: ListView.builder(
                      scrollDirection: Axis.horizontal,
                      itemBuilder: (context, index) {
                        return new Container(
                            margin: EdgeInsets.only(left: 8, bottom: 8, top: 8),
                            child: new ChoiceChip(
                              label: Text(
                                _bean.children[index].name,
                                style: TextStyle(
                                    fontSize: 13,
                                    color: DColor.textColorPrimary),
                              ),
                              selected: _bean.children[index].selected,
                              padding: EdgeInsets.only(
                                  left: 2, right: 2, top: 1, bottom: 1),
                              onSelected: (selected) {
                                refreshList(model, index,false);
                              },
                              selectedColor: DColor.bgColorThird,
                              backgroundColor: DColor.bgColorSecondary,
                            ));
                      },
                      itemCount: _bean.children.length,
                    )),
                new Expanded(
                  child: SmartRefresher(
                    controller: model.refreshController,
                    onRefresh: model.refresh,
                    onLoading: model.loadMore,
                    enablePullUp: true,
                    child: ListView.builder(
                      scrollDirection: Axis.vertical,
                      itemBuilder: (context, index) {
                        return BlogItemPage(model.list[index]);
                      },
                      itemCount: model.list.length,
                    ),
                  ),
                ),
              ],
            ));
      },
    );
  }

  refreshList(TreeListPageModel model, int index,bool isRetry) {
    for (Children children in _bean.children) {
      children.setSelected(false);
    }
    _bean.children[index].setSelected(true);
    if(isRetry){
      model.retry(_bean.children[index].id);
    }else{
      model.loadData(_bean.children[index].id);
    }
  }

  @override
  bool get wantKeepAlive => true;
}
