## Android
### 获取LinkedME_Key
`新用户`：在官网网站注册账号，注册后创建应用，在后台导航栏“`设置`”中查看LinkedME Key。
`老用户`：已经在官网网站注册账号，直接创建应用（可以创建多个应用），直接到导航栏“`设置`”中查看LinkedME Key。
### 获取LinkedME Android SDK支持包
前往并下载[Demo工程](https://github.com/WFC-LinkedME/LinkedME-Android-Deep-Linking-Demo)libs目录下的[LinkedME-Android-Deep-Linking-SDK-V1.0.\*.jar](https://github.com/WFC-LinkedME/LinkedME-Android-Deep-Linking-Demo/blob/master/LinkedME-Demo/libs)支持包。
### 导入LinkedME Android SDK支持包
支持两种方式添加支持库引用：
1.下载jar包并导入
将下载的`LinkedME-Android-Deep-Linking-SDK-V1.0.\*.jar`文件添加至项目`libs`文件夹下，并添加到项目Module层的`build.gradle`依赖中,如下所示:
```
dependencies {
    //注意修改jar包名,与下载的jar包名称一致
    compile files('libs/LinkedME-Android-Deep-Linking-SDK-V1.0.14.jar')
 }
```
2.添加maven仓库引用导入
- 在工程根节点的`build.gradle`中添加`maven`仓库地址，如下所示
```groovy
buildscript {
    repositories {
        jcenter()
        maven {
            url 'https://dl.bintray.com/linkedme2016/lkme-deeplinks'

        }
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:2.2.3'
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {

    repositories {
        jcenter()
        maven {
            url 'https://dl.bintray.com/linkedme2016/lkme-deeplinks'
        }
    }
}

```
- 在项目Module层的`build.gradle`中添加依赖，如下所示：
```groovy
dependencies {
compile fileTree(include: ['*.jar'], dir: 'libs')
compile "cc.linkedme.deeplinks:link-page:1.0.14"
}
```
### 配置AndroidManifest.xml
#### - 添加LinkedME Key配置
将下面配置中的`<meta-data>`标签复制到`<application>`节点之下
> android:value值替换为新创建应用的真实LinkedME Key值

```xml
<application
        android:name=".activity.LinkedMEDemoApp">
        // ...
       <!-- LinkedME官网注册应用后,从"设置"页面获取该Key -->
        <meta-data
            android:name="linkedme.sdk.key"
            android:value="替换为后台设置页面中的LinkedME Key" />
        // ...
</application>
```
#### - 添加访问权限
集成LinkedME SDK需要开启相关权限，将如下权限添加至配置文件中

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
  <!--LinkedME SDK 需要开启的权限-->
  <!-- 联网权限 -->
  <uses-permission android:name="android.permission.INTERNET" />
  <!-- 获取电话信息，为了获取手机的IMEI号 -->
  <uses-permission android:name="android.permission.READ_PHONE_STATE" />
  <!-- 获取网络状态，是否联网 -->
  <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
  <!-- 获取WiFi状态 -->
  <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
  <!-- 写入外部存储 -->
  <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
  <!-- 获取设备名称 -->
  <uses-permission android:name="android.permission.BLUETOOTH" />
  </manifest>
```
#### - 配置\<intent-filter/>
在工程主页的`Activity`节点下添加如下<intent-filter/>，并为该`Activity`添加`android:launchMode="singleTask"`属性。
> android:scheme值替换为新创建应用的真实URI Scheme值；可在后台“设置”->“链接”中查看Android下的URI Scheme的值
  android:pathPrefix值替换为新创建应用的真实LinkedME App ID值；可在后台“设置”->“概览”中查看LinkedME App ID的值

```xml
    <activity
         android:name=".activity.MainActivity"
         android:launchMode="singleTask">

         <!-- URI Scheme方式 在dashboard配置中,请保持与ios的URI Scheme相同 -->
         <intent-filter>
             <!-- 此处scheme值需要替换为后台设置中的scheme值 -->
             <!-- 可在后台“设置”->“链接”中查看Android下的URI Scheme的值 -->
             <data android:scheme="请填写Dashboard中设置的URI Scheme值" />

             <action android:name="android.intent.action.VIEW" />

             <category android:name="android.intent.category.DEFAULT" />
             <category android:name="android.intent.category.BROWSABLE" />
         </intent-filter>

         <!-- APP Links方式,Android 23版本及以后支持 -->
         <!-- 可在后台“设置”->“概览”中查看LinkedME App ID的值 -->
         <intent-filter android:autoVerify="true">
             <action android:name="android.intent.action.VIEW" />

             <category android:name="android.intent.category.DEFAULT" />
             <category android:name="android.intent.category.BROWSABLE" />

             <!-- 以下pathPrefix值需要替换为后台设置中 App ID 的值,注意包含“/”-->
             <data
                 android:host="lkme.cc"
                 <!-- 将AfC替换为后台设置中 App ID 的值，注意包含“/” -->
                 android:pathPrefix="/AfC"
                 android:scheme="https" />
             <data
                 android:host="lkme.cc"
                 <!-- 将AfC替换为后台设置中 App ID 的值，注意包含“/” -->
                 android:pathPrefix="/AfC"
                 android:scheme="http" />
         </intent-filter>
     </activity>
```
### *初始化LinkedME实例
在自定义`Application`类中的`onCreate()`方法中，添加初始化LinkedME实例的代码。
> 自定义Application需要在AndroidManifest.xml配置文件中的<application\/>中引用

LinkedME-Android-Deep-Linking-Demo示例代码如下所示：
```java
public class LinkedMEDemoApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        try {
           if (BuildConfig.DEBUG) {
                //设置debug模式下打印LinkedME日志
                LinkedME.getInstance(this).setDebug();
            } else {
                LinkedME.getInstance(this);
            }
            // 设置是否开启自动跳转指定页面，默认为true
            // 若在此处设置为false，请务必在配置Uri scheme的Activity页面的onResume()方法中，
            // 重新设置为true，否则将禁止开启自动跳转指定页面功能
            // 示例：
            // @Override
            // public class MainActivity extends AppCompatActivity {
            // ...
            // @Override
            // protected void onResume() {
            //    super.onResume();
            //    LinkedME.getInstance().setImmediate(true);
            //   }
            // ...
            //  }
            
            //建议初始时设置为false，在需要跳转的地方设置为true
            LinkedME.getInstance().setImmediate(false);
       } catch (Exception e) {
           e.printStackTrace();
       }
    }
    }
