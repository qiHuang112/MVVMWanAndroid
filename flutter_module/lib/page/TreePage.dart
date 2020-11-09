import 'dart:ui';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_module/bean/Tree.dart';
import 'package:flutter_module/config/Color.dart';
import 'package:flutter_module/model/ProviderWidget.dart';
import 'package:flutter_module/model/TreePageModel.dart';
import 'package:flutter_module/widget/GestureDragDrawer.dart';
import 'package:flutter_module/widget/LoadingContainer.dart';
import 'package:flutter_module/page/TreeListPage.dart';

import '../config/String.dart';

class TreeWidget extends StatefulWidget {
  @override
  _TreePageState createState() => _TreePageState();
}

class _TreePageState extends State<TreeWidget>
    with TickerProviderStateMixin, AutomaticKeepAliveClientMixin {
  TabController tabController;
  final PageController pageController = PageController();
  final ScrollController scrollController = new ScrollController();
  List<TreeListPage> pageList = List();
  int index = 0;

  GlobalKey<GestureDragDrawerState> drawerState = new GlobalKey<GestureDragDrawerState>();

  @override
  void dispose() {
    super.dispose();
    tabController.dispose();
    pageController.dispose();
  }

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    double top = MediaQueryData.fromWindow(window).padding.top;
    return Scaffold(
      backgroundColor: Colors.white,
      appBar: AppBar(
        title: Text(
          DString.tree,
          style: TextStyle(
            fontSize: 18,
            color: Colors.black,
          ),
        ),
        actions: [
          IconButton(
            icon: Icon(
              Icons.filter_list,
              color: Colors.black,
            ),
            onPressed: () => drawerState.currentState.openOrClose(),
          )
        ],
        brightness: Brightness.light,
        backgroundColor: Colors.white,
        centerTitle: true,
        elevation: 1.5,
      ),
      body: new ProviderWidget<TreePageModel>(
        model: TreePageModel(),
        onModelInit: (model) {
          model.loadData();
        },
        builder: (context, model, child) {
          tabController = TabController(length: model.list.length, vsync: this);
          tabController.index = index;
          return new LoadingContainer(
              loading: model.loading,
              error: model.error,
              retry: model.retry,
              child: Stack(
                children: [
                  Column(
                    children: <Widget>[
                      Container(
                        decoration: BoxDecoration(color: Colors.white),
                        child: TabBar(
                          controller: tabController,
                          labelColor: Colors.black,
                          isScrollable: true,
                          unselectedLabelColor: Colors.black54,
                          labelStyle: TextStyle(fontSize: 14),
                          unselectedLabelStyle: TextStyle(fontSize: 14),
                          indicatorColor: Colors.black,
                          indicatorSize: TabBarIndicatorSize.label,
                          tabs:
                              model.list.map((e) => Tab(text: e.name)).toList(),
                          onTap: (index) {
                            pageController.animateToPage(index,
                                duration: kTabScrollDuration, curve: Curves.ease);
                            this.index =index;
                          } ,
                        ),
                      ),
                      Expanded(
                        child: PageView(
                          controller: pageController,
                          onPageChanged: (index) {
                            tabController.index = index;
                            this.index = index;
                          } ,
                          children: initPage(model),
                        ),
                      ),
                    ],
                  ),
                  new GestureDragDrawer(
                    key:drawerState,
                    child: Container(
                      child: ListView.builder(
                        itemBuilder: (context, index) {
                          return new Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              new Padding(
                                padding: EdgeInsets.all(8),
                                child: Text(model.list[index].name),
                              ),
                              new Wrap(
                                children: model.list[index].children
                                    .map((e) => Container(
                                        margin: EdgeInsets.only(
                                            left: 8, bottom: 8, top: 8),
                                        child: new ChoiceChip(
                                          label: Text(
                                            e.name,
                                            style: TextStyle(
                                                fontSize: 13,
                                                color: DColor.textColorPrimary),
                                          ),
                                          selected: false,
                                          padding: EdgeInsets.only(
                                              left: 2,
                                              right: 2,
                                              top: 1,
                                              bottom: 1),
                                          onSelected: (selected) {
                                            pageController.animateToPage(index,
                                                duration: kTabScrollDuration,
                                                curve: Curves.ease);
                                            pageList[index].setCurrentIndex(
                                                model.list[index].children
                                                    .indexOf(e));
                                            drawerState.currentState.autoCallBackAnimation();
                                          },
                                          selectedColor: DColor.bgColorThird,
                                          backgroundColor:
                                              DColor.bgColorSecondary,
                                        )))
                                    .toList(),
                              ),
                              SizedBox(height: 1)
                            ],
                          );
                        },
                        scrollDirection: Axis.vertical,
                        itemCount: model.list.length,
                      ),
                      color: DColor.colorPrimary,
                      margin: EdgeInsets.only(bottom: 40),
                    ),
                    direction: DragDirection.left,
                    childSize: size.width - 80,
                    originOffset: 0,
                    parentHeight: size.height - kToolbarHeight - top,
                    parentWidth: size.width,
                  )
                ],
              ));
        },
      ),
    );
  }

  initPage(TreePageModel model) {
    pageList.clear();
    for (TreeBean bean in model.list) {
      var page = TreeListPage(bean);
      pageList.add(page);
    }
    return pageList;
  }

  @override
  bool get wantKeepAlive => true;
}
