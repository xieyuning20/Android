# 期中实验 NotePad记事本应用

## 一、基本功能的实现

### 1.NoteList中显示条目增加修改时间的时间戳显示

##### **第一步**：

修改Notelist的布局文件noteslist_item.xml，在布局文件中新增一个TextView，这里我采用的是线性布局。

```
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:paddingLeft="6dip"
    android:paddingRight="6dip"
    android:paddingBottom="3dip">

    <TextView
        android:id="@android:id/text1"
        android:layout_width="match_parent"
        android:layout_height="?android:attr/listPreferredItemHeight"
        android:textAppearance="?android:attr/textAppearanceLarge"
        android:gravity="center_vertical"
        android:paddingLeft="5dip"
        android:singleLine="true"
        />
    <TextView
        android:id="@+id/text2"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:textAppearance="?android:attr/textAppearanceLarge"
        android:gravity="center_vertical"
        android:singleLine="true"
        />
</LinearLayout>
```

##### **第二步**：

在NoteList类的PROJECTION中添加新的投影COLUMN_NAME_MODIFICATION_DATE字段

```
private static final String[] PROJECTION = new String[] {
        NotePad.Notes._ID, // 0
        NotePad.Notes.COLUMN_NAME_TITLE, // 1
        NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE,//添加修改时间
};
```

##### **第三步**：

修改适配器Adapter的内容，给dataColumns添加修改时间

```
// The names of the cursor columns to display in the view, initialized to the title column
String[] dataColumns = { NotePad.Notes.COLUMN_NAME_TITLE ,NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE} ;//添加修改时间

// The view IDs that will display the cursor columns, initialized to the TextView in
// noteslist_item.xml
int[] viewIDs = { android.R.id.text1,android.R.id.text2};//对应地添加对应布局的id

// Creates the backing adapter for the ListView.
// 数据装配
// 第一个参数指明上下文（Context），
// 第二个参数指明数据列表项（Item）的布局，
// 第三个参数为上文managedQuery已获取的数据的游标，
// 第四个参数为数据库表的投影列，
// 第五个参数为数据库表投影列在ListView上显示所对应的布局ID。
SimpleCursorAdapter adapter
    = new SimpleCursorAdapter(
              this,                             // The Context for the ListView
              R.layout.noteslist_item,          // Points to the XML for a list item
              cursor,                           // The cursor to get items from
              dataColumns,
              viewIDs
      );
```

此时时间戳处显示了一串长长的数字

