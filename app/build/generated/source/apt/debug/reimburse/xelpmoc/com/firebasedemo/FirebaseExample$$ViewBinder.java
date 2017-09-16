// Generated code from Butter Knife. Do not modify!
package reimburse.xelpmoc.com.firebasedemo;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class FirebaseExample$$ViewBinder<T extends reimburse.xelpmoc.com.firebasedemo.FirebaseExample> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558548, "field 'editvalue'");
    target.editvalue = finder.castView(view, 2131558548, "field 'editvalue'");
    view = finder.findRequiredView(source, 2131558553, "field 'imageDisplay'");
    target.imageDisplay = finder.castView(view, 2131558553, "field 'imageDisplay'");
    view = finder.findRequiredView(source, 2131558554, "field 'listValues'");
    target.listValues = finder.castView(view, 2131558554, "field 'listValues'");
    view = finder.findRequiredView(source, 2131558549, "field 'buttonUpdate' and method 'UpdateText'");
    target.buttonUpdate = finder.castView(view, 2131558549, "field 'buttonUpdate'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.UpdateText();
        }
      });
    view = finder.findRequiredView(source, 2131558550, "method 'addToFirebase'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.addToFirebase();
        }
      });
    view = finder.findRequiredView(source, 2131558551, "method 'retriveFirebase'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.retriveFirebase();
        }
      });
    view = finder.findRequiredView(source, 2131558552, "method 'addStorage'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.addStorage();
        }
      });
  }

  @Override public void unbind(T target) {
    target.editvalue = null;
    target.imageDisplay = null;
    target.listValues = null;
    target.buttonUpdate = null;
  }
}
