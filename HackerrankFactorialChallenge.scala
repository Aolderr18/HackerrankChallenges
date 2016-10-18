/*
 * Manasa was sulking her way through a boring class when suddenly her teacher 
 * singled her out and asked her a question. He gave her a number n and Manasa 
 * has to come up with the smallest number m which contains atleast n number of 
 * zeros at the end of m!
 */

object HackerrankFactorialChallenge {

  def main(args: Array[String]) {
    var T: Int = 0
    do {
      T = readLine.toInt
    } while (T < 1 || T > 100); // Constraints: 1 <= T <= 100
    while (T > 0) {
      T -= 1
      var n: Int = 0
      do {
        n = readLine.toInt
      } while (n < 1 || n > 1000000000); /*
      * The contrain is that 1 <= n <= 10^16. However 10^16 is beyond the integer 
      * max value.
      */
      val m: Int = 5 * n
      /*
       * The number of zeroes at the end of the factorial increases by one every time
       * the integer increases by five. For example, 4! = 24, 5! = 120, 9! = 362880,
       * and 10! = 3628800. The reason for this is that with each increase of 5 integers,
       * there are exactly 2 integers along the way divisible by 2 and 5, which multiple 
       * to get 10. Hence every successive 5 integers factorial, the number of zeroes
       * at the end of it increases by one.
       */
      println(m)
    }
  }
}
