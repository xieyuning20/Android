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

先使用ListView创建List，再为ListItem创建ActionMode形式的上下文菜单，设置响应即刻实现

这题的布局文件与第一题差不多但是要注意：**activity_main.xml的ListView控件中必须设置属性android:choiceMode="multipleChoiceModal，也就是可以为多选"**

上方选项菜单的有关设置menu.xml的代码如下：

```
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">
    <item
        android:id="@+id/menu_delete"
        app:showAsAction="always"
        android:icon="@drawable/dustbin"
        android:title="@string/delete" />
</menu>
```

为了实现显示选中数量的功能，新建一个Item类来给list新增一个是否被选中的属性

```
public class Item {

    private String name;//显示的选项名
    private boolean bo;//记录是否被选中

    //构造函数
    public Item(){
        super();
    }

    //带两个参数的构造函数
    public Item(String name, boolean bo){
        super();
        this.name = name;
        this.bo = bo;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public boolean isBo() {
        return bo;
    }
    public void setBo(boolean bo) {
        this.bo = bo;
    }
}
```

这里采用了BaseAdapter的适配器

```
public class Adapter extends BaseAdapter {

    List<Item> list;//item的list对象
    Context context;//上下文对象

    //初始化
    public Adapter(List<Item> list, Context context) {
        this.context = context;
        this.list = list;
        //列表同步方法
        notifyDataSetChanged();
    }

    //得到当前列表的选项数量
    public int getCount() {
        return list.size();
    }

    //根据下标得到列表项
    public Item getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        //如果还没加载
        if(convertView==null){
            //加载布局文件，并将各个选项以及每个选项中的内容一一对应
            convertView=View.inflate(context, R.layout.simple_item, null);
            viewHolder=new ViewHolder();
            viewHolder.imageView=(ImageView) convertView.findViewById(R.id.picture);
            viewHolder.textView=(TextView) convertView.findViewById(R.id.item);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }

        //得到十六进制的颜色的int值
        int blue = Color.parseColor("#33B5E5");
        int white = Color.parseColor("#FFFFFF");
        viewHolder.textView.setText(list.get(position).getName());
        //如果被选中，那么改变选中颜色
        if(list.get(position).isBo() == true){
            viewHolder.textView.setBackgroundColor(blue);
            viewHolder.imageView.setBackgroundColor(blue);
        }
        else {
            viewHolder.textView.setBackgroundColor(white);
            viewHolder.imageView.setBackgroundColor(white);
        }
        return convertView;

    }

    //创建内部类，定义每一个列表项所包含的东西，这里是每个列表项都有一个imageView和textView。
    class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}
```

在MainActivity.java中，采用onItemCheckedStateChanged()方法以及ActionMode.Callback接口中的各类方法实现要求的功能：

```
public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private List<Item> list;

    private BaseAdapter adapter;
    private String [] name = {"One","Two","Three","Four","Five"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.mylist);
        list = new ArrayList<Item>();
        //定义item并且加入list中
        for(int i = 0; i < name.length; i++){
            list.add(new Item(name[i], false));
        }
        //对listview进行适配器适配
        adapter = new Adapter(list, MainActivity.this);
        listView.setAdapter(adapter);

        //设置listview允许多选模式
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new MultiChoiceModeListener() {
            //选中数量
            int num = 0;
            /*
             * 参数：ActionMode是长按后出现的标题栏
             *        positon是当前选中的item的序号
             *    id 是当前选中的item的id
             *    checked 如果是选中事件则为true，如果是取消事件则为false
             */
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                // 调整选定条目
                if (checked == true) {
                    list.get(position).setBo(true);
                    //实时刷新
                    adapter.notifyDataSetChanged();
                    num++;
                } else {
                    list.get(position).setBo(false);
                    //实时刷新
                    adapter.notifyDataSetChanged();
                    num--;
                }
                // 用TextView显示
                mode.setTitle("  " + num + " Selected");
            }

            /*
             * 参数：ActionMode是长按后出现的标题栏
             *        Menu是标题栏的菜单内容
             */
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // 设置长按后所要显示的标题栏的内容
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.menu, menu);
                num = 0;
                adapter.notifyDataSetChanged();
                return true;
            }
            /*
             * 可在此方法中进行标题栏UI的创建和更新
             */
            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {

                adapter.notifyDataSetChanged();
                return false;
            }
            public void refresh(){
                for(int i = 0; i < name.length; i++){
                    list.get(i).setBo(false);
                }
            }
            /*
             * 可在此方法中监听标题栏Menu的监听，从而进行相应操作
             * 设置actionMode菜单每个按钮的点击事件
             * 这里我只设置了删除
             */
            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    //删除
                    case R.id.menu_delete:
                        adapter.notifyDataSetChanged();
                        num = 0;
                        refresh();
                        mode.finish();// 由于题目的要求主要是上下文菜单关联模式的实现，没有继续扩展相应的方法，将菜单按钮设置为返回，结束多选模式
                        return true;
                    default:
                        refresh();
                        adapter.notifyDataSetChanged();
                        num = 0;
                        return false;
                }
            }
            @Override
            public void onDestroyActionMode(ActionMode mode) {
                refresh();
                adapter.notifyDataSetChanged();
            }
        });
    }
}
```

实验截图：

[![5tMmEq.png](https://z3.ax1x.com/2021/10/17/5tMmEq.png)](https://imgtu.com/i/5tMmEq)