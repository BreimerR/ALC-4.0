package com.brymher.gmail.smile

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.http.SslError
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.Settings
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.webkit.*
import kotlinx.android.synthetic.main.activity_about_alc.*


// TODO create a network time out
class AboutAlc : BaseActivity(R.layout.activity_about_alc, "About ALC") {

    lateinit var URL: String

    var HAS_DIALOG = false

    private var A_FILED: Animation? = null
        get() {
            if (field == null) field = AnimationUtils.loadAnimation(this, R.anim.logo_animation) as Animation
            // return on use of field is redundant
            return field
        }

    private var ANIMATION_STARTED = false

    private val ANIMATION: Animation
        get() = A_FILED as Animation


    private val NETWORK_ACCESSIBLE: Boolean
        get() {
            val netInfo = (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo

            return netInfo != null && netInfo.isConnected
        }


    override fun init() {
        // init loader
        START_LOADER

        // init web view
        INIT_WEB_VIEW


        // load url to initialized WebView
        loadUrl()
    }


    private fun loadUrl(url: String = "https://andela.com/alc/") {
        URL = url
        if (NETWORK_ACCESSIBLE) {
            webView.loadUrl(url)
        } else showNetworkErrorDialog("No Internet connection available")
    }


    private fun reloadUrl() {
        loadUrl(URL)
    }

    // web view initializer
    private val INIT_WEB_VIEW: Unit
        @SuppressLint("SetJavaScriptEnabled")
        get() {
            val settings = webView.settings
            webView.webViewClient = Client(this)
            settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
            settings.javaScriptEnabled = true
            settings.setSupportZoom(true)
            settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
            settings.domStorageEnabled = true
            webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        }


    // avoid webView reload on displayChange
    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        webView.restoreState(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        webView.saveState(outState)
    }


    fun buildNetworkError(builder: AlertDialog.Builder) {
        builder.apply {
            setPositiveButton("retry") { _, _ ->
                reloadUrl()
            }

            setNeutralButton("Settings") { _, _ ->
                startActivityForResult(Intent(Settings.ACTION_WIRELESS_SETTINGS), 0)
            }

            setNegativeButton("exit") { _, _ ->
                finish()
            }

        }
    }


    // TODO client does not support time outs wor slow internet connections
    private class Client(private val ACTIVITY: AboutAlc) : WebViewClient() {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            ACTIVITY.START_LOADER

            super.onPageStarted(view, url, favicon)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            ACTIVITY.endLoader
        }


        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
            ACTIVITY.createDialog {
                ACTIVITY.webView.visibility = View.INVISIBLE
                val title: String
                val message: String
                val icon: Int = R.drawable.andela

                if (ACTIVITY.NETWORK_ACCESSIBLE) {
                    title = "Network Error"
                    message = "Internet Connection Lost"
                } else {
                    title = "Error Loading Page"
                    message = "Check Your Internet Connection"
                }


                ACTIVITY.buildNetworkError(this)

                setTitle(title)

                setMessage(message)

                setIcon(icon)

                setOnDismissListener {
                    ACTIVITY.HAS_DIALOG = false
                }

                if (!ACTIVITY.HAS_DIALOG) {
                    ACTIVITY.HAS_DIALOG = true
                    show()
                }
            }
        }
        // avoid errors from ssl
        // TODO request user acceptance of the error than just skipping it
        override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
            handler?.proceed()
        }
    }

    private fun showNetworkErrorDialog(message: String, title: String = "Connection Error") {
        showDialog(title, message) {
            buildNetworkError(this)
        }
    }


    override fun onActivityResult(request: Int, code: Int, data: Intent?) {
        when (request) {
            0 -> reloadUrl()
            else -> super.onActivityResult(request, code, data)
        }
    }


    private val START_LOADER: Unit
        get() {
            if (!ANIMATION_STARTED) {
                val anim = ANIMATION
                loading_logo.animation = anim
                loading_container.visibility = View.VISIBLE
                ANIMATION.reset()
                ANIMATION_STARTED = true
            }
        }

    val endLoader: Unit
        get() {
            loading_logo.clearAnimation()
            loading_container.visibility = View.INVISIBLE

            ANIMATION_STARTED = false
        }

    // maintain browser web page navigation
    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else super.onBackPressed()
    }
}
