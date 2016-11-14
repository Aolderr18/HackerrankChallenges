/*
 * At the annual meeting of Board of Directors of Acme Inc, every one starts 
 * shaking hands with everyone else in the room. Given the fact that any two 
 * persons shake hand exactly once, Can you tell the total count of handshakes?
*/

object Solution {

    def main(args: Array[String]) {
        var T : Int = 0
        var N : Int = 0
        do {
            T = readLine.toInt
        } while (T < 1 || T > 1000)
        while (T > 0) {
            T -= 1
            do {
                N = readLine.toInt
            } while (N < 1 || N > 1000000)
            var numberOfHandShakes : Int = 0
            while (N > 1) {
                N -= 1
                numberOfHandShakes += N
            }
            println(numberOfHandShakes)
        }
    }
}
