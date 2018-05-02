package com.example.alumfial1.ventasaplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.alumfial1.ventasaplication.view.ProductoFragment;
import com.example.alumfial1.ventasaplication.view.VentaFragment;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private VentaFragment ventaFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        if(findViewById(R.id.fragment_container)!=null){
            // Create a new Fragment to be placed in the activity layout
                ventaFragment = new VentaFragment();
            //En caso de que la actividad se haya iniciado por un Intent con parametros
            // estos los pasamos al fragment como argumentos



//   --> ventaFragment.setArguments(getIntent().getExtras());


            // Añadirmos el fragment al fragment_container(FrameLayout)
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container,ventaFragment).commit();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BoletaFragment boletaFragment = new BoletaFragment();

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, boletaFragment)
                                .addToBackStack(null).commit();

             //   Snackbar.make(view, "Vista previa de la venta", Snackbar.LENGTH_LONG)
               //         .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Bundle arg=new Bundle();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (id){
            case R.id.nav_sale:
                ventaFragment=new VentaFragment();
                //argumentos
//         -->       arg.putInt("saludo", 1);
//         -->       ventaFragment.setArguments(arg);
                // Remplazamos el fragmentos y añadimos la transaccion apra que el usuario vuelva
                // al fragmento anterior (vuela atras)
                // Commit the transaction
                transaction.replace(R.id.fragment_container, ventaFragment);
                transaction.addToBackStack(null).commit();
                break;
            case R.id.nav_product:
                ProductoFragment productFragment=new ProductoFragment();
                //argumentos
//         -->       arg.putInt("saludo", 1);
//         -->       ventaFragment.setArguments(arg);
                // Remplazamos el fragmentos y añadimos la transaccion apra que el usuario vuelva
                // al fragmento anterior (vuela atras)
                // Commit the transaction
                transaction.replace(R.id.fragment_container, productFragment);
                transaction.addToBackStack(null).commit();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void cambiarFragment(Fragment frag){
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container,frag).commit();
    }
}
