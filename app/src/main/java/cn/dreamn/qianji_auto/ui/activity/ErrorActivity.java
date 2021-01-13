/*
 * Copyright (C) 2021 dreamn(dream@dreamn.cn)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package cn.dreamn.qianji_auto.ui.activity;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.xuexiang.xutil.XUtil;

import java.io.File;

import cn.dreamn.qianji_auto.R;
import cn.dreamn.qianji_auto.utils.tools.Logs;

public class ErrorActivity extends AppCompatActivity {


    TextView exce_title;


    TextView exce_text;


    TextView exce_cancel;


    TextView exce_send;


    private String filePath="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exception);
        initView();
        initListen();
    }

    private void initView(){
        exce_title=findViewById(R.id.exce_title);
        exce_text=findViewById(R.id.exce_text);
        exce_cancel=findViewById(R.id.exce_cancel);
        exce_send=findViewById(R.id.exce_send);

        exce_title.setText("自动记账已崩溃");
        exce_text.setText(getString(R.string.err));
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            filePath=bundle.getString("fileName");
          //  exce_text.setText(filePath);
        }
    }
    private void initListen(){
        exce_cancel.setOnClickListener(view -> {
            XUtil.exitApp();
        });
        exce_send.setOnClickListener(view -> {
            Logs.d(filePath);
           // Uri uri = Uri.fromFile(new File(filePath));
            Intent shareIntent2 = new Intent();
            Uri uri = FileProvider.getUriForFile(this,"cn.dreamn.qianji_auto" + ".fileprovider", new File(filePath));
           // grantUriPermission(getPackageName(),uri,Intent.FLAG_GRANT_READ_URI_PERMISSION);
            shareIntent2.putExtra(Intent.EXTRA_STREAM, uri);


            //重点:针对7.0以上的操作

            shareIntent2.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            shareIntent2.setClipData(ClipData.newRawUri(MediaStore.EXTRA_OUTPUT, uri));
            shareIntent2.setAction(Intent.ACTION_SEND);
           // shareIntent2.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent2.setType("*/*");
            startActivity(Intent.createChooser(shareIntent2, "分享到"));
        });

    }
}