# LinkedME Android SDK

## `minSdkVersion` >= 8

## 使用说明

### SDK安装说明

+ 复制`LinkedME-Android-SDK.aar`到项目的`libs`目录下
+ 修改构建脚本`build.gradle`内容
    + 修改`repositories`配置
    ```
    repositories {
        flatDir {
            dirs 'libs'
        }
    }
    ```
    + 新增`dependencies`配置
    ```
    dependencies {
        compile(name: 'LinkedMe-Android-SDK', ext: 'aar')
    }
    ```
+ 修改Android程序配置文件`AndroidManifest.xml`内容
    + 新增`LinkedME`key
    ```
    <!-- LinkedME生产通道 -->
    <meta-data
                android:name="linkedme.sdk.key"
                android:value="46ed6c4bbdbb5c59ed0dd835f7c8868a" />
    <!-- LinkedME测试通道 -->            
    <meta-data
                android:name="linkedme.sdk.key.test"
                android:value="46ed6c4bbdbb5c59ed0dd835f7c8868a" />
    ```
    + 配置Debug模式
    ```
     <meta-data
                android:name="linkedme.sdk.TestMode"
                android:value="false" />
    ```
    + 确认`LinkedME`SDK权限
    ```
     <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
     <uses-permission android:name="android.permission.INTERNET" />
    ```
+ 修改`Application`文件
    + 确认`AndroidManifest.xml`中使用的`Application`,如没有自定义可是直接使用`com.microquation.linkedme.android.referral.LMApp`
    + 如使用自定义`Application`,请于`onCreate()`中调用`LinkedME#initialize`完成初始化
    ```
    @Override
        public void onCreate() {
            super.onCreate();
            LinkedME.initialize(this);
        }
    ```
+ 以上步骤完成后,SDK将可以正确工作.如果熟悉gradle及相关配置可执行修改

### SDK使用说明
 
+ 初始化LinkedME核心组件
    + 于入口`Activity`对应`onStart()`或者`onResume()`中调用`LinkedME.getInstance()`即可完成初始化工作.如使用低于`API 14`版本请使用`LinkedME.getInstance(Context)`(详细内容请移步`AutoSession`相关章节,使用说明章节将仅以`API 14`以上版本介绍`LinkedME`主要功能)
    + 为入口`Activity`配置相应的`IntentFilter`,并配置`launchMode`为`singleTask`
    ```
                <intent-filter>
                    <action android:name="android.intent.action.VIEW" />
                    <category android:name="android.intent.category.DEFAULT" />
                    <category android:name="android.intent.category.BROWSABLE" />
                    <data android:scheme="lkmedemo" />
                </intent-filter>
    ```
    
    ```
                <intent-filter>
                    <action android:name="android.intent.action.VIEW" />
    
                    <category android:name="android.intent.category.DEFAULT" />
                    <category android:name="android.intent.category.BROWSABLE" />
    
                    <data
                        android:host="lkme.cc"
                        android:pathPrefix="/obC"
                        android:scheme="https" />
                    <data
                        android:host="lkme.cc"
                        android:pathPrefix="/obC"
                        android:scheme="http" />
                </intent-filter>
    
    
                <intent-filter android:autoVerify="true">
                    <action android:name="android.intent.action.VIEW" />
    
                    <category android:name="android.intent.category.DEFAULT" />
                    <category android:name="android.intent.category.BROWSABLE" />
    
                    <data
                        android:host="lkme.cc"
                        android:pathPrefix="/obC"
                        android:scheme="https" />
                    <data
                        android:host="lkme.cc"
                        android:pathPrefix="/obC"
                        android:scheme="http" />
    
    
                </intent-filter>
    ```

+ 生成深度链接
    + 构造`LinkProperties` `LKMEUniversalObject`对象,并填写对应参数
    ```
    LinkProperties properties = new LinkProperties();
    //请根据需要配置参数
    properties.setIOSKeyControlParameter("apps");//ios自动路由匹配参数(非常重要)
    properties.setAndroidPathControlParameter("apps");//android自动路由匹配参数(非常重要)
    ```
    ```
    LKMEUniversalObject universalObject = new LKMEUniversalObject();
    //请根据需要配置参数
    ```
    + 调用`LKMEUniversalObject.generateShortUrl`生成深度链接,可以在`LKMELinkCreateListener`获得对应的深度链接
    ```
    universalObject.generateShortUrl(this, properties, (url, linkedMeError) -> {
            if (linkedMeError == null) {
                Log.info("LinkeME",url);
            }
    });
    ```

+ 获取深度链接参数
    + 为对应的`Activity`配置自动路由参数
    ```
    <activity android:name=".activity.AppsActivity">
        <meta-data
            android:name="android.support.PARENT_ACTIVITY"
            android:value=".activity.MainActivity" />
        <!-- 此处即为上一章节提到的AndroidPathControl参数 -->
        <meta-data
            android:name="linkedme.sdk.auto_link_path"
            android:value="apps" />
    </activity>
    ```
    + 于对应的`Activity`调用`LinkedME.initSession(LKMEUniversalReferralInitListener,Uri, Activity)`,并注意修改`newIntent()`方法
    ```
    @Override
    public void onStart() {
    super.onStart();
    linkedMe.initSession((linkedMeUniversalObject, linkProperties, linkedMeError) -> {
             //此处你将可以获得生成深度链接时配置的LinkProperties和LKMEUniversalObject对象
             //注意使用前判断LinkedMEError是否存在
            }, getIntent().getData(), this);
    }
    ```
    ```
    @Override
    public void onNewIntent(Intent intent) {
            this.setIntent(intent);
    }
    ```

### SDK发布
目前直接运行`sh ./gradlew clean makeJar`即可,打包位于根目录下libs中
### SDK尚未完成的内容

+ 参数校验
    详见`com.microquation.linkedme.android.network.RemoteInterface.addCommonParams`
+ 测试通道接入
    详见`com.microquation.linkedme.android.referral.PrefHelper.getAPIBaseUrl`
