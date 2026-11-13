package com.example.muzyczka_projekt

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform