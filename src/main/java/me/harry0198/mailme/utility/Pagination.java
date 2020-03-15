package me.harry0198.mailme.utility;

import java.util.ArrayList;
import java.util.List;

public class Pagination<T> extends ArrayList<T>  {

    private int pageSize;

    public Pagination(int pageSize, List<T> objects) {
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

    public List<T> getPage(int page) {
        if (page < 0 || page >= totalPages()) throw new IndexOutOfBoundsException("Page: " + page + ", Total: " + totalPages());

        List<T> objects = new ArrayList<>();

        int min = page * pageSize;
        int max = ((page * pageSize) + pageSize);

        if (max > size()) max = size();

        for (int i = min; max > i; i++) objects.add(get(i));

        return objects;
    }
}