[![IRtHJI.png](https://z3.ax1x.com/2021/11/15/IRtHJI.png)](https://imgtu.com/i/IRtHJI)

这是long类型的时间，接下来我们要对时间进行格式化

##### **第四步**：

在NoteEditor文件的updateNote()方法中获取当前系统的时间，并对时间进行格式化

```
// Sets up a map to contain values to be updated in the provider.
ContentValues values = new ContentValues();
// 转化时间格式
Long now = Long.valueOf(System.currentTimeMillis());
SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
Date d = new Date(now);
String format = sf.format(d);
values.put(NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE, format);
```

##### 效果展示:

实现时间戳的实验截图如下：

[![IRNWhn.png](https://z3.ax1x.com/2021/11/15/IRNWhn.png)](https://imgtu.com/i/IRNWhn)

### 2.添加笔记查询功能（根据标题查询）

##### 第一步：

在list_options_menu.xml添加一个新的菜单项——查询，实现在顶部的菜单栏添加查询图标（用的自带的图片）

    <item
        android:id="@+id/menu_search"
        android:icon="@android:drawable/ic_search_category_default"
        android:showAsAction="always"
        android:title="search">
    </item>

##### 第二步：

新建一个用于实现查询功能的布局文件note_search.xml

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical" android:layout_width="match_parent"
    android:layout_height="match_parent">
    <SearchView
        android:id="@+id/search_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:iconifiedByDefault="false"
        android:queryHint="输入搜索内容..."
        android:layout_alignParentTop="true">
    </SearchView>
    <ListView
        android:id="@android:id/list"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">
    </ListView>
</LinearLayout>

```

##### 第三步：

新建一个用于实现查询功能的Activity——NoteSearch

```
public class NoteSearch extends ListActivity implements SearchView.OnQueryTextListener {
    private static final String[] PROJECTION = new String[] {
            NotePad.Notes._ID, // 0
            NotePad.Notes.COLUMN_NAME_TITLE, // 1
            NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE,//添加修改时间

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_search);
        Intent intent = getIntent();
        if (intent.getData() == null) {
            intent.setData(NotePad.Notes.CONTENT_URI);
        }
        SearchView searchview = (SearchView)findViewById(R.id.search_view);
        //为查询文本框注册监听器
        searchview.setOnQueryTextListener(NoteSearch.this);
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
    @Override
    public boolean onQueryTextChange(String newText) {
        String selection = NotePad.Notes.COLUMN_NAME_TITLE + " Like ? ";
        String[] selectionArgs = { "%"+newText+"%" };
        Cursor cursor = managedQuery(
                getIntent().getData(),
                PROJECTION,
                selection,
                selectionArgs,
                NotePad.Notes.DEFAULT_SORT_ORDER
        );
        String[] dataColumns = { NotePad.Notes.COLUMN_NAME_TITLE ,  NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE };
        int[] viewIDs = { android.R.id.text1 , R.id.text2 };
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                R.layout.noteslist_item,
                cursor,
                dataColumns,
                viewIDs
        );
        setListAdapter(adapter);
        return true;
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        Uri uri = ContentUris.withAppendedId(getIntent().getData(), id);
        String action = getIntent().getAction();
        if (Intent.ACTION_PICK.equals(action) || Intent.ACTION_GET_CONTENT.equals(action)) {
            setResult(RESULT_OK, new Intent().setData(uri));
        } else {
            startActivity(new Intent(Intent.ACTION_EDIT, uri));
        }
    }
}
```

##### 第四步：

在NoteList文件的onOptionsItemSelected()方法中添加选择事件

```
case R.id.menu_search:
	Intent intent = new Intent(this, NoteSearch.class);
    this.startActivity(intent);
    return true;
```

前面这些步骤做完之后点击查询图标发现无法实现页面跳转

##### 第五步：

在AndroidManifest.xml里面注册NoteSearch，解决页面跳转的问题

```
<activity
            android:name="NoteSearch"
            android:label="@string/notes_search">
</activity>
```

##### 效果展示:

实现查询功能的实验截图如下：

[![oSrhUs.png](https://z3.ax1x.com/2021/11/23/oSrhUs.png)](https://imgtu.com/i/oSrhUs)

## 二、扩展功能的实现

### 1.导出Note为文本文件

##### 第一步：

在editor_options_menu.xml中添加一个新的菜单项Export

```
<item android:id="@+id/menu_export"
    android:title="@string/menu_export" />
```

##### 第二步：

新建一个用于实现导出功能的布局文件export.xml（和title_editor.xml差不多）

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:orientation="vertical"
    android:paddingLeft="6dip"
    android:paddingRight="6dip"
    android:paddingBottom="3dip">
    <EditText android:id="@+id/export_name"
        android:maxLines="1"
        android:layout_marginTop="2dp"
        android:layout_marginBottom="15dp"
        android:layout_width="wrap_content"
        android:ems="25"
        android:layout_height="wrap_content"
        android:autoText="true"
        android:capitalize="sentences"
        android:scrollHorizontally="true" />
    <Button android:id="@+id/export_ok"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="right"
        android:text="@string/export_ok"
        android:onClick="ExportOk" />
</LinearLayout>
```

##### 第三步：

权限的设置：由于导出文件涉及到文件的读写，需要给NotePad添加权限。API23+以上，不止要在AndroidManifest.xml里面添加权限，还要在java代码中请求权限

```
<!-- 在SD卡中创建与删除文件权限 -->
<uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"
    tools:ignore="ProtectedPermissions,WrongManifestParent" />
<!-- 向SD卡写入数据权限 -->
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"
    tools:ignore="WrongManifestParent" />
```

设置成功后系统会提示

[![oFpvoq.jpg](https://z3.ax1x.com/2021/11/24/oFpvoq.jpg)](https://imgtu.com/i/oFpvoq)

由于Noteslist是应用程序的入口，我选择在Noteslist.java中添加verifyStoragePermissions()方法请求权限，并在onCreate()方法中调用它

```
// Storage Permissions
private static final int REQUEST_EXTERNAL_STORAGE = 1;
private static String[] PERMISSIONS_STORAGE = {
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE };

/**
 * Checks if the app has permission to write to device storage
 *
 * If the app does not has permission then the user will be prompted to
 * grant permissions
 *
 * @param activity
 */
public static void verifyStoragePermissions(Activity activity) {
    // Check if we have write permission
    int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    if (permission != PackageManager.PERMISSION_GRANTED) {
        // We don't have permission so prompt the user
        ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
    }
}
```

##### 第四步：

新建一个用于实现导出功能的Activity——NoteExport

```
public class NoteExport extends Activity {
    //要使用的数据库中笔记的信息
    private static final String[] PROJECTION = new String[] {
            NotePad.Notes._ID, // 0
            NotePad.Notes.COLUMN_NAME_TITLE, // 1
            NotePad.Notes.COLUMN_NAME_NOTE, // 2
            NotePad.Notes.COLUMN_NAME_CREATE_DATE, // 3
            NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE, // 4
    };
    //读取出的值放入这些变量
    private String TITLE;
    private String NOTE;
    private String CREATE_DATE;
    private String MODIFICATION_DATE;
    //读取该笔记信息
    private Cursor mCursor;
    //导出文件的名字
    private EditText mName;
    //NoteEditor传入的uri，用于从数据库查出该笔记
    private Uri mUri;
    //关于返回与保存按钮的一个特殊标记，返回的话不执行导出，点击按钮才导出
    private boolean flag = false;
    private static final int COLUMN_INDEX_TITLE = 1;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.export);
        Intent intent = getIntent();
        if (intent.getData() == null) {
            intent.setData(NotePad.Notes.CONTENT_URI);
        }
        mUri = intent.getData();
        mCursor = getContentResolver().query(
                mUri,        // The URI for the note that is to be retrieved.
                PROJECTION,  // The columns to retrieve
                null,        // No selection criteria are used, so no where columns are needed.
                null,        // No where columns are used, so no where values are needed.
                null         // No sort order is needed.
        );
        mName = findViewById(R.id.export_name);
    }

    @Override
    protected void onResume(){
        super.onResume();
        if (mCursor != null) {
            // The Cursor was just retrieved, so its index is set to one record *before* the first
            // record retrieved. This moves it to the first record.
            mCursor.moveToFirst();
            mName.setText(mCursor.getString(COLUMN_INDEX_TITLE));
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (mCursor != null) {
            TITLE = mCursor.getString(mCursor.getColumnIndex(NotePad.Notes.COLUMN_NAME_TITLE));
            NOTE = mCursor.getString(mCursor.getColumnIndex(NotePad.Notes.COLUMN_NAME_NOTE));
            CREATE_DATE = mCursor.getString(mCursor.getColumnIndex(NotePad.Notes.COLUMN_NAME_CREATE_DATE));
            MODIFICATION_DATE = mCursor.getString(mCursor.getColumnIndex(NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE));
            //flag在点击导出按钮时会设置为true，执行写文件
            if (flag == true) {
                write();
            }
            flag = false;
        }
    }
    public void ExportOk(View v){
        flag = true;
        finish();
    }
    private void write()
    {

        try
        {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String filename=mName.getText().toString()+".txt";
                File targetFile=new File(getExternalFilesDir(null),filename);
                PrintWriter ps = new PrintWriter(new OutputStreamWriter(new FileOutputStream(targetFile), "UTF-8"));
                ps.println(TITLE);
                ps.println(NOTE);
                ps.println("创建时间：" + CREATE_DATE);
                ps.println("最后一次修改时间：" + MODIFICATION_DATE);
                ps.close();
                Toast.makeText(this, "导出成功,保存位置：" + targetFile, Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
```

在清单文件中将它的主题注册为对话框样式

```
<activity android:name=".NoteExport"
    android:label="Export Note"
    android:theme="@android:style/Theme.Holo.Dialog"
    android:windowSoftInputMode="stateVisible">
</activity>
```

在清单文件中将它的主题注册为对话框样式

##### 第五步：

在NoteEditor的onOptionsItemSelected()方法中添加选择事件

```
case R.id.menu_export:
    Intent intent = new Intent(this, NoteExport.class);
    this.startActivity(intent);
    break;
```

以上步骤完成后导出文本文件中的创建时间选项还是为long型，只需和时间戳步骤中一样转换一下格式就好

[![oiLhOx.png](https://z3.ax1x.com/2021/11/24/oiLhOx.png)](https://imgtu.com/i/oiLhOx)

##### 第六步：

创建时间的格式转换：在NotePadProvider的insert()中添加格式转换代码（和修改时间的代码是相同的）

```
// Gets the current system time in milliseconds
Long now = Long.valueOf(System.currentTimeMillis());
SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
Date d = new Date(now);
String format = sf.format(d);
// If the values map doesn't contain the creation date, sets the value to the current time.
if (values.containsKey(NotePad.Notes.COLUMN_NAME_CREATE_DATE) == false) {
    values.put(NotePad.Notes.COLUMN_NAME_CREATE_DATE, format);
}

// If the values map doesn't contain the modification date, sets the value to the current
// time.
if (values.containsKey(NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE) == false) {
    values.put(NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE, format);
}
```

##### 效果展示:

[![oiXQVf.png](https://z3.ax1x.com/2021/11/24/oiXQVf.png)](https://imgtu.com/i/oiXQVf)

[![oiXGGQ.png](https://z3.ax1x.com/2021/11/24/oiXGGQ.png)](https://imgtu.com/i/oiXGGQ)

[![oiXUrq.png](https://z3.ax1x.com/2021/11/24/oiXUrq.png)](https://imgtu.com/i/oiXUrq)

[![oF9lOH.png](https://z3.ax1x.com/2021/11/24/oF9lOH.png)](https://imgtu.com/i/oF9lOH)

### 2.添加设置字体大小、颜色的功能和一丢丢界面美化

用到的大多都是平时实验里的知识

##### 设置字体大小和颜色

首先在editor_options_menu.xml中添加菜单项

```
    <item
        android:id="@+id/font_size"
        android:title="@string/font_size">
        <menu>
            <group>
                <item
                    android:id="@+id/font_s"
                    android:title="@string/font_s"
                    />

                <item
                    android:id="@+id/font_m"
                    android:title="@string/font_m" />
                <item
                    android:id="@+id/font_l"
                    android:title="@string/font_l" />
            </group>
        </menu>
    </item>

    <item
        android:title="@string/font_color"
        android:id="@+id/font_color"
        >
        <menu>
            <group>
                <item
                    android:id="@+id/red_font"
                    android:title="@string/font_red" />
                <item
                    android:title="@string/font_black"
                    android:id="@+id/black_font"/>
            </group>
        </menu>
    </item>
```

然后在NoteEditor的onOptionsItemSelected()方法中添加相应的case事件

```
case R.id.font_s:
    mText.setTextSize(20);
    break;
case R.id.font_m:
    mText.setTextSize(32);
    break;
case R.id.font_l:
    mText.setTextSize(40);
    break;
case R.id.black_font:
    mText.setTextColor(Color.BLACK);
    break;
case R.id.red_font:
    mText.setTextColor(Color.RED);
    break;
```

做完前面的步骤后就可以初步实现字体设置的功能了，但是当退出后字体的改变没有保存下来

接下来在数据库中加入数据项保存字体大小和颜色信息，PROJECTION中也要添加这两项，细节和添加时间戳有点像，故不赘述

```
db.execSQL("CREATE TABLE " + NotePad.Notes.TABLE_NAME + " ("
        + NotePad.Notes._ID + " INTEGER PRIMARY KEY,"
        + NotePad.Notes.COLUMN_NAME_TITLE + " TEXT,"
        + NotePad.Notes.COLUMN_NAME_NOTE + " TEXT,"
        + NotePad.Notes.COLUMN_NAME_CREATE_DATE + " INTEGER,"
        + NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE + " INTEGER,"
        + NotePad.Notes.COLUMN_NAME_TEXT_SIZE + " INTEGER,"
        + NotePad.Notes.COLUMN_NAME_TEXT_COLOR+ " INTEGER"
        + ");");
```

过程中有遇到Caused by: android.database.sqlite.SQLiteException: no such column: 报错，导出数据库后发现notes表并没有加上新增的两列，先DROP之前的表再CREATE即可（个人觉得是最快的方法）

修改UpdateNote()方法中需要新增参数记录添加保存字体大小和颜色信息

```
updateNote(text, text,textView.getCurrentTextColor(),(int)textView.getTextSize())
```

```
values.put(NotePad.Notes.COLUMN_NAME_TEXT_SIZE, size);
values.put(NotePad.Notes.COLUMN_NAME_TEXT_COLOR, color);
```

效果截图：

[![oZ4MTS.png](https://z3.ax1x.com/2021/11/27/oZ4MTS.png)](https://imgtu.com/i/oZ4MTS)

[![oZ41YQ.png](https://z3.ax1x.com/2021/11/27/oZ41YQ.png)](https://imgtu.com/i/oZ41YQ)

##### 给ListView添加选中样式

在drawable下新建一个bgcolor.xml

```
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:state_focused="true" android:drawable="@color/gray"/>
    <item android:state_pressed="true" android:drawable="@color/gray" />
    <item android:state_selected="true" android:drawable="@color/gray" />
    <item android:state_activated="true" android:drawable="@color/gray" />
    <item android:drawable="@color/white"/>
</selector>
```

在noteslist_item.xml中应用

```
android:background="@drawable/bgcolor
```

在ListView中设置

```
android:choiceMode="singleChoice"
```

效果展示：

[![omRsZ8.png](https://z3.ax1x.com/2021/11/27/omRsZ8.png)](https://imgtu.com/i/omRsZ8)

[![om2fHO.png](https://z3.ax1x.com/2021/11/27/om2fHO.png)](https://imgtu.com/i/om2fHO)