```
若应用需要向前兼容到Android 4.0以下版本，请在基类（如：BaseActivity）中添加如下代码以便管理Session：
```
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //兼容14之前的版本需要在基类中添加以下代码
        LinkedME.getInstance().onLMCreated(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        //兼容14之前的版本需要在基类中添加以下代码
        LinkedME.getInstance().onLMStarted(this);
        super.onStart();
    }

    @Override
    protected void onResume() {
        //兼容14之前的版本需要在基类中添加以下代码
        LinkedME.getInstance().onLMResumed(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        //兼容14之前的版本需要在基类中添加以下代码
        LinkedME.getInstance().onLMPaused(this);
        super.onPause();
    }

    @Override
    public void onStop() {
        //兼容14之前的版本需要在基类中添加以下代码
        LinkedME.getInstance().onLMStoped(this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        //兼容14之前的版本需要在基类中添加以下代码
        LinkedME.getInstance().onLMDestoryed(this);
        super.onDestroy();
    }
}

```
### *配置URI Scheme唤起的Activity页面(例如：MainActivity)
若在自定义Application中初始化LinkedME时`未禁用`自动跳转功能，则只需添加以下代码：
```java
    // 添加此处目的是针对后台APP通过uri scheme唤起的情况，
    // 注意：即使不区分用户是否登录也需要添加此设置，也可以添加到基类中
    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
    }
```
若在自定义Application中初始化LinkedME时`禁用`自动跳转功能，则还需要在onResume()中方法调用LinkedME.getInstance().setImmediate(true);  方法，开启自动跳转功能，从而控制从主页面跳转到指定页面。
示例如下：
```
    @Override
    protected void onResume() {
        super.onResume();
        LinkedME.getInstance().setImmediate(true);
    }
```
### *解析深度链接

新建一个Activity(例如：MiddleActivity)，用于接收SDK回传的参数，并根据业务要求进行跳转

- 首先，创建MiddleActivity，并在AndroidManifest.xml中配置MiddleActivity

  1. 添加属性：android:noHistory="true"，目的是不显现该页面也不让其放入栈中，只进行页面逻辑跳转；
  2. 添加<meta-data/>配置，\<meta-data android:name="linkedme.sdk.auto_link_keys" android:value="linkedme"/>，目的是为了让SDK可以将参数回传给该页面(请不要更改value值！)。

LinkedME-Android-Deep-Linking-Demo的MiddleActivity在AndroidManifest.xml中的示例代码如下所示：
```xml
   <activity
      android:name=".activity.MiddleActivity"
      android:screenOrientation="portrait"
      android:noHistory="true">
      <meta-data android:name="linkedme.sdk.auto_link_keys" android:value="linkedme"/>
  </activity>
```
- 其次，在MiddleActivity的onCreate()方法中编写跳转逻辑

  1. 通过getIntent().getParcelableExtra(LinkedME.LM_LINKPROPERTIES)获取跳转参数

LinkedME-Android-Deep-Linking-Demo的MiddleActivity示例代码如下所示：
```java

