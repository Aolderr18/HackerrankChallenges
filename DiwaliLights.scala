/* On the eve of Diwali, Hari is decorating his house with a serial light bulb set. The serial light bulb set has N bulbs placed 
* sequentially on a string which is programmed to change patterns every second. If at least one bulb in the set is on at any given * instant of time, how many different patterns of light can the serial light bulb set produce?
* On the eve of Diwali, Hari is decorating his house with a serial light bulb set. The serial light bulb set has N bulbs placed 
* sequentially on a string which is programmed to change patterns every second. If at least one bulb in the set is on at any given * instant of time, how many different patterns of light can the serial light bulb set produce?
*/
object Solution {

    def main(args: Array[String]) {
        
        var T : Int = 0
        do {
            T = readLine.toInt
        } while (T < 1 || T > 1000)
        while (T >= 1) {
            T -= 1
            var N : Int = 0
            do {
                N = readLine.toInt
            } while (N < 1 || N > 10000)
            var numOfPatterns : Int = 0
            for (i <- 1 to N)
                numOfPatterns += nCr(N, i)
            println(numOfPatterns)
        }
        
        def factorial(x : Int) : Int = {
            if (x == 0)
                1
            else
                x * factorial(x - 1)
        }
        
        def nCr(n : Int, r : Int) : Int = {
            factorial(n) / (factorial(r) * factorial(n - r))
        }
    }
}
