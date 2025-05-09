package com.example.whatsinmycart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.whatsinmycart.databinding.ActivityMainBinding

private const val TAG_CART = "cart_fragment"
private const val TAG_LIST = "list_fragment"
private const val TAG_SEARCH = "search_fragment"
private const val TAG_MY_MENU = "my_menu_fragment"

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setFragment(TAG_CART, CartFragment())

        binding.bNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.cart -> setFragment(TAG_CART, CartFragment())
                R.id.list -> setFragment(TAG_LIST, ListFragment())
                R.id.search -> setFragment(TAG_SEARCH, SearchFragment())
                R.id.myMenu -> setFragment(TAG_MY_MENU, MyMenuFragment())
            }

            true
        }
    }

    private fun setFragment(tag: String, fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        val fragTransaction = manager.beginTransaction()

        if(manager.findFragmentByTag(tag) == null)
            fragTransaction.add(R.id.mainFrameLayout, fragment, tag)

        val cart = manager.findFragmentByTag(TAG_CART)
        val list = manager.findFragmentByTag(TAG_LIST)
        val search = manager.findFragmentByTag(TAG_SEARCH)
        val myMenu = manager.findFragmentByTag(TAG_MY_MENU)

        if (cart != null)
            fragTransaction.hide(cart)

        if (list != null)
            fragTransaction.hide(list)

        if (search != null)
            fragTransaction.hide(search)

        if (myMenu != null)
            fragTransaction.hide(myMenu)

        if (tag == TAG_CART) {
            if (cart != null)
                fragTransaction.show(cart)
        }

        else if (tag == TAG_LIST) {
            if(list != null) {
                fragTransaction.show(list)
            }
        }

        else if (tag == TAG_SEARCH) {
            if(search != null) {
                fragTransaction.show(search)
            }
        }

        else if (tag == TAG_MY_MENU) {
            if(myMenu != null) {
                fragTransaction.show(myMenu)
            }
        }

        fragTransaction.commitAllowingStateLoss()
    }
}