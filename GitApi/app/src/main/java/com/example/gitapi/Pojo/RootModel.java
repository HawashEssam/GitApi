package com.example.gitapi.Pojo;

import java.util.ArrayList;

public class
RootModel {
    private int total_count;
    private boolean incomplete_results;

    ArrayList<ItemsModel> items =new ArrayList<>();

    public ArrayList<ItemsModel> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemsModel> items) {
        this.items = items;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public boolean isIncomplete_results() {
        return incomplete_results;
    }

    public void setIncomplete_results(boolean incomplete_results) {
        this.incomplete_results = incomplete_results;
    }
}
