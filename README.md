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

###4.1 解析深度链接

###4.1.1 设置 linkedme_key

如果你之前已经在LinkedME官网注册了应用，并获取到了 linkedme_key`linkedme_key`，可以继续使用它.

如果你尚未在LinkedME官网注册开发者账号，需要先注册，注册之后登录你的账号，点击添加新应用，填写完应用基本信息后，将进入"设置"页面，此页面即可得到 `linkedme_key`。

###4.1.2 配置AndroidManifest

####4.1.2.1 修改 application及继承类

修改applincation中activity的类，该类需要集成LMApp，Demo中的示例代码如下：

```xml

<application
	android:name=".activity.LinkedMEDemoApp"
	android:configChanges="orientation|keyboard"
	android:allowBackup="true"
	android:icon="@drawable/icon1"
	android:label="@string/app_name"
	android:theme="@style/AppTheme">
```   
LinkedMEDemoApp类需要集成LMApp；示例代码如下所示：

```java
public class LinkedMEDemoApp extends LMApp {
	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
    }

	@Override
	public void onCreate() {
		super.onCreate();
	}
}
```

####4.1.2.2 添加 linkedme_key

```xml

<!--Test and Key-->
	<meta-data android:name="linkedme.sdk.TestMode" android:value="true" />
	<meta-data android:name="linkedme.sdk.key" 		android:value="linkedme_live_838212907f1a18565f85a63ed2508774" />
	<meta-data android:name="linkedme.sdk.key.test" android:value="linkedme_test_838212907f1a18565f85a63ed2508774" />
```
####4.1.2.3 添加 activity

```xml

<activity
	android:name=".activity.MainActivity"
	android:configChanges="orientation|keyboard"
	android:screenOrientation="portrait"
	android:label="@string/app_name">
	
	<intent-filter>
		<action android:name="android.intent.action.MAIN" />
		<category android:name="android.intent.category.LAUNCHER" />
	</intent-filter>

	<!-- URI Scheme方式 -->
	<intent-filter>
		<data android:scheme="linkedmedemo" />
		<action android:name="android.intent.action.VIEW" />
		<category android:name="android.intent.category.DEFAULT" />		<category android:name="android.intent.category.BROWSABLE" />
	</intent-filter>

	<!-- APP Links方式 -->
	<intent-filter android:autoVerify="true">
		<action android:name="android.intent.action.VIEW" />
		<category android:name="android.intent.category.DEFAULT" />
		<category android:name="android.intent.category.BROWSABLE" />
		<data android:host="lkme.cc" android:pathPrefix="/lbC" android:scheme="https" />
		<data android:host="lkme.cc" android:pathPrefix="/lbC" android:scheme="http" />
	</intent-filter>
	
</activity>
```

####4.1.2.4 添加访问权限
```xml
 	<!--需要开启的权限-->
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.BLUETOOTH" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <uses-permission android:name="android.permission.READ_LOGS" />
    <uses-permission android:name="android.permission.CALL_PHONE" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.GET_TASKS" />
    <uses-permission android:name="android.permission.SET_DEBUG_APP" />
    <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
    <uses-permission android:name="android.permission.GET_ACCOUNTS" />
    <uses-permission android:name="android.permission.USE_CREDENTIALS" />
    <uses-permission android:name="android.permission.MANAGE_ACCOUNTS" />
```


###4.1.2 获取深度链接跳转参数

在MainAcivity中的onStart()中添加如下代码，初始化LinkedME实例。

