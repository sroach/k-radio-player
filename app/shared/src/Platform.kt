package gy.roach.kradioplayer

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
