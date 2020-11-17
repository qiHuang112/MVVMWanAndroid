import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_module/bean/Coin.dart';
import 'package:flutter_module/config/Color.dart';

class MyCoinItem extends StatefulWidget{

  MyCoin myCoin;

  MyCoinItem(this.myCoin);

  @override
  MyCoinItemState createState() => MyCoinItemState();

}


class MyCoinItemState extends State<MyCoinItem>{


  @override
  Widget build(BuildContext context) {
    return Container(
      padding: EdgeInsets.only(top: 50,bottom: 10),
      child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            Text(
              widget.myCoin?.coinCount.toString(),
              style: TextStyle(
                color: DColor.textColorPrimary,
                fontSize:64,
                fontWeight: FontWeight.bold,
              ),
            ),
            SizedBox(height: 16,),
            Text(
              '等级：${widget.myCoin?.level}   排名：${widget.myCoin?.rank}',
              style: TextStyle(
                color: DColor.textColorPrimary,
                fontSize:14,
              ),
            ),
          ],
      ),
    );
  }


}