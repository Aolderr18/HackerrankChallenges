import scala.collection.mutable.ArrayBuffer 
import scala.io.Source  

object Solution {   
  def main(args: Array[String]) {         
    var N = 0          
    var T = 0         
    do {             
      T = readLine.toInt         
    } while (!(1 <= T && T <= 100000))         
    while (T >= 1) {             
    do {                 
      N = readLine.toInt             
    } while(!(1 <= N && N <= 100000))             
    val fibo = ArrayBuffer[Int]()            
    /*             
    * The Fibonacci Sequence should be an array buffer since its size can never be finite.             
    */             
    fibo.insert(0, 0) // The first number in the sequence is 0.             
    fibo.insert(1, 1) // The second number in the sequence is 1.             
    var a = 0             
    var b = 1             
    var index = 1             
    while (fibo(index) <= N) {                 
      /*                 
      * Each sequencial Fibonacci integer should be the sum of the previous two. As each index increments, each value of the                 
      * previous two should change.                 
      */                 
      val temp = b                 
      b = b + a                 
      a = temp                 
      fibo.insert(index + 1, b)                 
      index += 1             
    }              
    /*               
    * To know whether or not N is in the Fibonacci Sequence, if the previous index of it is equal to N, N must be part of it since the               
    * previous loop breaks if the current fibo index is greater than N.               
    */              
    if (fibo(index - 1) == N)                  
      println("IsFibo")              
    else                 
      println("IsNotFibo")              
    T -= 1         
    }      
  }  
} 
