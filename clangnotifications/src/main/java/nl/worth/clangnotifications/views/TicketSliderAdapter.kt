package nl.worth.clangnotifications.views

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.LayerDrawable
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.ticket_slider_item.view.*
import android.app.Activity
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.constraintlayout.widget.ConstraintLayout
import nl.evillage.views.Functions


class TicketSliderAdapter(private val context: Context, list: ArrayList<String>) : PagerAdapter() {

    public var aparent: Activity? = null
    public var mainConstraintLayout: ConstraintLayout? = null
    private var inflater: LayoutInflater? = null
    public val strings: ArrayList<String> = list
    override fun isViewFromObject(view: View, `object`: Any): Boolean {

        return view === `object`
    }

    override fun getCount(): Int {

        return strings.size
    }


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var vp = container as ViewPager

        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater!!.inflate(nl.worth.clangnotifications.R.layout.ticket_slider_item, null)
        view.setBackgroundColor(Color.TRANSPARENT)
        view.fragment_web.settings.javaScriptEnabled = true
        view.fragment_web.loadDataWithBaseURL(
            null,
            strings[position],
            "text/html",
            "utf-8",
            null
        )


        view.activity_back.background = Functions.setLayerShadow("#FFFFFF")

        view.fragment_web.setWebViewClient(object : WebViewClient() {

            override fun onPageFinished(view: WebView?, url: String) {
                Log.d("WebView", "onPageFinished $url")
                Log.d("makeText", view?.contentHeight.toString())


            }
            override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
                return if (url != null && (url.startsWith("http://") || url.startsWith("https://"))) {
                    view.context.startActivity(
                        Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    )
                    true
                } else {
                    false
                }
            }
            fun onProgressChanged(wv: WebView?, url: String?) {

                Log.d("makeText", view.fragment_web.contentHeight.toString())

            }
        })

        vp.addView(view, 0)
        val lp = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            70
        )
        lp.gravity = (Gravity.CENTER_HORIZONTAL)
        lp.setMargins(0,0,0,0)

        val button = Button(context)
        button.layoutParams = lp
        button.setBackgroundColor(Color.BLUE)
        button.text = " ✕  "
        button.setOnClickListener(View.OnClickListener {


            strings.remove(strings[position]);
            notifyDataSetChanged()

            Functions.removeTheTickets(aparent!!, strings, mainConstraintLayout!!)
        })
        button.setTextColor(Color.BLACK)
        button.gravity =(Gravity.RIGHT)
        button.setTextSize(22F)
        button.setPadding(0,0,10,0)
        view.fragment_web.addView(button)

        return view
    }


    private var cancelClickListener = View.OnClickListener { view ->

        Log.d("makeText", "")

    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

    }

    override fun notifyDataSetChanged() {
        super.notifyDataSetChanged()
    }

    override fun saveState(): Parcelable? {
        return null
    }

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {}
}

public fun setLayerShadow(color: String): LayerDrawable? {
    var shadow: GradientDrawable
    val strokeValue = 4
    val radiousValue = 10
    try {
        val colors1 = intArrayOf(
            Color.parseColor("#ffffff"),
            Color.parseColor(color),
            Color.parseColor("#ffffff")
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
    val layers = arrayOfNulls<Drawable>(2)
    layers[0] = shadow
    val layerList = LayerDrawable(layers)
    layerList.setLayerInset(0, 1, 0, 1, 0)
    layerList.setLayerInset(0, strokeValue, strokeValue, strokeValue, strokeValue)
    return layerList
}

