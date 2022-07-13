package com.obvious.nasapics.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.obvious.nasapics.R
import com.obvious.nasapics.ui.adapters.ImageGridRecyclerAdapter
import com.obvious.nasapics.utils.Constants
import com.obvious.nasapics.utils.State
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home_screen.*

@AndroidEntryPoint
class HomeScreenActivity : AppCompatActivity() {

    private val homeScreenViewModel:HomeScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        val imageGridAdapter=ImageGridRecyclerAdapter{ imageResult, position ->
            showImageDetailFragment(position)
        }
        images_recycler?.apply {
            layoutManager=
                StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL).apply {
                gapStrategy=StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
            }
            adapter=imageGridAdapter
        }

        swipe_refresh_layout?.setOnRefreshListener {
            homeScreenViewModel.getImages()
        }
        homeScreenViewModel.currentState.observe(this){ state ->
            when(state){
                State.LOADING->{
                    swipe_refresh_layout?.isRefreshing=true
                }
                State.LOADED->{
                    swipe_refresh_layout?.isRefreshing=false
                }

                State.OFFLINE->{
                    current_mode?.visibility= View.VISIBLE
                    current_mode?.text=Constants.OFFLINE_MODE
                }

                State.ONLINE->{
                    if(current_mode?.visibility==View.VISIBLE) {
                        current_mode?.text = Constants.BACK_ONLINE
                        Handler(Looper.getMainLooper())
                            .postDelayed({ current_mode?.visibility = View.GONE }, 1500)
                    }
                }
                else->{
                    no_internet?.visibility=View.VISIBLE
                }
            }
        }

        homeScreenViewModel.errorMessage.observe(this){ error->
            Toast.makeText(this,error,Toast.LENGTH_LONG).show()
        }

        homeScreenViewModel.imageList.observe(this){
            if(it.isNotEmpty()){
                no_internet?.visibility=View.GONE
                imageGridAdapter.submitList(it)
            }
        }

        homeScreenViewModel.getImages()

    }

    var imageDetailsFragment: ImageDetailsFragment?=null
    private fun showImageDetailFragment(position:Int){
        if(imageDetailsFragment==null)
            imageDetailsFragment= ImageDetailsFragment.newInstance()
        imageDetailsFragment?.currentPosition=position
        imageDetailsFragment?.show(supportFragmentManager,
            ImageDetailsFragment.DETAILS_FRAGMENT)

    }
}