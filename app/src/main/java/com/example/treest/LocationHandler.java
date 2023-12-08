package com.example.treest;

import android.location.Location;

public interface LocationHandler {
    void HandleLocationResponse(Location location);

    void HandleNonPermission();
}
