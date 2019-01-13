package app.kuatiseptiani.kenclengid.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import app.kuatiseptiani.kenclengid.R;

/**
 * Created by kuatiseptiani on 07/01/19.
 */

public class KenclengHolder extends RecyclerView.ViewHolder {

    public TextView txtNominal;
    public TextView txtCatatan;
    public Button btnDelete;

    public KenclengHolder(View itemView) {
        super(itemView);
        txtNominal = (TextView) itemView.findViewById(R.id.txt_nominal);
        txtCatatan = (TextView) itemView.findViewById(R.id.txt_catatan);
        btnDelete = (Button) itemView.findViewById(R.id.btn_delete);
    }

}
