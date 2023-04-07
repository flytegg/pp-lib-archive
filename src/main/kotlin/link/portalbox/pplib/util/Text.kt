package link.portalbox.pplib.util

/**
 * Finds the highest integer number in the given list of strings.
 * If no integer number is found, returns null.
 *
 * @param strings The list of strings to search.
 * @return The highest integer number found, or null if no integer number is found.
 */
fun findHighestNumber(strings: List<String>): Int? {
    return strings
        .mapNotNull { it.trim().toIntOrNull() }
        .maxOrNull()
}
