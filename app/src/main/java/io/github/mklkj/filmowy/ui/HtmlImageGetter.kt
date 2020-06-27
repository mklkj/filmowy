package io.github.mklkj.filmowy.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.Html
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.Request
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SizeReadyCallback
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition

class HtmlImageGetter(private val context: Context) : Html.ImageGetter {

    lateinit var textView: TextView

    override fun getDrawable(source: String?): Drawable {
        return BitmapDrawablePlaceholder().let {
            Glide.with(context)
                .asBitmap()
                .load(source)
                .apply(RequestOptions().centerInside())
                .into(it)
        }
    }

    private inner class BitmapDrawablePlaceholder internal constructor() :
        BitmapDrawable(context.resources, Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)), Target<Bitmap> {

        private var drawable: Drawable? = null

        override fun draw(canvas: Canvas) {
            if (drawable != null) {
                drawable!!.draw(canvas)
            }
        }

        private fun setImageDrawable(drawable: Drawable) {
            this.drawable = drawable
            val drawableWidth = drawable.intrinsicWidth
            val drawableHeight = drawable.intrinsicHeight
            val maxWidth = textView.measuredWidth
            if (drawableWidth > maxWidth) {
                val calculatedHeight = maxWidth * drawableHeight / drawableWidth
                drawable.setBounds(0, 0, maxWidth, calculatedHeight)
                setBounds(0, 0, maxWidth, calculatedHeight)
            } else {
                drawable.setBounds(0, 0, drawableWidth, drawableHeight)
                setBounds(0, 0, drawableWidth, drawableHeight)
            }
            textView.text = textView.text
        }

        override fun onLoadStarted(placeholderDrawable: Drawable?) {
            placeholderDrawable?.let { setImageDrawable(it) }
        }

        override fun onLoadFailed(errorDrawable: Drawable?) {
            errorDrawable?.let { setImageDrawable(it) }
        }

        override fun onResourceReady(bitmap: Bitmap, transition: Transition<in Bitmap>?) {
            setImageDrawable(BitmapDrawable(context.resources, bitmap))
        }

        override fun onLoadCleared(placeholderDrawable: Drawable?) {
            placeholderDrawable?.let { setImageDrawable(it) }
        }

        override fun getSize(cb: SizeReadyCallback) {
            textView.post { cb.onSizeReady(textView.width, textView.height) }
        }

        override fun removeCallback(cb: SizeReadyCallback) {}
        override fun setRequest(request: Request?) {}
        override fun getRequest(): Request? = null
        override fun onStart() {}
        override fun onStop() {}
        override fun onDestroy() {}
    }
}
