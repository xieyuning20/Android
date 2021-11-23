# 期中实验 NotePad记事本应用

## 一、基本功能的实现

### 1.NoteList中显示条目增加修改时间的时间戳显示

##### **第一步**：修改Notelist的布局文件noteslist_item.xml，在布局文件中新增一个TextView，这里我采用的是线性布局。

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

##### **第二步**：在NoteList类的PROJECTION中添加新的投影COLUMN_NAME_MODIFICATION_DATE字段

```
private static final String[] PROJECTION = new String[] {
        NotePad.Notes._ID, // 0
        NotePad.Notes.COLUMN_NAME_TITLE, // 1
        NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE,//添加修改时间
};
```

##### **第三步**：修改适配器Adapter的内容，给dataColumns添加修改时间

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

##### **第四步**：在NoteEditor文件的updateNote()方法中获取当前系统的时间，并对时间进行格式化

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

实现时间戳的实验截图如下：

[![IRNWhn.png](https://z3.ax1x.com/2021/11/15/IRNWhn.png)](https://imgtu.com/i/IRNWhn)

### 2.添加笔记查询功能（根据标题查询）

##### 第一步：在list_option_menu.xml添加一个新的菜单项——查询，实现在顶部的菜单栏添加查询图标（用的自带的图片）

    <item
        android:id="@+id/menu_search"
        android:icon="@android:drawable/ic_search_category_default"
        android:showAsAction="always"
        android:title="search">
    </item>
##### 第二步：新建一个用于实现查询功能的布局文件note_search.xml

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

##### 第三步：新建一个用于实现查询功能的Activity——NoteSearch

```
package com.example.android.notepad;
import android.app.ListActivity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;

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

##### 第四步：在NoteList文件的onOptionsItemSelected()方法中添加选择事件

```
case R.id.menu_search:
	Intent intent = new Intent(this, NoteSearch.class);
    this.startActivity(intent);
    return true;
```

前面这些步骤做完之后点击查询图标发现无法实现页面跳转

##### 第五步：在AndroidManifest.xml里面注册NoteSearch，解决页面跳转的问题

```
<activity
            android:name="NoteSearch"
            android:label="@string/notes_search">
</activity>
```

实现查询功能的实验截图如下：

[![oSrhUs.png](https://z3.ax1x.com/2021/11/23/oSrhUs.png)](https://imgtu.com/i/oSrhUs)

## 二、扩展功能的实现

### 1.界面美化，更改记事本的背景

### 2.