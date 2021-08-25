package com.example.promena.voter.Activity.Main;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.promena.voter.Fragments.ChatFragment;
import com.example.promena.voter.Fragments.ChatHomeFragment;
import com.example.promena.voter.R;

import java.util.HashMap;
import java.util.Map;

public class ChatMainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_main);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private int tabsCount;
        private Map<String, Item> tabsMap;
        private String[] tabsTags;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);

            tabsMap = new HashMap<>();

            // create a map with all tabs
            tabsMap.put(getString(R.string.tag_home),
                    new Item(getString(R.string.tab_home_title), 1));
            tabsMap.put(getString(R.string.tag_chat),
                    new Item(getString(R.string.tab_chat_title), 1));
            /*tabsMap.put(getString(R.string.tag_profile),
                    new Item(getString(R.string.tab_profile_title), 1));*/

            // retrieve tab tags
            tabsTags = getResources().getStringArray(R.array.tab_tags);
            tabsCount = tabsTags.length; // count tab tags
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.

            String tabTag = getTagByPosition(position);
            if (tabTag.equals(getString(R.string.tag_home))) {
                return ChatHomeFragment.newInstance();
            } else if (tabTag.equals(getString(R.string.tag_chat))) {
                return ChatFragment.newInstance();
            }  /*if (tabTag.equals(getString(R.string.tag_profile))) {
                return UserProfileFragment.newInstance();
            }*/ else {
                // default load home
                return ChatHomeFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return tabsCount;
        }

        private String getTagByPosition(int position) {
            return tabsTags[position];
        }

        private String getTitleByTag(String tag) {
            return tabsMap.get(tag).getTitle();
        }

        private int getIconByTag(String tag) {
            return tabsMap.get(tag).getIcon();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String tabTag = getTagByPosition(position);
            return getTitleByTag(tabTag);
        }

        private class Item {
            private String title;
            private int icon;

            private Item(String title, int icon) {
                setTitle(title);
                setIcon(icon);
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getIcon() {
                return icon;
            }

            public void setIcon(int icon) {
                this.icon = icon;
            }

            @Override
            public String toString() {
                return "Item{" +
                        "title='" + title + '\'' +
                        ", icon='" + icon + '\'' +
                        '}';
            }
        }
    }

}