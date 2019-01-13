package app.kuatiseptiani.kenclengid;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import app.kuatiseptiani.kenclengid.adapter.KenclengAdapter;
import app.kuatiseptiani.kenclengid.entity.IsiKencleng;
import app.kuatiseptiani.kenclengid.entity.Kencleng;
import app.kuatiseptiani.kenclengid.network.Network;
import app.kuatiseptiani.kenclengid.network.Routes;
import app.kuatiseptiani.kenclengid.util.Consts;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kuatiseptiani on 07/01/19.
 */

public class MainActivity extends AppCompatActivity {


    //deklarasikan recyclerviewnya
    private RecyclerView lstKencleng;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //casting recyclerviewnya dari id lst_mahasiswa yang ada di activity_main
        lstKencleng = (RecyclerView) findViewById(R.id.lst_kencleng);

        //set layout manager untuk lstMahasiswa
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        lstKencleng.setLayoutManager(linearLayoutManager);

        //requestDaftarMahasiswa();

        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent(MainActivity.this, DetailKenclengActivity.class);
                addIntent.putExtra(Consts.KEY_ACTION_DETAIL, Consts.INTENT_ADD);
                startActivity(addIntent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestIsiKencleng();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //menampilkan menu di activity
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                //ketika icon refresh di klik, maka panggil ...
                requestIsiKencleng();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void requestIsiKencleng() {
        //pertama, memanggil request() dari retrofit yang sudah dibuat
        final Routes services = Network.request().create(Routes.class);

        //kita melakukan request terhadap getMahasiswa()
        services.getKencleng().enqueue(new Callback<IsiKencleng>() {
            @Override
            public void onResponse(Call<IsiKencleng> call, Response<IsiKencleng> response) {
                //mengecek request yang dilakukan, berhasil/tidak
                if (response.isSuccessful()) {
                    //casting data yang didapatkan, menjadi DaftarMahasiswa
                    IsiKencleng kenclengs = response.body();

                    //get title
                    Log.d("kuatiseptiani", kenclengs.getTitle());

                    //tampilkan daftar mahasiswa di recyclerview
                    KenclengAdapter adapter = new KenclengAdapter(kenclengs.getData());

                    //untuk handle button delete di item mahasiswa
                    //fungsinya untuk menghapus data yang ada di API
                    adapter.setListener(new KenclengAdapter.KenclengListener() {
                        @Override
                        public void onDelete(int kcId) {
                            String id = String.valueOf(kcId); //konversi int to string
                            deleteKencleng(services, id);
                        }
                    });

                    lstKencleng.setAdapter(adapter);
                } else {
                    //ketika data tidak berhasil di load
                    onKenclengError();
                }
            }

            @Override
            public void onFailure(Call<IsiKencleng> call, Throwable t) {
                //ketika data tidak berhasil di load
                onKenclengError();
            }
        });
    }

    private void onKenclengError() {
        Toast.makeText(
                MainActivity.this,
                "Gagal. Silahkan periksa koneksi internet anda.",
                Toast.LENGTH_LONG).show();
    }

    private void deleteKencleng(final Routes services, final String kcId) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.app_name);
        alert.setMessage("are you sure?");
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                services.deleteKencleng(kcId).enqueue(new Callback<Kencleng>() {
                    @Override
                    public void onResponse(Call<Kencleng> call, Response<Kencleng> response) {
                        if (response.isSuccessful()) {
                            requestIsiKencleng();
                        } else {
                            onKenclengError();
                        }
                    }

                    @Override
                    public void onFailure(Call<Kencleng> call, Throwable t) {
                        onKenclengError();
                    }
                });
            }
        });
        alert.show();
    }

}
