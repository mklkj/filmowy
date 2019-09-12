package io.github.mklkj.filmowy.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.Html
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import io.github.mklkj.filmowy.R
import timber.log.Timber
import javax.inject.Inject
import kotlin.math.min

class HtmlImageGetter @Inject constructor(private val context: Context?, private val picasso: Picasso) : Html.ImageGetter {

    lateinit var textView: TextView

    override fun getDrawable(source: String?): Drawable {
        return BitmapDrawablePlaceHolder(textView).also {
            picasso.load(source)
                .error(context!!.getDrawable(R.drawable.ic_placeholder)!!)
                .into(it)
        }
    }

    @Suppress("DEPRECATION")
    private inner class BitmapDrawablePlaceHolder(private val textView: TextView) : BitmapDrawable(), Target {

        private var drawable: Drawable? = null
            set(value) {
                if (value != null) {
                    field = value
                    checkBounds()
                }
            }

        override fun draw(canvas: Canvas) {
            if (drawable != null) {
                checkBounds()
                drawable!!.draw(canvas)
            }
        }

        private fun checkBounds() {
            val defaultProportion = drawable!!.intrinsicWidth.toFloat() / drawable!!.intrinsicHeight.toFloat()
            val width = min(textView.width, drawable!!.intrinsicWidth)
            val height = (width.toFloat() / defaultProportion).toInt()

            if (bounds.right != textView.width || bounds.bottom != height) {
                setBounds(0, 0, textView.width, height) //set to full width

                val halfOfPlaceHolderWidth = (bounds.right.toFloat() / 2f).toInt()
                val halfOfImageWidth = (width.toFloat() / 2f).toInt()

                drawable!!.setBounds(
                    halfOfPlaceHolderWidth - halfOfImageWidth, //centering an image
                    0,
                    halfOfPlaceHolderWidth + halfOfImageWidth,
                    height
                )

                textView.text = textView.text //refresh text
            }
        }

        override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
            drawable = BitmapDrawable(context?.resources, bitmap)
        }

        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            drawable = placeHolderDrawable
        }

        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
            Timber.e(e)
        }
    }
}
