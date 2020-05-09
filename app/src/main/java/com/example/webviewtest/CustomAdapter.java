package com.example.webviewtest;

import android.app.AlertDialog;
import android.content.Context;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private ArrayList<Food> mList;
    private Context mContext;

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
//        protected TextView mName;
//        protected TextView mNowDate;
//        protected TextView mDate;
//        protected TextView mCount;

        private TextView name;
        private TextView nowDate;
        private TextView date;
        private TextView count;

        public CustomViewHolder(@NonNull View view) {
            super(view);
//            this.mName = (TextView)view.findViewById(R.id.name_listitem);
//            this.mNowDate = (TextView)view.findViewById(R.id.nowDate_listitem);
//            this.mDate = (TextView)view.findViewById(R.id.date_listitem);
//            this.mCount = (TextView)view.findViewById(R.id.count_listitem);
//            view.setOnCreateContextMenuListener(this);

            this.name = (TextView)view.findViewById(R.id.name_listitem);
            this.nowDate = (TextView)view.findViewById(R.id.nowDate_listitem);
            this.date = (TextView)view.findViewById(R.id.date_listitem);
            this.count = (TextView)view.findViewById(R.id.count_listitem);
            view.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem Edit = menu.add(Menu.NONE, 1001, 1, "수정");
            MenuItem Delete = menu.add(Menu.NONE, 1002, 2, "삭제");
            Edit.setOnMenuItemClickListener(onEditMenu);
            Delete.setOnMenuItemClickListener(onEditMenu);
        }

        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case 1001:
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        View view = LayoutInflater.from(mContext).inflate(R.layout.edit_box, null, false);
                        builder.setView(view);
                        final Button ButtonSubmit = (Button)view.findViewById(R.id.button_dialog_submit);
                        final EditText etName = (EditText)view.findViewById(R.id.et_dialog_name);
                        final EditText etNowDate = (EditText)view.findViewById(R.id.et_dialog_nowDate);
                        final EditText etDate = (EditText)view.findViewById(R.id.et_dialog_date);
                        final EditText etCount = (EditText)view.findViewById(R.id.et_dialog_count);

                        etName.setText(mList.get(getAdapterPosition()).getName());
                        etNowDate.setText(mList.get(getAdapterPosition()).getNowDate());
                        etDate.setText(mList.get(getAdapterPosition()).getDate());
                        etCount.setText(mList.get(getAdapterPosition()).getCount());

                        final AlertDialog dialog = builder.create();
                        ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String strName = etName.getText().toString();
                                String strNowDate = etNowDate.getText().toString();
                                String strDate = etDate.getText().toString();
                                String strCount = etCount.getText().toString();
                                Food food = new Food(strName,strNowDate,strDate,strCount);
                                mList.set(getAdapterPosition(), food);
                                notifyItemChanged(getAdapterPosition());
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                        break;

                    case 1002:
                        mList.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(), mList.size());

                        break;
                }
                return true;
            }
        };
    }

//    public CustomAdapter(ListActivity listActivity, ArrayList<Food> list) {
//        this.mList = list;
//    }

    public CustomAdapter(Context context, ArrayList<Food> list) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewHolder, int position) {
        viewHolder.name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        viewHolder.nowDate.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        viewHolder.date.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        viewHolder.count.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

        viewHolder.name.setGravity(Gravity.CENTER);
        viewHolder.nowDate.setGravity(Gravity.CENTER);
        viewHolder.date.setGravity(Gravity.CENTER);
        viewHolder.count.setGravity(Gravity.CENTER);

        viewHolder.name.setText(mList.get(position).getName());
        viewHolder.nowDate.setText(mList.get(position).getNowDate());
        viewHolder.date.setText(mList.get(position).getDate());
        viewHolder.count.setText(mList.get(position).getCount());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}
