package refaelozeri.com.counter.business;

import android.os.CountDownTimer;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

/**
 * Created by Refael Ozeri on 01/06/2016.
 */
public class CustomCountDown extends CountDownTimer {

  private static final long TICK_IN_MILLIS = 1000;

  private static final String DAYS_FORMAT = "%d %s %02d:%02d:%02d";

  private TextView mTextView;

  /**
   * @param textView          The updated TextView.
   *
   * @param millisInFuture    The number of millis in the future from the call
   *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
   *                          is called.
   */
  public CustomCountDown(TextView textView, long millisInFuture) {
    super(millisInFuture, TICK_IN_MILLIS);
    mTextView = textView;
  }

  @Override
  public void onTick(long millisUntilFinished) {

    long days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished);
    long hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished) - (TimeUnit.DAYS.toHours(days));
    long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - (TimeUnit.HOURS.toMinutes(hours) + TimeUnit.DAYS.toMinutes(days));
    long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - (TimeUnit.MINUTES.toSeconds(minutes) + TimeUnit.HOURS.toSeconds(hours) + TimeUnit.DAYS.toSeconds(days));

    mTextView.setText(String.format(DAYS_FORMAT,
        days,
        "Days and ",
        hours,
        minutes,
        seconds));
  }

  @Override
  public void onFinish() {

  }
}
