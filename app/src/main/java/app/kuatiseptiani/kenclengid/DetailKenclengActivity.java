package app.kuatiseptiani.kenclengid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import app.kuatiseptiani.kenclengid.entity.Kencleng;
import app.kuatiseptiani.kenclengid.network.Network;
import app.kuatiseptiani.kenclengid.network.Routes;
import app.kuatiseptiani.kenclengid.util.Consts;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.name;

/**
 * Created by kuatiseptiani on 07/01/19.
 */

public class DetailKenclengActivity extends AppCompatActivity {

    private EditText edtNominal, edtCatatan;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kencleng);

        //casting untuk semua view
        edtNominal = (EditText) findViewById(R.id.edt_nominal);
        edtCatatan = (EditText) findViewById(R.id.edt_catatan);
        btnAdd = (Button) findViewById(R.id.btn_add);

        String action = getIntent().getStringExtra(Consts.KEY_ACTION_DETAIL);
        switch (action) {
            case Consts.INTENT_ADD:
                btnAdd.setText("Tambah Tabungan");
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer nominal = edtNominal.getText().length();
                        String catatan = edtCatatan.getText().toString();
                        if (!nominal.isEmpty()) {
                            addNewKencleng(nominal, catatan);
                        } else {
                            Toast.makeText(DetailKenclengActivity.this,
                                    "maaf, nama dan nim tidak boleh kosong.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
                break;
            case Consts.INTENT_EDIT:
                final Kencleng kencleng = (Kencleng) getIntent().getSerializableExtra("kencleng");
                edtNominal.setText(kencleng.getNominal());
                edtCatatan.setText(kencleng.getCatatan());

                btnAdd.setText("UPDATE DATA");
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        kencleng.setNominal(edtNominal.getText().length());
                        kencleng.setCatatan(edtCatatan.getText().toString());
                        updateKencleng(kencleng);
                    }
                });
                break;
        }
    }

    private void updateKencleng(Kencleng kencleng) {
        Routes services = Network.request().create(Routes.class);

        String kenclengId = String.valueOf(kencleng.getId());
        Integer nominal = kencleng.getNominal();
        String catatan = kencleng.getCatatan();

        services.updateKencleng(kenclengId, nominal, catatan).enqueue(new Callback<Kencleng>() {
            @Override
            public void onResponse(Call<Kencleng> call, Response<Kencleng> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(DetailKenclengActivity.this,
                            "update berhasil!",
                            Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    onErrorAddKencleng();
                }
            }

            @Override
            public void onFailure(Call<Kencleng> call, Throwable t) {
                onErrorAddKencleng();
            }
        });
    }
    @Override
            public void onFailure(Call<Kencleng> call, Throwable t) {
                onErrorAddKencleng();
            }
        });
    }

    private void onErrorAddKencleng() {
        Toast.makeText(DetailKenclengActivity.this,
                "Maaf, terjadi kesalahan",
                Toast.LENGTH_LONG).show();
    }

}

