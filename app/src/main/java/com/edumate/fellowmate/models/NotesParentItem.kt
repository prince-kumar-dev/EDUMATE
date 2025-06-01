package com.edumate.fellowmate.models

data class NotesParentItem(
    val title: String = "",
    val logo: Int,
    val mList: List<NotesChildItem>
)
