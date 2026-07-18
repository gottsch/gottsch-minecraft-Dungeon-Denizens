/*
 * This file is part of  Dungeon Denizens.
 * Copyright (c) 2024 Mark Gottschling (gottsch)
 *
 * All rights reserved.
 *
 * Dungeon Denizens is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Dungeon Denizens is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Dungeon Denizens.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */
package com.someguyssoftware.ddenizens.client;

import com.someguyssoftware.ddenizens.util.LangUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.HoverEvent;

/**
 * Client-only helper that opens the {@link GuideBookItem}'s single page. Kept separate from the
 * item so its client-only references (Minecraft, screens) are never classloaded on a server.
 *
 * @author Mark Gottschling
 */
public class GuideBookClient {

    /** External wiki the guide book links to (gottsch's Monster Manual, the framework DD's mobs run on). */
    public static final String WIKI_URL = "https://github.com/gottsch/gottsch-minecraft-Monster-Manual/wiki";

    private GuideBookClient() {}

    public static void open() {
        Component page = Component.translatable(LangUtil.screen("guide.intro"))
                .append("\n\n")
                .append(Component.translatable(LangUtil.screen("guide.link_label"))
                        .withStyle(style -> style
                                .withColor(ChatFormatting.BLUE)
                                .withUnderlined(true)
                                .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, WIKI_URL))
                                .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal(WIKI_URL)))));

        Minecraft.getInstance().setScreen(new BookViewScreen(new BookViewScreen.BookAccess() {
            @Override
            public int getPageCount() {
                return 1;
            }

            @Override
            public FormattedText getPageRaw(int index) {
                return page;
            }
        }));
    }
}
