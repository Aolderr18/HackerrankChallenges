/*
* Jim is off to a party and is searching for a matching pair of socks. His drawer is 
* filled with socks, each pair of a different color. In its worst case scenario, how 
* many socks (x) should Jim remove from his drawer until he finds a matching pair?
*/

object Solution {

    def main(args: Array[String]) {
        var T : Int = 0
        do {
            T = readLine.toInt
            // The contrains are that 1 <= T <= 10000.
        } while (T < 1 || T > 10000)
        var n : Int = 0
        while (T >= 1) {
            T -= 1
            do {
                n = readLine.toInt
            } while (n < 1 || n > 1000000)
            val maxNumberOfDraws : Int = n + 1
                /*
                * The reason why the maximum number of draws is equal to
                * one more than the number of pairs of socks in the drawer
                * is because if in the worst scenario, Jim draws one of each
                * color of sock without finding a pair, the next sock will
                * inevitably match a sock already drawn.
                */
            println(maxNumberOfDraws)
        }
    }
}
