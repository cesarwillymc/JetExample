package com.summit.jetexample.navigation

import android.os.Bundle
import androidx.annotation.MainThread
import androidx.compose.runtime.*
import androidx.core.os.bundleOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

/**
 * A simple navigator which maintains a back stack.
 */
enum class ScreenName { HOME, CREATEPOLL, ARTICLE,REGISTER }

/**
 * Class defining the screens we have in the app: home, article details and interests
 */
sealed class Screen(val id: ScreenName) {
  object Home : Screen(ScreenName.HOME)
  object Register : Screen(ScreenName.REGISTER)
  object CreatePoll : Screen(ScreenName.CREATEPOLL)
  data class Article(val postId: String) : Screen(ScreenName.ARTICLE)
}

/**
 * Helpers for saving and loading a [Screen] object to a [Bundle].
 *
 * This allows us to persist navigation across process death, for example caused by a long video
 * call.
 */
private const val SIS_SCREEN = "sis_screen"
private const val SIS_NAME = "screen_name"
private const val SIS_POST = "post"

/**
 * Convert a screen to a bundle that can be stored in [SavedStateHandle]
 */
private fun Screen.toBundle(): Bundle {
  return bundleOf(SIS_NAME to id.name).also {
    // add extra keys for various types here
    if (this is Screen.Article) {
      it.putString(SIS_POST, postId)
    }
  }
}

/**
 * Read a bundle stored by [Screen.toBundle] and return desired screen.
 *
 * @return the parsed [Screen]
 * @throws IllegalArgumentException if the bundle could not be parsed
 */
private fun Bundle.toScreen(): Screen {
  val screenName = ScreenName.valueOf(getStringOrThrow(SIS_NAME))
  return when (screenName) {
    ScreenName.HOME -> Screen.Home
    ScreenName.CREATEPOLL -> Screen.CreatePoll
    ScreenName.REGISTER-> Screen.Register
    ScreenName.ARTICLE -> {
      val postId = getStringOrThrow(SIS_POST)
      Screen.Article(postId)
    }
  }
}

/**
 * Throw [IllegalArgumentException] if key is not in bundle.
 *
 * @see Bundle.getString
 */
private fun Bundle.getStringOrThrow(key: String) =
  requireNotNull(getString(key)) { "Missing key '$key' in $this" }

/**
 * This is expected to be replaced by the navigation component, but for now handle navigation
 * manually.
 *
 * Instantiate this ViewModel at the scope that is fully-responsible for navigation, which in this
 * application is [MainActivity].
 *
 * This app has simplified navigation; the back stack is always [Home] or [Home, dest] and more
 * levels are not allowed. To use a similar pattern with a longer back stack, use a [StateList] to
 * hold the back stack state.
 */
class NavigationViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
  /**
   * Hold the current screen in an observable, restored from savedStateHandle after process
   * death.
   *
   * mutableStateOf is an observable similar to LiveData that's designed to be read by compose. It
   * supports observability via property delegate syntax as shown here.
   */
  var currentScreen: Screen by savedStateHandle.getMutableStateOf<Screen>(
    key = SIS_SCREEN,
    default = Screen.Home,
    save = { it.toBundle() },
    restore = { it.toScreen() }
  )
    private set // limit the writes to only inside this class.

  /**
   * Go back (always to [Home]).
   *
   * Returns true if this call caused user-visible navigation. Will always return false
   * when [currentScreen] is [Home].
   */
  @MainThread
  fun onBack(): Boolean {
    val wasHandled = currentScreen != Screen.Home
    currentScreen = Screen.Home
    return wasHandled
  }

  /**
   * Navigate to requested [Screen].
   *
   * If the requested screen is not [Home], it will always create a back stack with one element:
   * ([Home] -> [screen]). More back entries are not supported in this app.
   */
  @MainThread
  fun navigateTo(screen: Screen) {
    currentScreen = screen
  }
  fun <T> SavedStateHandle.getMutableStateOf(
    key: String,
    default: T,
    save: (T) -> Bundle,
    restore: (Bundle) -> T
  ): MutableState<T> {
    val bundle: Bundle? = get(key)
    val initial = if (bundle == null) { default } else { restore(bundle) }
    val state = mutableStateOf(initial)
    setSavedStateProvider(key) {
      save(state.value)
    }
    return state
  }
}