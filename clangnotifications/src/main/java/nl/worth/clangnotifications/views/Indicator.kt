package nl.evillage.views

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.ViewPager



class Indicator(private val context: Context, list: ArrayList<String>, parent: Activity) {

    public var aparent = parent
    public val strings: ArrayList<String> = list
    public val bullets: ArrayList<String> = list

    fun reset(check : ConstraintLayout) {


        for (index in 0 until (check as ViewGroup).childCount) {
            val nextChild = (check as ViewGroup).getChildAt(index)
            if (nextChild.tag == "cp_Layout") {

                val layout = aparent.findViewById<LinearLayout>(nextChild.id) as LinearLayout

                var indicatorcopy = layout.findViewWithTag<RelativeLayout>("indicator")
                var i = 0
                for (item in strings) {
                    val lp = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    lp.leftMargin = 50 * i

                    val button = TextView(indicatorcopy.context)
                    button.layoutParams = lp
                    button.setBackgroundColor(Color.TRANSPARENT)
                    button.text = "●"
                    button.tag = i + 100
                    button.setGravity(Gravity.CENTER)
                    button.setPadding(0, 0, 0, 0)
                    if (i == 0) {
                        button.setTextColor(Color.BLACK)
                    } else {
                        button.setTextColor(Color.LTGRAY)
                    }
                    indicatorcopy.addView(button)
                    i++
                }
            }
        }


    }

    fun build(check : ConstraintLayout) {



        for (index in 0 until (check as ViewGroup).childCount) {
            val nextChild = (check as ViewGroup).getChildAt(index)
            if (nextChild.tag == "cp_Layout") {

                val layout = aparent.findViewById<LinearLayout>(nextChild.id) as LinearLayout
                var copier = layout.findViewWithTag<ViewPager>("ViewPager")


                var indicatorcopy = layout.findViewWithTag<RelativeLayout>("indicator")
                if (indicatorcopy != null) {
                    indicatorcopy.removeAllViews()

                    var i = 0
                    for (item in strings) {

                        val lp = LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                        lp.leftMargin = 50 * i

                        val button = TextView(indicatorcopy.context)
                        button.layoutParams = lp
                        button.setBackgroundColor(Color.TRANSPARENT)
                        button.text = "●"
                        button.tag = i + 100
                        button.setGravity(Gravity.CENTER)
                        button.setPadding(0, 0, 0, 0)
                        if (i == 0) {
                            button.setTextColor(Color.BLACK)
                        } else {
                            button.setTextColor(Color.LTGRAY)
                        }
                        indicatorcopy.addView(button)

                        i++
                    }


                } else {
                    val indicator = RelativeLayout(aparent)
                    indicator.layoutParams = RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT

                    )
                    indicator.gravity = Gravity.CENTER
                    indicator.setTag("indicator")
                    indicator.setBackgroundColor(Color.TRANSPARENT)
                    layout.addView(indicator, 1)

                    var i = 0
                    for (item in strings) {

                        val lp = LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                        lp.leftMargin = 50 * i


                        val button = TextView(indicator.context)
                        button.layoutParams = lp
                        button.setBackgroundColor(Color.TRANSPARENT)
                        button.text = "●"
                        button.tag = i + 100
                        button.setGravity(Gravity.CENTER)
                        button.setPadding(button.width * i, 0, 0, 0)
                        if (i == 0) {
                            button.setTextColor(Color.BLACK)
                        } else {
                            button.setTextColor(Color.LTGRAY)
                        }
                        indicator.addView(button)
                        i++
                    }
                }
            }
        }
    }

    fun changeBullets(position: Int, check : ConstraintLayout) {


        for (index in 0 until (check as ViewGroup).childCount) {
            val nextChild = (check as ViewGroup).getChildAt(index)
            if (nextChild.tag == "cp_Layout") {

                var layout = aparent.findViewById<LinearLayout>(nextChild.id) as LinearLayout
                var i = 0
                for (item in strings) {

                    var bullet = layout.findViewWithTag<TextView>(i + 100)
                    if (bullet != null) {
                        bullet.setTextColor(Color.LTGRAY)
                    }
                    i++
                }
                var bullet = layout.findViewWithTag<TextView>(position + 100)
                if (bullet != null) {
                    bullet.setTextColor(Color.BLACK)
                }
            }

        }
    }


}
