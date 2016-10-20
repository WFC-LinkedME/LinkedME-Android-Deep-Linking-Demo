# LinkedME-Android-Deep-Linking-Demo


##1 LinkedME-Android-Deep-Linking-Demo工程简述

### 1.1 功能描述 

LinkedME-Android-Deep-Linking-Demo工程，主要功能有4个方面：

功能名称 | 含义 
:------------: | :------------- 
应用方 | LinkedME的使用方和合作伙伴
产品简介 | LinkedME产品的简述
产品详述 | LinkedME产品的详细描述
Demo实例 | 主要演示生成深度链接

### 1.2 代码描述

LinkedME-Android-Deep-Linking-Demo工程，代码描述如下表格所示：

文件名称 | 含义 
:------------: | :------------- 
LinkedME-Demo | 集成LinkedME-Android-Deep-Linking-SDK的Demo示例工程
libs | 该文件夹下面放着LinkedME-Android-Deep-Linking-SDK-V1.0.1.jar包
com.wfc.linkedme | 该包下面放着LinkedME-Demo的Activity内容
umeng | 标示社会化分享出来的链接是携带深度链接的H5页面链接
AndroidManifest.xml | 注意AndroidManifest文件的配置文件

![LinkedME-Android-Deep-Linking-Demo工程详解](http://o8nck2exg.bkt.clouddn.com/LinkedME-Android-Deep-Linking.png)


##2 注册用户
注册用户，并登陆系统

![image](http://o8fx2z8ev.bkt.clouddn.com/lkme/register1.png)

##3 创建App
###3.1 创建app

![image](http://o8fx2z8ev.bkt.clouddn.com/lkme/create_app11.png)
![image](http://o8fx2z8ev.bkt.clouddn.com/lkme/create_app22.png)


###3.2 设置App

登录官网网站，进入“设置”->“链接”标签下面，设置Android深度链接相关的参数。

项目 | 选项 | 含义
:------------ | :-------------: | ------------
URI scheme | 必填  | App的URI Scheme
Package Name | 必填  | App包名
应用商店地址 | 必填  | 如果多个应用商店的地址，请用“分号”填写；例如：
开启AppLinks | 选填  | 填写SHA256验证码

- 如果没有Android应用，Directed URL框里填写深度链接默认跳转地址；
	
	![image](http://o8fx2z8ev.bkt.clouddn.com/lkme/set_app_android11.png)
	
- 如果有Android应用，勾选有Android应用的框，并填写App的URI Scheme(不要加“://”)和Package Name；如果App在应用商店上线了，请勾选“应用商店”，并填写App在应用商店的地址；
	
	![image](http://o8fx2z8ev.bkt.clouddn.com/lkme/set_app_android2.png)
	
- 如果App没有在应用商店上线，请勾选“自定义”，并填写App的apk文件的下载地址；
	
	![image](http://o8fx2z8ev.bkt.clouddn.com/lkme/set_app_android3.png)
	
- 如果App支持App Links，请勾选“开启App Links”,并填写SHA256验证码。
	
	![image](http://o8fx2z8ev.bkt.clouddn.com/lkme/set_app_android4.png)


##4 代码集成
参考[wiki快速集成](https://github.com/WFC-LinkedME/LinkedME-Android-Deep-Linking-Demo/wiki/Android%E5%BF%AB%E9%80%9F%E9%9B%86%E6%88%90)或者[官网集成文档](https://www.linkedme.cc/docs/page4.html#link1)

