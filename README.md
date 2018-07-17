# AndroidUnitTest



单元测试是应用程序测试策略中的基本测试，通过对代码进行单元测试，可以轻松地验证单个单元的逻辑是否正确，在每次构建之后运行单元测试，可以帮助您快速捕获和修复因代码更改（重构、优化等）带来的回归问题。本文主要聊聊Android中的单元测试，主要内容如下：

1. 单元测试的目的以及测试内容
2. Android中的单元测试分类
3. JUnit注解
4. 本地单元测试
5. 仪器化单元测试
6. 常用的单元测试开源库
7. 实践经验
8. 其他

# 一、单元测试的目的以及测试内容

为什么要进行单元测试？

- 提高稳定性，能够明确地了解是否正确的完成开发；
- 快速反馈bug，跑一遍单元测试用例，定位bug；
- 在开发周期中尽早通过单元测试检查bug，最小化技术债，越往后可能修复bug的代价会越大，严重的情况下会影响项目进度；
- 为代码重构提供安全保障，在优化代码时不用担心回归问题，在重构后跑一遍测试用例，没通过说明重构可能是有问题的，更加易于维护。

单元测试要测什么？

- 列出想要测试覆盖的正常、异常情况，进行测试验证;
- 性能测试，例如某个算法的耗时等等。


# 二、单元测试的分类

1. 本地测试(Local tests): 只在本地机器JVM上运行，以最小化执行时间，这种单元测试不依赖于Android框架，或者即使有依赖，也很方便使用模拟框架来模拟依赖，以达到隔离Android依赖的目的，模拟框架如google推荐的[Mockito][1]；

2. 仪器化测试(Instrumented tests): 在真机或模拟器上运行的单元测试，由于需要跑到设备上，比较慢，这些测试可以访问仪器（Android系统）信息，比如被测应用程序的上下文，一般地，依赖不太方便通过模拟框架模拟时采用这种方式。

# 三、JUnit 注解
了解一些JUnit注解，有助于更好理解后续的内容。

|Annotation|描述|
|--|--|
|@Test public void method()	|定义所在方法为单元测试方法|
|@Test (expected = Exception.class) public void method()	|测试方法若没有抛出Annotation中的Exception类型(子类也可以)->失败|
|@Test(timeout=100) public void method()|性能测试，如果方法耗时超过100毫秒->失败|
|@Before public void method()|	这个方法在每个测试之前执行，用于准备测试环境(如: 初始化类，读输入流等)，在一个测试类中，每个@Test方法的执行都会触发一次调用。|
|@After public void method()|	这个方法在每个测试之后执行，用于清理测试环境数据，在一个测试类中，每个@Test方法的执行都会触发一次调用。|
|@BeforeClass public static void method()|	这个方法在所有测试开始之前执行一次，用于做一些耗时的初始化工作(如: 连接数据库)，方法必须是static|
|@AfterClass public static void method()	|这个方法在所有测试结束之后执行一次，用于清理数据(如: 断开数据连接)，方法必须是static|
|@Ignore或者@Ignore("太耗时") public void method()|	忽略当前测试方法，一般用于测试方法还没有准备好，或者太耗时之类的|
|@FixMethodOrder(MethodSorters.NAME_ASCENDING) public class TestClass{}|	使得该测试类中的所有测试方法都按照方法名的字母顺序执行，可以指定3个值，分别是DEFAULT、JVM、NAME_ASCENDING|

# 四.本地测试

根据单元有没有外部依赖（如Android依赖、其他单元的依赖），将本地测试分为两类，首先看看没有依赖的情况：

- 添加依赖，google官方推荐
```
dependencies {
    // Required -- JUnit 4 framework
    testImplementation 'junit:junit:4.12'
    // Optional -- Mockito framework（可选，用于模拟一些依赖对象，以达到隔离依赖的效果）
    testImplementation 'org.mockito:mockito-core:2.19.0'
}
```

- 单元测试代码存储位置
事实上，AS已经帮我们创建好了测试代码存储目录。

