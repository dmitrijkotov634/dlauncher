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
import android.widget.ImageView;
import android.widget.LinearLayout;

public class AppNameAdapter extends RecyclerView.Adapter<AppNameAdapter.ViewHolder> {

    private EditText searchBar;
    private int nb, nf, sb, sf, textSize;

    private boolean icons = false;

    public List<AppInfo> data;

    public AppNameAdapter(List<AppInfo> data, EditText searchBar, int nb, int nf, int sb, int sf, int textSize, boolean icons) {
        this.data = data;
        this.searchBar = searchBar;
        this.icons = icons; this.textSize = textSize;
        this.nb = nb; this.nf = nf; this.sb = sb; this.sf = sf;

    }

    @Override
    public AppNameAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowItem;

        if (icons) 
            rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_name_icons, parent, false);
        else 
            rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_name, parent, false);

        return new ViewHolder(rowItem);
    }

    @Override
    public void onBindViewHolder(AppNameAdapter.ViewHolder holder, int position) {
        if (position == 0) {
            holder.item.setBackgroundColor(sb);
            holder.name.setTextColor(sf);
        } else {
            holder.item.setBackgroundResource(0x00000000);
            holder.name.setTextColor(nf);
        }
        holder.name.setText(this.data.get(position).title);

        if (icons) {
            holder.icon.setBackgroundDrawable(this.data.get(position).icon);
            holder.icon.setLayoutParams(new LinearLayout.LayoutParams(textSize * 2, textSize * 2));
        }
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout item;
        private ImageView icon;
        private TextView name;

        public ViewHolder(View view) {
            super(view);

            this.item = view.findViewById(R.id.item);
            this.name = view.findViewById(R.id.name);

            if (icons)
                this.icon = view.findViewById(R.id.icon);

            name.setTextSize(textSize);

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
            String pkg = data.get(getLayoutPosition()).packageName;

            if (pkg == null) {
                view.getContext().startActivity(new Intent(Intent.ACTION_VIEW,
                                                           Uri.parse("https://www.google.com/search?q=" + searchBar.getText().toString())));
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
