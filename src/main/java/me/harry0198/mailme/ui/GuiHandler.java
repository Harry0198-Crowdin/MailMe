package me.harry0198.mailme.ui;

import me.harry0198.mailme.MailMe;
import me.harry0198.mailme.ui.send.IconGui;
import me.harry0198.mailme.ui.send.MailGui;
import me.harry0198.mailme.ui.send.SendGui;

public final class GuiHandler {

    private final MailGui gui;
    private final IconGui iconGui;
    private final SendGui sendGui;
    private final PaginatedGui paginatedGui;

    public GuiHandler(MailMe plugin) {
        gui = new MailGui(plugin);
        iconGui = new IconGui(plugin);
        sendGui = new SendGui(plugin);
        paginatedGui = new PaginatedGui(plugin);
    }


    public MailGui getMailGui() {
        return gui;
    }

    public IconGui getIconGui() { return iconGui; }

    public SendGui getSendGui() {
        return sendGui;
    }

    public PaginatedGui getPaginatedGui() {
        return paginatedGui;
    }
}
