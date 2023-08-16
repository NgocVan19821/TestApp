package com.lutech.testapp
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.tlGallery
import kotlinx.android.synthetic.main.activity_main.vpGallery

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: FragmentPageAdapter

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vpGallery.isSaveEnabled = true
        vpGallery.isUserInputEnabled = true
        adapter = FragmentPageAdapter(supportFragmentManager, lifecycle)
        tlGallery.addTab(tlGallery.newTab().setText("Record"))
        tlGallery.addTab(tlGallery.newTab().setText("Listen"))
        vpGallery.adapter = adapter
        tlGallery.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab!= null){
                    vpGallery.currentItem = tab.position
                    Log.d("99999999999", "currentItem: " + vpGallery.currentItem)

                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
        vpGallery.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tlGallery.selectTab(tlGallery.getTabAt(position))
            }
        })


    }

}
