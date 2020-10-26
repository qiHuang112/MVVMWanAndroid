class ErrorBean{
  String _errMsg;
  int _errCode;

  int get errCode => _errCode;
  String get errMsg => _errMsg;

  ErrorBean(
      String errMsg,
      int errCode
      ){
    this._errCode = errCode;
    this._errMsg = errMsg;
  }
}