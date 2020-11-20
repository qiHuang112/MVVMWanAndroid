import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_module/config/Color.dart';
import 'package:flutter_module/config/String.dart';
import 'package:flutter_module/model/ProviderWidget.dart';
import 'package:flutter_module/model/SharePageModel.dart';
import 'package:flutter_module/widget/BlogItemPage.dart';
import 'package:flutter_module/widget/LoadingContainer.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';

class SharePage extends StatefulWidget{
  @override
  SharePageState createState() => SharePageState();

}

class SharePageState extends State<SharePage>{
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(
          DString.my_share,
          style: TextStyle(
            color: DColor.textColorPrimary,
            fontSize: 18,
          ),
        ),
        leading: IconButton(
          icon: Icon(
            Icons.arrow_back,
            color: Colors.black,
          ),
          onPressed: () => Navigator.of(context).pop(),
        ),
        brightness: Brightness.light,
        backgroundColor: Colors.white,
        centerTitle: true,
        elevation: 1.5,
      ),
      body: Container(
        child: new ProviderWidget<SharePageModel>(
            model: SharePageModel(),
            onModelInit: (model) {
              model.loadData();
            },
            builder: (context, model, child) {
              return LoadingContainer(
                  loading: model.loading,
                  retry: ()=>model.retry,
                  error: model.error,
                  child: SmartRefresher(
                    controller: model.refreshController,
                    onRefresh: model.loadData,
                    onLoading: model.loadMore,
                    enablePullUp: true,
                    child: ListView.builder(
                      scrollDirection: Axis.vertical,
                      itemBuilder: (context, index) {
                        return BlogItemPage(model.list[index], true);
                      },
                      itemCount: model.list.length,
                    ),
                  ));
            }),
      ),
    );
  }

}