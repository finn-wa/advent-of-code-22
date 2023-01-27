package advent.of.code.day13;

import java.util.List;

record PacketPair(
	List<Value<?>> left,
	List<Value<?>> right
) {}
