package com.example.administrator.corelmarket;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.corelmarket.adapter.CartAdapter;
import com.corelmarket.bean.CartBean;
import com.corelmarket.database.SQLiteHelper;

import java.util.List;

public class Main2Activity extends AppCompatActivity {

    SQLiteHelper mSQLiteHelper;
    CartAdapter mCartAdapter;
    List<CartBean> list;
    ImageView pic1;
    ListView mListView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoping_car);
        pic1 = (ImageView) findViewById(R.id.pic1);
        mListView = (ListView) findViewById(R.id.list_two);
        initData();
    }


    protected void initData() {
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this).setMessage("确定要移出购物车吗？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CartBean cartBean = list.get(position);
                        if (mSQLiteHelper.deleteData(cartBean.getId())) {
                            list.remove(position);  //删除对应的id
                            mCartAdapter.notifyDataSetChanged(); //更新记事本界面
                            Toast.makeText(Main2Activity.this, "移除成功", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();   //关闭对话框
                    }
                });
                dialog = builder.create();   //创建对话框
                dialog.show();               //显示对话框
                return true;
            }
        });
        mSQLiteHelper = new SQLiteHelper(this);
        showQueryData();
    }


    private void showQueryData() {
        if (list != null) {
            list.clear();
        }
        list = mSQLiteHelper.query();
        mCartAdapter = new CartAdapter(this, list);
        mListView.setAdapter(mCartAdapter);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pic1:
                Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                startActivityForResult(intent, 1);
                break;
        }
    }
}
