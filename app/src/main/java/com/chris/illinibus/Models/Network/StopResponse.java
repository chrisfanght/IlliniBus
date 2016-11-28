package com.chris.illinibus.Models.Network;

import java.util.List;

/**
 * Created by chrisfang on 11/27/16.
 */

public class StopResponse {
    List<StopTime> stop_times;

    public List<StopTime> getStopTimes() {
        return stop_times;
    }

    public void setStopTimes(List<StopTime> stop_times) {
        this.stop_times = stop_times;
    }
}
