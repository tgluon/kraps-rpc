/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.neoremind.kraps.RpcConf
import com.neoremind.kraps.rpc.netty.NettyRpcEnvFactory
import com.neoremind.kraps.rpc.{RpcCallContext, RpcEndpoint, RpcEnv, RpcEnvServerConfig}

/**
 * Usage:
 * {{{
 *   java -server -Xms4096m -Xmx4096m -cp kraps-rpc-example_2.11-1.0.1-SNAPSHOT-jar-with-dependencies.jar HelloworldServer 10.32.240.1
 * }}}
 */
object HelloworldServer {

  def main(args: Array[String]): Unit = {
    //    val host = args(0)
    val host = "localhost"
    val config = RpcEnvServerConfig(new RpcConf(), "hello-server", host, 52345)
    val rpcEnv: RpcEnv = NettyRpcEnvFactory.create(config)
    val helloEndpoint: RpcEndpoint = new HelloEndpoint(rpcEnv)
    // 设置RpcEndpoint
    rpcEnv.setupEndpoint("hello-service", helloEndpoint)
    // 关闭线程池
    rpcEnv.awaitTermination()
  }
}
// 定义一个HelloEndpoint继承自RpcEndpoint表明可以并发的调用该服务，如果继承自ThreadSafeRpcEndpoint则表明该Endpoint不允许并发
class HelloEndpoint(override val rpcEnv: RpcEnv) extends RpcEndpoint {

  override def onStart(): Unit = {

    println("start hello endpoint")
  }
// 接收和响应
  override def receiveAndReply(context: RpcCallContext): PartialFunction[Any, Unit] = {
    case SayHi(msg) => {
      //println(s"receive $msg")
      context.reply(s"$msg")
    }
    case SayBye(msg) => {
      //println(s"receive $msg")
      context.reply(s"bye, $msg")
    }
  }

  override def onStop(): Unit = {
    println("stop hello endpoint")
  }
}


case class SayHi(msg: String)

case class SayBye(msg: String)
