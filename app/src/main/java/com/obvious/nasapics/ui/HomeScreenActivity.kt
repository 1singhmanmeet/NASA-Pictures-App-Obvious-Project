package com.obvious.nasapics.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.obvious.nasapics.R
import com.obvious.nasapics.ui.adapters.ImageGridRecyclerAdapter
import com.obvious.nasapics.utils.State
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.activity_home_screen.*

@AndroidEntryPoint
class HomeScreenActivity : AppCompatActivity() {

    private val homeScreenViewModel:HomeScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        val imageGridAdapter=ImageGridRecyclerAdapter()
        images_recycler?.apply {
            layoutManager=
                StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL).apply {
                gapStrategy=StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
            }
            adapter=imageGridAdapter
        }

        swipe_refresh_layout?.setOnRefreshListener {
            homeScreenViewModel.getImages(true)
        }
        homeScreenViewModel.currentState.observe(this){ state ->
            when(state){
                State.LOADING->{
                    swipe_refresh_layout?.isRefreshing=true
                }
                State.LOADED->{
                    swipe_refresh_layout?.isRefreshing=false
                }
                else->{}
            }
        }

        homeScreenViewModel.errorMessage.observe(this){ error->
            Toast.makeText(this,error,Toast.LENGTH_LONG).show()
        }

        homeScreenViewModel.imageList.observe(this){
            Log.e("HomeActivity","List size: ${it.size}")
            if(it.isNotEmpty()){
                imageGridAdapter.submitList(it)
            }
        }

        homeScreenViewModel.getImages()

    }

    lateinit var detailsDialog:BottomSheetDialogFragment
    fun createImageDetailFragment(){

    }
}