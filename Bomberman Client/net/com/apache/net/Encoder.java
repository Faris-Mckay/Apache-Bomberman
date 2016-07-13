/* 
 * This file is part of Bomberman.
 *
 * Copyright (C) Apache-GS, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential.
 *
 * Further information can be acquired regarding the licensing of this product 
 * Apache-GS (C). In the project license directory.
 * Written by Faris McKay <faris.mckay@hotmail.com>, May 2016
 *
 */
package com.apache.net;

import java.util.Arrays;
import java.util.List;

import com.apache.net.packet.Packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

/**
 *
 * @author JP <https://github.com/TheRealJP>
 */
public class Encoder extends MessageToMessageEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, List<Object> out) throws Exception {
        System.out.println("Encoding in process... ");
        int headerLength = 3;
        int payloadLength = packet.getLength();

        ByteBuf buffer = Unpooled.buffer(headerLength + payloadLength);
        buffer.writeByte(packet.getOpcode());
        buffer.writeBytes(packet.getPayload());
        System.out.println("Opcode: " + packet.getOpcode() + " successfully added to buffer.");
        System.out.println("Payload:" + Arrays.toString(packet.getPayload().array()));
        out.add(buffer);
    }
}
