package com.android.sunghyun.zigbang_project;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


/*
    1. 그래들에 design 라이브러리 추가
    2. main xml 에 TabLayut , ViewPager 추가
 */
public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    static final int FRAGMENT_COUNT = 3;
    HomeFragment home;
    MapFragment map;
    PostFragment etc;


    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        home = new HomeFragment();
        map = new MapFragment();
        etc = new PostFragment();


        TabLayout tab = (TabLayout) findViewById(R.id.tab);
        tab.addTab(tab.newTab().setText("Home"));
        tab.addTab(tab.newTab().setText("지도에서찾기"));
        tab.addTab(tab.newTab().setText("방등록하기"));

        pager = (ViewPager) findViewById(R.id.pager);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);

        // 페이저가 변경되었을때 탭을 변경해주는 리스너
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));

        // 탭에 페이저를 연결해주는 리스너
        tab.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(pager));
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Toast.makeText(this, "서브 프래그먼트에서 클릭됨", Toast.LENGTH_SHORT).show();
    }


    class PagerAdapter extends FragmentStatePagerAdapter {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return FRAGMENT_COUNT;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch(position){
                case 0: fragment = home; break;
                case 1: fragment = map; break;
                case 2: fragment = etc; break;
            }
            return fragment;
        }
    }
}
