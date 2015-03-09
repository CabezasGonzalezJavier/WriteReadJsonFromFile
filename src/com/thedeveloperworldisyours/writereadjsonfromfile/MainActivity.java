package com.thedeveloperworldisyours.writereadjsonfromfile;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.thedeveloperworldisyours.writereadjsonfromfile.models.Actress;
import com.thedeveloperworldisyours.writereadjsonfromfile.requesttask.RequestTask;
import com.thedeveloperworldisyours.writereadjsonfromfile.utils.Constants;
import com.thedeveloperworldisyours.writereadjsonfromfile.utils.Utils;

public class MainActivity extends ActionBarActivity implements OnClickListener,
		TextWatcher {

	private Button mGetSave;
	private Button mShow;
	private ListView mListView;
	private EditText mEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mGetSave = (Button) findViewById(R.id.activity_main_get_save_button);
		mShow = (Button) findViewById(R.id.activity_main_show_button);
		mListView = (ListView) findViewById(R.id.activity_main_listView);
		mEditText = (EditText) findViewById(R.id.activity_main_editText);

		mGetSave.setOnClickListener(this);
		mShow.setOnClickListener(this);

		mGetSave.setEnabled(false);
		mShow.setEnabled(false);
		mEditText.addTextChangedListener(this);
	}

	public void getSaveInfo() {

		if (Utils.isOnline(MainActivity.this)) {
			mShow.setEnabled(true);
			RequestTask task = new RequestTask(MainActivity.this);
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(Constants.URL);
			stringBuilder.append(mEditText.getText());
			task.execute(stringBuilder.toString());
		} else {
			Toast.makeText(MainActivity.this, R.string.no_internet,
					Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_clean) {

			mListView.setAdapter(null);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activity_main_get_save_button:
			getSaveInfo();
			break;
		case R.id.activity_main_show_button:
			returnJSONToAtress();
			break;
		}
	}

	public void returnJSONToAtress() {
		final String json = Utils.readFromFile(MainActivity.this);
		final Gson gson = new Gson();
		final Actress actress = gson.fromJson(json, Actress.class);
		if (actress == null||actress.getNameApprox().size()==0) {
			Toast.makeText(this, R.string.activity_main_no_atress,
					Toast.LENGTH_SHORT).show();
		} else {
			final ArrayList<String> list = new ArrayList<String>();
			for (int i = 0; i < actress.getNameApprox().size(); i++) {
				list.add(actress.getNameApprox().get(i).getDescription());
			}
			showListView(list);
		}

	}

	public void showListView(ArrayList<String> list) {
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);
		mListView.setAdapter(arrayAdapter);

	}

	@Override
	public void afterTextChanged(Editable s) {

		if (mEditText.length() == 0) {
			mGetSave.setEnabled(false);
		} else {
			mGetSave.setEnabled(true);
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

	}
}
