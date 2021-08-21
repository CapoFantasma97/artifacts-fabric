package artifacts.common.network;

import artifacts.client.network.ClientPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SinkPacket {

    public final boolean shouldSink;

    @SuppressWarnings("unused")
    public SinkPacket(FriendlyByteBuf buffer) {
        shouldSink = buffer.readBoolean();
    }

    public SinkPacket(boolean shouldSink) {
        this.shouldSink = shouldSink;
    }

    @SuppressWarnings("unused")
    void encode(FriendlyByteBuf buffer) {
        buffer.writeBoolean(shouldSink);
    }

    void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandler.handleSinkPacket(this)));
        context.get().setPacketHandled(true);
    }
}
