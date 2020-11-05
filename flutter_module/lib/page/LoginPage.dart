import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_module/config/Color.dart';
import 'package:flutter_module/config/String.dart';
import 'package:flutter_module/model/LoginPageModel.dart';
import 'package:flutter_module/model/ProviderWidget.dart';
import 'package:flutter_module/utils/ToastUtil.dart';

class LoginPage extends StatefulWidget{
  @override
  LoginPageState createState() => LoginPageState();

}

class LoginPageState extends State<LoginPage>{

  TextEditingController userController = new TextEditingController();
  TextEditingController passwordController = new TextEditingController();

  String name;
  String password;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: DColor.bgColorPrimary,
      appBar: AppBar(
        title: Text(DString.login,
          style: TextStyle(
          fontSize: 18,
          color: Colors.black,
        ),),
        backgroundColor: Colors.white,
        leading: IconButton(
          icon: Icon(
            Icons.arrow_back,
            color: Colors.black,
          ),
          onPressed: ()=>Navigator.of(context).pop(),
        ),
        centerTitle: true,
        elevation: 1.5,
        brightness: Brightness.light,
      ),
      body: new ProviderWidget<LoginPageModel>(
        model: LoginPageModel(),
        builder: (context, model, child) {
          return Container(
            margin: EdgeInsets.fromLTRB(50, 60, 50, 0),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                TextField(
                  decoration: new InputDecoration(
                      hintText: '账号'
                  ),
                  onChanged: (val){
                    name = val;
                  },
                ),
                SizedBox(height: 10,),
                TextField(
                  decoration: new InputDecoration(
                      hintText: '密码'
                  ),
                  onChanged: (val){
                    password = val;
                  },
                ),
                SizedBox(height: 10,),
                new RaisedButton(onPressed: (){
                  if(name ==null || name.isEmpty){
                    ToastUtil.showTip(DString.userIsEmpty);
                    return;
                  }
                  if(password ==null || password.isEmpty){
                    ToastUtil.showTip(DString.passwordIsEmpty);
                    return;
                  }
                  model.login(name, password, context);
                },
                child: Text(DString.login,style: TextStyle(fontSize: 20,color: DColor.textColorSecondary),),
                color: DColor.bgColorThird,),
              ],
            ),
          );
        },
      )
    );
  }

}