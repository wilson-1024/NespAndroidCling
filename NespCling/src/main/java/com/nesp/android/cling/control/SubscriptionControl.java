package com.nesp.android.cling.control;

import android.content.Context;
import androidx.annotation.NonNull;
import com.nesp.android.cling.entity.IDevice;
import com.nesp.android.cling.service.callback.AVTransportSubscriptionCallback;
import com.nesp.android.cling.service.callback.RenderingControlSubscriptionCallback;
import com.nesp.android.cling.service.manager.ClingManager;
import com.nesp.android.cling.util.ClingUtils;
import com.nesp.android.cling.util.Utils;
import org.fourthline.cling.controlpoint.ControlPoint;
import org.fourthline.cling.model.meta.Device;

/**
 * 说明：
 * 作者：zhouzhan
 * 日期：17/7/21 16:43
 */

public class SubscriptionControl implements ISubscriptionControl<Device> {

    private AVTransportSubscriptionCallback mAVTransportSubscriptionCallback;
    private RenderingControlSubscriptionCallback mRenderingControlSubscriptionCallback;

    public SubscriptionControl() {
    }

    @Override
    public void registerAVTransport(@NonNull IDevice<Device> device, @NonNull Context context) {
        if (Utils.isNotNull(mAVTransportSubscriptionCallback)) {
            mAVTransportSubscriptionCallback.end();
        }
        final ControlPoint controlPointImpl = ClingUtils.getControlPoint();
        if (Utils.isNull(controlPointImpl)) {
            return;
        }

        mAVTransportSubscriptionCallback = new AVTransportSubscriptionCallback(device.getDevice().findService(ClingManager.AV_TRANSPORT_SERVICE), context);
        controlPointImpl.execute(mAVTransportSubscriptionCallback);
    }

    @Override
    public void registerRenderingControl(@NonNull IDevice<Device> device, @NonNull Context context) {
        if (Utils.isNotNull(mRenderingControlSubscriptionCallback)) {
            mRenderingControlSubscriptionCallback.end();
        }
        final ControlPoint controlPointImpl = ClingUtils.getControlPoint();
        if (Utils.isNull(controlPointImpl)) {
            return;
        }
        mRenderingControlSubscriptionCallback = new RenderingControlSubscriptionCallback(device.getDevice().findService(ClingManager
                .RENDERING_CONTROL_SERVICE), context);
        controlPointImpl.execute(mRenderingControlSubscriptionCallback);
    }

    @Override
    public void destroy() {
        if (Utils.isNotNull(mAVTransportSubscriptionCallback)) {
            mAVTransportSubscriptionCallback.end();
        }
        if (Utils.isNotNull(mRenderingControlSubscriptionCallback)) {
            mRenderingControlSubscriptionCallback.end();
        }
    }
}
