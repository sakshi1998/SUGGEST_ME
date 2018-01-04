package test.sakshi.com.suggest_me.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import test.sakshi.com.suggest_me.R;
import test.sakshi.com.suggest_me.data.columnndes;
import test.sakshi.com.suggest_me.utils.Constants;
import test.sakshi.com.suggest_me.utils.CursorRecyclerViewAdapter;




public class SituationListAdapter extends CursorRecyclerViewAdapter<SituationListAdapter.SituationViewHolder> {

    private Context context;
    private Cursor cursor;


    public static class SituationViewHolder extends RecyclerView.ViewHolder {
        TextView situationTitle;
        LinearLayout itemContainer;

        public SituationViewHolder(View v) {
            super(v);
            situationTitle = (TextView) v.findViewById(R.id.situation_name);
            itemContainer = (LinearLayout) v.findViewById(R.id.layout_situation_item);
        }
    }

    public SituationListAdapter(Context context, Cursor cursor) {
        super(context,cursor);
        this.context = context;
    }

    @Override
    public SituationListAdapter.SituationViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_situation, parent, false);
        return new SituationViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final SituationViewHolder holder, Cursor cursor, final int position) {

        final Integer id = cursor.getInt(cursor.getColumnIndex(columnndes.entry.COLUMN_ID));
        final String name = cursor.getString(cursor.getColumnIndex(columnndes.entry.COLUMN_NAME));

        holder.situationTitle.setText(name);
        Log.v(name,"dff");
        holder.itemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.putExtra("situationId",id);
                i.putExtra("situationName",name);
                ((Activity) context).setResult(Constants.SITUATION_REQUEST_CODE, i);
                ((Activity) context).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }
}
