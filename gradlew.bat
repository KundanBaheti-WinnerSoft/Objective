<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/background"
    android:orientation="vertical">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_gravity="center_vertical"
        android:layout_marginLeft="@dimen/margin_3dp"
        android:layout_margin="@dimen/margin_5dp"
        android:layout_marginTop="@dimen/margin_5dp"
        android:orientation="horizontal">

        <TextView
            android:id="@+id/tv_select"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center_vertical"
            android:gravity="center_vertical"
            android:text="@string/showColan"
            android:textAppearance="?android:attr/textAppearanceSmall"
            android:textColor="@color/text_color" />

        <RadioGroup
            android:id="@+id/radio_group"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:gravity="center_vertical"
            android:orientation="horizontal"
            android:textColorHint="@color/black">

            <RadioButton
                android:id="@+id/radio_teacher"
                style="@style/MyRadioButton"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:gravity="center_vertical"
                android:text="@string/examDetails"
                android:textAppearance="?android:attr/textAppearanceSmall"
                android:textColor="@color/text_color" />

            <RadioButton
                android:id="@+id/radio_student"
                style="@style/MyRadioButton"
                android:layout_width="wrap_content"
                android:layout_height="match_parent"
                android:gravity="center_vertical"
                android: