package artifacts.client.network;

import artifacts.common.capability.SwimHandler;
import artifacts.common.network.SinkPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class ClientPacketHandler {

    public static void handleSinkPacket(SinkPacket packet) {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            player.getCapability(SwimHandler.CAPABILITY).ifPresent(handler -> handler.setSinking(packet.shouldSink));
        }
    }
}
