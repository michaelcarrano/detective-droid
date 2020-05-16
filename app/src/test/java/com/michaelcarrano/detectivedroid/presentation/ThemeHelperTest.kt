import android.content.Context
import android.os.Build
import android.os.Build.VERSION
import androidx.appcompat.app.AppCompatDelegate
import com.michaelcarrano.detectivedroid.presentation.ThemeHelper
import com.nhaarman.mockitokotlin2.mock
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.robolectric.util.ReflectionHelpers

class ThemeHelperTest {
    private val context = mock<Context>()

    @Before
    fun setUp() {
        mockkStatic(AppCompatDelegate::class)
    }

    @Test
    fun `Given DARK_MODE, when applyTheme called, then enable night mode`() {
        verifyThemeSet(ThemeHelper.DARK_MODE, AppCompatDelegate.MODE_NIGHT_YES)
    }

    @Test
    fun `Given LIGHT_MODE, when applyTheme called, then disable night mode`() {
        verifyThemeSet(ThemeHelper.LIGHT_MODE, AppCompatDelegate.MODE_NIGHT_NO)
    }

    @Test
    fun `Given DEFAULT_MODE (SYSTEM) and Android Q, when applyTheme called, then follow system setting`() {
        setVersionCode(Build.VERSION_CODES.Q)

        verifyThemeSet(ThemeHelper.DEFAULT_MODE, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }

    @Test
    fun `Given DEFAULT_MODE (SYSTEM) and Android L, when applyTheme called, then follow battery saver`() {
        setVersionCode(Build.VERSION_CODES.LOLLIPOP)

        verifyThemeSet(ThemeHelper.DEFAULT_MODE, AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
    }

    @Test
    fun `Given DEFAULT_MODE (SYSTEM) and Android K, when applyTheme called, then no night mode`() {
        setVersionCode(Build.VERSION_CODES.KITKAT)

        verifyThemeSet(ThemeHelper.DEFAULT_MODE, AppCompatDelegate.MODE_NIGHT_NO)
    }

    @Test
    fun `Given invalid theme and Android Q, when applyTheme called, then follow system setting`() {
        setVersionCode(Build.VERSION_CODES.Q)

        verifyThemeSet("some fake theme", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }

    @Test
    fun `Given invalid theme and Android L, when applyTheme called, then follow battery saver`() {
        setVersionCode(Build.VERSION_CODES.LOLLIPOP)

        verifyThemeSet("some fake theme", AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
    }

    @Test
    fun `Given invalid theme and Android K, when applyTheme called, then no night mode`() {
        setVersionCode(Build.VERSION_CODES.KITKAT)

        verifyThemeSet("some fake theme", AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun verifyThemeSet(themeSetting: String, expectedTheme: Int) {
        // When
        ThemeHelper.applyTheme(themeSetting)

        // Then
        verify {
            AppCompatDelegate.setDefaultNightMode(expectedTheme)
        }
    }

    private fun setVersionCode(sdkInt: Int) {
        ReflectionHelpers.setStaticField(VERSION::class.java, "SDK_INT", sdkInt)
    }
}
