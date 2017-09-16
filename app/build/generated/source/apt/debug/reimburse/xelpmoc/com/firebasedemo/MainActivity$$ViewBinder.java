// Generated code from Butter Knife. Do not modify!
package reimburse.xelpmoc.com.firebasedemo;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MainActivity$$ViewBinder<T extends reimburse.xelpmoc.com.firebasedemo.MainActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558532, "field 'editEmail'");
    target.editEmail = finder.castView(view, 2131558532, "field 'editEmail'");
    view = finder.findRequiredView(source, 2131558533, "field 'editPAssword'");
    target.editPAssword = finder.castView(view, 2131558533, "field 'editPAssword'");
    view = finder.findRequiredView(source, 2131558534, "method 'SubmitDetails'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.SubmitDetails();
        }
      });
    view = finder.findRequiredView(source, 2131558535, "method 'SignUp'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.SignUp();
        }
      });
  }

  @Override public void unbind(T target) {
    target.editEmail = null;
    target.editPAssword = null;
  }
}
