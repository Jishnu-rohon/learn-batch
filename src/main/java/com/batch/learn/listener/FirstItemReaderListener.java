package com.batch.learn.listener;

import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Service;

@Service
public class FirstItemReaderListener implements ItemReadListener<Integer> {
    /**
     *
     */
    @Override
    public void beforeRead() {
        ItemReadListener.super.beforeRead();
    }

    /**
     * @param item
     */
    @Override
    public void afterRead(Integer item) {
        System.out.println("Inside Item reader listener: " + item);
        ItemReadListener.super.afterRead(item);
    }

    /**
     * @param ex
     */
    @Override
    public void onReadError(Exception ex) {
        ItemReadListener.super.onReadError(ex);
    }
}
