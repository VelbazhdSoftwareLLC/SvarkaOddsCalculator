package eu.veldsoft.svarka.odds.calculator;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.Toast;

public class CalculatorActivity extends Activity {
	private Spinner spinner1 = null;

	private Spinner spinner2 = null;

	private Spinner spinner3 = null;

	private Spinner spinner4 = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculator);

		spinner1 = ((Spinner) findViewById(R.id.spinner1));
		spinner2 = ((Spinner) findViewById(R.id.spinner2));
		spinner3 = ((Spinner) findViewById(R.id.spinner3));
		spinner4 = ((Spinner) findViewById(R.id.spinner4));

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
	}
}
