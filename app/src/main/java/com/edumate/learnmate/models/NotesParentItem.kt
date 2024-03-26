package com.edumate.learnmate.models

data class NotesParentItem(
    val title: String = "",
    val logo: Int,
    val mList: List<NotesChildItem>
)
