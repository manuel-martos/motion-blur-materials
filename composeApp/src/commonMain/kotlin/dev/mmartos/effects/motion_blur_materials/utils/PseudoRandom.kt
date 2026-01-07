package dev.mmartos.effects.motion_blur_materials.utils

/**
 * Generates a deterministic pseudo-random floating-point value from a given seed.
 *
 * This function uses a 64-bit mixing algorithm (inspired by SplitMix64-style
 * bit scrambling) to transform the input seed into a uniformly distributed
 * pseudo-random value. The result is deterministic: the same seed will always
 * produce the same output.
 *
 * The generated value is derived from the top 53 bits of the mixed 64-bit state,
 * making it suitable for mapping to a floating-point number with good statistical
 * properties.
 *
 * @param seed A 64-bit value used as the deterministic source of randomness.
 * @return A pseudo-random `Float` in the range [0.0, 1.0).
 */
fun randomFloatFromSeed(seed: Long): Float {
    var x = seed.toULong() + 0x9E8779B97F2A7C15uL
    x = x xor (x shr 12)
    x = x xor (x shl 25)
    x = x xor (x shr 27)
    val z = x * 2685821657736338717uL
    val bits53 = z shr 11
    return (bits53.toFloat() * (1.0f / (1L shl 53)))
}
