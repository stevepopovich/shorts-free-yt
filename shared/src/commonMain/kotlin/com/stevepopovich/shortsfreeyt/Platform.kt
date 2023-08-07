package com.stevepopovich.shortsfreeyt

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform