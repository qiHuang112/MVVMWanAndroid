import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_module/config/Color.dart';
import 'package:flutter_module/config/String.dart';
import 'package:flutter_module/model/ProviderWidget.dart';
import 'package:flutter_module/model/RankPageModel.dart';
import 'package:flutter_module/widget/LoadingContainer.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';

class RankPage extends StatefulWidget{
  @override
  RankPageState createState() => RankPageState();

}

class RankPageState extends State<RankPage>{
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(
          DString.rank,
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
      body: new ProviderWidget<RankPageModel>(
          model: RankPageModel(),
          onModelInit: (model){
            model.loadData();
          },
          builder: (context, model, child){
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
                      return Container(
                        padding: EdgeInsets.all(20),
                        child: Row(
                          children: [
                            Text(
                              model.list[index].rank,
                              style: TextStyle(
                                color: DColor.textColorSecondary
                              ),
                            ),
                            SizedBox(width: 20,),
                            Expanded(
                              flex: 1,
                              child: Text(
                                model.list[index].username,
                                style: TextStyle(
                                    color: DColor.textColorSecondary
                                ),
                              ),
                            ),
                            Align(
                              alignment: Alignment.centerRight,
                              child: Text(
                                model.list[index].coinCount.toString(),
                                style: TextStyle(
                                    color: DColor.textColorSecondary
                                ),
                              ),
                            )
                          ],
                        ),
                      );
                    },
                    itemCount: model.list.length,
                  ),
                ));
          }),
    );
  }

}