import org.apache.spark.network.util.JavaUtils

import java.util.concurrent.TimeUnit

object Demo {
  def main(args: Array[String]): Unit = {
    println(JavaUtils.timeStringAs("2021-09-01", TimeUnit.SECONDS))
  }

}