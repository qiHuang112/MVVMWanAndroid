import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_module/bean/Tree.dart';
import 'package:flutter_module/config/Color.dart';
import 'package:flutter_module/model/ProviderWidget.dart';
import 'package:flutter_module/model/TreeListPageModel.dart';
import 'package:flutter_module/widget/BlogItemPage.dart';
import 'package:flutter_module/widget/LoadingContainer.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';
import 'package:scroll_to_index/scroll_to_index.dart';

class TreeListPage extends StatefulWidget {
  TreeBean bean;
  TreeListState state;
  int index = 0;

  TreeListPage(this.bean);

  @override
  TreeListState createState() {
    state = TreeListState();
    return state;
  }

  void setCurrentIndex(int index) {
    if (state == null) {
      this.index = index;
      createState();
    } else {
      state.setCurrentIndex(index);
    }
  }
}

class TreeListState extends State<TreeListPage>
    with TickerProviderStateMixin, AutomaticKeepAliveClientMixin {
  TreeListPageModel model;

  AutoScrollController controller;

  @override
  void initState() {
    super.initState();
    controller = AutoScrollController(axis: Axis.horizontal);
    for (Children children in widget.bean.children) {
      children.setSelected(false);
    }
    widget.bean.children[widget.index].setSelected(true);
  }

  @override
  Widget build(BuildContext context) {
    return new ProviderWidget<TreeListPageModel>(
      model: TreeListPageModel(),
      onModelInit: (model) {
        this.model = model;
        refreshList(model, widget.index, false);
        controller.scrollToIndex(widget.index,
            preferPosition: AutoScrollPosition.middle);
      },
      builder: (context, model, child) {
        return new LoadingContainer(
            loading: model.loading,
            error: model.error,
            retry: () => refreshList(model, widget.index, true),
            child: new Column(
              mainAxisAlignment: MainAxisAlignment.start,
              children: <Widget>[
                new Container(
                    height:
                        Theme.of(context).textTheme.headline4.fontSize * 1.4,
                    child: ListView.builder(
                      controller: controller,
                      scrollDirection: Axis.horizontal,
                      itemBuilder: (context, index) {
                        return new AutoScrollTag(
                          key: ValueKey(index),
                          controller: controller,
                          index: index,
                          child: Container(
                              margin:
                                  EdgeInsets.only(left: 8, bottom: 8, top: 8),
                              child: new ChoiceChip(
                                label: Text(
                                  widget.bean.children[index].name,
                                  style: TextStyle(
                                      fontSize: 13,
                                      color: DColor.textColorPrimary),
                                ),
                                selected: widget.bean.children[index].selected,
                                padding: EdgeInsets.only(
                                    left: 2, right: 2, top: 1, bottom: 1),
                                onSelected: (selected) {
                                  refreshTag(index);
                                  refreshList(model, index, false);
                                },
                                selectedColor: DColor.bgColorThird,
                                backgroundColor: DColor.bgColorSecondary,
                              )),
                        );
                      },
                      itemCount: widget.bean.children.length,
                    )),
                new Expanded(
                  flex: 1,
                  child: SmartRefresher(
                    controller: model.refreshController,
                    onRefresh: model.refresh,
                    onLoading: model.loadMore,
                    enablePullUp: true,
                    child: ListView.builder(
                      scrollDirection: Axis.vertical,
                      itemBuilder: (context, index) {
                        return BlogItemPage(model.list[index],false);
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

  setCurrentIndex(int index) {
    refreshTag(index);
    controller.scrollToIndex(index, preferPosition: AutoScrollPosition.middle);
    refreshList(model, index, false);
  }

  refreshTag(int index) {
    setState(() {
      for (Children children in widget.bean.children) {
        children.setSelected(false);
      }
      widget.bean.children[index].setSelected(true);
    });
  }

  refreshList(TreeListPageModel model, int index, bool isRetry) {
    if (isRetry) {
      model.retry(widget.bean.children[index].id);
    } else {
      model.loadData(widget.bean.children[index].id);
    }
  }

  @override
  bool get wantKeepAlive => true;
}
