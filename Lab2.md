# 实验2 Android界面布局

### 一、线性布局

这题需要采用线性布局的嵌套来实现，大体的布局上需要先用一个垂直的线性布局嵌套四个水平的线性布局，再在水平线性布局中插入四个TextView或Button；接下来就是对组件属性的设置。

在水平线性布局中设置`android:layout_height="0dp" android:layout_weight="1"`就可以实现四个这组件的均匀分布。

同理，在一组TextView中也可以通过这样的设置实现权重不均的分布。

实验代码：

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="200dp"
    android:layout_gravity="center_vertical"
    android:orientation="vertical"
    android:paddingLeft="8dp"
    android:paddingRight="8dp"
    android:paddingTop="8dp"
    android:paddingBottom="8dp"
    >
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        android:layout_marginBottom="8dp"
        android:orientation="horizontal">

        <TextView
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_marginRight="8dp"
            android:layout_weight="2"
            android:background="#FFFFFF"
            android:gravity="center"
            android:text="@string/one_one"></TextView>
        <TextView
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="3"
            android:layout_marginRight="8dp"
            android:text="@string/one_two"
            android:gravity="center">
        </TextView>
        <TextView
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="2"
            android:layout_marginRight="8dp"
            android:text="@string/one_three"
            android:gravity="center">
        </TextView>
        <TextView
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="2"
            android:text="@string/one_four"
            android:gravity="center">
        </TextView>
    </LinearLayout>
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        android:layout_marginBottom="8dp">
        <TextView
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="2"
            android:layout_marginRight="8dp"
            android:text="@string/two_one"
            android:gravity="center">
        </TextView>
        <TextView
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="3"
            android:layout_marginRight="8dp"
            android:text="@string/two_two"
            android:gravity="center">
        </TextView>
        <TextView
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="2"
            android:layout_marginRight="8dp"
            android:text="@string/two_three"
            android:gravity="center">
        </TextView>
        <TextView
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="2"
            android:text="@string/two_four"
            android:gravity="center">
        </TextView>
    </LinearLayout>
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        android:layout_marginBottom="8dp">
        <TextView
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="1"
            android:layout_marginRight="8dp"
            android:text="@string/three_one"
            android:gravity="center">
        </TextView>
        <TextView
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="1"
            android:layout_marginRight="8dp"
            android:text="@string/three_two"
            android:gravity="center">
        </TextView>
        <TextView
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="1"
            android:layout_marginRight="8dp"
            android:text="@string/three_three"
            android:gravity="center">
        </TextView>
        <TextView
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="1"
            android:text="@string/three_four"
            android:gravity="center">
        </TextView>
    </LinearLayout>
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1">
        <TextView
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="6"
            android:layout_marginRight="8dp"
            android:text="@string/four_one"
            android:gravity="center">
        </TextView>
        <TextView
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="9"
            android:layout_marginRight="8dp"
            android:text="@string/four_two"
            android:gravity="center">
        </TextView>
        <TextView
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="7"
            android:layout_marginRight="8dp"
            android:text="@string/four_three"
            android:gravity="center">
        </TextView>
        <TextView
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="6"
            android:text="@string/four_four"
            android:gravity="center">
        </TextView>
    </LinearLayout>
