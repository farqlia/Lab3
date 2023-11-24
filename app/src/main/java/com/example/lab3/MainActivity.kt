package com.example.lab3

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.lab3.databinding.ActivityMainBinding

lateinit var mainBinding: ActivityMainBinding
lateinit var catFragment: CatFragment
lateinit var dogFragment: DogFragment
lateinit var hamsterFragment: HamsterFragment

lateinit var transaction : FragmentTransaction

val CAT_TAG = "CatFragment"
val DOG_TAG = "DogFragment"
val HAMSTER_TAG = "HamsterFragment"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        setSupportActionBar(mainBinding.mainToolbar)
        mainBinding.mainToolbar.showOverflowMenu()


        if (savedInstanceState == null){
            dogFragment = DogFragment.newInstance("Dog", "Fragment")
            catFragment = CatFragment.newInstance("Cat", "Fragment")
            hamsterFragment = HamsterFragment.newInstance("Hamster", "Fragment")

            transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.fragmentContainerView, catFragment, CAT_TAG)
            transaction.detach(catFragment)
            transaction.add(R.id.fragmentContainerView, dogFragment, DOG_TAG)
            transaction.detach(dogFragment)
            transaction.add(R.id.fragmentContainerView, hamsterFragment, HAMSTER_TAG)
            transaction.detach(hamsterFragment)
            transaction.commit()

        } else {
            dogFragment = supportFragmentManager.findFragmentByTag(DOG_TAG) as DogFragment
            catFragment = supportFragmentManager.findFragmentByTag(CAT_TAG) as CatFragment
            hamsterFragment = supportFragmentManager.findFragmentByTag(HAMSTER_TAG) as HamsterFragment

        }

        val currentFragment = getCurrentFragment()
        if (currentFragment != null){
            detachAttach(currentFragment, false)
        }

        val onFragmentButtonListener = View.OnClickListener { view ->
            when(view){
                mainBinding.catButton -> {
                    detachAttach(catFragment)
                }
                mainBinding.dogButton -> {
                    detachAttach(dogFragment)
                }
                mainBinding.hamsterButton -> {
                    detachAttach(hamsterFragment)
                }
            }

        }

        mainBinding.hamsterButton.setOnClickListener(onFragmentButtonListener)
        mainBinding.catButton.setOnClickListener(onFragmentButtonListener)
        mainBinding.dogButton.setOnClickListener(onFragmentButtonListener)

    }

    fun detachAttach(attachFragment: Fragment, detachCurrent: Boolean = true){
        val fragTransaction = supportFragmentManager.beginTransaction()
        val currentFragment = getCurrentFragment()
        if (detachCurrent && currentFragment != null){
            fragTransaction.detach(currentFragment)
        }
        fragTransaction.attach(attachFragment)
        setCurrentFragment(attachFragment)
        fragTransaction.commit()
    }

    fun setCurrentFragment(fragment: Fragment){
        val data = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val editor = data.edit()
        editor.putString("fragment_tag", fragment.tag)
        editor.apply()
    }

    fun getCurrentFragment(): Fragment? {
        val data = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val key = data.getString("fragment_tag", null)
        return supportFragmentManager.findFragmentByTag(key)
    }

}