package com.example.sispak_kel_9;

// tentang.java
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import android.os.Bundle;

public class tentang extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentang);

        tabLayout = findViewById(R.id.tablayout_id);
        viewPager = findViewById(R.id.viewpager_id);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Personal(), "tentang apk", R.drawable.apk);
        adapter.addFragment(new Pro(), "cara penggunaan apk", R.drawable.account_question_outline);
        adapter.addFragment(new Pakar_info(), "Info Pakar", R.drawable.pakar);
        adapter.addFragment(new kelompok(), "About Us", R.drawable.kelompok);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        // Menambahkan ikon ke setiap tab
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setIcon(adapter.getIconId(i));
            }
        }
    }
}
