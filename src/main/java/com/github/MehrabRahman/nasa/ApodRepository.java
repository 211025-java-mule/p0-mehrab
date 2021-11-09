package com.github.MehrabRahman.nasa;

import org.springframework.stereotype.Component;

@Component
public interface ApodRepository {
    public void create(Apod output);
}
