package com.dm.launcher;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import java.util.List;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.content.Intent;
import android.widget.EditText;
import android.view.View.OnLongClickListener;
import android.net.Uri;
import android.provider.Settings;

public class AppNameAdapter extends RecyclerView.Adapter<AppNameAdapter.ViewHolder> {

    public EditText searchBar;
    int nb, nf, sb, sf, textSize;

    public List<AppInfo> data;
    public AppNameAdapter(List<AppInfo> data, EditText searchBar, int nb, int nf, int sb, int sf, int textSize) {
        this.data = data;
        this.searchBar = searchBar;
        this.nb = nb; this.nf = nf; this.sb = sb; this.sf = sf;
        this.textSize = textSize;
    }

    @Override
    public AppNameAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_name, parent, false);
        return new ViewHolder(rowItem);
    }

    @Override
    public void onBindViewHolder(AppNameAdapter.ViewHolder holder, int position) {
        if (position == 0) {
            holder.appName.setBackgroundColor(sb);
            holder.appName.setTextColor(sf);
        } else {
            holder.appName.setBackgroundColor(nb);
            holder.appName.setTextColor(nf);
        }
        holder.appName.setText(this.data.get(position).title);
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView appName;

        public ViewHolder(View view) {
            super(view);

            this.appName = view.findViewById(R.id.app_name);
            
            appName.setTextSize(textSize);
            
            view.setOnClickListener(this);
            view.setOnLongClickListener(new OnLongClickListener() { 
                    @Override
                    public boolean onLongClick(View view) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.fromParts("package", data.get(getLayoutPosition()).packageName, null));
                        view.getContext().startActivity(intent);

                        return true;
                    }
                });

        }

        @Override
        public void onClick(View view) {
            if (data.get(getLayoutPosition()).packageName == null) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                                           Uri.parse("https://www.google.com/search?q=" + searchBar.getText().toString()));
                view.getContext().startActivity(intent);
            } else {
                Intent intent = new Intent();
                intent.setClassName(data.get(getLayoutPosition()).packageName,
                                    data.get(getLayoutPosition()).activity);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);
                searchBar.setText("");
            }
            
        }
    }
}
