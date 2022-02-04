package Classes;

import net.minecraft.server.v1_12_R1.ChatMessageType;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;

public class AaClassActionBar {
    public void send(Entity p, String message) {
        try {
            IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + message + "\"}");
            PacketPlayOutChat packet = new PacketPlayOutChat(icbc, ChatMessageType.GAME_INFO);
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        } catch (SecurityException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
