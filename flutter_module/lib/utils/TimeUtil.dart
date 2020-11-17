
class TimeUtil{

  static String getTimeFromMill(int timestamp){
    var date = new DateTime.fromMillisecondsSinceEpoch(timestamp);
    return dateToString(date);
  }

  static String dateToString(DateTime date) {
    String y = fourDigits(date.year);
    String m = twoDigits(date.month);
    String d = twoDigits(date.day);
    String h = twoDigits(date.hour);
    String min = twoDigits(date.minute);
    String sec = twoDigits(date.second);
    return "$y-$m-$d $h:$min:$sec";
  }

  static String fourDigits(int n) {
    int absN = n.abs();
    String sign = n < 0 ? "-" : "";
    if (absN >= 1000) return "$n";
    if (absN >= 100) return "${sign}0$absN";
    if (absN >= 10) return "${sign}00$absN";
    return "${sign}000$absN";
  }

  static String twoDigits(int n) {
    if (n >= 10) return "${n}";
    return "0${n}";
  }

}