package com.edumate.fellowmate.models

data class CompleteParentCoursePackage(
    val stepTitle: String = "",
    val stepDescription: String = "",
    val mList: List<CompleteChildCoursePackage>
)