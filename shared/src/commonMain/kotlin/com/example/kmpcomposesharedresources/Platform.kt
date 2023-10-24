package com.example.kmpcomposesharedresources

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform