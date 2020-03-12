package me.harry0198.mailme.ui;

import me.harry0198.mailme.MailMe;
import me.mattstudios.mfgui.gui.GUI;
import me.mattstudios.mfgui.gui.GuiItem;

import java.awt.*;
import java.util.*;
import java.util.List;

public class PaginatedGui {

    private final List<GuiItem> items;
    private final Map<Integer, GUI> gui = new HashMap<>();

    public PaginatedGui(int rowFrom, int colFrom, int rowTo, int colTo, List<GuiItem> items, int rows) {
        this.items = items;

        int min = getSlotFromRowCol( Math.min(rowFrom, rowTo), Math.min(colFrom, colTo) );
        int max = getSlotFromRowCol( Math.max(rowFrom, rowTo), Math.max(colFrom, colTo) );

        int pageSize = max - min;
        Pagination pagination = new Pagination(pageSize, items);

    }

    private int getSlotFromRowCol(final int row, final int col) {
        return (col + (row - 1) * 9) - 1;
    }


    private class Pagination extends ArrayList<GuiItem> {
        private int pageSize;

        public Pagination(int pageSize) {
            this(pageSize, new ArrayList<>());
        }

        public Pagination(int pageSize, GuiItem... objects) {
            this(pageSize, Arrays.asList(objects));
        }

        public Pagination(int pageSize, List<GuiItem> objects) {
            this.pageSize = pageSize;
            addAll(objects);
        }

        public int pageSize() {
            return pageSize;
        }

        public int totalPages() {
            return (int) Math.ceil((double) size() / pageSize);
        }

        public boolean exists(int page) {
            return page >= 0 && page < totalPages();
        }

        public List<GuiItem> getPage(int page) {
            if (page < 0 || page >= totalPages())
                throw new IndexOutOfBoundsException("Page: " + page + ", Total: " + totalPages());

            List<GuiItem> objects = new ArrayList<>();

            int min = page * pageSize;
            int max = ((page * pageSize) + pageSize);

            if (max > size()) max = size();

            for (int i = min; max > i; i++) objects.add(get(i));

            return objects;
        }
    }
}
