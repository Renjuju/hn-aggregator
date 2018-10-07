package com.renju.data.collector

import com.google.gson.Gson
import com.renju.data.models.HackerAggregatePost
import com.renju.data.models.HackerComment
import com.renju.data.models.HackerPost
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.launch
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.util.*

@Service
@EnableScheduling
class DataService {
    private val logger = LoggerFactory.getLogger(DataService::class.java.name)

    @Autowired
    private lateinit var restTemplate: RestTemplate

    private val gson = Gson().newBuilder().setPrettyPrinting().create()
    private var post = 18155085

    @Scheduled(fixedRate = 1000)
    fun grabHtml() {
        GlobalScope.launch {
            val response = restTemplate.getForEntity(
                "https://hacker-news.firebaseio.com/v0/item/$post.json?print=pretty",
                HackerPost::class.java
            )
            val hackerPost = response.body ?: HackerPost()

            val childQueue: Queue<Int> = LinkedList()
            childQueue.addAll(hackerPost.kids)

            val commentList: ArrayList<HackerComment> = ArrayList()
            while (childQueue.isNotEmpty()) {
                val kid = childQueue.poll()
                val hackerComment = restTemplate.getForEntity(
                    "https://hacker-news.firebaseio.com/v0/item/$kid.json?print=pretty",
                    HackerComment::class.java
                ).body

                if (hackerComment != null) {
                    commentList.add(hackerComment)
                    childQueue.addAll(hackerComment.kids)
                }
            }
            val jsonTree = gson.toJson(HackerAggregatePost(hackerPost, commentList))
            logger.info(jsonTree)
            post++
        }

    }
}