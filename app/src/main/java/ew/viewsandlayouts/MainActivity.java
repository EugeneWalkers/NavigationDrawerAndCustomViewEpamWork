package ew.viewsandlayouts;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FragmentTransaction fTrans;
    FragmentManager fManager;

    Uri headerImage = Uri.parse("http://bipbap.ru/wp-content/uploads/2017/04/leto_derevo_nebo_peyzazh_dom_derevya_domik_priroda_3000x2000.jpg");
    FragmentItemOne fIO;
    FragmentItemTwo fIT;
    Toolbar toolbar;
    int selectedItem;
    final String ITEM = "item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fIO = new FragmentItemOne();
        fIT = new FragmentItemTwo();
        fManager = getFragmentManager();
        fTrans = fManager.beginTransaction();
        toolbar = findViewById(R.id.toolbar);
        if (savedInstanceState == null){
            fTrans.add(R.id.content_frame, fIO);
            fTrans.commit();
            toolbar.setTitle(getResources().getString(R.string.item_1));
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        ImageView headerImageToLoad = headerView.findViewById(R.id.header_image);
        Picasso.get().load(headerImage).into(headerImageToLoad);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toolbar = findViewById(R.id.toolbar);
        switch (selectedItem){
            default:
            case R.id.item_1:
                fTrans.add(R.id.content_frame, fIO);
                toolbar.setTitle(getResources().getString(R.string.item_1));
                break;
            case R.id.item_2:
                fTrans.add(R.id.content_frame, fIT);
                toolbar.setTitle(getResources().getString(R.string.item_2));
                break;
        }
        fTrans.commit();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass;

        switch (id) {
            default:
            case R.id.item_1:
                fragmentClass = FragmentItemOne.class;
                break;

            case R.id.item_2:
                fragmentClass = FragmentItemTwo.class;
                break;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        fManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        item.setChecked(true);
        toolbar.setTitle(item.getTitle());
        selectedItem = id;
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ITEM, selectedItem);
    }
}
