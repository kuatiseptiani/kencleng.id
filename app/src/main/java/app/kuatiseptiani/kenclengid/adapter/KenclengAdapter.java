package app.kuatiseptiani.kenclengid.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import app.kuatiseptiani.kenclengid.DetailKenclengActivity;
import app.kuatiseptiani.kenclengid.R;
import app.kuatiseptiani.kenclengid.entity.Kencleng;
import app.kuatiseptiani.kenclengid.holder.KenclengHolder;
import app.kuatiseptiani.kenclengid.util.Consts;

/**
 * Created by kuatiseptiani on 07/01/19.
 */

public class KenclengAdapter extends RecyclerView.Adapter<KenclengHolder> {

    private List<Kencleng>kenclengs;
    private KenclengListener listener;

    public KenclengAdapter(List<Kencleng> kenclengs) {
        this.kenclengs = kenclengs;
    }

    public void setListener(KenclengListener listener) {
        this.listener = listener;
    }

    @Override
    public KenclengHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kencleng, parent, false);
        final KenclengHolder holder = new KenclengHolder(view);

        final Context context = holder.itemView.getContext();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //definisikan position untuk getMahasiswa.
                int adapterPosition = holder.getAdapterPosition();
                Kencleng kencleng = kenclengs.get(adapterPosition);

                Intent detailIntent = new Intent(context, DetailKenclengActivity.class);
                detailIntent.putExtra("kenclemg", kencleng);
                detailIntent.putExtra(Consts.KEY_ACTION_DETAIL, Consts.INTENT_EDIT);
                context.startActivity(detailIntent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(KenclengHolder holder, final int position) {
        holder.txtNominal.setText(kenclengs.get(position).getNominal());
        holder.txtCatatan.setText(kenclengs.get(position).getCatatan());

        //tambahkan fungsi delete
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDelete(kenclengs.get(position).getId());
            };
        });
    }

    @Override
    public int getItemCount() {
        return kenclengs.size();
    }

    public interface KenclengListener {
        void onDelete(int kcId);
    }

}
