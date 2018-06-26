# XTextFlagDemo
自定义标签

-----------------------------------------------
 <img width="300" height="550" src="https://github.com/BestCoderXQX/XTextFlagDemo/raw/master/screenshots/aaa.png"/>
 <img width="300" height="550" src="https://github.com/BestCoderXQX/XTextFlagDemo/raw/master/screenshots/bbb.png"/>

##
支持：
  ------------------------------------------------

1、编辑标签样式（梯形、三角形）  
2、标签前后背景色  
3、标签文字大小、内容、颜色  
4、标签位置样式  

##
Usage：
  ------------------------------------------------
 ###
  一、xml
    
           <xqx.com.xtextflag.XTextViewFlag
            android:layout_width="60dp"
            android:layout_height="60dp"
            android:gravity="center"
            app:xBackgroundColor="#f00"
            app:xFlagLength="30dp"
            app:xFlagMode="left_bottom_triangle"
            app:xTextContent="标签G"
            app:xTextColor="#0f0"
            app:xTextSize="16sp"
            ></xqx.com.xtextflag.XTextViewFlag>
            
 二、动态添加

            XTextViewFlag xTextViewFlag = new XTextViewFlag(this);
            xTextViewFlag.setText("标签A");
            xTextViewFlag.setTextSize(16);
            xTextViewFlag.setMode(0);
            xTextViewFlag.setSlantedBackgroundColor(getResources().getColor(R.color.colorAccent));
        
##
[我的博客: 听着music睡](http://www.cnblogs.com/xqxacm/)
