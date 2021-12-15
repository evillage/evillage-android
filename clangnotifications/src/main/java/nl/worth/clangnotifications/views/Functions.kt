package nl.evillage.views


import android.app.Activity
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.annotation.Keep
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.ViewPager
import nl.worth.clangnotifications.views.TicketSliderAdapter
import org.json.JSONObject


@Keep
class Functions {

    companion object {

        public var indicators: Indicator? = null

        fun setLayerShadow(color: String): LayerDrawable? {
            var shadow: GradientDrawable
            val strokeValue = 4
            val radiousValue = 10
            try {
                val colors1 = intArrayOf(
                    Color.parseColor("#FFFFFF"),
                    Color.parseColor(color),
                    Color.parseColor("#FFFFFF")
                )
                shadow = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors1)
                shadow.cornerRadius = radiousValue.toFloat()
            } catch (e: Exception) {
                val colors1 = intArrayOf(Color.parseColor("#FFFFFF"), Color.parseColor("#FFFFFF"))
                shadow = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors1)
                shadow.cornerRadius = radiousValue.toFloat()
                e.printStackTrace()
            }

            //finally c.reate a layer list and set them as background.
            val layers = arrayOfNulls<Drawable>(1)
            layers[0] = shadow
            val layerList = LayerDrawable(layers)
            layerList.setLayerInset(0, 1, 0, 1, 0)
            layerList.setLayerInset(0, strokeValue, strokeValue, strokeValue, strokeValue)
            return layerList
        }

        fun removeTheTickets(parent: Activity, strings: ArrayList<String>, mainConstraintLayout : ConstraintLayout) {

            for (index in 0 until (mainConstraintLayout as ViewGroup).childCount) {
                val nextChild = (mainConstraintLayout as ViewGroup).getChildAt(index)
                if (nextChild.tag == "cp_Layout") {

                    val layout = parent.findViewById<LinearLayout>(nextChild.id) as LinearLayout
                    var copier = layout.findViewWithTag<ViewPager>("ViewPager")
                    var indicator = layout.findViewWithTag<RelativeLayout>("indicator")

                    if (strings.size == 1) {
                        layout.removeView(indicator)
                    } else {

                        if (strings.size > 1) {
                            indicators!!.build(mainConstraintLayout)
                        }
                    }
                    if (strings.size == 0) {

                        if (copier != null) {
                            layout.removeView(copier)
                        }
                    } else {

                        layout.removeView(copier)
                        val viewPager = ViewPager(parent)
                        viewPager.setId(View.generateViewId())
                        viewPager.setTag("ViewPager")// --> this is important!

                        viewPager.adapter = TicketSliderAdapter(parent, strings)
                        (viewPager.adapter as TicketSliderAdapter).aparent = parent
                        (viewPager.adapter as TicketSliderAdapter).mainConstraintLayout = mainConstraintLayout
                        val params = FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT

                        )


                        params.height = Resources.getSystem().getDisplayMetrics().heightPixels / 3
                        viewPager.layoutParams = params

                        layout.addView(viewPager, 0)
                    }
                }
            }

        }


        fun convertDemoJSON(toConvert: String) : String {

            val decodeValue = Base64.decode(toConvert, Base64.DEFAULT)
            val json = JSONObject(String(decodeValue))
            val module = json.getString("body")
            val content = JSONObject(module)
            return  content.getString("content")
        }



        fun buildTheTickets(parent: Activity, toAdd: String, mainConstraintLayout : ConstraintLayout) {


            for (index in 0 until (mainConstraintLayout as ViewGroup).childCount) {
                val nextChild = (mainConstraintLayout as ViewGroup).getChildAt(index)
                if (nextChild.tag == "cp_Layout") {

                    val layout = parent.findViewById<LinearLayout>(nextChild.id) as LinearLayout
                    var copier = layout.findViewWithTag<ViewPager>("ViewPager")

                    if (copier != null) {

                        val strings: ArrayList<String> = (copier.adapter as TicketSliderAdapter).strings
                        layout.removeView(copier)

                        val viewPager = ViewPager(parent)
                        viewPager.setId(View.generateViewId())
                        viewPager.setTag("ViewPager")// --> this is important!

                        strings.add(0, convertDemoJSON(toAdd))
                        viewPager.adapter = TicketSliderAdapter(parent, strings)
                        (viewPager.adapter as TicketSliderAdapter).aparent = parent
                        (viewPager.adapter as TicketSliderAdapter).mainConstraintLayout = mainConstraintLayout
                        val params = FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT

                        )
                        params.height = Resources.getSystem().getDisplayMetrics().heightPixels / 3
                        viewPager.layoutParams = params
                        val animation =
                            AnimationUtils.loadAnimation(parent, nl.worth.clangnotifications.R.anim.popup)
                        animation.startOffset = 0
                        viewPager.startAnimation(animation)
                        layout.addView(viewPager, 0)
                        indicators = Indicator(layout.context, strings, parent)
                        indicators!!.build(mainConstraintLayout)

                        viewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

                            override fun onPageScrollStateChanged(state: Int) {


                            }
                            override fun onPageScrolled(
                                position: Int,
                                positionOffset: Float,
                                positionOffsetPixels: Int
                            ) {

                            }

                            override fun onPageSelected(position: Int) {
                                indicators!!.changeBullets(position, mainConstraintLayout)
                            }

                        })


                    } else {

                        val viewPager = ViewPager(parent)
                        viewPager.setId(View.generateViewId())
                        viewPager.setTag("ViewPager")// --> this is important!

                        val strings: ArrayList<String> = arrayListOf(convertDemoJSON(toAdd))

                        viewPager.adapter = TicketSliderAdapter(parent, strings)
                        (viewPager.adapter as TicketSliderAdapter).aparent = parent
                        (viewPager.adapter as TicketSliderAdapter).mainConstraintLayout = mainConstraintLayout
                        val params = FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT

                        )
                        params.height = Resources.getSystem().getDisplayMetrics().heightPixels / 3
                        viewPager.layoutParams = params
                        val animation =
                            AnimationUtils.loadAnimation(parent, nl.worth.clangnotifications.R.anim.popup)
                        animation.startOffset = 0
                        viewPager.startAnimation(animation)
                        layout.addView(viewPager, 0)


                        viewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {


                            override fun onPageScrollStateChanged(state: Int) {


                            }

                            override fun onPageScrolled(
                                position: Int,
                                positionOffset: Float,
                                positionOffsetPixels: Int
                            ) {

                            }

                            override fun onPageSelected(position: Int) {

                                Log.d("onPageSelected", position.toString())

                            }

                        })


                    }

                }
            }

        }





    }
}

