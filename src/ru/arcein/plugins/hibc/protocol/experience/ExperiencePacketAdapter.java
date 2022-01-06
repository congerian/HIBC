package ru.arcein.plugins.hibc.protocol.experience;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.entity.Player;
import ru.arcein.plugins.hibc.HIBC;

public class ExperiencePacketAdapter extends PacketAdapter {
    HIBC plugin;

    public ExperiencePacketAdapter(HIBC plugin) {
        super(plugin, ListenerPriority.NORMAL,
                PacketType.Play.Server.EXPERIENCE);

        this.plugin = plugin;
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        Player player = event.getPlayer();
        PacketContainer packet = event.getPacket();

        packet.getFloat().write(0, 0.0F);
        packet.getIntegers().write(0, 0);
        packet.getIntegers().write(1, 0);
    }
}
