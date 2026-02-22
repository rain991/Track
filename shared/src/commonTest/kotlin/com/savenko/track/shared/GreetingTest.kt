package com.savenko.track.shared

import kotlin.test.Test
import kotlin.test.assertTrue

class GreetingTest {
    @Test
    fun greetingContainsHello() {
        assertTrue(Greeting().greet().contains("Hello"))
    }
}
