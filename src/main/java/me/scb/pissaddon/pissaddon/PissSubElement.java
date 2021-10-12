package me.scb.pissaddon.pissaddon;



import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.Element.ElementType;
import com.projectkorra.projectkorra.Element.SubElement;
import org.bukkit.ChatColor;

public class PissSubElement {
    public static final SubElement PISS;

    public PissSubElement() {
    }


    static {
        PISS = new SubElement("Piss", Element.WATER, ElementType.BENDING, Pissaddon.plugin) {

            @Override
            public ChatColor getColor() {
                return (ChatColor.YELLOW);
            }
        };
    }
}
