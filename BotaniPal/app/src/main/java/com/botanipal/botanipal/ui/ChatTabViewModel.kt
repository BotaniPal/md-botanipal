package com.botanipal.botanipal.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.botanipal.botanipal.data.model.ChaTanis
import com.botanipal.botanipal.data.model.Topics
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

class ChatTabViewModel : ViewModel() {
    private val _topic = MutableLiveData<List<Topics>>()
    val topic: LiveData<List<Topics>> = _topic

    private val _isLoadingTopics = MutableLiveData<Boolean>()
    val isLoadingTopics: LiveData<Boolean> = _isLoadingTopics

    private val _chatanis = MutableLiveData<List<ChaTanis>>()
    val chatanis: LiveData<List<ChaTanis>> = _chatanis

    private val _isLoadingChatanis = MutableLiveData<Boolean>()
    val isLoadingChatanis: LiveData<Boolean> = _isLoadingChatanis

    fun searchTopics() {
        _isLoadingTopics.value = true
        _topic.value = listOf(
            Topics("How to grow corn", "Learn how to grow corn in your backyard", Timestamp(SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2024-01-04 15:00:00")!!.time), false),
            Topics("How to grow wheat", "Learn how to grow wheat in your backyard", Timestamp(SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2024-01-04 15:00:00")!!.time), false),
            Topics("How to grow rice", "Learn how to grow rice in your backyard", Timestamp(SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2024-01-04 15:00:00")!!.time), false),
            Topics("How to grow beans", "Learn how to grow beans in your backyard", Timestamp(SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2024-01-04 15:00:00")!!.time), false),
            Topics("How to grow sugar", "Learn how to grow sugar in your backyard", Timestamp(SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2024-01-04 15:00:00")!!.time), false),
            Topics("How to grow coffee", "Learn how to grow coffee in your backyard", Timestamp(SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2024-01-04 15:00:00")!!.time), false),
            Topics("How to grow tea", "Learn how to grow tea in your backyard", Timestamp(SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2024-01-04 15:00:00")!!.time), false),
            Topics("How to grow potatoes", "Learn how to grow potatoes in your backyard", Timestamp(SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2024-01-04 15:00:00")!!.time), false),
            Topics("How to grow corn", "Learn how to grow corn in your backyard", Timestamp(SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2024-01-04 15:00:00")!!.time), false),
            Topics("How to grow wheat", "Learn how to grow wheat in your backyard", Timestamp(SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2024-01-04 15:00:00")!!.time), false),
        )
        _isLoadingTopics.value = false
    }

    fun searchChatanis() {
        _isLoadingChatanis.value = true
        _chatanis.value = listOf(
            ChaTanis("How to grow corn", "Learn how to grow corn in your backyard", Timestamp(SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2024-01-04 15:00:00")!!.time)),
            ChaTanis("How to grow wheat", "Learn how to grow wheat in your backyard", Timestamp(SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2024-01-04 15:00:00")!!.time)),
            ChaTanis("How to grow rice", "Learn how to grow rice in your backyard", Timestamp(SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2024-01-04 15:00:00")!!.time)),
            ChaTanis("How to grow beans", "Learn how to grow beans in your backyard", Timestamp(SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2024-01-04 15:00:00")!!.time)),
            ChaTanis("How to grow sugar", "Learn how to grow sugar in your backyard", Timestamp(SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2024-01-04 15:00:00")!!.time)),
            ChaTanis("How to grow coffee", "Learn how to grow coffee in your backyard", Timestamp(SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2024-01-04 15:00:00")!!.time)),
            ChaTanis("How to grow tea", "Learn how to grow tea in your backyard", Timestamp(SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2024-01-04 15:00:00")!!.time)),
            ChaTanis("How to grow potatoes", "Learn how to grow potatoes in your backyard", Timestamp(SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2024-01-04 15:00:00")!!.time)),
            ChaTanis("How to grow corn", "Learn how to grow corn in your backyard", Timestamp(SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2024-01-04 15:00:00")!!.time)),
            ChaTanis("How to grow wheat", "Learn how to grow wheat in your backyard", Timestamp(SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2024-01-04 15:00:00")!!.time)),
        )
        _isLoadingChatanis.value = false
    }
}