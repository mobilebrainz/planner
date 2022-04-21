package app.khodko.planner.core

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.MainThread
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import app.khodko.planner.core.extension.hideSoftKeyboardExt
import app.khodko.planner.ui.activity.FabActivity
import app.khodko.planner.ui.activity.LoginActivity
import app.khodko.planner.ui.activity.USER_ID_PREF
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar.LENGTH_LONG

abstract class BaseFragment : Fragment() {

    private var errorSnackbar: Snackbar? = null
    private var infoSnackbar: Snackbar? = null

    protected lateinit var sharedPreferences: SharedPreferences
    protected lateinit var fab: FloatingActionButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNightTheme()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        hideSoftKeyboardExt()

        if (activity is FabActivity) {
            fab = (activity as FabActivity).getFab()
            fab.visibility = View.GONE
            initFab()
        }
    }

    protected fun checkUserId(): Long {
        val userId = requireActivity().getSharedPreferences(USER_ID_PREF, 0).getLong(USER_ID_PREF, -1)
        if (userId < 0) {
            startActivity(Intent(context, LoginActivity::class.java))
            requireActivity().finish()
        }
        return userId
    }

    private fun initNightTheme() {
        sharedPreferences = requireActivity().getSharedPreferences("night", 0)
        val booleanValue = sharedPreferences.getBoolean("night_mode", false)
        if (booleanValue) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    @MainThread
    protected fun showInfoSnackbar(
        @StringRes resId: Int,
        duration: Int = LENGTH_LONG,
        maxLines: Int = 2
    ) {
        view?.let {
            infoSnackbar = Snackbar.make(it, resId, duration).apply {
                if (maxLines > 2) {
                    //todo: in future android updates check R.id.snackbar_text
                    val textView =
                        view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
                    textView.maxLines = maxLines
                }
                show()
            }
        }
    }

    @MainThread
    protected fun showErrorSnackbar(
        @StringRes messageResId: Int,
        @StringRes actionResId: Int,
        listener: (v: View) -> Unit
    ) {
        view?.let {
            errorSnackbar = Snackbar.make(it, messageResId, LENGTH_INDEFINITE)
            errorSnackbar?.setAction(actionResId, View.OnClickListener(listener::invoke))?.show()
        }
    }

    protected fun dismissErrorSnackbar() {
        errorSnackbar?.apply { if (isShown) dismiss() }
    }

    protected fun dismissInfoSnackbar() {
        infoSnackbar?.apply { if (isShown) dismiss() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dismissErrorSnackbar()
        dismissInfoSnackbar()
    }

    open fun initFab() {

    }
}