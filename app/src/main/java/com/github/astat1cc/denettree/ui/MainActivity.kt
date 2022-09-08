package com.github.astat1cc.denettree.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.github.astat1cc.denettree.R
import com.github.astat1cc.denettree.ui.fragment.NodeFragment
import org.web3j.crypto.Hash

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, NodeFragment())
            .commit()
    }
}