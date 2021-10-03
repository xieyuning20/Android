# 实验2 Android界面布局

### 一、线性布局

这题需要采用线性布局的嵌套来实现，大体的布局上需要先用一个垂直的线性布局嵌套四个水平的线性布局，再在水平线性布局中插入四个TextView或Button；接下来就是对组件属性的设置。

在水平线性布局中设置`android:layout_height="0dp" android:layout_weight="1"`就可以实现四个这组件的均匀分布。

同理，在一组TextView中也可以通过这样的设置实现权重不均的分布。

实验结果截图：

[![4LmCGt.png](https://z3.ax1x.com/2021/10/03/4LmCGt.png)](https://imgtu.com/i/4LmCGt)

### 二、约束布局

这题需要使用到Chain来实现各个组件的对齐，在约束布局中每个组件至少要有一个水平约束和垂直约束。

实验结果截图：

[<img src="https://z3.ax1x.com/2021/10/03/4LmqFs.png" alt="4LmqFs.png" style="zoom:50%;" />](https://imgtu.com/i/4LmqFs)

### 三、表格布局

表格布局是线性布局的一种，添加一个TableRow就是添加表的一行，通过往TableRow中添加其他组件控制表格的列数。

这题采用了6行3列的表格，在第二列设置了`android:gravity="left"`在第三列设置了`android:gravity="right"`

实验结果截图：


[<img src="https://z3.ax1x.com/2021/10/03/4LmLYn.png" alt="4LmLYn.png" style="zoom:50%;" />](https://imgtu.com/i/4LmLYn)