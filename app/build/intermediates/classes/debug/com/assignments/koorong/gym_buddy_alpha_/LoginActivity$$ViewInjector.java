// Generated code from Butter Knife. Do not modify!
package com.assignments.koorong.gym_buddy_alpha_;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class LoginActivity$$ViewInjector<T extends com.assignments.koorong.gym_buddy_alpha_.LoginActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624036, "field '_emailText'");
    target._emailText = finder.castView(view, 2131624036, "field '_emailText'");
    view = finder.findRequiredView(source, 2131624037, "field '_passwordText'");
    target._passwordText = finder.castView(view, 2131624037, "field '_passwordText'");
    view = finder.findRequiredView(source, 2131624038, "field '_loginButton'");
    target._loginButton = finder.castView(view, 2131624038, "field '_loginButton'");
    view = finder.findRequiredView(source, 2131624039, "field '_signupLink'");
    target._signupLink = finder.castView(view, 2131624039, "field '_signupLink'");
  }

  @Override public void reset(T target) {
    target._emailText = null;
    target._passwordText = null;
    target._loginButton = null;
    target._signupLink = null;
  }
}
