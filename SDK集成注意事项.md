
## 1. 应用跳转到非集成SDK代码的其他页面，通过应用宝通道无法跳转到详情页 ##
**问题描述：**
应用跳转到非集成SDK代码的其他`Activity`后，通过点击深度链接走应用宝通道无法跳转到详情页，返回到集成SDK代码的`Activity`后又会跳转到详情页。

**解决方案：**
已解决，提供两套解决方案，需集成方根据自身需求处理LinkedME集成逻辑，建议采取方案一，该方案对集成方现有`Activity`生命周期几乎没有影响。

**方案一**:在公共`Activity`基类中集成SDK代码

为了让从后台唤起到前台的App能在任何页面处理deep linking并跳转到详情页，需要在公共Activity基类中集成SDK代码，其他Activity类不再需要集成SDK代码。App通常会有一个公共的Activity基类，例如`PublicActivity`、`BaseActivity`等，所有Activity均继承自该公共Activity基类。代码示例如下：

```java
public class BaseActivity extends AppCompatActivity {

       private static final String TAG = "BaseActivity";
       private LinkedME linkedME;


       @Override
       protected void onStart() {
           super.onStart();
           Log.i(TAG, "onStart: " + this.getClass().getSimpleName());
           try {
               //如果消息未处理则会初始化initSession，因此不会每次都去处理数据，不会影响应用原有性能问题
               if (!LinkedME.getInstance().isHandleStatus()) {
                   Log.i(TAG, "LinkedME +++++++ initSession... " + this.getClass().getSimpleName());
                   //初始化LinkedME实例
                   linkedME = LinkedME.getInstance();
                   //初始化Session，获取Intent内容及跳转参数
                   linkedME.initSession(simpleInitListener, this.getIntent().getData(), this);
               }
           } catch (Exception e) {
               e.printStackTrace();
           }
       }

       /**
        * <p>解析深度链获取跳转参数，开发者自己实现参数相对应的页面内容</p>
        * <p>通过LinkProperties对象调用getControlParams方法获取自定义参数的HashMap对象,
        * 通过创建的自定义key获取相应的值,用于数据处理。</p>
        */
       //。
       LMSimpleInitListener simpleInitListener = new LMSimpleInitListener() {
           @Override
           public void onSimpleInitFinished(@Nullable LMUniversalObject lmUniversalObject, @Nullable LinkProperties linkProperties, @Nullable LMError error) {
               try {
                   Log.i(TAG, "开始处理deep linking数据... " + this.getClass().getSimpleName());
                   if (error != null) {
                       Log.i("LinkedME-Demo", "LinkedME初始化失败. " + error.getMessage());
                   } else {

                       //LinkedME SDK初始化成功，获取跳转参数，具体跳转参数在LinkProperties中，和创建深度链接时设置的参数相同；
                       Log.i("LinkedME-Demo", "LinkedME初始化完成");

                       if (linkProperties != null) {
                           Log.i("LinkedME-Demo", "Channel " + linkProperties.getChannel());
                           Log.i("LinkedME-Demo", "control params " + linkProperties.getControlParams());

                           //获取自定义参数封装成的hashmap对象
                           HashMap<String, String> hashMap = linkProperties.getControlParams();
                           Log.i(TAG, "onSimpleInitFinished: " + hashMap.toString());
                           //获取传入的参数
                           String view = hashMap.get("View");
                           if (view != null) {
                               if (view.equals("post")) {
                                   Intent intent = new Intent(BaseActivity.this, SecondActivity.class);
                                   intent.putExtra("id", hashMap.get("id"));
                                   startActivity(intent);
                               }

                           }
                       }

                       if (lmUniversalObject != null) {
                           Log.i("LinkedME-Demo", "title " + lmUniversalObject.getTitle());
                           Log.i("LinkedME-Demo", "control " + linkProperties.getControlParams());
                           Log.i("ContentMetaData", "metadata " + lmUniversalObject.getMetadata());
                       }
                   }
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }
       };


       @Override
       public void onStop() {
           super.onStop();
           Log.i(TAG, "onStop: " + this.getClass().getSimpleName());
           if (linkedME != null){
               linkedME.closeSession(new LMReferralCloseListener() {
                   @Override
                   public void onCloseFinish() {
                   }
               });
           }
       }

       @Override
       protected void onNewIntent(Intent intent) {
           Log.i(TAG, "onNewIntent: " + this.getClass().getSimpleName());
           simpleInitListener.reset();
           setIntent(intent);
       }
       }
  ```
所有Activity类均继承自`BaseActivity`基类，例如：

```java
public class MainActivity extends BaseActivity {

}
```
其他配置不变，请参照[LinkedME Demo](https://github.com/WFC-LinkedME/LinkedME-Android-Deep-Linking-Demo)


**方案二**：在**启动**`Activity`中集成SDK，同时设置启动模式为`singleTask`

在启动Activity中集成SDK，同时设置启动模式为singleTask，这会在Activity退到后台再进入到前台后，始终展现的是启动Activity，因此每次都可以调用deep liking来进行页面的跳转。配置代码如下：

```xml
<activity
            android:name=".activity.MainActivity"
            android:label="@string/app_name"
            android:launchMode="singleTask">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>

            <!-- URI Scheme方式 -->
            <intent-filter>
                <data android:scheme="lkmedemo" />

                <action android:name="android.intent.action.VIEW" />

                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="android.intent.category.BROWSABLE" />
            </intent-filter>

            <!-- APP Links方式,Android 23版本及以后支持 -->
            <intent-filter android:autoVerify="true">
                <action android:name="android.intent.action.VIEW" />

                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="android.intent.category.BROWSABLE" />

                <data
                    android:host="lkme.cc"
                    android:pathPrefix="/AfC"
                    android:scheme="https" />
                <data
                    android:host="lkme.cc"
                    android:pathPrefix="/AfC"
                    android:scheme="http" />
                <data
                    android:host="www.lkme.cc"
                    android:pathPrefix="/AfC"
                    android:scheme="https" />
                <data
                    android:host="www.lkme.cc"
                    android:pathPrefix="/AfC"
                    android:scheme="http" />
            </intent-filter>
        </activity>
```

在MainActivity中集成SDK代码，其他配置不变，请参照[LinkedME Demo](https://github.com/WFC-LinkedME/LinkedME-Android-Deep-Linking-Demo)

> **注意！！**方案二会产生一个问题：因为是在**启动**`Activity`中设置`singleTask`,页面加载后不`finish()`掉该Activity,则当点击到其他页面后，按home键将应用退到后台，通过点击桌面上的图标打开应用，其实用户希望的是展现最后一次点击的页面，但是方案二会导致系统直接将`launchmode=singleTask`的启动Activity之上的所有的Activity全部弹出并销毁掉，所以建议采用**方案一**。

