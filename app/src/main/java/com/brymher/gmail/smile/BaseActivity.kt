package com.brymher.gmail.smile


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity


/**@description
 * base ActivityCass avoid repetitive on create setContentView
 * standardise view optionsMenu
 * */
abstract class BaseActivity(private val layout: Int, public val tTitle: String? = null) : AppCompatActivity() {

    protected val TAG: String
        get() {
            return javaClass.name
        }

    protected var savedInstanceState: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout)

        this.savedInstanceState = savedInstanceState

        if (tTitle != null) title = tTitle
        init()
    }

    abstract fun init()

    public fun showDialog(
        title: String,
        message: String,
        icon: Int = R.drawable.andela,
        dialog: AlertDialog.Builder
    ) {
        showDialog(dialog) {
            setTitle(title)
            setMessage(message)
            setIcon(icon)
            show()
        }
    }

    public fun showDialog(icon: Int, creator: AlertDialog.Builder.() -> Unit) {
        createDialog {
            creator(this)
            setIcon(icon)
            show()
        }
    }


    public fun showDialog(title: String, message: String) {
        showDialog(title, message) {
            setTitle(title)
            setMessage(message)
            show()
        }
    }

    public fun showDialog(
        title: String,
        message: String,
        icon: Int = R.drawable.andela,
        dialog: AlertDialog.Builder,
        creator: AlertDialog.Builder.() -> Unit
    ) {
        showDialog(dialog) {
            setTitle(title)
            setMessage(message)
            setIcon(icon)
            show()
        }
    }

    public fun showDialog(
        title: String,
        message: String,
        icon: Int = R.drawable.andela,
        creator: AlertDialog.Builder.() -> Unit
    ) {
        createDialog {
            creator(this)
            setTitle(title)
            setMessage(message)
            setIcon(icon)
            show()
        }
    }

    public fun showDialog(
        dialog: AlertDialog.Builder? = null,
        creator: AlertDialog.Builder.() -> Unit
    ) {
        createDialog(dialog, creator).show()
    }

    public fun showDialog(
        showIcon: Boolean,
        dialog: AlertDialog.Builder? = null,
        creator: AlertDialog.Builder.() -> Unit
    ) {
        if (showIcon) {
            createDialog(dialog, creator).setIcon(R.drawable.andela).show()
        } else createDialog(dialog, creator).show()
    }


    // dialog creator
    protected fun createDialog(
        dialog: AlertDialog.Builder? = null,
        creator: AlertDialog.Builder.() -> Unit
    ): AlertDialog.Builder = (dialog ?: AlertDialog.Builder(this)).apply(creator)


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.about_app -> {
                showAppDetails()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showAppDetails() {
        showDialog {
            setView(R.layout.about_app)
        }
    }

}