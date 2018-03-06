package ly.developer.mohammedalosifi1990.muslembagv2.ui.QuranListen;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import ly.developer.mohammedalosifi1990.muslembagv2.R;
import ly.developer.mohammedalosifi1990.muslembagv2.wedgit.CustomTextView;

/**
 * Created by pc on 05/03/18.
 */

public class QuranAdapter extends RecyclerView.Adapter<QuranAdapter.QuranViewHolder>  {

    Context context;

    public QuranAdapter(Context context) {
        this.context = context;
    }

    @Override
    public QuranViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new QuranViewHolder(LayoutInflater.from(context).inflate(R.layout.shukh_list_items,parent,false));
    }

    @Override
    public void onBindViewHolder(QuranViewHolder holder, int position) {
        holder.bindView(shekhNames[position],quranPrefix[position]);
    }

    @Override
    public int getItemCount() {
        return shekhNames.length;
    }


    public class QuranViewHolder extends RecyclerView.ViewHolder
    {
        CustomTextView tvShekhName,tvShekhName2;
        LinearLayout ll;
        View view;
        public QuranViewHolder(View itemView) {
            super(itemView);
            tvShekhName=(CustomTextView)itemView.findViewById(R.id.tvShekhName);
            tvShekhName2=(CustomTextView)itemView.findViewById(R.id.tvShekhName2);
            ll=(LinearLayout)itemView.findViewById(R.id.ll);
        }

        public void bindView(String name,String type){
            tvShekhName.setText(name);
            tvShekhName2.setText(type);
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }


    public interface onItemClickListener{
        public void onItemClick();
    }
    public String[] shekhNames={
            "aaaaaaa"
            ,"Aaaaaaaaaaa"
    };

    public String[] quranPrefix={
            "bbbbbbbbbbbbb"
            ,"bbbbbbbbbbbbb"
    };

}
