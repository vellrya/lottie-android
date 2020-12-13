package com.airbnb.lottie.issues

import android.content.res.AssetManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieCompositionFactory
import java.util.zip.ZipInputStream

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    
    var animated = true
    lateinit var stickerPreviewLAV: LottieAnimationView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        stickerPreviewLAV = findViewById(R.id.lav)
        changeRes(null)

    }

    fun changeRes(view: View?) {

        //Click on button three times: 1 - change from static to animation, 2 - change from
        //animation to static, 3 and next click: nothing changes.
        //If we will use random string instead of "anim_123321" cache key, animation would
        //change, but it's a very bad solution for performance I think

        animated = !animated
        if (animated) {
//            println("animated resource:")


            val compositionCancellable = LottieCompositionFactory.fromZipStream(
                    ZipInputStream(assets.open("anim.zip")), "anim_123321"
            )
//            println("Old composition: "+stickerPreviewLAV.composition)
            compositionCancellable.addListener {
                stickerPreviewLAV.setComposition(it)
                stickerPreviewLAV.playAnimation()
                println("compositionCancellable success")
            }.addFailureListener {
                println("Fail in lottie")
                it.printStackTrace()
            }
        } else {
            println("static resource:")
            stickerPreviewLAV.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.owl))
        }
    }
}
