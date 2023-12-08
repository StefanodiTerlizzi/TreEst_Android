// Generated by view binder compiler. Do not edit!
package com.example.treest.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.treest.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class LineElementBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView tvTerminus1;

  @NonNull
  public final TextView tvTerminus2;

  @NonNull
  public final TextView tvTitle;

  @NonNull
  public final View vT1;

  @NonNull
  public final View vT2;

  private LineElementBinding(@NonNull ConstraintLayout rootView, @NonNull TextView tvTerminus1,
      @NonNull TextView tvTerminus2, @NonNull TextView tvTitle, @NonNull View vT1,
      @NonNull View vT2) {
    this.rootView = rootView;
    this.tvTerminus1 = tvTerminus1;
    this.tvTerminus2 = tvTerminus2;
    this.tvTitle = tvTitle;
    this.vT1 = vT1;
    this.vT2 = vT2;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static LineElementBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static LineElementBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.line_element, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static LineElementBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.tv_terminus1;
      TextView tvTerminus1 = ViewBindings.findChildViewById(rootView, id);
      if (tvTerminus1 == null) {
        break missingId;
      }

      id = R.id.tv_terminus2;
      TextView tvTerminus2 = ViewBindings.findChildViewById(rootView, id);
      if (tvTerminus2 == null) {
        break missingId;
      }

      id = R.id.tv_title;
      TextView tvTitle = ViewBindings.findChildViewById(rootView, id);
      if (tvTitle == null) {
        break missingId;
      }

      id = R.id.v_t1;
      View vT1 = ViewBindings.findChildViewById(rootView, id);
      if (vT1 == null) {
        break missingId;
      }

      id = R.id.v_t2;
      View vT2 = ViewBindings.findChildViewById(rootView, id);
      if (vT2 == null) {
        break missingId;
      }

      return new LineElementBinding((ConstraintLayout) rootView, tvTerminus1, tvTerminus2, tvTitle,
          vT1, vT2);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
