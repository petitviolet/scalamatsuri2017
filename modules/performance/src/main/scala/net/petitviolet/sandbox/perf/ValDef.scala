package net.petitviolet.sandbox.perf

import org.openjdk.jmh.annotations._

/**
 * a little `def` is faster
 * [info] Benchmark         (N)   Mode  Cnt          Score          Error  Units
 * [info] ValDef.defHeavy    10  thrpt   20    2407523.197 ±   106449.521  ops/s
 * [info] ValDef.valHeavy    10  thrpt   20    2500756.190 ±    35970.424  ops/s
 * [info] ValDef.defLight    10  thrpt   20  404379759.031 ±  4221763.228  ops/s
 * [info] ValDef.valLight    10  thrpt   20  345622658.732 ±  2851731.608  ops/s
 *
 * [info] ValDef.defHeavy   100  thrpt   20     466049.253 ±     4312.192  ops/s
 * [info] ValDef.valHeavy   100  thrpt   20     448293.509 ±     2568.814  ops/s
 * [info] ValDef.defLight   100  thrpt   20  405220625.556 ±  2604729.646  ops/s
 * [info] ValDef.valLight   100  thrpt   20  347918434.761 ±  2766661.683  ops/s
 *
 * [info] ValDef.defHeavy  1000  thrpt   20      48376.713 ±      834.762  ops/s
 * [info] ValDef.valHeavy  1000  thrpt   20      46069.093 ±      416.818  ops/s
 * [info] ValDef.defLight  1000  thrpt   20  361894876.917 ± 77044466.386  ops/s
 * [info] ValDef.valLight  1000  thrpt   20  347141304.545 ±  2012639.189  ops/s
 * sbt 'project performance' 'jmh:run -i 20 -wi 20 -f1 .*ValDef.*'
 */
@State(Scope.Benchmark)
class ValDef {
  @Param(Array("10", "100", "1000"))
  var N: Int = _

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput))
  def valLight() = {
    val run: Int => Int = _ * 2
    run(N)
  }

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput))
  def defLight() = {
    def run(i: Int): Int = i * 2
    run(N)
  }

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput))
  def valHeavy() = {
    val run: Int => Int = i => {
      (0 to i).map {_ % 5}.distinct.sum
    }

    run(N)
  }

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput))
  def defHeavy() = {
    def run(i: Int): Int = {
      (0 to i).map {_ % 5}.distinct.sum
    }

    run(N)
  }
}