public class MiddleActivity extends AppCompatActivity {
    // ...
/**
     * <p>解析深度链获取跳转参数，开发者自己实现参数相对应的页面内容</p>
     * <p>通过LinkProperties对象调用getControlParams方法获取自定义参数的ArrayMap对象,
     * 通过创建的自定义key获取相应的值,用于数据处理。</p>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null) {
            //获取与深度链接相关的值
            LinkProperties linkProperties = getIntent().getParcelableExtra(LinkedME.LM_LINKPROPERTIES);
            if (linkProperties != null) {
                Log.i("LinkedME-Demo", "Channel " + linkProperties.getChannel());
                Log.i("LinkedME-Demo", "control params " + linkProperties.getControlParams());
                Log.i("LinkedME-Demo", "link(深度链接) " + linkProperties.getLMLink());
                Log.i("LinkedME-Demo", "是否为新安装 " + linkProperties.isLMNewUser());
                //获取自定义参数封装成的ArrayMap对象,参数键值对由集成方定义
                ArrayMap<String, String> arrayMap = linkProperties.getControlParams();
                //根据key获取传入的参数的值,该key关键字View可为任意值,由集成方规定,请与web端商议,一致即可
                String view = arrayMap.get("View");
                if (view != null) {
                    //根据不同的参数进行页面跳转,detail代表具体跳转到哪个页面,此处语义指详情页
                    if (view.equals("detail")) {
                        //DetailActivity类不存在,此处语义指要跳转的详情页,参数也是由上面的ArrayMap对象指定
                        Intent intent = new intent(BaseActivity.this, DetailActivity.class);
                        startActivity(intent);
                    }
                }
            }
        }
        finish();
    }
    // ...
    }
```
### 创建深度链接

> 如果web端集成了web sdk,则无需客户端创建深度链接,此处可忽略

LinkedME SDK创建深度链接，必须传入链接的参数，用于区分App内不同的页面。比如唯品会商品详情页面的唯一标识为productId=230453452
```

------------------------------------------------------------------------------
参数名称   | 含义  |	功能
------------------------------------------------------------------------------
Channel	  | 渠道  | 表示深度链接的渠道，方便统计分析和追踪，例如微信、微博，百度等等；
------------------------------------------------------------------------------
Feature   | 特点  | 表示深度链接的特点，例如邀请，分享等等；
------------------------------------------------------------------------------
Tags	  | 标签	 | 表示深度链接的标签特性，自定义任何值；
------------------------------------------------------------------------------
Compagin  | 活动	 | 表示深度链接的活动，例如618，双11活动；
------------------------------------------------------------------------------
Stage     | 阶段 | 表示深度链接的阶段特性，比如第一版产品发布，第二版本测试等等；
------------------------------------------------------------------------------
```
```java
public class ShareActivity extends BaseActivity {

    public void share() {
/**创建深度链接*/
        //web服务器无法创建深度链接时,客户端可选择创建
        //深度链接属性设置
        LinkProperties properties = new LinkProperties();
        //渠道
        properties.setChannel("");  //微信、微博、QQ
        //功能
        properties.setFeature("Share");
        //标签
        properties.addTag("LinkedME");
        properties.addTag("Demo");
        //阶段
        properties.setStage("Live");
        //设置该h5_url目的是为了iOS点击右上角lkme.cc时跳转的地址，一般设置为当前分享页面的地址
        //客户端创建深度链接请设置该字段
        properties.setH5Url("https://linkedme.cc/h5/feature");
        //自定义参数,用于在深度链接跳转后获取该数据
        properties.addControlParameter("LinkedME", "Demo");
        properties.addControlParameter("View", loadUrl);
        LMUniversalObject universalObject = new LMUniversalObject();
        universalObject.setTitle(title);
        // 异步生成深度链接
        universalObject.generateShortUrl(ShareActivity.this, properties, new LMLinkCreateListener() {
            //https://www.lkme.cc/AfC/idFsW02l7
            @Override
            public void onLinkCreate(final String url, LMError error) {
                //deepLinkUrl创建返回的深度链接
                final UMImage image = new UMImage(ShareActivity.this, "https://www.linkedme.cc/homepage2.jpg");
                /**友盟分享化分享，分享的链接不单单是H5链接，而是携带深度链接的H5链接*/
                new ShareAction(ShareActivity.this).setDisplayList(SHARE_MEDIA.WEIXIN)
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {

                        if (share_media == SHARE_MEDIA.WEIXIN) {
                            //微信
                            new ShareAction(ShareActivity.this)
                                    .setPlatform(share_media)
                                    .withText(shareContent)
                                    .withTitle("LinkedME" + title)
                                    .withMedia(image)
                                    //拼接深度链接,客户端将生成的深度链接值拼接到链接后,web端需要截取该url链接并放置到"打开APP"按钮下
                                    .withTargetUrl(H5_URL + url_path + "?linkedme=" + url)
                                    .setCallback(umShareListener)
                                    .share();
                        }
                    }
                        }).open();
            }
        });
    }
}
```