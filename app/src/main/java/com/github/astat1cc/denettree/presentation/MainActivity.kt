package com.github.astat1cc.denettree.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.astat1cc.denettree.R
import com.github.astat1cc.denettree.presentation.fragment.NodeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, NodeFragment())
            .commit()
    }
}