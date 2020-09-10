package com.tleksono.discovermovie.common

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screen {

    class FragmentScreen(private val fragments: Fragment) : SupportAppScreen() {
        override fun getFragment(): Fragment? {
            this.screenKey = fragments.javaClass.simpleName
            return fragments
        }
    }

    class ActivityScreen(private val intent: Intent) : SupportAppScreen() {
        override fun getActivityIntent(context: Context): Intent? {
            return intent
        }
    }


}
