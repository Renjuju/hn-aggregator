package com.renju.data.models

data class HackerAggregatePost(
    val post: HackerPost = HackerPost(),
    val comments: ArrayList<HackerComment> = ArrayList()
)

data class HackerPost(
    val by: String = "",
    val descendants: Int = 0,
    val id: Int = 0,
    val kids: ArrayList<Int> = ArrayList(),
    val score: Int = 0,
    val time: Long = 0L,
    val title: String = "",
    val type: String = "",
    val url: String = ""
)

data class HackerComment(
    val by: String = "cultus",
    val id: Int = 0,
    val kids: ArrayList<Int> = ArrayList(),
    val parent: Long = 0,
    val text: String = "",
    val time: Long = 0,
    val type: String = "comment",
    val childComments: ArrayList<HackerComment> = ArrayList()
)