# 实验三

### 一、Android ListView的用法

列表项的布局可以采用线性布局、表格布局、约束布局等等布局完成，我采用的是线性布局，以下是我的列表项布局代码：

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="horizontal"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:gravity="center_vertical"
    android:padding="10dp"
    android:background="@drawable/bgcolor">

    <TextView
        android:id="@+id/name"
        android:layout_width="0dp"
        android:layout_weight="1"
        android:layout_height="wrap_content"
        android:layout_gravity="left"
        android:textColor="#000000"></TextView>

    <ImageView
        android:id="@+id/img"
        android:layout_width="40dp"
        android:layout_height="40dp"
        android:layout_gravity="right"
        android:onClick="ItemOnclick"
        ></ImageView>
</LinearLayout>
```

采用SimpleAdapter来提供列表项，选中列表项时，列表项的背景颜色发生改变，同时Toast显示选中的列表项信息，以下是我的MainActivity.java代码：

```
package com.example.lab3_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private String[] names=new String[]{"Lion","Tiger","Monkey","Dog","Cat","Elephant"};
    private int[] imgs=new int[]{R.drawable.lion,R.drawable.tiger,R.drawable.monkey,R.drawable.dog,R.drawable.cat,R.drawable.elephant};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Map<String,Object>> listItems=new ArrayList<>();
        for (int i=0;i<names.length;i++){
            Map<String,Object> listItem =new HashMap<>();
            listItem.put("name",names[i]);
            listItem.put("img",imgs[i]);
            listItems.add(listItem);
        }
        SimpleAdapter simpleAdapter =new SimpleAdapter(this,listItems,R.layout.simple_item,new String[]{"name","img"},new int[]{R.id.name,R.id.img});
        ListView list=findViewById(R.id.mylist);
        list.setAdapter(simpleAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView=view.findViewById(R.id.name);
                Toast.makeText(MainActivity.this,textView.getText(),Toast.LENGTH_SHORT).show();

            }
        });
    }
}
```

为了实现背景颜色的改变，需要在drawable下新建一个xml，以下是该xml文件的代码：

**注意：ListView控件中必须设置属性android:choiceMode="singleChoice"，不然是不起作用的；以下代码的drawable属性的值必须定义在资源文件中**

```
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <!--    激活状态下的颜色-->
    <item android:drawable="@color/colorPrimaryDark" android:state_activated="true" />
    <!--    默认状态下的颜色-->
    <item android:drawable="@color/white"/>
</selector>
```

实验结果截图：

[![5epIII.png](https://z3.ax1x.com/2021/10/11/5epIII.png)](https://imgtu.com/i/5epIII)

### 二、创建自定义布局的AlertDialog

为了便于展示，在activity_main.xml中设置了一个按钮，通过点击按钮弹出对话框

[![5e1l1U.png](https://z3.ax1x.com/2021/10/12/5e1l1U.png)](https://imgtu.com/i/5e1l1U)

对于对话框的自定义布局，我采用的是表格布局的形式，以下为对话框布局文件的代码：

```
<?xml version="1.0" encoding="utf-8"?>
<TableLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    <ImageView
        android:id="@+id/img"
        android:src="@drawable/header_logo"></ImageView>
    <EditText
        android:id="@+id/username"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:inputType="text"
        android:hint="@string/username"
        android:layout_margin="5dp"></EditText>
    <EditText
        android:id="@+id/password"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:inputType="textPassword"
        android:hint="@string/password"
        android:layout_margin="5dp"></EditText>
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="1dp"
        android:background="@color/gray"></TextView>
    <TableRow
        android:layout_height="50dp">
        <TextView
            android:id="@+id/cancel"
            android:layout_width="0dp"
            android:layout_height="40dp"
            android:layout_weight="1"
            android:text="@string/cancel"
            android:gravity="center"
            android:layout_margin="5dp"
            android:textStyle="bold"
            android:onClick="neg"></TextView>
        <TextView
            android:layout_width="1dp"
            android:layout_height="match_parent"
            android:background="@color/gray"></TextView>
        <TextView
            android:id="@+id/sign"
            android:layout_width="0dp"
            android:layout_height="40dp"
            android:layout_weight="1"
            android:text="@string/sign"
            android:gravity="center"
            android:layout_margin="5dp"
            android:textStyle="bold"
            android:onClick="pos"></TextView>
    </TableRow>
</TableLayout>
```

在MainActivity.java文件中创建AlertDialog.Builder对象，调用setView()将布局添加到AlertDialog：

```
package com.example.lab3_2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 加载login.xml文件
                View loginForm =View.inflate(getApplication(),R.layout.login,null);
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this)
                        .setView(loginForm);
                builder.create().show();
            }
        });

    }
}
```

实验结果截图：

[![5e1ouQ.png](https://z3.ax1x.com/2021/10/12/5e1ouQ.png)](https://imgtu.com/i/5e1ouQ)

### 三、使用XML定义菜单

这题的布局比较简单，通过重写onCreateOptionsMenu(Menu menu)方法添加菜单或子菜单，重写onOptionsItemSelected(MenuItem mi)方法添加响应菜单项的单击事件。add()方法用于添加菜单项，addSubMenu()用于添加子菜单。以下是我的MainActivity.java代码：

```
package com.example.lab3_3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
//    字体大小
    private static final int FONT_S = 0x111;
    private static final int FONT_M = 0x112;
    private static final int FONT_L = 0x113;
//    字体颜色
    private static final int FONT_RED = 0x114;
    private static final int FONT_BLACK = 0x115;
//    普通菜单项
    private static final int PLAIN_ITEM = 0x116;
    private TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text=findViewById(R.id.txt);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        SubMenu fontMenu =menu.addSubMenu("字体大小");
        fontMenu.add(0,FONT_S,0,"小号字体");
        fontMenu.add(0,FONT_M,0,"中号字体");
        fontMenu.add(0,FONT_L,0,"大号字体");
        menu.add(0,PLAIN_ITEM,0,"普通菜单项");
        SubMenu colorMenu =menu.addSubMenu("字体颜色");
        colorMenu.add(0,FONT_RED,0,"红色");
        colorMenu.add(0,FONT_BLACK,0,"黑色");
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem mi){
        switch (mi.getItemId()){
            case FONT_S:text.setTextSize(30); break;
            case FONT_M:text.setTextSize(40); break;
            case FONT_L:text.setTextSize(50); break;
            case PLAIN_ITEM:
                Toast.makeText(MainActivity.this,"普通菜单项",Toast.LENGTH_SHORT).show(); break;
            case FONT_RED:text.setTextColor(getResources().getColor(R.color.RED)); break;
            case FONT_BLACK:text.setTextColor(getResources().getColor(R.color.BLACK)); break;
        }
        return true;
    }
}
```

实验结果截图：

[![5nDmpF.png](https://z3.ax1x.com/2021/10/12/5nDmpF.png)](https://imgtu.com/i/5nDmpF)

### 四、创建上下文操作模式（ActionMode）的上下文菜单

**注意：ListView控件中必须设置属性android:choiceMode="multipleChoiceModal"**