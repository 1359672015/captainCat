package com.example.captaincat.Ui.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;

public class AvoidLeakDialog extends Dialog {


    public AvoidLeakDialog(@NonNull Context context) {
        super(context);
    }

    public AvoidLeakDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected AvoidLeakDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    public void setOnDismissListener(@Nullable OnDismissListener listener) {
        super.setOnDismissListener(new WrappedDismissDialogListener(listener));
    }

    @Override
    public void setOnShowListener(@Nullable OnShowListener listener) {
        super.setOnShowListener(new WrappedShowListener(listener));
    }

    @Override
    public void setOnCancelListener(@Nullable OnCancelListener listener) {
        super.setOnCancelListener(new WrappedCancelListener(listener));
    }

    private static class WrappedCancelListener implements OnCancelListener {
        private WeakReference<OnCancelListener> weakRef;

        public WrappedCancelListener(OnCancelListener delegate) {
            weakRef = new WeakReference(delegate);
        }

        @Override
        public void onCancel(DialogInterface dialog) {
            if (weakRef != null&&weakRef.get()!=null)
                weakRef.get().onCancel(dialog);
        }
    }

    private static class WrappedDismissDialogListener implements OnDismissListener {
        private WeakReference<OnDismissListener> weakRef;

        public WrappedDismissDialogListener(OnDismissListener delegate) {
            weakRef = new WeakReference(delegate);
        }


        @Override
        public void onDismiss(DialogInterface dialog) {
            if (weakRef != null&&weakRef.get()!=null)
                weakRef.get().onDismiss(dialog);
        }
    }

    private static class WrappedShowListener implements OnShowListener {
        private WeakReference<OnShowListener> weakRef;

        public WrappedShowListener(OnShowListener delegate) {
            weakRef = new WeakReference(delegate);
        }


        @Override
        public void onShow(DialogInterface dialog) {
            if (weakRef != null&&weakRef.get()!=null)
                weakRef.get().onShow(dialog);
        }
    }
}
