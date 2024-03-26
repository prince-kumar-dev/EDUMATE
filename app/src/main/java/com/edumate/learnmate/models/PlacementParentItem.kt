package com.edumate.learnmate.models

data class PlacementParentItem(
    val title: String = "",
    val logo: Int,
    val mList: List<PlacementChildItem>
)
