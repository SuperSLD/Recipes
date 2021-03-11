package com.jutter.sharerecipes.ui

import android.content.Intent
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.jutter.sharerecipes.R
import com.jutter.sharerecipes.Screens
import com.jutter.sharerecipes.common.base.BaseFragment
import com.jutter.sharerecipes.common.enums.DeeplinkType
import com.jutter.sharerecipes.comtrollers.DeeplinkOpenController
import com.jutter.sharerecipes.extensions.getIsDayTheme
import com.jutter.sharerecipes.extensions.hideKeyboard
import org.koin.android.ext.android.inject
import timber.log.Timber

class AppActivity : AppCompatActivity() {
    private val currentFragment: BaseFragment?
        get() = supportFragmentManager.findFragmentById(R.id.container) as? BaseFragment

    private val deeplinkOpenController: DeeplinkOpenController by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        initTransparent()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_container)

        handleIntent(intent)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, Screens.FlowGlobal.fragment)
                .commitNow()
        }
    }

    override fun onBackPressed() {
        currentFragment?.onBackPressed() ?: super.onBackPressed()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initTransparent() {
        window.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (context.getIsDayTheme()) {
                    decorView.systemUiVisibility = decorView.systemUiVisibility or
                            View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR or
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                } else {
                    decorView.systemUiVisibility = decorView.systemUiVisibility
                }
            }

            statusBarColor = ContextCompat.getColor(context, R.color.colorStatusBar)
            navigationBarColor = ContextCompat.getColor(context, R.color.colorNavigationBar)
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    hideKeyboard()
                    v.clearFocus()
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    /**
     * Чтение данных из Диплинка.
     */
    private fun handleIntent(intent: Intent?) {
        val appLinkAction: String? = intent?.action
        val appLinkData: Uri? = intent?.data

        Timber.d("handleIntent: $appLinkAction")
        Timber.d("handleIntent: ${appLinkData?.getQueryParameter("type")}")
        if (Intent.ACTION_VIEW == appLinkAction && appLinkData != null) {
            when (appLinkData.getQueryParameter("type")) {
                "profile" -> deeplinkOpenController.open(
                        Pair(
                                DeeplinkType.PROFILE,
                                appLinkData.getQueryParameter("id")!!.toInt()
                        )
                )
                "recipes" -> deeplinkOpenController.open(
                        Pair(
                                DeeplinkType.RECIPES,
                                appLinkData.getQueryParameter("id")
                        )
                )
            }
            deeplinkOpenController.isOpened = false
        }
    }

    /**
     * Проверка входящего интента для перехода на новый скрин.
     */
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }
}
