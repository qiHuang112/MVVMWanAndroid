import 'package:flutter/cupertino.dart';
import 'package:flutter_module/bean/Coin.dart';
import 'package:flutter_module/config/Color.dart';
import 'package:flutter_module/utils/TimeUtil.dart';

class CoinItem extends StatefulWidget{
  Coin coin;

  CoinItem(this.coin);

  @override
  CoinItemState createState() => CoinItemState();

}

class CoinItemState extends State<CoinItem>{



  @override
  Widget build(BuildContext context) {
    return Container(
      padding: EdgeInsets.all(14),
      child: Row(
        children: [
          Expanded(
            flex: 1,
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  widget.coin.reason,
                  style: TextStyle(
                    color: DColor.textColorSecondary,
                    fontSize:14,
                  ),
                ),
                SizedBox(height: 8,),
                Text(
                  TimeUtil.getTimeFromMill(widget.coin.date),
                  style: TextStyle(
                    color: DColor.textColorSecondary,
                    fontSize:14,
                  ),
                ),
              ],
            ),
          ),
          Align(
            alignment: Alignment.centerRight,
            child: Text(
              '+${widget.coin.coinCount}',
              style: TextStyle(
                color: DColor.textColorPrimary,
                fontSize:20,
              ),
            ),
          )
        ],
      ),
    );
  }

}