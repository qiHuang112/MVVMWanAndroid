import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_module/config/Color.dart';
import 'package:flutter_module/config/String.dart';
import 'package:flutter_module/model/MyCoinPageModel.dart';
import 'package:flutter_module/model/ProviderWidget.dart';
import 'package:flutter_module/widget/CoinItem.dart';
import 'package:flutter_module/widget/LoadingContainer.dart';
import 'package:flutter_module/widget/MyCoinItem.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';

class MyCoinPage extends StatefulWidget {
  @override
  MyCoinPageState createState() => MyCoinPageState();
}

class MyCoinPageState extends State<MyCoinPage> with TickerProviderStateMixin{
  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      appBar: AppBar(
        title: Text(
          DString.coin,
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
      body: new ProviderWidget<MyCoinPageModel>(
          model: new MyCoinPageModel(),
          onModelInit: (model) {
            model.loadData();
          },
          builder: (context, model, child) {
            return new LoadingContainer(
                loading: model.loading,
                retry: ()=>model.retry(),
                error: model.error,
                child: SmartRefresher(
                  controller: model.refreshController,
                  onRefresh: model.loadData,
                  onLoading: model.loadMore,
                  enablePullUp: true,
                  child: ListView.separated(
                    scrollDirection: Axis.vertical,
                    separatorBuilder: (context,index){
                      return Container(
                        height: 1,
                        color: DColor.bgColorThird,
                      );
                    },
                    itemBuilder: (context, index) {
                      if (index == 0) {
                        return MyCoinItem(model.myCoin);
                      } else {
                        return CoinItem(model.list[index - 1]);
                      }
                    },
                    itemCount: model.list.length + 1,
                  ),
                ));
          }),
    );
  }
}
