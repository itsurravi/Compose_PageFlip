@file:OptIn(ExperimentalFoundationApi::class)

package com.ravikantsharma.compose_pageflip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.ravikantsharma.compose_pageflip.data.headlines
import com.ravikantsharma.compose_pageflip.ui.FakeAppBar
import com.ravikantsharma.compose_pageflip.ui.HeadlineArticle
import com.ravikantsharma.compose_pageflip.ui.flip.FlipPager
import com.ravikantsharma.compose_pageflip.ui.flip.FlipPagerOrientation
import com.ravikantsharma.compose_pageflip.ui.theme.Compose_PageFlipTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { App() }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun App() {
    val isSystemDark = isSystemInDarkTheme()
    var darkMode by remember {
        mutableStateOf(isSystemDark)
    }
    Compose_PageFlipTheme(darkTheme = darkMode) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Column(
                Modifier.navigationBarsPadding()
            ) {
                var orientation: FlipPagerOrientation by remember {
                    mutableStateOf(FlipPagerOrientation.Vertical)
                }
                val state = rememberPagerState { headlines.size }
                FakeAppBar(
                    modifier = Modifier.statusBarsPadding(),
                    orientation = orientation,
                    darkMode = darkMode,
                    setOrientation = { if (!state.isScrollInProgress) orientation = it },
                    setDarkMode = { darkMode = it }
                )

                FlipPager(
                    state = state,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    orientation = orientation,
                ) { page ->
                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                            .clip(RoundedCornerShape(16.dp)),
                    ) {
                        HeadlineArticle(
                            modifier = Modifier.align(Alignment.Center),
                            headline = headlines[page],
                        )
                    }
                }
            }
        }
    }
}