```
app/src
     ├── androidTestjava (仪器化单元测试、UI测试)
     ├── main/java (业务代码)
     └── test/java  (本地单元测试)
```
- 创建测试类

可以自己手动在相应目录创建测试类，AS也提供了一种快捷方式：选择对应的类->将光标停留在类名上->按下ALT + ENTER->在弹出的弹窗中选择Create Test

![Create Test](https://upload-images.jianshu.io/upload_images/3631399-184be93f9567c4f0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

>Note: 勾选```setUp/@Before```会生成一个带```@Before```注解的```setUp()```空方法，```tearDown/@After```则会生成一个```带@After```的空方法。

```
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class EmailValidatorTest {

    @Test
    public void isValidEmail() {
        assertThat(EmailValidator.isValidEmail("name@email.com"), is(true));
    }
}
```

- 运行测试用例

1. 运行单个测试方法：选中@Test注解或者方法名，右键选择**Run**；
2. 运行一个测试类中的所有测试方法：打开类文件，在类的范围内右键选择**Run**，或者直接选择类文件直接右键**Run**；
3. 运行一个目录下的所有测试类：选择这个目录，右键**Run**。

- 运行前面测试验证邮箱格式的例子，测试结果会在**Run**窗口展示，如下图：

![本地单元测试-通过](https://upload-images.jianshu.io/upload_images/3631399-c21d2da0f7d88b9e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

从结果可以清晰的看出，测试的方法为 ```EmailValidatorTest``` 类中的 ```isValidEmail()```方法，测试状态为passed，耗时12毫秒。

修改一下前面的例子，传入一个非法的邮箱地址：
```
@Test
public void isValidEmail() {
    assertThat(EmailValidator.isValidEmail("#name@email.com"), is(true));
}
```

![本地单元测试-失败](https://upload-images.jianshu.io/upload_images/3631399-bfe8506dd335f1ad.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

测试状态为failed，耗时14毫秒，同时也给出了详细的错误信息：在15行出现了断言错误，错误原因是期望值(Expected)为true，但实际(Actual)结果为false。


也可以通过命令 ```gradlew test``` 来运行所有的测试用例，这种方式可以添加如下配置，输出单元测试过程中各类测试信息：

```
android {
    ...
    testOptions.unitTests.all {
        testLogging {
            events 'passed', 'skipped', 'failed', 'standardOut', 'standardError'
            outputs.upToDateWhen { false }
            showStandardStreams = true
        }
    }
}
```
还是验证邮箱地址格式的例子 ```gradlew test```：

![gradlew test](https://upload-images.jianshu.io/upload_images/3631399-92fe6f1019f445bb.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

在单元测试中通过System.out或者System.err打印的也会输出。


- 通过模拟框架模拟依赖，隔离依赖
前面验证邮件格式的例子，本地JVM虚拟机就能提供足够的运行环境，但如果要测试的单元依赖了Android框架，比如用到了Android中的Context类的一些方法，本地JVM将无法提供这样的环境，这时候模拟框架[Mockito][1]就派上用场了。

- 一个Context#getString(int)的测试用例
```
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MockUnitTest {
    private static final String FAKE_STRING = "AndroidUnitTest";

    @Mock
    Context mMockContext;

    @Test
    public void readStringFromContext_LocalizedString() {
        //模拟方法调用的返回值，隔离对Android系统的依赖
        when(mMockContext.getString(R.string.app_name)).thenReturn(FAKE_STRING);
        assertThat(mMockContext.getString(R.string.app_name), is(FAKE_STRING));

        when(mMockContext.getPackageName()).thenReturn("com.jdqm.androidunittest");
        System.out.println(mMockContext.getPackageName());
    }
}
```

![read string from context](https://upload-images.jianshu.io/upload_images/3631399-6562a503401d7f03.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

通过模拟框架[Mockito][1]，指定调用context.getString(int)方法的返回值，达到了隔离依赖的目的，其中[Mockito][1]使用的是[cglib][2]动态代理技术。


# 五、仪器化测试

在某些情况下，虽然可以通过模拟的手段来隔离Android依赖，但代价很大，这种情况下可以考虑仪器化的单元测试，有助于减少编写和维护模拟代码所需的工作量。

仪器化测试是在真机或模拟器上运行的测试，它们可以利用Android framework APIs 和 supporting APIs。如果测试用例需要访问仪器(instrumentation)信息(如应用程序的Context)，或者需要Android框架组件的真正实现(如Parcelable或SharedPreferences对象)，那么应该创建仪器化单元测试，由于要跑到真机或模拟器上，所以会慢一些。

- 配置

```
dependencies {
    androidTestImplementation 'com.android.support:support-annotations:27.1.1'
    androidTestImplementation 'com.android.support.test:runner:1.0.2'
    androidTestImplementation 'com.android.support.test:rules:1.0.2'
}
```
```
android {
    ...
    defaultConfig {
        ...
        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
    }
}
```
- Example
这里举一个操作SharedPreference的例子，这个例子需要访问Context类以及SharedPreference的具体实现，采用模拟隔离依赖的话代价会比较大，所以采用仪器化测试比较合适。

这是业务代码中操作SharedPreference的实现
```
public class SharedPreferenceDao {
    private SharedPreferences sp;

    public SharedPreferenceDao(SharedPreferences sp) {
        this.sp = sp;
    }

    public SharedPreferenceDao(Context context) {
        this(context.getSharedPreferences("config", Context.MODE_PRIVATE));
    }

    public void put(String key, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String get(String key) {
        return sp.getString(key, null);
    }
}
```

创建仪器化测试类（app/src/androidTest/java）
```
// @RunWith 只在混合使用 JUnit3 和 JUnit4 需要，若只使用JUnit4，可省略
@RunWith(AndroidJUnit4.class)
public class SharedPreferenceDaoTest {

    public static final String TEST_KEY = "instrumentedTest";
    public static final String TEST_STRING = "玉刚说";

    SharedPreferenceDao spDao;

    @Before
    public void setUp() {
        spDao = new SharedPreferenceDao(App.getContext());
    }

    @Test
    public void sharedPreferenceDaoWriteRead() {
        spDao.put(TEST_KEY, TEST_STRING);
        Assert.assertEquals(TEST_STRING, spDao.get(TEST_KEY));
    }
}
```
运行方式和本地单元测试一样，这个过程会向连接的设备安装apk，测试结果将在Run窗口展示，如下图：

![instrumented test passed](https://upload-images.jianshu.io/upload_images/3631399-af13137ec347a9b6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

通过测试结果可以清晰看到状态passed，仔细看打印的log，可以发现，这个过程向模拟器安装了两个apk文件，分别是app-debug.apk和app-debug-androidTest.apk，instrumented测试相关的逻辑在app-debug-androidTest.apk中。简单介绍一下安装apk命令pm install:
```
// 安装apk
//-t：允许安装测试 APK
//-r：重新安装现有应用，保留其数据，类似于替换安装
//更多请参考 https://developer.android.com/studio/command-line/adb?hl=zh-cn
adb shell pm install -t -r filePath
```

安装完这两个apk后，通过```am instrument```命令运行instrumented测试用例，该命令的一般格式：
```
am instrument [flags] <test_package>/<runner_class>
```
例如本例子中的实际执行命令：
```
adb shell am instrument -w -r -e debug false -e class 'com.jdqm.androidunittest.SharedPreferenceDaoTest#sharedPreferenceDaoWriteRead' com.jdqm.androidunittest.test/android.support.test.runner.AndroidJUnitRunner
```
```
-w: 强制 am instrument 命令等待仪器化测试结束才结束自己(wait)，保证命令行窗口在测试期间不关闭，方便查看测试过程的log
-r: 以原始格式输出结果(raw format)
-e: 以键值对的形式提供测试选项，例如 -e debug false
关于这个命令的更多信息请参考
https://developer.android.com/studio/test/command-line?hl=zh-cn
```

如果你实在没法忍受instrumented test的耗时问题，业界也提供了一个现成的方案[Robolectric][3]，下一小节讲开源框库的时候会将这个例子改成本地本地测试。


# 六、常用单元测试开源库
#### 1. [Mocktio][1]

https://github.com/mockito/mockito

Mock对象，模拟控制其方法返回值，监控其方法的调用等。

- 添加依赖
```
testImplementation 'org.mockito:mockito-core:2.19.0'
```
- Example
```
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.atLeast;

@RunWith(MockitoJUnitRunner.class)
public class MyClassTest {

    @Mock
    MyClass test;

    @Test
    public void mockitoTestExample() throws Exception {

        //可是使用注解@Mock替代
        //MyClass test = mock(MyClass.class);

        // 当调用test.getUniqueId()的时候返回43
        when(test.getUniqueId()).thenReturn(18);
        // 当调用test.compareTo()传入任意的Int值都返回43
        when(test.compareTo(anyInt())).thenReturn(18);

        // 当调用test.close()的时候，抛NullPointerException异常
        doThrow(new NullPointerException()).when(test).close();
        // 当调用test.execute()的时候，什么都不做
        doNothing().when(test).execute();

        assertThat(test.getUniqueId(), is(18));
        // 验证是否调用了1次test.getUniqueId()
        verify(test, times(1)).getUniqueId();
        // 验证是否没有调用过test.getUniqueId()
        verify(test, never()).getUniqueId();
        // 验证是否至少调用过2次test.getUniqueId()
        verify(test, atLeast(2)).getUniqueId();
        // 验证是否最多调用过3次test.getUniqueId()
        verify(test, atMost(3)).getUniqueId();
        // 验证是否这样调用过:test.query("test string")
        verify(test).query("test string");
        // 通过Mockito.spy() 封装List对象并返回将其mock的spy对象
        List list = new LinkedList();
        List spy = spy(list);
        //指定spy.get(0)返回"Jdqm"
        doReturn("Jdqm").when(spy).get(0);
        assertEquals("Jdqm", spy.get(0));
    }
}
```

#### 2. [powermock][4]

https://github.com/powermock/powermock

对于静态方法的mock

- 添加依赖
```
    testImplementation 'org.powermock:powermock-api-mockito2:1.7.4'
    testImplementation 'org.powermock:powermock-module-junit4:1.7.4'
```
>Note: 如果使用了Mockito，需要这两者使用兼容的版本，具体参考 https://github.com/powermock/powermock/wiki/Mockito#supported-versions

- Example
```
@RunWith(PowerMockRunner.class)
@PrepareForTest({StaticClass1.class, StaticClass2.class})
public class StaticMockTest {

    @Test
    public void testSomething() throws Exception{
        // mock完静态类以后，默认所有的方法都不做任何事情
        mockStatic(StaticClass1.class);
        when(StaticClass1.getStaticMethod()).thenReturn("Jdqm");
         StaticClass1.getStaticMethod();
        //验证是否StaticClass1.getStaticMethod()这个方法被调用了一次
        verifyStatic(StaticClass1.class, times(1));
    }
}

```

或者是封装为非静态，然后用[Mockito][1]:

```
class StaticClass1Wraper{
  void someMethod() {
    StaticClass1.someStaticMethod();
  }
```

#### 3. [Robolectric][3]

http://robolectric.org

主要是解决仪器化测试中耗时的缺陷，仪器化测试需要安装以及跑在Android系统上，也就是需要在Android虚拟机或真机上面，所以十分的耗时，基本上每次来来回回都需要几分钟时间。针对这类问题，业界其实已经有了一个现成的解决方案: Pivotal实验室推出的Robolectric，通过使用Robolectrict模拟Android系统核心库的Shadow Classes的方式，我们可以像写本地测试一样写这类测试，并且直接运行在工作环境的JVM上，十分方便。

- 添加配置
```
testImplementation "org.robolectric:robolectric:3.8"

android {
  ...
  testOptions {
    unitTests {
      includeAndroidResources = true
    }
  }
}
```

- Example
模拟打开MainActivity，点击界面上面的Button，读取TextView的文本信息。

MainActivity.java
```
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView tvResult = findViewById(R.id.tvResult);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvResult.setText("Robolectric Rocks!");
            }
        });
    }
}
```
测试类(app/src/test/java/)
```
@RunWith(RobolectricTestRunner.class)
public class MyActivityTest {

    @Test
    public void clickingButton_shouldChangeResultsViewText() throws Exception {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        Button button =  activity.findViewById(R.id.button);
        TextView results = activity.findViewById(R.id.tvResult);
        //模拟点击按钮，调用OnClickListener#onClick
        button.performClick();
        Assert.assertEquals("Robolectric Rocks!", results.getText().toString());
    }
}
```
测试结果
![Robolectric test passed](https://upload-images.jianshu.io/upload_images/3631399-3a0bba49cccc15a1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

耗时917毫秒，是要比单纯的本地测试慢一些。这个例子非常类似于直接跑到真机或模拟器上，然而它只需要跑在本地JVM即可，这都是得益于Robolectric的Shadow。
> Note: 第一次跑需要下载一些依赖，可能时间会久一点，但后续的测试肯定比仪器化测试打包两个apk并安装的过程快。

在第六小节介绍了通过仪器化测试的方式跑到真机上进行测试SharedPreferences操作，可能吐槽的点都在于耗时太长，现在通过[Robolectric][3]改写为本地测试来尝试减少一些耗时。

在实际的项目中，Application可能创建时可能会初始化一些其他的依赖库，不太方便单元测试，这里额外创建一个Application类，不需要在清单文件注册，直接写在本地测试目录即可。
```
public class RoboApp extends Application {}
```
在编写测试类的时候需要通过```@Config(application = RoboApp.class)```来配置Application，当需要传入Context的时候调用```RuntimeEnvironment.application```来获取：
app/src/test/java/
```
@RunWith(RobolectricTestRunner.class)
@Config(application = RoboApp.class)
public class SharedPreferenceDaoTest {

    public static final String TEST_KEY = "instrumentedTest";
    public static final String TEST_STRING = "玉刚说";

    SharedPreferenceDao spDao;

    @Before
    public void setUp() {
        //这里的Context采用RuntimeEnvironment.application来替代应用的Context
        spDao = new SharedPreferenceDao(RuntimeEnvironment.application);
    }

    @Test
    public void sharedPreferenceDaoWriteRead() {
        spDao.put(TEST_KEY, TEST_STRING);
        Assert.assertEquals(TEST_STRING, spDao.get(TEST_KEY));
    }

}
```
像本地此时一样把它跑起来即可。


# 七、实践经验

#### 1. 代码中用到了TextUtil.isEmpty()的如何测试
```
public static boolean isValidEmail(CharSequence email) {
    if (TextUtils.isEmpty(email)) {
        return false;
    }
    return EMAIL_PATTERN.matcher(email).matches();
}
```
当你尝试本地测试这样的代码，就会收到一下的异常：
```
java.lang.RuntimeException: Method isEmpty in android.text.TextUtils not mocked.
```
这种情况，直接在本地测试目录(app/src/test/java)下添加TextUtils类的实现，但必须保证包名相同。
```
package android.text;

public class TextUtils {
    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }
}
```

#### 2. 隔离native方法
```
public class Model {
    public native boolean nativeMethod();
}
```
```
public class ModelTest {
    Model model;

    @Before
    public void setUp() throws Exception {
        model = mock(Model.class);
    }

    @Test
    public void testNativeMethod() throws Exception {
        when(model.nativeMethod()).thenReturn(true);
        Assert.assertTrue(model.nativeMethod());
    }
}
```
#### 3. 在内部new，不方便Mock

```
public class Presenter {

    Model model;
    public Presenter() {
        model = new Model();
    }
    public boolean getBoolean() {
        return model.getBoolean());
    }
}
```
这种情况，需要改进一下代码的写法，不在内部new，而是通过参数传递。

```
public class Presenter {
    Model model;
    public Presenter(Model model) {
        this.model = model;
    }
    public boolean getBoolean() {
        return model.getBoolean();
    }
}
```
这样做方便Mock Model对象。
```
public class PresenterTest {
    Model     model;
    Presenter presenter;

    @Before
    public void setUp() throws Exception {
        // mock Model对象
        model = mock(Model.class);
        presenter = new Presenter(model);
    }

    @Test
    public void testGetBoolean() throws Exception {
        when(model.getBoolean()).thenReturn(true);

        Assert.assertTrue(presenter.getBoolean());
    }
}
```
从这个例子可以看出，代码的框架是否对单元测试友好，也是推进单元测试的一个因素。

#### 4. 本地单元测试-文件操作
在一些涉及到文件读写的App，通常都会在运行时调用```Environment.getExternalStorageDirectory()```得到机器的外存路径，通常的做法是跑到真机或者模拟器上进行调试，耗时比较长，可以通过模拟的方式，在本地JVM完成文件操作。
```
//注意包名保持一致
package android.os;
public class Environment {
    public static File getExternalStorageDirectory() {
        return new File("本地文件系统目录");
    }
}
```

直接在本地单元测试进行调试，不再需要跑到真机，再把文件pull出来查看。

```
public class FileDaoTest {

    public static final String TEST_STRING = "Hello Android Unit Test.";

    FileDao fileDao;

    @Before
    public void setUp() throws Exception {
        fileDao = new FileDao();
    }

    @Test
    public void testWrite() throws Exception {
        String name = "readme.md";
        fileDao.write(name, TEST_STRING);
        String content = fileDao.read(name);
        Assert.assertEquals(TEST_STRING, content);
    }
}
```


# 八、其他
- 考虑可读性，对于方法名使用表达能力强的方法名，对于测试范式可以考虑使用一种规范, 如 RSpec-style。方法名可以采用一种格式，如: [测试的方法]_[测试的条件]_[符合预期的结果]。
- 不要使用逻辑流关键字(If/else、for、do/while、switch/case)，在一个测试方法中，如果需要有这些，拆分到单独的每个测试方法里。
- 测试真正需要测试的内容，需要覆盖的情况，一般情况只考虑验证输出（如某操作后，显示什么，值是什么）。

- 不需要考虑测试private的方法，将private方法当做黑盒内部组件，测试对其引用的public方法即可；不考虑测试琐碎的代码，如getter或者setter。
- 每个单元测试方法，应没有先后顺序；尽可能的解耦对于不同的测试方法，不应该存在Test A与Test B存在时序性的情况。

> 文章给出的一些示例性代码片段中，有一些类代码没有贴出来，有需要可到以下地址获取完整代码：
[https://github.com/jdqm/AndroidUnitTest][6]

参考资料
https://developer.android.com/training/testing/unit-testing/
https://developer.android.com/training/testing/unit-testing/local-unit-tests
https://developer.android.com/training/testing/unit-testing/instrumented-unit-tests
https://blog.dreamtobe.cn/2016/05/15/android_test/
https://www.jianshu.com/p/bc99678b1d6e
https://developer.android.com/studio/test/command-line?hl=zh-cn
https://developer.android.com/studio/command-line/adb?hl=zh-cn

[1]: https://github.com/mockito/mockito
[2]: https://github.com/cglib/cglib
[3]: http://robolectric.org/
[4]: https://github.com/powermock/powermock
[5]: https://upload-images.jianshu.io/upload_images/3631399-11da81c156bed56a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240
[6]: https://github.com/jdqm/AndroidUnitTest
[7]: https://developer.android.com/studio/test/command-line?hl=zh-cn
