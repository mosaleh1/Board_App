package com.example.boardapp

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.view.get
import androidx.core.view.marginStart
import com.example.boardapp.databinding.ActivityMainBinding
import com.example.boardapp.model.BoardActions
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener {

    //private val colors = arrayOf(Color.RED, Color.GREEN, Color.BLUE, Color.BLACK)

    val selectedColor = Color.BLACK

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tabLayout.addOnTabSelectedListener(this)
        binding.boardCustomView.changeColor(selectedColor)
        binding.tabLayout.tabIconTint = ColorStateList.valueOf(Color.BLACK)
        binding.tabLayout.setSelectedTabIndicatorColor(Color.BLACK)
        binding.colorGroup.setOnCheckedChangeListener { radioGroup, _ ->
            when(radioGroup.checkedRadioButtonId){
                 R.id.red_color->{
                     binding.boardCustomView.changeColor(Color.RED)
                     binding.tabLayout.tabIconTint = ColorStateList.valueOf(Color.RED)
                     binding.tabLayout.setSelectedTabIndicatorColor(Color.RED)
                 }
                R.id.green_color->{
                    binding.boardCustomView.changeColor(Color.GREEN)
                    binding.tabLayout.tabIconTint = ColorStateList.valueOf(Color.GREEN)
                    binding.tabLayout.setSelectedTabIndicatorColor(Color.GREEN)
                }
                R.id.blue_color->{
                    binding.boardCustomView.changeColor(Color.BLUE)
                    binding.tabLayout.tabIconTint = ColorStateList.valueOf(Color.BLUE)
                    binding.tabLayout.setSelectedTabIndicatorColor(Color.BLUE)
                }
                R.id.black_color->{
                    binding.boardCustomView.changeColor(Color.BLACK)
                   binding.tabLayout.tabIconTint = ColorStateList.valueOf(Color.BLACK)
                    binding.tabLayout.setSelectedTabIndicatorColor(Color.BLACK)
                }
            }
            binding.tabLayout.selectTab(binding.tabLayout.getTabAt(0))
            binding.colorGroup.visibility = View.INVISIBLE

        }
    }

    private fun changeColor() {
        binding.colorGroup.visibility = View.VISIBLE
    }

    override fun onTabSelected(tab: TabLayout.Tab) {
        Log.d(TAG, "onTabSelected: ")
        Toast.makeText(this@MainActivity, "Clicked", Toast.LENGTH_LONG).show()
        when (tab.position) {
            0 -> {
                binding.boardCustomView.changeState(BoardActions.ActionPen)
            }
            1 -> {
                Toast.makeText(this@MainActivity, "Clicked", Toast.LENGTH_LONG).show()
                binding.boardCustomView.changeState(BoardActions.ActionArrow)
            }
            2 -> {
                binding.boardCustomView.changeState(BoardActions.ActionRectangle)
            }
            3 -> {
                binding.boardCustomView.changeState(BoardActions.ActionCircle)
            }
            4 -> {
                changeColor()
            }

        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab) {
        if (tab.position ==4){

        }
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }
}

private const val TAG = "MainActivity"