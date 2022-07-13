package com.obvious.nasapics.ui.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.obvious.nasapics.R
import com.obvious.nasapics.data.models.ImageResult
import com.obvious.nasapics.ui.HomeScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.details_fragment.*


@AndroidEntryPoint
class ImageDetailsFragment: BottomSheetDialogFragment() {

    var currentPosition=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //currentPosition=arguments?.getInt(CURRENT_POSITION)!!
    }

    val homeScreenViewModel:HomeScreenViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.details_fragment,container,false)
    }

    var imageList:List<ImageResult>?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imagePagerAdapter=ImageViewPagerAdapter(ArrayList())
        image_pager?.offscreenPageLimit=3
        image_pager?.setPadding(30,0,30,0)
        image_pager?.adapter=imagePagerAdapter
        image_pager?.registerOnPageChangeCallback(object:ViewPager2.OnPageChangeCallback(){
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                if(imageList!=null){
                    copyright?.text=imageList!![position].copyright
                    //explain?.text=imageList!![position].explanation
                }
            }
        })

        homeScreenViewModel.imageList.observe(this){
            imageList=it
            imagePagerAdapter.imageList=it
            imagePagerAdapter.notifyDataSetChanged()
            image_pager?.currentItem=currentPosition
        }
    }

    companion object{
        const val CURRENT_POSITION="current_position"
        const val DETAILS_FRAGMENT="details_fragment"
        fun newInstance(currentPosition:Int)=ImageDetailsFragment().apply {
            arguments?.apply {
                putInt(CURRENT_POSITION,currentPosition)
            }
        }
    }
}