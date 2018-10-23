package com.jopool.chargingStation.www.netty.service.codec;

import com.jopool.chargingStation.www.netty.util.BitOperator;
import com.jopool.chargingStation.www.netty.util.JT808ProtocolUtils;

public class MsgEncoder {
	private BitOperator bitOperator;
	private JT808ProtocolUtils jt808ProtocolUtils;

	public MsgEncoder() {
		this.bitOperator = new BitOperator();
		this.jt808ProtocolUtils = new JT808ProtocolUtils();
	}

}
