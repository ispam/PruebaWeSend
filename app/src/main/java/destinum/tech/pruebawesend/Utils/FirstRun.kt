package destinum.tech.pruebawesend.Utils

import android.content.Context
import android.content.SharedPreferences
import destinum.tech.pruebawesend.BuildConfig

fun checkFirstRun(context: Context, normal: () -> Unit?, newInstall: () -> Unit?, upgrade: () -> Unit?) {

    val sp: SharedPreferences
    val PREF_NAME = "FirstRunPrefs"
    val PREF_VERSION_CODE_KEY = "1"
    val DOESNT_EXIST = -1

    // Get current version code
    val currentVersionCode = BuildConfig.VERSION_CODE

    // Get saved version code
    sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    val savedVersionCode = sp.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST)

    // Check for first run or upgrade
    when {
        currentVersionCode == savedVersionCode -> {
            // This is just a normal run
            normal.invoke()
        }
        savedVersionCode == DOESNT_EXIST -> {
            // TODO This is a new install (or the user cleared the shared preferences)
            newInstall.invoke()
        }
        currentVersionCode > savedVersionCode -> {
            // TODO This is an upgrade
            upgrade.invoke()
        }
    }

    sp.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply()
}