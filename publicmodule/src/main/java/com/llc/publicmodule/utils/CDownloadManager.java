package com.llc.publicmodule.utils;

import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.File;



public class CDownloadManager {
    DownloadManager downloadManager;
    Context context;

    public CDownloadManager(Context context) {
        super();
        this.context = context;
        downloadManager = (DownloadManager) context
                .getSystemService(Context.DOWNLOAD_SERVICE);
    }

    /**
     *
     * @param url
     *            下载地址
     * @param downloadId
     *            下载的id
     * @param isNotification
     *            是否通知
     */
    long download_id;
    String fileName;

    public void startDownLoad(String url, boolean isNotification) {
        Request request = new Request(
                Uri.parse(url));
        request.setAllowedNetworkTypes(Request.NETWORK_WIFI
                | Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(false);// 是否使用漫游
        // 设置文件类型
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        String mimeString = mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap
                .getFileExtensionFromUrl(url));
        request.setMimeType(mimeString);
        // 在通知栏中显示
        request.setShowRunningNotification(isNotification);
        request.setVisibleInDownloadsUi(true);
        fileName = url.substring(url.lastIndexOf("/") + 1);
        // sdcard的目录下的download文件夹
        String str = "/pronetwaycache/" + fileName;
        String file = Environment.getExternalStorageDirectory() + str;
        CFileUtils cFileUtils = new CFileUtils();
        cFileUtils.deleteFile(new File(file));
        request.setDestinationInExternalPublicDir("/pronetwaycache/", fileName);
        request.setTitle(fileName);
        int state = context.getPackageManager().getApplicationEnabledSetting(
                "com.android.providers.downloads");
        if (state != 0) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(url);
            intent.setData(content_url);
            context.startActivity(intent);
            return ;
        }
        download_id = downloadManager.enqueue(request);
        context.registerReceiver(receiver, new IntentFilter(
                DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
            if (id == download_id) {
                DownloadManager.Query query = new DownloadManager.Query();

                Cursor c = downloadManager.query(query);
                if (c.moveToFirst()) {
                    int status = c.getInt(c
                            .getColumnIndex(DownloadManager.COLUMN_STATUS));
                    switch (status) {
                        case DownloadManager.STATUS_PAUSED:
                            Log.v("down", "STATUS_PAUSED");
                        case DownloadManager.STATUS_PENDING:
                            Log.v("down", "STATUS_PENDING");
                        case DownloadManager.STATUS_RUNNING:
                            // 正在下载，不做任何事情
                            Log.v("down", "STATUS_RUNNING");
                            break;
                        case DownloadManager.STATUS_SUCCESSFUL:
                            // 完成
                            Log.v("down", "下载完成");
                            String str = "/pronetwaycache/" + fileName;
                            String fileName = Environment
                                    .getExternalStorageDirectory() + str;
                            Intent intent2 = new Intent(
                                    Intent.ACTION_VIEW);
                            File file = new File(fileName);
                            boolean b = file.exists();
                            intent2.setDataAndType(Uri.fromFile(file),
                                    "application/vnd.android.package-archive");
                            context.startActivity(intent2);
                            break;
                        case DownloadManager.STATUS_FAILED:
                            // 清除已下载的内容，重新下载
                            Log.v("down", "STATUS_FAILED");
                            downloadManager.remove(download_id);
                            break;
                    }
                }
                context.unregisterReceiver(receiver);
            }

        }
    };

}