```java

SimpleInitListener listener = new SimpleInitListener() {
	@Override
	public void onSimpleInitFinished(LMUniversalObject lmUniversalObject, LinkProperties linkProperties, LMError error) {
	if (error != null) {
		Log.i("LinkedME-Demo", "LinkedME init failed. " + error.getMessage());
	} else {
		Log.i("LinkedME-Demo", "LinkedME init complete!");
		if (lmUniversalObject != null) {
			Log.i("LinkedME-Demo", "title " + lmUniversalObject.getTitle());
			Log.i("LinkedME-Demo", "CanonicalIdentifier " + lmUniversalObject.getCanonicalIdentifier());
			Log.i("ContentMetaData", "metadata " + lmUniversalObject.getMetadata());

			//获取深度链接跳转参数后，跳转到具体的页面；
			HashMap<String, String> hashMap = lmUniversalObject.getMetadata();
			String view = hashMap.get("View").toString();
			if (view.equals("Partners")) {
				Intent intent = AppsActivity.newIntent(MainActivity.this);
				startActivity(intent);
			} else if (view.equals("Features")) {
				startActivity(FeaturesActivity.newIntent(MainActivity.this));
			} else if (view.equals("Demo")) {
				startActivity(DemoActivity.newIntent(MainActivity.this));
			} else if (view.equals("Summary")) {
				startActivity(SummaryActivity.newIntent(MainActivity.this));
			}
		}

		if (linkProperties != null) {
			Log.i("LinkedME-Demo", "Channel " + linkProperties.getChannel());
			Log.i("LinkedME-Demo", "control params " + linkProperties.getControlParams());
		}
	}
}
};
```
    

###4.2 生成深度链接
```java

/**创建深度链接*/
LinkProperties properties = new LinkProperties();
properties.setChannel("Wechat");
properties.setFeature("sharing");
properties.addTag("LinkedME");
properties.addTag("test");
properties.setStage("Test");
properties.addControlParameter("$desktop_url", "http://www.linkedme.cc");
properties.addControlParameter("$ios_url", "http://www.linkeme.cc");
properties.setIOSKeyControlParameter("Partners");
properties.setAndroidPathControlParameter("Partners");
LMUniversalObject universalObject = new LMUniversalObject();
universalObject.setTitle("Partners");
universalObject.setContentDescription("LinkedME测试内容");
universalObject.addContentMetadata("View", "Partners");
universalObject.addContentMetadata("LinkedME", "Demo");
	
//异步或同步方式生成深度链接
universalObject.generateShortUrl(AppsActivity.this, properties, new LMLinkCreateListener() {
	@Override
	public void onLinkCreate(String url, LMError error) {
	//该url是LinkedME生成的深度链接，在此使用该深度链接即可；
	}
});
```

``` java
 /**创建深度链接*/
                    LinkProperties properties = new LinkProperties();
                    properties.setChannel("");  //微信、微博、QQ
                    properties.setFeature("Share");
                    properties.addTag("LinkedME");
                    properties.addTag("Partner");
                    properties.setStage("Live");
                    properties.addControlParameter("LinkedME", "Demo");
                    properties.addControlParameter("View", "Partner");
                    LMUniversalObject universalObject = new LMUniversalObject();
                    universalObject.setTitle("Partner");

                    // Async Link creation example
                    universalObject.generateShortUrl(AppsActivity.this, properties, new LMLinkCreateListener() {
                        @Override
                        public void onLinkCreate(String url, LMError error) {
                            UMImage image = new UMImage(AppsActivity.this, "http://api.linkedme.cc/homepage2.jpg");
                            /**友盟分享化分享，分享的链接不单单是H5链接，而是携带深度链接的H5链接*/
                            new ShareAction(AppsActivity.this).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA)
                                    .withText("LinkedME产品已经被众多移动应用垂青, 比如Uber、滴滴、36Kr、小饭桌、每天、道口贷等等, 更多应用正在集成中...;")
                                    .withTitle("LinkedME应用方")
                                    .withMedia(image)
                                    .withTargetUrl(LIVE_H5_URL + url)
                                    .setCallback(umShareListener)
                                    .open();
                        }
                    });
                    

 ```         
###4.3 设置Debug模式
在AndroidManifest.xml文件中，进行相应的配置；

```xml
<!--Test and Key-->
	<meta-data android:name="linkedme.sdk.TestMode" android:value="true" />
	<meta-data android:name="linkedme.sdk.key" 		android:value="linkedme_live_838212907f1a18565f85a63ed2508774" />
	<meta-data android:name="linkedme.sdk.key.test" android:value="linkedme_test_838212907f1a18565f85a63ed2508774" />
```

