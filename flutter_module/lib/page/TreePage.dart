import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_module/bean/Tree.dart';
import 'package:flutter_module/model/ProviderWidget.dart';
import 'package:flutter_module/model/TreePageModel.dart';
import 'package:flutter_module/page/TreeListPage.dart';
import '../config/string.dart';

class TreeWidget extends StatefulWidget{
  @override
  _TreePageState createState() => _TreePageState();

}

class _TreePageState extends State<TreeWidget> with TickerProviderStateMixin,AutomaticKeepAliveClientMixin{

  TabController tabController;
  final PageController pageController = PageController();
  final ScrollController scrollController = new ScrollController();

  @override
  void dispose() {
    super.dispose();
    tabController.dispose();
    pageController.dispose();
  }

  @override
  Widget build(BuildContext context) {
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
        brightness:Brightness.light,
        backgroundColor: Colors.white,
        centerTitle: true,
        elevation: 0,
        actions: <Widget>[
          IconButton(
            icon: Icon(
              Icons.filter_list,
              color: Colors.black87,
            ),
            onPressed: (){

            },
          )
        ],
      ),
      body:new ProviderWidget<TreePageModel>(
        model: TreePageModel(),
        onModelInit: (model){
          model.loadData();
        },
        builder: (context,model,child){
          tabController = TabController(length: model.list.length,vsync: this);
          return Column(
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
                  tabs: model.list.map((e) => Tab(text: e.name)).toList(),
                  onTap: (index) => pageController.animateToPage(index, duration: kTabScrollDuration, curve: Curves.ease),
                ),
              ),
              Expanded(
                child: PageView(
                  controller: pageController,
                  onPageChanged: (index) =>tabController.index = index,
                  children: initPage(model),
                ),
              )
            ],
          );
        },
      ),
    );
  }

  initPage(TreePageModel model) {
    List<TreeListPage> pageList = List();
    for(TreeBean bean in model.list){
      var page = TreeListPage(bean);
      pageList.add(page);
    }
    return pageList;
  }

  @override
  bool get wantKeepAlive => true;

}

