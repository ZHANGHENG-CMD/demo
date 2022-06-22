package com.example.demo.node;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.LinkedHashSet;

/**
 * 代表当前主机的信息。
 */
public class HostInfo implements Serializable {

	private static final Logger logger = LoggerFactory.getLogger(HostInfo.class);

	private static final long serialVersionUID = 1L;

	private String hostName;
	private String hostAddress;

	public HostInfo(){
		try {
			LinkedHashSet<InetAddress> hostSet = NetUtil.getLocalhost();
			hostSet.add(InetAddress.getLocalHost());
			final InetAddress localhost = new ArrayList<>(hostSet).get(0);
			hostName = localhost.getHostName();
			hostAddress = localhost.getHostAddress();
		} catch (Exception e) {
			logger.error("获取主机信息异常:",e);
		}
	}

	/**
	 * 取得当前主机的名称。
	 *
	 * <p>
	 * 例如：<code>"webserver1"</code>
	 * </p>
	 *
	 * @return 主机名
	 */
	public final String getName() {
		return hostName;
	}

	/**
	 * 取得当前主机的地址。
	 *
	 * <p>
	 * 例如：<code>"192.168.0.1"</code>
	 * </p>
	 *
	 * @return 主机地址
	 */
	public final String getAddress() {
		return hostAddress;
	}
}