</LinearLayout>
```

实验结果截图：

[![4LmCGt.png](https://z3.ax1x.com/2021/10/03/4LmCGt.png)](https://imgtu.com/i/4LmCGt)

### 二、约束布局

这题需要使用到Chain来实现各个组件的对齐，在约束布局中每个组件至少要有一个水平约束和垂直约束。

实验代码：

```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="8dp">

    <TextView
        android:id="@+id/input"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="24dp"
        android:gravity="left"
        android:text="@string/input"
        android:textSize="40dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"></TextView>

    <TextView
        android:id="@+id/display"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="32dp"
        android:background="#C0C091"
        android:gravity="right"
        android:text="@string/display"
        android:textSize="40dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.0"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/input"></TextView>

    <Button
        android:id="@+id/seven"
        android:layout_width="85dp"
        android:layout_height="35dp"
        android:layout_marginStart="8dp"
        android:layout_marginLeft="8dp"
        android:layout_marginTop="96dp"
        android:text="@string/seven"
        app:layout_constraintBottom_toTopOf="@+id/point"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/display"
        app:layout_constraintVertical_bias="0.032" />

    <Button
        android:id="@+id/eifht"
        android:layout_width="85dp"
        android:layout_height="35dp"
        android:text="@string/eight"
        app:layout_constraintBottom_toBottomOf="@+id/seven"
        app:layout_constraintEnd_toStartOf="@+id/nine"
        app:layout_constraintStart_toEndOf="@+id/seven"
        app:layout_constraintTop_toTopOf="@+id/seven"
        app:layout_constraintVertical_bias="0.0" />

    <Button
        android:id="@+id/nine"
        android:layout_width="85dp"
        android:layout_height="35dp"
        android:layout_marginEnd="12dp"
        android:layout_marginRight="12dp"
        android:text="@string/nine"
        app:layout_constraintBottom_toBottomOf="@+id/eifht"
        app:layout_constraintEnd_toStartOf="@+id/divide"
        app:layout_constraintHorizontal_bias="1.0"
        app:layout_constraintStart_toEndOf="@+id/seven"
        app:layout_constraintTop_toTopOf="@+id/eifht"
        app:layout_constraintVertical_bias="0.0" />

    <Button
        android:id="@+id/divide"
        android:layout_width="85dp"
        android:layout_height="35dp"
        android:layout_marginStart="204dp"
        android:layout_marginLeft="204dp"
        android:text="@string/divide"
        app:layout_constraintBottom_toBottomOf="@+id/nine"
        app:layout_constraintStart_toEndOf="@+id/seven"
        app:layout_constraintTop_toTopOf="@+id/nine"
        app:layout_constraintVertical_bias="0.0" />

    <Button
        android:id="@+id/four"
        android:layout_width="85dp"
        android:layout_height="35dp"
        android:text="@string/four"
        app:layout_constraintBottom_toTopOf="@+id/one"
        app:layout_constraintEnd_toEndOf="@+id/seven"
        app:layout_constraintHorizontal_bias="0.875"
        app:layout_constraintStart_toStartOf="@+id/seven"
        app:layout_constraintTop_toBottomOf="@+id/seven" />

    <Button
        android:id="@+id/one"
        android:layout_width="85dp"
        android:layout_height="35dp"
        android:text="@string/one"
        app:layout_constraintBottom_toTopOf="@+id/point"
        app:layout_constraintEnd_toEndOf="@+id/four"
        app:layout_constraintHorizontal_bias="0.0"
        app:layout_constraintStart_toStartOf="@+id/four"
        app:layout_constraintTop_toBottomOf="@+id/seven"
        app:layout_constraintVertical_bias="0.751" />

    <Button
        android:id="@+id/point"
        android:layout_width="85dp"
        android:layout_height="35dp"
        android:layout_marginBottom="48dp"
        android:text="@string/point"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="@+id/one"
        app:layout_constraintHorizontal_bias="0.0"
        app:layout_constraintStart_toStartOf="@+id/one" />

    <Button
        android:id="@+id/five"
        android:layout_width="85dp"
        android:layout_height="35dp"
        android:layout_marginStart="87dp"
        android:layout_marginLeft="87dp"
        android:layout_marginTop="68dp"
        android:text="@string/five"
        app:layout_constraintBottom_toBottomOf="@+id/four"
        app:layout_constraintEnd_toEndOf="@+id/eifht"
        app:layout_constraintHorizontal_bias="1.0"
        app:layout_constraintStart_toStartOf="@+id/eifht"
        app:layout_constraintTop_toTopOf="@+id/four"
        app:layout_constraintVertical_bias="1.0" />

    <Button
        android:id="@+id/six"
        android:layout_width="85dp"
        android:layout_height="35dp"
        android:text="@string/six"
        app:layout_constraintBottom_toBottomOf="@+id/five"
        app:layout_constraintEnd_toEndOf="@+id/nine"
        app:layout_constraintHorizontal_bias="0.0"
        app:layout_constraintStart_toStartOf="@+id/nine"
        app:layout_constraintTop_toTopOf="@+id/five"
        app:layout_constraintVertical_bias="1.0" />

    <Button
        android:id="@+id/multipl"
        android:layout_width="85dp"
        android:layout_height="35dp"
        android:text="@string/multiply"
        app:layout_constraintBottom_toBottomOf="@+id/six"
        app:layout_constraintEnd_toEndOf="@+id/divide"
        app:layout_constraintStart_toStartOf="@+id/divide"
        app:layout_constraintTop_toTopOf="@+id/six" />

    <Button
        android:id="@+id/two"
        android:layout_width="85dp"
        android:layout_height="35dp"
        android:layout_marginStart="4dp"
        android:layout_marginLeft="4dp"
        android:text="@string/two"
        app:layout_constraintBottom_toBottomOf="@+id/one"
        app:layout_constraintEnd_toEndOf="@+id/five"
        app:layout_constraintHorizontal_bias="1.0"
        app:layout_constraintStart_toStartOf="@+id/five"
        app:layout_constraintTop_toTopOf="@+id/one"
        app:layout_constraintVertical_bias="1.0" />

    <Button
        android:id="@+id/three"
        android:layout_width="85dp"
        android:layout_height="35dp"
        android:text="@string/three"
        app:layout_constraintBottom_toBottomOf="@+id/two"
        app:layout_constraintEnd_toEndOf="@+id/six"
        app:layout_constraintHorizontal_bias="0.0"
        app:layout_constraintStart_toStartOf="@+id/six"
        app:layout_constraintTop_toTopOf="@+id/two"
        app:layout_constraintVertical_bias="0.0" />

    <Button
        android:id="@+id/ad"
        android:layout_width="85dp"
        android:layout_height="35dp"
        android:text="@string/add"
        app:layout_constraintBottom_toBottomOf="@+id/three"
        app:layout_constraintEnd_toEndOf="@+id/multipl"
        app:layout_constraintHorizontal_bias="0.741"
        app:layout_constraintStart_toStartOf="@+id/multipl"
        app:layout_constraintTop_toTopOf="@+id/three"
        app:layout_constraintVertical_bias="0.0" />

    <Button
        android:id="@+id/zero"
        android:layout_width="85dp"
        android:layout_height="35dp"
        android:text="@string/zero"
        app:layout_constraintBottom_toBottomOf="@+id/point"
        app:layout_constraintEnd_toEndOf="@+id/two"
        app:layout_constraintHorizontal_bias="0.1"
        app:layout_constraintStart_toStartOf="@+id/two"
        app:layout_constraintTop_toTopOf="@+id/point"
        app:layout_constraintVertical_bias="0.0" />

    <Button
        android:id="@+id/equa"
        android:layout_width="85dp"
        android:layout_height="35dp"
        android:text="@string/equal"
        app:layout_constraintBottom_toBottomOf="@+id/zero"
        app:layout_constraintEnd_toEndOf="@+id/three"
        app:layout_constraintHorizontal_bias="0.0"
        app:layout_constraintStart_toStartOf="@+id/three"
        app:layout_constraintTop_toTopOf="@+id/zero"
        app:layout_constraintVertical_bias="0.0" />

    <Button
        android:id="@+id/sybtract"
        android:layout_width="85dp"
        android:layout_height="35dp"
        android:text="@string/subtract"
        app:layout_constraintBottom_toBottomOf="@+id/equa"
        app:layout_constraintEnd_toEndOf="@+id/ad"
        app:layout_constraintHorizontal_bias="0.583"
        app:layout_constraintStart_toStartOf="@+id/ad"
        app:layout_constraintTop_toTopOf="@+id/equa"
        app:layout_constraintVertical_bias="0.0" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

