package com.ravikantsharma.compose_pageflip.ui.flip

sealed class FlipPagerOrientation {
    data object Vertical: FlipPagerOrientation()
    data object Horizontal: FlipPagerOrientation()
}