package edu.uestc.sdn;

import struct.StructClass;
import struct.StructField;



@StructClass
public class Packet_in {
    @StructField(order = 0)
    public short ingress_port ;

    @StructField(order = 1)
    public short reason ;

    @StructField(order = 2)
    public byte[] dmac = new byte[6];

    @StructField(order = 3)
    public byte[] smac = new byte[6];
}
