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

package com.neoremind.kraps.rpc

import com.neoremind.kraps.util.Utils


/**
  * Address for an RPC environment, with hostname and port.
  */
case class RpcAddress(host: String, port: Int) {
  /**
   *  地址加端口
   * @return
   */
  def hostPort: String = host + ":" + port

  /**
   * Returns a string in the form of "spark://host:port".
   * 拼接处spark方式的地址
   */
  def toSparkURL: String = "spark://" + hostPort

  /**
   * host加端口
   * @return
   */
  override def toString: String = hostPort
}


object RpcAddress {

  /** Return the [[RpcAddress]] represented by `uri`. */
  def fromURIString(uri: String): RpcAddress = {
    val uriObj = new java.net.URI(uri)
    RpcAddress(uriObj.getHost, uriObj.getPort)
  }

  /**
   * Returns the [[RpcAddress]] encoded in the form of "spark://host:port"
   * 从spark方式的url里面获取host 和port
   */
  def fromSparkURL(sparkUrl: String): RpcAddress = {
    val (host, port) = Utils.extractHostPortFromKrapsUrl(sparkUrl)
    RpcAddress(host, port)
  }
}
