package refaelozeri.com.counter.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import refaelozeri.com.counter.R;
import refaelozeri.com.counter.business.CustomCountDown;

public class AddCounterActivity extends AppCompatActivity implements View.OnClickListener {

  private View mBgView;
  private TextView mCounterText, mCountDownText;
  private Button mAddButton;

  private CustomCountDown mCounter;
  private Calendar mCalendar;
  private SimpleDateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy");
  private SimpleDateFormat mHourFormat = new SimpleDateFormat("HH:mm");

  public static Intent getStartActivityIntent(Context context) {
    return new Intent(context, AddCounterActivity.class);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_counter);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    initViews();

    initCalendar();
  }

  private void initViews() {
    mBgView = (View) findViewById(R.id.add_counter_bg_view);
    mCounterText = (TextView) findViewById(R.id.add_counter_time_text);
    mCountDownText = (TextView) findViewById(R.id.add_counter_countdown);
    mAddButton = (Button) findViewById(R.id.add_counter_btn);

    findViewById(R.id.add_counter_calendar_img).setOnClickListener(this);
    findViewById(R.id.add_counter_clock_img).setOnClickListener(this);
    mAddButton.setOnClickListener(this);
  }

  private void initCalendar() {
    mCalendar = Calendar.getInstance();
    mCalendar.clear(Calendar.HOUR);
    mCalendar.clear(Calendar.MINUTE);
    mCalendar.set(Calendar.HOUR_OF_DAY, 10);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.add_counter_calendar_img:
        openDateDialog();
        break;
      case R.id.add_counter_clock_img:
        openTimeDialog();
        break;
      case R.id.add_counter_btn:
        //TODO 31.5 - Add functionality to the start button
        break;
    }
  }

  private void openTimeDialog() {
    new TimePickerDialog(AddCounterActivity.this, new TimePickerDialog.OnTimeSetListener() {
      @Override
      public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        mCalendar.set(Calendar.MINUTE, minute);
        updateText();
      }
    },
    mCalendar.get(Calendar.HOUR_OF_DAY),
    mCalendar.get(Calendar.MINUTE),
    true).show();
    //TODO (31.5) - Add support for AM:PM instead of hardcoded 24 hours use.
  }

  private void openDateDialog() {
    new DatePickerDialog(AddCounterActivity.this, new DatePickerDialog.OnDateSetListener() {
      @Override
      public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, monthOfYear);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateText();
      }
    }, mCalendar.get(Calendar.YEAR),
        mCalendar.get(Calendar.MONTH),
        mCalendar.get(Calendar.DAY_OF_MONTH))
        .show();
  }

  private void updateText() {
    mCounterText.setVisibility(View.VISIBLE);
    mCountDownText.setVisibility(View.VISIBLE);
    mAddButton.setVisibility(View.VISIBLE);
    mBgView.setVisibility(View.VISIBLE);

    StringBuilder sb = new StringBuilder();
    sb.append(getString(R.string.add_counter_youre_counting_towards));
    sb.append(mDateFormat.format(mCalendar.getTime()));
    sb.append(getString(R.string.add_counter_at));
    sb.append(mHourFormat.format(mCalendar.getTime()));
    mCounterText.setText(sb);

    initCounter(mCountDownText, (mCalendar.getTimeInMillis()-System.currentTimeMillis()));
  }

  private void initCounter(TextView countDownText, long futureMillis) {
    if (mCounter != null) {
      mCounter.cancel();
      mCounter.onFinish();
    }
    mCounter = new CustomCountDown(countDownText, futureMillis);
    mCounter.start();
  }
}