实验结果截图：

[<img src="https://z3.ax1x.com/2021/10/03/4LmqFs.png" alt="4LmqFs.png" style="zoom:50%;" />](https://imgtu.com/i/4LmqFs)

### 三、表格布局

表格布局是线性布局的一种，添加一个TableRow就是添加表的一行，通过往TableRow中添加其他组件控制表格的列数。

这题采用了6行3列的表格，在第二列设置了`android:gravity="left"`在第三列设置了`android:gravity="right"`

实验代码：

```
<?xml version="1.0" encoding="utf-8"?>
<TableLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <TableRow>

        <TextView
            android:layout_width="30dp"
            android:layout_height="wrap_content"></TextView>

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/open"
            android:textSize="30sp"></TextView>

        <TextView
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:gravity="right"
            android:text="@string/o"
            android:textSize="30sp"></TextView>
    </TableRow>

    <TableRow>

        <TextView
            android:layout_width="30dp"
            android:layout_height="wrap_content"></TextView>

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/save"
            android:textSize="30sp"></TextView>

        <TextView
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:gravity="right"
            android:text="@string/s"
            android:textSize="30sp"></TextView>
    </TableRow>

    <TableRow>

        <TextView
            android:layout_width="30dp"
            android:layout_height="wrap_content"></TextView>

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/as"
            android:textSize="30sp"></TextView>

        <TextView
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:gravity="right"
            android:text="@string/ss"
            android:textSize="30sp"></TextView>
    </TableRow>

    <View
        android:layout_width="match_parent"
        android:layout_height="2dp"
        android:background="@color/colorPrimaryDark" />

    <TableRow>

        <TextView
            android:layout_width="30dp"
            android:layout_height="wrap_content"
            android:text="@string/x"
            android:textSize="30sp"></TextView>

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/impor"
            android:textSize="30sp"></TextView>

        <TextView
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:gravity="end"></TextView>
    </TableRow>

    <TableRow>

        <TextView
            android:layout_width="30dp"
            android:layout_height="wrap_content"
            android:text="@string/x"
            android:textSize="30sp"></TextView>

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/export"
            android:textSize="30sp"></TextView>

        <TextView
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:gravity="right"
            android:text="@string/e"
            android:textSize="30sp"></TextView>
    </TableRow>

    <View
        android:layout_width="match_parent"
        android:layout_height="2dp"
        android:background="@color/colorPrimaryDark" />

    <TableRow>

        <TextView
            android:layout_width="30dp"
            android:layout_height="wrap_content"></TextView>

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/quit"
            android:textSize="30sp"></TextView>

        <TextView
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:gravity="right"></TextView>
    </TableRow>
</TableLayout>
```

实验结果截图：


[<img src="https://z3.ax1x.com/2021/10/03/4LmLYn.png" alt="4LmLYn.png" style="zoom:50%;" />](https://imgtu.com/i/4LmLYn)