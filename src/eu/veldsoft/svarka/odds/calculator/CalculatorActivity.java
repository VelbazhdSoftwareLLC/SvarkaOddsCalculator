package eu.veldsoft.svarka.odds.calculator;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CalculatorActivity extends Activity {
	private static long MIN_TIME_FOR_CALCULATION = 1000;

	private Map<String, Integer> stringToNumber = new HashMap<String, Integer>();

	private Simulation simulation = null;

	private double result[] = { 0.0, 0.0, 0.0 };

	private Thread task = new Thread(new Runnable() {
		@Override
		public void run() {

			while (true) {
				if (simulation == null) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
					}
					continue;
				}

				long time = System.currentTimeMillis();
				while (System.currentTimeMillis() - time < MIN_TIME_FOR_CALCULATION) {
					result = simulation.round();
				}

				runOnUiThread(new Runnable() {
					public void run() {
						progressUpdate();
					}
				});

				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
				time = System.currentTimeMillis();
			}
		}
	});

	private Spinner spinner1 = null;

	private Spinner spinner2 = null;

	private Spinner spinner3 = null;

	private Spinner spinner4 = null;

	private TextView text1 = null;

	private TextView text2 = null;

	private TextView text3 = null;

	private void progressUpdate() {
		text1.setText("" + (int) (result[0] * 10000.0 + 0.5) / 100.0);
		text2.setText("" + (int) (result[1] * 10000.0 + 0.5) / 100.0);
		text3.setText("" + (int) (result[2] * 10000.0 + 0.5) / 100.0);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculator);

		stringToNumber.put("7♥", Constants.CARD_SUIT_HEARTS
				| Constants.CARD_KIND_SEVEN | Constants.CARD_SCORE_SEVEN);
		stringToNumber.put("8♥", Constants.CARD_SUIT_HEARTS
				| Constants.CARD_KIND_EIGHT | Constants.CARD_SCORE_EIGHT);
		stringToNumber.put("9♥", Constants.CARD_SUIT_HEARTS
				| Constants.CARD_KIND_NINE | Constants.CARD_SCORE_NINE);
		stringToNumber.put("T♥", Constants.CARD_SUIT_HEARTS
				| Constants.CARD_KIND_TEN | Constants.CARD_SCORE_TEN);
		stringToNumber.put("J♥", Constants.CARD_SUIT_HEARTS
				| Constants.CARD_KIND_JACK | Constants.CARD_SCORE_JACK);
		stringToNumber.put("Q♥", Constants.CARD_SUIT_HEARTS
				| Constants.CARD_KIND_QUEEN | Constants.CARD_SCORE_QUEEN);
		stringToNumber.put("K♥", Constants.CARD_SUIT_HEARTS
				| Constants.CARD_KIND_KING | Constants.CARD_SCORE_KING);
		stringToNumber.put("A♥", Constants.CARD_SUIT_HEARTS
				| Constants.CARD_KIND_ACE | Constants.CARD_SCORE_ACE);
		stringToNumber.put("7♣", Constants.CARD_SUIT_CLUBS
				| Constants.CARD_KIND_SEVEN | Constants.CARD_SCORE_SEVEN);
		stringToNumber.put("8♣", Constants.CARD_SUIT_CLUBS
				| Constants.CARD_KIND_EIGHT | Constants.CARD_SCORE_EIGHT);
		stringToNumber.put("9♣", Constants.CARD_SUIT_CLUBS
				| Constants.CARD_KIND_NINE | Constants.CARD_SCORE_NINE);
		stringToNumber.put("T♣", Constants.CARD_SUIT_CLUBS
				| Constants.CARD_KIND_TEN | Constants.CARD_SCORE_TEN);
		stringToNumber.put("J♣", Constants.CARD_SUIT_CLUBS
				| Constants.CARD_KIND_JACK | Constants.CARD_SCORE_JACK);
		stringToNumber.put("Q♣", Constants.CARD_SUIT_CLUBS
				| Constants.CARD_KIND_QUEEN | Constants.CARD_SCORE_QUEEN);
		stringToNumber.put("K♣", Constants.CARD_SUIT_CLUBS
				| Constants.CARD_KIND_KING | Constants.CARD_SCORE_KING);
		stringToNumber.put("A♣", Constants.CARD_SUIT_CLUBS
				| Constants.CARD_KIND_ACE | Constants.CARD_SCORE_ACE);
		stringToNumber.put("7♦", Constants.CARD_SUIT_DIAMONDS
				| Constants.CARD_KIND_SEVEN | Constants.CARD_SCORE_SEVEN);
		stringToNumber.put("8♦", Constants.CARD_SUIT_DIAMONDS
				| Constants.CARD_KIND_EIGHT | Constants.CARD_SCORE_EIGHT);
		stringToNumber.put("9♦", Constants.CARD_SUIT_DIAMONDS
				| Constants.CARD_KIND_NINE | Constants.CARD_SCORE_NINE);
		stringToNumber.put("T♦", Constants.CARD_SUIT_DIAMONDS
				| Constants.CARD_KIND_TEN | Constants.CARD_SCORE_TEN);
		stringToNumber.put("J♦", Constants.CARD_SUIT_DIAMONDS
				| Constants.CARD_KIND_JACK | Constants.CARD_SCORE_JACK);
		stringToNumber.put("Q♦", Constants.CARD_SUIT_DIAMONDS
				| Constants.CARD_KIND_QUEEN | Constants.CARD_SCORE_QUEEN);
		stringToNumber.put("K♦", Constants.CARD_SUIT_DIAMONDS
				| Constants.CARD_KIND_KING | Constants.CARD_SCORE_KING);
		stringToNumber.put("A♦", Constants.CARD_SUIT_DIAMONDS
				| Constants.CARD_KIND_ACE | Constants.CARD_SCORE_ACE);
		stringToNumber.put("7♠", Constants.CARD_SUIT_SPADES
				| Constants.CARD_KIND_SEVEN | Constants.CARD_SCORE_SEVEN);
		stringToNumber.put("8♠", Constants.CARD_SUIT_SPADES
				| Constants.CARD_KIND_EIGHT | Constants.CARD_SCORE_EIGHT);
		stringToNumber.put("9♠", Constants.CARD_SUIT_SPADES
				| Constants.CARD_KIND_NINE | Constants.CARD_SCORE_NINE);
		stringToNumber.put("T♠", Constants.CARD_SUIT_SPADES
				| Constants.CARD_KIND_TEN | Constants.CARD_SCORE_TEN);
		stringToNumber.put("J♠", Constants.CARD_SUIT_SPADES
				| Constants.CARD_KIND_JACK | Constants.CARD_SCORE_JACK);
		stringToNumber.put("Q♠", Constants.CARD_SUIT_SPADES
				| Constants.CARD_KIND_QUEEN | Constants.CARD_SCORE_QUEEN);
		stringToNumber.put("K♠", Constants.CARD_SUIT_SPADES
				| Constants.CARD_KIND_KING | Constants.CARD_SCORE_KING);
		stringToNumber.put("A♠", Constants.CARD_SUIT_SPADES
				| Constants.CARD_KIND_ACE | Constants.CARD_SCORE_ACE);

		spinner1 = ((Spinner) findViewById(R.id.spinner1));
		spinner2 = ((Spinner) findViewById(R.id.spinner2));
		spinner3 = ((Spinner) findViewById(R.id.spinner3));
		spinner4 = ((Spinner) findViewById(R.id.spinner4));

		text1 = ((TextView) findViewById(R.id.textView1));
		text2 = ((TextView) findViewById(R.id.textView2));
		text3 = ((TextView) findViewById(R.id.textView3));

		spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (spinner1.getSelectedItemPosition() == spinner2
						.getSelectedItemPosition()) {
					spinner1.setSelection(0);
				} else if (spinner1.getSelectedItemPosition() == spinner3
						.getSelectedItemPosition()) {
					spinner1.setSelection(0);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (spinner2.getSelectedItemPosition() == spinner1
						.getSelectedItemPosition()) {
					spinner2.setSelection(0);
				} else if (spinner2.getSelectedItemPosition() == spinner3
						.getSelectedItemPosition()) {
					spinner2.setSelection(0);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		spinner3.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (spinner3.getSelectedItemPosition() == spinner1
						.getSelectedItemPosition()) {
					spinner3.setSelection(0);
				} else if (spinner3.getSelectedItemPosition() == spinner2
						.getSelectedItemPosition()) {
					spinner3.setSelection(0);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		((Button) findViewById(R.id.button1))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (spinner1.getSelectedItemPosition() == 0) {
							Toast.makeText(CalculatorActivity.this,
									R.string.card_1_warning, Toast.LENGTH_SHORT)
									.show();
							return;
						}
						if (spinner2.getSelectedItemPosition() == 0) {
							Toast.makeText(CalculatorActivity.this,
									R.string.card_2_warning, Toast.LENGTH_SHORT)
									.show();
							return;
						}
						if (spinner3.getSelectedItemPosition() == 0) {
							Toast.makeText(CalculatorActivity.this,
									R.string.card_3_warning, Toast.LENGTH_SHORT)
									.show();
							return;
						}
						if (spinner4.getSelectedItemPosition() == 0) {
							Toast.makeText(CalculatorActivity.this,
									R.string.number_of_players_warning,
									Toast.LENGTH_SHORT).show();
							return;
						}

						simulation = new Simulation(Integer
								.valueOf((String) spinner4.getSelectedItem()),
								stringToNumber.get(spinner1.getSelectedItem()),
								stringToNumber.get(spinner2.getSelectedItem()),
								stringToNumber.get(spinner3.getSelectedItem()));
					}
				});

		((ImageView) findViewById(R.id.ebinqoLogo))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						CalculatorActivity.this.startActivity(new Intent(
								Intent.ACTION_VIEW, Uri.parse(getResources()
										.getString(R.string.ebinqo_url))));
					}
				});

		task.start();
	}

	@Override
	protected void onDestroy() {
		task.interrupt();
	}
}
