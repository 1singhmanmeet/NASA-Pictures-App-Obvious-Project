package com.obvious.nasapics.ui

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.obvious.nasapics.R
import com.obvious.nasapics.data.models.ImageResult
import com.obvious.nasapics.ui.adapters.ImageViewPagerAdapter
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.stfalcon.imageviewer.StfalconImageViewer
import com.stfalcon.imageviewer.loader.ImageLoader
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.details_fragment.*
import java.lang.Exception


@AndroidEntryPoint
class ImageDetailsFragment: BottomSheetDialogFragment() {

    var currentPosition=0

    private val homeScreenViewModel:HomeScreenViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.details_fragment,container,false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            setupFullHeight(bottomSheetDialog)
        }
        return dialog
    }

    private fun setupFullHeight(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet =
            bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
        val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(bottomSheet!!)
        val layoutParams = bottomSheet.layoutParams
        val windowHeight = getWindowHeight()
        if (layoutParams != null) {
            layoutParams.height = windowHeight
        }
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun getWindowHeight(): Int {
        // Calculate window height for fullscreen use
        val displayMetrics = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    var imageList:List<ImageResult>?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imagePagerAdapter= ImageViewPagerAdapter(ArrayList()){
            val builder = StfalconImageViewer.Builder<String>(context,
                arrayListOf(imageList!![it].url),
                ImageLoader<String> { imageView, image ->

                    Picasso.get().load(image)
                        .into(imageView)
                })
            builder.show()
        }
        image_pager?.offscreenPageLimit=3
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
                    Log.e("deatails","deatils: ${imageList!![position].copyright}")
                    copyright?.text= "Copyright : ${imageList!![position].copyright?:"Not Available"}"
                    explain?.text=imageList!![position].explanation
                }
            }
        })

        homeScreenViewModel.imageList.observe(this){
            imageList=it
            imagePagerAdapter.imageList=it
            imagePagerAdapter.notifyDataSetChanged()
            copyright?.text= "Copyright : ${imageList!![currentPosition].copyright?:"Not Available"}"
            explain?.text=imageList!![currentPosition].explanation
            image_pager?.currentItem=currentPosition
        }
    }

    companion object{
        const val CURRENT_POSITION="current_position"
        const val DETAILS_FRAGMENT="details_fragment"
        fun newInstance()= ImageDetailsFragment()
    }
}