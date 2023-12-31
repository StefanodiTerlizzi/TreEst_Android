// Generated by view binder compiler. Do not edit!
package com.example.treest.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.treest.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class PostofficialBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView author;

  @NonNull
  public final ConstraintLayout background;

  @NonNull
  public final TextView date;

  @NonNull
  public final TextView description;

  @NonNull
  public final View divider;

  @NonNull
  public final Guideline guidelineCenter;

  @NonNull
  public final ImageView imageView;

  @NonNull
  public final TextView time;

  @NonNull
  public final TextView title;

  @NonNull
  public final TextView titleLabel;

  private PostofficialBinding(@NonNull ConstraintLayout rootView, @NonNull TextView author,
      @NonNull ConstraintLayout background, @NonNull TextView date, @NonNull TextView description,
      @NonNull View divider, @NonNull Guideline guidelineCenter, @NonNull ImageView imageView,
      @NonNull TextView time, @NonNull TextView title, @NonNull TextView titleLabel) {
    this.rootView = rootView;
    this.author = author;
    this.background = background;
    this.date = date;
    this.description = description;
    this.divider = divider;
    this.guidelineCenter = guidelineCenter;
    this.imageView = imageView;
    this.time = time;
    this.title = title;
    this.titleLabel = titleLabel;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static PostofficialBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static PostofficialBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.postofficial, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static PostofficialBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.author;
      TextView author = ViewBindings.findChildViewById(rootView, id);
      if (author == null) {
        break missingId;
      }

      ConstraintLayout background = (ConstraintLayout) rootView;

      id = R.id.date;
      TextView date = ViewBindings.findChildViewById(rootView, id);
      if (date == null) {
        break missingId;
      }

      id = R.id.description;
      TextView description = ViewBindings.findChildViewById(rootView, id);
      if (description == null) {
        break missingId;
      }

      id = R.id.divider;
      View divider = ViewBindings.findChildViewById(rootView, id);
      if (divider == null) {
        break missingId;
      }

      id = R.id.guideline_center;
      Guideline guidelineCenter = ViewBindings.findChildViewById(rootView, id);
      if (guidelineCenter == null) {
        break missingId;
      }

      id = R.id.imageView;
      ImageView imageView = ViewBindings.findChildViewById(rootView, id);
      if (imageView == null) {
        break missingId;
      }

      id = R.id.time;
      TextView time = ViewBindings.findChildViewById(rootView, id);
      if (time == null) {
        break missingId;
      }

      id = R.id.title;
      TextView title = ViewBindings.findChildViewById(rootView, id);
      if (title == null) {
        break missingId;
      }

      id = R.id.title_label;
      TextView titleLabel = ViewBindings.findChildViewById(rootView, id);
      if (titleLabel == null) {
        break missingId;
      }

      return new PostofficialBinding((ConstraintLayout) rootView, author, background, date,
          description, divider, guidelineCenter, imageView, time, title, titleLabel);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
