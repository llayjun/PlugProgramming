package com.millet.plugprogramming;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.morgoo.droidplugin.pm.PluginManager;
import com.morgoo.helper.compat.PackageManagerCompat;

import java.io.File;

public class PlugMainActivity extends AppCompatActivity {
    private TextView mPluginText;
    private Button mPluginButton;
    private File[] mFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_main);
        mPluginText = (TextView) findViewById(R.id.host_text_view);
        mPluginButton = (Button) findViewById(R.id.host_button);
        mPluginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                PackageManager _packageManager = getPackageManager();
                Intent _intent = _packageManager.getLaunchIntentForPackage("com.millet.plug");
                _intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(_intent);
            }
        });
        //获取插件
        File _file = new File(Environment.getExternalStorageDirectory(), "/plugin");
        mFiles = _file.listFiles();
        if (null == mFiles || mFiles.length == 0) {
            return;
        } else {
            //安装第一个插件
            try {
                int _s = PluginManager.getInstance().installPackage(mFiles[0].getAbsolutePath(), PackageManagerCompat.INSTALL_REPLACE_EXISTING);
                System.out.println("返回的数据" + _s);
            } catch (RemoteException _e) {
                _e.printStackTrace();
            }
            mPluginText.setText(mFiles[0].getAbsolutePath());
        }
    }
}
