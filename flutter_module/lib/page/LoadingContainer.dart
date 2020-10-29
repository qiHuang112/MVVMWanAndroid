import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_module/config/Color.dart';
import 'package:flutter_module/config/String.dart';

class LoadingContainer extends StatelessWidget {
  final Widget child;
  final bool error;
  final bool loading;
  final VoidCallback retry;

  const LoadingContainer(
      {Key key,
      @required this.loading,
      this.error = false,
      @required this.retry,
      @required this.child})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return !loading ? error ? _errorView : child : _loadingView;
  }

  Widget get _errorView {
    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          new Text(
            DString.error,
            style: TextStyle(color: DColor.textColorThird),
          ),
          Padding(
            padding: EdgeInsets.only(top: 10),
            child: OutlineButton(
              onPressed: () => retry.call(),
              child: Text(DString.reload),
              highlightColor: DColor.bgColorThird,
              highlightedBorderColor: Colors.black12,
            ),
          )
        ],
      ),
    );
  }

  Widget get _loadingView {
    return Center(
      child: CircularProgressIndicator(),
    );
  }
}
