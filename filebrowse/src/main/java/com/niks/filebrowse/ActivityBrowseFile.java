package com.niks.filebrowse;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.niks.baseutils.BaseAppCompatActivity;

import java.io.File;
import java.io.FilenameFilter;

public class ActivityBrowseFile extends BaseAppCompatActivity implements OnItemClickListener {
    ListView lview;
    ListViewAdapter lviewAdapter;
    private String filelists[];
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogbrowse);
        lview = (ListView) findViewById(R.id.adbilv1);
        lview.setOnItemClickListener(this);

        path = getIntent().getStringExtra("path");
        if (path != null && path.length() > 0)
            loadFileList(path);
    }

    FilenameFilter filter = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String filename) {
            File sel = new File(dir, filename);
            return (sel.isFile() || sel.isDirectory()) && !sel.isHidden();
        }
    };

    void loadFileList(String path) {

        try {
            File mypath = new File(path);
            if (mypath.exists()) {
                if (mypath.isDirectory()) {
                    filelists = mypath.list(filter);
                    if (filelists != null) {
                        lviewAdapter = new ListViewAdapter(this, filelists);
                        lview.setAdapter(lviewAdapter);
                    }
                } else if (mypath.isFile()) {
                    myToast(mypath.toString());
                    finish();
                }

            }
        } catch (Exception e) {
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_browse, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.browse_back) {
            backButtonClicked();
        }
        return super.onOptionsItemSelected(item);
    }

    void backButtonClicked() {

        loadFileList(getBackFolder(path));

    }

    String getBackFolder(String filepath) {

        if (filepath.equalsIgnoreCase("/mnt/sdcard") || filepath.equalsIgnoreCase("/mnt/extSdCard"))
            return filepath;
        String[] allfolder = path.split("/");
        String tempstr = "";
        for (int i = 1; i < allfolder.length - 1; i++) {
            tempstr = tempstr + "/" + allfolder[i];
        }

        path = tempstr;

        return tempstr;

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (filelists != null && filelists.length > 0) {
            File newpath = new File(path, filelists[position]);

            if (newpath.exists()) {
                if (newpath.isDirectory())
                    path = newpath.toString();
                loadFileList(newpath.toString());
            }
        }
    }

    public class ListViewAdapter extends BaseAdapter {
        Activity context;

        String[] list;

        public ListViewAdapter(Activity context, String[] list1) {
            super();
            this.context = context;
            this.list = list1;
        }

        public int getCount() {
            return list.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        private class ViewHolder {

            ImageView tvIcon;
            TextView tvName;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            LayoutInflater inflater = context.getLayoutInflater();
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.dialogbrowserow, null);
                holder = new ViewHolder();

                holder.tvIcon = (ImageView) convertView.findViewById(R.id.adbiiv);
                holder.tvName = (TextView) convertView.findViewById(R.id.adbitv);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            File check = new File(path, list[position]);
            if (check.isDirectory())
                holder.tvIcon.setBackgroundResource(R.drawable.directory_icon);
            else
                holder.tvIcon.setBackgroundResource(R.drawable.file_icon);
            holder.tvName.setText(list[position]);
            return convertView;
        }
    }


}